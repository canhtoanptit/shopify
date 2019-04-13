import axios from 'axios';

const HOST = 'localhost:9000';
const GET_ORDERS = '/api/orders';

const api = axios.create({
  baseURL: HOST,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  }
});

const getListOrders = async () => {
  try {
    return await api.get(GET_ORDERS, {});
  } catch (e) {
    return e
  }
};

export {
  getListOrders
}
