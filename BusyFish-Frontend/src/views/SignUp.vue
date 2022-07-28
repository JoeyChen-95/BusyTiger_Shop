<template id="login">
  <div class="user-main">
    <div>
      <b-button @click="$router.push('/')" variant="outline-primary" class="goToHome"><b-icon icon="house"></b-icon></b-button>
      <div class="user-info">
        Sign Up
      </div>
    </div>

    <div class="menu-items">
      <div class="signup-form">
        <div class="signup-form-title" style="font-size: 50px">
          Create a new account for you
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
                @blur.native.capture="checkRepeatUsername(form.username)"
                :state="checkUsername&&!usernameRepeat"
              ></b-form-input>

              <b-form-invalid-feedback id="username">
                {{usernameRepeat?'A user has already registered this username':'The username can only contains English letters and Chinese characters, "_", "-" and be 4 - 10 characters long'}}
              </b-form-invalid-feedback>

              <b-form-text id="username">Your username.</b-form-text>

            </b-form-group>
            <!--password-->
            <b-form-group id="input-group-5" label="Password:" label-for="password">
              <b-form-input
                id="password"
                v-model="form.userPassword"
                :state="checkPassword"
                placeholder="Enter password"
                type="password"
              ></b-form-input>
              <b-form-invalid-feedback id="password">
                Password should contain 1 upper letter, 1 lower letter, 1 digit and be 8-16 characters long
              </b-form-invalid-feedback>

              <b-form-text id="password">Your password.</b-form-text>
            </b-form-group>

            <b-form-group id="input-group-5" label="Confirm Password:" label-for="password">
              <b-form-input
                id="confirmPassword"
                v-model="form.confirmPassword"
                placeholder="Enter confirm password"
                type="password"
                :state="checkConfirmPassword"
              ></b-form-input>
              <b-form-invalid-feedback id="confirmPassword">
               Your confirm password is not identical to your password
              </b-form-invalid-feedback>

              <b-form-text id="confirmPassword">Your password again.</b-form-text>
            </b-form-group>


            <b-form-group id="input-group-5" label="Email:" label-for="password">
              <b-form-input
                id="userEmail"
                v-model="form.userEmail"
                placeholder="Enter Email"
                :state="checkEmail"
              ></b-form-input>
              <b-form-invalid-feedback id="confirmPassword">
                Your email address is invalid
              </b-form-invalid-feedback>

              <b-form-text id="confirmPassword">Your email address: name@example.com</b-form-text>
            </b-form-group>
            <b-form-group id="input-group-5" label="Primary Phone Number:" label-for="password">
              <b-form-input
                id="userPrimaryPhone"
                v-model="form.userPrimaryPhone"
                placeholder="Enter Phone Number"
                :state="checkPhone"
              ></b-form-input>
            </b-form-group>
            <b-form-invalid-feedback id="confirmPassword">
              Your confirm password is not identical to your password
            </b-form-invalid-feedback>

            <b-form-text id="userPrimaryPhone">Your phone number</b-form-text>
          </div>

          <div style="text-align: center">
            <div class="formInputError">
              {{ formInputError.usernameInputError }}
            </div>
            <b-button @click="signUp(form)" size="lg" type="submit" variant="primary" :disabled="!(checkEmail&&checkConfirmPassword&&checkUsername&&checkPassword&&checkPhone)">Sign Up!</b-button>
            <b-button size="lg" type="reset" variant="danger">Reset</b-button>
          </div>
        </b-form>

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
      //data of sign up
      form: {
        userEmail: '',
        username: '',
        userPassword: '',
        userPrimaryPhone:'',
        confirmPassword:'',
        error: ''
      },
      show: true,
      usernameRepeat:false,
      //error
      formInputError: {
        usernameInputError: '',
        passwordInputError:''
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
      this.form.userEmail = ''
      this.form.username = ''
      this.form.userPassword = ''
      this.form.confirmPassword = ''
      this.form.userPrimaryPhone = ''
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
              this.$router.push('recommend')
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
            this.$router.push('recommend')
          }
        })
    },
    signUp(form){
      var form_data=new FormData()
      form_data.append('username',form.username)
      form_data.append('userPassword',form.userPassword)
      form_data.append('userEmail',form.userEmail)
      form_data.append('userPrimaryPhone',form.userPrimaryPhone)
      form_data.append('userMemberShip','REGULAR')
      AXIOS.post('user/insertUser',form_data,{})
        .then(response=>{
          if(response.data.code==200){
            AXIOS.get('/user/login?username='+form.username+'&password='+form.userPassword)
              .then(response=>{
                  this.$router.push('recommend')
              })
          }else{
            this.formInputError.usernameInputError='Fail to sign up: '+response.data.msg
          }
        })

    },
    checkRepeatUsername(username){
      AXIOS.get('/user/selectUserByUsername?username='+username)
        .then(response=>{
          if(response.data){
            this.usernameRepeat=true
          }else{
            this.usernameRepeat=false
          }
        })
    }
  },
  created() {
    this.tryToLoginWithSession()
  },
  computed:{
    checkPassword() {
      let reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,16}$/
      let flag = reg.test(this.form.userPassword)
      return flag
    },
    checkUsername() {
      var reg = /^[\u4e00-\u9fa5a-zA-Z0-9_-]{4,20}$/;
      let flag = reg.test(this.form.username)
      return flag
    },
    checkConfirmPassword() {
      return this.checkPassword&&this.form.userPassword==this.form.confirmPassword
    },
    checkEmail(){
      let reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
      let flag = reg.test(this.form.userEmail)
      return flag
    },
    checkPhone(){
      var reg = /^[0-9]/;
      let flag = reg.test(this.form.userPrimaryPhone)
      return flag
    }
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
