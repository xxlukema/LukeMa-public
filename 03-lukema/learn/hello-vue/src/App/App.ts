import Vue from 'vue';
import { Component } from "vue-property-decorator";


@Component({
    components: {}
})
export default class App extends Vue {

    public setLang(lang: string): void {
        this.$store.dispatch({
            type: 'SET_LANG',
            lang: lang
        })
    }

    public getLang(): string {
        return this.$store.getters.LANG;
    }
}
