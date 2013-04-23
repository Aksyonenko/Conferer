var conferer = conferer || {};
conferer.proto = conferer.proto || {};
conferer.proto.models = {};







conferer.proto.models.ConferenceSummary = Backbone.Model.extend({
	idAttribute: 'conferenceID',
	defaults: {
		'title': 'unknown2',
		'startDate': 'unknow2n'
	},

	initialize: function(var_args) {
		//console.log('ConferenceSummary.initialize: ' + this.getTitle());
	},

	getID: function() { return this.id; },

	getTitle: function() { return this.get('title'); },

	getStartDay: function() { return new Date(this.get('startDate')).getDateParams().day; },
	getStartMonth: function() { return GetMonthName(new Date(this.get('startDate')).getDateParams().month, false); },
	getEndDay: function() { return new Date(this.get('endDate')).getDateParams().day; },
	getEndMonth: function() { return GetMonthName(new Date(this.get('endDate')).getDateParams().month, false); }
});

conferer.proto.models.ConferencesList = Backbone.Collection.extend({
	model: conferer.proto.models.ConferenceSummary,
	month: 0,
	year: 0,

	changeDate: function(deltaMonthes) {
		this.month += deltaMonthes;
		if (this.month > 12) {
			this.month = this.month - 12;
			this.year++;
		} else if (this.month < 1) {
			this.month = this.month + 12;
			this.year--;
		}
	},

	initialize: function(var_args) {
		_.bind(this, 'loadCollections');
		var_args = var_args || {};

		//this.url = 'http://conferer.local/get_cl.php';
		this.url = 'http://contacts.cityi.com.ua/akqa/conferer/get_cl.php';
		//this.url = 'http://10.11.100.25:8080/conferer/conferences?start=1357084800000';
		//this.url = 'http://ec2-79-125-106-6.eu-west-1.compute.amazonaws.com:8080/conferences?start=1357084800000';

		this.month = var_args.month || 0;
		this.year = var_args.year || 0;

		this.loadCollections();
	},

	loadCollections: function() {
		LOG('models.js begin loading conferences');
		this.fetch({
			query: {
				year: this.year,
				month: this.month
			},
			reset: true,
			success: function(collection, response) {
				// console.log('[' + new Date().toISOString() + '] ConferencesListModel.loadCollections.success !!!!!!!!', collection);
				LOG('models.js loading success');
			},
			error: function(collection, response, errorText) {
				// console.log('[' + new Date().toISOString() + '] ConferencesListModel.loadCollections.failure !!!!!!!!!!!!!', collection, response, errorText);
				LOG('models.js loading failure: ' + response.statusText + ': ' + response.status);
			},
		});
	}
});