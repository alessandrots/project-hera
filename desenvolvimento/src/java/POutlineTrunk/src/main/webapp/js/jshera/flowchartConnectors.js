;(function() {
	var allDivTarefas = [], allConnToDetach = [], e1, e2;
    var allDivHierarquias = [], allTarefasConectadas = []; //, tarefasPai= [];
    var arvore = null;
    var referenciaPosicao = null;


    // this is the paint style for the connecting lines..
    //CORES: http://shibolete.tripod.com/RGB.html
    //TODO aqui pode ser mudado nextColour que tem no stateMachineDemo-jquery.js
    var connectorPaintStyle = {
            lineWidth:5,
            strokeStyle:"#deea18",
            joinstyle:"round",
            outlineColor:"#EAEDEF",
            outlineWidth:7
        },

    // .. and this is the hover style.arvore
    connectorHoverStyle = {
            lineWidth:7,
            strokeStyle:"#2e2aF8"
    },

    targetEndpoint = {
            endpoint:"Dot",
            paintStyle:{ fillStyle:"#238E23",radius:7 },//Verde Floresta
            hoverPaintStyle:connectorHoverStyle,
            maxConnections:-1,
            dropOptions:{ hoverClass:"hover", activeClass:"active" },
            isTarget:true,
            parameters:{
                "II_TT":0
            },
            overlays:[
                [ "Label", {
                    location:[0.5, -0.5],
                    //label:"Drop",
                    cssClass:"endpointTargetLabel" } ]
            ]
    },

    sourceEndpoint = {
            endpoint:"Dot",
            paintStyle:{ fillStyle:"#5959AB",radius:7 },//Azul Rich
            isSource:true,
            maxConnections:-1,
            //connector:[ "Flowchart", { stub:[40, 60], gap:10 } ],
            connector:[ "StateMachine", { curviness:0 } ],
            connectorStyle:connectorPaintStyle,
            hoverPaintStyle:connectorHoverStyle,
            connectorHoverStyle:connectorHoverStyle,
            dragOptions:{},
            parameters:{
                "II_TT":0 //0 define conexão TI
            },
            overlays:[
                [ "Label", {
                    location:[0.5, 1.5],
                    //label:"Drag",
                    cssClass:"endpointSourceLabel"
                } ]
            ]
    },

    //padrão de destino para II e TT
    targetIITTEndpoint = {
            endpoint:"Dot",
            paintStyle:{ fillStyle:"#8C1717",radius:7 },//Escarlata
            hoverPaintStyle:connectorHoverStyle,
            maxConnections:-1,
            dropOptions:{ hoverClass:"hover", activeClass:"active" },
            isTarget:true,
            parameters:{
                "II_TT":1
            },
            overlays:[
                [ "Label", {
                    location:[0.5, -0.5],
                    //label:"Drop",
                    cssClass:"endpointTargetLabel" } ]
            ]
    },


    //padrão de origem para II e TT
    sourceIIEndpoint = {
        endpoint:"Dot",
        paintStyle:{ fillStyle:"#FF7F00",radius:7 },//Coral
        isSource:true,
        maxConnections:-1,
        connector:[ "StateMachine", { curviness:90 } ],
        connectorStyle:connectorPaintStyle,
        hoverPaintStyle:connectorHoverStyle,
        connectorHoverStyle:connectorHoverStyle,
        dragOptions:{},
        parameters:{
            "II_TT":1 //1 define conexão II
        },
        overlays:[
            [ "Label", {
                location:[0.5, 1.5],
                //label:"Drag",
                cssClass:"endpointSourceLabel"
            } ]
        ]
    },

    //padrão de origem para II e TT
    sourceTTEndpoint = {
        endpoint:"Dot",
        paintStyle:{ fillStyle:"#FF7F00",radius:7 },//Coral
        isSource:true,
        maxConnections:-1,
        //connector:[ "StateMachine", { curviness:-90 } ],
        connector:[ "StateMachine", { curviness:-90 } ],
        connectorStyle:connectorPaintStyle,
        hoverPaintStyle:connectorHoverStyle,
        connectorHoverStyle:connectorHoverStyle,
        dragOptions:{},
        parameters:{
            "II_TT":2 //2 define conexão TT
        },
        overlays:[
            [ "Label", {
                location:[0.5, 1.5],
                //label:"Drag",
                cssClass:"endpointSourceLabel"
            } ]
        ]
    };
	

	window.jsPlumbDemo = {

        /**
         * Criacao da Tarefa com base no id da tarefa(div)
         * @param id
         */
		criarTarefa : function(id) {
			//alert('oi');
			jsPlumb.importDefaults({
				// default drag options
				DragOptions : { cursor: 'pointer', zIndex:2000 },
				// default to blue at one end and green at the other
				EndpointStyles : [{ fillStyle:'#225588' }, { fillStyle:'#558822' }],
			
				// blue endpoints 7 px; green endpoints 11. -> características das linhas
				Endpoints : [ [ "Dot", {radius:7} ], [ "Dot", { radius:11 } ]],
			
				// the overlays to decorate each connection with.  note that the label overlay uses a function to generate the label text; in this
				// case it returns the 'labelText' member that we set on each connection in the 'init' method below.
				ConnectionOverlays : [
					[ "Arrow", { location:0.9 } ],
					[ "Label", { 
						location:0.1,
						id:"label",
						cssClass:"aLabel"
					}]
				],
				ConnectorZIndex:5
			});

			//Aqui tá colocando o label do source com o target (source-target)
			var init = function(connection) {
                 //id das janelas conectadas
                 var idConector = connection.sourceId + "_" + connection.targetId;

                 console.log(" source = " + connection.sourceId +
                             " target = " + connection.targetId +
                             " II_TT  = " + connection.endpoints[0].getParameter("II_TT"));


                 jsPlumbDemo.sincronizarNoServer(connection.sourceId,
                                                 connection.targetId,
                                                 connection.endpoints[0].getParameter("II_TT"));

				//guardando as tarefas que foram conectadas				
				if ($.inArray(idConector, allDivHierarquias) == -1){
					allDivHierarquias.push(idConector)	;
					console.log ('connection.sourceId = ' + connection.sourceId
                        + ' - connection.targetId = ' + connection.targetId);

                    if (arvore == null){
                        arvore = new Arvore();
                    }

                    /*
                     * Qdo fizer a conexão entre componentes já seta na respectiva combo quem é
                     * de origem e quem é de destino.
                     */
                    //source - adicionando ao combo de origem
                    $('#sltOrigem').addOption(connection.sourceId, connection.sourceId, false);

                    //target - adicionando ao combo de destino
                    $('#sltDestino').addOption(connection.targetId, connection.targetId, false);
					
                    //guardando para a hierarquia
                    arvore.montarArvore(connection.sourceId, connection.targetId);
				}//fim if inArray

			};//fim init

			jsPlumb.draggable(jsPlumb.getSelector(".window"));

            /**
             Guardando as divs tarefas criadas para trabalhar a hierarquia.
             */
            if (referenciaPosicao == null){
                referenciaPosicao = new ReferenciaPosicao();
            }
            referenciaPosicao.definirPosicaoCriacao(id);
            allDivTarefas.push(id);

            //Criando pontos de origem II e TT (sourceIITTEndpoint)
            jsPlumb.addEndpoint(id, { anchor:[0, 0, 0, 0] }, targetIITTEndpoint);
            jsPlumb.addEndpoint(id, { anchor:[1, 0, 0, 0] }, targetIITTEndpoint);

            //Criando pontos de origem II e TT (sourceIITTEndpoint)
            jsPlumb.addEndpoint(id, { anchor:[0, 1, 0, 0] }, sourceIIEndpoint);
            jsPlumb.addEndpoint(id, { anchor:[1, 1, 0, 0] }, sourceTTEndpoint);

			//Criando pontos de origem
			jsPlumb.addEndpoint(id, { anchor:[0, 0.3, -1, 0] }, sourceEndpoint);
            jsPlumb.addEndpoint(id, { anchor:[0, 0.6, -1, 0] }, targetEndpoint);

            //Criando pontos de destino
			jsPlumb.addEndpoint(id, { anchor:[1, 0.3, -1, 0] }, sourceEndpoint);
            jsPlumb.addEndpoint(id, { anchor:[1, 0.6, -1, 0] }, targetEndpoint);

			// listen for new connections; initialise them the same way we initialise the connections at startup.
			jsPlumb.bind("jsPlumbConnection", function(connInfo, originalEvent) {
                //alert('quando faz a conexão das tarefas');
				init(connInfo.connection);
			});

            jsPlumb.bind("connectionDrag", function(connection) {
                //console.log("connection " + connection.id + " is being dragged");
            });

            //Clicar na seta de conexão
            jsPlumb.bind("click", function(connection) {
                var idConector = connection.sourceId + "_" + connection.targetId;

                //guardando as tarefas que já foram questionadas ao usuário se deseja ser deletada
                if ($.inArray(idConector, allConnToDetach) == -1){
                    console.log("id = " + connection.id
                        +  " source = " + connection.endpoints[0].elementId
                        +  " target = " + connection.endpoints[1].elementId
                        +  " source x = " + connection.endpoints[0].anchor.x
                        +  " source y = " + connection.endpoints[0].anchor.y
                        +  " source dx = " + connection.endpoints[0].anchor.getOrientation(connection.endpoints[0])[0]
                        +  " source dy = " + connection.endpoints[0].anchor.getOrientation(connection.endpoints[0])[1]
                        +  "  "
                        +  " target x = " + connection.endpoints[1].anchor.x
                        +  " target y = " + connection.endpoints[1].anchor.y
                        +  " target dx = " + connection.endpoints[1].anchor.getOrientation(connection.endpoints[1])[0]
                        +  " target dy = " + connection.endpoints[1].anchor.getOrientation(connection.endpoints[1])[1]
                    );

                    if (confirm("Vc quer apagar conexão - origem: " + connection.sourceId + " destino: " + connection.targetId + "?")) {
                        jsPlumb.detach(connection);
                    }
                    //guardando a conexão
                    allConnToDetach.push(idConector);
                } else {
                    allConnToDetach.pop(idConector);
                }
            });
	    }, //fim do método criarTarefa

        imprimir : function(escolha) {
            if (arvore == null){
                arvore = new Arvore();
            }

            if (escolha == 1){
                //imprime hierarquia em formato de árvore
                arvore.imprimirHierarquia(arvore.root, escolha);
            } else if (escolha == 2){
                //imprimi as tarefas que já foram guardadas
                arvore.imprimirHierarquia(arvore.tarefas, escolha);
            }
        },

        /**
         * Exibe a hierarquia em forma de árvore utilizando o jstree.
         */
        exibirHierarquia : function() {
            if (arvore == null){
                arvore = new Arvore();
            }

            var sbRoot = new StringBuffer();
            sbRoot = arvore.montarTreeView(arvore.root, sbRoot);
        },

        /**
         * Reposicionamento de objetos, após definição da hierarquia.
         */
        reposicionarObjetos : function() {
            if (arvore == null){
                arvore = new Arvore();
            }

            arvore.reposicionarObjetos();

            //Force repaint of every connection
            jsPlumb.repaintEverything();
        },

        /***
         * Faz a clonagem da tarefa com todos os seus respectivos endpoints.
         *
         * @param idTarefaModelo
         * @param idTarget: É o nome da nova tarefa a ser clonada,
         */
        clonarTarefa : function(idTarefaModelo, idNovaTarefaClone) {
            jsPlumb.importDefaults({
                // default drag options
                DragOptions : { cursor: 'pointer', zIndex:2000 },
                // default to blue at one end and green at the other
                EndpointStyles : [{ fillStyle:'#225588' }, { fillStyle:'#558822' }],

                // blue endpoints 7 px; green endpoints 11. -> características das linhas
                Endpoints : [ [ "Dot", {radius:7} ], [ "Dot", { radius:11 } ]],

                // the overlays to decorate each connection with.  note that the label overlay uses a function to generate the label text; in this
                // case it returns the 'labelText' member that we set on each connection in the 'init' method below.
                ConnectionOverlays : [
                    [ "Arrow", { location:0.9 } ],
                    [ "Label", {
                        location:0.1,
                        id:"label",
                        cssClass:"aLabel"
                    }]
                ],
                ConnectorZIndex:5
            });

            jsPlumb.draggable(jsPlumb.getSelector(".window"));

            //COPIANDO OS ENDPOINTS do tipo SOURCE
            jsPlumb.selectEndpoints({source:$('#' + idTarefaModelo)}).each(function(endpoint) {
                //console.log(" anchor.elementId = " +endpoint.anchor.elementId);
                //console.log(" endpoint.uuid = " + endpoint.uuid + " isSource = " + endpoint.isSource);//ok
                //console.log(" source - endpoint.II_TT = " + endpoint.getParameter("II_TT"));
                if (endpoint.getParameter("II_TT") == 1) {
                    jsPlumb.addEndpoint($('#' + idNovaTarefaClone), sourceIITTEndpoint, {anchor:endpoint.anchor});
                } else {
                    jsPlumb.addEndpoint($('#' + idNovaTarefaClone), sourceEndpoint, {anchor:endpoint.anchor});
                }

            });

            //COPIANDO OS ENDPOINTS do tipo TARGET
            jsPlumb.selectEndpoints({target:$('#' + idTarefaModelo)}).each(function(endpoint) {
                //console.log(" anchor.elementId = " +endpoint.anchor.elementId);
                //console.log(" endpoint.uuid = " + endpoint.uuid + " isTarget = " + endpoint.isTarget);//ok
                //console.log(" target - endpoint.II_TT = " + endpoint.getParameter("II_TT"));
                if (endpoint.getParameter("II_TT") == 1) {
                    jsPlumb.addEndpoint($('#' + idNovaTarefaClone), targetIITTEndpoint, {anchor:endpoint.anchor});
                } else {
                    jsPlumb.addEndpoint($('#' + idNovaTarefaClone), targetEndpoint, {anchor:endpoint.anchor});
                }
            });

            //Recuperando o ARRAY de Anchor para para os endpoints source da tarefa
            jsPlumb.selectEndpoints({source:$('#' + idTarefaModelo)}).each(function(endpoint) {
                console.log(" SOURCE anchor id = " + endpoint.anchor.elementId
                    + " x = " + endpoint.anchor.x
                    + " y = " + endpoint.anchor.y
                    + " dx = " + endpoint.anchor.getOrientation(endpoint)[0]
                    + " dy = " + endpoint.anchor.getOrientation(endpoint)[1]);
            });

            //Recuperando o ARRAY de Anchor para os endpoints target da tarefa
            jsPlumb.selectEndpoints({target:$('#' + idTarefaModelo)}).each(function(endpoint) {
                console.log(" TARGET anchor id = " + endpoint.anchor.elementId
                    + " x = " + endpoint.anchor.x
                    + " y = " + endpoint.anchor.y
                    + " dx = " + endpoint.anchor.getOrientation(endpoint)[0]
                    + " dy = " + endpoint.anchor.getOrientation(endpoint)[1]);
            });

            //para setar o nome da tarefa no combo específico
            //TODO - colocar no core.js
            if ($("#cbxCloneTarget").is(":checked")) {
                console.log("Clonei uma tarefa de target.");
                //target - adicionando tarefa clonada ao combo de destino
                $('#sltDestinoNovo').addOption(idNovaTarefaClone, idNovaTarefaClone, false);
            } else {
                console.log("Clonei uma tarefa de source.");
                //source - adicionando tarefa clonada ao combo de origem
                $('#sltOrigemNovo').addOption(idNovaTarefaClone, idNovaTarefaClone, false);
            }
        },


        showAllConexoes : function(tipoEndpoint, nomeEndpoint) {
            //var connectionList = jsPlumb.getConnections();
            //var c = jsPlumb.getConnections({source:"mySourceElement"});
            var connectionList = null;

            console.log('escolha do radio = ', tipoEndpoint);
            console.log('escolha do nome da tarefa = ', nomeEndpoint);

            if (tipoEndpoint == 0) {
                console.log('0');
                connectionList = jsPlumb.getConnections({source:nomeEndpoint});
            } else if (tipoEndpoint == 1) {
                console.log('1');
                connectionList = jsPlumb.getConnections({target:nomeEndpoint});
            } else {
                console.log('2');
                connectionList = jsPlumb.getConnections();
            }

            //Endpoints
            var endConnSource = null;
            var endConnTarget = null;

            if (connectionList != null && connectionList.length >0){
                console.log(' connectionList = ' + connectionList);

                for (var j = 0; j < connectionList.length; j++) {
                    console.log(" *** NOVA CONEXÃO *** ");
                    console.log(
                        " conexoes id = " +  connectionList[j].id
                            +  " sourceId = " + connectionList[j].sourceId
                            +  " targetId = " + connectionList[j].targetId);

                    console.log(
                        " source x = " +  connectionList[j].endpoints[0].anchor.x
                            +  " source y = " + connectionList[j].endpoints[0].anchor.y
                            +  " source dx = " + connectionList[j].endpoints[0].anchor.getOrientation(connectionList[j].endpoints[0])[0]
                            +  " source dy = " + connectionList[j].endpoints[0].anchor.getOrientation(connectionList[j].endpoints[0])[1]
                            +  " source II_TT = " + connectionList[j].endpoints[0].getParameter("II_TT")
                    );

                    console.log(
                        " target x = " +  connectionList[j].endpoints[1].anchor.x
                            +  " target y = " + connectionList[j].endpoints[1].anchor.y
                            +  " target dx = " + connectionList[j].endpoints[1].anchor.getOrientation(connectionList[j].endpoints[1])[0]
                            +  " target dy = " + connectionList[j].endpoints[1].anchor.getOrientation(connectionList[j].endpoints[1])[1]
                            +  " target II_TT = " + connectionList[j].endpoints[1].getParameter("II_TT")
                    );

                    //Endpoint de ORIGEM pertencene a conexao recuperada
                    endConnSource = connectionList[j].endpoints[0];

                    //Endpoint de DESTINO pertencene a conexao recuperada
                    endConnTarget = connectionList[j].endpoints[1];
                }
            } else {
                console.log(" NÃO TEM CONEXÃO NENHUMA. ");
            }


        },

        /**
         * Recupera todas as conexões existentes no modelo, guarda o nome das conexões num array.
         * Estas conexões vão servir para, em outro método, descobrir qual é(quais são) a(s) tarefas PAI,
         * ou seja aquelas que somente são origem.
         */
        recuperarTodasConexoes : function() {
            var connectionList = jsPlumb.getConnections();

            //Endpoints
            var endConnSource = null;
            var endConnTarget = null;

            allTarefasConectadas = new Array();

            for (var j = 0; j < connectionList.length; j++) {
                /*
                console.log(" *** NOVA CONEXÃO *** ");
                console.log(" conexoes id = " +  connectionList[j].id
                        +  " sourceId = " + connectionList[j].sourceId
                        +  " targetId = " + connectionList[j].targetId);
                 */

                if ($.inArray(connectionList[j].sourceId, allTarefasConectadas) == -1) {
                    allTarefasConectadas.push(connectionList[j].sourceId)
                }

                if ($.inArray(connectionList[j].targetId, allTarefasConectadas) == -1) {
                    allTarefasConectadas.push(connectionList[j].targetId)
                }

                //Endpoint de ORIGEM pertencene a conexao recuperada
                endConnSource = connectionList[j].endpoints[0];

                //Endpoint de DESTINO pertencene a conexao recuperada
                endConnTarget = connectionList[j].endpoints[1];
            }

            console.log(" *** TOTAL CONEXões encontradas = *** ", allTarefasConectadas.length);
        },

        /**
         * Função responsável por descobrir quais tarefas são PAI, ou seja somente são endpoint do tipo SOURCE.
         *
         * Não tem nenhuma connection bind chegando como destinho nela.
         */
        descobrirTarefaPai : function() {
            var connectionList = null;

            //recupera todas as conexões
            this.recuperarTodasConexoes();

            var tarefasPai = new Array();

            //verifica qual tarefa não é target de ninguém
            for (var j = 0; j < allTarefasConectadas.length; j++) {
                connectionList = jsPlumb.getConnections({target:allTarefasConectadas[j]});
                if (connectionList != null && connectionList.length == 0){
                    tarefasPai.push(allTarefasConectadas[j]);
                }
            }

            return tarefasPai;
        },

        /**
         * Aqui é para fazer a sincronização automática de todo o modelo.
         * @param tarefasRaiz
         */
        proverSincronizacao : function(tarefasRaiz) {
            var connectionListTarget = null;
            var novaTarefaRaiz = [];

            if (tarefasRaiz != null && tarefasRaiz.length > 0) {
                for (var j = 0; j < tarefasRaiz.length; j++) {
                    console.log('proverSincronizacao >> tarefa PAI = ', tarefasRaiz[j]);

                    //Recupera todas as conexões na qual ela é origem
                    connectionListTarget = jsPlumb.getConnections({source:tarefasRaiz[j]});

                    console.log('tamanho lista de targets = ',connectionListTarget.length);

                    //se houver conexões de destino então elas serão percorridas
                    if (connectionListTarget != null && connectionListTarget.length > 0){
                        for (var k = 0; k < connectionListTarget.length; k++) {
                            //vai chamar a sincronização que é a implementação da regra no server.
                            /*
                            this.sincronizarNoServer(connectionListTarget[k].sourceId,
                                                     connectionListTarget[k].targetId,
                                                     connectionListTarget[k].endpoints[0].getParameter("II_TT")
                            );

                             //                            novaTarefaRaiz = new Array();
                             //                            novaTarefaRaiz.push(connectionListTarget[k].targetId);
                             //                            this.proverSincronizacao(novaTarefaRaiz);

                            */
                            myModel = new TarefaModel();
                            myModel.urlRoot = 'project/cadTarefas/sincronizarTarefas';

                            var search_params = {
                                'source': connectionListTarget[k].sourceId,
                                'target': connectionListTarget[k].targetId,
                                'tipoRelacionamentoLogico': connectionListTarget[k].endpoints[0].getParameter("II_TT")
                            };

                            var myTarget = connectionListTarget[k].targetId;

                            //enviando VÁRIOS PARÂMETROS,
                            myModel.fetch({data: $.param(search_params)}, {
                                success: function() {
                                    console.log('funcionou!!!');
                                },
                                error: function() {
                                    console.log('se deu mal!!!');
                                }
                             }
                            ).done(function () {
                                /*
                                    Aqui processa sincronamente
                                 */
                                novaTarefaRaiz = new Array();
                                novaTarefaRaiz.push(myTarget);
                                jsPlumbDemo.proverSincronizacao(novaTarefaRaiz);
                            });


                        }
                    } else {
                        return;
                    }
                }
            } else {
                return;
            }
        },

        /**
         * Aqui faz a sincronização no momento em que há um bind entre duas tarefas (divs)
         *
         * @param sourceId
         * @param targetId
         * @param tipoRelacionamentoLogico
         */
        sincronizarNoServer : function(sourceId, targetId, tipoRelacionamentoLogico) {
//            Backbone.emulateJSON = true;
            myModel = new TarefaModel();
            myModel.urlRoot = 'project/cadTarefas/sincronizarTarefas';

            console.log(' >>> sincronizarNoServer.');

            var search_params = {
                'source': sourceId,
                'target': targetId,
                'tipoRelacionamentoLogico': tipoRelacionamentoLogico
            };

            //enviando VÁRIOS PARÂMETROS,
            myModel.fetch({data: $.param(search_params)}).done(function () {
                /* */
                console.log(' retorno... ');
            });
        },


        /**
         * Recupera as conexões existentes de dois componentes (idSource e idTarget - divs) já em tela.
         *
         * De cada conexão recupera os ENDPOINT de origem e de destino.
         *
         * Recupera as divs dos novos componentes (tarefas) por meio de combos (sltOrigemNovo e sltDestinoNovo).
         *
         * A partir dos Endpoints, recupera os anchors de cada um e seta a conexão (connect) paras os novos divs
         * que foram recuperados anteriormente.
         *
         *
         * @param idSource : corresponde  ao <div> de onde saiu o Endpoint de origem.
         * @param idTarget : corresponde  ao <div> de onde chegou o Endpoint de destino.
         */
        carregarConexoes : function(idSource, idTarget) {
            /* */
            jsPlumb.importDefaults({
                // default drag options
                DragOptions : { cursor: 'pointer', zIndex:2000 },
                // default to blue at one end and green at the other
                EndpointStyles : [{ fillStyle:'#225588' }, { fillStyle:'#558822' }],

                // blue endpoints 7 px; green endpoints 11. -> características das linhas
                Endpoints : [ [ "Dot", {radius:7} ], [ "Dot", { radius:11 } ]],

                // the overlays to decorate each connection with.  note that the label overlay uses a function to generate the label text; in this
                // case it returns the 'labelText' member that we set on each connection in the 'init' method below.
                ConnectionOverlays : [
                    [ "Arrow", { location:0.9 } ],
                    [ "Label", {
                        location:0.1,
                        id:"label",
                        cssClass:"aLabel"
                    }]
                ],
                ConnectorZIndex:5
            });

            jsPlumb.draggable(jsPlumb.getSelector(".window"));

            console.log(' carregarConexoes ');

            var connection_   = jsPlumb.getConnections({source:$('#' + idSource), target:$('#' + idTarget)});

            //Endpoints
            var endConnSource = null;
            var endConnTarget = null;

            for (var j = 0; j < connection_.length; j++) {

                console.log(" conexoes id = "
                    +  connection_[j].id
                    +  " sourceId = " + connection_[j].sourceId
                    +  " targetId = " + connection_[j].targetId);

                console.log(" source x = "
                    +  connection_[j].endpoints[0].anchor.x
                    +  " source y = " + connection_[j].endpoints[0].anchor.y
                    +  " source dx = " + connection_[j].endpoints[0].anchor.getOrientation(connection_[j].endpoints[0])[0]
                    +  " source dy = " + connection_[j].endpoints[0].anchor.getOrientation(connection_[j].endpoints[0])[1]);

                console.log(" target x = "
                    +  connection_[j].endpoints[1].anchor.x
                    +  " target y = " + connection_[j].endpoints[1].anchor.y
                    +  " target dx = " + connection_[j].endpoints[1].anchor.getOrientation(connection_[j].endpoints[1])[0]
                    +  " target dy = " + connection_[j].endpoints[1].anchor.getOrientation(connection_[j].endpoints[1])[1]
                );

                //Endpoint de ORIGEM pertencene a conexao recuperada
                endConnSource = connection_[j].endpoints[0];

                //Endpoint de DESTINO pertencene a conexao recuperada
                endConnTarget = connection_[j].endpoints[1];
            }

            //TODO - passar os parâmetros via chamada da função
            //TODO - fazer o tratamento no index.html ou no core.js
            if ($('#sltOrigemNovo').val() == 'SELECIONE' || $('#sltDestinoNovo').val() =='SELECIONE') {
                alert('Escolha novas tarefas a serem conectados')
            }
            idNovaTarefaOrigem  = $('#sltOrigemNovo').val();
            idNovaTarefaDestino = $('#sltDestinoNovo').val();

            //Recuperando o ARRAY de Anchor para para os endpoints source da tarefa
            jsPlumb.selectEndpoints({source:$('#' + idNovaTarefaOrigem)}).each(function(endpoint) {
                console.log(" idNovaTarefaOrigem anchor id = "
                    + endpoint.anchor.elementId
                    + " x = " + endpoint.anchor.x
                    + " y = " + endpoint.anchor.y
                    + " dx = " + endpoint.anchor.getOrientation(endpoint)[0]
                    + " dy = " + endpoint.anchor.getOrientation(endpoint)[1]);
            });

            //Recuperando o ARRAY de Anchor para os endpoints target da tarefa
            jsPlumb.selectEndpoints({target:$('#' + idNovaTarefaDestino)}).each(function(endpoint) {
                console.log(" idNovaTarefaDestino anchor id = "
                    + endpoint.anchor.elementId
                    + " x = " + endpoint.anchor.x
                    + " y = " + endpoint.anchor.y
                    + " dx = " + endpoint.anchor.getOrientation(endpoint)[0]
                    + " dy = " + endpoint.anchor.getOrientation(endpoint)[1]);
            });

            //TODO - quase lá
            jsPlumb.connect({
                source:idNovaTarefaOrigem,
                target:idNovaTarefaDestino,
                endpoint:[ "Dot", { fillStyle:"#5959AB",radius:7  } ],
                connector:[ "Flowchart", { stub:[40, 60], gap:10 } ],
                connectorStyle:connectorPaintStyle,
                hoverPaintStyle:connectorHoverStyle,
                connectorHoverStyle:connectorHoverStyle,
                overlays: [
                    [ "Label", {
                        location:[0.5, 1.5],
                        cssClass:"endpointSourceLabel"
                    } ]
                ],
                anchors:[endConnSource.anchor, endConnTarget.anchor]
            });

            /*
            jsPlumb.makeSource(idNovaTarefaOrigem, {
                endpoint:sourceEndpoint
            });

            jsPlumb.makeTarget(idNovaTarefaDestino, {
                endpoint:targetEndpoint
            });
            */
        }//end of carregarConexoes

	};
})();

//Fazendo a inicialização do objeto jsPlumbDemo
jsPlumb.bind("ready", function() {
        alert('abrindo jshera NOVO!');
	
	//comentando a inicialização de objetos
	//jsPlumbDemo.init();
       
});
