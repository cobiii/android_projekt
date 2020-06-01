const express = require('express');
const app = express();
var bodyParser = require('body-parser');

// parse application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: false }))

// parse application/json
app.use(bodyParser.json())

//povezava z bazo
var mongoose = require('mongoose');
//Set up default mongoose connection
var mongoDB = 'mongodb+srv://admin:admin@movies-jhvre.mongodb.net/data';
mongoose.connect(mongoDB);
// Get Mongoose to use the global promise library
mongoose.Promise = global.Promise;
//Get the default connection
var db = mongoose.connection;

//Bind connection to error event (to get notification of connection errors)
db.on('error', console.error.bind(console, 'MongoDB connection error:'));

var movieSchema = new mongoose.Schema({
  title: String,
  synopsis: String,
  genre: String,
  director: String,
  writer: String,
  boxoffice: String,
  runningTimeStr: String,
  studio: String,
  poster: String,
  rating : Number,
  ratingCount: Number,
  year: Number
});

var Movie = mongoose.model('Movie', movieSchema);


app.listen(3000, function() {
  console.log('listening on 3000')
});

const request = require('request');

app.get('/', function(req, res) {
  res.send('Hello World');
});

app.get('/movies', function(req, res) {
  Movie.find().exec(function (err, movies) {
    return res.json(movies);
  });
});

app.get('/addrandom/:int', function(req, res) {
  console.log(parseInt(req.params.int)+5);
  for (i = req.params.int; i < parseInt(req.params.int)+5; i++) { 
    request('https://www.rottentomatoes.com/api/private/v1.0/movies/'+i, function (error, response, body) {
      console.error('error:', error);
      console.log('statusCode:', response && response.statusCode);
      var parsed = JSON.parse(body);
      console.log(parsed.ratingSummary.audience);
      try {
        Movie.create({ title: parsed.title, synopsis: parsed.synopsis, genre: parsed.genreSet[0].displayName, director: parsed.casts.directors[0].name, writer: parsed.casts.screenwriters[0].name, boxoffice: parsed.boxoffice, runningTimeStr: parsed.runningTimeStr, studio: parsed.studio, poster: parsed.posters.detailed, rating: parsed.ratingSummary.audience.averageScore, ratingCount:  parsed.ratingSummary.audience.numTotal, year: parsed.year}, function (err, small) {
          if (err) return handleError(err);
        });
      }catch(e) {
        console.log(e);
      }
      //console.log('body:', parsed);
    });
  } 
});