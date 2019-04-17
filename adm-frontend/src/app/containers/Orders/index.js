import React, {Component} from 'react';
import io from 'socket.io-client';

const socket = io('http://localhost:3001');

class Orders extends Component {

  componentDidMount() {
    // getListOrders()
    //   .then((res) => {
    //       console.log('res is: ', res)
    //     }
    //   )
    //   .catch((err) => console.log('error ', err))
    socket.on('connect', function () {
      console.log('connected ', socket);
      socket.emit('authentication', {data: {name: 'toannc'}});
      socket.on('update_order', function (msg) {
        console.log('receive message ', msg)
      });
    })
  }

  render() {
    return (
      <p>Orders list</p>
    )
  }
}

export default Orders;
