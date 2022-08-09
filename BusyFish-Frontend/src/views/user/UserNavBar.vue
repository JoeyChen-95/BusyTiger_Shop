<template>
  <div>
    <div>
      <b-navbar toggleable="xl" type="dark" variant="dark">
        <b-navbar-brand><router-link to="/recommend/"><span>BusyTiger</span></router-link></b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav >
            <b-nav-item><router-link to="/recommend/">Recommend</router-link></b-nav-item>
            <b-nav-item><router-link to="/byCategory">By Category</router-link></b-nav-item>
            <b-nav-item><router-link to="/busyTigerChoice">BusyTiger Prime Collection</router-link></b-nav-item>
            <b-nav-item><router-link to="/myOrder">My Order</router-link></b-nav-item>
            <b-nav-item><router-link to="/mySell">My Sell</router-link></b-nav-item>
            <b-nav-item><router-link to="/myItem">My Published Item</router-link></b-nav-item>
            <b-nav-item><router-link to="/userProfile">My Profile</router-link></b-nav-item>
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-form>
              <b-form-input size="sm" class="mr-sm-2" placeholder="Search"></b-form-input>
              <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
            </b-nav-form>
            <b-nav-item-dropdown right>
              <!-- Using 'button-content' slot -->
              <template #button-content>
                <b-icon icon="person-circle"></b-icon>
                <em>Hello, {{ currentUser.username }} !</em>
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
        username:''
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
  font-weight: bolder;
  color: white;
}
a{
  text-decoration: none;
  color: white;
}
</style>

