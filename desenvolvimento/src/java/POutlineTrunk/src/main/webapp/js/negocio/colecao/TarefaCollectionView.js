var TarefaCollectionView = Backbone.View.extend({
    //el: ('#liCadastro'),
    tagName: 'form',
    attributes: {
        type: 'PUT'
    },

//    events: {
//        "submit" : "savePost"
//    },


    //FUNCIONOU TROUXE A LISTA
    initialize: function(){
//        _.bindAll(this, 'render', 'savePost', 'goToIndex');

        this.template = $('#post-form').html();
        //console.log( this.template);
        this.collection.on('add', this.addOne, this);
        this.collection.on('reset', this.addAll, this);
        this.collection.on('#remove-button', this.removeBtn, this);
    },

    addOne: function(modelItem){
        var rendered = Mustache.to_html(this.template, modelItem.toJSON());
        console.log(rendered);
        //this.$el.append(rendered);
        this.$el.html(rendered);

        //VER TODO https://www.openshift.com/blogs/building-single-page-web-applications-with-backbonejs-jaxrs-mongodb-and-openshift
        //VER http://fernandomantoan.com/javascript/serie-backbone-js-parte-4-collection/

        //Assim não dá
        $('#liCadastro').empty();
        $('#liCadastro').append(this.el);

    },

    addAll: function(){
        this.collection.forEach(this.addOne, this);
    },

    render: function(){
        this.addAll();
    },


    removeBtn: function() {
        //        var postList = new PostList();
        //        postList.url = '/newproject/project/cadTarefas/recuperarListaTeste2';
        //        var postCollectionView = new PostCollectionView({collection:postList});
        //        console.log('testando o remove 2 NOVO... = ', this.href);

        //myparam= "Alessandro_1";//TODO retirar- Com o parâmetro vindo FUNCIONA!!! Atualizou o HTML
        //console.log('recuperando a variável ' + myparam);
        //tarefaList.fetch({data: {nome: myparam}});
    }




});