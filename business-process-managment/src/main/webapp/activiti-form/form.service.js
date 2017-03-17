(function() {

   angular
       .module('bpm_app')
       .factory('FormService', FormService);

   FormService.$inject = ['$http', 'TaskService'];

   function FormService($http, TaskService){

        return{
            checkIfPropertyIsDropdownForSelectingListItem: function(formProperty, doThis){
//TODO
//                //dropdown id template: '<listid>_group_list_<formPropertyId>'
//                var TEMPLATE_DIVIDER = "_groups_list_";
//                var id = formProperty.id
//                if(id.includes(TEMPLATE_DIVIDER)){
//                    var searchFor = id.split(TEMPLATE_DIVIDER)[0];
//                    var retVal = {};
//                    retVal.listId = id.split(TEMPLATE_DIVIDER)[0];
//                    retVal.list = TaskService.getList(retVal.listId);
//                    console.log("RET VAL:");console.log(retVal);
//                    return retVal;
//                }else{
//                    return null;
//                }

            },
            checkIfPropertyIsDropdownForSelectingGroupMember: function(formProperty, doThis){
//TODO
//                    //dropdown id template: '<groupId>_group_member_<formPropertyId>'
//                    var TEMPLATE_DIVIDER = "_group_member_";
//                    var id = formProperty.id;
//                    var retVal = {};
//                    if(id.includes(TEMPLATE_DIVIDER)){
//                        retVal.groupId = id.split(TEMPLATE_DIVIDER)[0];
//                        TaskService.getGroupMembers(
//                            retVal.groupId,
//                            function(res){
//                                retVal.members = [];
//                                retVal.members = res.data;
//                                if(formProperty.value){
//                                    TaskService.getUserById(
//                                        formProperty.value,
//                                        function(res){
//                                           retVal.selectedUserName = res.data.firstName+" "+res.data.lastName;
//                                        },
//                                        function(res){
//                                            //error
//                                        }
//                                    );
//                                }
//                            },
//                            function(res){
//                                //error
//                            }
//                        );
//                    }else
//                    {
//                        retVal.groupId = false;
//                    }
//                    return retVal;
            }

        }

   }

})();
