const axios = require('axios');
const Config = require("../config/config");
const TOKEN_PATH = '/v1/oauth2/token';
const TRACKING_PATH = '/v1/shipping/trackers-batch';
const querystring = require('querystring');
const cacher = require('../cache/redis.cache');

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
  const url = Config.paypal.sanbox.host + TRACKING_PATH;
  const token = 'Bearer ' + await cacher.getPaypalToken();
  try {
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
      console.log('res data ', res.data)
    } else {
      console.log('res ', res)
    }
    return res
  } catch (e) {
    console.log('error when batch tracking number ', e);
    return e
  }
};

module.exports = {
  getPaypalToken,
  batchAddTrackingNumbers
};