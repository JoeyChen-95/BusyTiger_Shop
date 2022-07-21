<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>
    <div>{{currentUserProfile.username}}</div>
    <div><b-button @click="console.log(currentUserProfile.username+'aaa')"></b-button></div>
    <div>
      <b-card>
        <b-row class="mb-2">
          <b-col sm="3" class="text-sm-right"><b>ID</b></b-col>
          <b-col>{{currentUserProfile.id}}</b-col>
        </b-row>
        <b-row class="mb-2">
          <b-col sm="3" class="text-sm-right"><b>Username</b></b-col>
          <b-col>{{currentUserProfile.username}}</b-col>
        </b-row>
        <b-row class="mb-2">
          <b-col sm="3" class="text-sm-right"><b>Email</b></b-col>
          <b-col>{{currentUserProfile.email}}</b-col>
        </b-row>
        <b-row class="mb-2">
          <b-col sm="3" class="text-sm-right"><b>Primary Phone</b></b-col>
          <b-col>{{currentUserProfile.primaryPhone}}</b-col>
        </b-row>
<!--        <b-row class="mb-2">-->
<!--          <b-col sm="3" class="text-sm-right"><b>Shipping Addresses</b></b-col>-->
<!--          <b-card>-->
<!--            <b-table striped hover :items="list" :fields="shippingAddressField"></b-table>-->
<!--          </b-card>-->
<!--        </b-row>-->
      </b-card>
    </div>
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
      fields: ['id', 'username','memberShip', 'show_details','Operation'],
      shippingAddressField: ['name','phone', 'address'],
      currentUserProfile:{
        id:'',
        username:'',
        email:'',
        primaryPhone: '',
        memberShip: ''
      },
      list:[]
      ,
      userUpdate:{
        id:'',
        username:'',
        password:'',
        email:'',
        primaryPhone:'',
        memberShip: null
      },
      userMemberShipOptions:['REGULAR', 'GOLDEN_PRIME', 'DIAMOND_PRIME', 'BANNED', 'FROZEN']
    }
  },
  methods:{
    getUserProfile(){
      AXIOS.get('/user/getCurrentUserId')
        .then(response=>{
          this.currentUserProfile.id=response.data.id
          this.currentUserProfile.username=response.data.username
          this.currentUserProfile.email=response.data.email
          this.currentUserProfile.primaryPhone=response.data.primaryPhone
          // AXIOS.get('user/selectAllShippingAddressFromUser?userId='+response.data.id)
          //   .then(response2=>{
          //     this.list=response2.data
          //   })

        })
    }
  },
  created() {
    this.getUserProfile()
    this.getUserProfile()
    this.getUserProfile()
    this.getUserProfile()
  },
  computed:{
    key(){
      return this.$route.path+Math.random();
    }
  }
}
</script>

<style scoped>


</style>
