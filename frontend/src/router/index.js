import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import PalabrasList from '@/views/PalabrasList.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/palabras',
      name: 'PalabrasList',
      component:PalabrasList
     
    }
  ]
})

export default router
