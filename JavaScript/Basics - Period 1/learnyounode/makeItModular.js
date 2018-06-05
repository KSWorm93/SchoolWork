var module = require('./makeItModularModule.js');
var path = process.argv[2];
var filterStr = process.argv[3];

module(path, filterStr, function(err, list){
    if(err){
        console.log("Error appeared" + err);
    } else{
        list.forEach(function(file){
            console.log(file);
        });
    }
});