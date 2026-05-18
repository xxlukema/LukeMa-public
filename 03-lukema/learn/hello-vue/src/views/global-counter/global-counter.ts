import Vue from 'vue';
import { Component, Watch } from 'vue-property-decorator';
import { GlobalStore } from '@/views/global/global-store';


@Component
export default class App extends Vue {

  counter: number = GlobalStore.state.counter;
  mutated: boolean = false;

  @Watch('mutated')
  updateCounter(newValue: boolean, oldValue: boolean) {
    this.counter = GlobalStore.state.counter;
  }

  add() {
    GlobalStore.increment();
    this.mutated = !this.mutated;
  }

  deduct() {
    GlobalStore.decrement();
    this.mutated = !this.mutated;
  }
};