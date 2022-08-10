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
import otherUserProfile from "../views/user/OtherUserProfile";
import Homepage from "../views/Homepage";
import signUp from "../views/SignUp";
import itemDetail from "../views/user/ItemDetail";
import ItemStatusHint from "../views/hint/ItemStatusHint";
import MemberShipHint from "../views/hint/MemberShipHint";
import orderStatusHint from "../views/hint/OrderStatusHint";
import testImg from "../views/test/testImg";
import busyTigerChoice from "../views/user/BusyTigerChoice";
import Collection from "../views/Collection";
import busyTigerChoiceItems from "../views/user/BusyTigerChoiceItems";

Vue.use(Router)

export default new Router({
  mode: "history",
  routes: [
    {
      path:'/',
      component: Homepage
    },
    {
      path:'/login',
      component: Login
    },
    {
      path:'/signUp',
      component:signUp
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
      path:'/collection',
      component: Collection
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
      path:'/recommend/*',
      component:Recommend
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
    },
    {
      path:'/otherUserProfile/*',
      component: otherUserProfile
    },
    {
      path:'/otherUserProfile',
      component: otherUserProfile
    },
    {
      path:'/itemDetail/*',
      component: itemDetail
    },
    {
      path:'/itemDetail',
      component: itemDetail
    },
    {
      path:'/hint/itemStatus',
      component: ItemStatusHint
    },
    {
      path:'/hint/memberShip',
      component: MemberShipHint
    },
    {
      path:'/hint/orderStatus',
      component: orderStatusHint
    },
    {
      path:'/test/testImg',
      component:testImg
    },
    {
      path:'/busyTigerChoice',
      component: busyTigerChoice
    },
    {
      path:'/busyTigerChoice/*',
      component: busyTigerChoiceItems
    }
  ]
})
