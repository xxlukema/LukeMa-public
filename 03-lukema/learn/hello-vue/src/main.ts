import App from "@/App/index.vue";
import FluxStore from "@/flux-store";
import i18n from '@/lang/lang';
import "@/registerServiceWorker";
import router from "@/router";
import VueMask from 'v-mask';
import { ValidationProvider } from 'vee-validate';
import Vue from "vue";


Vue.config.productionTip = false;

Vue.use(VueMask);

Vue.component('ValidationProvider', ValidationProvider);

new Vue({
  router,

  /**
   * Why this line is needed?
   * i18n
   */
  i18n,

  /**
   * Provide the store using the "store" option. This will inject the store instance to all child components.
   * All child components of the root will be available on them as 'this.$store.state'
   * 
   * https://vuex.vuejs.org/guide/state.html
   */
  store: FluxStore,

  render: h => h(App)
}).$mount("#app");
