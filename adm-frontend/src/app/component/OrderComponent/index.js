import React from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

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

function SimpleTable(props) {
  const {classes, orders} = props;

  return (
    <Paper className={classes.root}>
      <Table className={classes.table}>
        <TableHead>
          <TableRow>
            <TableCell>Variant</TableCell>
            <TableCell align="left">Name</TableCell>
            <TableCell align="right">Total</TableCell>
            <TableCell align="right">Cost</TableCell>
            <TableCell align="left">Total price</TableCell>
            <TableCell align="right">Profit</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {orders.map(row => (
            <TableRow key={row.variant_id}>
              <TableCell component="th" scope="row">
                {row.variant_id}
              </TableCell>
              <TableCell align="left">{row.data.data.name}</TableCell>
              <TableCell align="right">{row.data.total}</TableCell>
              <TableCell align="right">{row.fat}</TableCell>
              <TableCell align="left">{Number.parseFloat(row.data.total * row.data.data.price).toFixed(2)}</TableCell>
              <TableCell align="right">{row.protein}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Paper>
  );
}

SimpleTable.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SimpleTable);
