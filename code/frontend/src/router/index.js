import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Mypage from '../views/Mypage.vue'
import CodeBlock from '../views/CodeBlock.vue'
import ChangeCharacter from "../views/ChangeCharacter.vue"
import GameMap from '../views/GameMap.vue'
import Apitest from '../views/Apitest.vue'
import Apitest2 from '../views/Apitest2.vue'
import Speech from '../views/Speech.vue'
import VRTest from '../views/vrtest.vue'
import Practice from '../views/Practice.vue'
import Main from '../views/Main.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Main',
    component: Main
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    // beforeEnter(to, from, next) {
    //   if (Vue.$cookies.isKey('access-token')) {
    //     next()
    //   }
    //   else {
    //     next('/')
    //   }
    // }
  },
  {
    path: '/mypage',
    name: 'Mypage',
    component: Mypage,
    // beforeEnter(to, from, next) {
    //   if (Vue.$cookies.isKey('access-token')) {
    //     next()
    //   }
    //   else {
    //     next('/')
    //   }
    // }
  },
  {
    path: '/speech',
    name: 'Speech',
    component: Speech,
    // beforeEnter(to, from, next) {
    //   if (Vue.$cookies.isKey('access-token')) {
    //     next()
    //   }
    //   else {
    //     next('/')
    //   }
    // }
  },
  {
    path: '/codeblock',
    name: 'CodeBlock',
    component: CodeBlock,
    // beforeEnter(to, from, next) {
    //   if (Vue.$cookies.isKey('access-token')) {
    //     next()
    //   }
    //   else {
    //     next('/')
    //   }
    // }
  },
  {
    path: '/changecharacter',
    name: 'ChangeCharacter',
    component: ChangeCharacter,
    // beforeEnter(to, from, next) {
    //   if (Vue.$cookies.isKey('access-token')) {
    //     next()
    //   }
    //   else {
    //     next('/')
    //   }
    // }
  },
  {
    path: '/gamemap',
    name: 'GameMap',
    component: GameMap,
    // beforeEnter(to, from, next) {
    //   if (Vue.$cookies.isKey('access-token')) {
    //     next()
    //   }
    //   else {
    //     next('/')
    //   }
    // }
  },
  {
    path: '/apitest',
    name: 'Apitest',
    component: Apitest,
    // beforeEnter(to, from, next) {
    //   if (Vue.$cookies.isKey('access-token')) {
    //     next()
    //   }
    //   else {
    //     next('/')
    //   }
    // }
  },
  {
    path: '/vrtest',
    name: 'VRTest',
    component: VRTest,
    // beforeEnter(to, from, next) {
    //   if (Vue.$cookies.isKey('access-token')) {
    //     next()
    //   }
    //   else {
    //     next('/')
    //   }
    // }
  },
  {
    path: '/practice',
    name: 'Practice',
    component: Practice,
    // beforeEnter(to, from, next) {
    //   if (Vue.$cookies.isKey('access-token')) {
    //     next()
    //   }
    //   else {
    //     next('/')
    //   }
    // }
  },
  {
    path: '/apitest2',
    name: 'Apitest2',
    component: Apitest2,
    // beforeEnter(to, from, next) {
    //   if (Vue.$cookies.isKey('access-token')) {
    //     next()
    //   }
    //   else {
    //     next('/')
    //   }
    // }
  },
  {
    path: '*',
    redirect: '/'
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
