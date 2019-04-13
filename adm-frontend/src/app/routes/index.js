import React, {Component} from 'react';
import {BrowserRouter, Route, withRouter} from 'react-router-dom';
import Login from '../containers/Login';
import Orders from '../containers/Orders';

class RootRoute extends Component {

  componentDidMount() {
  }

  render() {
    return (
      <BrowserRouter>
        <Route path="/" component={Orders} />
        <Route path="/login" component={Login} />
      </BrowserRouter>
    )
  }
}

export default withRouter(RootRoute)
