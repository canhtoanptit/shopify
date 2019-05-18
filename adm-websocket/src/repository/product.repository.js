const pool = require("./db.connector");

const insertAllProduct = async (variants) => {
  const sql = "INSERT INTO variants(id, product_id, title, product_title, jhi_cost, price) VALUES ?";
  return await pool.query(sql, [variants]);
};

const findAllProductById = async (ids) => {
  console.log('findAllCostById ', ids);
  const sql = "SELECT id, jhi_cost FROM `variants` WHERE `id` IN (?)";
  return await pool.query(sql, [ids])
};

const updateProductMo = (id, mo) => {
  console.log('updateProductMo');
  const sql = "UPDATE `variants` SET `mo` = ? WHERE `id` = ?";
  return pool.query(sql, [id, mo])
};

module.exports = {
  insertAllProduct,
  findAllProductById,
  updateProductMo
};
