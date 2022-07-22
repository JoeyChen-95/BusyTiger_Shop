<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>
    <h1 class="item-title">
      BusyTiger Shop
    </h1>
    <div>
      <b-container fluid>
        <b-row cols="3">
          <b-col v-for="item in itemList" style="padding-top: 20px">
            <b-card bg-variant="light" >
              <div>
                <h3 class="order-card-order-name-title">{{item.name}} &nbsp;<b-icon icon="cart"></b-icon></h3>

              </div>
              <b-card-text>
                <div> <span class="order-card-order-info-key">Seller:</span> <span class="order-card-order-info-value">&nbsp;{{item.sellerName}}</span> </div>
                <div> <span class="order-card-order-info-key">Price:</span>  <span class="order-card-order-info-value">&nbsp;{{item.price}}</span> </div>

              </b-card-text>

              <b-button
                href="#"
                variant="primary"
                aria-controls="collapse-4"
                @click="item.visible=!item.visible">
                View Order Detail
              </b-button>
              <b-collapse id="collapse-4" v-model="item.visible" class="mt-2">
                <b-card>
                  <b-card-text>
                    <div> <span class="order-card-detail-key">Item Name:</span> <span class="order-card-detail-value">&nbsp;{{item.name}}</span> </div>
<!--                    <div> <span class="order-card-detail-key">Status:</span> <span class="order-card-detail-value">&nbsp;{{order.status}}</span> </div>-->
<!--                    <b-progress :max="100" height="1.5rem" animated>-->
<!--                      <b-progress-bar :value="order.status=='PROCESSING'?25:(order.status=='SHIPPED'?50:(order.status=='COMPLETED'?100:0))">-->
<!--                        <span><strong>{{order.status}}</strong></span>-->
<!--                      </b-progress-bar>-->
<!--                    </b-progress>-->
<!--                    <div v-show="order.status!='PROCESSING'&&order.status!='CANCELED'"> <span class="order-card-detail-value">&nbsp;{{order.courier}}</span> </div>-->
<!--                    <div v-show="order.status!='PROCESSING'&&order.status!='CANCELED'"> <span class="order-card-detail-value">&nbsp;{{order.trackingNo}}</span> </div>-->
<!--                    <div v-show="order.status!='PROCESSING'&&order.status!='CANCELED'"> <span class="order-card-detail-value">&nbsp;{{order.courierFee}}</span> </div>-->
<!--                    <div> <span class="order-card-detail-key">Shipping Address:</span></div>-->
<!--                    <div> <span class="order-card-detail-value">&nbsp;{{order.shippingAddressName}}</span> </div>-->
<!--                    <div> <span class="order-card-detail-value">&nbsp;{{order.shippingAddressPhone}}</span> </div>-->
<!--                    <div> <span class="order-card-detail-value">&nbsp;{{order.shippingAddressAddress}}</span> </div>-->
<!--                    <div> <span class="order-card-detail-key">Order Time:</span> <span class="order-card-detail-value">&nbsp;{{order.createTime}}</span> </div>-->


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
  data() {
    return {
      currentUserProfile: {
        id: '',
        username: '',
        email: '',
        primaryPhone: '',
        memberShip: '',
        shippingAddress: []
      },
      itemList:[]
    }

  },
  methods: {
    getUserProfile() {
      AXIOS.get('/user/getCurrentUserId')
        .then(response => {
          this.currentUserProfile.id = response.data.id
          this.currentUserProfile.username = response.data.username
          this.currentUserProfile.email = response.data.email
          this.currentUserProfile.primaryPhone = response.data.primaryPhone
          this.currentUserProfile.memberShip = response.data.memberShip
          AXIOS.get('user/selectAllShippingAddressFromUser?userId=' + response.data.id)
            .then(response => {
              this.currentUserProfile.shippingAddress = response.data
            })
        })
    },
    getItemList(){
      AXIOS.get('/item/selectAllActiveItem')
        .then(response=>{
          this.itemList=response.data
        })
        .catch(e=>{

        })
    }
  },
  created() {
    this.getUserProfile()
    this.getItemList()
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
</style>
