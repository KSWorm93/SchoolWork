'use strict';

angular.module('myApp.view3', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view3', {
    templateUrl: 'view3/view3.html',
    controller: 'View3Ctrl'
  });
}])

.controller('View3Ctrl', function($http) {
    var self = this;
    
    self.company = {};
    self.search = function() {
        $http.post('api/cvrSearch/getcomp/' + self.search.option + '/' + self.search.text + '/' + self.search.country)
        .success(function (data) {
            
            self.company = data;
            
            console.log(data);
            if(data.error != null) {
                $('#view3Alert').addClass('alert alert-danger');
                $('#view3Alert').text(data.message);
            }
        });
    };        
});