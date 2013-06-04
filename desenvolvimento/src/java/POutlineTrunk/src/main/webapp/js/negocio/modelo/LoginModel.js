/**
 * Created with JetBrains WebStorm.
 * User: alessandrots
 * Date: 03/06/13
 * Time: 21:29
 * To change this template use File | Settings | File Templates.
 */
var url_ = 'j_spring_security_check';

var LoginModel = Backbone.Model.extend({
    urlRoot: url_,

    defaults : function(){
        return {
            codigo: '0',
            nome: '',
            j_username: '',
            j_password: ''
        }
    },

    showErrors: function(errors) {
        console.log('showErrors');
    },

    validate: function(attrs) {
        console.log('validate...');
        var errors =[];

        if (attrs.j_username == '') {
            console.log('O login do usuário é obrigatório.');
            errors.push({name: 'username', message: 'Preencha o login para autenticação.'});
        }

        if (attrs.j_password == '') {
            console.log('A senha do usuário é obrigatória.');
            errors.push({name: 'password', message: 'Preencha a senha para autenticação.'});
        }

        return errors.length > 0 ? errors : false;

    }
})
