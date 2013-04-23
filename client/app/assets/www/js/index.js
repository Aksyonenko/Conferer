
function LOG(text) {
    if($('#log')) {
        $('#log').append('<p>[' + new Date().toISOString() + ']: ' + text + '</p>');
    }
    console.log('-------------- ', text);
}

Date.prototype.getDateParams = function() {
    return {
        day: this.getDate(),
        month: this.getMonth() + 1,
        year: this.getFullYear()
    }
};

var monthNames = {
    '1' : {'short': 'JAN', 'full': 'January'},
    '2' : {'short': 'FEB', 'full': 'February'},
    '3' : {'short': 'MAR', 'full': 'March'},
    '4' : {'short': 'APR', 'full': 'April'},
    '5' : {'short': 'MAY', 'full': 'May'},
    '6' : {'short': 'JUN', 'full': 'June'},
    '7' : {'short': 'JUL', 'full': 'July'},
    '8' : {'short': 'AUG', 'full': 'August'},
    '9' : {'short': 'SEP', 'full': 'September'},
    '10' : {'short': 'OCT', 'full': 'October'},
    '11' : {'short': 'NOV', 'full': 'November'},
    '12' : {'short': 'DEC', 'full': 'December'}
}

function GetMonthName(month, isFull) {
    if (month < 1) { month = 12 + month; }
    if (month > 12) { month = month % 12 }

    return isFull ? monthNames[month].full : monthNames[month].short;
}

var coferer = {
    _isLoaded: false,
    mainView: null,

    containerSelectors: {
        headerGlobal: '#header-global',
        mainView: '#content' //this.containerSelectors.mainView
        // ...
    },


    // getSelector: function(params) {}

    // Application Constructor
    initialize: function() {
        if (this._isLoaded) {
            return;
        }

        var version = '0.1.20';
        // LOG('version: ' + version);

        this.bindEvents();
    },

    bindEvents: function() {
        document.addEventListener('deviceready', this.onDeviceReady, false); // phonegap
        // $(document).on('mobileinit', this.onDeviceReady); // jquerymobile
        $(document).ready(this.onDeviceReady); // jquery event
    },

    onDeviceReady: function() {
        if (this._isLoaded) {
            return;
        }

        LOG('index.js onDeviceReady');
        this._isLoaded = true;

        // cross domains requests
        $.support.cors = true;

        this.mainView = new conferer.proto.views.MainView({el: '#container'});
    }
};
