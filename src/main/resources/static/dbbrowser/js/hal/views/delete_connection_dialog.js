HAL.Views.DeleteConnectionDialog = Backbone.View.extend({
  initialize: function(opts) {
    this.href = opts.href;
    this.vent = opts.vent;
    this.connectionsAllLink = opts.connectionsAllLink;
    this.uriTemplate = uritemplate(this.href);
    _.bindAll(this, 'submitQuery');
  },

  events: {
    'submit form': 'submitQuery'
  },

  className: 'modal fade',

  submitQuery: function(e) {
    e.preventDefault();
    var self = this;
    
    opts = {
            url: this.href,
            headers: HAL.parseHeaders("Content-Type: application/json"),
            method:  "DELETE",
          };

      var request = HAL.client.request(opts);
      request.done(function(response) {
    	    	  
      }).fail(function(response) {
        self.vent.trigger('fail-response', { jqxhr: jqxhr });
      }).always(function() {
        //self.vent.trigger('response-headers', { jqxhr: jqxhr });
    	  opts = {
  	            url: self.connectionsAllLink,
  	            method: 'GET',
  	          };

  	      var request = HAL.client.request(opts);
  	      request.done(function(response) {
  	        self.vent.trigger('response', { resource: response, jqxhr: jqxhr });
  	      }) .fail(function(response) {
  	        self.vent.trigger('fail-response', { jqxhr: jqxhr });
  	      }).always(function() {
  	        self.vent.trigger('response-headers', { jqxhr: jqxhr });
  	      });

      });

 
    
    this.$el.modal('hide');
  },
  
  render: function(opts) {
    this.$el.html(this.template({ href: this.href}));
    this.$el.modal();
    return this;
  },

  template: _.template($('#delete-connection-template').html())
});
