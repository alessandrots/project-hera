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
        this.contaJanela = 0;
        this.nomeTarefa  = '';

        this.model = new TarefaModel();

        this.model.on("error", this.showError);
        //this.model.on("error", this.model.showErrors);
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

        this.nomeInput          = this.$el.find('#cad_nome');
        this.dataInicioInput    = this.$el.find('#cad_data_inicio');
        this.dataEntregaInput   = this.$el.find('#cad_data_entrega');
        this.dataTerminoInput   = this.$el.find('#cad_data_termino');
        //this.idWindowTarefa     = this.$el.find('#id_window_tarefa');
        this.duracaoInput       = this.$el.find('#cad_duracao');

        console.log('this.el = ' + this.el);

        $('#liCadastro').append(this.el);
    },

    savePost: function(e) {
        e.preventDefault();//isto é importante

        //Definindo o nome da Janela
        this.contaJanela = retornarProximoID(this.contaJanela);
        this.nomeTarefa  = 'window_' + this.contaJanela;

        var nomeInput          = this.nomeInput.val();
        var dataInicioInput    = this.dataInicioInput.val();
        var dataEntregaInput   = this.dataEntregaInput.val();
        var dataTerminoInput   = this.dataTerminoInput.val();
        var duracaoInput       = this.duracaoInput.val();
        var idWindowTarefa     = this.nomeTarefa;//this.idWindowTarefa.val();

        var details= {
            nome: nomeInput,
            dataInicio: dataInicioInput,
            duracao: duracaoInput,
            dataEntrega: dataEntregaInput,
            dataTermino: dataTerminoInput,
            idWinTarefa: idWindowTarefa
        };

        //setando os atributos no model específico.
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



        //http://www.jamesyu.org/2011/01/27/cloudedit-a-backbone-js-tutorial-by-example/
        //http://localhost:8080/newproject/
        //http://www.json.org/js.html
        //http://backbonejs.org/#Model-save
        //http://backbonejs.org/#Model-url
        //http://documentcloud.github.com/backbone/docs/backbone.html

        this.model.save();
        /*
        this.model.save(details, {
            success: function () {
                console.log('sucesso... this.model = ');
            },

            error: function (model, errors) {
                console.log('erro = ');
                that.showErrors(errors);
            }

        });
        */
    },

    showError:function(model, error) {
        console.log('showError');add
        console.log(error.responseText);
    },

    goToIndex: function() {
        //TODO tem que enviar para o index.html novamente
        //console.log('chamaria o model novamente via PostView.js');
        console.log('decide o que deve ser feito aqui!!!!');
        this.criarJanelaPlumb();
    },

    criarJanelaPlumb: function() {
        //this.contaJanela = retornarProximoID(this.contaJanela);

        //var nomeTarefa = 'window_' + this.contaJanela;

        //alert(nomeTarefa);
        $('<div class="window" id="' + this.nomeTarefa + '"><strong>' + this.contaJanela + '</strong></div>').prependTo('#render');

        var tarefaCurrent = this.nomeTarefa;

        //Clique na tarefa para adicionar o nome no input
        $('#' + this.nomeTarefa).click(function(){
            //$('#id_window_tarefa').val(this.nomeTarefa);
            console.log('tarefa criada e clicada = ', tarefaCurrent);

            myModel = new TarefaModel();
            myModel.urlRoot = 'project/cadTarefas/recuperarTarefaPorWinTarefa';

            //Aqui busca os dados filtrado pelo param idWinTarefa com o valor window_1002,
            //é retornado o formato JSON e chamo o stringify para ver os dados num formato legível
            //por fim faço um parse do JSON para poder recuperar os valores nos atributos
            myModel.fetch({data: {idWinTarefa: tarefaCurrent}}).done(function () {
                //console.log('myModel = ', myModel.toJSON());
                console.log(JSON.stringify(myModel.toJSON(), '', '  '));
                var contact = JSON.parse(JSON.stringify(myModel.toJSON(), '', '  '));

                if (contact != undefined && contact[0] != undefined) {
                    console.log('codigo = ',        contact[0].codigo);
                    console.log('nome = ',          contact[0].nome);
                    console.log('duracao = ',       contact[0].duracao);
                    console.log('dataInicio = ',    contact[0].dataInicio);
                    console.log('dataEntrega = ',   contact[0].dataEntrega);
                    console.log('dataTermino = ',   contact[0].dataTermino);
                    console.log('idWinTarefa = ',   contact[0].idWinTarefa);
                } else {
                    console.log('Nenhum resultado foi encontrado com a pesquisa feita.');
                }
            });

        });

        //Criando a tarefa
        jsPlumbDemo.criarTarefa(this.nomeTarefa);
    }
})