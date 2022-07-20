<template>

  <div>
    <div class="wrapper">
      <AdminNavBar></AdminNavBar>
    </div>
    <h1 class="user-title">
      User Management
    </h1>
    <div>
      <b-button-group size="lg">
        <b-button v-b-toggle.sidebar-no-header variant="primary">Create User</b-button>
        <b-button @click="flipShowPassword" variant="danger" >{{ showPassword?'Hide Password':'Show Password'}}</b-button>
        <b-button variant="safe">More Function</b-button>
      </b-button-group>
    </div>
    <div>
      <b-sidebar id="sidebar-no-header" width="600px" aria-labelledby="sidebar-no-header-title" no-header shadow right>
        <template #default="{ hide }">
          <div class="p-3">
            <h4 id="sidebar-no-header-title">Create an new user</h4>
            <b-container fluid>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Username</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="userCreate.username" placeholder="Enter username"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Password</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default"  v-model="userCreate.userPassword" placeholder="Enter password"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Email</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="userCreate.userEmail" placeholder="Enter email"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Primary Phone</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="userCreate.userPrimaryPhone" placeholder="Enter user's phone number"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Membership</label>
                </b-col>
                <b-col sm="10">
                  <b-form-select id="input-default" :options="userCreateMembership" v-model="userCreate.userMemberShip" ></b-form-select>
                </b-col>
              </b-row>

            </b-container>

            <b-button variant="primary" block @click="createUser(userCreate)">Create</b-button>
            <b-button variant="danger" block @click="hide">Close</b-button>
          </div>
        </template>
      </b-sidebar>
    </div>

    <b-table :items="userList" :fields="fields" striped responsive="sm">
      <template #cell(show_details)="row">
        <b-button size="sm" @click="row.toggleDetails" class="mr-2">
          {{ row.detailsShowing ? 'Hide' : 'Show'}} Details
        </b-button>
      </template>

      <template #cell(operation)="row">
        <b-button @click="deleteUser(parseInt(row.item.id))" size="sm">Delete</b-button>
        <b-dropdown dropright text="Update Membership" size="sm">
          <b-dropdown-item @click="updateUserMembership(row.item.id,'REGULAR')">REGULAR</b-dropdown-item>
          <b-dropdown-item @click="updateUserMembership(row.item.id,'GOLDEN_PRIME')">GOLDEN_PRIME</b-dropdown-item>
          <b-dropdown-item @click="updateUserMembership(row.item.id,'DIAMOND_PRIME')">DIAMOND_PRIME</b-dropdown-item>
          <b-dropdown-item @click="updateUserMembership(row.item.id,'FROZEN')">FROZEN</b-dropdown-item>
          <b-dropdown-item @click="updateUserMembership(row.item.id,'BANNED')">BANNED</b-dropdown-item>
        </b-dropdown>
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
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Shipping Addresses</b></b-col>
            <b-card>
              <b-table striped hover :items="row.item.shippingList" :fields="shippingAddressField"></b-table>
            </b-card>
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
      fields: ['id', 'username','memberShip', 'show_details','Operation'],
      shippingAddressField: ['code','name','phone', 'address'],
      items: [],
      userList:[],
      showPassword: false,
      userShippingAddressList:[],
      userCreate:{
        userEmail:'',
        userMemberShip:'',
        userPassword:'',
        userPrimaryPhone:'',
        username:''
      },
      userCreateMembership:[ 'REGULAR', 'GOLDEN_PRIME', 'DIAMOND_PRIME', 'BANNED', 'FROZEN']
    }
  },
  methods:{
    getUserList(){
      AXIOS.get('user/selectAllUsers')
        .then(response=>{
          this.userList=response.data
          this.getUserShippingAddress()
        })
    },
    refreshUserList(){
      this.getUserList()
    },
    flipShowPassword(){
      this.showPassword=!this.showPassword
    },
    getUserShippingAddress(){
      for(const index in this.userList){
        AXIOS.get('user/selectAllShippingAddressFromUser?userId='+this.userList[index].id)
          .then(response=>{
            this.userList[index].shippingList=response.data
          })
      }
    },
    deleteUser(id){

    },
    updateUserMembership(id,newMembership){

    },
    createUser(userCreate){
      var form_data=new FormData()
      form_data.append('userEmail',userCreate.userEmail)
      form_data.append('userMemberShip',userCreate.userMemberShip)
      form_data.append('userPassword',userCreate.userPassword)
      form_data.append('userPrimaryPhone',userCreate.userPrimaryPhone)
      form_data.append('username',userCreate.username)
      AXIOS.post('/user/insertUser',form_data,{})
        .then(response=>{
          this.refreshUserList()
          this.toastMessage(response.data.msg)
        })
        .catch(e=>{
          this.toastMessage('Fail to create user')
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
    this.getUserList()
  }
}
</script>

<style scoped>
.user-title{
  font-size: 32px;
  font-weight: bold;
  text-align: center;
  box-shadow: 0 0 50px rgba(0, 0, 0, 0.3);
  padding: 10px;
  background: #fff;
  margin: 0 auto;
}

</style>
