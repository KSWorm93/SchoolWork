/**
 * Created by kasper on 2/14/16.
 */
'use strict'

/*
Hoisting is when variables and functions are moved to the top of the javascript engine.
the javascript ending runs over your code and looks at the variables and if it
can see if needs them, it will reserve them memory, not necessarily initialising them.
 */

console.log('---Hoisting---')
console.log('***Exercise 1***')
//Function is hoisted
isItHoisted()
function isItHoisted() {
    console.log("This function is hoisted " + hoisted);
    //I can use the variable hoisted in the above line but it will come out as undefined
    //since it hasn't been initialised yet
    var hoisted = "So is this var"
    //After the initialisation it will be written out correctly
    console.log("This function is hoisted " + hoisted);
}

//To avoid problems with using undeclared variables,
//a good rule is to always declare your variables at the top of the scope they need to be used in