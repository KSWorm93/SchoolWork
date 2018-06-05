/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


'use strict';

describe('myApp.view6 View6Ctrl', function () {
    var scope, httpBackendMock, ctrl;
    var testResponse = {message: "Test Message Mock"};

    beforeEach(module('myApp.view6'));

    beforeEach(inject(function ($httpBackend, $rootScope, $controller) {
        httpBackendMock = $httpBackend;
        httpBackendMock.expectPOST('api/register').respond(testResponse);
        scope = $rootScope.$new();
        ctrl = $controller('View6Ctrl', {$scope: scope});
    }));

    it('Should not give an error message', function () {
        expect(scope.error).toEqual(undefined)
    });
});