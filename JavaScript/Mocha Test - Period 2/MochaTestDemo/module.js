/**
 * Created by kasper on 3/2/16.
 */

function add(n1, n2) {
    return n1 + n2;
}

function addAsync(n1, n2, callback) {
    setTimeout(function () {
        var result = n1 + n2;
        callback(result);
    }, 1000);
}

module.exports.add = add;
module.exports.addAsync = addAsync;

//console.log(add(2,2));
//var res = addAsync(1, 1, function (res) {
//    console.log('Result: ' + res)
//});
