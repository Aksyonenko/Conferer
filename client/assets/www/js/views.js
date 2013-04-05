var conferer = conferer || {};
conferer.proto = conferer.proto || {};
conferer.proto.views = {};

conferer.proto.views._Base = Backbone.View.extend({
	_name: '_base',
	_selector: '',
	_templateParsed : null,

	render: function() {
		//console.log('_Base.render');
		if (this._templateParsed) {
			this.$el.html(this._templateParsed((this.model && this.model.attributes) ? this.model.attributes : null));
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

conferer.proto.views.MainView = conferer.proto.views._Base.extend({
	_name: 'MainView',

	conferenceList: null,

	render: function() {
		//console.log('MainView.render');
		conferer.proto.views._Base.prototype.render.call(this);
	},

	initialize: function(var_args) {
		//console.log('MainView.initialize');
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);
		this.loadTemplateAsync('main', function(thisView) {
			//console.log('MainView.onTemplateLoaded');

			thisView.render();

			this.conferenceList = new conferer.proto.views.ConferencesList({
				el: '#conference-list',
				model: new conferer.proto.models.ConferencesList()
			});
		});
	}
});

conferer.proto.views.ConferenceAnnouncement =  conferer.proto.views._Base.extend({
	_name: 'ConferenceAnnouncement',

	initialize: function(var_args) {
		//console.log('ConferenceAnnouncement.initialize');
	}
});

conferer.proto.views.ConferencesList = conferer.proto.views._Base.extend({
	_name: 'ConferencesList',

	initialize: function(var_args) {
		//console.log('ConferencesList.initialize');
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);
		this.loadTemplateAsync('conferencelist', function(thisView) {
			thisView.render();
		});

		var that = this;
		this.listenTo(this.model, 'reset', function() {
			that.render();

			var collection = that.model;
			_.each(collection.models, function(conferenceModel, index) {
				var announcementView = new conferer.proto.views.ConferenceAnnouncement({model: conferenceModel});
				announcementView.loadTemplateAsync('conference-announcement', function(thisView) {
					thisView.render();
					that.$el.find('#conference-announcements').append(thisView.$el);
				});

			});
		});
	},

    render: function() {
		//console.log('ConferencesList.render');
		conferer.proto.views._Base.prototype.render.call(this);
    }
});