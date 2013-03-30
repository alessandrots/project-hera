var PostView = Backbone.View.extend({

    events: {
        "click #remove-button": "removePost"
    },

    initialize: function() {
        _.bindAll(this, 'render', 'removePost', 'refresh');

        this.template = _.template($('#post-template').html());


        //this.model = new PostModel();

        this.model.on("change", this.render, this);
        this.model.on("destroy", this.refresh);
    },

    render: function() {
        console.log('PostView - render ');//ok

        var template = this.$el.html(this.template(this.model.toJSON()));
        //this.$el.html(this.template(this.model.toJSON()));

        return this;
    },

    removePost: function() {
        this.model.destroy();
    },

    refresh: function() {
        this.model.clear({silent: true});
        this.model.fetch();
    }
});