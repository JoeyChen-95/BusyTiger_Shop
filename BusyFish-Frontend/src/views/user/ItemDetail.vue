<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>
    <b-card img-src="https://placekitten.com/300/300" img-alt="Card image" img-left class="mb-3">
      <b-card-text>
        <span class="item-desc-key">Name: {{this.currentItem.name}}</span>
      </b-card-text>
      <b-card-text>
        <span class="item-desc-value">Price: {{this.currentItem.price}}</span>
      </b-card-text>


      <b-card-text>
        <span class="item-desc-key">Status:<b-badge v-bind:variant="this.currentItem.status=='ACTIVE'?'success':this.currentItem.status=='SOLD'?'danger':'warning'">{{this.currentItem.status}}</b-badge></span>

      </b-card-text>

      <b-card-text>
        <span class="item-desc-key">Tag: <b-badge v-bind:href="'/recommend/'+this.currentItem.tag" variant="primary">{{this.currentItem.tag}}</b-badge> </span>
      </b-card-text>

      <b-card-text>
        <span class="item-desc-key">Description:{{this.currentItem.description}} </span>
      </b-card-text>

      <b-card-text>
        <b-button variant="primary" size="sm" class="item-desc-key" v-bind:href="'/otherUserProfile/userId='+this.currentItem.sellerId">View Seller Profile</b-button>
      </b-card-text>
    </b-card>

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
.item-desc-key{
  font-size: 35px;
  font-weight: bold;
}
.item-desc-value{
  font-size: 30px;
  font-weight: bold;
}

</style>
