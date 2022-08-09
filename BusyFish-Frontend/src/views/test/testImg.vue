<template>
  <div>
    <b-form-file v-model="file1" class="mt-3" plain></b-form-file>
    <b-form-input v-model="userId"></b-form-input>
    <b-button @click="uploadImg(file1,userId)">Upload</b-button>

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
      file1:null,
      userId:null

    }
  },
  methods:{
    uploadImg(file,userId){
      var form_data=new FormData()
      form_data.append('file',file)
      form_data.append('userId',userId)
      AXIOS.post('/user/addImg',form_data,{})
    }

  },
  created() {

  }
}
</script>

<style scoped>

</style>
