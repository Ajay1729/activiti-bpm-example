(function() {
    'use strict';

    angular
        .module('bpm_app')
        .controller('ProcessController', ProcessController);

    ProcessController.$inject = ['$scope', '$state', 'AuthService', '$rootScope', 'ProcessService', '$stateParams'];

    function ProcessController($scope, $state, AuthService, $rootScope, ProcessService, $stateParams) {

        $scope.type = $stateParams.type; // my or instances

        $scope.processes = [];      // list of process def that user can start
        $scope.currentForm = [];    // start form for current selected process def
        $scope.currentProcessKey;   // current selected process def
        $scope.processInstances=[]; // process instances for current selected process

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

        var getProcesses = function(){

            //get process def that user can start
            ProcessService.my(
                function(res){
                    $scope.processes = res.data;
                },
                function(res){
                }
            );

            //reset
            $scope.currentForm = [];
            $scope.currentProcessKey = undefined;

        }

        getProcesses();

        $scope.showProcessDetail = function(processId){

            //set currnet process id
            $scope.currentProcessKey = processId;

            //get start form
            ProcessService.getStartForm(
                processId,
                function(res){

                    $scope.currentForm = res.data;

                    //dropdown
                    if($scope.currentForm.length!=0){
                        for(var i=0; i<$scope.currentForm.length; i++){
                            var id = $scope.currentForm[i].id
                            if(id.includes("_groups_list_")){
                                var idx = i;
                                var searchFor = id.split("_groups_list_")[0];
                                $scope.currentForm[i].listId = id.split("_groups_list_")[0];
                                if($scope.currentForm[i].listId=="list1"){
                                    $scope.currentForm[idx].list = faxList;
                                }else{
                                    var tmp = [];
                                    for(var g=0; g<faxList.length; g++){
                                        tmp = tmp.concat(faxList[g].list);
                                    }
                                    $scope.currentForm[idx].list = tmp;
                                }
                            }
                        }
                    }
                },
                function(res){
                    //fail
                }
             );

        }

        $scope.showProcessInstances = function(processId){

                //set currnet process
                $scope.currentProcessKey = processId;

                //get process instances
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
            //transform form params
            var o = transform();
            console.log(o);
            ProcessService.startProcess(
                o,
                function(res){

                },
                function(res){

                });
        }


        //util
        $scope.itemSelected = function(propertyId, item){
            //set value to user field
            for(var i = 0; i<$scope.currentForm.length; i++){
                if(propertyId===$scope.currentForm[i].id){
                    $scope.currentForm[i].value = item.id;
                    $scope.currentForm[i].selectedItemName = item.name;
                }
            }
        }

        //util
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

        //util
        var checkIfPropertyIsDropbox(property){

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
