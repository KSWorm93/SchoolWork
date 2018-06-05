/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';

angular.module('myApp.view5', ['ngRoute'])

.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/view5', {
            templateUrl: 'app/view5/view5.html',
            controller: 'View5Ctrl'
        });
    }])
.controller('View5Ctrl', function ($rootScope, FlightReservationService, $http) {
    var self = this;
    
    $rootScope.isAuthenticated;
    
    self.success = null;
    self.error = null;
    self.errorMessage = "";
    self.successMessage = "";
    
    
    self.flightInfo = FlightReservationService.getFlight();
    self.reserveInfo = FlightReservationService.getReserveInfo();
    
    self.passengerAmount = function(num) {
        return new Array(num);
    };
    
    self.addPassenger = function(passengerObj) {
        self.passengers.push(passengerObj);
    };
    
    self.makeReservation = function () {
        var passengersFilled = false;
        
        if(self.reservee.phone === null || self.reservee.phone === '' || self.reservee.phone === undefined) {
            self.error = 1;
            self.errorMessage = "You need to fill out your phone number.";
        }
        if(self.reservee.email === null || self.reservee.email === '' || self.reservee.email === undefined) {
            self.error = 1;
            self.errorMessage = "You need to fill out your email.";
        }
        
        for(var i = 0; i < self.reserveInfo.passengers; i++) {
            
            if(self.reservee.passengers[i] !== undefined) {
                if(self.reservee.passengers[i].firstName === undefined || self.reservee.passengers[i].firstName === null || self.reservee.passengers[i].firstName === '' &&
                        self.reservee.passengers[i].lastName === undefined || self.reservee.passengers[i].lastName === null || self.reservee.passengers[i].lastName === '') {
                    self.error = 1;
                    self.errorMessage = "You need to fill out the information about the passengers.";
                } else {
                    passengersFilled = true;
                }
            } else {
                self.error = 1;
                self.errorMessage = "You need to fill out the information about the passengers.";
                passengersFilled = false;
            }
        }
        
        if(passengersFilled) {
            self.finalReservation = {};
            self.finalReservation.flightID = self.flightInfo.flightID;
            self.finalReservation.numberOfSeats = self.reserveInfo.passengers;
            self.finalReservation.ReserveeName = $rootScope.username;
            self.finalReservation.ReservePhone = self.reservee.phone;
            self.finalReservation.ReserveeEmail = self.reservee.email;
            self.finalReservation.Passengers = [];
            self.finalReservation.url = self.flightInfo.url;
            
            for(var i = 0; i < self.finalReservation.numberOfSeats; i++) {
                self.finalReservation.Passengers.push(self.reservee.passengers[i]);
            }
            
            $http.post('api/flightreservation', self.finalReservation)
                .success(function (data) {
                    self.success = 200;
                    self.successMessage = "Reservation Succesfull!";
                })
                .error(function (data) {
                    self.error = data.httpError;
                    self.errorMessage = data.message;
                });
        }
        

    };
});



