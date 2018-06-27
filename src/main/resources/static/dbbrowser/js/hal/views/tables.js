HAL.Views.Tables = Backbone.View.extend({
  initialize: function(opts) {
	    var self = this;
	    this.vent = opts.vent;
	    this.vent.bind('response', function(e) {
	    	
	      if (e.resource != null &&
	    	  typeof e.resource != undefined && 
	    	  typeof e.resource._links != undefined && 
	    	  typeof e.resource._links.self != undefined)	{

	    	  var selfUrl = e.resource._links.self.href;
	    	  
	    	  if (selfUrl.endsWith("/tables")) {
	    		  self.render(new HAL.Models.Resource(e.resource));
	    	  } else {
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
  },

  className: 'tables',

  followLink: function(e) {
    e.preventDefault();
    var $target = $(e.currentTarget);
    var uri = $target.attr('href');
    window.location.hash = uri;
  },

  template: _.template($('#tables-template').html()),

  render: function(tables) {
    this.$el.html(this.template({ tables: tables }));
  }
});
