/**
 * Created by kasper on 4/20/16.
 */

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var ChatLogSchema = new Schema({
    username: String,
    message: String,
    postedAt: Date
});

module.exports = mongoose.model('ChatLog', ChatLogSchema);