/**
 * Created with JetBrains WebStorm.
 * User: alessandrots
 * Date: 17/03/13
 * Time: 17:23
 * To change this template use File | Settings | File Templates.
 */
//var serverName  = 'http://localhost:8080/';
//var context_path   = 'newproject/project/cadTarefas/add';
var url_ = 'project/cadTarefas/add';

var TarefaModel = Backbone.Model.extend({
    urlRoot: url_,

    defaults : function(){
        return {
            codigo: '0',
            nome: '',
            dataInicio: '',
            dataEntrega: '',
            dataTermino: '',
            duracao: '0',
            idWinTarefa:''

        }
    },


    showErrors: function(errors) {
        console.log('showErrors');
        /*
        _.each(errors, function (error) {
            console.log('showErrors');
            //var controlGroup = this.$('.' + error.name);
            //controlGroup.addClass('error');
            //controlGroup.find('.help-inline').text(error.message);
        }, this);
        */
    },



    validate: function(attrs) {
        console.log('validate...');
        var errors =[];

        if (attrs.nome == '') {
            console.log('O nome é obrigatório.');
            errors.push({name: 'nome', message: 'Please fill nome field.'});
        }

        if (attrs.dataInicio == '') {
            console.log('A data de início é obrigatória.');
            errors.push({name: 'data de início', message: 'Please fill data de início field.'});
        }

        if (attrs.duracao == '') {
            console.log('A duração é obrigatória.');
            errors.push({name: 'duração', message: 'Please fill duração field.'});
        }

//        if (attrs.idWinTarefa == '') {
//            console.log('A definição da tarefa é obrigatória.');
//            errors.push({name: 'definição da tarefa', message: 'Please fill definição da tarefa field.'});
//            //return 'A duração é obrigatória.';
//        }

        return errors.length > 0 ? errors : false;

    }
})