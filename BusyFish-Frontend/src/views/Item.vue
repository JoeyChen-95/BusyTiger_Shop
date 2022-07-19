<template>
  <div>
    <h1>
      Item Management
    </h1>

    <b-button @click="getItemList">Refresh</b-button>
    <b-table :items="itemList" :fields="fields" striped responsive="sm">
      <template #cell(show_details)="row">
        <b-button size="sm" @click="row.toggleDetails" class="mr-2">
          {{ row.detailsShowing ? 'Hide' : 'Show'}} Details
        </b-button>
      </template>

      <template #cell(operation)="row">
        <b-dropdown text="Operations" size="sm">
          <b-dropdown-item href="#">Update</b-dropdown-item>
          <b-dropdown-item @click="deleteItem(parseInt(row.item.id))">Delete</b-dropdown-item>
        </b-dropdown>
      </template>

      <template #row-details="row">
        <b-card>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Description</b></b-col>
            <b-col>{{ row.item.description }}</b-col>
          </b-row>

          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Seller's ID</b></b-col>
            <b-col>{{ row.item.sellerId }}</b-col>
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
      error:''
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
      AXIOS.delete('item/deleteItemById?id='+Integer)
        .then(response=>{

        })
    }

  },
  created() {
    this.getItemList()
  }
}
</script>

<style scoped>

</style>
