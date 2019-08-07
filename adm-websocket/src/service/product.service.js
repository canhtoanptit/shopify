const productRepo = require('../repository/product.repository');
const CronJob = require('cron').CronJob;
const shopifyService = require('./shopify.service');

// new CronJob('*/15 * * * *', async function () {
//   try {
//     console.log('Update sync product every 15 minutes');
//     shopifyService.getAllProduct();
//   } catch (e) {
//     console.log('error when sync variant ', e)
//   }
//
// }, null, true, 'America/Los_Angeles');

const updateProductMO = (id, mo) => {
  return productRepo.updateProductMo(id, mo)
};

module.exports = {
  updateProductMO
};
