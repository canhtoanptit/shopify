import React, {Component} from 'react';
import OrdersTable from '../../component/OrderComponent'
import io from 'socket.io-client';
import {getListOrders} from "../../services/ordersAPI";

class Orders extends Component {
  constructor(props) {
    super(props);
    this.state = {
      orders: []
    };
    this._updateOrders = this._updateOrders.bind(this)
  }

  componentDidMount() {
    const socket = io('http://quyenbeo.com:3001');
    socket.on('connect', function () {
      console.log('connected ', socket);
      socket.emit('authentication', {data: {name: 'toannc'}});
    });

    socket.on('update_order', this._updateOrders);
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
    return <OrdersTable orders={this.state.orders}/>
  }
}

export default Orders;
