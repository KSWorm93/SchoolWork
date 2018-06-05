/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';

angular.module('myApp.view7', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view7', {
                    templateUrl: 'app/view7/view7.html',
                    controller: 'View7Ctrl'
                });
            }])

        .controller('View7Ctrl', function ($http, $scope, $location, FlightDetailFactory) {

            var self = this;

            self.reservations = getUserReservation();

            function getUserReservation() {
                $http.get('api/flightreservation/reservations/' + $scope.username)
                        .success(function (data) {
                            self.reservations = data;
                        })
                        .error(function (data) {
                        });
            }
            ;

            self.goToDetail = function (reservation) {
                FlightDetailFactory.setFlightDetail(reservation);
            };

        }); 