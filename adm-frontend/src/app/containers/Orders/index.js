import React, {Component} from 'react';
import Demo from '../../component/OrderComponent'
import io from 'socket.io-client';
import {getListOrders} from "../../services/ordersAPI";

const socket = io('http://localhost:3001');

class Orders extends Component {
  constructor(props) {
    super(props);
    this.state = {
      orders: []
    }
  }

  componentDidMount() {
    getListOrders()
      .then((res) => {
          this.setState({
            orders: res
          })
        }
      )
      .catch((err) => console.log('error ', err))
    socket.on('connect', function () {
      console.log('connected ', socket);
      socket.emit('authentication', {data: {name: 'toannc'}});

      socket.on('update_order', function (msg) {
        console.log('receive message ', msg);
      });
    })
  }

  render() {
    return <Demo orders={this.state.orders}/>
  }
}

export default Orders;
