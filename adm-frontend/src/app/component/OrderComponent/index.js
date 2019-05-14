import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import Modal from 'react-responsive-modal';

const styles = theme => ({
  root: {
    width: '100%',
    marginTop: theme.spacing.unit * 3,
    overflowX: 'auto',
  },
  table: {
    minWidth: 700,
  },
});

class OrderList extends Component {

  constructor(props) {
    super(props);
    this.state = {
      openModal: false,
    };
    this._handleChange = this._handleChange.bind(this)
  }

  _handleChange = (event) => {
    console.log('event ', event.target.value)
  };

  render() {
    const {classes, orders} = this.props;
    return (
      <Paper className={classes.root}>
        <Table className={classes.table}>
          <TableHead>
            <TableRow>
              <TableCell>Variant</TableCell>
              <TableCell align="left">Name</TableCell>
              <TableCell align="right">Total</TableCell>
              <TableCell align="right">Cost</TableCell>
              <TableCell align="center">MO</TableCell>
              <TableCell align="left">Total price</TableCell>
              <TableCell align="right">Profit</TableCell>
            </TableRow>
          </TableHead>
          <TableBody className={classes.tableBody}>
            {orders.map(row => (
              <TableRow key={row.variant_id}>
                <TableCell component="th" scope="row">
                  {row.variant_id}
                </TableCell>
                <TableCell align="left">{row.data.data.name}</TableCell>
                <TableCell align="right">{row.data.total}</TableCell>
                <TableCell align="right">{row.cost}</TableCell>
                <CustomTableCell align="right">
                  {row.mo ? row.mo : 0}
                </CustomTableCell>
                <TableCell align="left">{Number.parseFloat(row.data.total * row.data.data.price).toFixed(2)}</TableCell>
                <TableCell align="right">{row.protein}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
        <Modal>

        </Modal>
      </Paper>
    )
  }
}

const CustomTableCell = withStyles(theme => ({
  head: {
    // backgroundColor: 'transparent',
    fontSize: 15,
    textAlign: 'center',
    color: theme.palette.common.white,
    height: 26.3,
    backgroundColor: 'rgba(180, 180, 180, 0.8)',
    boxShadow: 'inset 0 3px 5px 0 #757575',
  },
  body: {
    fontSize: 14,
    textAlign: 'center',
    color: '#757575',
  },
}))(TableCell);

OrderList.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(OrderList);
