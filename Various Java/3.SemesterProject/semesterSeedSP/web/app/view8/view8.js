/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';

angular.module('myApp.view8', ['ngRoute'])

.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/view8', {
            templateUrl: 'app/view8/view8.html',
            controller: 'View8Ctrl'
        });
    }])

.controller('View8Ctrl', function ($http, $scope, $location, FlightDetailFactory) {

    var self = this;

    self.reservations = getUserReservation();

    function getUserReservation() {
        $http.get('api/flightreservation/reservations/')
                .success(function (data) {
                    self.reservations = data;

                    console.log(data);
                })
                .error(function (data) {
                    console.log(data);
                });
    };
    
    self.goToDetail = function(reservation) {
        FlightDetailFactory.setFlightDetail(reservation);
    };

}); 

