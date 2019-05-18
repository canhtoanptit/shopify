const productRepo = require('../repository/product.repository');

const updateProductMO = (id, mo) => {
  return productRepo.updateProductMo(id, mo)
};

module.exports = {
  updateProductMO
};