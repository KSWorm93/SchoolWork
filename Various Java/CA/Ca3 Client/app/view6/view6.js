/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';

angular.module('myApp.view6', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view6', {
                    templateUrl: 'view6/view6.html',
                    controller: 'View6Ctrl'
                });
            }])

        .controller('View6Ctrl', function ($http) {
            var self = this;

            self.addUser = function () {
                $http.post('api/register', self.user)
                        .success(function (data) {
                            $('#registerAlertSucces').addClass('alert alert-success');
                            $('#registerAlertSucces').text("Your user has been created!");
                            console.log(data);
                        });
            };
        });