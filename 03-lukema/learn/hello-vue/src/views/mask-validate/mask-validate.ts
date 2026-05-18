import HelloWorld from "@/components/HelloWorld.vue"; // @ is an alias to /src
import { extend, ValidationProvider } from 'vee-validate';
import { required } from 'vee-validate/dist/rules';
import { Component, Vue } from "vue-property-decorator";

Vue.component('ValidationProvider', ValidationProvider);


extend('secret', {
  validate: value => {
    if (value === 'www') {
      return true;
    } else {
      return false;
    }
  },
  message: 'It has to be www.'
});

extend('between', {
  validate: (value, { min, max }: any) => {

    return value >= min && value <= max;
  },
  params: ['min', 'max'],
  message: 'The value must be between {min} and {max}'
});


extend('required', {
  ...required,
  message: 'Household size is required'
}
);

/*
extend('required1', {
  validate: value => {
    console.log('value=', value);

    if (typeof value == undefined || value == null || value === '') {
      return false;
    } else {
      return true;
    }
  },
  message: 'Household size is required'
});


/*
extend('between', {
  params: ['min', 'max'],
  validate(value, { min, max }) {
    return Number(value) <= max && Number(value) >= min;
  },
  message: 'Household size is between ' + min + ' and ' + max
});
*/

@Component({
  components: {
    HelloWorld,
    ValidationProvider
  }
})
export default class Home extends Vue {

  $refs!: {
    files: HTMLFormElement
  }

  phone = '';
  email = '';
  email2 = '';

  files: File[] = [];

  handleFileUploads() {
    console.log('handleFileUpload', this.files);

    this.files.push(...this.$refs.files.files);

  }

  submitFiles() {
    console.log('submitFile');

    let formData = new FormData();

    for (let i = 0; i < this.files.length; i++) {
      let file = this.files[i];

      formData.append('files[' + i + ']', file);

      console.log('files[' + i + ']', file);

      /*
      axios.post( '/multiple-files',
          formData,
          {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
          }
        ).then(function(){
          console.log('SUCCESS!!');
        })
        .catch(function(){
          console.log('FAILURE!!');
        });
        */
    }

  }
}
