var AppRouter = Backbone.Router.extend({
    routes: {
        "": "index",
        "findByName/:id": "findByName"
    },

    /**
     * Inicializa o router.js
     */
    initialize: function(){
        console.log('router => initialize');
        this.postList = new PostList();//options.postList;
        this.postList.url = '/newproject/project/cadTarefas/recuperarListaTeste2';
    },

    /**
     * starta o history
     */
    start: function(){
        console.log('router => start');
        Backbone.history.start();
    },

    /**
     * Carrega toda a lista do servidor e apresenta no body do browser
     * Na primeira vez que entrar não vai trazer nada.
     */
    index: function(){
        console.log('router => index');
        this.postCollectionView = new PostCollectionView({collection: this.postList});
        this.postList.fetch();
    },

    /**
     * Faz uma pesquisa filtrando pelo campo específico.
     * Aí cria um postList específico para ele fazer a pesquisa na url em questão e
     * adicionar na url da classe.
     *
     * @param id
     */
    findByName: function(id){
        console.log('router => findByName');
        var postList2 = new PostList();
        postList2.url = '/newproject/project/cadTarefas/recuperarListaTeste2';
        this.postCollectionView = new PostCollectionView({collection: postList2});
        postList2.fetch({data: {nome: id}});
    }

    /*
     loadRestfulData:function (pageUrl) {
     //Set the content pane to a loading screen
     $('#content-pane').text('loading data...');
     //Load the data in using jQuerys ajax call
     $.ajax({
     url:pageUrl,
     dataType:'jsonp',
     success:function (data) {
     //Once we receive the data, set it to the content pane.
     $('#content-pane').text(data);
     }
     });
     }
     */
});