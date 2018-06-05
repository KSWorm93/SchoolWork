'use strict';

angular.module('myApp.view4', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view4', {
    templateUrl: 'view4/view4.html'
  });
}])

.controller('View4Ctrl', function($http) {
    var self = this;
    
    self.dato = formattedDate(new Date());
    
    function formattedDate(date) {
        var d = new Date(date || Date.now()),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;

        return [month, day, year].join('/');
    }
    
    self.currencyRates = getCurrencies();
    
    function getCurrencies() {
        $http.get('api/currency/dailyrates')
        .success(function(data) {
            self.currencyRates = data;
        });
    };
    
    self.calculate = function() {
        $http.post('api/currency/calculate/' + self.fromCurrency.amount + '/' + self.fromCurrency.currency + '/' + self.toCurrency.currency)
        .success(function (data) {
            $('#toCurrency_result p').text(data);
        });
    };
});