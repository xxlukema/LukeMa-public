'user strict';

// import { environment } from './environments/environment';
const environment = require('./environments/environment');

const env = environment.ecorr;

/**
 * https://expressjs.com/en/starter/installing.html
 */
const express = require('express');

/**
 * https://www.npmjs.com/package/request
 */
const request = require('request');

const app = express();

app.use((req, res, next) => {
  res.header('Access-Control-Allow-Origin', '*');
  res.header('Access-Control-Allow-Methods', 'POST, GET, PUT, OPTIONS, DELETE');
  res.header('Access-Control-Max-Age', '1600');
  // res.header('Access-Control-Allow-Headers', 'X-Requested-With, WWW-Authenticate, Authorization, Origin, Content-Type, Version, cache-control');
  res.header('Access-Control-Allow-Headers', '*');
  // res.header('Access-Control-Expose-Headers', 'X-Requested-With, WWW-Authenticate, Authorization, Origin, Content-Type');
  res.header('Access-Control-Allow-Credentials', 'true');

  next();
});

app.use(express.static('public'));
// app.use('/static', express.static('public'));
// app.use('/static', express.static(path.join(__dirname, 'public')));

/**
 * root
 */
app.get('/', (req, res) => res.send('Hello World!'));


/**
 * target path
 */
/*
app.get('/data/D_CasesByContact', (req, res) => {

  const options = {
    method: 'GET',
    url: environment.baseUrl + 'data/D_CasesByContact',
    headers: {
      'User-Agent': 'request'
    },
    auth: {
      user: environment.user,
      pass: environment.pass,
      'sendImmediately': true
    },
     // Or rejectUnauthorized: false
    strictSSL: false
  };

  const handler = (error, response, body) => {

    // console.log(response);

    if (error || response.statusCode !== 200) {
      return res.status(500).json({
        type: 'error',
        message: 'error.message'
      });
    }

    res.json(JSON.parse(body));
  };

  request(options, handler);
});
*/


/**
 * Any path
 */
app.get('/**', (req, res) => {

  console.log(req.params[0], req.params);
  // res.send('Hello Any Path: ' + req.params[0]);

  const options = {
    method: 'GET',
    url: env.baseUrl + req.params[0],
    headers: {
      'User-Agent': 'request',
      'Cookie': 'JSESSIONID=336C06AA9106D9279E0784BF7F5686B5'
    },
    auth: {
      user: env.user,
      pass: env.pass,
      'sendImmediately': true
    },
    // Or rejectUnauthorized: false
    strictSSL: false
  };

  const handler = (error, response, body) => {

    // console.log(response);

    if (error) {
      return res.status(500).json({
        type: 'error',
        message: error.message
      });
    }

    if (response.statusCode !== 200) {
      return res.status(response.statusCode).send(body);
    }

    try {
      res.json(JSON.parse(body));
    } catch (error) {
      console.error(error);
      res.send(body);
    }
  };

  request(options, handler);
});


const PORT = process.env.PORT || env.port;
app.listen(PORT, () => console.log(`listening on ${PORT}`));