import axios from 'axios';

const HOST = 'http://quyenbeo.com';
const SIGN_IN = '/api/authenticate';

const api = axios.create({
  baseURL: HOST,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  }
});

const signIn = async (username, password) => {
  console.log(username, password);
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
