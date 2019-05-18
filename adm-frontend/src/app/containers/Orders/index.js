import React, {Component} from 'react';
import OrdersTable from '../../component/OrderComponent'
import io from 'socket.io-client';
import {getListOrders} from "../../services/ordersAPI";
import {AUTHENTICATE_TOKEN} from "../../constants/common";
import {Redirect} from 'react-router-dom';

class Orders extends Component {
  constructor(props) {
    super(props);
    this.state = {
      orders: []
    };
    this._updateOrders = this._updateOrders.bind(this)
  }

  componentDidMount() {
    const userToken = sessionStorage.getItem(AUTHENTICATE_TOKEN);
    if (userToken) {
      const socket = io('http://13.251.52.49:3001');
      socket.on('connect', function () {
        console.log('connected ', socket);
        socket.emit('authentication', {data: {name: 'toannc'}});
      });

      socket.on('update_order', this._updateOrders);
    }
    getListOrders()
      .then((res) => {
          this.setState({
            orders: res
          })
        }
      )
      .catch((err) => console.log('error ', err));
  }

  _updateOrders(orders) {
    this.setState({
      orders: orders.data
    })
  }

  render() {
    const userToken = sessionStorage.getItem(AUTHENTICATE_TOKEN);
    if (!userToken) {
      return (
        <Redirect
          to={{
            pathname: '/login',
          }}
        />
      );
    }
    return <OrdersTable orders={this.state.orders}/>
  }
}

export default Orders;
