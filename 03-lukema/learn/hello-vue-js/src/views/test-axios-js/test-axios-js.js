import axios from 'axios';
import { environment } from '../../../environments/environment';

export default {
  name: 'test-axios-js',
  components: {},
  props: [],
  data () {
    return {
      loading: false,
      retMsg: '',
      errMsg: '',
      url: environment.baseUrl + '/studyarea/getStudyArea'
    };
  },
  computed: {

  },
  mounted () {
    console.log('test-axio mounted.');

    this.loading = false;
    this.retMsg = 'GET: response here';
    this.errMsg = 'GET: error message here';
  },
  methods: {

    clearAll () {
      console.log('Called clearAll().');

      this.loading = false;
      this.retMsg = 'GET: Called clearAll()';
      this.errMsg = 'GET: No error for clearAll()';
    },

    doGet () {
      console.log('Calling doGet()...');

      this.loading = true;
      this.retMsg = 'GET: Please wait...';
      this.errMsg = 'GET: No error yet...';

      axios
        .get(this.url, {
          params: {
            sac: 100003
          },
          withCredentials: true
        })
        .then((response) => {
          const msg = 'Response data: id=' + response.data.interimPeriodEndDate + ', holdingCompanyName=' + response.data.holdingCompanyName;

          console.log('GET received: ' + msg);

          this.retMsg = msg;
          this.errMsg = 'GET: success.';
        })
        .catch(error => {
          console.log('GET: ERROR', error);

          this.retMsg = 'GET: with error';
          this.errMsg = JSON.stringify(error, undefined, 2);
        })
        .finally(() => {
          this.loading = false;
          console.log('GET: finalize.');
        });
    }

  }
};
