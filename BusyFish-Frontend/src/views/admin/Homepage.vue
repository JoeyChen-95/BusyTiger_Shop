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
            img-width="1024"
            img-height="480"
            style="text-shadow: 1px 1px 2px #333;"
            @sliding-start="onSlideStart"
            @sliding-end="onSlideEnd"
          >
            <!-- Text slides with image -->

            <b-carousel-slide
              caption="BusyTiger Inc."
              text="Found by 2022, one of the biggest online free market in the world. Provide clients with excellent and satisfactory service."
              img-src="https://picsum.photos/id/593/1024/480/"
            ></b-carousel-slide>
            <b-carousel-slide
              caption="Convenient&Fast"
              text="Via your laptops or computers, you can purchase and sell goods worldwide."
              img-src="https://picsum.photos/id/0/1024/480/"
            ></b-carousel-slide>

            <b-carousel-slide
              caption="All kinds of merchandise"
              text="BusyTiger shop allows user to access all kinds of goods."
              img-src="https://picsum.photos/id/491/1024/480/"
            ></b-carousel-slide>


            <!-- Slides with custom text -->
            <b-carousel-slide img-src="https://picsum.photos/id/292/1024/480/">
              <h1>BusyTiger EAT</h1>
            </b-carousel-slide>
            <!-- Slides with image only -->

            <b-carousel-slide img-src="https://picsum.photos/id/535/1024/480">
              <h1>BusyTiger FASHION</h1>
            </b-carousel-slide>


          </b-carousel>

        </div>

        <div style="padding-top: 20px">
          <b-jumbotron>
            <template #header>Join BusyTiger</template>

            <template #lead>
              Create an account or log in with an existing account, enjoy the fast and convenient seller/buyer service.
            </template>

            <hr class="my-4">

            <b-button @click="tryToLoginWithSession" size="lg" type="submit" variant="primary">Login in
            </b-button>
            <b-button @click="$router.push('/signUp')" size="lg" type="submit" variant="success">Don't have an account?
            </b-button>
          </b-jumbotron>
        </div>

        <div>
          <b-card-group deck>
            <b-card
              title="Author's Github"
              img-src="https://logos-world.net/wp-content/uploads/2020/11/GitHub-Emblem.png"
              img-height="180px"
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 20rem;"
              class="mb-2"
            >
              <b-card-text>
                Click here to view the author's github page.
              </b-card-text>

              <b-button href="https://github.com/JoeyChen-95" variant="primary">Go</b-button>
            </b-card>

            <b-card
              title="Have Questions?"
              img-height="180px"
              img-src="https://media.istockphoto.com/photos/managing-the-days-inquiries-picture-id938430346?k=20&m=938430346&s=612x612&w=0&h=-5azqEhroazZL-ofgB_UWwQe9JMFDar4KnhB8VZiSeU="
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 20rem;"
              class="mb-2"
            >
              <b-card-text>
                Contact our customer service for help. We will reply as soon as possible.
              </b-card-text>

              <b-button href="https://baidu.com" variant="success">Contact us</b-button>
            </b-card>

<!--            <b-card-->
<!--              title="Author's Github"-->
<!--              img-src="https://logos-world.net/wp-content/uploads/2020/11/GitHub-Emblem.png"-->
<!--              img-alt="Image"-->
<!--              img-top-->
<!--              tag="article"-->
<!--              style="max-width: 20rem;"-->
<!--              class="mb-2"-->
<!--            >-->
<!--              <b-card-text>-->
<!--                Click here to view the author's github page.-->
<!--              </b-card-text>-->

<!--              <b-button href="https://github.com/JoeyChen-95" variant="primary">Go</b-button>-->
<!--            </b-card>-->

          </b-card-group>


        </div>



      </div>
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
    },
    tryToLoginWithSession(){
      AXIOS.get('user/getCurrentUserId')
        .then(response=>{
          if(response.data.id){
            this.$router.push('/recommend/')
          }else{
            this.$router.push('/login')
          }
        })
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
