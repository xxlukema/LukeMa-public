import App from "@/App/index.vue";
import FluxStore from "@/flux-store";
import "@/registerServiceWorker";
import router from "@/router";
import VueMask from 'v-mask';
import VeeValidate from 'vee-validate';
import Vue from "vue";

Vue.config.productionTip = false;

Vue.use(VueMask);

Vue.use(VeeValidate);

new Vue({
  router,

  /**
   * Provide the store using the "store" option. This will inject the store instance to all child components.
   * All child components of the root will be available on them as 'this.$store.state'
   * 
   * https://vuex.vuejs.org/guide/state.html
   */
  store: FluxStore,

  render: h => h(App)
}).$mount("#app");
