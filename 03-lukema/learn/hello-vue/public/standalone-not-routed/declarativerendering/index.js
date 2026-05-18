var app = new Vue({
  el: "#app",
  data() {
    return {
      message: "Hello Vue!"
    };
  }
});

var app5 = new Vue({
  el: "#app-5",
  data: function () {
    return {
      message: "Hello Vue.js!"
    };
  },
  methods: {
    reverseMessage: function () {
      this.message = this.message
        .split("")
        .reverse()
        .join("");
    }
  }
});

var app6 = new Vue({
  el: "#app-6",
  data: function () {
    return {
      message: "Hello Vue!"
    };
  }
});

var example1 = new Vue({
  el: "#add-1",
  data() {
    return {
      counter: 0
    };
  }
});
