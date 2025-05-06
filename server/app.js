var createError = require('http-errors');
var express = require('express');
var path = require('path');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
require('dotenv').config();
var apiRouter = require('./routes/api');
const database = require('./config/db');

var app = express();

// ❌ Bỏ cấu hình view engine vì không dùng HTML render
// app.set('views', path.join(__dirname, 'views'));
// app.set('view engine', 'hbs');
app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use("/uploads", express.static(path.join(__dirname, "public/uploads")));
app.use('/api', apiRouter);

// Kết nối database
database.connect();

// Xử lý lỗi 404
app.use(function(req, res, next) {
  next(createError(404));
});

// Error handler
app.use(function(err, req, res, next) {
  res.status(err.status || 500).json({
    message: err.message,
    error: req.app.get('env') === 'development' ? err : {}
  });
});

module.exports = app;
