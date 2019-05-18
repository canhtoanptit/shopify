function convertMapToArray(map, product) {
  const ary = [];
  map.forEach((value, key) => {
    let cost = 0;
    let mo = 0;
    if (product && Array.isArray(product)) {
      for (let i = 0; i < product.length; i++) {
        if (product[i].id === key) {
          cost = product[i].jhi_cost;
          mo = product[i].mo;
          break
        }
      }
    }
    ary.push({
      variant_id: key,
      data: value,
      cost: cost,
      mo: mo
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
