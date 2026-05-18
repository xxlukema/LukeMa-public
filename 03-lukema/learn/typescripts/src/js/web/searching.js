const http = require('http');
const os = require('os');
const port = 3000;

const requestHandler = (request, response) => {
  console.log(request.url)
  setTimeout(function() {
    response.end('Hello, This is machine learning model hyper parameters searching Server! from ' + os.hostname())
    }, 300);

}

const server = http.createServer(requestHandler)

server.listen(port, (err) => {
  if (err) {
    return console.log('something bad happened', err)
  }

  console.log(`server is listening on ${port}`)
})
