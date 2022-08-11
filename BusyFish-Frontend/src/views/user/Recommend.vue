<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>
    <h1 class="item-title">
      BusyTiger Shop
    </h1>

    <div style="padding-left: 10px">
      <b-button-group size="lg">
        <b-button variant="primary" v-b-toggle.item-search-bar>Search Item&nbsp;<b-icon icon="search"></b-icon></b-button>
      </b-button-group>
    </div>
    <div style="padding-left: 10px">
      <b-collapse id="item-search-bar">
        <b-form inline style="margin: 15px 0" id="item-search-bar">
          <b-form-group id="input-group-3" label="ID:" label-for="input-1" class="search-bar-key">
            <b-form-input
              id="input-1"
              class="mb-2 mr-sm-2 mb-sm-0"
              placeholder="Item's ID"
              v-model="itemSearch.id"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-3" label="Name/Desc:" label-for="input-1" class="search-bar-key">
            <b-form-input
              id="input-1"
              class="mb-2 mr-sm-2 mb-sm-0"
              placeholder="Item's Name or Description"
              v-model="itemSearch.name"
              onkeyup="this.searchItem(itemSearch)"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-3" label="Price Range: from" label-for="input-1" class="search-bar-key">
            <b-form-input
              id="input-1"
              class="mb-2 mr-sm-2 mb-sm-0"
              placeholder="Lower Price"
              type="number"
              v-model="itemSearch.minPrice"
            ></b-form-input>
            to&nbsp;
            <b-form-input
              id="input-1"
              class="mb-2 mr-sm-2 mb-sm-0"
              placeholder="Higher Price"
              type="number"
              v-model="itemSearch.maxPrice"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-3" label="Tag:" label-for="input-1" class="search-bar-key">
            <b-form-select :options="itemTagForCreate" v-model="itemSearch.tag">
            </b-form-select>
          </b-form-group>
          <b-form-group type="number" id="input-group-3" label="Seller's ID:" label-for="input-1" class="search-bar-key">
            <b-form-input
              id="input-1"
              class="mb-2 mr-sm-2 mb-sm-0"
              placeholder="Item's Name or Description"
              v-model="itemSearch.sellerId"
            ></b-form-input>
          </b-form-group>
          <b-button-group style="padding-left: 5px">
            <b-button variant="primary" @click="searchItem(itemSearch)"><b-icon icon="search"></b-icon></b-button>
          </b-button-group>
          <b-button-group style="padding-left: 5px">
            <b-button variant="primary" @click="refreshItemList" style="padding-left: 10px">Show All</b-button>
          </b-button-group>

        </b-form>

      </b-collapse>


    </div>

    <div class="not-found-tip" v-show="itemList.length==0">
      <h1>Sorry, no item found&nbsp;<b-icon-emoji-frown></b-icon-emoji-frown></h1>
    </div>


    <div>
      <b-container fluid>
        <b-row cols="3">
          <b-col v-for="item in itemList" style="padding-top: 20px">
            <b-card  v-bind:img-src="imgDirUrl+'/item_img_'+item.id+'.jpg'" img-height="150px" img-right bg-variant="light" >
              <div>
                <h3 class="order-card-order-name-title">{{item.name}} &nbsp;<b-icon icon="cart"></b-icon></h3>
              </div>
              <b-card-text>
<!--                <div> <span class="order-card-order-info-key">Seller:</span> <span class="order-card-order-info-value">&nbsp;{{item.sellerName}}</span> </div>-->
                <div> <span class="order-card-order-info-key">Seller:</span> <b-link class="order-card-order-info-value" v-bind:href="'/otherUserProfile/userId='+item.sellerId">&nbsp;{{item.sellerName}}</b-link> </div>
                <div> <span class="order-card-order-info-key">Price:</span>  <span class="order-card-order-info-value">&nbsp;{{item.price}}</span> </div>

              </b-card-text>
              <b-button variant="primary" v-bind:href="'/itemDetail/'+item.id">
                Item Page
                <b-icon icon="link45deg"></b-icon>
              </b-button>

              <b-button
                variant="warning"
                @click="loadOrderConfirm(item)"
                v-show="currentUserProfile.id!=item.sellerId"
                v-b-modal.order-confirm-panel>
                Buy Now
                <b-icon icon="cart"></b-icon>
              </b-button>
              <b-button
                @click="toastMessage('The item has been added to your favorites!')"
                variant="danger">
                Favorite
                <b-icon icon="heart-fill"></b-icon>
              </b-button>
              <b-button
                href="#"
                variant="primary"
                v-b-toggle="'recommend-item-detail-card-'+item.id">
                <b-icon icon="three-dots"></b-icon>
              </b-button>
