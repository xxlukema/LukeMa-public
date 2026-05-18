
import { Component, Mixins, Vue } from 'vue-property-decorator';

@Component
export default class MyMixin extends Vue {
    protected mixinData = 'Data from MyMixin';
    protected mixinOverride = 'MyMixin message to be overriden';

    mixinFunc(): void {
        console.log('From MyMixin', this.mixinData, this.mixinOverride);
    }
}

