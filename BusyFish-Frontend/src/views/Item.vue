<template>
  <div>
    <div class="wrapper">
      <AdminNavBar></AdminNavBar>
    </div>
    <h1 class="item-title">
      Item Management
    </h1>
    <div>
      <b-button-group size="lg">
        <b-button v-b-toggle.sidebar-no-header variant="primary">Create Item</b-button>
        <b-button variant="danger">More Function</b-button>
      </b-button-group>
    </div>
    <div>
      <b-sidebar id="sidebar-no-header" width="600px" aria-labelledby="sidebar-no-header-title" no-header shadow right>
        <template #default="{ hide }">
          <div class="p-3">
            <h4 id="sidebar-no-header-title">Create an new Item</h4>
            <b-container fluid>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Seller's ID</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="itemCreate.sellerId" placeholder="Enter seller's ID"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Name</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="itemCreate.itemName" placeholder="Enter item's name"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Price</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="itemCreate.itemPrice" placeholder="Enter item's price"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Tag</label>
                </b-col>
                <b-col sm="10">
                  <b-form-select id="input-default" v-model="itemCreate.itemTag" :options="itemTagForCreate"></b-form-select>
                </b-col>
              </b-row>


              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Status</label>
                </b-col>
                <b-col sm="10">
                  <b-form-select id="input-default" v-model="itemCreate.itemStatus" :options="itemStatusForCreate" ></b-form-select>
                </b-col>
              </b-row>


              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Description</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="itemCreate.itemDesc" placeholder="Enter item's description"></b-form-input>
                </b-col>
              </b-row>

            </b-container>

            <b-button variant="primary" block @click="createItem(itemCreate)">Create</b-button>
            <b-button variant="danger" block @click="hide">Close</b-button>
          </div>
        </template>
      </b-sidebar>
    </div>
    <div>
      <b-sidebar id="update-sidebar" width="600px" aria-labelledby="sidebar-no-header-title" no-header shadow right>
        <template #default="{ hide }">

          <b-row class="my-1">
            <b-col sm="2">
              <label for="input-default">Seller's ID</label>
            </b-col>
            <b-col sm="10">
              <b-form-input id="input-default" v-model="itemUpdate.sellerId" placeholder=""></b-form-input>
            </b-col>
          </b-row>

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
              <label for="input-default">Status</label>
            </b-col>
            <b-col sm="10">
              <b-form-select id="input-default" v-model="itemUpdate.itemStatus" :options="itemStatusForCreate" ></b-form-select>
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

          <b-button variant="primary" block >Update</b-button>
          <b-button variant="danger" block @click="hide">Close</b-button>
        </template>
      </b-sidebar>
    </div>
    <b-table :items="itemList" :fields="fields" striped responsive="sm" style="padding-top: 50px">


      <template #cell(operation)="row">
        <b-button @click="deleteItem(parseInt(row.item.id))" size="sm">Delete</b-button>
        <b-dropdown dropright text="Update Status" size="sm">
          <b-dropdown-item @click="updateItemStatus(row.item.id,'ACTIVE')">ACTIVE</b-dropdown-item>
          <b-dropdown-item @click="updateItemStatus(row.item.id,'SOLD')">SOLD</b-dropdown-item>
          <b-dropdown-item @click="updateItemStatus(row.item.id,'BANNED')">BANNED</b-dropdown-item>
          <b-dropdown-item @click="updateItemStatus(row.item.id,'PRIVATE')">PRIVATE</b-dropdown-item>
          <b-dropdown-item @click="updateItemStatus(row.item.id,'PENDING')">PENDING</b-dropdown-item>
        </b-dropdown>
        <b-button v-b-toggle.update-sidebar size="sm" @click="loadUpdateItem(row.item.id,row.item.sellerId,row.item.name,row.item.price,row.item.tag,row.item.status,row.item.description)">Update</b-button>
      </template>

      <template #cell(show_details)="row">
        <b-button size="sm" @click="row.toggleDetails" class="mr-2">
          {{ row.detailsShowing ? 'Hide' : 'Show'}} Details
        </b-button>
      </template>

      <template #row-details="row">
        <b-card>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Item's ID</b></b-col>
            <b-col>{{ row.item.id }}</b-col>
          </b-row>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Seller's ID</b></b-col>
            <b-col>{{ row.item.sellerId }}</b-col>
          </b-row>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Name</b></b-col>
            <b-col>{{ row.item.name }}</b-col>
          </b-row>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Tag</b></b-col>
            <b-col>{{ row.item.tag }}</b-col>
          </b-row>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Price</b></b-col>
            <b-col>{{ row.item.price }}</b-col>
          </b-row>

          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Description</b></b-col>
            <b-col>{{ row.item.description }}</b-col>
          </b-row>

          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Status</b></b-col>
            <b-col>{{ row.item.status }}</b-col>
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
      fields: ['id', 'name','price','tag','status','show_details','operation'],
      items: [],
      itemList:[],
      error:'',
      itemCreate:{
        sellerId:'',
        itemName:'',
        itemPrice:'',
        itemTag: null,
        itemStatus: null,
        itemDesc:''
      },
      itemUpdate:{
        id:'',
        sellerId:'',
        itemName:'',
        itemPrice:'',
        itemTag: null,
        itemStatus: null,
        itemDesc:''
      },
      itemTagForCreate: [{text: 'Select a tag', value: null}, 'Book', 'Food', 'Electronics', 'Music', 'Movie','Men_Cloth','Women_Cloth','Sport','Health','Games','Tool','Baby'],
      itemStatusForCreate: [{text: 'Select a status', value: null}, 'ACTIVE', 'SOLD', 'PRIVATE', 'BANNED', 'PENDING']
    }
  },
  methods:{
    getItemList(){
      AXIOS.get('item/selectAllItems')
        .then(response=>{
          this.itemList=response.data
          for(const index in response.data){
            this.items.push(
              {
                id: response.data[index].id,
                name: response.data[index].name,
                price: response.data[index].price,
                tag: response.data[index].tag,
                status: response.data[index].status
              }
            )
          }
        })
        .catch(e=>{
          this.error=e
        })
    },
    deleteItem(id){
      AXIOS.delete('item/deleteItemById?id='+id)
        .then(response=>{
          this.refreshItemList()

        })
    },
    updateItemStatus(itemId,newStatus){
      const form_data=new FormData()
      form_data.append('itemId',itemId)
      form_data.append('newStatus',newStatus)
      AXIOS.put('item/updateItemStatus',form_data,{})
        .then(response=>{
          this.refreshItemList()

        })


    },
    createItem(item){
      const form_data=new FormData()
      form_data.append('sellerId',item.sellerId)
      form_data.append('itemName',item.itemName)
      form_data.append('itemPrice',item.itemPrice)
      form_data.append('itemTag',item.itemTag)
      form_data.append('itemStatus',item.itemStatus)
      form_data.append('itemDesc',item.itemDesc)
      AXIOS.post('item/insertItem',form_data,{})
        .then(response=>{
          this.refreshItemList()
          this.toastMessage(response.data.msg)
        })
        .catch(e=>{
          this.toastMessage('Fail to create item')
        })
    },
    refreshItemList(){
      this.getItemList()
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
    loadUpdateItem(id,sellerId,itemName,itemPrice,itemTag,itemStatus,itemDesc){
      this.itemUpdate.id=id
      this.itemUpdate.sellerId=sellerId
      this.itemUpdate.itemName=itemName
      this.itemUpdate.itemPrice=itemPrice
      this.itemUpdate.itemTag=itemTag
      this.itemUpdate.itemStatus=itemStatus
      this.itemUpdate.itemDesc=itemDesc
    }
  }
,
  created() {
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
