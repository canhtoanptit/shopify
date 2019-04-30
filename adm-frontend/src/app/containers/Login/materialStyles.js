const materialStyles = theme => ({
  button: {
    color: 'white',
    width: '50%',
    fontSize: 24,
    fontWeight: 500,
    marginTop: 56,
    minWidth: 560,
    height: 54,
    fontFamily: 'AvenirNext',
    textTransform: 'none',
  },
  buttonLink: {
    textTransform: 'none',
    fontSize: 15,
    fontWeight: 400,
    letterSpacing: 'normal',
    color: '#f9976b',
    fontFamily: 'AvenirNext',
  },
  bootstrapRoot: {
    marginBottom: 18,
    textTransform: 'none',
  },
  bootstrapInput: {
    borderRadius: 8,
    backgroundColor: theme.palette.common.white,
    border: '1px solid #ced4da',
    fontSize: 16,
    padding: '10px 12px',
    lineHeight: '20px',
    transition: theme.transitions.create(['border-color', 'box-shadow']),
    textTransform: 'none',
    fontFamily: [
      '-apple-system',
      'BlinkMacSystemFont',
      '"Segoe UI"',
      'Roboto',
      '"Helvetica Neue"',
      'Arial',
      'sans-serif',
      '"Apple Color Emoji"',
      '"Segoe UI Emoji"',
      '"Segoe UI Symbol"',
    ].join(','),
    '&:focus': {
      borderColor: '#80bdff',
      boxShadow: '0 0 0 0.2rem rgba(0,123,255,.25)',
    },
  },
});

export default materialStyles;
