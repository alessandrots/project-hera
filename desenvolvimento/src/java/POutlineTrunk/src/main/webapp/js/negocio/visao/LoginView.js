/**
 * Created with JetBrains WebStorm.
 * User: alessandrots
 * Date: 03/06/13
 * Time: 21:21
 * To change this template use File | Settings | File Templates.
 */
var LoginView = Backbone.View.extend({

    tagName: 'form',

    events: {
        "submit" : "savePost"
    },

    initialize: function(model) {
        _.bindAll(this, 'render', 'savePost', 'goToIndex');

        this.template = $('#login-post-form').html();

        //http://net.tutsplus.com/tutorials/javascript-ajax/build-a-contacts-manager-using-backbone-js-part-5/
        /*
         If you're working with a legacy web server that can't handle requests encoded as application/json, setting Backbone.emulateJSON = true;
         will cause the JSON to be serialized under a model parameter, and the request to be made with a application/x-www-form-urlencoded mime type,
         as if from an HTML form.

         Aí printou
         */
        Backbone.emulateHTTP = true;
        Backbone.emulateJSON = true;
        this.model = new LoginModel();

        this.model.on("error", this.showError);
        this.model.on("sync", this.goToIndex);
        this.render();
    },

    /**
     * Entrou na hora que o link foi clicado: <a href="testeBack.html" id="add-button">Adicionar Post</a>
     * Funcionou com Mustache.js.
     */
    render: function() {
        var rendered = Mustache.to_html(this.template);
        //console.log(rendered);

        this.$el.html(rendered);

        this.j_username    = this.$el.find('#j_username');
        this.j_password    = this.$el.find('#j_password');

        console.log('this.el = ' + this.el);

        $('#liCadastroLogin').append(this.el);
    },

    savePost: function(e) {
        e.preventDefault();//isto é importante

        var username_    = this.j_username.val();
        var password_    = this.j_password.val();

        var details= {
            j_username: username_,
            j_password: password_
        };

        //setando os atributos no model específico.
        this.model.set(details);

        console.log('savePost --> ' +
            ' username  = '  + this.model.get('j_username') +
            ' password  = '  + this.model.get('j_password')
        );

        var that = this.model;

        this.model.save();
    },

    showError:function(model, error) {
        console.log('showError');
        console.log(error.responseText);
    },

    goToIndex: function() {
        console.log('decide o que deve ser feito aqui!!!!');
    }
})
