const pool = require("./db.connector");

const findAll = async (limit, offset) => {
  const sql = "select * from tracking order by created_at desc litmit ? offset ?";
  return pool.query(sql, [limit, offset])
};

const insertAll = async (trackers) => {
  const sql = "insert into tracker(transaction_id, tracking_number) values ?";
  return pool.query(sql, [trackers])
};

module.exports = {
  findAll,
  insertAll
};