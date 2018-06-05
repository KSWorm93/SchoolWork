/**
 * Created by kasper on 3/2/16.
 */

//Import chai to allow us to assert that we are right
var expect = require('chai').expect;
var adder = require('../module');

//Describe what it should do. describe our test
describe("Test Calculator", function () {
    it("Should return 4", function () {
        //If this is true, the test is a succes
        expect(adder.add(2, 2)).to.be.equal(4);
    })
});
it("Should return 8 asynchronous", function (done) {
    adder.addAsync(4, 4, function (res) {
        expect(res).to.be.equal(8);
        done();
    })
});


