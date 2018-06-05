/**
 * Created by kasper on 2/14/16.
 */
'use strict'

console.log('---This---')
console.log('***Exercise 1')
/*
In java 'this' refers to the current instance of the object.
In javascript 'this' initially refers to the global object but can change.
 */
//'This' refers to the the new object when the using 'new'
function color() {
    this.color = "Purpur";
    console.log(this)
}
new color();

console.log('***Exercise 2')
//Here 'this' refers to the obj in which it was invoked/executed
var obj = {
    method: function () {
        console.log(this === obj) //Will return true
    }
}
obj.method();
console.log('***Exercise 3')
//This can be set by yourself or by using the call(), apply() or bind method()
//call() attaches 'this' into the function and executes the function immediately
var person = {
    name: "Kasper",
    hello: function (thing) {
        console.log(this.name + " says hello " + thing);
    }
}
person.hello.call(person, "world");
