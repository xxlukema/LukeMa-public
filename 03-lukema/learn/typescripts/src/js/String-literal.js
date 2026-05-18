

/**
 * This is ES2015 template literals.
 *
 **/

var str1 = 'John\'s quote.';

console.log(str1);

var str2 =
`In JavaScript template strings can run
 over multiple lines, but double and single
 quoted strings cannot.`;

console.log(str2);

// String interpolation
var name = 'Bob'; var time = 'today';
var str3 = `Hello ${name}, how are you ${time}?`;

console.log(str3);


/**
 * Construct an HTTP request prefix is used to interpret the replacements and construction
 * POST `http://foo.org/bar?a=${a}&b=${b}
 *       Content-Type: application/json
 *       X-Credentials: ${credentials}
 *       { "foo": ${foo},
 *         "bar": ${bar}}` (myOnReadyStateChangeHandler);
 **/




