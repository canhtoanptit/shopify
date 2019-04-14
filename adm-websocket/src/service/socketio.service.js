let ioEvents = function (io) {

};

let init = function (app) {
  const server = require('http').createServer(app);
  const io = require('socket.io')(server);
  ioEvents(io);

  return server;
};

module.exports = init;
