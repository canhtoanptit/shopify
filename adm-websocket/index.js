const express = require('express')();
const bodyParser = require('body-parser');
const cors = require('cors');
const server = require('./src/service/socketio.service')(express);
const shopifyService = require('./src/service/shopify.service');

express.use(bodyParser.json());
express.use(cors());

express.get('/api/orders', async function (req, res) {
  const result = await shopifyService.getListOrder();

  res.send(result)
});

server.listen(3001, function () {
  console.log("server listen at port 3001")
});
