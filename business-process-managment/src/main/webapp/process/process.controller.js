(function() {
    'use strict';

    angular
        .module('bpm_app')
        .controller('ProcessController', ProcessController);

    ProcessController.$inject = ['$scope', '$state', 'AuthService', '$rootScope', 'ProcessService', '$stateParams'];

    function ProcessController($scope, $state, AuthService, $rootScope, ProcessService, $stateParams) {

        $scope.type = $stateParams.type; // my process def or my instances

        $scope.processes = [];      // all process def that user can start
        $scope.currentForm = [];    // start form for current selected process def
        $scope.currentProcessId;   // current selected process def

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

            ProcessService.my(
                function(res){
                    $scope.processes = res.data;
                },
                function(res){
                    //error
                }
            );

            //reset fields
            $scope.currentForm = [];
            $scope.currentProcessId = undefined;

        }

        getProcesses();

        $scope.showProcessDetail = function(processId){

            //set currnet process id
            $scope.currentProcessId = processId;

            ProcessService.getStartForm(
                processId,
                function(res){
                    $scope.currentForm = res.data;
                    console.log(res.data);
                    //add dropdown menu if needed
                    for(var i=0; i<$scope.currentForm.length; i++){
                        checkIfPropertyIsDropdownForSelectingListItem(i);
                    }
                },
                function(res){
                    //fail
                }
             );

        }

        $scope.showProcessInstances = function(processId){

            //set currnet process
            $scope.currentProcessId = processId;

            ProcessService.instances(
                processId,
                function(res){
                    $scope.processInstances = res.data;
                },
                function(res){
                    //error
                }
             );

        }


        $scope.start = function(){
            //transform form params
            var o = transform();
            ProcessService.startProcess(
                o,
                function(res){
                    //good
                },
                function(res){
                    //error
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
            obj.id=$scope.currentProcessId+"";
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
        var checkIfPropertyIsDropdownForSelectingListItem = function(i){
            var TEMPLATE_DIVIDER = "_groups_list_";
            var id = $scope.currentForm[i].id
            if(id.includes(TEMPLATE_DIVIDER)){
                var idx = i;
                var searchFor = id.split(TEMPLATE_DIVIDER)[0];
                $scope.currentForm[i].listId = id.split(TEMPLATE_DIVIDER)[0];
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
})();
