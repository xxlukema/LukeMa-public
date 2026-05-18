
/**
 * Vue.component globally a component. The component is globally available.
 * 
 * When using a component directly in the DOM, custom tag name is "all-lowercase, must contain a hyphen".
 * When using a component as SingleFileComponenet, custom tag name is "CamelCase".
 * 
 * In most projects, component names should always be PascalCase in single-file components and string 
 * templates - but kebab-case in DOM templates.
 * 
 * React recommends using inline styles. When we want to set inline styles, we need to use camelCase syntax. 
 * React will also automatically append px after the number value on specific elements.
 * 
 * React Naming Convention: 
 *    - HTML tags always use lowercase tag names, while React components start with Uppercase.
 *    - Note − You should use className and htmlFor as XML attribute names instead of class and for.
 * 
 * Component Local Registration: 
 *    - components: {'component-a': ComponentA},
 * 
 */
Vue.component("todo-item", {
  template: "<li>This is a global todo</li>"
});

Vue.component("todo-item-2", {
  props: ["todo", "foo"],
  template: "<li>Also global: {{ todo.text }}</li>"
});

const app = new Vue({
  el: "#app"
});

/**
 * JSX is not working for stand-alone
 */
/*
const app = new Vue({
  el: "#app",
  render: function (h) {
    return (
      <div data-attrib={"attrib"}>
        <h2>Hello JSX!</h2>
      </div>
    );
  }
});
*/

const groceryList = [
  {
    id: 0,
    text: "Vegetables"
  },
  {
    id: 1,
    text: "Cheese 2"
  },
  {
    id: 2,
    text: "Whatever else humans are supposed to eat"
  }
];

const foo = "Init valie of foo";
Object.freeze(foo);

const app7 = new Vue({
  el: "#app-7",
  data: function () {
    return {
      groceryList: groceryList,
      foo: foo
    };
  },
  watch: {
    /**
     * Don't use arrow functions on an options property or callback,
     * since arrow functions are bound to the parent context.
     */
    foo: function (val, oldVal) {
      console.log('new: "%s", old: "%s"', val, oldVal);
    }
  }
});

/**
 * Don't use arrow functions on an options property or callback,
 * since arrow functions are bound to the parent context.
 */
app7.$watch("foo", (val, oldVal) => {
  console.log('Not recommended but working: new: "%s", old: "%s"', val, oldVal);
});

const example1 = new Vue({
  el: "#add-1",
  data() {
    return {
      counter: 0
    };
  }
});
