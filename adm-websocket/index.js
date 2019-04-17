const express = require('express')();
const bodyParser = require('body-parser');
const server = require('./src/service/socketio.service')(express);

express.use(bodyParser.json());

express.get('/', function (req, res) {
  res.send('hello user!')
});

server.listen(3001, function () {
  console.log("server listen at port 3001")
});
