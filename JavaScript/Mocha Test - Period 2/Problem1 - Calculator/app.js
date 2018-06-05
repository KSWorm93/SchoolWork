/**
 * Created by kasper on 3/1/16.
 */
var express = require("express");
var app = express();

//Makes the api available to use in browser/postman
app.use("/api/calculator/:operation/:n1/:n2", function (req, res, next) {

    var calculatorRequest = {
        operation: req.params.operation,
        n1: Number(req.params.n1),
        n2: Number(req.params.n2)
    };
    res.calculator = calculatorRequest;

    next();
});
//The get request where you get the result from from what you typed in the url field
app.get("/api/calculator/*", function (req, res) {

    var result = getResult(res.calculator.operation, res.calculator.n1, res.calculator.n2);

    res.send('Result is: ' + result);
});

//The different functions to make the calculator
function getResult(operation, n1, n2) {
    if (operation === 'add') {
        return n1 + n2;
    } else if (operation === 'subtract') {
        return n1 - n2;
    } else if (operation === 'divide') {
        return n1 / n2;
    } else if (operation === 'multiply') {
        return n1 * n2;
    }
}


app.listen(3000, function () {
    console.log("Server started, listening on: " + 3000);
});

//To run open browser/postman and type in: http://localhost:3000/api/calculator/add/15/20
//Or another operation and values in the last columns.