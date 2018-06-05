var fs = require('fs');
var content;
var buf = fs.readFile(process.argv[2], function (err, content){

line = content.toString().split('\n').length-1;
console.log(line);
});

