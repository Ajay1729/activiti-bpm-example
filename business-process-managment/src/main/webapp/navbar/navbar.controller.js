(function() {
    'use strict';

    angular
        .module('bpm_app')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$scope', '$state', 'AuthService', '$rootScope'];

    function NavbarController ($scope, $state, AuthService, $rootScope) {

        $scope.logout=function(){
            AuthService.clearStorage();
            $rootScope.user = undefined;
            $state.go("home");
        }

    }


})();
