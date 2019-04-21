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

module.exports = {
  convertMapToArray
};
