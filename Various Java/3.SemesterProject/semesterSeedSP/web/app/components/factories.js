'use strict';

/* Place your global Factory-service in this file */

angular.module('myApp.factories', []).
    factory('InfoFactory', function () {
        var info = "Hello World from a Factory";
        var getInfo = function getInfo() {
            return info;
        };
        return {
            getInfo: getInfo
        };
    })

    .factory('FlightDetailFactory', function () {
        var flightDetail = {};

        function setFlightDetail(obj) {
            flightDetail = obj;
        }
        
        function getFlightDetail() {
            return flightDetail;
        }
        
        return {
            setFlightDetail: setFlightDetail,
            getFlightDetail: getFlightDetail
        };
    });
