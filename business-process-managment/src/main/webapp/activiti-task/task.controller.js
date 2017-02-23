(function() {
    'use strict';

    angular
        .module('bpm_app')
        .controller('TaskController', TaskController);

    TaskController.$inject = ['$scope', '$state', 'AuthService', '$rootScope', 'TaskService', '$stateParams', 'FormService'];

    function TaskController($scope, $state, AuthService, $rootScope, TaskService, $stateParams, FormService) {

        $scope.type = $stateParams.type; // my or involved tasks

        $scope.tasks = [];          // all tasks list
        $scope.currentForm = [];    // task form for current selected task
        $scope.currentTaskId = undefined;       // current selected task id


        var getTasks = function(){

            if($scope.type==='my'){
                TaskService.myTasks(
                    function(res){
                        $scope.tasks = res.data;
                    },
                    function(res){
                        //error
                    }
                );
            }else
            if($scope.type==='involved'){
                TaskService.involvedTasks(
                    function(res){
                        $scope.tasks = res.data;
                    },
                    function(res){
                        //error
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

            TaskService.getTaskForm(
                taskId,
                function(res){
                    $scope.currentForm = res.data;
                    //console.log(res.data);
                    //add dropdown menu if needed
                    for(var i=0; i<$scope.currentForm.length; i++){
                        checkIfPropertyIsDropdownForSelectingGroupMember(i);
                    }
                },
                function(res){
                    //error
                }
             );

        }

        /*EXECUTE TASK*/
        $scope.complete = function(){
            var o = transform();
            TaskService.completeTask(
                o,
                function(res){
                    getTasks();
                    alertify.success('Success!');
                },
                function(res){
                    //error
                    alertify.error('Error!');
                });
        }

        /*CLAIM TASK*/
        $scope.claim = function(){
            TaskService.claimTask(
                $scope.currentTaskId,
                function(res){
                    getTasks();
                    alertify.success('Success!');
                },
                function(res){
                    //error
                    alertify.error('Error!');
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

        //util
        var checkIfPropertyIsDropdownForSelectingGroupMember = function(i){
            //dropdown id template: '<groupId>_group_member_<formPropertyId>'
            var TEMPLATE_DIVIDER = "_group_member_";
            var id = $scope.currentForm[i].id;
            if(id.includes(TEMPLATE_DIVIDER)){
                var idx = i;
                $scope.currentForm[i].groupId = id.split(TEMPLATE_DIVIDER)[0];
                TaskService.getGroupMembers(
                    $scope.currentForm[i].groupId,
                    function(res){
                        $scope.currentForm[idx].members = [];
                        $scope.currentForm[idx].members=res.data;
                        if($scope.currentForm[idx].value){
                            TaskService.getUserById(
                            $scope.currentForm[idx].value,
                            function(res){
                                $scope.currentForm[idx].selectedUserName = res.data.firstName+" "+res.data.lastName;
                            },
                            function(res){
                                //error
                            }
                            );
                        }
                    },
                    function(res){
                        //error
                    }
                );
            }else
            {
                $scope.currentForm[i].groupId = false;
            }
        }


    }
})();
