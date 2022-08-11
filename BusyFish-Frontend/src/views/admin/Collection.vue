<template>
  <div>
    <div class="wrapper">
      <AdminNavBar></AdminNavBar>
    </div>
    <h1 class="item-title">
      Collection Management
    </h1>
    <div>
      <b-button-group size="lg">
        <b-button variant="primary" v-b-toggle.collection-add-bar>Create a new Collection&nbsp;<b-icon icon="bookmark-plus"></b-icon></b-button>
      </b-button-group>
    </div>
    <div style="padding-left: 10px">
      <b-collapse id="collection-add-bar">
        <b-form inline style="margin: 15px 0" id="item-search-bar">
          <b-form-group id="input-group-3" label="Name:" label-for="input-1" class="search-bar-key">
            <b-form-input
              id="input-1"
              class="mb-2 mr-sm-2 mb-sm-0"
              placeholder="Name of the new collection"
              v-model="collectionCreate"
            ></b-form-input>
            <b-button @click="createNewCollection(collectionCreate)" variant="primary">Create</b-button>
          </b-form-group>
        </b-form>

      </b-collapse>
    </div>
    <div>
      <b-container fluid>
        <b-row cols="1">
          <b-col v-for="collection in collectionList" style="padding-top: 20px">
            <b-card bg-variant="light" >
              <div>
                <h3 class="order-card-order-name-title">{{collection}} &nbsp;<b-icon icon="bookmarks"></b-icon></h3>

              </div>
              <b-button
                @click="loadCurrentViewCollectionName(collection)"
                href="#"
                variant="primary"
                v-b-modal.items-modal>
                View Detail
              </b-button>
              <b-button
                @click="deleteCollection(collection)"
                variant="danger">
                Delete
              </b-button>
            </b-card>
          </b-col>
        </b-row>
      </b-container>

    </div>
    <b-modal id="items-modal"  v-bind:title="currentViewCollectionName+'\'s Items'" centered size="lg" @ok="">
      <b-form-group>
        <b-form-input type="number" v-model="itemAdd" placeholder="Item's Id"></b-form-input>
        <b-button variant="primary" @click="addItemToCollection(currentViewCollectionName,itemAdd)">Add</b-button>
      </b-form-group>

      <b-table :items="currentViewCollectionItems" :fields="fields">
        <template #cell(operations)="row">
          <b-button @click="deleteItemFromCollection(currentViewCollectionName,row.item.id)" variant="danger" size="sm">
            <b-icon icon="trash"></b-icon>
            &nbsp;Delete
          </b-button>
        </template>

      </b-table>


      <template #modal-footer="{ cancel,ok }">
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
  name: "Collection",
  data(){
    return{
      fields:['id','sellerId','name','price','tag','status','operations'],
      collectionList:[],
      collectionCreate:'',
      currentViewCollectionName:'',
      currentViewCollectionItems:[],
      itemAdd:''
    }
  },
  methods:{
    getCollectionList(){
      AXIOS.get('/collection/selectAllCollection')
        .then(response=>{
          this.collectionList=response.data
        })
        .catch(e=>{
          this.toastMessage("Fail to obtain collection information!")
        })
    },
    loadCurrentViewCollectionName(name){
      this.currentViewCollectionName=name
      AXIOS.get('/collection/selectCollection?collectionName='+name)
        .then(response=>{
          this.currentViewCollectionItems=response.data
        })
    },
    deleteItemFromCollection(collectionName,itemId){
      var form_data=new FormData()
      form_data.append('collectionName',collectionName)
      form_data.append('itemId',itemId)
      AXIOS.post('/collection/deleteItemFromCollection',form_data,{})
        .then(response=>{
          this.toastMessage(response.data.msg)
          this.loadCurrentViewCollectionName(collectionName)
        })
    },
    addItemToCollection(collectionName,itemId){
      var form_data=new FormData()
      form_data.append('collectionName',collectionName)
      form_data.append('itemId',itemId)
      AXIOS.post('/collection/addItemToCollection',form_data,{})
        .then(response=>{
          this.toastMessage(response.data.msg)
          this.loadCurrentViewCollectionName(collectionName)
        })
    },
    createNewCollection(name){
      var form_data=new FormData()
      form_data.append('collectionName',name)
      AXIOS.post('/collection/insertCollection',form_data,{})
        .then(response=>{
          this.toastMessage(response.data.msg)
          this.refreshCollectionList()
        })
        .catch(e=>{
          this.toastMessage('Fail to create the collection!')
        })
    },
    deleteCollection(name){
      AXIOS.delete('/collection/deleteCollection?collectionName='+name)
        .then(response=>{
          this.refreshCollectionList()
          this.toastMessage(response.data.msg)
        })
    },
    refreshCollectionList(){
      this.getCollectionList()
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
    this.refreshCollectionList()

  },
  computed:{
    getItemsOfCollection(){
      AXIOS.get('/collection/selectCollection?collectionName='+'FOOD')
        .then(response=>{
          return response.data
        })
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
</style>
