import Vue from "vue";
import Router from "vue-router";
import Home from "@/views/home/index.vue";

Vue.use(Router);

export default new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      name: "home",
      component: Home
    },
    {
      path: "/test-axios",
      name: "test-axios",
      component: () => import("./views/test-axios/index.vue")
    },
    {
      path: "/mask-validate",
      name: "mask-validate",
      component: () => import("./views/mask-validate/index.vue")
    },
    {
      path: "/animation",
      name: "animation",
      component: () => import("./views/animation/index.vue")
    },
    {
      path: "/confirm",
      name: "confirm",
      component: () => import("./views/confirm/index.vue")
    },
    {
      path: "/vuex-counter",
      name: "vuex-counter",
      component: () => import("./views/vuex-counter/index.vue")
    },
    {
      path: "/global-counter",
      name: "global-counter",
      component: () => import("./views/global-counter/index.vue")
    },
    {
      path: "/about",
      name: "about",
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("./views/About.vue")
    }
  ]
});
