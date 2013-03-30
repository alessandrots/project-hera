/**
 * Created with JetBrains WebStorm.
 * User: alessandrots
 * Date: 13/03/13
 * Time: 19:14
 * To change this template use File | Settings | File Templates.
 */
var AppRouter = Backbone.Router.extend({
    routes: {
        "": "index",
        "findByName/:id": "findByName"
    },

    initialize: function(){
        console.log('router => initialize');
        this.postList = new PostList();//options.postList;
        this.postList.url = '/newproject/project/cadTarefas/recuperarListaTeste2';
    },

    start: function(){
        console.log('router => start');
        Backbone.history.start();
    },

    index: function(){
        console.log('router => index');
        this.postCollectionView = new PostCollectionView({collection: this.postList});
        //this.postCollectionView.render();
        this.postList.fetch();
    },

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