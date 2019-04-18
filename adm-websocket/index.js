const express = require('express')();
const bodyParser = require('body-parser');
const server = require('./src/service/socketio.service')(express);
const shopifyService = require('./src/service/shopify.service');

express.use(bodyParser.json());

express.get('/', function (req, res) {
  shopifyService.getListOrder();
  res.send('hello user!')
});

server.listen(3001, function () {
  console.log("server listen at port 3001")
});
