const pool = require("./db.connector");

const insertAllProduct = async (variants) => {
  const sql = "INSERT INTO Variants(id, product_id, title, product_title, jhi_cost, price) VALUES ?";
  return await pool.query(sql, [variants]);
};

module.exports = {
  insertAllProduct
};
