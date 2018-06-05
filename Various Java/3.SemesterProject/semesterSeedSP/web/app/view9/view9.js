'use strict';

angular.module('myApp.view9', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view9', {
                    templateUrl: 'app/view9/view9.html',
                    controller: 'View9Ctrl'
                });
            }])

        .controller('View9Ctrl', function ($http, $scope, FlightDetailFactory) {

            var self = this;

            self.flightDetail = FlightDetailFactory.getFlightDetail();
            var date = new Date(self.flightDetail.Date);
            var jsDate = JSON.stringify(date);
//    var jsDate2 = jsDate.format("dd-MM-yyyy hh:mm");
            self.transformedDate = new Date(self.flightDetail.Date).toDateString();


        });

