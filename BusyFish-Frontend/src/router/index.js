import Vue from 'vue'
import Router from 'vue-router'
import Item from "../views/Item"
import User from "../views/User"
import Order from "../views/Order"
import Overview from "../views/Overview";
import Welcome from "../views/Welcome";
import Login from "../views/Login";
import ByCategory from "../views/user/ByCategory";
import Recommend from "../views/user/Recommend";
import myOrder from "../views/user/MyOrder";
import mySell from "../views/user/MySell";
import UserProfile from "../views/user/UserProfile";
import myItem from "../views/user/MyItem";

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
    },
    {
      path:'/byCategory',
      component:ByCategory
    },
    {
      path:'/recommend',
      component:Recommend
    },
    {
      path:'/myOrder',
      component: myOrder
    },
    {
      path:'/mySell',
      component:mySell
    },
    {
      path:'/userProfile',
      component: UserProfile
    },
    {
      path:'/myItem',
      component: myItem
    }

  ]
})
