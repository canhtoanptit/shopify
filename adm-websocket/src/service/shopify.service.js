const Shopify = require('shopify-api-node');
const moment = require('moment-timezone');
const util = require('../util/data.util');
const Config = require('../config/config.json');
const productRepo = require('../repository/product.repository');

const shopify = new Shopify({
  shopName: Config.shopify.shopName,
  apiKey: Config.shopify.apiKey,
  password: Config.shopify.password
});

const getListOrder = async function () {
  const startOfDay = moment.tz('America/Juneau').startOf('day').format();
  const data = new Map();
  const totalRecords = await shopify.order.count({created_at_min: startOfDay});
  return shopify.order.list({limit: totalRecords, fields: 'id,name,created_at,total_price,line_items'})
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
};

const getAllProduct = () => {
  shopify.product.list({limit: 250, fields: 'id,title,variants'})
    .then(res => {
      return productRepo.insertAllProduct(util.convertProductToVariants(res))
    })
    .catch(error => {
      console.log('error ', error)
    })
};

module.exports = {
  getListOrder,
  getAllProduct
};
