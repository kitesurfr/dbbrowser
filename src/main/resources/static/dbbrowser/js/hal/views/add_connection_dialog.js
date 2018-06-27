HAL.Views.AddConnectionDialog = Backbone.View.extend({
  initialize: function(opts) {
    this.href = opts.href;
    this.vent = opts.vent;
    this.connectionsAllLink = opts.connectionsAllLink;
    this.type = opts.type;
    this.connectionData = opts.connectionData; 
    
    var self = this;
    
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
            method:  this.type == "update" ? "PUT" : "POST",
            data: this.stringify(this) 
          };

      var request = HAL.client.request(opts);
      request.done(function(response) {
    	    	  
      }).fail(function(response) {
        self.vent.trigger('fail-response', { jqxhr: jqxhr });
      }).always(function() {
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
  	      });

      });

 
    
    this.$el.modal('hide');
  },

  
  stringify : function(self) {
	  var obj = new Object();
	  obj.name = self.$('.name').val();
	  obj.username = self.$('.username').val();
	  obj.databaseName = self.$('.databaseName').val();
	  obj.port = self.$('.port').val();
	  obj.password = self.$('.password').val();
	  obj.hostname = self.$('.hostname').val();
	  
	  return JSON.stringify(obj);
  },
  
  render: function(opts) {
 
    this.$el.html(this.template({ href: this.href, connectionData : this.connectionData, type : this.type}));
    this.$el.modal();
    return this;
  },

  template: _.template($('#add-connection-template').html())
});
