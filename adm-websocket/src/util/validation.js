const jwt = require('jsonwebtoken');
const Config = require('../config/config.json');

const verifyToken = (token, callback) => {
  return callback(jwt.verify(token, new Buffer.from(Config.secret, 'base64')))
};

module.exports = {
  verifyToken
};
