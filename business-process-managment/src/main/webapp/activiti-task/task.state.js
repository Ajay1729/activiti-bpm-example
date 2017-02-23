(function() {
'use strict';

angular
    .module('bpm_app')
    .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

    function stateConfig($stateProvider, $urlRouterProvider) {

         $stateProvider
           .state('task', {
                   url: '/tasks/:type',
                   views: {
                       'content@': {
                           templateUrl: 'activiti-task/task.html',
                           controller: 'TaskController'
                       },
                       'navbar':{
                           templateUrl: 'navbar/navbar.html',
                           controller: 'NavbarController'
                       }
                   }
               })
    }

 })();