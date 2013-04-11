var conferer = conferer || {};
conferer.proto = conferer.proto || {};
conferer.proto.views = {};

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ _Base
conferer.proto.views._Base = Backbone.View.extend({
	_name: '_base',
	_selector: '',
	_templateRaw: '',
	_templateParsed : null,

	render: function() {
		if (this._templateParsed) {
			this.$el.html(this._templateParsed((this.model && this.model.attributes) ? this.model.attributes : null));
		}
		return this;
	},
/*
	loadTemplateAsync: function(templateName, callbackFunc) {
		(function(caller) {
			conferer.helper.loadTemplate(templateName, function(parsedHTML) {
				caller.onTemplateLoaded(parsedHTML, callbackFunc);
			});
		})(this);
	},

	onTemplateLoaded: function(parsedHTML, callbackFunc) {
		this._templateParsed = parsedHTML;
		callbackFunc(this);
	},
*/
	initialize: function(var_args) {
		_.bindAll(this, 'render'/*, 'loadTemplateAsync', 'onTemplateLoaded'*/);
		this._templateParsed = _.template(this._templateRaw);
	}
});


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ MainView
conferer.proto.views.MainView = conferer.proto.views._Base.extend({
	_name: 'MainView',
	_templateRaw: ' \
		<div id="dashboard" style="background:#444; position:fixed; top:0; left:0; height:2000px; width: 200px; overflow-y:auto;">xdfgsdrfg</div> \
        <div id="footer-global"></div> \
        <div id="content-wrapper" style="background:black; overflow:hidden; position:absolute; left: 200px; top:0px; width:100%; overflow:hidden; z-index:100;"> \
            <div id="header-global" style="background:#ccc; position:fixed; top:0; left:200px; width:100%; height:100px; z-index:101;"></div> \
            <div id="content" style="background:#999; margin-top:100px; min-height:800px;"> \
                <div id="conference-list" style="width:100%; position:relative;"> \
                	<div class="left" style="background:red; "></div> \
                	<div class="center" style="background:green; "></div> \
                	<div class="right" style="background:blue;"></div> \
                </div> \
                <div id="conference-details"> \
                    conference-details \
                </div> \
            </div> \
        </div>',
	conferenceListLeft: null,
	conferenceListCenter: null,
	conferenceListRight: null,
	header: null,

	render: function() {
		conferer.proto.views._Base.prototype.render.call(this);
	},

	initialize: function(var_args) {
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);
		this.render();

		this.header = new conferer.proto.views.HeaderGlobal({
			el: '#header-global'
		});

		var currentDate = new Date(),
			prevDate = new Date(currentDate.getFullYear(), currentDate.getMonth() - 1, 1),
			nextDate = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 1);

		this.conferenceListLeft = new conferer.proto.views.ConferencesList({
			el: '#conference-list > .left',
			model: new conferer.proto.models.ConferencesList({
				month: prevDate.getMonth()*1 + 1,
				year: prevDate.getFullYear()
			}) // {filters: {order: 'date'}})
		});
		
		this.conferenceListCenter = new conferer.proto.views.ConferencesList({
			el: '#conference-list > .center',
			model: new conferer.proto.models.ConferencesList({
				month: currentDate.getMonth()*1 + 1,
				year: currentDate.getFullYear()
			}) // {filters: {order: 'date'}})
		});
		
		this.conferenceListCenter = new conferer.proto.views.ConferencesList({
			el: '#conference-list > .right',
			model: new conferer.proto.models.ConferencesList({
				month: nextDate.getMonth()*1 + 1,
				year: nextDate.getFullYear()
			}) // {filters: {order: 'date'}})
		});

	}
});


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ HeaderGlobal
conferer.proto.views.HeaderGlobal = conferer.proto.views._Base.extend({
	_name: 'HeaderGlobal',
	_templateRaw: ' \
		<a href="#" data-icon="home">Home</a> \
			<h1>Conferer</h1> \
		<a href="#" data-icon="bars">Menu</a> \
	',

    render: function() {
		conferer.proto.views._Base.prototype.render.call(this);
    },

	initialize: function(var_args) {
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);
		this.render();
	}
});


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ConferencesList -> ConferenceSummary
conferer.proto.views.ConferenceSummary = conferer.proto.views._Base.extend({
	_name: 'ConferenceSummary',
	_templateRaw: ' \
                        <li class="conference-summary">  \
                            <h3><%= this.model.getTitle() %></h3> \
                            <p><%= this.model.getDates().year + " " + "asdfasdf" %></p> \
                        </li> \
	',
	_html: '',

    render: function() { return this; },

	getHtml: function() { return this._html; },

	initialize: function(var_args) {
		_.bind(this, 'getHtml');
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);
    	this._html = this._templateParsed((this.model && this.model.attributes) ? this.model.attributes : null);
	}
});


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ConferencesList
conferer.proto.views.ConferencesList = conferer.proto.views._Base.extend({
	_name: 'ConferencesList',
	_templateRaw: '<%= this.$el.attr("class")  %><ul class="conference-summary-container">conference-list<span class="swipeleft">></span><span class="swiperight"><</span></ul>',
	_listContainer: null,
	_listContainerName: '.conference-summary-container',

    render: function() {
		conferer.proto.views._Base.prototype.render.call(this);
		this._listContainer = this.$el.find(this._listContainerName);
    },

	initialize: function(var_args) {
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);




		
		var that = this;


		//conferer.events['conferenceList-moveRight']
		this.listenTo(conferer.events.conferenceList, 'conferenceList-moveRight', function(e) {
			var currentClass = that.$el.attr('class');
			switch (currentClass) {
				case 'left':
					that.$el.removeClass(currentClass).addClass('right');
					break;
				case 'center':
					that.$el.removeClass(currentClass).addClass('left');
					break;
				case 'right':
					that.$el.removeClass(currentClass).addClass('center');
					break;
				default: break;
			}
		});
		this.listenTo(conferer.events.conferenceList, 'conferenceList-moveLeft', function(e) {
			var currentClass = that.$el.attr('class');
			switch (currentClass) {
				case 'left':
					that.$el.removeClass(currentClass).addClass('center');
					break;
				case 'center':
					that.$el.removeClass(currentClass).addClass('right');
					break;
				case 'right':
					that.$el.removeClass(currentClass).addClass('left');
					break;
				default: break;
			}
		});



		this.listenTo(this.model, 'reset', function() {
			that.render();

			that.$el.find('.swipeleft').on('click', function() {
				console.log('fire swipeleft');
				conferer.events.conferenceList.trigger('conferenceList-moveLeft', {t:22});
			});



			that.$el.find('.swiperight').on('click', function() {
// TODO:
// animate
// ajax
				// change
				//console.log(conferer.events['conferenceList-moveRight']);
				console.log('fire swiperight');
				conferer.events.conferenceList.trigger('conferenceList-moveRight', {t:11});
			});

			// create and append subViews (ConferenceSummary)
			var collection = that.model;
			_.each(collection.models, function(conferenceModel, index) {
				var summaryView = new conferer.proto.views.ConferenceSummary({
					model: conferenceModel // {filters: {order: 'date'}})
				});
				that._listContainer.append(summaryView.getHtml());
			});
		});
	}
});