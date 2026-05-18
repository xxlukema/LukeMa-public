import HelloWorld from "@/components/HelloWorld.vue"; // @ is an alias to /src
import { Component, Mixins } from "vue-property-decorator";
import MyMixin from './my-mixins';


@Component({
  components: {
    HelloWorld
  }
})
export default class Home extends Mixins(MyMixin) {

  protected mixinOverride = 'Override MyMixin by Component';

  public mounted(): void {
    console.log(this.mixinData);
    this.mixinFunc();
  }

}
