(function() {

   angular
       .module('bpm_app')
       .factory('TaskService', TaskService);

   TaskService.$inject = ['$http'];

   function TaskService($http){

        return{
            my: function(onSuccess, onError){
                var req = {
                    method: 'GET',
                    url: '/api/task/mytasks',
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
                $http(req).then(onSuccess, onError);
            },
            getTaskForm:function(taskId, onSuccess, onError){
                var req = {
                    method: 'GET',
                    url: '/api/task/'+taskId,
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
                $http(req).then(onSuccess, onError);
            },
            completeTask:function(obj, onSuccess, onError){
                var req = {
                    method: 'POST',
                    url: '/api/task/execute',
                    data: obj,
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
             $http(req).then(onSuccess, onError);
            }
        }

   }

})();
