import ConfirmDialog from "@/components/confirm-dialog/index.vue"; // @ is an alias to /src


export default {
  name: 'Confirm',
  components: {
    ConfirmDialog
  },
  data() {
    return {
      confirmed: true
    };
  },
  methods: {
    launch() {
      this.$refs.confirmRef.launch().then((confirmed) => {
        console.log('App: Dialog closed.');
        if (confirmed) {
          console.log('App: confirm Promise received');
          this.confirmed = true;
        } else {
          console.log('App: cancel Promise received');
          this.confirmed = false;
        }
      });
    },
    confirm() {
      console.log('Confirm: confirm event heard.');
    },
    cancel() {
      console.log('Confirm: cancel event heard.');
    }
  }
};
