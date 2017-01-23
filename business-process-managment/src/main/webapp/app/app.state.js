 (function() {
     'use strict';

     angular
         .module('bpm_app')
         .config(stateConfig);

        stateConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

        function stateConfig($stateProvider, $urlRouterProvider, $translateProvider) {


        $urlRouterProvider.otherwise('/home');

         $stateProvider
           .state('home', {
                   url: '/home',
                   views: {
                       'content': {
                           templateUrl: 'home/home.html',
                           controller: 'HomeController'
                       },
                       'navbar':{
                           templateUrl: 'navbar/navbar.html',
                           controller: 'NavbarController'
                       }
                   }
               });

    }


    })();