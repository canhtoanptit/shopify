import axios from 'axios';

const HOST = 'http://13.251.52.49';
const SIGN_IN = '/api/authenticate';

const api = axios.create({
  baseURL: HOST,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  }
});

const signIn = async (username, password) => {
  try {
    return await api.post(SIGN_IN, {
      username,
      password
    });
  } catch (e) {
    return e
  }
};

export {
  signIn
};
