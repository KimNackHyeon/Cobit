import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Mypage from '../views/Mypage.vue'
import CodeBlock from '../views/CodeBlock.vue'
import ChangeCharacter from "../views/ChangeCharacter.vue"

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
    path: '/changecharacter',
    name: 'ChangeCharacter',
    component: ChangeCharacter
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
