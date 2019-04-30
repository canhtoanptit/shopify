import React, {Component} from 'react';
import {Button, Checkbox, TextField} from '@material-ui/core';
import Swal from 'sweetalert2';
import * as userAPI from '../../services/userAPI';
import {AUTHENTICATE_TOKEN} from '../../constants/common';
import styles from './styles.css';
import materialStyles from './materialStyles';
import {withStyles} from '@material-ui/core/styles';
import Spinner from 'react-spinkit';

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isLoading: false,
    }
  }

  handleSubmit = (event) => {
    event.preventDefault();
    const email = event.target.email.value;
    const password = event.target.password.value;
    if (!email || email.length === 0) {
      Swal.fire({
        type: 'warning',
        title: 'Please enter your email',
        confirmButtonColor: '#f9976b',
        confirmButtonText: 'OK',
      });
      return;
    }
    if (!password || password.length === 0) {
      Swal.fire({
        type: 'warning',
        title: 'Please enter your password',
        confirmButtonColor: '#f9976b',
        confirmButtonText: 'OK',
      });
      return;
    }
    if (password.length < 5) {
      Swal.fire({
        type: 'error',
        title: 'Password should NOT be shorter than 5 characters',
        confirmButtonColor: '#f9976b',
        confirmButtonText: 'OK',
      });
      return;
    }
    this.setState({
      isLoading: true,
    });

    userAPI
      .signIn(email, password)
      .then((res) => {
        console.log('res is: ', res);
        if (res.status === 200) {
          const {history} = this.props;
          localStorage.setItem(AUTHENTICATE_TOKEN, res.data.id_token);
          history.push('/orders');
        } else if (res.response) {
          console.log('res.response.data', res.response.data);
          Swal.fire({
            type: 'error',
            title: typeof res.response.data.err_msg === 'string'
              ? res.response.data.err_msg
              : 'Login failure. Please try again',
            confirmButtonColor: '#f9976b',
            confirmButtonText: 'OK',
          });
          this.setState({
            isLoading: false,
          });
        } else {
          Swal.fire({
            type: 'error',
            title: 'Login failure. Please try again.',
            confirmButtonColor: '#f9976b',
            confirmButtonText: 'OK',
          });
          this.setState({
            isLoading: false,
          });
        }
      })
      .catch((err) => {
        console.log(err);
        this.setState({
          isLoading: false,
        });
      });
  };

  render() {
    const {classes} = this.props;
    const {isLoading} = this.state;
    return (
      <div className={styles.container}>
        <form className={styles.containerForm} onSubmit={this.handleSubmit}>
          <img className="image" src="https://cdn.shopify.com/s/files/1/0067/9382/2308/files/logo_dai_1012_360x.png?v=1544431384" alt="logo"/>
          <p className="label">
            Shop management system
          </p>
          <TextField
            disabled={isLoading}
            placeholder="Email"
            name="email"
            style={{width: '50%', minWidth: 560}}
            inputstyle={{width: '50%', minWidth: 560}}
            InputProps={{
              disableUnderline: true,
              classes: {
                root: classes.bootstrapRoot,
                input: classes.bootstrapInput,
              },
            }}
          />
          <TextField
            disabled={isLoading}
            placeholder="Password"
            type="password"
            name="password"
            style={{width: '50%', minWidth: 560}}
            inputstyle={{width: '50%', minWidth: 560}}
            InputProps={{
              disableUnderline: true,
              classes: {
                root: classes.bootstrapRoot,
                input: classes.bootstrapInput,
              },
            }}
          />
          <div className="option">
            <div className="checkboxContainer">
              <Checkbox defaultChecked color="default" value="checkedG"/>
              Remember me
            </div>
            <div className="forgotPassword">
              <Button className={classes.buttonLink} disabled={isLoading} href="/forgot-password">
                Forgot Password?
              </Button>
            </div>
          </div>
          <Button
            className={classes.button}
            disabled={isLoading}
            variant="contained"
            type="submit"
            color="primary"
          >
            {isLoading ? (
              <Spinner
                fadeIn="none"
                name="line-scale-pulse-out"
                color="#f9976b"
                className="sk-line-scale-pulse-out"
              />
            ) : (
              'Log in'
            )}
          </Button>
        </form>
      </div>
    );
  }
}

export default withStyles(materialStyles)(Login);
