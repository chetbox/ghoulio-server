var express = require('express');
var server = require('./server')(express());

var port = process.env.PORT || 1337;

server.listen(port, function() {
    console.log((new Date()) + ' Started successfully on port ' + port);
});
