(function() {
    'use strict';

    angular
        .module('bpm_app',
                ['ui.router'])
        .run(run);


      run.$inject = ['$rootScope', '$state', '$http', 'AuthService'];
      function run($rootScope, $state, $http, AuthService) {

            var token = AuthService.getFromStorage("token");

            if(token === null || token === undefined){
                AuthService.clearStorage();
                $state.go('login');
            }else{
                AuthService.setHeader();
                AuthService.me(
                            function(res){
                               $rootScope.user = res.data.user;
                            },
                            function(res){

                            });
            }

      }

 })();