const express = require('express')();
const bodyParser = require('body-parser');
const cors = require('cors');
const server = require('./src/service/socketio.service')(express);
const orderRouter = require('./src/routes/orders.route');
const productRouter = require('./src/routes/product.route');

express.use(bodyParser.json());
express.use(cors());

express.use('/api/orders', orderRouter);
express.use('/api/products', productRouter);

server.listen(3001, function () {
  console.log("server listen at port 3001")
});
