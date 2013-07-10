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
        "findByWinTarefa/:id" : "findByWinTarefa",
        "findByWinTarefa2/:id" : "findByWinTarefa2",
        "findAll": "findAll"
    },

    /**
     * Inicializa o router.js
     */
    initialize: function(){
        console.log('router => initialize');
        this.tarefaList = new TarefaList();
        this.tarefaView = new TarefaView();
        this.tarefaList.url = 'project/cadTarefas/recuperarTodos';
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
        console.log('router => index - url = ', this.tarefaList.url);
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
        var tarefaList2 = new TarefaList();
        tarefaList2.url = 'project/cadTarefas/recuperarTarefaPorNome';
        this.tarefaCollectionView = new TarefaCollectionView({collection: tarefaList2});
        tarefaList2.fetch({data: {nome: id}});
    },

    findByWinTarefa: function(id){
        console.log('router => findByWinTarefa');
        this.tarefaList = new TarefaList();
        this.tarefaList.url = 'project/cadTarefas/recuperarTarefaPorWinTarefa';
        this.tarefaCollectionView = new TarefaCollectionView({collection: this.tarefaList});
        console.log('router => findByWinTarefa => mudando o el.', id);
        this.tarefaList.fetch({data: {idWinTarefa: id}});
    },

    findByWinTarefa2: function(id){
        console.log('router => findByWinTarefa2');
        myModel = new TarefaModel();
        myModel.urlRoot = 'project/cadTarefas/recuperarTarefaPorWinTarefa';

        //guardando a instância já criada...
        var _thisView = this.tarefaView;

        //Aqui busca os dados filtrado pelo param idWinTarefa com o valor window_1002,
        //é retornado o formato JSON e chamo o stringify para ver os dados num formato legível
        //por fim faço um parse do JSON para poder recuperar os valores nos atributos
        myModel.fetch({data: {idWinTarefa: id}}).done(function () {
            var contact = JSON.parse(JSON.stringify(myModel.toJSON(), '', '  '));

            //http://stackoverflow.com/questions/10566178/backbone-fetch-the-data-from-a-json-file-which-is-in-the-client-part
            console.log('findByWinTarefa2 => atualizando a view... ');
            _thisView.addOne(myModel);
        });
    },

    findAll: function(){
        console.log('router => findAll');
        var tarefaList2 = new TarefaList();
        tarefaList2.url = 'project/cadTarefas/recuperarTodasTarefas';
        this.tarefaCollectionView = new TarefaCollectionView({collection: tarefaList2});
        tarefaList2.fetch();
    }

});