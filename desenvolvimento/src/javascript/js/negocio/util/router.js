/**
 * Created with JetBrains WebStorm.
 * User: alessandrots
 * Date: 30/03/13
 * Time: 19:14
 * To change this template use File | Settings | File Templates.
 */
var AppRouter = Backbone.Router.extend({
    routes: {
        "": "index",
        "findByName/:id": "findByName",
        "findAll": "findAll"
    },

    /**
     * Inicializa o router.js
     */
    initialize: function(){
        console.log('router => initialize');
        this.tarefaList = new TarefaModel();
        this.tarefaList.url = 'project/cadTarefas/recuperarTodasTarefas';
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
     */
    index: function(){
        console.log('router => index');
        this.tarefaCollectionView = new TarefaCollectionView({collection: this.tarefaList});
        this.tarefaList.fetch();
    },

    /**
     * Faz uma pesquisa filtrando pelo campo específico.
     * Aí cria um tarefaList específico para ele fazer a pesquisa na url em questão e
     * adicionar na url da classe.
     *
     * @param id
     */
    findByName: function(id){
        console.log('router => findByName');
        var tarefaList2 = new TarefaModel();
        tarefaList2.url = 'project/cadTarefas/recuperarListaTeste2';
        this.tarefaCollectionView = new TarefaCollectionView({collection: tarefaList2});
        tarefaList2.fetch({data: {nome: id}});
    },

    findAll: function(){
        console.log('router => findAll');
        var tarefaList2 = new TarefaModel();
        tarefaList2.url = 'project/cadTarefas/recuperarTodasTarefas';
        this.tarefaCollectionView = new TarefaCollectionView({collection: tarefaList2});
        tarefaList2.fetch();
    }

});