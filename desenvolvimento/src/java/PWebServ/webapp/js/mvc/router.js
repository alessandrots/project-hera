/**
 * Created with JetBrains WebStorm.
 * User: alessandrots
 * Date: 13/03/13
 * Time: 19:14
 * To change this template use File | Settings | File Templates.
 */
var AppRouter = Backbone.Router.extend({
    routes: { "": "index",
        "appointments/:id": "show"
    },

    initialize: function(options){
        this.appointmentList = options.appointmentList;
    },

    index: function(){
        var postListView = new PostCollectionView({collection: this.appointmentList});
        postListView.render();
        $('#app').html(postListView.el);
        this.appointmentList.fetch();
    },

    show: function(id){
        var appointment = new Appointment({id: id});
        var appointmentView = new AppointmentView({model: appointment});
        appointmentView.render();
        $('#app').html(appointmentView.el);
        appointment.fetch();
    }
});