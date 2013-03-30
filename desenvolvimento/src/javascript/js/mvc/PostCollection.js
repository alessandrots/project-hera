var PostList = Backbone.Collection.extend({
    url: '/newproject/project/cadTarefas/recuperarListaTeste',

    /*
    url : function(queryPath, idSearch) {
        var urlRetorno = this.urlRoot + '/project/cadTarefas/';

        if (queryPath != null && queryPath != undefined) {
            urlRetorno = urlRetorno + queryPath;
        } else {
            urlRetorno = this.urlRoot + this.urlBase;
        }

        if (idSearch != null && idSearch != undefined) {
            urlRetorno = urlRetorno + "/" +  idSearch;
        }

        console.log(urlRetorno);

        return urlRetorno;
    },
   */


	
	model: PostModel
});