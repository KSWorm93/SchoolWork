/**
 * Created by kasper on 3/3/16.
 */
var express = require('express');
var api = express.Router();
var jokes = require('../model/jokes');

api.get('/api/allJokes', function(req, res, next) {
    return res.send({jokes: jokes.allJokes});
});

api.get('/api/randomJoke', function(req, res, next) {
    return res.send({joke: jokes.getRandomJoke()});
});

api.post('/api/addJoke', function(req, res, next) {
    var joke = req.body.joke;
    jokes.addJoke(joke);
});

module.exports = api;