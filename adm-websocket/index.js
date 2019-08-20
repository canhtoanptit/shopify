const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const app = express();
const port = 3000;
const orderRouter = require('./src/routes/orders.route');
// const productRouter = require('./src/routes/product.route');
// const paypalService = require('./src/service/paypal.service');

app.use(bodyParser.json());
app.use(cors());

app.use('/api/order', orderRouter);
// express.use('/api/products', productRouter);

app.listen(port, () => console.log(`Example app listening on port ${port}!`))
