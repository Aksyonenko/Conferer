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
		<!-- div id="dashboard">dashboard</div --> \
        <div id="content-wrapper"> \
	        <div id="footer-global"></div> \
            <div id="header-global"></div> \
            <div id="content"> \
				<div id="conference-list-header"> \
				</div> \
            	<div id="conference-list"> \
            	</div> \
            	<div id="conference-details"> \
            	</div> \
            </div> \
        </div>',
	conferenceListLeft: null,
	conferenceListCenter: null,
	conferenceListRight: null,
	conferenceListHeader: null,
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

		var currentDate = new Date(),
			prevDate = new Date(currentDate.getFullYear(), currentDate.getMonth() - 1, 1),
			nextDate = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 1);


		this.conferenceListContainer = new conferer.proto.views.ConferenceListContainer({
			el: '#conference-list'
		});

		this.conferenceListHeader = new conferer.proto.views.ConferenceListHeader({
			el: '#conference-list-header'
		});

	}
});


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ HeaderGlobal
conferer.proto.views.HeaderGlobal = conferer.proto.views._Base.extend({
	_name: 'HeaderGlobal',
	_templateRaw: ' \
		<div id="header-global-search"></div> \
		<div id="dashboard-button"></div> \
		<div id="header-global-title">Confere<span>r</span> v0.1.119</div> \
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
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ConferenceListHeader
conferer.proto.views.ConferenceListHeader = conferer.proto.views._Base.extend({
	_name: 'conferenceListHeader',

	_currentMonth: 0,
	_currentYear: 0,
	getNextMonth: function() { return GetMonthName(this._currentMonth + 1, true); },
	getPrevMonth: function() { return GetMonthName(this._currentMonth - 1, true); },
	getCurrentMonth: function() { return GetMonthName(this._currentMonth, true); },

	_templateRaw: ' \
						<div id="conference-list-header-next"><%=this.getNextMonth()%></div> \
						<div id="conference-list-header-prev"><%=this.getPrevMonth()%></div> \
						<div id="conference-list-header-current"> \
							<div id="conference-list-header-calendar"> \
								<div id="conference-list-header-calendar-week">tue</div> \
								<div id="conference-list-header-calendar-day">16</div> \
							</div> \
							<div id="conference-list-header-current-month"><%=this.getCurrentMonth()%></div> \
							<div id="conference-list-header-current-year"><%=this._currentYear%></div> \
						</div> \
	',

	initialize: function(var_args) {
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);
		var curDate = new Date().getDateParams();
		this._currentMonth = curDate.month;
		this._currentYear = curDate.year;

		this.render();

		this.listenTo(conferer.events.conferenceList, 'conferenceList-moveRight', function(e) {
			this._currentMonth++;
			if (this._currentMonth > 12) {
				this._currentMonth = 1;
				this._currentYear++;
			}

			this.render();
		});

		this.listenTo(conferer.events.conferenceList, 'conferenceList-moveLeft', function(e) {
			this._currentMonth--;
			if (this._currentMonth < 1) {
				this._currentMonth = 12;
				this._currentYear--;
			}

			this.render();
		});
	}
});


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ConferenceListContainer
conferer.proto.views.ConferenceListContainer = conferer.proto.views._Base.extend({
	_name: 'conferenceListContainer',
	_templateRaw: ' \
                	<div class="left"></div> \
                	<div class="center"></div> \
                	<div class="right"></div> \
	',
	_bodyWidth: 0,
	_centerColumn: null,
	_isCenterColumnResized: false,

	onChangedMonth: function() {
		this._centerColumn = this.$el.find('.center');
		this._isCenterColumnResized = false;
		this._centerColumn.css({
			'top': '0px',
		});
/*
		var totalHeight = 0;
		console.log($('.conference-summary', this._centerColumn));
		this._centerColumn.find('.conference-summary-container').find('.conference-summary').each(function(element) {
			totalHeight += element.outerHeight(true);
		});

		console.log('totalHeight: ' + totalHeight);

		this._centerColumn.css({
			'top': '0px',
			'height': totalHeight + 'px'
		});
*/
	},

    bindEvents: function() {
		$(document).bind('touchmove', function(e) {
			e.preventDefault();
		});

		var DRAG_STATUS = {
			IDLE: 0,
			WAITING_DIR: 1,
			HORIZONTAL: 2,
			VERTICAL: 3
		}


		var that = this,
			hammerHandler = this.$el.hammer({
				drag_min_distance: 1,
				stop_browser_behavior : true
			}),

			currentDragStatus = DRAG_STATUS.IDLE,
			shift = 0,

			lastPositions = {x: -1, y: -1},
			lastHandledDragTime = 0,

			childWidth = $('body').width(),

			horizontalThreshold = Math.floor(childWidth / 3), // half of the screen
			maxScrollableHeight = 0;

		function DragReset() {
			currentDragStatus = DRAG_STATUS.IDLE;
			shift = 0;

			lastPositions = {x: -1, y: -1};
			lastHandledDragTime = 0;

			childWidth = $('body').width();
			horizontalThreshold = Math.floor(childWidth / 3);
		}


		hammerHandler.on('dragstart', function(e) {
			DragReset();
			lastHandledDragTime = e.gesture.timeStamp;
			currentDragStatus = DRAG_STATUS.WAITING_DIR;

			lastPositions.x = e.gesture.center.pageX - e.target.offsetLeft;
			lastPositions.y = e.gesture.center.pageY - e.target.offsetTop;

			if (!that._isCenterColumnResized) {

				maxScrollableHeight = 0;
				/*var n = 0;
				that.$el.find('.conference-summary').each(function(index, element) {
					n++;
					console.log(n + ' element height ' +  $(element).outerHeight(true));
					maxScrollableHeight += $(element).outerHeight(true);
				});
				*/
				maxScrollableHeight = 810;

				that._isCenterColumnResized = true;
				that._centerColumn.css('height', maxScrollableHeight);

				console.log('center column resized to ' + maxScrollableHeight);
			}

			return true;
		});

		hammerHandler.on('drag', function(e) {
			if (e.gesture.timeStamp - lastHandledDragTime < 1) {
				return true;
			}


			lastHandledDragTime = e.gesture.timeStamp;
			var deltaX = (e.gesture.center.pageX - e.target.offsetLeft) - lastPositions.x,
				deltaY = (e.gesture.center.pageY - e.target.offsetTop) - lastPositions.y;

			lastPositions.x = e.gesture.center.pageX - e.target.offsetLeft;
			lastPositions.y = e.gesture.center.pageY - e.target.offsetTop;

			if (currentDragStatus <= DRAG_STATUS.WAITING_DIR) {
				if (Math.abs(deltaX) > Math.abs(deltaY)) {
					currentDragStatus = DRAG_STATUS.HORIZONTAL;
				} else {
					currentDragStatus = DRAG_STATUS.VERTICAL;
					shift = parseInt(that._centerColumn.css('top'));
				}
			}

			if (currentDragStatus === DRAG_STATUS.HORIZONTAL) {
				shift += deltaX;
				//shift += (Math.abs(shift) + deltaX < childWidth) ? deltaX : 0;
				that.$el.css('left', shift);
			}

			if (currentDragStatus === DRAG_STATUS.VERTICAL) {
				shift += deltaY;
				//shift += (Math.abs(shift) + deltaY < childWidth) ? deltaY : 0;
				if (shift < -maxScrollableHeight) {
					shift = -maxScrollableHeight;
				}

				if (shift > 0) {
					shift = 0;
				}

				that._centerColumn.css('top', shift);
			}
		});

		hammerHandler.on('dragend', function(e) {
			DragReset();
			currentDragStatus = DRAG_STATUS.IDLE;
		});

/*
		var that = this,
			hammerHandler = this.$el.hammer({
				drag_min_distance: 1,
				stop_browser_behavior : true
			}),
			lastHandledDragTime = 0,
			lastHandledDragPositionX = -1,
			lastHandledDragPositionY = -1,
			isVertical = false,
			isDraggingNow = false,
			shift = 0,

			childWidth = $('body').width(),
			minShift = Math.floor(childWidth / 3); // half of the screen

		hammerHandler.on('dragstart', function(e) {
			shift = 0;
			isDraggingNow = true;
			isVertical = false;
			lastHandledDragTime = e.gesture.timestamp;
			lastHandledDragPositionX = e.gesture.center.pageX - e.target.offsetLeft;
			lastHandledDragPositionY = e.gesture.center.pageY - e.target.offsetTop;
		});

		hammerHandler.on('dragend', function(e) {
			if (!isDraggingNow) {
				return;
			}

			lastHandledDragTime = 0;
			lastHandledDragPositionX = -1;
			lastHandledDragPositionY = -1;
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
						conferer.events.conferenceList.trigger('conferenceList-moveRight', {t:11});
					} else {
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
			var currentPositionX = e.gesture.center.pageX - e.target.offsetLeft,
				currentPositionY = e.gesture.center.pageY - e.target.offsetTop,
				deltaPositionX = currentPositionX - lastHandledDragPositionX,
				deltaPositionY = currentPositionY - lastHandledDragPositionY;

			if (Math.abs(deltaPositionX) < 5) {
				return;
			}
			lastHandledDragTime = e.gesture.timestamp;
			lastHandledDragPositionX = currentPositionX;

			shift += (Math.abs(shift) + deltaPositionX < childWidth) ? deltaPositionX : 0;
			that.$el.css('left', shift);

			if (Math.abs(shift) >= minShift) {
				hammerHandler.trigger('dragend');
			}
		});
*/
	},

	createSubViews: function() {
		var currentDate = new Date(),
			prevDate = new Date(currentDate.getFullYear(), currentDate.getMonth() - 1, 1),
			nextDate = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 1);


		this._bodyWidth = $('body').width();
		var shift = this._bodyWidth,
			scrollWidth = 17;;

		this.conferenceListLeft = new conferer.proto.views.ConferencesList({
			el: '#conference-list > .left',
			shift: -shift,
			maxShift: shift,
			scrollWidth: scrollWidth,
			model: new conferer.proto.models.ConferencesList({
				month: prevDate.getMonth()*1 + 1,
				year: prevDate.getFullYear()
			})
		});

		this.conferenceListCenter = new conferer.proto.views.ConferencesList({
			el: '#conference-list > .center',
			shift: 0,
			maxShift: shift,
			scrollWidth: scrollWidth,
			model: new conferer.proto.models.ConferencesList({
				month: currentDate.getMonth()*1 + 1,
				year: currentDate.getFullYear()
			})
		});

		this.conferenceListRight = new conferer.proto.views.ConferencesList({
			el: '#conference-list > .right',
			shift: shift,
			maxShift: shift,
			scrollWidth: scrollWidth,
			model: new conferer.proto.models.ConferencesList({
				month: nextDate.getMonth()*1 + 1,
				year: nextDate.getFullYear()
			})
		});
	},

	initialize: function(var_args) {
		conferer.proto.views._Base.prototype.initialize.call(this, var_args);
		_.bind(this, 'bindEvents', 'createSubViews', 'onMonthChanged');
		this.render();
		this.createSubViews();
		this.onChangedMonth();
		this.bindEvents();
	}
});

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ConferenceListContainer -> ConferencesList -> ConferenceSummary
conferer.proto.views.ConferenceSummary = conferer.proto.views._Base.extend({
	_name: 'ConferenceSummary',
	_templateRaw: '\
    <li class="conference-summary"> \
		<div class="white"> \
			<div class="title"><%=this.model.getTitle()%></div> \
			<div class="info"> \
				<div class="logo"> \
					<div class="img"> \
						<img src="<%=this.model.get("logoURL")%>"> \
					</div> \
					<div class="date"><%=this.model.getStartDay()%> <span><%=this.model.getStartMonth()%></span> - <%=this.model.getEndDay()%> <span><%=this.model.getEndMonth()%></span></div> \
				</div> \
				<div class="details"> \
					<div class="description"> \
						<%=this.model.get("summary")%> \
					</div> \
					<div class="location"> \
						<%=this.model.get("city")%><%=(this.model.get("region") ? (", " + this.model.get("region")) : "")%>, <%=this.model.get("country")%> \
					</div> \
				</div> \
			</div> \
		</div> \
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
	_templateRaw: '<!-- %= this.$el.attr("class") % --><ul class="conference-summary-container"></ul>',
	_listContainer: null,
	_listContainerName: '.conference-summary-container',
	_shift: 0,
	_maxShift: 0,
	_scrollWidth: 0,

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
		this._scrollWidth = var_args.scrollWidth;


		this.$el.css('left', this._shift);
		this.$el.css('width', this._maxShift);

		this.$el.css('height',
			($('body').height() - $('#footer-global').outerHeight(true) - $('#conference-list-header').outerHeight(true) - $('#footer-global').outerHeight(true)) + 'px'
		);
		var that = this;


// TODO: refactor
		//conferer.events['conferenceList-moveRight']
		this.listenTo(conferer.events.conferenceList, 'conferenceList-moveRight', function(e) {
			var currentClass = that.$el.attr('class');
			switch (currentClass) {
				case 'left':
					that.$el.removeClass(currentClass).addClass('right').css('left', this._maxShift);
					that.model.changeDate(3);
					console.log('loading next');
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
					console.log('loading prev');
					that.model.loadCollections();
					break;
				default: break;
			}
		});


		this.showLoader();
		this.listenTo(this.model, 'reset', function() {
			that.render();

			that.$el.find('ul').css('width', that._maxShift + that._scrollWidth);

			// create and append subViews (ConferenceSummary)
			var collection = that.model;
			_.each(collection.models, function(conferenceModel, index) {
				var summaryView = new conferer.proto.views.ConferenceSummary({
					model: conferenceModel, // {filters: {order: 'date'}})
					scrollWidth: that._scrollWidth,
				});
				that._listContainer.append(summaryView.getHtml());
			});

			that.hideLoader();
		});
	}
});