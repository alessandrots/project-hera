var PostList = Backbone.Collection.extend({
	url: 'http://localhost:8080/newproject/project/cadTarefas/recuperarListaTeste',
	
	model: PostModel
});