/*global module */
/*jshint unused: false */

module.exports = function(grunt) {
    "use strict";

    var appPath = 'client/assets/www/',
        jsPath = appPath + 'js/',
        cssPath = appPath + 'css/',
        libJsPath = jsPath + 'lib/',
        outputPath = appPath + 'output/';


    grunt.initConfig({

        pkg: grunt.file.readJSON('package.json'),

        banner: '/*! <%= pkg.title || pkg.name %> - v<%= pkg.version %> - ' +
        '<%= grunt.template.today("yyyy-mm-dd") %>\n' +
        '* Copyright (c) <%= grunt.template.today("yyyy") %> <%= pkg.author %> */\n\n',

        jshint: {
        },

        concat: {
            options: {
                banner: '<%= banner %>'
            },
            js: {
                src: [ 
                    appPath + 'cordova-2.5.0.js',
                    jsPath + 'index.js',
                    libJsPath + 'jquery-1.9.1.js',
                    libJsPath + 'jquery-mobile-1.3.0.js',
                    libJsPath + 'underscore-min.js',
                    libJsPath + 'backbone.js',
                    jsPath + 'custom-ajax.js',
                    jsPath + 'events.js',
                    jsPath + 'models.js',
                    jsPath + 'views.js',
                    jsPath + 'run.js'
                ],
                dest: jsPath + 'conferer.js'
            }
        },

        uglify: {
            biuld: {
                src: jsPath + 'conferer.js',
                dest: outputPath + 'conferer.min.js'
            }
        },

        sass: {
            dist: {
                options: {
                    compass: true,
                    style: 'compressed'
                },
                files: {
                    'client/assets/www/output/conferer.min.css': [
                        'client/assets/www/css/index.scss'
                    ]
                }
            }
        },

        watch: {
            js: {
                files: jsPath + '**/*.js',
                tasks: ['watchJs']
            },
            css: {
                files: jsPath + '**/*.js',
                tasks: ['watchCss']
            }
        }        

    });

    //grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-sass');
    //grunt.loadNpmTasks('grunt-contrib-watch');



/*
    // custom tasks
    grunt.registerTask('bookmarklet', ['jshint', 'sass:Bookmarklet', 'concat:Bookmarklet']);
    grunt.registerTask('default', ['jshint', 'concat:PublicModules', 'concat:AdminModules', 'uglify:Public', 'uglify:Admin', 'uglify:PublicModules', 'uglify:AdminModules', 'uglify:Validate']);
    grunt.registerTask('all', ['sass', 'jshint', 'concat', 'uglify']);
    grunt.registerTask('libs', ['concat:PublicLibs', 'concat:AdminLibs', 'uglify:PublicLibs', 'uglify:AdminLibs']);
    grunt.registerTask('js', ['jshint', 'concat', 'uglify:Public', 'uglify:Admin']);
    grunt.registerTask('run', ['server', 'watch']);

*/

    grunt.registerTask('default', [/*'jshint', */ 'concat:js', 'uglify', 'sass']);

    // grunt.registerTask('watchJs', ['jshint', 'concat:js', 'uglify:Public', 'uglify:Admin']);
    // grunt.registerTask('watchCss', ['concat:js', 'cssmin:uglify:Public', 'uglify:Admin']);


};
