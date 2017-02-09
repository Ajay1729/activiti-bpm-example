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
            },
            involved:function(onSuccess, onError){
                var req = {
                    method: 'GET',
                    url: '/api/task/involved',
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
                $http(req).then(onSuccess, onError);
            },
            getGroupMembers:function(groupId, onSuccess, onError){
                var req = {
                    method: 'GET',
                    url: '/api/user/group/'+groupId,
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
                $http(req).then(onSuccess, onError);
            },
            claim:function(taskId, onSuccess, onError){
                var req = {
                    method: 'POST',
                    url: '/api/task/claim/'+taskId,
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
                $http(req).then(onSuccess, onError);
            },
            getUserById:function(userId, onSuccess, onError){
                var req = {
                    method: 'GET',
                    url: '/api/user/user/'+userId,
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
                $http(req).then(onSuccess, onError);
            },
            getList:function(listId){
                var faxList = [
                    {
                        name:"Katedra za informatiku",
                        id:"katedra_za_informatiku",
                        list:[
                            {
                            name:"E1",
                            id:"stud_program_e1"
                            },
                            {
                            name:"E2",
                            id:"stud_program_e2"
                            }
                        ]
                    },
                    {
                    name:"Katedra za mehaniku",
                    id:"katedra_za_mehaniku",
                    list:[
                        {
                        name:"x",
                        id:"stud_program_e1"
                        },
                        {
                        name:"y",
                        id:"stud_program_e2"
                        }
                    ]
                    }
                ];

                if(listId=="list1"){
                    return faxList;
                }else
                if(listId=='list2'){
                    var tmp = [];
                    for(var g=0; g<faxList.length; g++){
                        tmp = tmp.concat(faxList[g].list);
                    }
                    return tmp;
                }
            }

        }

   }

})();
