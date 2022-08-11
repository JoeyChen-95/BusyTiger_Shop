<template>
  <div>
    <div class="wrapper">
      <UserNavBar></UserNavBar>
    </div>

    <!--    User Edit Profile Sidebar-->
    <div>
      <b-sidebar id="edit-user-profile-sidebar" width="600px" aria-labelledby="sidebar-no-header-title" no-header shadow right>
        <template #default="{ hide }">
          <div class="p-3">
            <h4 id="sidebar-no-header-title">Edit My Profile</h4>
            <b-container fluid>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Username</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="userProfileUpdate.username" placeholder="Enter username"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Email</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="userProfileUpdate.email" placeholder="Enter email"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Primary Phone</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="userProfileUpdate.primaryPhone" placeholder="Enter user's phone number"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Membership</label>
                </b-col>
                <b-col sm="10">
                  {{currentUserProfile.memberShip}}
                </b-col>
              </b-row>

            </b-container>

            <b-button variant="primary" block @click="editMyProfile(userProfileUpdate)">OK</b-button>
            <b-button variant="danger" block @click="hide">Close</b-button>
          </div>
        </template>
      </b-sidebar>
    </div>
<!--    Edit Shipping Address Sidebar-->
    <div>
      <b-sidebar id="edit-shipping-sidebar" width="600px" aria-labelledby="sidebar-no-header-title" no-header shadow right>
        <template #default="{ hide }">
          <div class="p-3">
            <h4 id="sidebar-no-header-title">Edit My Shipping Address</h4>
            <b-container fluid>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Name</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="shippingAddressUpdate.name" placeholder="Enter Name"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Phone</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="shippingAddressUpdate.phone" placeholder="Enter Phone"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Address</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="shippingAddressUpdate.address" placeholder="Enter Address"></b-form-input>
                </b-col>
              </b-row>

            </b-container>

            <b-button variant="primary" block @click="updateShippingAddress(shippingAddressUpdate)">OK</b-button>
            <b-button variant="danger" block @click="hide">Close</b-button>
          </div>
        </template>
      </b-sidebar>
    </div>
<!--Edit Shipping Address Sidebar-->
    <div>
      <b-sidebar id="add-shipping-address-sidebar" width="600px" aria-labelledby="sidebar-no-header-title" no-header shadow right>
        <template #default="{ hide }">
          <div class="p-3">
            <h4 id="sidebar-no-header-title">Add a new shipping address</h4>
            <b-container fluid>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Name</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="shippingAddressCreate.name" placeholder="Name of the receiver"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Phone</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="shippingAddressCreate.phone" placeholder="Phone of the receiver"></b-form-input>
                </b-col>
              </b-row>

              <b-row class="my-1">
                <b-col sm="2">
                  <label for="input-default">Address</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input id="input-default" v-model="shippingAddressCreate.address" placeholder="Address of the receiver"></b-form-input>
                </b-col>
              </b-row>


            </b-container>

            <b-button variant="primary" block @click="addNewShippingAddress(shippingAddressCreate)">OK</b-button>
            <b-button variant="danger" block @click="hide">Close</b-button>
          </div>
        </template>
      </b-sidebar>
    </div>

    <b-modal id="change-my-photo-modal" centered title="Change My Photo" @ok="changeMyPhoto">
      <div>
        <b-form-file
          v-model="uploadedPhoto"
          :state="Boolean(uploadedPhoto)"
          placeholder="Choose a file or drop it here..."
          drop-placeholder="Drop file here..."
        ></b-form-file>
        <div class="change-photo-modal-tips">Selected file: <span style="color: coral">{{ uploadedPhoto ? uploadedPhoto.name : '' }}</span></div>
        <div class="change-photo-modal-tips">Supported Image Type: jpg, jpeg, png, webp</div>
      </div>

    </b-modal>


    <div class="box">
<!--      Left Part of the page: User Avator-->
      <div class="column-left" >
        <div class="mb-2">
          <div class="user-avator">
            <b-avatar id="user-profile-photo" v-bind:src="myPhotoURL" size="20rem" class="a">
            </b-avatar>
<!--            <b-avatar src="http://img.jinghooo.com/img_1648544638755_GYXHesgG6toO8_Tdzl-.png" size="20rem" class="a"></b-avatar>-->
          </div>
          <div class="username-under-avator">{{currentUserProfile.username}}</div>
          <div class="change-photo-button">
            <b-button variant="outline-primary" style="width: 50%" v-b-modal.change-my-photo-modal>Change My Photo</b-button>
          </div>
