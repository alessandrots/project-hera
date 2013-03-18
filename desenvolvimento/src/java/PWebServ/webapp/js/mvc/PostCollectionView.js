var PostCollectionView = Backbone.View.extend({
    initialize: function(){
        this.collection.on('add', this.addOne, this);
        this.collection.on('reset', this.addAll, this);
    },

    addOne: function(modelItem){
        var postView = new PostView({model: modelItem});

        //var meuhtml = postView.render().$el.html();

        console.log('my html come from view = ' + postView.render().$el.html());

        this.$el.append(postView.render().$el.html());

        //return this;
    },

    addAll: function(){
        this.collection.forEach(this.addOne, this);
    },

    render: function(){
        this.addAll();
    }
});