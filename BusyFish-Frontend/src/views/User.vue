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
        <b-button v-b-toggle.sidebar-no-header variant="primary">
          <b-icon icon="person-plus"></b-icon> &nbsp;Create User
        </b-button>
        <b-button @click="flipShowPassword" variant="danger">
          <b-icon icon="eye" v-show="!showPassword"></b-icon>
          <b-icon icon="eye-slash" v-show="showPassword"></b-icon>
          {{ showPassword?'Hide Password':'Show Password'}}
        </b-button>
        <b-button variant="primary" v-b-toggle.item-search-bar>Search Item&nbsp;<b-icon icon="search"></b-icon></b-button>
        <b-button>
          <b-icon icon="three-dots"></b-icon>
          More Function
        </b-button>
      </b-button-group>
    </div>
    <div style="padding-left: 10px">
      <b-collapse id="item-search-bar">
        <b-form inline style="margin: 15px 0" id="item-search-bar">
          <b-form-group id="input-group-3" label="ID:" label-for="input-1" class="search-bar-key">
            <b-form-input
              id="input-1"
              class="mb-2 mr-sm-2 mb-sm-0"
              placeholder="User ID"
              v-model="userSearch.id"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-3" label="Username:" label-for="input-1" class="search-bar-key">
            <b-form-input
              id="input-1"
              class="mb-2 mr-sm-2 mb-sm-0"
              placeholder="Username"
              v-model="userSearch.username"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-3" label="Primary Phone:" label-for="input-1" class="search-bar-key">
            <b-form-input
              id="input-1"
              class="mb-2 mr-sm-2 mb-sm-0"
              placeholder="User's primary phone"
              v-model="userSearch.primaryPhone"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-3" label="Email:" label-for="input-1" class="search-bar-key">
            <b-form-input
              id="input-1"
              class="mb-2 mr-sm-2 mb-sm-0"
              placeholder="User's email address"
              v-model="userSearch.email"
            ></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-3" label="Membership:" label-for="input-1" class="search-bar-key">
            <b-form-select :options="userCreateMembership" v-model="userSearch.memberShip">
            </b-form-select>
          </b-form-group>
          <b-button-group style="padding-left: 5px">
            <b-button variant="primary" @click="searchUser(userSearch)"><b-icon icon="search"></b-icon></b-button>
          </b-button-group>
          <b-button-group style="padding-left: 5px">
            <b-button variant="primary" @click="refreshUserList" style="padding-left: 10px">Show All</b-button>
          </b-button-group>

        </b-form>

      </b-collapse>
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

    <div>
      <b-sidebar id="update-sidebar" width="600px" aria-labelledby="sidebar-update-title" no-header shadow right>
        <template #default="{ hide }">
          <div class="p-3">
            <h4 id="sidebar-update-title">Update User Information</h4>
            <b-container fluid>
              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Username</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="userUpdate.username" placeholder=""></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Name</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="userUpdate.password" placeholder="Enter item's name"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Email</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="userUpdate.email" placeholder="Enter item's price"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Primary Phone</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="userUpdate.primaryPhone" ></b-form-input>
                </b-col>
              </b-row>


              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Membership</label>
                </b-col>
                <b-col sm="10">
                  <b-form-select id="input-default" v-model="userUpdate.memberShip" :options="userCreateMembership" ></b-form-select>
                </b-col>
              </b-row>

            </b-container>
            <b-button variant="primary" @click="updateUser(userUpdate)" block >Update</b-button>
            <b-button variant="danger" block @click="hide">Close</b-button>
          </div>

        </template>
      </b-sidebar>
    </div>

    <b-table :items="userList" :fields="fields" striped responsive="sm">
      <template #cell(show_details)="row">
        <b-button size="sm" @click="row.toggleDetails" class="mr-2">
<!--          {{ row.detailsShowing ? 'Hide' : 'Show'}} Details-->
          <b-icon icon="three-dots"></b-icon>
        </b-button>
      </template>

      <template #cell(operation)="row">
        <b-button @click="deleteUser(parseInt(row.item.id))" variant="danger" size="sm">
          <b-icon icon="person-x"></b-icon>
          &nbsp;Delete
        </b-button>
        <b-button v-b-toggle.update-sidebar @click="loadUpdateUser(row.item)" variant="info" size="sm">
          <b-icon icon="person-lines-fill"></b-icon>
          &nbsp;Update
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
      userUpdate:{
        id:'',
        username:'',
        password:'',
        email:'',
        primaryPhone:'',
        memberShip: null
      },
      userSearch:{
        id:null,
        username:null,
        memberShip:null,
        primaryPhone:null,
        email:null
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
      this.toastMessage("这个功能还没做")

    },
    searchUser(userSearch){
      var form_data=new FormData()
      if(userSearch.id!=null){
        form_data.append('id',userSearch.id)
      }
      if(userSearch.primaryPhone!=null){
        form_data.append('primaryPhone',userSearch.primaryPhone)
      }
      if(userSearch.email!=null){
        form_data.append('email',userSearch.email)
      }
      if(userSearch.username!=null){
        form_data.append('username',userSearch.username)
      }
      if(userSearch.memberShip!=null){
        form_data.append('memberShip',userSearch.memberShip)
      }
      AXIOS.post('/user/queryUser',form_data,{})
        .then(response=>{
          this.userList=response.data
        })
        .catch(e=>{
          this.toastMessage("Fail to query users!")
        })

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
    updateUser(userUpdate){
      var form_data=new FormData()
      form_data.append('id',userUpdate.id)
      form_data.append('username',userUpdate.username)
      form_data.append('password',userUpdate.password)
      form_data.append('email',userUpdate.email)
      form_data.append('primaryPhone',userUpdate.primaryPhone)
      form_data.append('memberShip',userUpdate.memberShip)
      AXIOS.put('/user/updateUser',form_data,{})
        .then(response=>{
          this.refreshUserList()
          this.toastMessage(response.data.msg)
        })
        .catch(e=>{
          this.toastMessage('Fail to update user')
        })

    },
    loadUpdateUser(user){
      this.userUpdate.id=user.id
      this.userUpdate.username=user.username
      this.userUpdate.password=user.password
      this.userUpdate.email=user.email
      this.userUpdate.primaryPhone=user.primaryPhone
      this.userUpdate.memberShip=user.memberShip
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
