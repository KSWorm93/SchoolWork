var path = process.argv[2];
var http = require('http');

http.get(path, function(res) {
    res.on('data', function(data){
        console.log(data.toString());
    })
}).on('error', function(e) {
    console.log("Got error: " + e.message);
});
