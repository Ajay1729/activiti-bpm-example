(function() {
    'use strict';

    angular
        .module('bpm_app')
        .controller('TaskController', TaskController);

    TaskController.$inject = ['$scope', '$state', 'AuthService', '$rootScope', 'TaskService', '$stateParams'];

    function TaskController($scope, $state, AuthService, $rootScope, TaskService, $stateParams) {

        $scope.type = $stateParams.type; // my or involved

        $scope.tasks = [];          // tasks list
        $scope.currentForm = [];    // task form for current selected task
        $scope.currentTaskId;       // current selected task id


        var getTasks = function(){

            if($scope.type==='my'){
                //get my tasks
                TaskService.my(
                    function(res){
                        $scope.tasks = res.data;
                    },
                    function(res){
                    }
                );
            }else
            if($scope.type==='involved'){
                //get tasks im involved in
                TaskService.involved(
                    function(res){
                        $scope.tasks = res.data;
                    },
                    function(res){

                    }
                );
            }

             //reset fields
             $scope.currentForm = [];
             $scope.currentTaskId = undefined;

        }

        getTasks();

        $scope.showTaskDetail = function(taskId){

            //save current task id
            $scope.currentTaskId = taskId;

            //get task form
            TaskService.getTaskForm(
                taskId,
                function(res){

                    //form properties
                    $scope.currentForm = res.data;

                    //add dropdown menu items for property if needed
                    if($scope.currentForm.length!=0){
                        for(var i=0; i<$scope.currentForm.length; i++){
                            //dropdown id for form property template: '<groupId>_group_member_<formPropertyId>'
                            var id = $scope.currentForm[i].id;
                            if(id.includes("_group_member_")){
                                var idx = i;
                                $scope.currentForm[i].groupId = id.split("_group_member_")[0];
                                TaskService.usersByGroup(
                                    $scope.currentForm[i].groupId,
                                    function(res){
                                        //$scope.currentForm[i].selectedUserName = "Pick User...";
                                        $scope.currentForm[idx].members = [];
                                        $scope.currentForm[idx].members=res.data;
                                    },
                                    function(res){

                                    }
                                );

                            }else
                            {
                                $scope.currentForm[i].groupId = false;

                                //test
                                //$scope.currentForm[i].selectedUserName = "Pick User...";
                                //$scope.currentForm[0].groupId="asdf";
                                //$scope.currentForm[1].groupId="asdsasaf";
                                //$scope.currentForm[0].members=[{firstName:"neko", id:1}];
                                //$scope.currentForm[1].members=[{firstName:"nekosadsad", id:2}];

                            }
                        }
                    }
                },
                function(res){

                }
             );


        }

        //util
        $scope.userSelected = function(propertyId, user){
            //set value to user field
            for(var i = 0; i<$scope.currentForm.length; i++){
                if(propertyId===$scope.currentForm[i].id){
                    $scope.currentForm[i].value = user.id;
                    $scope.currentForm[i].selectedUserName = user.firstName+" "+user.lastName;
                }
            }
        }

        /*EXECUTE TASK*/
        $scope.complete = function(){
            //complete task
            var o = transform();
            console.log(o);
            TaskService.completeTask(
                o,
                function(res){
                    getTasks();
                },
                function(res){

                });
        }

        /*CLAIM TASK*/
        $scope.claim = function(){
            alert($scope.currentTaskId);
            TaskService.claim(
            $scope.currentTaskId,
            function(res){
                getTasks();
            },
            function(res){

            }
            );

        }

        //util
        var transform = function(){
            var obj = {}
            obj.id=$scope.currentTaskId+"";
            obj.formProperties=[];
            for(var i=0; i<$scope.currentForm.length; i++){
                var tmp={};
                tmp.id=$scope.currentForm[i].id;
                tmp.value=$scope.currentForm[i].value;
                obj.formProperties.push(tmp);
            }
            return obj;
        }


        //////////////////////////////////////////////////
        //          formProperties example              //
        //////////////////////////////////////////////////
       /*
        [
          {
            "id": "text",
            "name": "TEXT",
            "type": {
              "name": "string",
              "mimeType": "text/plain"
            },
            "value": null,
            "readable": true,
            "required": false,
            "writable": true
          },
          {
            "id": "new_property_1",
            "name": "DA NE",
            "type": {
              "name": "boolean",
              "mimeType": "plain/text"
            },
            "value": "true",
            "readable": true,
            "required": false,
            "writable": true
          }
        ]
        */

        //////////////////////////////////////////////////

    }
})();
