import Vue from 'vue'
import Router from 'vue-router'
import Page from '../views/SamplePage.vue'
import DashBoard from "../views/DashBoard";
import Test from "../views/Test"
import Page1 from "../views/Page1"
import Item from "../views/Item"
import User from "../views/User"
import Order from "../views/Order"
import Overview from "../views/Overview";
import Welcome from "../views/Welcome";

Vue.use(Router)

export default new Router({
  mode: "history",
  routes: [
    {
      path: '/',
      name: 'welcome',
      component: Welcome
    },
    {
      path:'/page',
      name: 'HelloPage',
      component: Page
    },
    {
      path:'/dashboard',
      name: 'Dashboard',
      component: DashBoard
    },
    {
      path:'/test',
      name: 'Test',
      component: Test
    },
    {
      path:'/page1',
      component: Page1
    },
    {
      path:'/page1/:name',
      component: Page1
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
