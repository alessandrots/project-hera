var PostCollectionView = Backbone.View.extend({
    el: ('body'),

    /*
    //associando o evento ao botão com id #remove-button:
     <a href="#findByName/{{title}}" id="remove-button">Remover este Post</a>
    events: {
        "click #remove-button" : "removeBtn"
    },
    */

    //FUNCIONOU TROUXE A LISTA
    initialize: function(){
        //this.template = _.template($('#post-template').html());
        this.template = $('#post-template').html();
        this.collection.on('add', this.addOne, this);
        this.collection.on('reset', this.addAll, this);
        this.collection.on('#remove-button', this.removeBtn, this);
    },

    addOne: function(modelItem){
        console.log('addOne = ', modelItem.get("title"));
        var rendered = Mustache.to_html(this.template, modelItem.toJSON());
        //console.log(rendered);
        //$('#sampleArea').append(rendered);
        this.$el.append(rendered);
    },

    addAll: function(){
        this.collection.forEach(this.addOne, this);
    },

    render: function(){
        console.log('render');
        this.addAll();
    },


    removeBtn: function() {
        var postList = new PostList();
        postList.url = '/newproject/project/cadTarefas/recuperarListaTeste2';

        var postCollectionView = new PostCollectionView({collection:postList});

        console.log('testando o remove 2 NOVO... = ', this.href);
    }

});