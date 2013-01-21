;(function() {
	var allDivTarefas = [], e1, e2;
        var allDivHierarquias = [];
        //var self = this;
        var arvore = null;

    // this is the paint style for the connecting lines..
    //TODO aqui pode ser mudado nextColour que tem no stateMachineDemo-jquery.js
    var connectorPaintStyle = {
            lineWidth:5,
            strokeStyle:"#deea18",
            joinstyle:"round",
            outlineColor:"#EAEDEF",
            outlineWidth:7
        },

    // .. and this is the hover style.
        connectorHoverStyle = {
            lineWidth:7,
            strokeStyle:"#2e2aF8"
        },

        targetEndpoint = {
            endpoint:"Dot",
            paintStyle:{ fillStyle:"#558822",radius:7 },
            hoverPaintStyle:connectorHoverStyle,
            maxConnections:-1,
            dropOptions:{ hoverClass:"hover", activeClass:"active" },
            isTarget:true,
            overlays:[
                [ "Label", {
                    location:[0.5, -0.5],
                    //label:"Drop",
                    cssClass:"endpointTargetLabel" } ]
            ]
        },

        sourceEndpoint = {
            endpoint:"Dot",
            paintStyle:{ fillStyle:"#225588",radius:7 },
            isSource:true,
            connector:[ "Flowchart", { stub:[40, 60], gap:10 } ],
            connectorStyle:connectorPaintStyle,
            hoverPaintStyle:connectorHoverStyle,
            connectorHoverStyle:connectorHoverStyle,
            dragOptions:{},
            overlays:[
                [ "Label", {
                    location:[0.5, 1.5],
                    //label:"Drag",
                    cssClass:"endpointSourceLabel"
                } ]
            ]
        };
	

	window.jsPlumbDemo = {

		/********************************************************/
		/* Criacao da Tarefa */
		/********************************************************/
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
					connection.getOverlay("label").setLabel(connection.sourceId.substring(6) + "-" + connection.targetId.substring(6));


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
					
                    //console.log("c is a Connection object.  it connects " + connection.sourceId + " to " + connection.targetId);
                    //console.log("sourceEndpoint : " + connection.sourceEndpoint);
                    arvore.montarArvore(connection.sourceId, connection.targetId);
				}//fim if inArray

			};//fim init

			jsPlumb.draggable(jsPlumb.getSelector(".window"));
		
			/**
			 Mudando a posição
			 Havendo mais de uma tarefa, Pega a última tarefa, para reposicionar a mesma,
			 mantendo o mesmo left e variando o top com base na altura mais um valor constante,
			 a partir do último componente div.
			*/	
			if (allDivTarefas.length > 0) {
			  var idUltimaTarefaTmp = allDivTarefas[allDivTarefas.length-1];
			  var coordenadas = $('#' + idUltimaTarefaTmp).offset();
              var altura = $('#' + idUltimaTarefaTmp).height();
                          
			  
              $('#' + id).offset(function(index, coord) {
			      var newPosition =coordenadas.top + altura + 30;
			      return {top: newPosition, left: coordenadas.left};
			  });
			} else {
			   $('#' + id).offset(function(index, coord) {
			      return {top: coord.top +20, left: coord.left + 25};
			   });	
			}  
			
			//Criando pontos de origem
            //TODO botar num loop igual dynamicAnchorsDemo.js = for (var i = 0 ; i < divsWithWindowClass.length; i++) {
			jsPlumb.addEndpoint(id, { anchor:[0, 0, 0, 0] }, sourceEndpoint);
			jsPlumb.addEndpoint(id, { anchor:[1, 0, 0, 0] }, sourceEndpoint);
			jsPlumb.addEndpoint(id, { anchor:[0, 1, 0, 0] }, sourceEndpoint);
			jsPlumb.addEndpoint(id, { anchor:[1, 1, 0, 0] }, sourceEndpoint);

			//Criando pontos de destino
            jsPlumb.addEndpoint(id, { anchor:[0.5, 0, 0, 0] }, targetEndpoint);
            jsPlumb.addEndpoint(id, { anchor:[0.5, 1, 0, 0] }, targetEndpoint);
			jsPlumb.addEndpoint(id, { anchor:[0, 0.5, -1, 0] }, targetEndpoint);
			jsPlumb.addEndpoint(id, { anchor:[1, 0.5, -1, 0] }, targetEndpoint);

			/**
			 Guardando as divs tarefas criadas para trabalhar a hierarquia.
			*/
			allDivTarefas.push(id);

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
            });

            //
            // listen for clicks on connections, and offer to delete connections on click.
            //
            /*
            jsPlumb.bind("click", function(conn, originalEvent) {
                if (confirm("Delete connection from " + conn.sourceId + " to " + conn.targetId + "?"))
                    jsPlumb.detach(conn);
            });

            jsPlumb.bind("connectionDrag", function(connection) {
                console.log("connection " + connection.id + " is being dragged");
            });

            jsPlumb.bind("connectionDragStop", function(connection) {
                console.log("connection " + connection.id + " was dragged");
            });
            */

			//TODO ver pagina 5 do tutorial para conexão depois
	    },

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


        exibirHierarquia : function() {
            if (arvore == null){
                arvore = new Arvore();
            }

            var sbRoot = new StringBuffer();

            //imprime hierarquia em formato de árvore
            var sb = arvore.montarTreeView(arvore.root);

            sbRoot.append(sb.toString());

            //alert('retorno = ' + sb.toString());
            sbRoot.append('<script type="text/javascript">');
            sbRoot.append('$(function () {');
            sbRoot.append('$("#arvoreTarefas")');
            sbRoot.append(' .jstree({ "plugins" : ["themes","html_data","ui"] }) ');
            sbRoot.append('.bind("loaded.jstree", function (event, data) { })    ');
            sbRoot.append('.one("reopen.jstree", function (event, data) { })     ');
            sbRoot.append('.one("reselect.jstree", function (event, data) { }); ');
            sbRoot.append('});');
            sbRoot.append('</script>');

            $('#arvoreTarefas').html('');
            $('#arvoreTarefas').html(sbRoot.toString());
        },

        /***
         * Faz a clonagem da tarefa com todos os endpoints.
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
                jsPlumb.addEndpoint($('#' + idNovaTarefaClone), sourceEndpoint, {anchor:endpoint.anchor});
            });

            //COPIANDO OS ENDPOINTS do tipo TARGET
            jsPlumb.selectEndpoints({target:$('#' + idTarefaModelo)}).each(function(endpoint) {
                //console.log(" anchor.elementId = " +endpoint.anchor.elementId);
                //console.log(" endpoint.uuid = " + endpoint.uuid + " isTarget = " + endpoint.isTarget);//ok
                jsPlumb.addEndpoint($('#' + idNovaTarefaClone), targetEndpoint, {anchor:endpoint.anchor});
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
            if ($("#cbxCloneTarget").is(":checked")) {
                console.log("Clonei uma tarefa de target.");
                //target - adicionando tarefa clonada ao combo de destino
                $('#sltDestinoNovo').addOption(idNovaTarefaClone, idNovaTarefaClone, false);
            } else {
                console.log("Clonei uma tarefa de source.");
                //source - adicionando tarefa clonada ao combo de origem
                $('#sltOrigemNovo').addOption(idNovaTarefaClone, idNovaTarefaClone, false);
            }

            /*
            var connection_ = jsPlumb.getConnections({source:$('#' + idSource), target:$('#' + idTarget)});


            console.log("id = " + connection_.id
                +  " source x = " + connection_.endpoints[0].anchor.x
                +  " source y = " + connection_.endpoints[0].anchor.y
                +  " source dx = " + connection_.endpoints[0].anchor.getOrientation(connection.endpoints[0])[0]
                +  " source dy = " + connection_.endpoints[0].anchor.getOrientation(connection.endpoints[0])[1]
                +  "  "
                +  " target x = " + connection_.endpoints[1].anchor.x
                +  " target y = " + connection_.endpoints[1].anchor.y
                +  " target dx = " + connection_.endpoints[1].anchor.getOrientation(connection.endpoints[1])[0]
                +  " target dy = " + connection_.endpoints[1].anchor.getOrientation(connection.endpoints[1])[1]
            );

            */
            //para conexões ver doc. TODO -> Retrieving Connection Information
            /*
            jsPlumb.select({scope:"foo"}).each(function(connection) {
                // do something
            });

             var c = jsPlumb.getAllConnections();

             var c = jsPlumb.getConnections({source:["mySourceElement", "yourSourceElement"]});

             var c = jsPlumb.getConnections({target:"myTargetElement"});

             //Get all connections for the given source and targets (return value is a map of scope names to connection lists):
             >>>>> var c = jsPlumb.getConnections({source:"mySourceElement", target:["target1", "target2"]});/TODO parece ser bem interessante
            */

            //ver depois Utility Functions TODO
        },


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

            var connection_ = jsPlumb.getConnections({source:$('#' + idSource), target:$('#' + idTarget)});

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

                //primeira conexao do tipo source
                endConnSource = connection_[j].endpoints[0];

                //primeira conexao do tipo target
                endConnTarget = connection_[j].endpoints[1];
            }

            console.log(" ");
            console.log(" ");

            if ($('#sltOrigemNovo').val() == 'SELECIONE' || $('#sltDestinoNovo').val() =='SELECIONE') {
                alert('Escolha novas tarefas a serem conectados')
            }

            idNovaTarefaOrigem  = $('#sltOrigemNovo').val();
            idNovaTarefaDestino = $('#sltDestinoNovo').val();

            /* */
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


            //CONSEGUIIIIIIIIII... agora é comparar os anchors
            /*
            jsPlumb.connect({
                source:idNovaTarefaOrigem,
                target:idNovaTarefaDestino,
                anchors:[[1,0,0,0], [0,0.5,-1,0]]
            });
            */

            alert(' conectando via anchors ....')
            jsPlumb.connect({
                source:idNovaTarefaOrigem,
                target:idNovaTarefaDestino,
                anchors:[endConnSource.anchor, endConnTarget.anchor]
            });
        },
                
  
        /**
         * retornarProximoID - função de teste
	     */
        retornarProximoID : function(proximoID) {
		          //alert(proximoID);
                  if (proximoID == 0){
                    proximoID = 1000
                  }
                  proximoID = proximoID +1;
                  return proximoID;
	    }
	};
})();

//ALESSANDRO:
//Fazendo a inicialização do objeto jsPlumbDemo - consegui retirar o demo-helper-jquery.js e demo-list.js
jsPlumb.bind("ready", function() {
        alert('abrindo jshera2');
	
	//comentando a inicialização de objetos
	//jsPlumbDemo.init();
       
});
