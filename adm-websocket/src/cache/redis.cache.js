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

module.exports = {
  setPaypalToken,
  getPaypalToken,
  setTrackerIdentifier,
  getTrackerIdentifier
};