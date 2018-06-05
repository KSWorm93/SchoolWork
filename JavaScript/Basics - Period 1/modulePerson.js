/**
 * Created by kasper on 2/14/16.
 */
'use strict'

module.exports = (function () {
    //Makes standard person empty strings, they will be overruled when methods are called
    var modulePerson = {name: "", age: ""}

    function setAge(val) {
        modulePerson.age = val;
    }

    function setName(val) {
        modulePerson.name = val;
    }

    function getInfo() {
        return modulePerson.name + " " + modulePerson.age;
    }

    return {
        setAgeFunction: function (val) {
            setAge(val);
        },
        setNameFunction: function (val) {
            setName(val);
        },
        getInfoFunction: function (val) {
            return getInfo();
        }
    }
})()