# JSON - JavaScript Object Notation

## Syntax

1. Data is in name/value pairs
2. Data is separated by commas
3. Curly braces hold objects
4. Square brackets hold arrays

Example

    {
      "name": "John",
      "age": 20,
      "isEmployed": true
    }

## Keys

### In JSON, keys **must** be **strings**, written with **double quotes**

    {"name":"John"}

### JavaScript - keys can be strings, numbers, or identifier names

    # In JavaScript, key can be without quotes is it is a single word
    {name:"John"}

## JSON Values

### In JSON, values **must** be one of the following data types

    a string
    a number
    an object
    an array
    a boolean
    null

### In JavaScript values can be all of the above, **plus** any other valid JavaScript expression, including

    a function
    a date
    undefined

### In JSON, string values must be written with **double quotes**

#### JSON

    {"name":"John"}

#### In JavaScript, (1) you can write string values with **double** or **single** quotes, (2) key can be without quotes is it is a single word

    # JavaScript - (1) you can write string values with **double** or **single** quotes, (2) key can be without quotes is it is a single word
    {name:'John'}

## Read/Write JOSN

    person = {name:"John", age:31, city:"New York"};

    person.name;
    person["name"];
    person.name = "Gilbert";
    person["name"] = "Gilbert"; 

## Create a json object in JavaScript

    export interface Person {
      fname?: string;  /** comma is OK in JavaScript `,` */
      lname?: string;  /** comma is OK in JavaScript `,` */
    }
    
    const fname = 'Luke';
    const lname = 'Ma';

    // concise
    const person: Person = {
      fname,
      lname
    };
    //
    // or
    const person: Person = {
      fname: fname, /** use comman not semicolon because this is inside a josn body */
      lname: lname, /** last property momma is optional. perfer no comma. */
    };
    //
    // or verbose
    const person: Person = {
      'fname', fname,
      'lname', lname
    };
