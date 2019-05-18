const express = require('express');
const router = express.Router();
const validation = require('../util/validation');
const productService = require('../service/product.service');

/**
 router.post('/api/products', async function (req, res) {
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
    console.log('error: ', e);
    res.status(500);
    res.send('internal error')
  }
});
 **/

router.put('/:id', async function (req, res) {
  try {
    console.log('update product id');
    const token = req.headers.authorization.split(" ")[1];
    validation.verifyToken(token, function (payload, err) {
      if (err) {
        console.log('error: ', err);
        res.status(401);
        res.send('invalid token')
      }
      if (payload) {
        const id = req.params['id'];
        productService.updateProductMO(id, req.body.mo)
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
    console.log('error: ', e);
    res.status(500);
    res.send('internal error')
  }
});

module.exports = router;
