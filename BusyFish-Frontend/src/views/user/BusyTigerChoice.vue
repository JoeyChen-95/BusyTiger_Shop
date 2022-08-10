<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>
    <div class="item-title">
      Busy Tiger Prime Collection<br>
      <span style="font-size: 27px">Discover our collections of products with premium quality.</span>
    </div>
    <div>
      <b-container fluid>
        <b-row cols="1">
          <b-col v-for="collection in collectionList" style="padding-top: 20px">
            <div>
              <b-jumbotron class="jumbotron-schonbrunner_gelb" v-bind:header="collection" v-bind:lead="'View our collections of '+collection" >
                <b-button size="lg" variant="outline-dark" v-bind:href="'/busyTigerChoice/collectionName='+collection">GO</b-button>
              </b-jumbotron>
            </div>

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
      collectionList:[]

    }
  },
  methods:{
    getCollectionList(){
      AXIOS.get('/collection/selectAllCollection')
        .then(response=>{
          this.collectionList=response.data
        })
        .catch(e=>{
          this.toastMessage("Fail to obtain collection information!")
        })
    },
    refreshCollectionList(){
      this.getCollectionList()
    }

  },
  created() {
    this.refreshCollectionList()
  }
}
</script>

<style scoped>
.item-title{
  font-size: 32px;
  font-weight: bold;
  text-align: center;
  box-shadow: 0 0 50px rgba(0, 0, 0, 0.3);
  padding: 10px;
  background: #fff;
  margin: 0 auto;
}
.jumbotron-schonbrunner_gelb{
  background-color: #FBD26A;
  color: #492D22;
}
</style>
