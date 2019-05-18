const pool = require("./db.connector");

const insertAllProduct = async (variants) => {
  const sql = "INSERT IGNORE INTO variants(id, product_id, title, product_title, jhi_cost, price) VALUES ?";
  return await pool.query(sql, [variants]);
};

const findAllProductById = async (ids) => {
  const sql = "SELECT id, jhi_cost, mo FROM `variants` WHERE `id` IN (?)";
  return await pool.query(sql, [ids])
};

const updateProductMo = (id, mo) => {
  console.log('updateProductMo ' + id + ' ' + mo);
  const sql = "UPDATE `variants` SET `mo` = ? WHERE `id` = ?";
  return pool.query(sql, [mo, id])
};

module.exports = {
  insertAllProduct,
  findAllProductById,
  updateProductMo
};
