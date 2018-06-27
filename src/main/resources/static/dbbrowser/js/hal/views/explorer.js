HAL.Views.Explorer = Backbone.View.extend({
  initialize: function(opts) {
    var self = this;
    this.vent = opts.vent;
    this.connectionsView = new HAL.Views.Connections({ vent: this.vent });
    this.tablesView = new HAL.Views.Tables({ vent: this.vent });
    this.columnsView = new HAL.Views.Columns({ vent: this.vent });
    this.dataView = new HAL.Views.Data({ vent: this.vent });
  },

  className: 'explorer',

  render: function() {

    this.$el.html(this.template());

    this.$el.append(this.connectionsView.el);
    this.$el.append(this.tablesView.el);
    this.$el.append(this.columnsView.el);
    this.$el.append(this.dataView.el);
  },

  template: function() {
    return '';
  }
});