<!--          <div class="change-photo-button">-->
<!--            <b-button variant="outline-primary" style="width: 50%" @click="getMyPhoto">Get My Photo</b-button>-->
<!--          </div>-->
          <div class="change-photo-button">
            <b-button v-b-toggle.edit-user-profile-sidebar variant="outline-primary" style="width: 50%" @click="loadUpdateProfile">Edit My Profile</b-button>
          </div>
          <div class="change-photo-button">
            <b-button v-b-toggle.add-shipping-address-sidebar variant="outline-primary" style="width: 50%" :disabled="currentUserProfile.shippingAddress.length>=10">Add Shipping Address</b-button>
          </div>
        </div>
      </div>
<!--      Right Part of the page: User Profile-->
      <div class="column-right">
        <div>
          <b-card>
            <b-row class="mb-2">
              <b-col sm="3" class="text-lg-right" style="font-size: 20px"><b>ID</b></b-col>
              <b-col style="font-size: 20px">{{currentUserProfile.id}}</b-col>
            </b-row>
            <b-row class="mb-2">
              <b-col sm="3" class="text-sm-right" style="font-size: 20px"><b>Username</b></b-col>
              <b-col style="font-size: 20px">{{currentUserProfile.username}}</b-col>
            </b-row>
            <b-row class="mb-2">
              <b-col sm="3" class="text-sm-right" style="font-size: 20px"><b>Email</b></b-col>
              <b-col style="font-size: 20px">{{currentUserProfile.email}}</b-col>
            </b-row>
            <b-row class="mb-2">
              <b-col sm="3" class="text-sm-right" style="font-size: 20px"><b>Primary Phone</b></b-col>
              <b-col style="font-size: 20px">{{currentUserProfile.primaryPhone}}</b-col>
            </b-row>
            <b-row class="mb-2">
              <b-col sm="3" class="text-sm-right" style="font-size: 20px"><b>Membership</b></b-col>
              <b-col style="font-size: 20px"><b-badge href="/hint/memberShip" v-bind:variant="currentUserProfile.memberShip=='REGULAR'?'success':currentUserProfile.memberShip=='GOLDEN_PRIME'?'warning':currentUserProfile.memberShip=='DIAMOND_PRIME'?'info':'secondary'">{{currentUserProfile.memberShip.replace('_',' ')}}</b-badge></b-col>
            </b-row>
            <b-row class="mb-2">
              <b-col sm="3" class="text-sm-right" style="font-size: 20px">
                <b>Shipping Addresses</b>
              </b-col>
              <b-card>
                <b-table striped hover :items="currentUserProfile.shippingAddress" :fields="shippingAddressField">
                  <template #cell(operation)="row">
                    <b-button @click="deleteShippingAddress(row.item.code)" variant="danger" size="sm">
                      Delete&nbsp;<b-icon icon="x"></b-icon>
                    </b-button>
                    <b-button v-b-toggle.edit-shipping-sidebar @click="loadUpdateShippingAddress(row.item)" variant="info" size="sm">
                      Edit&nbsp;<b-icon icon="person-lines-fill"></b-icon>
                    </b-button>
                  </template>
                </b-table>
              </b-card>
            </b-row>
          </b-card>
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
  headers: {'Access-Control-Allow-Origin': frontendUrl,
  },
  withCredentials:true,
  crossDomain:true
})

