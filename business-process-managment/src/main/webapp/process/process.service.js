(function() {

   angular
       .module('bpm_app')
       .factory('ProcessService', ProcessService);

   ProcessService.$inject = ['$http'];

   function ProcessService($http){

        return{
            my: function(onSuccess, onError){
                var req = {
                    method: 'GET',
                    url: '/api/process/my',
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
                $http(req).then(onSuccess, onError);
            },
            getStartForm:function(processId, onSuccess, onError){
                var req = {
                    method: 'GET',
                    url: '/api/process/'+processId,
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
                $http(req).then(onSuccess, onError);
            },
            startProcess:function(obj, onSuccess, onError){
                var req = {
                    method: 'POST',
                    url: '/api/process/start',
                    data: obj,
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
             $http(req).then(onSuccess, onError);
            },
            instances:function(id, onSuccess, onError){
                var req = {
                    method: 'GET',
                    url: '/api/process/instances/'+id,
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
             $http(req).then(onSuccess, onError);
            }
        }

   }

})();
