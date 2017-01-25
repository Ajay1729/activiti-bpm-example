(function() {
    'use strict';

    angular
        .module('bpm_app')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$scope', '$state', 'AuthService', '$rootScope'];

    function LoginController($scope, $state, AuthService, $rootScope) {

        $scope.user = {};
        $scope.user.id="";
        $scope.user.password="";

        $scope.login = function(){
            AuthService.login(
                $scope.user,
                function(res){
                    AuthService.saveToStorage("token", res.data.token);
                    AuthService.setHeader();
                    AuthService.me(
                        function(res){
                            $rootScope.user = res.data.user;
                        },
                        function(res){

                        });
                       
                    $state.go("home");
                },
                function(res){
                    console.log(res);
                });
        }




    }
})();
