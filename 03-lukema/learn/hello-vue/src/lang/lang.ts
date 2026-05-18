import Vue from 'vue';
import VueI18n from 'vue-i18n';
import Vuex from "vuex";
import cn from '@/lang/cn.json';
import en from '@/lang/en.json';


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

export default i18n;

