import React, {Component} from 'react';
import OrdersTable from '../../component/OrderComponent'
import io from 'socket.io-client';
import {getListOrders} from "../../services/ordersAPI";

const socket = io('http://localhost:3001');

class Orders extends Component {
  constructor(props) {
    super(props);
    this.state = {
      orders: []
    };
    this._updateOrders = this._updateOrders.bind(this)
  }

  componentDidMount() {
    getListOrders()
      .then((res) => {
          this.setState({
            orders: res
          })
        }
      )
      .catch((err) => console.log('error ', err));
    socket.on('connect', function () {
      console.log('connected ', socket);
      socket.emit('authentication', {data: {name: 'toannc'}});
    });

    socket.on('update_order', this._updateOrders);
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
