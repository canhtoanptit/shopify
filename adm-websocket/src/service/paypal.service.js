const axios = require('axios');
const Config = require("../config/config");
const TOKEN_PATH = '/v1/oauth2/token';
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

module.exports = {
  getPaypalToken
};