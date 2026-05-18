import axios from "axios";
import Vue from "vue";
import { Component } from "vue-property-decorator";
import { environment } from "../../../environments/environment";


interface Greeting {
  id: number;
  content: string;
}

@Component
export default class TestAxios extends Vue {

  url = environment.baseUrl + "/spring/slowget";

  loading = false;
  retMsg = "";
  errMsg = "";

  /**
   * Step 1. 
   */
  beforeCreate() {
    console.log('test-axio: beforeCreate.');
  }

  /**
   * Step 2. 
   */
  created() {
    console.log('test-axio: created.');
  }

  /**
   * Step 3. 
   */
  mounted() {
    console.log("test-axio: mounted.");
  }

  /**
   * Step 4. 
   */
  beforeMount() {
    console.log("test-axio: beforeMount.");
  }

  /**
   * Step 5. 
   */
  beforeUpdate() {
    console.log("test-axio: beforeUpdate.");
  }

  /**
   * Step 6. 
   */
  updated() {
    console.log("test-axio: updated.");
  }

  /**
   * Step 7. 
   */
  beforeDestroy() {
    console.log("test-axio: beforeDestroy.");
  }

  /**
   * Step 8. 
   */
  destroyed() {
    console.log("test-axio: destroyed.");
  }

  doGet() {
    console.log("Calling doGet()...");

    this.loading = true;
    this.retMsg = "GET: Please wait...";
    this.errMsg = "GET: No error yet...";

    axios.get<Greeting>(this.url, {
      params: {
        name: "Luke Ma"
      }
    })
      .then((response) => {
        const msg = "Response data: id=" + response.data.id + ", content=" + response.data.content;

        console.log("GET received: " + msg);

        this.retMsg = msg;
        this.errMsg = "GET: success.";
      })
      .catch(error => {
        console.log("GET: ERROR", error);

        this.retMsg = "GET: with error";
        this.errMsg = JSON.stringify(error, undefined, 2);
      })
      .finally(() => {
        this.loading = false;
        console.log("GET: finalize.");
      });
  }

  clearAll() {
    console.log("Called clearAll().");

    this.loading = false;
    this.retMsg = "GET: Called clearAll()";
    this.errMsg = "GET: No error for clearAll()";
  }

}
