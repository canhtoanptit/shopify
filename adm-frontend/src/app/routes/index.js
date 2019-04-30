import React, {Component} from 'react';
import {BrowserRouter, Route, withRouter, Redirect} from 'react-router-dom';
import Login from '../containers/Login';
import Orders from '../containers/Orders';
import {AUTHENTICATE_TOKEN} from '../constants/common';
import styles from './styles.css';
import Spinner from 'react-spinkit';

class RootRoute extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isLoading: true,
    }
  }

  componentDidMount() {
    const userToken = localStorage.getItem(AUTHENTICATE_TOKEN);
    if (userToken) {
      this.setState({
        isLoading: false,
      });
    } else {
      this.setState({
        isLoading: false,
      });
    }
  }

  render() {
    const userToken = localStorage.getItem(AUTHENTICATE_TOKEN);
    const {isLoading} = this.state;
    if (isLoading) {
      return (
        <div className={styles.loadingOverlay}>
          <Spinner
            fadeIn="none"
            name="line-scale-pulse-out"
            color="#f9976b"
            className="sk-line-scale-pulse-out"
          />
        </div>
      );
    }
    return (
      <BrowserRouter>
        <Route
          path="/"
          exact
          render={() => {
            if (userToken) {
              return (
                <Redirect
                  to={{
                    pathname: '/orders',
                  }}
                />
              );
            }
            return (
              <Redirect
                to={{
                  pathname: '/login',
                }}
              />
            );
          }}
        />
        <Route path="/orders" component={Orders}/>
        <Route path="/login" component={Login}/>
      </BrowserRouter>
    )
  }
}

export default withRouter(RootRoute)
