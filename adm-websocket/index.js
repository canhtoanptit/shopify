const express = require('express')();
const Shopify = require('shopify-api-node');
const bodyParser = require('body-parser');
const server = require('./src/service/socketio.service')(express);
express.use(bodyParser.json());

const shopify = new Shopify({
  shopName: 'vuzila',
  apiKey: '',
  password: ''
});

express.get('/', function (req, res) {
  shopify.order.list()
    .then(res => console.log(res))
    .catch(e => console.log(e));
  res.send('hello user!')
});

server.listen(3000, function () {
  console.log("server listen at port 3000")
});
