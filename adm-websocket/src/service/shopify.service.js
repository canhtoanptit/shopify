const Shopify = require('shopify-api-node');
// const Config = require('../config/config.json');
const cacher = require('../cache/redis.cache');

// const shopify = new Shopify({
//   shopName: Config.shopify.shopName,
//   apiKey: Config.shopify.apiKey,
//   password: Config.shopify.password
// });

// const getListOrder = async function () {
//   const startOfDay = moment.tz('America/Juneau').startOf('day').format();
//   const data = new Map();
//   const totalRecords = await shopify.order.count({created_at_min: startOfDay});
//   let res = await shopify.order.list({limit: totalRecords, fields: 'id,name,created_at,total_price,line_items'});
//   res.map(row => {
//       row.line_items.map(item => {
//           const variant = data.get(item.variant_id);
//           if (!variant) {
//             data.set(item.variant_id, {
//               total: 1,
//               data: item
//             })
//           } else {
//             variant.total++;
//           }
//         }
//       )
//     }
//   );
//
//   //get product cost
//   let product = await productRepo.findAllProductById(Array.from(data.keys()));
//   return util.convertMapToArray(data, product);
// };

// const getAllProduct = () => {
//   shopify.product.list({limit: 250, fields: 'id,title,variants'})
//     .then(res => {
//       return productRepo.insertAllProduct(util.convertProductToVariants(res))
//     })
//     .catch(error => {
//       console.log('error ', error)
//     })
// };

const getOrderFulfilled = async (req, res) => {
  const shopName = req.body.storeName;
  const apiKey = req.body.shopifyApiKey;
  const password = req.body.shopifyApiPassword;
  if (!shopName || !apiKey || !password) {
    res.status(400);
    res.send(JSON.stringify({data: 'bad request'}))
  }
  const shopify = new Shopify({
    shopName: shopName,
    apiKey: apiKey,
    password: password
  });
  try {
    let since_id = await cacher.getCacheAsync(shopName);
    let params = {
      limit: 1,
      fields: 'id,name,order_number,fulfillments',
      fulfillment_status: 'shipped',
      status: 'any',
      order: 'created_at asc'
    };
    if (since_id) {
      params.since_id = since_id
    }
    let resp = await shopify.order.list(params);
    cacher.setCache(shopName, resp[0].id);
    res.set('Content-Type', 'application/json');
    res.send(JSON.stringify({orders: resp}))
  } catch (e) {
    console.log('err', e);
    res.status(500);
    res.send(e)
  }
};

module.exports = {
  getOrderFulfilled
};
