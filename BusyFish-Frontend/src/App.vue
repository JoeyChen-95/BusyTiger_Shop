<template>
  <div class="app">
<!--    <div class="slide">-->
<!--      <ul>-->
<!--        <li><router-link to="/page1/freddy">freddy</router-link></li>-->
<!--        <li><router-link to="/page1/nick">nick</router-link></li>-->
<!--        <li><router-link to="/page1/mike">mike</router-link></li>-->
<!--      </ul>-->
<!--    </div>-->
<!--    <div class="content">-->
<!--      <router-view :key="key"> </router-view>-->
<!--    </div>-->
    <div>
      <b-navbar toggleable="lg" type="dark" variant="info">
        <b-navbar-brand href="#">BusyFish-Management</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item><router-link to="/page1/freddy">Overview</router-link></b-nav-item>
            <b-nav-item><router-link to="/item">Item</router-link></b-nav-item>
            <b-nav-item><router-link to="/user">User</router-link></b-nav-item>
            <b-nav-item><router-link to="/order">Order</router-link></b-nav-item>
            <b-nav-item href="#">More...</b-nav-item>
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
                <em>Administrator</em>
              </template>
              <b-dropdown-item href="#">Profile</b-dropdown-item>
              <b-dropdown-item href="#">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>
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
export default {
  name: 'app',
  data(){
    return{

    }
  },
  computed:{
    key(){
      return this.$route.path+Math.random();
    }
  }
}
</script>

<style>

</style>
