import { Component, Vue } from "vue-property-decorator";

@Component({
  props: ['title', 'body', 'confirmLabel', 'cancelLabel']
})
export default class ConfirmDialog extends Vue {

  active = false;
  confirmed = false;

  launch() {
    this.active = true;

    return new Promise<Boolean>((resolve, reject) => {
      const interval = setInterval(() => {
        if (!this.active) {
          clearInterval(interval);
          resolve(this.confirmed);
        }
      }, 50);
    });
  }

  closeModal() {
    this.active = false;
  }

  /**
   * Optional. Prefer Promise to event handling. 
   * This is to demo the use of $emit()
   */
  cancel() {
    this.confirmed = false;
    this.$emit('event-cancel');
    this.closeModal();
  }

  /**
   * Optional. Prefer Promise to event handling. 
   * This is to demo the use of $emit()
   */
  confirm() {
    this.confirmed = true;
    this.$emit('event-confirm');
    this.closeModal();
  }
}

