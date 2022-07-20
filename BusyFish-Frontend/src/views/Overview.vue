<template>
  <div class="wrapper">
    <AdminNavBar></AdminNavBar>
    <div style="padding-top: 40px">

      <b-card-group>
        <div>
          <router-link to="/order">
            <b-card
              title="Order Number"
              style="max-width: 20rem;"
              class="mb-2"
              img-src="https://pic.616pic.com/ys_bnew_img/00/07/03/CuqNUYtEhX.jpg"
              img-height="320px"
              img-alt="Image"
              img-bottom

            >
              <span class="h2 font-weight-bold mb-0">{{orderNumber}}</span>
            </b-card>
          </router-link>
        </div>

        <div style="padding-left: 30px">
          <router-link to="/user">
            <b-card
              title="User Number"
              style="max-width: 20rem;"
              class="mb-2"
              img-src="https://pic.616pic.com/ys_bnew_img/00/04/49/vcK1P05xD0.jpg"
              img-alt="Image"
              img-height="320px"
              img-bottom
            >
              <span class="h2 font-weight-bold mb-0">{{userNumber}}</span>
            </b-card>

          </router-link>

        </div>

        <div style="padding-left: 30px">
          <router-link to="/item">
            <b-card
              title="Item Number"
              style="max-width: 20rem;"
              class="mb-2"
              img-src="https://pic.616pic.com/ys_bnew_img/00/23/00/6SpNiKWhq1.jpg"
              img-alt="Image"
              img-height="320px"
              img-bottom
            >
              <span class="h2 font-weight-bold mb-0">{{itemNumber}}</span>
            </b-card>
          </router-link>
        </div>
      </b-card-group>
    </div>
  </div>

</template>

<script>
import axios from 'axios'

var config = require('../../config')
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: {'Access-Control-Allow-Origin': frontendUrl,
  }
})

export default {
  data() {
    return {
      orderNumber: 0,
      itemNumber: 0,
      userNumber: 0
    }
  },
  methods:{
    getOrderNumber(){
      AXIOS.get("/order/selectOrderNumber")
        .then(response=>{
          this.orderNumber=response.data
        })
      AXIOS.get("user/selectAllUsers")
        .then(response=>{
          this.userNumber=response.data.length
        })
      AXIOS.get("item/selectAllItems")
        .then(response=>{
          this.itemNumber=response.data.length
        })
    }

  },
  created() {
    this.getOrderNumber()
  }
}
</script>

<style scoped>

.router-link-active {
  font-weight: bolder;
  color: black;
}
a{
  text-decoration: none;
  color: black;
}
</style>
