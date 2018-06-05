'use strict';

angular.module('myApp.view3', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view3', {
    templateUrl: 'app/view3/view3.html',
    controller: 'View3Ctrl'
  });
}])

.controller('View3Ctrl', function($http, $scope, $rootScope) {
  
    var self = this;
    
    self.editUser = function() {
        self.user.userName = $rootScope.username;
        
        $http.put('api/userSettings/editUserInfo', self.user)
            .success(function(res) {
                    console.log(res);
            }).error(function(res) {
                console.log(res);
        });
    };
});