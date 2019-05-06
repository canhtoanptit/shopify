function convertMapToArray(map) {
  const ary = [];
  map.forEach((value, key) => {
    ary.push({
      variant_id: key,
      data: value
    })
  });
  return ary;
}

function convertProductToVariants(product) {
  const rs = [];
  product.forEach((product) => {
    product.variants.forEach(variant => {
      rs.push([variant.id, product.id, variant.title, product.title, 0, Number.parseFloat(variant.price)])
    })
  });

  return rs;
}

module.exports = {
  convertMapToArray,
  convertProductToVariants
};
