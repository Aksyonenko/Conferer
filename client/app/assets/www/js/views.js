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
	_width: 0,
	_templateRaw: ' \
		<div id="dashboard" style="background:#444; position:fixed; top:0; left:0; height:2000px; width: 200px; overflow-y:auto;">xdfgsdrfg</div> \
        <div id="footer-global"></div> \
        <div id="content-wrapper" style="background:black; overflow:hidden; position:absolute; left: 0; top:0px; width:100%; overflow:hidden; z-index:100;"> \
            <div id="header-global" style="background:#ccc; position:fixed; top:0; left:0; width:100%; height:100px; z-index:101;"></div> \
            <div id="content" style="background:#999; margin-top:100px; min-height:800px;"> \
            	<div id="conference-list" style="position:relative; width: 100%;"> \
            	</div> \
            	<div id="conference-details"> \
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

	getWidth: function() { return this._width; },

	initialize: function(var_args) {
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);
		_.bind(this, 'getWidth')
		this.render();

		this.header = new conferer.proto.views.HeaderGlobal({
			el: '#header-global'
		});

		this.conferenceListContainer = new conferer.proto.views.conferenceListContainer({
			el: '#conference-list'
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
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ConferenceListContainer
conferer.proto.views.conferenceListContainer = conferer.proto.views._Base.extend({
	_name: 'conferenceListContainer',
	_templateRaw: ' \
                	<div class="left" style="background:#D3C2C2; "></div> \
                	<div class="center" style="background:#C2D3C5; "></div> \
                	<div class="right" style="background:#BBC8D3;"></div> \
	',
	_bodyWidth: 0,


    bindEvents: function() {
		$(document).bind('touchmove', function(e) {
			e.preventDefault();
		});

		var that = this,
			hammerHandler = this.$el.hammer({
				drag_min_distance: 1,
				stop_browser_behavior : true
			}),
			lastHandledDragTime = 0,
			lastHandledDragPosition = -1,
			isDraggingNow = false,
			shift = 0,

			//childWidth = this.$el.find('.center').outerWidth(),
			childWidth = $('body').width(),
			minShift = Math.floor(childWidth/2); // half of one of three views;

			//containerWidth = this.$el.width(),
			//maxShift = child*2//containerWidth,
			//minShift = Math.floor(containerWidth/2); // half of one of three views

		hammerHandler.on('dragstart', function(e) {
			shift = 0;
			isDraggingNow = true;
			lastHandledDragTime = e.gesture.timestamp;
			lastHandledDragPosition = e.gesture.center.pageX - e.target.offsetLeft;
		});

		hammerHandler.on('dragend', function(e) {
			if (!isDraggingNow) {
				return;
			}

			lastHandledDragTime = 0;
			lastHandledDragPosition = -1;
			isDraggingNow = false;


			if (Math.abs(shift) < minShift) {
				that.$el.animate({
					left: 0
				}, 500);

				shift = 0;
			} else {

				that.$el.animate({
					left: (shift < 0 ? -1 : 1) * (childWidth)
				}, 500, function() {

					if (shift < 0) {
						console.log('moveRight');
						conferer.events.conferenceList.trigger('conferenceList-moveRight', {t:11});
					} else {
						console.log('moveLeft');
						conferer.events.conferenceList.trigger('conferenceList-moveLeft', {t:22});
					}
					that.$el.css('left', 0);
					shift = 0;
				});
			}

		});

		hammerHandler.on('drag', function(e) {
			if (e.gesture.timestamp - lastHandledDragTime < 10 || !isDraggingNow) {
				return;
			}
			var currentPosition = e.gesture.center.pageX - e.target.offsetLeft,
				deltaPosition = currentPosition - lastHandledDragPosition;
			if (Math.abs(deltaPosition) < 5) {
				return;
			}
			lastHandledDragTime = e.gesture.timestamp;
			lastHandledDragPosition = currentPosition;

			shift += (Math.abs(shift) + deltaPosition < childWidth) ? deltaPosition : 0;
			that.$el.css('left', shift);

			if (Math.abs(shift) >= minShift) {
				hammerHandler.trigger('dragend');
			}
		});

	},

	createSubViews: function() {

		var currentDate = new Date(),
			prevDate = new Date(currentDate.getFullYear(), currentDate.getMonth() - 1, 1),
			nextDate = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 1);


		this._bodyWidth = $('body').width();
		var shift = this._bodyWidth;

		this.conferenceListLeft = new conferer.proto.views.ConferencesList({
			el: '#conference-list > .left',
			shift: -shift,
			maxShift: shift,
			model: new conferer.proto.models.ConferencesList({
				month: prevDate.getMonth()*1 + 1,
				year: prevDate.getFullYear()
			})
		});

		this.conferenceListCenter = new conferer.proto.views.ConferencesList({
			el: '#conference-list > .center',
			shift: 0,
			maxShift: shift,
			model: new conferer.proto.models.ConferencesList({
				month: currentDate.getMonth()*1 + 1,
				year: currentDate.getFullYear()
			})
		});

		this.conferenceListCenter = new conferer.proto.views.ConferencesList({
			el: '#conference-list > .right',
			shift: shift,
			maxShift: shift,
			model: new conferer.proto.models.ConferencesList({
				month: nextDate.getMonth()*1 + 1,
				year: nextDate.getFullYear()
			})
		});
	},

	initialize: function(var_args) {
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);
		_.bind(this, 'bindEvents', 'createSubViews');
		this.render();
		this.createSubViews();
		this.bindEvents();
	}
});

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ConferenceListContainer -> ConferencesList -> ConferenceSummary
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
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);
		_.bind(this, 'getHtml');
    	this._html = this._templateParsed((this.model && this.model.attributes) ? this.model.attributes : null);
	}

});


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ConferenceListContainer -> ConferencesList
conferer.proto.views.ConferencesList = conferer.proto.views._Base.extend({
	events: {
	},
	_name: 'ConferencesList',
	_templateRaw: '<%= this.$el.attr("class") %><div class="loader"></div><ul class="conference-summary-container">conference-list<!-- span class="swipeleft">></span><span class="swiperight"><</span --></ul>',
	_listContainer: null,
	_listContainerName: '.conference-summary-container',
	_shift: 0,
	_maxShift: 0,

	showLoader: function() {
		this.$el.find('.loader').addClass('is-visible-block').removeClass('is-hidden');
	},

	hideLoader: function() {
		this.$el.find('.loader').addClass('is-hidden').removeClass('is-visible-block');
	},

    render: function() {
		conferer.proto.views._Base.prototype.render.call(this);
		this._listContainer = this.$el.find(this._listContainerName);
    },

	initialize: function(var_args) {
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);
		_.bind(this, 'showLoader', 'hideLoader');

		this._shift = var_args.shift;
		this._maxShift = var_args.maxShift;

		this.$el.css('left', this._shift);
// TODO: $('body').width() should become variable
		this.$el.css('width', this._maxShift);
		//this.$el.css('width', $('body').width());
		var that = this;


// TODO: refactor
		//conferer.events['conferenceList-moveRight']
		this.listenTo(conferer.events.conferenceList, 'conferenceList-moveRight', function(e) {
			var currentClass = that.$el.attr('class');
			switch (currentClass) {
				case 'left':
					that.$el.removeClass(currentClass).addClass('right').css('left', this._maxShift);
					that.model.changeDate(3);
					that.model.loadCollections();
					break;
				case 'center':
					that.$el.removeClass(currentClass).addClass('left').css('left', -this._maxShift);
					break;
				case 'right':
					that.$el.removeClass(currentClass).addClass('center').css('left', 0);
					break;
				default: break;
			}
		});
		this.listenTo(conferer.events.conferenceList, 'conferenceList-moveLeft', function(e) {
			var currentClass = that.$el.attr('class');
			switch (currentClass) {
				case 'left':
					that.$el.removeClass(currentClass).addClass('center').css('left', 0);
					break;
				case 'center':
					that.$el.removeClass(currentClass).addClass('right').css('left', this._maxShift);
					break;
				case 'right':
					that.$el.removeClass(currentClass).addClass('left').css('left', -this._maxShift);
					that.model.changeDate(-3);
					that.model.loadCollections();
					break;
				default: break;
			}
		});


		this.showLoader();
		this.listenTo(this.model, 'reset', function() {
			that.render();

			// create and append subViews (ConferenceSummary)
			var collection = that.model;
			_.each(collection.models, function(conferenceModel, index) {
				var summaryView = new conferer.proto.views.ConferenceSummary({
					model: conferenceModel // {filters: {order: 'date'}})
				});
				that._listContainer.append(summaryView.getHtml());
			});

			setTimeout(function () {that.hideLoader();}, 1500);
		});
	}
});