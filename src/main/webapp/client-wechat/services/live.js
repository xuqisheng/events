var constant = require('../utils/constant.js')
var post = require('./post.js')

function getData(callback) {
    post.post('live/getData', {
        eventId: constant.eventId
    }, callback);
}
module.exports = {
  getData: getData
}