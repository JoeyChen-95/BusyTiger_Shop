<template id="login">
  <div class="user-main">
    <div class="menu-items">
      <div class="signup-form">
        <div class="busyfish-title">
          Welcome to BusyTiger
        </div>
<!--        <div style="text-align: center">-->
<!--          <img style="width: 500px" src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F1114%2F060221092429%2F210602092429-1-1200.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661497900&t=e668aca4f33a9a89a9df659084a954a5">-->
<!--        </div>-->

        <div>
          <b-carousel
            id="carousel-1"
            v-model="slide"
            :interval="4000"
            controls
            indicators
            background="#ababab"
            img-width="1px"
            img-height="1px"
            style="text-shadow: 1px 1px 2px #333;"
            @sliding-start="onSlideStart"
            @sliding-end="onSlideEnd"
          >
            <!-- Text slides with image -->
            <b-carousel-slide
              caption="First slide"
              text="Nulla vitae elit libero, a pharetra augue mollis interdum."
              img-src="https://placekitten.com/50/50"
            ></b-carousel-slide>

            <!-- Slides with custom text -->
            <b-carousel-slide img-src="https://placekitten.com/50/50">
              <h1>Hello world!</h1>
            </b-carousel-slide>
            <!-- Slides with image only -->
            <b-carousel-slide img-src="https://placekitten.com/50/50"></b-carousel-slide>


          </b-carousel>

          <p class="mt-4">
            Slide #: {{ slide }}<br>
            Sliding: {{ sliding }}
          </p>
        </div>




        <div class="busyfish-desc" >
          BusyTiger, one of the biggest online second hands shop in the world. Provide clients with excellent and satisfactory service.
        </div>

        <div class="signUp">
          <b-button href="/login" size="lg" type="submit" variant="primary">Login in
          </b-button>
        </div>

        <div class="signUp">
          <b-button href="/signUp" size="lg" type="submit" variant="success">Don't have an account?
          </b-button>
        </div>
        <div style="padding-top: 14vh"></div>

      </div>
    </div>


  </div>
</template>
<script>
import axios from 'axios'

var config = require('../../config')
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
  data() {
    return {
      slide: 0,
      sliding: null
    }
  },
  methods:{
    onSlideStart(slide) {
      this.sliding = true
    },
    onSlideEnd(slide) {
      this.sliding = false
    }

  },
  created() {
  }
}

</script>
<style>
.user-main {
  background-repeat: no-repeat;
  background-size: cover;
  background-attachment: scroll;
}
.user-info {
  font-size: 32px;
  font-weight: bold;
  text-align: center;
  box-shadow: 0 0 50px rgba(0, 0, 0, 0.3);
  padding: 10px;
  background: #fff;
  margin: 0 auto;
}
.menu-items {
  margin-top: 50px;
}
.signup-form {
  margin: 0 400px 0 400px;
  padding-bottom: 50px;
}
.busyfish-title {
  text-align: center;
  font-size: 70px;
  font-weight: bold;
  margin-bottom: 20px;
}
.signUp {
  padding-top: 30px;
  text-align: center
}
.busyfish-desc {
  text-align: center;
  font-size: 26px;
  font-weight: bold;
  margin-bottom: 20px;
}

</style>
