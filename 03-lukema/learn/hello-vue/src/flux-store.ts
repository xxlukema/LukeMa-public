import i18n from '@/lang/lang';
import Vue from "vue";
import Vuex from "vuex";


Vue.use(Vuex);


/**
 * At the center of every Vuex application is the store. A "store" is basically a container that
 * holds your application state. There are two things that make a Vuex store different from a 
 * plain global object:
 * 
 * 1. Vuex stores are reactive. When Vue components retrieve state from it, they will reactively
 *    and efficiently update if the store's state changes.
 * 2. You cannot directly mutate the store's state. The only way to change a store's state is by
 *    explicitly committing mutations. This ensures every state change leaves a track-able record,
 *    and enables tooling that helps us better understand our applications
 */
export default new Vuex.Store({
  state: {
    count: 20,
    i18n: i18n
  },
  getters: {
    COUNT: state => {
      return state.count
    },
    LANG: state => {
      return state.i18n.locale;
    }
  },
  /**
   * Mutations are synchronous.
   */
  mutations: {
    INCREMENT: (state, payload: any) => {
      state.count += payload;
    },
    DECREMENT: (state, payload: any) => {
      state.count -= payload;
    },
    SET_LANG: (state, payload: string) => {
      state.i18n.locale = payload;
    }
  },
  /**
   * Actions are not synchronous.
   * Make it a practice to never commit your Mutations directly. Always use Actions to commit your mutations.
   * 
   * https://medium.com/dailyjs/mastering-vuex-zero-to-hero-e0ca1f421d45
   */
  actions: {
    DO_INCREMENT: async (context, payload) => {
      context.commit('INCREMENT', payload);
    },
    DO_DECREMENT: async (context, payload) => {
      context.commit('DECREMENT', payload.count);
    },
    SET_LANG: async (context, payload) => {
      context.commit('SET_LANG', payload.lang);
    }
  }
});
