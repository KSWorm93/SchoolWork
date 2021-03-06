/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';

angular.module('myApp.view5', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view5', {
    templateUrl: 'view5/view5.html',
    controller: 'View5Ctrl'
  });
}])

.controller('View5Ctrl', function($http) {
    
    var self = this;
    
    self.users = getUsers();
    
    function getUsers() {
        $http.get('api/demoadmin/users')
        .success(function (data) {
            self.users = data;
            
            console.log(data);
        })
        .error(function (data) {

        });
    };
    
    self.deleteUser = function(id) {
        $http.delete('api/demoadmin/user/' + id)
        .success(function (data) {
            console.log(data);
        })
        .error(function (data) {
            console.log(data);
        });
    };
});

