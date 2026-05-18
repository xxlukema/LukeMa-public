module.exports = {
  env: {
    node: true
  },
  extends: ["plugin:vue/essential", "@vue/typescript"],
  parserOptions: {
    parser: "@typescript-eslint/parser"
  },
  root: true,
  rules: {
    "no-console": process.env.NODE_ENV === "production" ? "error" : "off",
    "no-debugger": process.env.NODE_ENV === "production" ? "error" : "off"
  }
};
