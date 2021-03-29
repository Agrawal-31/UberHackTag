import axios from "axios";

const setDefaultAxios = (token) => {
  (function () {
    console.log(token);
    if (token) {
      axios.defaults.headers.common["Authorization"] = "Bearer " + token;
        axios.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
        axios.defaults.headers.common["Access-Control-Allow-Headers"] =
          "X-Requested-With";
    } else {
      axios.defaults.headers.common["Authorization"] = null;
    }
  })();
};

export default setDefaultAxios;
