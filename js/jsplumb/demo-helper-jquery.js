/*
 *  This file contains the JS that handles the first init of each jQuery demonstration, and also switching
 *  between render modes.
 
 ALESSANDRO:
 Entender o que � este render modes
 */
jsPlumb.bind("ready", function() {

	//jsPlumb.DemoList.init(); //N�o vi diferen�a

	// chrome fix.
	document.onselectstart = function () { return false; };				

    // render mode
	var resetRenderMode = function(desiredMode) {
		var newMode = jsPlumb.setRenderMode(desiredMode);
		$(".rmode").removeClass("selected");
		$(".rmode[mode='" + newMode + "']").addClass("selected");		

		$(".rmode[mode='canvas']").attr("disabled", !jsPlumb.isCanvasAvailable());
		$(".rmode[mode='svg']").attr("disabled", !jsPlumb.isSVGAvailable());
		$(".rmode[mode='vml']").attr("disabled", !jsPlumb.isVMLAvailable());

		//ALESSANDRO:
		//dentro de cada arquivo *.js existe este objeto (jsPlumbDemo) que inicializa	
		//jsPlumbDemo � um objeto e o init() � o m�todo dentro dele
		jsPlumbDemo.init();
	};
     
	$(".rmode").bind("click", function() {
		var desiredMode = $(this).attr("mode");
		if (jsPlumbDemo.reset) jsPlumbDemo.reset();
		jsPlumb.reset();
		resetRenderMode(desiredMode);					
	});	

	resetRenderMode(jsPlumb.SVG);
       
});