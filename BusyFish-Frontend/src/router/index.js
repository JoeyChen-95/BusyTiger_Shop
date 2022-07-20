import Vue from 'vue'
import Router from 'vue-router'
import Item from "../views/Item"
import User from "../views/User"
import Order from "../views/Order"
import Overview from "../views/Overview";
import Welcome from "../views/Welcome";
import Login from "../views/Login";

Vue.use(Router)

export default new Router({
  mode: "history",
  routes: [
    {
      path:'/',
      component: Login
    },
    {
      path: '/adminWelcome',
      name: 'adminWelcome',
      component: Welcome
    },
    {
      path:'/item',
      component: Item
    },
    {
      path:'/user',
      component: User
    },
    {
      path:'/order',
      component: Order
    },
    {
      path:'/overview',
      component: Overview
    }
  ]
})
