const express = require('express')();
const bodyParser = require('body-parser');
const cors = require('cors');
const server = require('./src/service/socketio.service')(express);
const shopifyService = require('./src/service/shopify.service');
const validation = require('./src/util/validation');

express.use(bodyParser.json());
express.use(cors());

express.get('/api/orders', async function (req, res) {
  try {
    const token = req.headers.authorization.split(" ")[1];
    validation.verifyToken(token, function (payload, err) {
      if (err) {
        console.log('error: ', err);
        res.status(401);
        res.send('invalid token')
      }
      if (payload) {
        shopifyService.getListOrder()
          .then(result => res.send(result))
          .catch(err => {
            console.log('error: ', err);
            res.status(500);
            res.send('internal error')
          })
      }
    });
  } catch (e) {
    res.status(500);
    res.send('internal error')
  }
});

express.post('/api/product', async function (req, res) {
  try {
    const token = req.headers.authorization.split(" ")[1];
    validation.verifyToken(token, function (payload, err) {
      if (err) {
        console.log('error: ', err);
        res.status(401);
        res.send('invalid token')
      }
      if (payload) {
        shopifyService.getAllProduct()
          .then(() => {
            res.send('ok')
          })
          .catch(err => {
            console.log('error: ', err);
            res.status(500);
            res.send('internal error')
          })
      }
    });
  } catch (e) {
    res.status(500);
    res.send('internal error')
  }
});

server.listen(3001, function () {
  console.log("server listen at port 3001")
});
