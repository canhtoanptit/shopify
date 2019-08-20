const {promisify} = require('util');
const Config = require('../config/config.json')
const redis = require("redis"),
  client = redis.createClient({
    host:  Config.redis.host
  });
const getAsync = promisify(client.get).bind(client);

const setPaypalToken = (token, expiresIn) => {
  console.log('set pay pal token %s and expired in %s ', token, expiresIn);
  client.set('paypal_token', token, 'EX', expiresIn)
};

const getPaypalToken = async () => {
  return getAsync('paypal_token')
};

const setTrackerIdentifier = (tracker_identifier) => {
  console.log('set tracker identifier ', tracker_identifier);
  client.set(tracker_identifier.tracking_number, tracker_identifier.transaction_id)
};

const getTrackerIdentifier = async (tracking_number ) => {
  return getAsync(tracking_number)
};

const setCache = async (key, value) => {
  console.log('set key %s and value %s ', key, value);
  client.set(key, value)
};

const getCacheAsync = async (key) => {
  return getAsync(key)
};

module.exports = {
  setPaypalToken,
  getPaypalToken,
  setTrackerIdentifier,
  getTrackerIdentifier,
  getCacheAsync,
  setCache
};