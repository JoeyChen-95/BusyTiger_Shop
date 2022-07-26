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
                v-b-toggle="'recommend-item-detail-card-'+item.id">
                View Detail
              </b-button>
              <b-button
                variant="warning"
                @click="loadOrderConfirm(item)"
                v-b-modal.order-confirm-panel>
                Buy Now
                <b-icon icon="cart"></b-icon>
              </b-button>
              <b-button
                @click="toastMessage('The item has been added to your favorites!')"
                variant="danger">
                Add To Favorite
                <b-icon icon="heart-fill"></b-icon>
              </b-button>
              <b-collapse v-bind:id="'recommend-item-detail-card-'+item.id" class="mt-2">
                <b-card>
                  <b-card-text>
                    <div> <span class="order-card-detail-key">Item Name:</span> <span class="order-card-detail-value">&nbsp;{{item.name}}</span> </div>
                    <div> <span class="order-card-detail-key">Seller:</span> <span class="order-card-detail-value">&nbsp;{{item.sellerName}}</span> </div>
                    <div> <span class="order-card-detail-key">Price:</span> <span class="order-card-detail-value">&nbsp;{{item.price}}</span> </div>
                    <div> <span class="order-card-detail-key">Category:</span> <span class="order-card-detail-value">&nbsp;{{item.tag}}</span> </div>
                    <div> <span class="order-card-detail-key">Description:</span> <span class="order-card-detail-value">&nbsp;{{item.description}}</span> </div>
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
      <b-modal id="order-confirm-panel" centered title="Order Confirm" size="lg" @ok="placeOrder(orderConfirmItem)">
        <h4><b>Item Information</b> </h4>
        <div> <span class="order-card-detail-key">Seller:</span> <span class="order-card-detail-value">&nbsp;{{orderConfirmItem.sellerName}}</span> </div>
        <div> <span class="order-card-detail-key">Item:</span> <span class="order-card-detail-value">&nbsp;{{orderConfirmItem.name}}</span> </div>
<!--        <div> <span class="order-card-detail-key">Price:</span> <span class="order-card-detail-value">&nbsp;{{orderConfirmItem.price}}</span> </div>-->
        <div> <span class="order-card-detail-key">Description:</span> <span class="order-card-detail-value">&nbsp;{{orderConfirmItem.description}}</span> </div>
        <div> <span class="order-card-detail-key">Shipping Address:</span> <b-form-select v-model="orderConfirmItem.shippingAddress" :options="currentUserProfile.shippingAddress"></b-form-select></div>
        <div> <span class="order-card-detail-key">Comment:</span> <b-input v-model="orderConfirmItem.comment"></b-input> </div>
        <h2 style="text-align: right;padding-top: 20px"><b>{{orderConfirmItem.price}}￥ </b> </h2>


        <template #modal-footer="{ cancel,ok }">
          <b-button variant="primary" @click="ok()">
            Pay &nbsp;<b-icon icon="credit-card" aria-hidden="true"></b-icon>
          </b-button>
          <b-button variant="danger" @click="cancel()">
            Cancel
          </b-button>
        </template>
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
      itemList:[],
      orderConfirmItem:{
        id:'',
        sellerId:'',
        sellerName:'',
        name:'',
        price:'',
        tag:'',
        status:'',
        description:'',
        comment:'',
        shippingAddress:{
          address:'',
          name:'',
          phone:'',
          code:''
        }
      }
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
              for(var index in response.data){
                this.currentUserProfile.shippingAddress.push({value:response.data[index],text:response.data[index].address+' '+response.data[index].name})
              }
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
    },
    refreshItemList(){
      this.getItemList()
    },
    loadOrderConfirm(item){
      this.orderConfirmItem.id=item.id
      this.orderConfirmItem.sellerId=item.sellerId
      this.orderConfirmItem.sellerName=item.sellerName
      this.orderConfirmItem.name=item.name
      this.orderConfirmItem.price=item.price
      this.orderConfirmItem.tag=item.tag
      this.orderConfirmItem.status=item.status
      this.orderConfirmItem.description=item.description
      this.orderConfirmItem.comment=''
    },
    placeOrder(orderConfirmItem){
      var form_data=new FormData()
      form_data.append('buyerId',this.currentUserProfile.id)
      form_data.append('itemId',orderConfirmItem.id)
      form_data.append('shippingCode',orderConfirmItem.shippingAddress.code)
      form_data.append('comment',orderConfirmItem.comment)
      AXIOS.post('/order/buy',form_data,{})
        .then(response=>{
          this.toastMessage(response.data.msg)
          this.refreshItemList()
        })
        .catch(e=>{

        })
    },
    toastMessage(content){
      this.$bvToast.toast(content, {
        title: 'Tips',
        autoHideDelay: 2000,
        variant: 'success',
        solid: true,
        appendToast: false
      });
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
</style>
