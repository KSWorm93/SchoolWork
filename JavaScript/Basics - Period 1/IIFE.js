/**
 * Created by kasper on 2/14/16.
 */
'use strict'

console.log('---IIFE---')
//Here we make a function that calls/invokes itself - this could be used to run some code
//when a user first enter the webpage
var iife = (function () {
    console.log("iife function - nobody called me")
})()

console.log('---Objects---');
console.log('***Exercise 1***');

var object = {
    Name: "Kasper",
    Hobby: "Hiking",
    DistanceToSchool: "35km",
    OS: "Linux/Ubuntu"
}
function ObjLoop() {
    for (var propt in object) {
        console.log(propt + ": " + object[propt]);
    }
}
ObjLoop();
console.log("Deleting OS property")
delete object.OS;
ObjLoop();

console.log("Testing hasOwnProperty")
console.log("Exist: " + object.hasOwnProperty('Name'));
console.log("Not Exist: " + object.hasOwnProperty('IDontThinkSO'));

console.log("***Exercise 2***")
function Person(fName, lName, age) {
    this.fName = fName;
    this.lName = lName;
    this.age = age;
    this.getInfo = function () {
        return fName + " " + lName + " " + age;
    }
}

var newPerson = new Person("Kasper", "Worm", 22);
console.log(newPerson.getInfo())

console.log("***Exercise 3***")
console.log(listAllProperties(newPerson))
delete newPerson.age
console.log("After delete " + listAllProperties(newPerson))

function listAllProperties(o) {
    var objectToInspect;
    var result = [];

    for (objectToInspect = o; objectToInspect !== null; objectToInspect = Object.getPrototypeOf(objectToInspect)) {
        result = result.concat(Object.getOwnPropertyNames(objectToInspect));
    }

    return result;
}