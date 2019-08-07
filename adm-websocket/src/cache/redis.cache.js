const {promisify} = require('util');
const redis = require("redis"),
  client = redis.createClient({
    host:  '192.168.56.102'
  });
const getAsync = promisify(client.get).bind(client);

const setPaypalToken = (token, expiresIn) => {
  console.log('set pay pal token %s and expired in %s ', token, expiresIn);
  client.set('paypal_token', token, 'EX', expiresIn)
};

const getPaypalToken = () => {
  return getAsync('paypal_token')
};

module.exports = {
  setPaypalToken,
  getPaypalToken
};