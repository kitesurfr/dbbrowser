HAL.Views.Connections = Backbone.View.extend({
  initialize: function(opts) {
    
	this.vent = opts.vent;
	var self = this;
    
    this.vent.bind('response', function(e) {

    	if (e.resource && e.resource._links && e.resource._links.add) {
    		var addUrl = e.resource._links.add.href;
    		
    		if (addUrl.endsWith("connection/add")) {
    			HAL.Views.Connections.addLink = addUrl;
    		} 
    	} 	
    	
    
      if (e.resource && e.resource._links && e.resource._links.self) {
    	  var selfUrl = e.resource._links.self.href;
    	  
    	  if (selfUrl.includes("connection/all")) {
    		  HAL.Views.Connections.connectionsAllLink = selfUrl;
    		  self.render(new HAL.Models.Resource(e.resource));
    	  }  else {
    		  self.$el.html('');
    	  }
      } 	
    	
    });

    this.vent.bind('fail-response', function(e) {
        try {
            resource = JSON.parse(e.jqxhr.responseText);
        } catch(err) {
            resource = null;
        }
        self.vent.trigger('response', { resource: resource, jqxhr: e.jqxhr });
    });
  },

  
  events: {
    'click .follow': 'followLink',
    'click .add': 'showAddConnectionDialog',
    'click .delete': 'showDeleteConnectionDialog',
    'click .update': 'showUpdateConnectionDialog',
  },

  className: 'connections',

  followLink: function(e) {
    e.preventDefault();
    var $target = $(e.currentTarget);
    var uri = $target.attr('href');
    window.location.hash = uri;
  },

  showAddConnectionDialog: function(e) {
    this.renderForm("add", e)
  },
  
  showUpdateConnectionDialog: function(e) {
	  
	  e.preventDefault();
	  	var connectionData = new Object();
	  	var self = this;
    	opts = {
    	            url: $(e.currentTarget).attr('href'),
    	            headers: HAL.parseHeaders("Content-Type: application/json"),
    	            method:  "GET",
    	          };

    	      var request = HAL.client.request(opts);
    	      request.done(function(response) {
    	    	 
    	    	  connectionData.username = response.username;
    	    	  connectionData.name = response.name;
    	    	  connectionData.hostname = response.hostname;
    	    	  connectionData.databaseName = response.databaseName;
    	    	  connectionData.port = response.port;
    	    	 
    	    	  self.renderForm("update", e, connectionData);
    	      }).fail(function(response) {
    	        self.vent.trigger('fail-response', { jqxhr: jqxhr });
    	      }).always(function() {
    	    	 
    	    });
	  
  },

  showDeleteConnectionDialog: function(e) {
	  e.preventDefault();
	    var postForm = HAL.Views.DeleteConnectionDialog;
	    var d = new postForm({
	      connectionsAllLink : HAL.Views.Connections.connectionsAllLink,
	      href: $(e.currentTarget).attr('href'),
	      vent: this.vent,
	    }).render({})
  },

  template: _.template($('#connections-template').html()),

  render: function(resources) {
	if (typeof resources != 'undefined') {
		this.$el.html(this.template({ resources }));
	}  
  },
  
  renderForm: function(type, e, connectionData){
	e.preventDefault();

    var postForm = HAL.Views.AddConnectionDialog;
    var d = new postForm({
      connectionsAllLink : HAL.Views.Connections.connectionsAllLink,
      href: type == "add" ? HAL.Views.Connections.addLink : $(e.currentTarget).attr('href'),
      vent: this.vent,
      type : type,
      connectionData : connectionData
    }).render({})
	  
  }
});
