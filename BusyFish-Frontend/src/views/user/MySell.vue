<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>
    <h1 class="item-title">
      My Sell
    </h1>

    <div class="not-found-tip" v-show="orderList.length==0">
      <h1>You haven't sold any item yet&nbsp;<b-icon-question-circle-fill></b-icon-question-circle-fill></h1>
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
                <div> <span class="order-card-order-info-key">Buyer:</span> <span class="order-card-order-info-value">&nbsp;{{order.buyerName}}</span>  <b-button size="sm" variant="primary" @click="viewSellerProfile(order.buyerName)"><b-icon icon="person"></b-icon></b-button></div>
                <div> <span class="order-card-order-info-key">Paid:</span> <span class="order-card-order-info-value">&nbsp;{{order.price}}</span> </div>
                <div> <span class="order-card-order-info-key">Order Time:</span> <span class="order-card-order-info-value">&nbsp;{{order.createTime}}</span> </div>
                <div class="order-card-order-info-key"> <span >Status:</span>  <b-badge href="/hint/orderStatus" v-bind:variant="order.status=='PROCESSING'?'info':order.status=='SHIPPED'?'primary':order.status=='COMPLETED'?'success':order.status=='CANCELED'?'secondary':'warning'">{{order.status}}</b-badge></div>
                <div v-show="order.completeTime"> <span class="order-card-order-info-key">Delivered Time:</span>  <span class="order-card-order-info-value">&nbsp;{{order.completeTime}}</span> </div>

              </b-card-text>

              <b-button
                href="#"
                variant="primary"
                aria-controls="collapse-4"
                v-b-toggle="'mysell-order-detail-card-'+order.id">
                View Order Detail
              </b-button>
              <b-button
                variant="warning"
                @click="loadOrderConfirm(order)"
                :disabled="order.status=='COMPLETED'||order.status=='CANCELED'||order.status=='EXCEPTION'||order.status=='RETURNING'"
                v-b-modal.order-ship-panel>
                {{ order.status=='PROCESSING'?'Ship Order':'Edit Courier Information' }}&nbsp;<b-icon icon="truck"></b-icon>
              </b-button>
              <b-button
                variant="danger"
                @click="updateOrderStatus(order,'CANCELED')"
                v-show="order.status=='PROCESSING'"
                >
                Cancel
              </b-button>
              <b-collapse v-bind:id="'mysell-order-detail-card-'+order.id" class="mt-2">
                <b-card>
                  <b-card-text>
                    <div> <span class="order-card-detail-key">Order ID:</span> <span class="order-card-detail-value">&nbsp;{{order.id}}</span> </div>
                    <div> <span class="order-card-detail-key">Item Name:</span> <span class="order-card-detail-value">&nbsp;{{order.itemName}}</span> </div>
                    <div> <span class="order-card-detail-key">Buyer:</span> <span class="order-card-detail-value">&nbsp;{{order.buyerName}}</span> <b-button size="sm" variant="primary" @click="viewSellerProfile(order.sellerName)"><b-icon icon="person"></b-icon></b-button></div>
                    <div> <span class="order-card-detail-key">Status:</span> <span class="order-card-detail-value">&nbsp;{{order.status}}</span> </div>
                    <b-progress :max="100" height="1.5rem">
                      <b-progress-bar :value="order.status=='PROCESSING'?25:(order.status=='SHIPPED'?50:(order.status=='COMPLETED'?100:0))" animated>
                        <span><strong>{{order.status}}</strong></span>
                      </b-progress-bar>
                    </b-progress>
                    <div> <span class="order-card-detail-key">Courier Information</span> </div>
                    <div v-show="order.trackingNo!=null&&order.courier.length!=null"> <span class="order-card-detail-value">{{order.courier}}&nbsp;{{order.trackingNo}}</span> </div>
                    <div v-show="order.courierFee!=null"> <span class="order-card-detail-value">Courier Fee:&nbsp;{{order.courierFee}}</span> </div>
                    <div> <span class="order-card-detail-key">Shipping Address:</span></div>
                    <div> <span class="order-card-detail-value">&nbsp;{{order.shippingAddressName}}</span> </div>
                    <div> <span class="order-card-detail-value">&nbsp;{{order.shippingAddressPhone}}</span> </div>
                    <div> <span class="order-card-detail-value">&nbsp;{{order.shippingAddressAddress}}</span> </div>
                    <div> <span class="order-card-detail-key">Order Time:</span> <span class="order-card-detail-value">&nbsp;{{order.createTime}}</span> </div>


                  </b-card-text>

                </b-card>
              </b-collapse>
            </b-card>
          </b-col>
        </b-row>
      </b-container>

    </div>
    <b-modal id="order-ship-panel" centered title="Ship Order" size="lg" @ok="shipOrder(orderModify)">
      <h4><b>Order Information</b> </h4>
      <div> <span class="order-card-detail-key">Order ID:</span><span class="order-card-detail-value">&nbsp;{{orderModify.id}}</span> </div>
      <div> <span class="order-card-detail-key">Item Name:</span><span class="order-card-detail-value">&nbsp;{{orderModify.itemName}}</span> </div>
      <div> <span class="order-card-detail-key">Buyer:</span><span class="order-card-detail-value">&nbsp;{{orderModify.buyerName}}</span> </div>
      <h4><b>Buyer's Shipping Information</b> </h4>
      <div> <span class="order-card-detail-key">Shipping Address:</span></div>
      <div>  <span class="order-card-detail-value">&nbsp;{{orderModify.shippingAddressName}}</span> </div>
      <div>  <span class="order-card-detail-value">&nbsp;{{orderModify.shippingAddressPhone}}</span> </div>
      <div>  <span class="order-card-detail-value">&nbsp;{{orderModify.shippingAddressAddress}}</span> </div>
      <h4><b>Fill Courier Information</b> </h4>
      <div> <span class="order-card-detail-key">Courier:</span> <b-form-select v-model="orderModify.courier" :options="courierOptions"></b-form-select></div>
      <div> <span class="order-card-detail-key">Tracking Number:</span> <b-input v-model="orderModify.trackingNo" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"></b-input> </div>
      <div> <span class="order-card-detail-key">Courier Fee:</span> <b-form-input type="number" v-model="orderModify.courierFee" onkeyup="value=value.replace(/[^\d^\.]+/g,'').replace('.','$#$').replace(/\./g,'').replace('$#$','.')"></b-form-input> </div>
