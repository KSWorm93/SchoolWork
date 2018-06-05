/**
 * Created by kasper on 2/10/16.
 */
'use strict';

var names = ["Lars", "Tue", "Anders", "Mia", "Rudolf", "Ida", "Kasper"];

//Callbacks
//Exercise 1 - filter and map methods
console.log("---Callbacks---")
console.log("***Exercise 1***");

function lowerThan3(obj) {
    return obj.toString().length <= 3;
}

function upperCase(obj) {
    return obj.toString().toUpperCase();
}
//Filter
var filteredArray = names.filter(lowerThan3);
console.log("filter method:")
console.log(filteredArray);

//Map
var mappedArray = names.map(upperCase);
console.log("mapp method:")
console.log(mappedArray);

//Exercise 2 - custom filter and map methods
console.log("***Exercise 2***");

//myFilter
function myFilter(arr, callback) {
    var newArray = [];
    if (callback && typeof(callback) === "function") {
        for (var i = 0; i < arr.length; i++) {
            if (callback(arr[i])) {
                newArray.push(arr[i]);
            }
        }
        return newArray;
    }
    else {
        throw Error("TypeError: " + callback + " is not a function");
    }
}

var myFilteredArray = myFilter(names, lowerThan3)
console.log("myFilter method")
console.log(myFilteredArray);

//myMap
function myMap(arr, callback) {
    var newArray = [];
    if (callback && typeof(callback) === "function") {
        for (var i = 0; i < arr.length; i++) {
            if (callback(arr[i])) {
                newArray.push(callback(arr[i]));
            }
        }
        return newArray;
    }
    else {
        throw Error("TypeError: " + callback + " is not a function");
    }
}

var myMappedArray = myMap(names, upperCase)
console.log("myMap method")
console.log(myMappedArray);

//Exercise 3 - Custom filter and map property
console.log("***Exercise 3***");

//MyPropertyFilter
function PrototypeCustomFilter() {
    var namesFiltered = names.myPropertyFilter(function (name) {
        return name.length <= 3;
    });
    return namesFiltered;
}

Array.prototype.myPropertyFilter = function (callback) {
    if (callback && typeof(callback) === "function") {
        var array = this;
        var tempArray = [];
        for (var x = 0; x < array.length; x++) {
            var val = array[x];
            if (callback(val)) {
                tempArray.push(val);
            }
        }
        return tempArray;
    }
};
var myPropertyFilter = PrototypeCustomFilter();
console.log("myPropertyFilter")
console.log(myPropertyFilter);

//MyPropertyMap
function PrototypeCustomMap() {
    var namesMapped = names.myPropertyMap(function (name) {
        return name.toString().toUpperCase();
    });
    return namesMapped;
}

Array.prototype.myPropertyMap = function (callback) {
    if (callback && typeof(callback) === "function") {
        var array = this;
        var tempArray = [];
        for (var x = 0; x < array.length; x++) {
            var val = array[x];
            if (callback(val)) {
                tempArray.push(callback(val));
            }
        }
        return tempArray;
    }
};

var myPropertyMap = PrototypeCustomMap();
console.log("myPropertyMap")
console.log(myPropertyMap);

console.log("***Exercise 4***");
//Handling numbered arrays
var a = [1, 2, 3];
var b = [1, 2, 3];

function handleNumArrays(a, b, callback) {
    var tempArray = [];
    for (var i = 0; i < a.length; i++) {
        tempArray.push(a[i] + b[i]);
    }
    callback(tempArray);
}

handleNumArrays(a, b, function (res) {
    console.log("Example: " + res)
});
handleNumArrays(a, b, function (res) {
    console.log("Sum: " + res.reduce(function (val1, val2) {
            return val1 + val2
        }))
});
handleNumArrays(a, b, function (res) {
    console.log("Multiply: " + res.map(function (val) {
            return val * 10
        }))
})





