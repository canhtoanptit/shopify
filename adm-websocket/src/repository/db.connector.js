const mysql = require("mysql");
const Config = require("../config/config");
const util = require('util');

const pool = mysql.createPool({
  connectionLimit: 10,
  host: Config.db.host,
  user: Config.db.user,
  password: Config.db.password,
  database: Config.db.database
});

pool.getConnection((err, connection) => {
  if (err) {
    if (err.code === 'PROTOCOL_CONNECTION_LOST') {
      console.error('Database connection was closed.')
    }
    if (err.code === 'ER_CON_COUNT_ERROR') {
      console.error('Database has too many connections.')
    }
    if (err.code === 'ECONNREFUSED') {
      console.error('Database connection was refused.')
    }
  }

  if (connection) connection.release();
});

pool.query = util.promisify(pool.query);

module.exports = pool;
