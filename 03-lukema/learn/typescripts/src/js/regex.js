
let re = /{\w+}\s{\w+}/;
let str = 'John Doe Smith';
let newstr = str.replace(re, '$2, $1');
console.log(newstr);  // Smith, John

