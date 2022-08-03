<template id="login">
  <div class="user-main">
    <div>
      <b-button @click="$router.push('/')" variant="outline-primary" class="goToHome"><b-icon icon="house"></b-icon></b-button>
      <div class="user-info">
        Login
      </div>
    </div>

    <div class="menu-items">
      <div class="signup-form">
        <div class="signup-form-title" style="font-size: 50px">
          Welcome
        </div>
        <!--Form of submitting user login request-->
        <b-form @submit="onSubmit" @reset="onReset" v-if="show">

          <div id="onlineAccountInfo" class="onlineAccountInfo">
            <!--username-->
            <b-form-group id="input-group-2" label="Username:" label-for="username">
              <b-form-input
                id="username"
                placeholder="Enter username"
                v-model="form.username"
              ></b-form-input>
              <div class="formInputError">
<!--                {{ formInputError.usernameInputError }}-->
              </div>
            </b-form-group>
            <!--password-->
            <b-form-group id="input-group-5" label="Password:" label-for="password">
              <b-form-input
                id="password"
                v-model="form.password"
                placeholder="Enter password"
                type="password"
              ></b-form-input>
            </b-form-group>

          </div>

          <div class="errorMessage" id="errorMessage">
            <p>
              {{ form.error }}
            </p>
          </div>

          <div style="text-align: center">
            <b-button size="lg" type="submit" variant="primary">Login</b-button>
            <b-button size="lg" type="reset" variant="danger">Reset</b-button>
          </div>
        </b-form>

        <div class="signUp">
          <b-button size="lg" type="submit" variant="warning" v-b-popover.hover="'Contact: 707973013@qq.com to reset'" title="Reset your Password">Forget My Password
          </b-button>
        </div>

        <div class="signUp">
          <b-button @click="$router.push('/signUp')" size="lg" type="submit" variant="success">Don't have an account?
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
      //data of login
      form: {
        email: '',
        username: '',
        password: '',
        error: ''
      },
      show: true,
      //error
      formInputError: {
        usernameInputError: '',
      }
    }
  },
  methods:{
    onSubmit(event) {
      event.preventDefault()
      this.login(this.form.username, this.form.password)
    },

    /**
     * reset the form
     * @param event
     */
    onReset(event) {
      event.preventDefault()
      // Reset our form values
      this.form.email = ''
      this.form.username = ''
      this.form.password = ''
      this.error = ''
      // Trick to reset/clear native browser form validation state
      this.show = false
      this.$nextTick(() => {
        this.show = true
      })
    },
    login(username,password){
      if(username=="admin"&&password=="admin"){
        this.$router.push('/adminWelcome')
      }else{
        AXIOS.get('/user/login?username='+username+'&password='+password)
          .then(response=>{
            if(response.data.code==200){
              this.$router.push('recommend/')
            }else{
              this.form.error=response.data.msg
            }
          })
          .catch(e=>{
            this.form.error="Unknown error"
          })
      }
    },
    tryToLoginWithSession(){
      AXIOS.get('user/getCurrentUserId')
        .then(response=>{
          if(response.data.id){
            this.$router.push('recommend/')
          }
        })
    },
  },
  created() {
    this.tryToLoginWithSession()
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
.signup-form-title {
  text-align: center;
  font-size: 26px;
  font-weight: bold;
  margin-bottom: 20px;
}
.signUp {
  padding-top: 30px;
  text-align: center
}
.errorMessage {
  color: #dd2222;
}
.formInputError {
  color: #dd2222;
}
.goToHome{
  float: left;
  position: absolute;
  left: 0;
  margin: 13px;
}
</style>
