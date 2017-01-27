(function() {
    'use strict';

    angular
        .module('bpm_app')
        .controller('ProcessController', ProcessController);

    ProcessController.$inject = ['$scope', '$state', 'AuthService', '$rootScope', 'ProcessService', '$stateParams'];

    function ProcessController($scope, $state, AuthService, $rootScope, ProcessService, $stateParams) {

        $scope.type = $stateParams.type; // my or instances
        $scope.processes = []; //  list
        $scope.currentForm = []; // current selected statprocess form
        $scope.currentProcessKey; // current selected process id
        $scope.processInstances=[];

        var getProcesses = function(){

                //get process def that I can start
                ProcessService.my(
                    function(res){
                        $scope.processes = res.data;
                    },
                    function(res){
                    }
                );
                 $scope.currentForm = [];
                 $scope.currentProcessKey = undefined;

        }

        getProcesses();

        $scope.showProcessDetail = function(processId){
            $scope.currentProcessKey = processId;
            ProcessService.getStartForm(
                            processId,
                            function(res){
                                $scope.currentForm = res.data;
                            },
                            function(res){

                            }
             );

        }

        $scope.showProcessInstances = function(processId){
                    $scope.currentProcessKey = processId;
                    ProcessService.instances(
                                    processId,
                                    function(res){
                                        $scope.processInstances = res.data;
                                    },
                                    function(res){

                                    }
                     );

                }

        $scope.start = function(){
            var o = transform();
            console.log(o);
            ProcessService.startProcess(
                o,
                function(res){

                },
                function(res){

                });
        }


        //util function
        var transform = function(){
            var obj = {}
            obj.id=$scope.currentProcessKey+"";
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
