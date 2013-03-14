/**
 * Created with JetBrains WebStorm.
 * User: alessandrots
 * Date: 13/03/13
 * Time: 18:37
 * To change this template use File | Settings | File Templates.
 */

var PostCollectionView = Backbone.View.extend({
    initialize: function(){
        this.collection.on('add', this.addOne, this);
        this.collection.on('reset', this.addAll, this);
    },

    addOne: function(modelItem){
        var postView = new PostView({model: modelItem});
        this.$el.append(postView.render().el);
    },

    addAll: function(){
        this.collection.forEach(this.addOne, this);
    },

    render: function(){
        this.addAll();
    }
});