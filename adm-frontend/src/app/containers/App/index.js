import React from 'react';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import Route from '../../routes';

const theme = createMuiTheme({
  palette: {
    primary: {
      main: '#7b6f67',
      contrastText: '#fff',
    },
    secondary: {
      main: '#f9976b',
      contrastText: '#fff',
    },
  },
  typography: {
    // fontFamily: "AvenirNext, Avenir Next"
    fontFamily: 'AvenirNextRegular',
  },
});

export default () => (
  <MuiThemeProvider theme={theme}>
    <Route />
  </MuiThemeProvider>
);
