<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>
    <div>
      <b-container fluid>
        <b-row cols="4">
          <b-col v-for="tag in itemTags2" style="padding-top: 20px">
            <b-card bg-variant="light">
              <router-link v-bind:to="'recommend/'+tag.name">
                <div>
                  <h3 class="order-card-order-name-title">{{tag.name}}</h3>
                  <b-img v-bind:src="tag.img"></b-img>
                </div>
              </router-link>
            </b-card>
          </b-col>
        </b-row>
      </b-container>

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
  },
  withCredentials:true,
  crossDomain:true
})


export default {
  name: "Format",
  data(){
    return{
      itemTags: ['Book', 'Food', 'Electronics', 'Music', 'Movie','Men_Cloth','Women_Cloth','Sport','Health','Games','Tool','Baby'],
      itemTags2: [{
        name:'Book',
        img:'https://www.logosc.cn/oss/icons/2022/06/08/3e6d5e0d86f47133e5edc3ae26500ca6.png'
      },{
        name:'Food',
        img:'https://www.logosc.cn/oss/icons/2022/06/28/ce7ce472e116a07a9841f2c6ef9366b2.png'
    },{
        name:'Electronics',
        img:'https://www.logosc.cn/oss/icons/2022/04/08/4be3593d6d97e196bd3624c483055416.png'
      },{
        name:'Music',
        img:'https://www.logosc.cn/oss/icons/2022/06/24/3fa9312a2db3124e9208450ff05d2527.png'
      },{
        name:'Movie',
        img:'https://www.logosc.cn/oss/icons/2022/06/23/dee329ccb6c0ae55a120bb2478cf8e77.png'
      },{
        name:'Men_Cloth',
        img:'https://www.logosc.cn/oss/icons/2022/07/30/76356c80fdaf7ef0bba3045685fa2f5f.png'
      },{
        name:'Women_Cloth',
        img:'https://www.logosc.cn/oss/icons/2022/05/06/6dad08862309ae46ac262474179bac47.png'
      },{
        name:'Sport',
        img:'https://www.logosc.cn/oss/icons/2022/06/28/be881703fbe897e66c21d4a6cc653b0f.png'
      },{
        name:'Health',
        img:'https://www.logosc.cn/oss/icons/2022/06/26/22b4978938e9424da4c81d9552ada582.png'
      },{
        name:'Games',
        img:'https://www.logosc.cn/oss/icons/2022/06/27/1c1370e37585d802baa28fa6246281a7.png'
      },{
        name:'Tool',
        img:'https://www.logosc.cn/oss/icons/2021/12/19/970fa57a1da294ecb035994cd5f9581e.png'
      },{
        name:'Baby',
        img:'https://www.logosc.cn/oss/icons/2022/05/13/5a09d5d29f2f304e5f2c584637fd0d7d.png'
      }]
    }
  },
  methods:{

  },
  created() {

  }
}
</script>

<style scoped>
.router-link-active {
  font-weight: bolder;
  color: black;
}
a{
  text-decoration: none;
  color: black;
}
</style>
