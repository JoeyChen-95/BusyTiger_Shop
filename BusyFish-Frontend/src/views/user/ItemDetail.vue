<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>

    <h1>Name: {{this.currentItem.name}}</h1>
    <h1>Price: {{this.currentItem.price}}</h1>
    <h1>Tag: {{this.currentItem.tag}}</h1>
    <h1>Status: {{this.currentItem.status}}</h1>
    <h1>Description: {{this.currentItem.description}}</h1>

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
  name: "ItemDetail",
  data(){
    return{
      currentItemId:'',
      currentItem:{
        id:'',
        name:''
      }
    }
  },
  methods:{
    getCurrentItem(){
      var currentURL=window.location.href
      var reg=/itemDetail\/(.*)/
      this.currentItemId=currentURL.match(reg)[1]
      AXIOS.get('/item/selectItemById?itemId='+this.currentItemId)
        .then(response=>{
          this.currentItem=response.data
        })
    }

  },
  created() {
    this.getCurrentItem()
  }
}
</script>

<style scoped>

</style>
