<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>
    <div>
      <b-img left v-bind:src="currentItemImgURL" alt="Sorry, this item has no images." height="350px" style="padding-top: 20px"></b-img>
    </div>
    <div>
      <b-card img-width="800px" img-height="800px" img-alt="Card image" img-left class="mb-3" >
        <h2 class="item-desc-key">Name</h2>
        <p class="item-desc-value">{{this.currentItem.name}}</p>
        <h2 class="item-desc-key">Price</h2>
        <p class="item-desc-value">{{this.currentItem.price}}</p>
        <h2 class="item-desc-key">Status</h2>
        <p class="item-desc-value"><b-badge href="/hint/itemStatus" v-bind:variant="this.currentItem.status=='ACTIVE'?'success':this.currentItem.status=='SOLD'?'danger':'warning'">{{this.currentItem.status}}</b-badge></p>
        <h2 class="item-desc-key">Tag</h2>
        <p class="item-desc-value"><b-badge v-bind:href="'/recommend/'+this.currentItem.tag" variant="primary">{{this.currentItem.tag}}</b-badge></p>
        <h2 class="item-desc-key">Description</h2>
        <p class="item-desc-value">{{this.currentItem.description}}</p>


        <b-button squared variant="outline-primary" size="sm" class="item-desc-value" v-bind:href="'/otherUserProfile/userId='+this.currentItem.sellerId">View Seller Profile</b-button>

      </b-card>


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
  name: "ItemDetail",
  data(){
    return{
      currentItemId:'',
      currentItem:{
        id:'',
        name:''
      },
      currentItemImgURL:''
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
          this.currentItemImgURL=config.dev.itemImgDirPath+'/item_img_'+response.data.id+'.jpg'
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
  font-size: 32px;
  font-weight: bold;
}
.item-desc-value{
  font-size: 25px;
}

</style>
