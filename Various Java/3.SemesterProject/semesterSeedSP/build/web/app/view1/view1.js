'use strict';

angular.module('myApp.view1', ['ngRoute'])

.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/view1', {
        templateUrl: 'app/view1/view1.html',
        controller: 'View1Ctrl',
        controllerAs: 'ctrl'
    });
}])


/*
 * Factories
 */
.factory('FlightFactory', function($http) {
    var getFlights = function(search) {
        var day = search.dateStart.getDate();
        var month = search.dateStart.getMonth();
        var year = search.dateStart.getFullYear();
        var myDate = new Date(year, month, day,1);
        var dateStart = myDate.toISOString();
            return $http.get('api/flightinfo/external/' + search.origin + '/' + search.destination + '/' + dateStart + '/' + search.passengers);
    };
    
    var searchFlights = function(search) {
        var day = search.dateStart.getDate();
        var month = search.dateStart.getMonth();
        var year = search.dateStart.getFullYear();
        var myDate = new Date(year, month, day,1);
        var dateStart = myDate.toISOString();
            return $http.get('api/flightinfo/external/' + search.origin + '/' + dateStart + '/' + search.passengers);
    };
    
    return {
        getFlights: getFlights,
        searchFlights: searchFlights
    };
})

.factory('CountryFactory', function($http) {
    var getCountries = function() {
        return $http.get('https://restcountries.eu/rest/v1/all');
    };
    
    var getSpecificCountry = function() {
        
    };
    
    
    
    return {
        getCountries: getCountries,
        getSpecificCountry: getSpecificCountry
    };
})

/*
 * Controllers
 */
.controller('View1Ctrl', ["InfoFactory", "InfoService", 'FlightReservationService', "FlightFactory", "CountryFactory", function (InfoFactory, InfoService, FlightReservationService, FlightFactory, CountryFactory, $location) {
    this.msgFromFactory = InfoFactory.getInfo();
    this.msgFromService = InfoService.getInfo();
    
    // Setting scope.
    var self = this;
    
    // Creating error variables.
    self.error = null;
    self.errorMessage = "";
    
    // Array variables
    self.resultFlights = {};
    
    // Search for flight
    self.searchFlight = function() {
        self.error = null;
        self.errorMessage = "";
        
        if(self.search.origin === "" ||
            self.search.origin === undefined ||
            self.search.dateStart === undefined ||
            self.search.passengers === undefined) {
                self.error = 1;
                self.errorMessage = "You have to enter valid data.";
        } else {
            
            if(self.search.origin.length > 3) {
                var arr = self.search.origin.split('(');
                var str = arr[1].split(')');
                
                self.search.origin = str[0];
            }
        
            // Search with a specific destination.
            if(self.search.destination !== undefined) {
                if(self.search.destination.length > 3) {
                    var arr = self.search.destination.split('(');
                    var str = arr[1].split(')');
                    
                    self.search.destination = str[0];
                }
                
                FlightFactory.getFlights(self.search).then(function(res) {
                    self.resultFlights = res.data;
                }, function(res) {
                    self.error = res.data.httpError;
                    self.errorMessage = res.data.message;
                });
            } 
            // Search without a specific destination
            else {
                
                FlightFactory.searchFlights(self.search).then(function(res) {
                    self.resultFlights = res.data;
                }, function(res) {
                    self.error = res.data.httpError;
                    self.errorMessage = res.data.message;
                });
            }
        }
    };
    
    self.orderFlight = function(flight) {
        FlightReservationService.setFlight(flight);
        FlightReservationService.setReserveInfo(self.search);
    };
    
    /*
     * AIRPORT SECTION
     */
    self.airports = [
                { airportCode: 'BCN', city : 'Barcelona', airportName : 'Barcelona International' },
                { airportCode: 'CDG', city: 'Paris', airportName: 'Charles de Gaulle International' },
                { airportCode: 'CPH', city: 'Copenhagen', airportName: 'Copenhagen Kastrup' },
                { airportCode: 'STN', city: 'London', airportName: 'London Stansted' },
                { airportCode: 'SXF', city: 'Berlin', airportName: 'Berlin-Schönefeld International' },
                { airportCode: 'TXL', city: 'Berlin', airportName: 'Berlin-Tegel Airport' },
                { airportCode: 'MAD', city: 'Madrid', airportName: 'Madrid-Barajas Airport' },
                { airportCode: 'LHR', city: 'London', airportName: 'London Heathrow'}
            ];
            
    self.chooseSuggestAirport = function(city, airportCode) {
        self.search.origin = city + ' (' + airportCode + ')';
    };
    self.chooseSuggestDestinationAirport = function(city, airportCode) {
        self.search.destination = city + ' (' + airportCode + ')';
    };
    
}]);