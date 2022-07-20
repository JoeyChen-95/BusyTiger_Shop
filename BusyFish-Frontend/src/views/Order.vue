<template>

  <div>
    <div class="wrapper">
      <AdminNavBar></AdminNavBar>
    </div>
    <h1 class="order-title">
      Order Management
    </h1>
    <b-table :items="orderList" :fields="fields" striped responsive="sm">
      <template #cell(show_details)="row">
        <b-button size="sm" @click="row.toggleDetails" class="mr-2">
          {{ row.detailsShowing ? 'Hide' : 'Show'}} Details
        </b-button>

      </template>

      <template #row-details="row">
        <b-card>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Order ID:</b></b-col>
            <b-col>{{ row.item.id}}</b-col>
          </b-row>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Buyer's ID:</b></b-col>
            <b-col>{{ row.item.buyerId}}</b-col>
          </b-row>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Item's ID:</b></b-col>
            <b-col>{{ row.item.itemId}}</b-col>
          </b-row>

          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Courier Information:</b></b-col>
            <b-col><i><b>Courier Company: </b></i><br>{{ row.item.courier }}</b-col>
            <b-col><i><b>Tracking Number: </b></i><br>{{ row.item.trackingNo }}</b-col>
            <b-col><i><b>Courier Fee: </b></i><br>{{ row.item.courierFee }}</b-col>
          </b-row>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Shipping Address:</b></b-col>
            <b-col><i><b>Name: </b></i><br>{{ row.item.shippingAddress.name }}</b-col>
            <b-col><i><b>Phone: </b></i><br>{{ row.item.shippingAddress.phone }}</b-col>
            <b-col><i><b>Address: </b></i><br>{{ row.item.shippingAddress.address }}</b-col>
          </b-row>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Order Status:</b></b-col>
            <b-col><i><b>Status:</b></i><br> {{ row.item.status }}</b-col>
            <b-col><i><b>Create Time:</b></i><br> {{ row.item.createTime }}</b-col>
            <b-col><i><b>Complete Time:</b></i><br> {{ row.item.completeTime }}</b-col>
          </b-row>

          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Comment:</b></b-col>
            <b-col>{{ row.item.comment }}</b-col>
          </b-row>

          <b-button size="sm" @click="row.toggleDetails">Hide Details</b-button>
        </b-card>
      </template>
    </b-table>
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
      fields: ['id', 'status','createTime' ,'show_details','Operation'],
      items: [],
      orderList: [],
      shippingAddress: {
        name:'',
        phone:'',
        address:''
      }
    }
  },
  methods:{
    getOrderList(){
      AXIOS.get('order/selectAllOrders')
        .then(response=>{
          this.orderList=response.data
          for(const index in this.orderList){
            AXIOS.get('order/selectShippingAddressByOrderId?orderId='+this.orderList[index].id)
              .then(response=>{
                var shippingAddress= {
                  name:'',
                  phone:'',
                  address:''
                }
                shippingAddress.name=response.data.name
                shippingAddress.phone=response.data.phone
                shippingAddress.address=response.data.address
                this.orderList[index].shippingAddress=shippingAddress
              })
          }
        })
    }
  },
  created() {
    this.getOrderList()
  }
}
</script>

<style scoped>
.order-title{
  font-size: 32px;
  font-weight: bold;
  text-align: center;
  box-shadow: 0 0 50px rgba(0, 0, 0, 0.3);
  padding: 10px;
  background: #fff;
  margin: 0 auto;
}

</style>
