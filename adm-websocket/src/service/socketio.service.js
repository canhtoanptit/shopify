const CronJob = require('cron').CronJob;

let ordersScheduler = function (io) {
  new CronJob('*/1 * * * *', function () {
    console.log('you will see this message every minute');
    io.to('1').emit('update_order', {data: 'hello'})
  }, null, true, 'America/Los_Angeles');
};

let ioEvents = function (io) {
  io.on('connection', function (socket) {
    console.log('socket connected : ', socket.id);
    socket.on('authentication', function (msg) {
      // if authenticated success join room of client id
      console.log('authen ', msg);
      if (msg) {
        socket.join('1');
      }
    })
  })
};

let init = function (app) {
  const server = require('http').createServer(app);
  const io = require('socket.io')(server);
  ioEvents(io);
  ordersScheduler(io);
  return server;
};

module.exports = init;
