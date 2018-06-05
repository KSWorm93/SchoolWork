'use strict';

/* Place your Global Services in this File */

// Demonstrate how to register services
angular.module('myApp.services', [])
  .service('InfoService', [function () {
    var info = "Hello World from a Service";
    this.getInfo = function(){return info;};
  }])
    
    /*
     * This service is for storing the information about the specific flight
     * that the user wants to make a reservation for.
     * 
     * The information should be set by view1 and collected by view5.
     */
    .service('FlightReservationService', [function() {
        var flight = {};
        var reserveInfo = {};
        
        this.setFlight = function(obj) {
            flight = obj;
        };
        this.getFlight = function() {
            return flight;
        };
        
        this.setReserveInfo = function(obj) {
            reserveInfo = obj;
        };
        this.getReserveInfo = function() {
            return reserveInfo;
        };
    }]);