<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>

  </div>
</template>

<script>
import axios from 'axios'

var config = require('../../../config')
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: {'Access-Control-Allow-Origin': frontendUrl,
  },
  withCredentials:true,
  crossDomain:true
})


export default {
  name: "Format",
  data(){
    return{

    }
  },
  methods:{

  },
  created() {

  }
}
</script>

<style scoped>

</style>
