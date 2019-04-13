import React, {Component} from 'react';
import {getListOrders} from "../../services/ordersAPI";

class Orders extends Component {

  componentDidMount() {
    getListOrders()
      .then((res) => {
          console.log('res is: ', res)
        }
      )
      .catch((err) => console.log('error ', err))
  }

  render() {
    return (
      <p>Orders list</p>
    )
  }
}

export default Orders;
