module.exports = {
    devServer: {
        host: 'localhost'
    },
    runtimeCompiler: true,
    publicPath: process.env.NODE_ENV === 'production' ? '/hello-vue/' : '/'
};
