const express = require('express')();
const bodyParser = require('body-parser');
express.use(bodyParser.json());

express.get('/', function (req, res) {
  res.send('hello user!')
});

const server = require('./src/service/socketio.service')(express);

server.listen(3000, function () {
  console.log("server listen at port 3000")
});
