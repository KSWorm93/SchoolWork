
var http = require('http');
var path = process.argv[2];
var bl = require('bl');

http.get(path, function(res){
    res.on('data', function(data){
    console.log(data.toString())
    })
        .on('error', function(e){
        console.log('error' + e);
    })
});
