import React, {PureComponent} from 'react';
import {Formik} from 'formik';
import TextField from "@material-ui/core/es/TextField/TextField";
import Button from "@material-ui/core/es/Button/Button";
import {updateProductMO} from '../../../services/productAPI'

class OrderDetail extends PureComponent {

  render() {
    const {orderDetail} = this.props;
    console.log('detail ', orderDetail);
    return (
      <div>
        <Formik
          initialValues={orderDetail}
          onSubmit={async (values, {setSubmitting}) => {
            console.log('values ', values);
            setSubmitting(false);
            let res = await updateProductMO(orderDetail);
            if (res.status === 200) {
              console.log('ok ', res.data)
            }
          }}
        >
          {({
              values,
              handleChange,
              handleBlur,
              handleSubmit,
              isSubmitting,
              /* and other goodies */
            }) => (
            <form onSubmit={handleSubmit}>
              <TextField
                type="number"
                name="mo"
                onChange={handleChange}
                onBlur={handleBlur}
                value={values.mo}
              />
              <Button variant="contained" type="submit" disabled={isSubmitting}>
                Update
              </Button>
            </form>
          )}
        </Formik>
      </div>
    )
  }
}

export default OrderDetail;
