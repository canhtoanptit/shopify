const express = require('express');
const router = express.Router();
const validation = require('../util/validation');
const shopifyService = require('../service/shopify.service');

router.get('/', async function (req, res) {
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
    console.log('error: ', e);
    res.status(500);
    res.send('internal error')
  }
});

module.exports = router;
