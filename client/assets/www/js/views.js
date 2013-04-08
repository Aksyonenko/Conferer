var conferer = conferer || {};
conferer.proto = conferer.proto || {};
conferer.proto.views = {};

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Base
conferer.proto.views._Base = Backbone.View.extend({
	_name: '_base',
	_selector: '',
	_templateParsed : null,

	render: function() {
		if (this._templateParsed) {


// TODO
if (this.model) {
	console.log(this.model.attributes);
}

			this.$el.html(this._templateParsed((this.model && this.model.attributes) ? this.model.attributes : null)); // .trigger('pagecreate'); 
		}
		return this;
	},

	loadTemplateAsync: function(templateName, callbackFunc) {
		//console.log('_Base.loadTemplateAsync');
		(function(caller) {
			$.get('tpl/' + templateName + '.tpl', function(data) {
				caller.onTemplateLoaded(_.template(data), callbackFunc);
			});
		})(this);
	},

	onTemplateLoaded: function(parsedHTML, callbackFunc) {
		//console.log('_Base.onTemplateLoaded');
		this._templateParsed = parsedHTML;
		callbackFunc(this);
	},

	initialize: function(var_args) {
		//console.log('_Base.initialize');
		_.bindAll(this, 'render', 'loadTemplateAsync', 'onTemplateLoaded');
	}
});


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ MainView
conferer.proto.views.MainView = conferer.proto.views._Base.extend({
	_name: 'MainView',

	conferenceList: null,
	header: null,

	render: function() {
		conferer.proto.views._Base.prototype.render.call(this);
	},

	initialize: function(var_args) {
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);

		this.loadTemplateAsync('main', function(thisView) {
			thisView.render();

			thisView.header = new conferer.proto.views.HeaderGlobal({
				el: '#header'
			});

			thisView.conferenceList = new conferer.proto.views.ConferencesList({
				el: '#content',
				model: new conferer.proto.models.ConferencesList() // {filters: {order: 'date'}})
			});

		});
	}
});



// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ HeaderGlobal
conferer.proto.views.HeaderGlobal = conferer.proto.views._Base.extend({
	_name: 'HeaderGlobal',

    render: function() {
		conferer.proto.views._Base.prototype.render.call(this);
    },

	initialize: function(var_args) {
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);
		this.loadTemplateAsync('header', function(thisView) {
			thisView.render();
		});
	}

});



// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ConferenceSummary
conferer.proto.views.ConferenceSummary =  conferer.proto.views._Base.extend({
	_name: 'ConferenceSummary',

    render: function() {
		//console.log('ConferencesList.render');
		conferer.proto.views._Base.prototype.render.call(this);
    },

	initialize: function(var_args) {
		//console.log('ConferenceSummary.initialize');
	}
});

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ConferencesList
conferer.proto.views.ConferencesList = conferer.proto.views._Base.extend({
	_name: 'ConferencesList',

    render: function() {
		conferer.proto.views._Base.prototype.render.call(this);
    },

	initialize: function(var_args) {
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);
		this.loadTemplateAsync('conference-list', function(thisView) {
			thisView.render();
		});

		var that = this;
		this.listenTo(this.model, 'reset', function() {
			that.render();

// TODO: remember $('#container') into local var

			$('#container').trigger('pagecreate'); // to let jqm style new content

			var collection = that.model;
			_.each(collection.models, function(conferenceModel, index) {
				var summaryView = new conferer.proto.views.ConferenceSummary({
					el: '#conference-summary-container',
					model: new conferer.proto.models.ConferenceSummary() // {filters: {order: 'date'}})
				});
				summaryView.loadTemplateAsync('conference-summary', function(thisView) {
					thisView.render();
					// that.$el.find('#conference-summary-container').append(thisView.$el);
				});

			});
		});
		

	}
});