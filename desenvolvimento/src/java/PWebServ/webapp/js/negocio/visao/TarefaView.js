/**
 * Created with JetBrains WebStorm.
 * User: alessandrots
 * Date: 17/03/13
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
var TarefaView = Backbone.View.extend({


    tagName: 'form',
    /*
    className: 'page-form',

    id: 'post-form',

    attributes: {
        action: 'posts',
        method: 'PUT'
    },
   */

    events: {
        "submit" : "savePost"
    },

    initialize: function(model) {
        _.bindAll(this, 'render', 'savePost', 'goToIndex');

        this.template = $('#post-form').html();

        //http://net.tutsplus.com/tutorials/javascript-ajax/build-a-contacts-manager-using-backbone-js-part-5/
        /*

         If you're working with a legacy web server that can't handle requests encoded as application/json, setting Backbone.emulateJSON = true;
         will cause the JSON to be serialized under a model parameter, and the request to be made with a application/x-www-form-urlencoded mime type,
         as if from an HTML form.

         Aí printou
         */
        //Backbone.emulateHTTP = true;
        Backbone.emulateJSON = true;

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
        this.dataEntregaInput   = this.$el.find('#cad_data_entrega');
        this.dataTerminoInput   = this.$el.find('#cad_data_termino');
        this.idWindowTarefa     = this.$el.find('#id_window_tarefa');
        this.duracaoInput       = this.$el.find('#cad_duracao');

        console.log('this.el = ' + this.el);

        $('#liCadastro').append(this.el);
    },

    savePost: function(e) {
        e.preventDefault();//isto é importante

        var nomeInput          = this.nomeInput.val();
        var dataInicioInput    = this.dataInicioInput.val();
        var dataEntregaInput   = this.dataEntregaInput.val();
        var dataTerminoInput   = this.dataTerminoInput.val();
        var duracaoInput       = this.duracaoInput.val();
        var idWindowTarefa     = this.idWindowTarefa.val();

        var details= {
            nome: nomeInput,
            dataInicio: dataInicioInput,
            duracao: duracaoInput,
            dataEntrega: dataEntregaInput,
            dataTermino: dataTerminoInput,
            idWinTarefa: idWindowTarefa
        };

        this.model.set(details);

        console.log('savePost --> ' +
            ' nomeInput        = '  + this.model.get('nome') +
            ' dataInicioInput  = '  + this.model.get('dataInicio') +
            ' dataEntregaInput = '  + this.model.get('dataEntrega') +
            ' dataTerminoInput = '  + this.model.get('dataTermino') +
            ' duracaoInput     = '  + this.model.get('duracao') +
            ' idWinTarefa      = '  + this.model.get('idWinTarefa')
        );

        var that = this.model;

        if (this.model.isValid()) {
            //params.contentType = 'application/json';
            //params.data = JSON.stringify(model.toJSON());
            //console.log('data = ' + params.data);

            //http://www.jamesyu.org/2011/01/27/cloudedit-a-backbone-js-tutorial-by-example/
            //http://localhost:8080/newproject/
            //http://www.json.org/js.html
            //http://backbonejs.org/#Model-save
            //http://backbonejs.org/#Model-url
            //http://documentcloud.github.com/backbone/docs/backbone.html

            //this.model.save();

            this.model.save(details, {
                success: function (that) {
                    console.log('sucesso... this.model = ' + that.toJSON());
                },

                error: function (that) {
                    console.log('erro!');
                }

            });
        }
    },

    showError:function(model, error) {
        console.log('showError');
        console.log(error.responseText);
    },

    goToIndex: function() {
        //TODO tem que enviar para o index.html novamente
        //console.log('chamaria o model novamente via PostView.js');
        console.log('decide o que deve ser feito aqui!!!!');
        //window.location = 'http://localhost:8080/newproject/pages/testeBackColl.html';
    }
})