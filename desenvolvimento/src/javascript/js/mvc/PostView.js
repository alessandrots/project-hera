var PostView = Backbone.View.extend({
    template: _.template('<h3> <%=title%> - <%=text%> </h3>'),
    tagName: 'article',
    className: 'page-posts',
    events: {
        "click #remove-button": "removePost"
    },

    initialize: function() {
        _.bindAll(this, 'render', 'removePost', 'refresh');
        console.log('initialize PostView');

        this.template = $('#post-template').html();
        console.log(this.template);

        this.model = new PostModel();

        //this.model.on("change", this.render, this);
        //this.model.on("destroy", this.refresh);
        this.render();
        //this.model.fetch();
    },

    /**/
    /*
    render: function() {
        console.log("Rendering... json = " + this.model.toJSON());
        var rendered = Mustache.to_html(this.template, this.model.toJSON());
        //this.$el.html(this.template(this.model.toJSON()));
        this.$el.html(rendered);
        $('body').append(this.$el);
    },
    */

    render: function() {
        console.log("Rendering... json = " + this.model.toJSON());
        //$('body').append(this.$el);

        var template = $(this.el).html(_.template(this.template, this.model.toJSON()));
        $(this.parent).append(template);
    },

    /*
    render: function(){
        var attributes = this.model.toJSON();
        this.$el.html( this.template(attributes) );
    },
*/

    removePost: function() {
        this.model.destroy();
    },

    refresh: function() {
        this.model.clear({silent: true});
        this.model.fetch();
    }
});