<!--      <h2 style="text-align: right;padding-top: 20px"><b>{{orderConfirmItem.price}}￥ </b> </h2>-->
    </b-modal>


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
      orderModify:{
        id:'',
        buyerId:'',
        buyerName:'',
        itemName:'',
        shippingAddressName:'',
        shippingAddressPhone:'',
        shippingAddressAddress:'',
        courier:'',
        trackingNo:'',
        courierFee:'',
        completeTime:''
      },
      courierOptions:['Amazon', 'DHL', 'Fedex', 'UPS', 'USPS', 'SF_Express', 'Canada_Post', 'TNT']
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
          this.getUserSell(response.data.id)
          AXIOS.get('user/selectAllShippingAddressFromUser?userId='+response.data.id)
            .then(response=>{
              this.currentUserProfile.shippingAddress=response.data
            })
        })
    },
    getUserSell(sellerId){
      AXIOS.get('/order/selectOrdersBySellerId?sellerId='+sellerId)
        .then(response=>{
          this.orderList=response.data
        })
    },
    refreshOrderList(){
      this.getUserProfile()
    },
    loadOrderConfirm(item){
      this.orderModify.id=item.id
      this.orderModify.buyerName=item.buyerName
      this.orderModify.itemName=item.itemName
      this.orderModify.courierFee=0
      this.orderModify.courier=null
      this.orderModify.trackingNo=''
      this.orderModify.shippingAddressName=item.shippingAddressName
      this.orderModify.shippingAddressPhone=item.shippingAddressPhone
      this.orderModify.shippingAddressAddress=item.shippingAddressAddress
      this.orderModify.completeTime=item.completeTime
      this.orderModify.buyerId=item.buyerId
    },
    shipOrder(item){
      var form_data=new FormData()
      form_data.append('orderId',item.id)
      form_data.append('courier',item.courier)
      form_data.append('trackingNo',item.trackingNo)
      form_data.append('courierFee',item.courierFee)
      AXIOS.put('order/shipOrder',form_data,{})
        .then(response=>{
          this.toastMessage(response.data.msg)
          this.refreshOrderList()
        })
        .catch(e=>{
          this.toastMessage("Fail to ship the order!")
        })
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
    viewSellerProfile(username){
      AXIOS.get('/user/selectUserByUsername?username='+username)
        .then(response=>{
          if(response.data!=null){
            window.open(frontendUrl + '/otherUserProfile/userId=' + response.data.id)
          }
        })
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
.not-found-tip{
  font-size: 40px;
  text-align: center;
  padding-top: 50px;
}

</style>
