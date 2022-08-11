<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>
    <div>
      <b-jumbotron class="jumbotron-schonbrunner_gelb" v-bind:header="(currentViewCollectionName)" v-bind:lead="'Best quality and service from '+currentViewCollectionName+', just for you.'">
        <b-link style="color: #492D22" href="/recommend/">To buy the item, please refer to “Recommend” page.</b-link>
        <p></p>
        <b-button variant="outline-dark" href="/busyTigerChoice">View more collections</b-button>
      </b-jumbotron>
    </div>
    <div>
      <b-container fluid>
        <b-row cols="3">
          <b-col v-for="item in currentCollectionItems" style="padding-top: 20px" visible="false">
            <b-card bg-variant="light" v-show="item.status!='PRIVATE'">
              <div>
                <h3 class="order-card-order-name-title">{{item.name}} &nbsp;<b-icon icon="award"></b-icon></h3>
              </div>
              <b-card-text>
                <div class="order-card-order-info-key"> <span >Status:</span> <b-badge href="/hint/itemStatus" v-bind:variant="item.status=='ACTIVE'?'success':item.status=='SOLD'?'danger':'warning'">{{item.status}}</b-badge></div>
                <div class="order-card-order-info-key"> <span >Price:</span>  <span >&nbsp;{{item.price}}</span> </div>
                <div class="order-card-order-info-key"> <span>Category:</span> <b-badge variant="primary">{{item.tag}}</b-badge> </div>
              </b-card-text>
              <b-button variant="outline-primary" v-b-toggle="'item-desc-'+item.id" >View Description</b-button>
              <b-button variant="outline-primary" v-bind:href="'/itemDetail/'+item.id" >More Information</b-button>
              <b-collapse v-bind:id="'item-desc-'+item.id" class="mt-2">
                <b-card>
                  <b-card-text>
                    <span>
                      {{item.description}}
                    </span>

                  </b-card-text>

                </b-card>
              </b-collapse>
            </b-card>
          </b-col>
        </b-row>
      </b-container>

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
      currentViewCollectionName:'',
      currentCollectionItems:[]

    }
  },
  methods:{
    getCurrentViewCollection(){
      var currentURL=window.location.href
      var reg=/collectionName=(.*)/
      this.currentViewCollectionName=decodeURI(currentURL.match(reg)[1])
      AXIOS.get('/collection/selectCollection?collectionName='+this.currentViewCollectionName)
        .then(response=>{
          this.currentCollectionItems=response.data
        })
    },
    refreshItems(){
      this.getCurrentViewCollection()
    }

  },
  created() {
    this.refreshItems()

  }
}
</script>

<style scoped>
.order-card-order-name-title{
  font-size: 30px;
  font-weight: bold;
}
.order-card-order-info-key{
  font-size: 20px;
  font-weight: bold;
}
.order-card-order-info-value{
  font-size: 20px;
}
.jumbotron-schonbrunner_gelb{
  background-color: #FBD26A;
  color: #492D22;
}
</style>
