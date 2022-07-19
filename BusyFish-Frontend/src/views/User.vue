<template>

  <div>
    <h1>
      User Management
    </h1>
    <b-button @click="flipShowPassword" variant="danger" size="sm">{{ showPassword?'Hide Password':'Show Password'}}</b-button>
    <b-table :items="userList" :fields="fields" striped responsive="sm">
      <template #cell(show_details)="row">
        <b-button size="sm" @click="row.toggleDetails" class="mr-2">
          {{ row.detailsShowing ? 'Hide' : 'Show'}} Details
        </b-button>
      </template>

      <template #row-details="row">
        <b-card>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Email</b></b-col>
            <b-col>{{ row.item.email }}</b-col>
          </b-row>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Primary Phone</b></b-col>
            <b-col>{{ row.item.primaryPhone }}</b-col>
          </b-row>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Password</b></b-col>
            <b-col>{{ showPassword ? row.item.password:'********'}}</b-col>
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
      fields: ['id', 'username','memberShip', 'show_details'],
      items: [],
      userList:[],
      showPassword: false
    }
  },
  methods:{
    getUserList(){
      AXIOS.get('user/selectAllUsers')
        .then(response=>{
          this.userList=response.data
          for(const index in response.data){
            this.items.push(
              {
                id: response.data[index].id,
                username: response.data[index].username,
                memberShip: response.data[index].memberShip,
              }
            )
          }
        })
    },
    flipShowPassword(){
      this.showPassword=!this.showPassword
    }
  },
  created() {
    this.getUserList()
  }
}
</script>

<style scoped>

</style>