export default {
  data() {
    return {
      fields: ['id', 'username','memberShip', 'show_details','Operation'],
      shippingAddressField: ['name','phone', 'address','operation'],
      currentUserProfile:{
        id:'',
        username:'',
        email:'',
        primaryPhone: '',
        memberShip: '',
        shippingAddress:[],
      },
      userProfileUpdate:{
        username:'',
        email:'',
        primaryPhone:'',
      },
      shippingAddressCreate:{
        name:'',
        phone:'',
        address:''
      },
      shippingAddressUpdate:{
        code:'',
        name:'',
        phone:'',
        address:'',
      },
      userMemberShipOptions:['REGULAR', 'GOLDEN_PRIME', 'DIAMOND_PRIME', 'BANNED', 'FROZEN'],
      uploadedPhoto: null,
      myPhotoFile: null,
      myPhotoURL:null
    }
  },
  methods:{
    getUserProfile(){
      AXIOS.get('/user/getCurrentUserId')
        .then(response=>{
          this.currentUserProfile.id=response.data.id
          this.myPhotoURL=config.dev.userProfileImgDirPath+'/user_profile_img_'+response.data.id+'.jpg'
          this.currentUserProfile.username=response.data.username
          this.currentUserProfile.email=response.data.email
          this.currentUserProfile.primaryPhone=response.data.primaryPhone
          this.currentUserProfile.memberShip=response.data.memberShip
          AXIOS.get('user/selectAllShippingAddressFromUser?userId='+response.data.id)
            .then(response=>{
              this.currentUserProfile.shippingAddress=response.data
            })

        })
    },
    refreshUserProfile(){
      this.getUserProfile()
    },
    editMyProfile(userUpdate){
      var form_data=new FormData()
      form_data.append('id',this.currentUserProfile.id)
      form_data.append('username',userUpdate.username)
      form_data.append('email',userUpdate.email)
      form_data.append('primaryPhone',userUpdate.primaryPhone)
      AXIOS.put('/user/updateUserProfile',form_data,{})
        .then(response=>{
          this.refreshUserProfile()
          this.toastMessage(response.data.msg)
        })
        .catch(e=>{
          this.toastMessage('Fail to update user')
        })
    },
    addNewShippingAddress(shippingAddressCreate){
      var form_data=new FormData()
      form_data.append('userId',this.currentUserProfile.id)
      form_data.append('name',shippingAddressCreate.name)
      form_data.append('phone',shippingAddressCreate.phone)
      form_data.append('address',shippingAddressCreate.address)
      AXIOS.post('/user/addShippingAddressToUser',form_data,{})
        .then(response=>{
          this.toastMessage(response.data.msg)
          this.refreshUserProfile()
        })
        .catch(e=>{
          this.toastMessage("Fail to add  the shipping address!")
        })
    },
    loadUpdateProfile(){
      this.userProfileUpdate.username=this.currentUserProfile.username
      this.userProfileUpdate.primaryPhone=this.currentUserProfile.primaryPhone
      this.userProfileUpdate.email=this.currentUserProfile.email
    },
    loadUpdateShippingAddress(item){
      this.shippingAddressUpdate.code=item.code
      this.shippingAddressUpdate.address=item.address
      this.shippingAddressUpdate.name=item.name
      this.shippingAddressUpdate.phone=item.phone
    },
    updateShippingAddress(shippingAddressUpdate){
      var form_data=new FormData()
      form_data.append("shippingCode",shippingAddressUpdate.code)
      form_data.append("name",shippingAddressUpdate.name)
      form_data.append("phone",shippingAddressUpdate.phone)
      form_data.append("address",shippingAddressUpdate.address)
      AXIOS.put('/user/updateShippingAddress',form_data,{})
        .then(response=>{
          this.toastMessage(response.data.msg)
          this.refreshUserProfile()
        })
        .catch(e=>{
          this.toastMessage("Fail to update shipping address!")
        })
    },
    deleteShippingAddress(code){
      // var form_data=new FormData()
      // form_data.append('userId',this.currentUserProfile.id)
      // form_data.append('shippingNum',row.code)
      AXIOS.delete('/user/deleteShippingAddressFromUser?userId='+this.currentUserProfile.id+"&shippingCode="+code)
        .then(response=>{
          this.toastMessage(response.data.msg)
          this.refreshUserProfile()
        })
        .catch(e=>{
          this.toastMessage('Fail to delete shipping address!')
        })

    },
    changeMyPhoto(){
      var form_data=new FormData()
      form_data.append('file',this.uploadedPhoto)
      form_data.append('userId',this.currentUserProfile.id)
      AXIOS.post('/user/addImg',form_data,{})
        .then(response=>{
          this.toastMessage(response.data)
        })
        .catch(e=>{
          this.toastMessage('Fail to change the photo!')
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
    this.getUserProfile()
  },
  computed:{
    key(){
      return this.$route.path+Math.random();
    },
    userImgSrc(){
      return config.dev.userProfileImgDirPath+"/user_profile_img_"+this.currentUserProfile.id+".jpg"
    }
  }
}
</script>

<style scoped>
.column-right{

  float: right;
  width: 80%;
}
.column-left{

  float: left;
  width: 20%;
}
.username-under-avator {
  font-size: 32px;
  font-weight: bold;
  text-align: center;
  padding: 10px;
  background: #fff;
  margin: 0 auto;
}
.user-avator{
  text-align: center;
  padding-top: 20px;
}

.change-photo-button{
  padding-top: 10px;
  text-align: center;
}
.change-photo-modal-tips {
  font-size: 20px;
  font-weight: bold;
  padding: 10px;
  margin: 0 auto;
}


</style>
