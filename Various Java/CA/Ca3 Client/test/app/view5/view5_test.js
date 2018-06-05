/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';

describe('myApp.view5 view5Ctrl', function () {
    var scope, httpBackendMock, ctrl;
    var testResponse = {message: "Test Message Mock"};
    var undefined = "Test Message Mock";
    beforeEach(module('myApp.view5'));

    beforeEach(inject(function ($httpBackend, $rootScope, $controller) {
        httpBackendMock = $httpBackend;
        httpBackendMock.expectGET('api/demoadmin/users').respond(testResponse);
        scope = $rootScope.$new();
        ctrl = $controller('View5Ctrl', {$scope: scope});
    }));

    it('Should fetch a test message', function () {
        expect(ctrl.users).toBeUndefined();
        httpBackendMock.flush();
        expect(ctrl.users.message).toEqual(undefined);
    });
});