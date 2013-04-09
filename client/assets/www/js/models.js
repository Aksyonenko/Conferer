var conferer = conferer || {};
conferer.proto = conferer.proto || {};
conferer.proto.models = {};





conferer.proto.models.ConferenceSummary = Backbone.Model.extend({
	idAttribute: 'conferenceID',
	defaults: {
		'title': 'unknown2',
		'date': 'unknow2n'
	},

	initialize: function(var_args) {
		//console.log('ConferenceSummary.initialize: ' + this.getTitle());
	},

	getID: function() { return this.id; },
	getTitle: function() { return this.get('title'); },
	getDates: function() { return '11-12.04.12'; }
});






conferer.proto.models.ConferencesList = Backbone.Collection.extend({
	model: conferer.proto.models.ConferenceSummary,
	_filters: {},

	initialize: function(var_args) {
		_.bind(this, 'loadCollections');
		var_args = var_args || {};
		this._filters = var_args.filters || {order: 'date'};
		//this.url = 'http://conferer.local/get_cl.php';
		this.url = 'http://contacts.cityi.com.ua/conferer/get_cl.php';
		this.loadCollections();
	},

	loadCollections: function() {
		LOG('models.js begin loading conferences');
		this.fetch({
			query: this._filters,
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