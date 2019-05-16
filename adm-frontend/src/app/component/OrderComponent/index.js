import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import IconButton from '@material-ui/core/IconButton';
import EditIcon from '@material-ui/icons/Edit';
import Modal from '@material-ui/core/Modal';
import OrderDetail from './OrderDetail';

const styles = theme => ({
  root: {
    width: '100%',
    marginTop: theme.spacing.unit * 3,
    overflowX: 'auto',
  },
  table: {
    minWidth: 700,
  },
  fab: {
    margin: theme.spacing.unit,
  },
  paper: {
    position: 'absolute',
    width: theme.spacing.unit * 50,
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[5],
    padding: theme.spacing.unit * 4,
  },
});

class OrderList extends Component {

  constructor(props) {
    super(props);
    this.state = {
      openModal: false,
      orderDetail: {},
      redirect: false
    };
  }

  toggleEditMO = (row) => {
    const {openModal} = this.state;
    this.setState({
      openModal: !openModal,
      orderDetail: {
        id: row.variant_id,
        mo: row.mo ? row.mo : 0
      }
    });
  };

  render() {
    const {classes, orders} = this.props;
    const {openModal} = this.state;
    return (
      <Paper className={classes.root}>
        <Table className={classes.table}>
          <TableHead>
            <TableRow>
              <TableCell>Variant</TableCell>
              <TableCell text-align="left">Name</TableCell>
              <TableCell text-align="right">Total</TableCell>
              <TableCell text-align="right">Cost</TableCell>
              <TableCell align="center">MO</TableCell>
              <TableCell text-align="left">Total price</TableCell>
              <TableCell text-align="right">Profit</TableCell>
            </TableRow>
          </TableHead>
          <TableBody className={classes.tableBody}>
            {orders.map(row => (
              <TableRow key={row.variant_id}>
                <TableCell component="th" scope="row">
                  {row.variant_id}
                </TableCell>
                <TableCell text-align="left">{row.data.data.name}</TableCell>
                <TableCell text-align="right">{row.data.total}</TableCell>
                <TableCell text-align="right">{row.cost}</TableCell>
                <CustomTableCell>
                  {row.mo ? row.mo : 0}
                  <IconButton aria-label="Edit" className={classes.margin} onClick={() => this.toggleEditMO(row)}>
                    <EditIcon fontSize="small"/>
                  </IconButton>
                </CustomTableCell>
                <TableCell
                  text-align="left">{Number.parseFloat(row.data.total * row.data.data.price).toFixed(2)}</TableCell>
                <TableCell text-align="right">{row.protein}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
        <Modal
          aria-labelledby="simple-modal-title"
          aria-describedby="simple-modal-description"
          open={openModal}
          onClose={this.toggleEditMO}
        >
          <div style={getModalStyle()} className={classes.paper}>
            <OrderDetail orderDetail={this.state.orderDetail}/>
          </div>
        </Modal>
      </Paper>
    )
  }
}

function rand() {
  return Math.round(Math.random() * 20) - 10;
}

function getModalStyle() {
  const top = 50 + rand();
  const left = 50 + rand();

  return {
    top: `${top}%`,
    left: `${left}%`,
    transform: `translate(-${top}%, -${left}%)`,
  };
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
