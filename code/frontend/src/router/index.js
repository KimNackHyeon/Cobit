import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Mypage from '../views/Mypage.vue'
import CodeBlock from '../views/CodeBlock.vue'
import GameMap from '../views/GameMap.vue'
import Apitest from '../views/Apitest.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/mypage',
    name: 'Mypage',
    component: Mypage
  },
  {
    path: '/codeblock',
    name: 'CodeBlock',
    component: CodeBlock
  },
  {
    path: '/gamemap',
    name: 'GameMap',
    component: GameMap
  },
  {
    path: '/apitest',
    name: 'Apitest',
    component: Apitest
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
