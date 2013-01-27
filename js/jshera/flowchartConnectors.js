;(function() {
	var allDivTarefas = [], allConnToDetach = [], e1, e2;
    var allDivHierarquias = [];
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
                "II_TT":0
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
            "II_TT":1
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
            "II_TT":1
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

				//guardando as tarefas que foram conectadas				
				if ($.inArray(idConector, allDivHierarquias) == -1){
					allDivHierarquias.push(idConector)	;
					//alert ('connection.sourceId = ' + connection.sourceId + ' - connection.targetId = ' + connection.targetId);
					//connection.getOverlay("label").setLabel(connection.sourceId.substring(6) + "-" + connection.targetId.substring(6));


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
        alert('abrindo jshera2');
	
	//comentando a inicialização de objetos
	//jsPlumbDemo.init();
       
});
