var PostCollectionView = Backbone.View.extend({
    //el: $('#somediv'),

    //FUNCIONOU TROUXE A LISTA
    initialize: function(){
        //this.template = _.template($('#post-template').html());
        this.template = $('#post-template').html();
        this.collection.on('add', this.addOne, this);
        this.collection.on('reset', this.addAll, this);
    },

    addOne: function(modelItem){
        var rendered = Mustache.to_html(this.template, modelItem.toJSON());
        console.log(rendered);
        $('#sampleArea').append(rendered);
    },

    addAll: function(){
        this.collection.forEach(this.addOne, this);
    },

    render: function(){
        this.addAll();
    }
});