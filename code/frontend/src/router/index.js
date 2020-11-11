import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Mypage from '../views/Mypage.vue'
import CodeBlock from '../views/CodeBlock.vue'
import ChangeCharacter from "../views/ChangeCharacter.vue"
import GameMap from '../views/GameMap.vue'
import Apitest from '../views/Apitest.vue'
import Test4 from '../views/Test4.vue'
import Speech from '../views/Speech.vue'
import VRTest from '../views/vrtest.vue'
import PracticeMode from '../views/PracticeMode.vue'
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
    component: Home
  },
  {
    path: '/mypage',
    name: 'Mypage',
    component: Mypage
  },
  {
    path: '/speech',
    name: 'Speech',
    component: Speech
  },
  {
    path: '/codeblock',
    name: 'CodeBlock',
    component: CodeBlock
  },
  {
    path: '/changecharacter',
    name: 'ChangeCharacter',
    component: ChangeCharacter
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
  {
    path: '/vrtest',
    name: 'VRTest',
    component: VRTest
  },
  {
    path: '/test4',
    name: 'Test4',
    component: Test4
  },
  {
    path: '/practice',
    name: 'PracticeMode',
    component: PracticeMode
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
