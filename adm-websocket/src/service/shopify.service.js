const Shopify = require('shopify-api-node');
const moment = require('moment-timezone');

const shopify = new Shopify({
  shopName: 'vuzila',
  apiKey: '',
  password: ''
});

const getListOrder = function () {
  const startOfDay = moment.tz('America/Juneau').startOf('day').format();
  shopify.order.list({limit: 1, fields: 'name,created_at,total_price,line_items', created_at_min: startOfDay})
    .then(res => console.log('response: ', res))
    .catch(error => console.log('error ', error));
  // shopify.order.count({created_at_min : startOfDay})
  //   .then(res => console.log('response: ', res))
  //   .catch(error => console.log('error ', error));
};

module.exports = {
  getListOrder
};
