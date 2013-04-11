
function LOG(text) {
    if($('#log')) {
        $('#log').append('<p>[' + new Date().toISOString() + ']: ' + text + '</p>');
    }
    console.log('-------------- ', text);
}

var coferer = {
    _isLoaded: false,
    
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

        var mainView = new conferer.proto.views.MainView({el: '#container'});
    }
};
