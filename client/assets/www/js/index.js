/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

var isLoaded = false;

function LOG(text) {
    if($('#log')) {
        $('#log').append('<p>[' + new Date().toISOString() + ']: ' + text + '</p>');    
    }
}

var app = {
    _isLoaded: false,

    // Application Constructor
    initialize: function() {
        if (isLoaded) {
            return;
        }

        var version = '0.1.19';
        LOG('version: ' + version);

        this.bindEvents();
    },

    bindEvents: function() {
        document.addEventListener('deviceready', this.onDeviceReady, false);
        $(document).on('mobileinit', this.onDeviceReady);
        $(document).ready(this.onDeviceReady);
    },

    onDeviceReady: function() {
        if (isLoaded) {
            return;
        }

        LOG('<p>[' + new Date().toISOString() + '] app.onDeviceReady()</p>');
        isLoaded = true;

        //$.mobile.allowCrossDomainPages = true;
        //$.mobile.hashListeningEnabled = false;
        //$.mobile.linkBindingEnabled = false;
        //$.mobile.ajaxLinksEnabled = false;

        $.support.cors = true;

        var mainView = new conferer.proto.views.MainView({el: '#container'});


/*

$('#page_id).page('destroy').page();
$("div[data-role='page']").page('destroy').page();
w
*/



    }
};
