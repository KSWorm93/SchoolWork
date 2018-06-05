/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';

angular.module('myApp.view6', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view6', {
                    templateUrl: 'app/view6/view6.html',
                    controller: 'View6Ctrl'
                });
            }])





        .controller('View6Ctrl', function ($http, $location, $timeout) {
            var self = this;

            self.success = null;
            self.error = null;
            self.errorMessage = "";
            self.successMessage = "";

            self.addUser = function () {
                $http.post('api/register', self.user)
                        .success(function (data) {
                            console.log(data);
                            self.success = 200;
                            self.successMessage = "Your user has been created!" + "\n" + "You will be redirected to the login page in a few seconds";

                            //Will set a short delay before automatically redirecting
                            $timeout(function () {
                                window.location = "#view4"
                                //This did not work i dunno why??
                                //$location.url("#/view4");
                            }, 3000);
                        })
                        .error(function (data) {
                            console.log(data);
                            self.error = data.httpError;
                            self.errorMessage = data.message;
                        });
            };
        });