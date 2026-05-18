module.exports = {
  plugins: {
    autoprefixer: {}
  },
  runtimeCompiler: true,
  publicPath: process.env.NODE_ENV === 'production' ? '/hello-vue-js/' : '/'
};
