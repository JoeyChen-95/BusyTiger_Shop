<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>
    <h1 class="item-title">
      My Orders
    </h1>

    <div class="not-found-tip" v-show="orderList.length==0">
      <h1>You don't have any order&nbsp;<b-icon-emoji-frown></b-icon-emoji-frown></h1>
    </div>

    <div>
      <b-container fluid>
        <b-row cols="3">
          <b-col v-for="order in orderList" style="padding-top: 20px">
            <b-card bg-variant="light" >
              <div>
                <h3 class="order-card-order-name-title">{{order.itemName}} &nbsp;<b-icon icon="cart"></b-icon></h3>

              </div>
              <b-card-text>
                <div> <span class="order-card-order-info-key">Order Time:</span> <span class="order-card-order-info-value">&nbsp;{{order.createTime}}</span> </div>
                <div class="order-card-order-info-key"> <span >Status:</span>  <b-badge href="/hint/orderStatus" v-bind:variant="order.status=='PROCESSING'?'info':order.status=='SHIPPED'?'primary':order.status=='COMPLETED'?'success':order.status=='CANCELED'?'secondary':'warning'">{{order.status}}</b-badge></div>
<!--                <div> <span class="order-card-order-info-key">Delivered Time:</span>  <span class="order-card-order-info-value">&nbsp;{{order.completeTime}}</span> </div>-->

              </b-card-text>

              <b-button
                href="#"
                variant="primary"
                aria-controls="collapse-4"
                v-b-toggle="'myorder-detail-card-'+order.id">
                View Order Detail
              </b-button>
              <b-button
                href="#"
                variant="warning"
                v-show="order.status=='PROCESSING'||order.status=='SHIPPING'"
                @click="loadOrderConfirmReceived(order)"
                v-b-modal.order-confirm-received>
                Confirm Received
              </b-button>
              <b-button
                href="#"
                variant="danger"
                v-show="order.status=='COMPLETED'||order.status=='SHIPPED'">
                Item has problem?
              </b-button>
              <b-collapse v-bind:id="'myorder-detail-card-'+order.id" class="mt-2">
                <b-card>
                  <b-card-text>
                    <div> <span class="order-card-detail-key">Item Name:</span> <span class="order-card-detail-value">&nbsp;{{order.itemName}}</span> </div>
                    <div> <span class="order-card-detail-key">Seller:</span> <b-button class="order-card-detail-value" size="sm" variant="outline-primary" @click="viewSellerProfile(order.sellerName)">{{order.sellerName}}<b-icon icon="person"></b-icon></b-button></div>
                    <div> <span class="order-card-detail-key">Price:</span> <span class="order-card-detail-value">&nbsp;{{order.price}}</span> </div>
                    <div class="order-card-detail-key"> <span>Status:</span>  <b-badge href="/hint/orderStatus" v-bind:variant="order.status=='PROCESSING'?'info':order.status=='SHIPPED'?'primary':order.status=='COMPLETED'?'success':order.status=='CANCELED'?'secondary':'warning'">{{order.status}}</b-badge> </div>
                    <b-progress :max="100" height="1.5rem" animated>
                      <b-progress-bar :value="order.status=='PROCESSING'?25:(order.status=='SHIPPED'?50:(order.status=='COMPLETED'?100:0))">
                        <span><strong>{{order.status}}</strong></span>
                      </b-progress-bar>
                    </b-progress>
                    <div v-show="order.courier!=null"> <span class="order-card-detail-value">&nbsp;{{order.courier}}</span> </div>
                    <div v-show="order.trackingNo!=null"> <span class="order-card-detail-value">&nbsp;{{order.trackingNo}}</span> </div>
                    <div v-show="order.courierFee!=null"><span class="order-card-detail-key">Courier Fee:</span> <span class="order-card-detail-value">&nbsp;{{order.courierFee}}</span> </div>
                    <div> <span class="order-card-detail-key">Shipping Address:</span></div>
                    <div > <span class="order-card-detail-value">&nbsp;{{order.shippingAddressName}}</span> </div>
                    <div> <span class="order-card-detail-value">&nbsp;{{order.shippingAddressPhone}}</span> </div>
                    <div> <span class="order-card-detail-value">&nbsp;{{order.shippingAddressAddress}}</span> </div>
                    <div> <span class="order-card-detail-key">Order Time:</span> <span class="order-card-detail-value">&nbsp;{{order.createTime}}</span> </div>
                    <div v-show="order.completeTime!=null"> <span class="order-card-detail-key">Complete Time:</span> <span class="order-card-detail-value">&nbsp;{{order.completeTime}}</span> </div>
                  </b-card-text>
                </b-card>
              </b-collapse>
            </b-card>
          </b-col>
        </b-row>
      </b-container>

      <b-modal size="lg" id="order-confirm-received" @ok="confirmReceived(orderConfirmReceived.id)" title="Confirm Received">
        <h5><b>Click OK to confirm you have received {{orderConfirmReceived.itemName}}</b> </h5>
        <h5><b>Order ID: {{orderConfirmReceived.id}}</b> </h5>
        <br>
        <h4><b style="color: red">Note: Be careful, after confirmation the money will be deposited to the seller's account</b> </h4>

      </b-modal>

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
  name: "MyOrder",
  data(){
    return{
      currentUserProfile:{
        id:'',
        username:'',
        email:'',
        primaryPhone: '',
        memberShip: '',
        shippingAddress:[]
      },
      orderList:[],
      visible: false,
      orderConfirmReceived:{
        id:'',
        itemName:''
      }
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
          this.getUserOrder(response.data.id)
          AXIOS.get('user/selectAllShippingAddressFromUser?userId='+response.data.id)
            .then(response=>{
              this.currentUserProfile.shippingAddress=response.data
            })
        })
    },
    getUserOrder(buyerId){
      AXIOS.get('/order/selectOrdersByBuyerId?buyerId='+buyerId)
        .then(response=>{
          this.orderList=response.data
        })
    },
    refreshOrderList() {
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
    },
    updateOrderStatus(item,status){
      var form_data=new FormData()
      form_data.append('orderId',item.id)
      form_data.append('status',status)
      AXIOS.put('order/updateOrderStatus',form_data,{})
        .then(response=>{
          this.toastMessage(response.data.msg)
          this.refreshOrderList()

        })
        .catch(e=>{
          this.toastMessage("Fail to modify status of the order!")
        })
    },
    loadOrderConfirmReceived(order){
      this.orderConfirmReceived.id=order.id
      this.orderConfirmReceived.itemName=order.itemName
    },
    viewSellerProfile(username){
      AXIOS.get('/user/selectUserByUsername?username='+username)
        .then(response=>{
          if(response.data!=null){
            window.open(frontendUrl + '/otherUserProfile/userId=' + response.data.id)
          }
        })
    },
    confirmReceived(id){
      var form_data=new FormData()
      form_data.append("id",id)
      AXIOS.put('/order/confirmReceived',form_data,{})
        .then(response=>{
          this.refreshOrderList()
          this.toastMessage(response.data.msg)
        })
        .catch(e=>{
          this.toastMessage("Fail to confirm received!")
        })
    }
  },
  created() {
    this.getUserProfile()
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
.order-card-detail-key{
  font-size: 17px;
  font-weight: bold;

}
.order-card-detail-value{
  font-size: 17px;
}
.not-found-tip {
  font-size: 40px;
  text-align: center;
  padding-top: 50px;
}
</style>
