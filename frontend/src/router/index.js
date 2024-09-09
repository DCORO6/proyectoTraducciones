import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import PalabrasList from '@/views/PalabrasList.vue'
import PalabrasForm from '@/views/PalabrasForm.vue'

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
     
    },
    {
      path: '/palabras-form',
      name: 'PalabrasForm',
      component:PalabrasForm
     
    },
    {
      path: '/palabras-form/:id',
      name: 'PalabrasEditForm',
      component:PalabrasForm
     
    }
  ]
})

export default router
