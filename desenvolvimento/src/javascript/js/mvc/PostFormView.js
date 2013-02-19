var PostFormView = Backbone.View.extend({
    tagName: 'form',
    className: 'page-form',
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
        this.model = new PostModel();

        this.model.on("error", this.showError);
        this.model.on("sync", this.goToIndex);
        this.render();
    },

    /**
     * Entrou na hora que o link foi clicado: <a href="testeBack.html" id="add-button">Adicionar Post</a>
     */
    render: function() {
        var rendered = Mustache.to_html(this.template);
        console.log(rendered);

        this.$el.html(rendered);

        this.titleInput = this.$el.find('#post-title');
        this.textInput = this.$el.find('#post-text');

        $('body').append(this.el);
    },

    savePost: function(e) {
        e.preventDefault();

        var title = this.titleInput.val();
        var text = this.textInput.val();

        this.model.set({
            title: title,
            text: text
        });

        if (this.model.isValid()) {
            console.log('entrei save()');
            this.model.save();
        } else{
            console.log('model is NOT Valid()');
        }
    },

    showError:function(model, error) {
        console.log('showError');
        console.log(error.responseText);
        //var responseObj = $.parseJSON(error.responseText);
        //console.log('Type: ' + responseObj.error + ' Message: ' + responseObj.message)
    },

    goToIndex: function() {
        window.location = 'http://localhost:8080/newproject/pages/index2.html';
    }
});