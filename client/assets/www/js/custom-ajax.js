Backbone.sync = function(method, model, options) {
	console.log('>>>> CUSTOM SYNC: started');
	var methodMap = {
		'create': 'POST',
		'update': 'PUT',
		'patch': 'PATCH',
		'delete': 'DELETE',
		'read': 'GET'
	};

	var type = methodMap[method];
	// Default options, unless specified.
	_.defaults(options || (options = {}), {
		emulateHTTP: Backbone.emulateHTTP,
		emulateJSON: Backbone.emulateJSON
	});
	// Default JSON-request options.
	var params = {type: type, dataType: 'json'};
	// Ensure that we have a URL.
	if (!options.url) {
		params.url = _.result(model, 'url') || urlError();
	}

	// Ensure that we have the appropriate request data.
	if (options.data == null && model && (method === 'create' || method === 'update' || method === 'patch')) {
		params.contentType = 'application/json';
		params.data = JSON.stringify(options.attrs || model.toJSON(options));
	}
	// For older servers, emulate JSON by encoding the request into an HTML-form.
	if (options.emulateJSON) {
		params.contentType = 'application/x-www-form-urlencoded';
		params.data = params.data ? {model: params.data} : {};
	}
	// For older servers, emulate HTTP by mimicking the HTTP method with `_method`
	// And an `X-HTTP-Method-Override` header.
	if (options.emulateHTTP && (type === 'PUT' || type === 'DELETE' || type === 'PATCH')) {
		params.type = 'POST';
		if (options.emulateJSON) {
			params.data._method = type;
		}

		var beforeSend = options.beforeSend;
		options.beforeSend = function(xhr) {
			xhr.setRequestHeader('X-HTTP-Method-Override', type);
			if (beforeSend) {
				return beforeSend.apply(this, arguments);
			}
		};
	}

	// Don't process data on a non-GET request.
	if (params.type !== 'GET' && !options.emulateJSON) {
		params.processData = false;
	}

	// Make the request, allowing the user to override any Ajax options.

	console.log('>>>> CUSTOM SYNC: creating ajax ');
	var ajax = new XMLHttpRequest();
	var timeout = setTimeout(function() {
		console.log('>>>> CUSTOM SYNC: error: timeout');
		ajax.abort();
		params.error(ajax, 'timeout');
	}, 300000);

	console.log('>>>> CUSTOM SYNC: timer: started');


	ajax.onreadystatechange = function() {
			console.log('>>>> CUSTOM SYNC: readyState:' + ajax.readyState);
		if(ajax.readyState != 4) {
			return;
		}

		clearTimeout(timeout);

		if((ajax.status == 200) || (ajax.status == 0)) {
			console.log('>>>> CUSTOM SYNC: success ' + ajax.responseText);
			params.success(params.dataType == 'json' ? JSON.parse(ajax.responseText) : ajax.responseText, 'ok', ajax);
		} else {
			console.log('>>>> CUSTOM SYNC: error:error');
			params.error(ajax, 'error');
		}
	}

	_.extend(params, options);

	ajax.open(params.type, params.url, true);
	ajax.send(null); //

	model.trigger('request', model, ajax, options);
	return ajax;
};
/*


	var args = arguments[0];
	args.type = args.type || 'GET';
	args.dataType = args.dataType || 'json';

	var ajax = new XMLHttpRequest();
	var timeout = setTimeout(function() {
			console.log('error: timeout');
		ajax.abort();
		args.error(ajax, 'timeout');
	}, 10000);


	var queryString = '';
	for (var i in args.query) {
		queryString += (queryString.length == 0 ? '?' : '&') + i + '=' + args.query[i];
	}

	ajax.open(args.type, args.url + queryString, true);
	ajax.send(); //

	ajax.onreadystatechange = function() {
		if(ajax.readyState != 4) {
			console.log('readyState:' + ajax.readyState);
			return;
		}

		clearTimeout(timeout);

		if((ajax.status == 200) || (ajax.status == 0)) {
			console.log('success');
			//var parsedObject = args.dataType == 'json' ? JSON.parse(ajax.responseText) : ajax.responseText;
			var parsedObject = [
			    {
			        "conferenceID": "1",
			        "title": "PHP conference 111",
			        "date": "2013-04-14"
			    },
			    {
			        "conferenceID": "2",
			        "title": "Drupal conference",
			        "date": "2013-04-16"
			    }
			];

			console.log('parsed:', parsedObject);
			args.success(parsedObject, 'ok', ajax);
		} else {
			console.log('error:error');
			args.error(ajax, 'error');
		}
	}

	return ajax;
}*/