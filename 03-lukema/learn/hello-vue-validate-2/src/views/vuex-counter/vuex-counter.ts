import { Component, Vue } from "vue-property-decorator";
import { mapState } from 'vuex'


/**
 * Using Vuex doesn't mean you should put all the state in Vuex. Although putting more state into Vuex makes your state 
 * mutations more explicit and debuggable, sometimes it could also make the code more verbose and indirect. If a piece 
 * of state strictly belongs to a single component, it could be just fine leaving it as local state.
 * 
 * https://vuex.vuejs.org/guide/state.html
 * 
 */
@Component({
  components: {
  },
  computed: mapState({
    localCount: (state: any) => state.count
  }),
})
export default class VuexCounter extends Vue {

  /**
   * computed: {}
   * 
   * Whenever store.state.count changes, it will cause the computed property to re-evaluate, and trigger associated DOM updates.
   */
  get count() {
    /**
     * The store had been injected from root. No need to
     *    import store from '../../store';
     * Use this.$store.state to access state
     */
    return this.$store.getters.COUNT;
  }

  add() {
    this.$store.dispatch('DO_INCREMENT', 2);
  }

  deduct() {
    this.$store.dispatch({
      type: 'DO_DECREMENT',
      count: 1
    })
  }

}
