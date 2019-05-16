import axios from 'axios';
import {AUTHENTICATE_TOKEN} from "../constants/common";

const HOST = 'http://13.251.52.49:3001';
const PRODUCT = '/api/products';

const api = axios.create({
  baseURL: HOST,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  }
});

const updateProductMO = ({id, mo}) => {
  const userToken = sessionStorage.getItem(AUTHENTICATE_TOKEN);
  return api.put(PRODUCT + '/' + id, {
      mo
    }, {
      headers: {
        Authorization: `Bearer ${userToken}`,
      },
    }
    ,
  )
};

export {updateProductMO}
