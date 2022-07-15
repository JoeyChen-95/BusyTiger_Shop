import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Page from '../views/SamplePage.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path:'/page',
      name: 'HelloPage',
      component: Page
    }
  ]
})
