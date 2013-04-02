var TarefaList = Backbone.Collection.extend({
    url: 'project/cadTarefas/recuperarListaTeste',
	model: TarefaModel
});