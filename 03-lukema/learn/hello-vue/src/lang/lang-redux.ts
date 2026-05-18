import Vue from 'vue';
import VueI18n from 'vue-i18n';
import Vuex from "vuex";
import cn from './cn.json';
import en from './en.json';



/**
 * Vue Multiple Language
 * https://kuanhsuh.github.io/2017/09/16/How-to-implement-multi-language-with-Vue-i18n/
 */
Vue.use(Vuex);
Vue.use(VueI18n);

const messages = {
    en: en,
    cn: cn
}

const i18n = new VueI18n({
    locale: 'en',
    messages: messages
})

// export default i18n;

const ActionTypes = {
    SET_LANG: 'SET_LANG'
}

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
        i18n: i18n
    },
    getters: {
        lang: state => {
            return state.i18n.locale;
        }
    },
    /**
     * Mutations are synchronous.
     */
    mutations: {
        [ActionTypes.SET_LANG]: (state, payload: string) => {
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
        [ActionTypes.SET_LANG]: async (context, payload) => {
            context.commit(ActionTypes.SET_LANG, payload);
        }
    }
});
