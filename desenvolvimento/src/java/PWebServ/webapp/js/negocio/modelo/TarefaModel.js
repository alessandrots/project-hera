/**
 * Created with JetBrains WebStorm.
 * User: alessandrots
 * Date: 17/03/13
 * Time: 17:23
 * To change this template use File | Settings | File Templates.
 */
var serverName  = 'http://localhost:8080/';
var context_path   = 'newproject/project/cadTarefas/add';
var url_           = serverName + context_path;

var TarefaModel = Backbone.Model.extend({
    urlRoot: url_,

    defaults : function(){
        return {
            codigo: '',
            nome: '',
            dataInicio: '',
            dataEntrega: '',
            dataTermino: '',
            duracao: '',
            idWinTarefa:''

        }
    },

    validate: function(attrs) {
        console.log('validate...');

        if (attrs.nome == '') {
            console.log('O nome é obrigatório');
            return 'O nome é obrigatório';
        }

    }
})