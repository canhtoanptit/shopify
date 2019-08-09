const axios = require('axios');
const Config = require("../config/config");
const TOKEN_PATH = '/v1/oauth2/token';
const TRACKING_PATH = '/v1/shipping/trackers-batch';
const querystring = require('querystring');
const cacher = require('../cache/redis.cache');
const trackerRepo = require('../repository/tracker.repository');

const api = axios.create({
  baseURL: Config.paypal.sanbox.host,
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded',
  },
  auth: {
    username: Config.paypal.sanbox.client_id,
    password: Config.paypal.sanbox.secret
  },
});

const getPaypalToken = async () => {
  try {
    const res = await api.post(TOKEN_PATH, querystring.stringify({ grant_type : 'client_credentials' }));
    if (res.status === 200) {
      cacher.setPaypalToken(res.data.access_token, res.data.expires_in);
    }
    return res.data
  } catch (e) {
    console.log('error when get paypal token ', e);
    return e
  }
};

const batchAddTrackingNumbers = async (trackers) => {
  try {
    // Check if tracking number exist
    trackers.filter(async tracker => {
      return tracker.transaction_id !== await cacher.getTrackerIdentifier(tracker.tracking_number)
    });
    const url = Config.paypal.sanbox.host + TRACKING_PATH;
    const token = 'Bearer ' + await cacher.getPaypalToken();
    const res = await axios.post(url, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization' : token
      },
      data: {
        'trackers' : trackers
      }
    });
    if (res.status === 200) {
      console.log('res data ', res.data);
      //add to cache
      res.data.tracker_identifiers.forEach(tracker_identifier => cacher.setTrackerIdentifier(tracker_identifier));
      // add to database
      trackerRepo.insertAll(res.data.tracker_identifiers);
      return res.data
    } else {
      console.log('res ', res)
    }
  } catch (e) {
    console.log('error when batch tracking number ', e);
    return e
  }
};

const getTrackingInfo = () => {

};
module.exports = {
  getPaypalToken,
  batchAddTrackingNumbers
};