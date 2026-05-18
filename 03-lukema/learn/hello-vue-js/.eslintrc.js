module.exports = {
  root: true,
  env: {
    node: true
  },
  'extends': [
    'plugin:vue/essential',
    '@vue/standard'
  ],
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'semi': [2, 'always']
    /* "quotes": [2, ["double", "single", "backtick"], { "avoidEscape": true, "allowTemplateLiterals": true }], */
  },
  parserOptions: {
    parser: 'babel-eslint',
    ecmaVersion: 13,
    sourceType: 'module',
    ecmaFeatures: {
      jsx: true
    }
  }
};
