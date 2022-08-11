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
        <b-button v-b-toggle.sidebar-no-header variant="success">
          Create Item&nbsp;<b-icon icon="plus-square"></b-icon>
        </b-button>
        <b-button variant="primary" v-b-toggle.item-search-bar>Search Item&nbsp;<b-icon icon="search"></b-icon></b-button>
        <b-button variant="danger">More Function</b-button>
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
          <b-form-group id="input-group-3" label="Status:" label-for="input-1" class="search-bar-key">
            <b-form-select :options="itemStatusForCreate" v-model="itemSearch.status">
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
      <b-sidebar id="update-sidebar" width="600px" aria-labelledby="sidebar-update-title" no-header shadow right>
        <template #default="{ hide }">
          <div class="p-3">
            <h4 id="sidebar-update-title">Update Item</h4>
            <b-container fluid>
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

            </b-container>
            <b-button variant="primary" @click="updateItem(itemUpdate)" block >Update</b-button>
            <b-button variant="danger" block @click="hide">Close</b-button>
          </div>

        </template>
      </b-sidebar>
    </div>
    <b-table :items="itemList" :fields="fields" striped responsive="sm" style="padding-top: 50px">


      <template #cell(operation)="row">
        <b-button @click="deleteItem(parseInt(row.item.id))" size="sm" variant="danger">
          Delete <b-icon icon="trash"></b-icon>
        </b-button>
        <b-dropdown dropright text="Update Status" size="sm" >
          <b-dropdown-item @click="updateItemStatus(row.item.id,'ACTIVE')">ACTIVE</b-dropdown-item>
          <b-dropdown-item @click="updateItemStatus(row.item.id,'SOLD')">SOLD</b-dropdown-item>
          <b-dropdown-item @click="updateItemStatus(row.item.id,'BANNED')">BANNED</b-dropdown-item>
          <b-dropdown-item @click="updateItemStatus(row.item.id,'PRIVATE')">PRIVATE</b-dropdown-item>
          <b-dropdown-item @click="updateItemStatus(row.item.id,'PENDING')">PENDING</b-dropdown-item>
        </b-dropdown>
        <b-button v-b-toggle.update-sidebar size="sm" variant="info" @click="loadUpdateItem(row.item.id,row.item.sellerId,row.item.name,row.item.price,row.item.tag,row.item.status,row.item.description)" >
          Update&nbsp;<b-icon icon="bag-check"></b-icon>
        </b-button>
        <b-button v-b-modal.update-img-modal size="sm" variant="warning" @click="loadItemImgUpdate(row.item.id)" >
          Update Image&nbsp;<b-icon icon="cloud-arrow-up"></b-icon>
        </b-button>
      </template>


      <template #cell(show_details)="row">
        <b-button size="sm" @click="row.toggleDetails" class="mr-2">
          <b-icon icon="three-dots"></b-icon>
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
          <b-button size="sm" v-bind:href="'itemDetail/'+row.item.id">View Individual Page</b-button>
        </b-card>
      </template>
    </b-table>
    <b-modal id="update-img-modal" centered title="Change My Photo" @ok="updateItemImg">
      <div>
        <b-form-file
          v-model="itemImgUpdate.itemImg"
          :state="Boolean(itemImgUpdate.itemImg)"
          placeholder="Choose a file or drop it here..."
          drop-placeholder="Drop file here..."
        ></b-form-file>
        <div class="change-photo-modal-tips">Selected file: <span style="color: coral">{{ itemImgUpdate.itemImg ?itemImgUpdate.itemImg.name : '' }}</span></div>
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
      itemSearch:{
        id:null,
        name:null,
        maxPrice:null,
        minPrice:0,
        tag:null,
        status:null,
        sellerId: null
      },
      itemTagForCreate: [{text: 'All', value: null}, 'Book', 'Food', 'Electronics', 'Music', 'Movie','Men_Cloth','Women_Cloth','Sport','Health','Games','Tool','Baby'],
      itemStatusForCreate: [{text: 'All', value: null}, 'ACTIVE', 'SOLD', 'PRIVATE', 'BANNED', 'PENDING'],
      itemImgUpdate:{
        itemImg:null,
        itemId:null
      }
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
      if(itemSearch.status!=null){
        form_data.append('status',itemSearch.status)
      }
      AXIOS.post('/item/queryItem',form_data,{})
        .then(response=>{
          this.itemList=response.data
        })
    },
    loadItemImgUpdate(itemId){
      this.itemImgUpdate.itemId=itemId
    },
    updateItemImg(){
      var form_data=new FormData()
      form_data.append('file',this.itemImgUpdate.itemImg)
      form_data.append('itemId',this.itemImgUpdate.itemId)
      AXIOS.post('/item/addImg',form_data,{})
        .then(response=>{
          this.toastMessage(response.data)
        })
        .catch(e=>{
          this.toastMessage('Fail to update the photo!')
        })
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