<!--              <b-avatar square src="http://47.103.219.4:8089/item_img_20.jpg" size="lg"></b-avatar>-->
              <b-collapse v-bind:id="'recommend-item-detail-card-'+item.id" class="mt-2">
                <b-card>
                  <b-card-text>
                    <div> <span class="order-card-detail-key">Item Name:</span> <span class="order-card-detail-value">&nbsp;{{item.name}}</span> </div>
<!--                    <div> <span class="order-card-detail-key">Seller:</span> <span class="order-card-detail-value">&nbsp;{{item.sellerName}}</span> <b-button size="sm" variant="primary" @click="viewSellerProfile(item.sellerId)"><b-icon icon="person"></b-icon></b-button></div>-->
                    <div> <span class="order-card-detail-key">Seller:</span> <b-button size="sm" variant="outline-primary" v-bind:href="'/otherUserProfile/userId='+item.sellerId">{{item.sellerName}}<b-icon icon="person"></b-icon></b-button></div>
                    <div> <span class="order-card-detail-key">Price:</span> <span class="order-card-detail-value">&nbsp;{{item.price}}</span> </div>
                    <div class="order-card-detail-key"> <span>Category:</span> <b-badge class="order-card-detail-value" variant="primary">{{item.tag}}</b-badge> </div>
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
        <div v-show="currentUserProfile.shippingAddress.length==0" > <b-link class="no-shipping-address-tip" href="/userProfile"> You don't have any shipping address! Click here to add your shipping addresses in "My Profile"</b-link></div>
        <div> <span class="order-card-detail-key">Comment:</span> <b-input v-model="orderConfirmItem.comment"></b-input> </div>
        <h2 style="text-align: right;padding-top: 20px"><b>{{orderConfirmItem.price}}￥ </b> </h2>


        <template #modal-footer="{ cancel,ok }">
          <b-button variant="primary" @click="ok()" :disabled="orderConfirmItem.shippingAddress.code==null">
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
          address:null,
          name:null,
          phone:null,
          code:null
        }
      },
      itemTagForCreate: [{text: 'Select a tag', value: null}, 'Book', 'Food', 'Electronics', 'Music', 'Movie','Men_Cloth','Women_Cloth','Sport','Health','Games','Tool','Baby'],
      itemStatusOptions: [{text: 'Select a status', value: null}, 'ACTIVE', 'SOLD', 'BANNED', 'PRIVATE', 'PENDING'],
      itemSearch:{
        id:null,
        name:null,
        maxPrice:null,
        minPrice:0,
        tag:null,
        status:null,
        sellerId: null
      },
      imgDirUrl:config.dev.itemImgDirPath
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
    searchItem(itemSearch){
      var form_data=new FormData()
      if(itemSearch.id!=null){
        form_data.append('id',itemSearch.id)
      }
      if(itemSearch.name!=null){
        form_data.append('name',itemSearch.name)
      }
      if(itemSearch.minPrice!=null){
        form_data.append('minPrice',itemSearch.minPrice)
      }
      if(itemSearch.maxPrice!=null){
        form_data.append('maxPrice',itemSearch.maxPrice)
      }
      if(itemSearch.tag!=null){
        form_data.append('tag',itemSearch.tag)
      }
      if(itemSearch.sellerId!=null){
        form_data.append('sellerId',itemSearch.sellerId)
      }
      form_data.append('status','ACTIVE')
      AXIOS.post('/item/queryItem',form_data,{})
        .then(response=>{
          this.itemList=response.data
        })
    },
    searchItemByScanURL(){
      var currentURL=window.location.href
      var reg=/recommend\/(.*)/
      var matchList=currentURL.match(reg)
      var scanCategory=matchList.length>1?matchList[1]:null

      var form_data=new FormData()

      if(scanCategory!=null){
        form_data.append('tag',scanCategory)
      }

      form_data.append('status','ACTIVE')
      AXIOS.post('/item/queryItem',form_data,{})
        .then(response=>{
          this.itemList=response.data
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
    },
    viewSellerProfile(id){
      AXIOS.get('/user/selectUserById?userId='+id)
        .then(response=>{
          if(response.data!=null){
            window.open(frontendUrl + '/otherUserProfile/userId=' + id)
          }
        })
    }
  },
  created() {
    this.getUserProfile()
    this.searchItemByScanURL()
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
.no-shipping-address-tip{
  color: red;
  font-weight: bold;
}
</style>
