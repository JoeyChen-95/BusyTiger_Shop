<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>
    <div class="box">
<!--      Left Part of the page: User Avator-->
      <div class="column-left" >
        <div class="mb-2">
          <div class="user-avator">
            <b-avatar src="https://placekitten.com/300/300" size="20rem" class="a"></b-avatar>
          </div>
          <div class="username-under-avator">{{currentViewUserProfile.username}}</div>
        </div>
      </div>
<!--      Right Part of the page: User Profile-->
      <div class="column-right">
        <div>
          <b-card>
            <b-row class="mb-2">
              <b-col sm="3" class="text-lg-right" style="font-size: 20px"><b>ID</b></b-col>
              <b-col style="font-size: 20px">{{currentViewUserProfile.id}}</b-col>
            </b-row>
            <b-row class="mb-2">
              <b-col sm="3" class="text-sm-right" style="font-size: 20px"><b>Username</b></b-col>
              <b-col style="font-size: 20px">{{currentViewUserProfile.username}}</b-col>
            </b-row>
            <b-row class="mb-2">
              <b-col sm="3" class="text-sm-right" style="font-size: 20px"><b>Email</b></b-col>
              <b-col style="font-size: 20px">{{currentViewUserProfile.email}}</b-col>
            </b-row>
            <b-row class="mb-2">
              <b-col sm="3" class="text-sm-right" style="font-size: 20px"><b>Membership</b></b-col>
              <b-col style="font-size: 20px">{{currentViewUserProfile.memberShip}}</b-col>
            </b-row>
          </b-card>
          <div style="padding-top: 20px">
            <div class="recent-order-title">He/She recently bought:</div>
            <b-container fluid>

              <b-row cols="3">
                <b-col v-for="order in orderList" style="padding-top: 20px">
                  <b-card bg-variant="light" >
                    <div>
                      <h3 class="order-card-order-name-title">{{order.itemName}} &nbsp;<b-icon icon="cart"></b-icon></h3>
                    </div>
                  </b-card>
                </b-col>
              </b-row>
            </b-container>
          </div>
          <div style="padding-top: 20px">
            <div class="recent-order-title">He/She recently sold:</div>
            <b-container fluid>

              <b-row cols="3">
                <b-col v-for="order in sellList" style="padding-top: 20px">
                  <b-card bg-variant="light" >
                    <div>
                      <h3 class="order-card-order-name-title">{{order.itemName}} &nbsp;<b-icon icon="cart"></b-icon></h3>
                    </div>
                  </b-card>
                </b-col>
              </b-row>
            </b-container>
          </div>
        </div>


      </div>
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
      fields: ['id', 'username','memberShip', 'show_details','Operation'],
      shippingAddressField: ['name','phone', 'address'],
      currentUserProfile:{
        id:'',
        username:'',
        email:'',
        primaryPhone: '',
        memberShip: '',
        shippingAddress:[]
      },
      currentViewUserProfile:{
        id:'',
        username:'',
        email:'',
        memberShip:''
      },
      userMemberShipOptions:['REGULAR', 'GOLDEN_PRIME', 'DIAMOND_PRIME', 'BANNED', 'FROZEN'],
      orderList:[],
      sellList:[]
    }
  },
  methods:{
    getUserProfile(){
      AXIOS.get('/user/getCurrentUserId')
        .then(response=>{
          this.currentUserProfile.id=response.data.id
          this.currentUserProfile.username=response.data.username
          this.currentUserProfile.email=response.data.email
          this.currentUserProfile.primaryPhone=response.data.primaryPhone
          this.currentUserProfile.memberShip=response.data.memberShip
          AXIOS.get('user/selectAllShippingAddressFromUser?userId='+response.data.id)
            .then(response=>{
              this.currentUserProfile.shippingAddress=response.data
            })
        })
    },
    getUserOrder(buyerId){
      AXIOS.get('/order/selectOrdersByBuyerId?buyerId='+buyerId)
        .then(response=>{
          if(response.data!=null){
            this.orderList=response.data.slice(0,5)
          }
        })
    },
    getUserSell(sellerId){
      AXIOS.get('/order/selectOrdersBySellerId?sellerId='+sellerId)
        .then(response=>{
          if(response.data!=null){
            this.sellList=response.data.slice(0,5)
          }
        })
    },
    getCurrentViewUserProfile(){
      var currentURL=window.location.href
      var reg=/userId=(.*)/
      this.currentViewUserProfile.id=currentURL.match(reg)[1]
      AXIOS.get('/user/selectUserById?userId='+this.currentViewUserProfile.id)
        .then(response=>{
          this.getUserOrder(response.data.id)
          this.getUserSell(response.data.id)
          this.currentViewUserProfile.email=response.data.email
          this.currentViewUserProfile.username=response.data.username
          this.currentViewUserProfile.memberShip=response.data.memberShip
        })
    },
    refreshUserProfile(){
      this.getUserProfile()
    },
    toastMessage(content){
      this.$bvToast.toast(content, {
        title: 'Tips',
        autoHideDelay: 2000,
        variant: 'warning',
        solid: true,
        appendToast: false
      });
    }
  },
  created() {
    this.getUserProfile()
    this.getCurrentViewUserProfile()
  },
  computed:{
    key(){
      return this.$route.path+Math.random();
    }
  }
}
</script>

<style scoped>
.column-right{

  float: right;
  width: 80%;
}
.column-left{

  float: left;
  width: 20%;
}
.username-under-avator {
  font-size: 32px;
  font-weight: bold;
  text-align: center;
  padding: 10px;
  background: #fff;
  margin: 0 auto;
}
.user-avator{
  text-align: center;
  padding-top: 20px;
}
.recent-order-title {
  font-size: 32px;
  font-weight: bold;
  text-align: left;
  padding: 10px;
  background: #fff;
  margin: 0 auto;
}


</style>
