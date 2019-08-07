const CronJob = require('cron').CronJob;
const shopifyService = require('./shopify.service');

// let ordersScheduler = function (io) {
//   new CronJob('*/10 * * * *', async function () {
//     console.log('you will see this message every 10 minute');
//     const data = await shopifyService.getListOrder();
//     io.to('1').emit('update_order', {data: data})
//   }, null, true, 'America/Los_Angeles');
// };

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
  // ordersScheduler(io);
  return server;
};

module.exports = init;
