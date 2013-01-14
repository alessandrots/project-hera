;(function() {
	var allDivTarefas = [], e1, e2;
        //var allSourceEndpoints = [], allTargetEndpoints = [];

	window.jsPlumbDemo = {
		init : function() {
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

			// this is the paint style for the connecting lines..
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
			
			// the definition of source endpoints (the small blue ones)
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
						label:"Drag",
						cssClass:"endpointSourceLabel" 
					} ]
			]
				},
			// a source endpoint that sits at BottomCenter
			//	bottomSource = jsPlumb.extend( { anchor:"BottomCenter" }, sourceEndpoint),
			// the definition of target endpoints (will appear when the user drags a connection) 
			targetEndpoint = {
					endpoint:"Dot",					
					paintStyle:{ fillStyle:"#558822",radius:11 },
					hoverPaintStyle:connectorHoverStyle,
					maxConnections:-1,
					dropOptions:{ hoverClass:"hover", activeClass:"active" },
					isTarget:true,			
					overlays:[
					    [ "Label", { location:[0.5, -0.5], label:"Drop", cssClass:"endpointTargetLabel" } ]
					]
			},
  
			//Aqui tá colocando o label do source com o target (source-target)
			init = function(connection) {
					connection.getOverlay("label").setLabel(connection.sourceId.substring(6) + "-" + connection.targetId.substring(6));
			};			
			
			//Função para fazer a conexão das setas é só chamar a função addEndpoint
			var allSourceEndpoints = [], allTargetEndpoints = [];
			
			/**
			  Este método faz somente a adição dos EndPoints
			*/
			_addEndpoints = function(toId, sourceAnchors, targetAnchors) {
			for (var i = 0; i < sourceAnchors.length; i++) {
				var sourceUUID = toId + sourceAnchors[i];
				//alert (sourceUUID);
				allSourceEndpoints.push(jsPlumb.addEndpoint(toId, sourceEndpoint, 
										{ anchor:sourceAnchors[i],uuid:sourceUUID }));						
			}
			
			for (var j = 0; j < targetAnchors.length; j++) {
				var targetUUID = toId + targetAnchors[j];
				allTargetEndpoints.push(jsPlumb.addEndpoint(toId, targetEndpoint, 
										{ anchor:targetAnchors[j], uuid:targetUUID }));						
			}
			};
			
			/* ALESSANDRO:
			   importante para fazer as conexões - não pode descomentar 
			   na função addEndpoint, o terceiro parâmetro a ser montado vai ser a concatenação:
			    - no loop do source:id + sourceAnchors. Ex.:window4TopCenter			    
			    isto vai fazer o endpoint(Dot) aparecer no bloco naquela posição específica
			    
			    - O segundo parámetro são os Endpoints de origem, de onde a seta vai sair, no caso
			    abaixo, vai ter um Endpoint no centro superior e outro Endpoint no centro inferior..
			    
			    - O terceiro parámetro são os Endpoints de destino, aonde a seta vai entrar, no caso
			    abaixo, vai ter um Endpoint no meio esquerdo e outro Endpoint no meio direito.
			*/			
			_addEndpoints("window4", ["TopCenter", "BottomCenter"], ["LeftMiddle", "RightMiddle"]);			
			_addEndpoints("window2", ["LeftMiddle", "BottomCenter"], ["TopCenter", "RightMiddle"]);
			_addEndpoints("window3", ["RightMiddle", "BottomCenter"], ["LeftMiddle", "TopCenter"]);
			_addEndpoints("window1", ["LeftMiddle", "RightMiddle"], ["TopCenter", "BottomCenter"]);
			/**/
			
			// listen for new connections; initialise them the same way we initialise the connections at startup.
			jsPlumb.bind("jsPlumbConnection", function(connInfo, originalEvent) {
				alert('conecteiiii'); 
				init(connInfo.connection);
			});			
						
			// make all the window divs draggable						
			//jsPlumb.draggable(jsPlumb.getSelector(".window"), { grid: [20, 20] });
			// THIS DEMO ONLY USES getSelector FOR CONVENIENCE. Use your library's appropriate selector method!
			jsPlumb.draggable(jsPlumb.getSelector(".window"));

			/**
			 * ALESSANDRO:
			  Aqui faz a conexão dos componentes, por isso que no método _addEndpoints ele faz a concatenação dos
			  ids (ex.:window2) com os localizadores (ex.:BottomCenter)
			  
			  PARA A SOLUÇÃO QUE EU VOU FAZER:
			   - gerar a id dinamicamente (da forma como já fiz no outro js)
			   - 
			*/
			jsPlumb.connect({uuids:["window2BottomCenter", "window3TopCenter"]});
			jsPlumb.connect({uuids:["window2LeftMiddle", "window4LeftMiddle"]});
			jsPlumb.connect({uuids:["window4TopCenter", "window4RightMiddle"]});
			jsPlumb.connect({uuids:["window3RightMiddle", "window2RightMiddle"]});
			jsPlumb.connect({uuids:["window4BottomCenter", "window1TopCenter"]});
			jsPlumb.connect({uuids:["window3BottomCenter", "window1BottomCenter"]});
			
			//Adicionando outros EndPoints para teste //TODO
			/**
			  ALESSANDRO:
			  Descobri que um ponto target pode receber vários setas,só 
			  que todas as setas ficam em cima da mesma, mas a medida que
			  vc for clicando nas setas, elas permite a deleção de uma por uma, aí
			  isto porque na definição do targetEndpoint ele colocou maxConnections = -1
			  isto permite várias conexões no mesmo endpoint
			*/
			jsPlumb.addEndpoint("window3", { anchor:[0, 0, 0, 0] }, sourceEndpoint)
			jsPlumb.addEndpoint("window3", { anchor:[0.2, 0, 0, 0] }, sourceEndpoint)
			jsPlumb.addEndpoint("window3", { anchor:[0.8, 0, 0, 0] }, targetEndpoint)
			jsPlumb.addEndpoint("window3", { anchor:[1, 0, 0, 0] }, targetEndpoint)
			
					
			//
			// listen for clicks on connections, and offer to delete connections on click.
			//
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
		},



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
			
			// this is the paint style for the connecting lines..
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
			
			// the definition of source endpoints (the small blue ones)
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
				},
			// a source endpoint that sits at BottomCenter
			//	bottomSource = jsPlumb.extend( { anchor:"BottomCenter" }, sourceEndpoint),
			// the definition of target endpoints (will appear when the user drags a connection) 
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

			//Aqui tá colocando o label do source com o target (source-target)
			init = function(connection) {
				connection.getOverlay("label").setLabel(connection.sourceId.substring(6) + "-" + connection.targetId.substring(6));
			};

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
				alert('DESCOBRIIIIII... '); 
				init(connInfo.connection);
			});

			//TODO ver pagina 5 do tutorial para conexão depois
	        },
              
                conectarComponentes: function() {
		   //jsPlumb.connect({uuids:["window_1001", "window_1002"]});
                   //jsPlumb.connect({source:e1, target:e2});//TODO
		},
  
               /**
	          retornarProximoID - função de teste
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
