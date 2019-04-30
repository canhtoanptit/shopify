import axios from 'axios';
import {AUTHENTICATE_TOKEN} from "../constants/common";

const HOST = 'http://quyenbeo.com:3001';
const GET_ORDERS = '/api/orders';

const api = axios.create({
  baseURL: HOST,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  }
});

const getListOrders = async () => {
  const userToken = sessionStorage.getItem(AUTHENTICATE_TOKEN);
  try {
    const result = await api.get(GET_ORDERS, {
      headers: {
        Authorization: `Bearer ${userToken}`,
      },
    },);
    if (result.status !== 200) {
      return []
    }

    return result.data
  } catch (e) {
    return e
  }
};

export {
  getListOrders
}
