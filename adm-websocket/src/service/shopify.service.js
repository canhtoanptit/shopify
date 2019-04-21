const Shopify = require('shopify-api-node');
const moment = require('moment-timezone');
const util = require('../util/data.util');

const shopify = new Shopify({
  shopName: 'vuzila',
  apiKey: '',
  password: ''
});

const getListOrder = function () {
  const startOfDay = moment.tz('America/Juneau').startOf('day').format();
  const data = new Map();
  // return shopify.order.list({limit: 100, fields: 'id,name,created_at,total_price,line_items', created_at_min: startOfDay})
  return shopify.order.list({limit: 100, fields: 'id,name,created_at,total_price,line_items'})
    .then(res => {
      res.map(row => {
        row.line_items.map(item => {
          const variant = data.get(item.variant_id);
            if (!variant) {
              data.set(item.variant_id, {
                total: 1,
                data: item
              })
            } else {
              variant.total++;
            }
          }
        )
      });

      return util.convertMapToArray(data);
    })
    .catch(error => console.log('error ', error));
  // shopify.order.count({created_at_min : startOfDay})
  //   .then(res => console.log('response: ', res))
  //   .catch(error => console.log('error ', error));
};

module.exports = {
  getListOrder
};
