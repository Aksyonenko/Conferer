var conferer = conferer || {};
conferer.proto = conferer.proto || {};
conferer.proto.views = {};

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ _Base
conferer.proto.views._Base = Backbone.View.extend({
	_name: '_base',
	_selector: '',
	_templateParsed : null,

	render: function() {
		if (this._templateParsed) {
			this.$el.html(this._templateParsed((this.model && this.model.attributes) ? this.model.attributes : null));
		}
		return this;
	},

	loadTemplateAsync: function(templateName, callbackFunc) {
		(function(caller) {
			$.get('tpl/' + templateName + '.tpl', function(data) {
				caller.onTemplateLoaded(_.template(data), callbackFunc);
			});
		})(this);
	},

	onTemplateLoaded: function(parsedHTML, callbackFunc) {
		this._templateParsed = parsedHTML;
		callbackFunc(this);
	},

	jqmStyle: function() {
		$('#container').trigger('pagecreate'); // to let jqm style new content
	},

	initialize: function(var_args) {
		_.bindAll(this, 'render', 'loadTemplateAsync', 'onTemplateLoaded', 'jqmStyle');
	}
});


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ MainView
conferer.proto.views.MainView = conferer.proto.views._Base.extend({
	_name: 'MainView',
	_templateName: 'main',
	conferenceList: null,
	header: null,

	render: function() {
		conferer.proto.views._Base.prototype.render.call(this);
	},

	initialize: function(var_args) {
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);
		this.loadTemplateAsync(this._templateName, function(thisView) {
			thisView.render();

			// add header-global.tpl into #header-global
			thisView.header = new conferer.proto.views.HeaderGlobal({
				el: '#header-global'
			});

			// add conference-list.tpl into #conference-list
			thisView.conferenceList = new conferer.proto.views.ConferencesList({
				el: '#conference-list',
				model: new conferer.proto.models.ConferencesList() // {filters: {order: 'date'}})
			});

		});
	}
});


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ HeaderGlobal
conferer.proto.views.HeaderGlobal = conferer.proto.views._Base.extend({
	_name: 'HeaderGlobal',
	_templateName: 'header-global',

    render: function() {
		conferer.proto.views._Base.prototype.render.call(this);
		this.jqmStyle();
    },

	initialize: function(var_args) {
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);
		this.loadTemplateAsync(this._templateName, function(thisView) {
			thisView.render();
		});
	}
});


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ConferenceSummary
conferer.proto.views.ConferenceSummary = conferer.proto.views._Base.extend({
	_name: 'ConferenceSummary',
	_html: '',

    render: function() { return this; },

	getHtml: function() { return this._html; },

	initialize: function(var_args) {
		_.bind(this, 'getHtml');
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);
		this._templateParsed = var_args.templateParsed;
    	this._html = this._templateParsed((this.model && this.model.attributes) ? this.model.attributes : null);
	}
});


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ConferencesList
conferer.proto.views.ConferencesList = conferer.proto.views._Base.extend({
	_name: 'ConferencesList',
	_templateName: 'conference-list',
	_subTemplateName: 'conference-summary',
	subViewTemplate: null,
	// subViewsRenderCounter: 0,

    render: function() {
		conferer.proto.views._Base.prototype.render.call(this);
    },

    setSubViewTemplate: function(thisView, templateName) {
		$.get('tpl/' + templateName + '.tpl', function(data) {
			thisView.subViewTemplate = _.template(data);
		});
    },

	initialize: function(var_args) {
		// get template for subView only once, then pass it to subView.init as templateParsed param
		this.setSubViewTemplate(this, this._subTemplateName);

LOG(this._templateName);

		// add conference-list.tpl into #conference-list
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);
		this.loadTemplateAsync(this._templateName, function(thisView) {
			thisView.render();
		});

		var that = this;
		this.listenTo(this.model, 'reset', function() {
			that.render();
// TODO: save $('#container') into local var
			$('#container').trigger('pagecreate'); // to let jqm style new content
			// create and append subViews (ConferenceSummary)
			var collection = that.model;
			_.each(collection.models, function(conferenceModel, index) {

				var summaryView = new conferer.proto.views.ConferenceSummary({
					el: '#conference-summary-container',
					templateParsed: that.subViewTemplate,
					model: conferenceModel // {filters: {order: 'date'}})
				});
				that.$el.append(summaryView.getHtml());
			});

			this.jqmStyle();

			// show conference-list
			//$.mobile.changePage(this.el); // this.el is a string here

// Now call changePage() and tell it to switch to
        // the page we just modified.
        $.mobile.changePage($page, options);

		});
	}
});