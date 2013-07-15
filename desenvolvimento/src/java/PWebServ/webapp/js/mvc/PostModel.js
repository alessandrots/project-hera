/**
 * Created with JetBrains WebStorm.
 * User: alessandrots
 * Date: 17/02/13
 * Time: 22:55
 * To change this template use File | Settings | File Templates.
 */
var urlPrincipal  = 'http://localhost:8080/';
var contextPath   = 'newproject/project/cadTarefas/add';
var url_           = urlPrincipal + contextPath;

var PostModel = Backbone.Model.extend({
    urlRoot: url_,

    defaults : function(){
        return {
            //some
            title: [],
            text: []
        }
    },

    validate: function(attrs) {
        console.log('validate...');

        if (attrs.title == '') {
            console.log('O título é obrigatório');
            return 'O título é obrigatório';
        } else {
            console.log('titulo');
        }

        if (attrs.text == '') {
            console.log('O texto é obrigatório');
            return 'O texto é obrigatório'
        } else {
            console.log('texto');
        }

    }
});