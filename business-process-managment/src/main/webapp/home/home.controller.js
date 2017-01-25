(function() {
    'use strict';

    angular
        .module('bpm_app')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', '$state'];

    function HomeController ($scope, $state) {

        $scope.start = function(){
            $state.go('process');
        }


    }

})();
