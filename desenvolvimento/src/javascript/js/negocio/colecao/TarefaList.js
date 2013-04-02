var TarefaList = Backbone.Collection.extend({
    url: '/newproject/project/cadTarefas/recuperarListaTeste',
	
	model: TarefaModel
});