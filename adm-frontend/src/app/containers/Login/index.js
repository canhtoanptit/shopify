import React, {Component} from 'react';
import {Checkbox, TextField} from "@material-ui/core";

class Login extends Component {
  render() {
    return (
      <div>
        <form>
          <p>
            Shop management system
          </p>
          <TextField
            placeholder="Email"
          />
          <Checkbox defaultChecked color="default"/>
          Remember me
        </form>
      </div>
    )
  }
}

export default Login;
