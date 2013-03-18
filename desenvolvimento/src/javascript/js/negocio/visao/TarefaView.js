/**
 * Created with JetBrains WebStorm.
 * User: alessandrots
 * Date: 17/03/13
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
var TarefaView = Backbone.View.extend({

    id: 'post-form',

    attributes: {
        action: 'posts',
        method: 'POST'
    },

    events: {
        "submit" : "savePost"
    },

    initialize: function(model) {
        _.bindAll(this, 'render', 'savePost', 'goToIndex');

        this.template = $('#post-form').html();
        this.model = new TarefaModel();

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
        console.log(rendered);

        this.$el.html(rendered);

        this.nomeInput          = this.$el.find('#cad_nome');
        this.dataInicioInput    = this.$el.find('#cad_data_inicio');
        this.duracaoInput       = this.$el.find('#cad_duracao');
        this.dataEntregaInput   = this.$el.find('#cad_data_entrega');
        this.dataTerminoInput   = this.$el.find('#cad_data_termino');

        console.log('this.el = ' + this.el);

        $('#liCadastro').append(this.el);
    },

    savePost: function(e) {
        e.preventDefault();//isto é importante

        var nomeInput          = this.nomeInput.val();
        var dataInicioInput    = this.dataInicioInput.val();
        var duracaoInput       = this.duracaoInput.val();
        var dataEntregaInput   = this.dataEntregaInput.val();
        var dataTerminoInput   = this.dataTerminoInput.val();

        this.model.set({
            nomeInput: nomeInput,
            dataInicioInput: dataInicioInput,
            duracaoInput: duracaoInput,
            dataEntregaInput: dataEntregaInput,
            dataTerminoInput: dataTerminoInput
        });

        if (this.model.isValid()) {
            console.log('savePost --> ' +
                ' nomeInput        = '  + this.model.get('nomeInput') +
                ' dataInicioInput  = '  + this.model.get('dataInicioInput') +
                ' duracaoInput     = '  + this.model.get('duracaoInput') +
                ' dataEntregaInput = '  + this.model.get('dataEntregaInput') +
                ' dataTerminoInput = '  + this.model.get('dataTerminoInput')
            );

            this.model.save();
        }
    },

    showError:function(model, error) {
        console.log('showError');
        console.log(error.responseText);
    },

    goToIndex: function() {
        //TODO tem que enviar para o index.html novamente
        //console.log('chamaria o model novamente via PostView.js');
        console.log('chamaria o model novamente via PostCollection.js');
        //window.location = 'http://localhost:8080/newproject/pages/testeBackColl.html';
    }
})