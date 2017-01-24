(function() {

   angular
       .module('bpm_app')
       .factory('AuthService', AuthService);

   AuthService.$inject = ['$http'];

   function AuthService($http){

        return{
            login: function(obj, onSuccess, onError){
                var req = {
                    method: 'POST',
                    url: '/api/user/login',
                    data: obj,
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
                $http(req).then(onSuccess, onError);
            },
            me: function(onSuccess, onError){
                var req = {
                    method: 'POST',
                    url: '/api/user/me',
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }
                $http(req).then(onSuccess, onError);
            },
            logout: function(){
                //todo - remove from local storage
            },
            saveToStorage(id, val){
                localStorage.setItem(id, val);
            },
            getFromStorage(something){
                return localStorage.getItem(something);
            },
            clearStorage(){
                localStorage.clear();
                $http.defaults.headers.common.Authorization = undefined;
            },
            setHeader(){
                $http.defaults.headers.common.Authorization = localStorage.getItem("token");;
            }
        }

   }

})();
