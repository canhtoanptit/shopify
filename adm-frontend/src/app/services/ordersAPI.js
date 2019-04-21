import axios from 'axios';

const HOST = 'http://localhost:3001';
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
    const result = await api.get(GET_ORDERS, {});
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
