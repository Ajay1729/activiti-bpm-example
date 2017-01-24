(function() {
    'use strict';

    angular
        .module('bpm_app',
                ['ui.router', 'LocalStorageModule'])
        .run(run);


      run.$inject = ['$rootScope', '$state', '$http', 'AuthService'];
      function run($rootScope, $state, $http, AuthService) {

           //TODO 1 - interceptor
           //TODO 2 - request /me on refresh and save to local if any
            var token = AuthService.getFromStorage("token");
            if(token === null || token === undefined){
                AuthService.clearStorage();
                $state.go('login');
            }else{
                AuthService.setHeader();
                AuthService.me(
                            function(res){
                               $rootScope.user = res.data;
                            },
                            function(res){

                            });
            }

      }

 })();