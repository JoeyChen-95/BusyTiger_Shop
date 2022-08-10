<template>
  <div>
    <div>
      <b-navbar toggleable="xl" type="dark"style="background-color: #D8C7B5;color: #674B36">
        <b-navbar-brand><router-link to="/recommend/"><span style="font-size: 25px">BusyTiger</span></router-link></b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item><router-link to="/recommend/">Recommend&nbsp;<b-icon icon="shop"></b-icon></router-link></b-nav-item>
            &nbsp;&nbsp;
            <b-nav-item><router-link to="/byCategory">By Category&nbsp;<b-icon icon="grid-fill"></b-icon></router-link></b-nav-item>
            &nbsp;&nbsp;
            <b-nav-item><router-link to="/busyTigerChoice">BusyTiger Prime Collection&nbsp;<b-icon icon="award-fill"></b-icon></router-link></b-nav-item>
            &nbsp;&nbsp;
            <b-nav-item><router-link to="/myOrder">My Order&nbsp;<b-icon icon="box-seam"></b-icon></router-link></b-nav-item>
            &nbsp;&nbsp;
            <b-nav-item><router-link to="/mySell">My Sell&nbsp;<b-icon icon="cash-coin"></b-icon></router-link></b-nav-item>
            &nbsp;&nbsp;
            <b-nav-item><router-link to="/myItem">My Published Item&nbsp;<b-icon icon="clipboard-plus"></b-icon></router-link></b-nav-item>
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item-dropdown right class="router-link-active">
              <!-- Using 'button-content' slot -->
              <template #button-content class="router-link-active">
                <b-icon icon="person-circle" class="router-link-active"></b-icon>
                <em class="router-link-active">    {{currentUser.username==null?'You haven\'t login yet!':'Hello, '+currentUser.username+'!'}}</em>
              </template>
              <b-dropdown-item @click="deleteSession" href="/">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>
            <b-nav-item><router-link to="/userProfile">My Profile</router-link></b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>
    <div class="content">
      <router-view :key="key"> </router-view>
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
  headers: {'Access-Control-Allow-Origin': frontendUrl
  },
  withCredentials:true,
  crossDomain:true
})
export default {
  data(){
    return{
      currentUser:{
        id:'',
        username:null
      }

    }
  },
  methods:{
    deleteSession(){
      AXIOS.get('/user/deleteSession')
        .then(response=>{
        })
    },
    getCurrentUserInfo(){
      AXIOS.get('/user/getCurrentUserId')
        .then(response=>{
          this.currentUser.id=response.data.id
          this.currentUser.username=response.data.username
        })
    }
  },
  created() {
    this.getCurrentUserInfo()
  },
  computed:{
    key(){
      return this.$route.path+Math.random();
    }
  }
}
</script>

<style scoped>
.router-link-active {
  font-size: 18px;
  font-weight: bolder;
  color: #674B36;
}
a{
  font-size: 18px;
  font-weight: bold;
  color: #674B36;
}
</style>

