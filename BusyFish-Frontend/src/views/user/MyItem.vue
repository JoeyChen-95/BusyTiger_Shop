<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>
    <h1 class="item-title">
      My Published Item
    </h1>
    <div>
      <b-button-group size="lg">
        <b-button variant="success" v-b-toggle.sidebar-publish-item>Publish a New Item&nbsp;<b-icon icon="plus-square"></b-icon></b-button>
        <b-button variant="primary" v-b-toggle.item-search-bar>Search Item&nbsp;<b-icon icon="search"></b-icon></b-button>
      </b-button-group>
    </div>
    <div>
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
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-3" label="Price Range: from" label-for="input-1" class="search-bar-key">
            <b-form-input
              id="input-1"
              class="mb-2 mr-sm-2 mb-sm-0"
              placeholder="Lower Price"
              v-model="itemSearch.minPrice"
            ></b-form-input>
            to&nbsp;
            <b-form-input
              id="input-1"
              class="mb-2 mr-sm-2 mb-sm-0"
              placeholder="Higher Price"
              v-model="itemSearch.maxPrice"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-3" label="Tag:" label-for="input-1" class="search-bar-key">
            <b-form-select :options="itemTagForCreate" v-model="itemSearch.tag">
            </b-form-select>
          </b-form-group>
          <b-form-group id="input-group-3" label="Status:" label-for="input-1" class="search-bar-key">
            <b-form-select :options="itemStatusOptions" v-model="itemSearch.status">
            </b-form-select>
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
      <h1>No item found&nbsp;<b-icon-question-circle-fill></b-icon-question-circle-fill></h1>
    </div>

    <div>
      <b-container fluid>
        <b-row cols="3">
          <b-col v-for="item in itemList" style="padding-top: 20px" visible="false">
            <b-card bg-variant="light" >
              <div>
                <h3 class="order-card-order-name-title">{{item.name}} &nbsp;<b-icon icon="cart"></b-icon></h3>
              </div>
              <b-card-text>
                <div class="order-card-order-info-key"> <span >Status:</span> <b-badge href="/hint/itemStatus" v-bind:variant="item.status=='ACTIVE'?'success':item.status=='SOLD'?'danger':'warning'">{{item.status}}</b-badge></div>
                <div class="order-card-order-info-key"> <span >Price:</span>  <span class="order-card-order-info-value">&nbsp;{{item.price}}</span> </div>

              </b-card-text>
              <b-button variant="primary" v-bind:href="'/itemDetail/'+item.id">
                Item Page
                <b-icon icon="link45deg"></b-icon>
              </b-button>
              <b-button
                variant="warning"
                @click="loadItemUpdate(item)"
                :disabled="item.status=='SOLD'"
                v-b-toggle.update-sidebar>
                Edit
                <b-icon icon="cart"></b-icon>
              </b-button>
              <b-button
                @click="loadItemDelete(item)"
                variant="danger"
                :disabled="item.status=='SOLD'"
                v-b-modal.item-delete-panel
                v-b-tooltip.hover title="Only unsold item can be deleted">
                Delete
                <b-icon icon="trash"></b-icon>
              </b-button>
              <b-button
                href="#"
                variant="primary"
                aria-controls="order-detail-panel"
                v-b-toggle="'item-detail-card-'+item.id">
                <b-icon icon="three-dots"></b-icon>
              </b-button>

              <div>
                <b-collapse v-bind:id="'item-detail-card-'+item.id.toString()">
                  <b-card>
                    <b-card-text>
                      <div> <span class="order-card-detail-key">Item Name:</span> <span class="order-card-detail-value">&nbsp;{{item.name}}</span> </div>
                      <div> <span class="order-card-detail-key">Seller:</span> <span class="order-card-detail-value">&nbsp;{{item.sellerName}}</span> </div>
                      <div> <span class="order-card-detail-key">Price:</span> <span class="order-card-detail-value">&nbsp;{{item.price}}</span> </div>
                      <div class="order-card-detail-key"> <span >Category:</span> <b-badge href="/hint/itemStatus" variant="primary">{{item.tag}}</b-badge> </div>
                      <div> <span class="order-card-detail-key">Description:</span> <span class="order-card-detail-value">&nbsp;{{item.description}}</span> </div>

                    </b-card-text>

                  </b-card>
                </b-collapse>
              </div>

            </b-card>

          </b-col>
        </b-row>
      </b-container>

    </div>
    <div>
      <b-sidebar id="update-sidebar" width="600px" aria-labelledby="sidebar-update-title" no-header shadow right>
        <template #default="{ hide }">
          <div class="p-3">
            <h4 id="sidebar-update-title">Update Item</h4>
            <b-container fluid>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Name</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="itemUpdate.itemName" placeholder="Enter item's name"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Price</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="itemUpdate.itemPrice" placeholder="Enter item's price"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Tag</label>
                </b-col>
                <b-col sm="10">
                  <b-form-select id="input-default" v-model="itemUpdate.itemTag" :options="itemTagForCreate"></b-form-select>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Description</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="itemUpdate.itemDesc" placeholder="Enter item's description"></b-form-input>
                </b-col>
              </b-row>

            </b-container>
            <b-button variant="primary" @click="updateItem(itemUpdate),updateItemImg" block >Update</b-button>
            <b-button v-b-modal.update-img-modal variant="warning" block>Update Image</b-button>
            <b-button variant="danger" block @click="hide">Close</b-button>
          </div>

        </template>
      </b-sidebar>
    </div>

    <b-modal id="item-delete-panel" centered title="Delete Confirm" size="lg" @ok="deleteItem(itemDelete.id)">
      <h4><b>Are you sure you want to delete {{itemDelete.name}}?</b> </h4>
    </b-modal>

    <div>
      <b-sidebar id="sidebar-publish-item" width="600px" aria-labelledby="sidebar-no-header-title" no-header shadow right>
        <template #default="{ hide }">
          <div class="p-3">
            <h4 id="sidebar-no-header-title">Publish a new Item</h4>
            <b-container fluid>

              <b-row class="my-1">

                <b-col sm="10">
                  <b-form-group id="input-group-2" label="Item Name:" label-for="itemName">
                    <b-form-input
                      placeholder="Enter Item's Name"
                      v-model="itemPublish.name"
                      :state="checkItemName"
                    ></b-form-input>

                    <b-form-invalid-feedback id="itemName">
                      Please enter item's name.
                    </b-form-invalid-feedback>
                  </b-form-group>
                </b-col>
              </b-row>


              <b-row class="my-1">
                <b-col sm="10">
                  <b-form-group id="input-group-2" label="Item Price:" label-for="itemPrice">
                    <b-form-input
                      placeholder="Enter Item's Price"
                      v-model="itemPublish.price"
                      :state="checkPrice"
                      type="number"
                    ></b-form-input>

                    <b-form-invalid-feedback id="itemPrice">
                      {{itemPublish.price.length==0?'Please enter item\'s price.':'Enter a price>=0.'}}
                    </b-form-invalid-feedback>
                  </b-form-group>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="10">
                  <b-form-group id="input-group-2" label="Item Tag:" label-for="itemTag">
                    <b-form-select
                      v-model="itemPublish.tag"
                      :options="itemTagForCreate"
                      :state="checkTag"
                    ></b-form-select>

                    <b-form-invalid-feedback id="itemTag">
                      Please choose a tag.
                    </b-form-invalid-feedback>
                  </b-form-group>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="10">
                  <b-form-group id="input-group-2" label="Description:" label-for="itemTag">
                    <b-form-textarea
                      v-model="itemPublish.description"
                      :state="checkDesc"
                    ></b-form-textarea>

                    <b-form-invalid-feedback id="itemTag">
                      The description is 1000 words max.
                    </b-form-invalid-feedback>
                  </b-form-group>
                </b-col>
              </b-row>

              <b-row>
                <h4>Note: To set a image for your item, please click "Edit" button.</h4>
              </b-row>

            </b-container>

            <b-button variant="primary" block :disabled="!(checkItemName&&checkPrice&&checkTag&&checkDesc==null)" @click="createItem(itemPublish)">Create</b-button>
            <b-button variant="danger" block @click="hide">Close</b-button>
          </div>
        </template>
      </b-sidebar>
    </div>

    <b-modal id="update-img-modal" centered title="Update Item Image" @ok="updateItemImg">
      <div>
        <b-form-file
          v-model="itemUpdate.itemImg"
          :state="Boolean(itemUpdate.itemImg)"
          placeholder="Choose a file or drop it here..."
          drop-placeholder="Drop file here..."
        ></b-form-file>
        <div class="change-photo-modal-tips">Selected file: <span style="color: coral">{{ itemUpdate.itemImg ?itemUpdate.itemImg.name : '' }}</span></div>
        <div class="change-photo-modal-tips">Supported Image Type: jpg, jpeg, png, webp</div>
      </div>

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
      itemUpdate:{
        id:'',
        sellerId:'',
        itemName:'',
        itemPrice:'',
        itemTag: null,
        itemStatus: null,
        itemDesc:'',
        itemImg:null
      },
    itemDelete:{
      id:'',
      name:''
    },
      itemTagForCreate: [{text: 'Select a tag', value: null}, 'Book', 'Food', 'Electronics', 'Music', 'Movie','Men_Cloth','Women_Cloth','Sport','Health','Games','Tool','Baby'],
      itemStatusOptions: [{text: 'Select a status', value: null}, 'ACTIVE', 'SOLD', 'BANNED', 'PRIVATE', 'PENDING'],
      itemSearch:{
        id:null,
        name:null,
        maxPrice:null,
        minPrice:0,
        tag:null,
        status:null
      },
      searchBarVisible:'',
      itemPublish:{
        name:'',
        price:'',
        tag:'',
        description:''
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
          AXIOS.get('/item/selectItemBySellerId?sellerId='+response.data.id)
            .then(response=>{
              this.itemList=response.data
            })
            .catch(e=>{

            })
          AXIOS.get('user/selectAllShippingAddressFromUser?userId=' + response.data.id)
            .then(response => {
              for(var index in response.data){
                this.currentUserProfile.shippingAddress.push({value:response.data[index],text:response.data[index].address+' '+response.data[index].name})
              }
            })
        })
    },
    getItemList(id){
      AXIOS.get('/item/selectItemBySellerId?sellerId='+id)
        .then(response=>{
          this.itemList=response.data
        })
        .catch(e=>{

        })
    },
    refreshItemList(){
      this.getItemList(this.currentUserProfile.id)
    },
    loadItemUpdate(item){
      this.itemUpdate.id=item.id
      this.itemUpdate.sellerId=item.sellerId
      this.itemUpdate.itemName=item.name
      this.itemUpdate.itemPrice=item.price
      this.itemUpdate.itemTag=item.tag
      this.itemUpdate.itemStatus=item.status
      this.itemUpdate.itemDesc=item.description
    },
    loadItemDelete(item){
      this.itemDelete.id=item.id
      this.itemDelete.name=item.name
    },
    updateItem(itemUpdate){
      var form_data=new FormData()
      form_data.append('id',itemUpdate.id)
      form_data.append('sellerId',itemUpdate.sellerId)
      form_data.append('itemName',itemUpdate.itemName)
      form_data.append('itemPrice',itemUpdate.itemPrice)
      form_data.append('itemTag',itemUpdate.itemTag)
      form_data.append('itemStatus',itemUpdate.itemStatus)
      form_data.append('itemDesc',itemUpdate.itemDesc)
      AXIOS.put('item/updateItem',form_data,{})
        .then(response=>{
          this.refreshItemList()
          this.toastMessage(response.data.msg)
        })
        .catch(e=>{
          this.toastMessage("Update item fail!")
        })
    },
    deleteItem(id){
      AXIOS.delete('item/deleteItemById?id='+id)
        .then(response=>{
          this.refreshItemList()
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
      if(itemSearch.status!=null){
        form_data.append('status',itemSearch.status)
      }
      form_data.append('sellerId',this.currentUserProfile.id)
      AXIOS.post('/item/queryItem',form_data,{})
        .then(response=>{
          this.itemList=response.data
        })
    },
    createItem(item){
      const form_data=new FormData()
      form_data.append('sellerId',this.currentUserProfile.id)
      form_data.append('itemName',this.itemPublish.name)
      form_data.append('itemPrice',this.itemPublish.price)
      form_data.append('itemTag',this.itemPublish.tag)
      form_data.append('itemStatus','ACTIVE')
      form_data.append('itemDesc',this.itemPublish.description)
      AXIOS.post('item/insertItem',form_data,{})
        .then(response=>{
          this.refreshItemList()
          this.toastMessage(response.data.msg)
        })
        .catch(e=>{
          this.toastMessage('Fail to create item')
        })
    },
    updateItemImg(){
      var form_data=new FormData()
      form_data.append('file',this.itemUpdate.itemImg)
      form_data.append('itemId',this.itemUpdate.id)
      AXIOS.post('/item/addImg',form_data,{})
        .then(response=>{
          this.toastMessage(response.data)
        })
        .catch(e=>{
          this.toastMessage('Fail to update the photo!')
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
    },
  },
  created() {
    this.getUserProfile()
  },
  computed: {
    checkItemName(){
      return this.itemPublish.name.length>0
    },
    checkPrice(){
      return this.itemPublish.price>0
    },
    checkTag(){
      return this.itemPublish.tag!=null&&this.itemPublish.tag.length>0
    },
    checkDesc(){
      return this.itemPublish.description.length<=1000?null:false
    }
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
.search-bar-key{
  padding-left: 10px;
  font-size: 20px;
  font-weight: bold;
}
.not-found-tip{
  font-size: 40px;
  text-align: center;
  padding-top: 50px;
}
</style>
