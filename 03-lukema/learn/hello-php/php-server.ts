// import phpServer from 'php-server';

const phpServer = require('php-server');

const params = {
    port: 8080,
    base: "public"
};

(async () => {
    const server = await phpServer(params);
    console.log(`PHP server running at ${server.url}`);
})();