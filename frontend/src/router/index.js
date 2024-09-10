import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import PalabrasList from '@/views/PalabrasList.vue'
import PalabrasForm from '@/views/PalabrasForm.vue'
import IdiomasList from '@/views/IdiomasList.vue'
import IdiomasForm from '@/views/IdiomasForm.vue'

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
      path: '/palabras-form/:id?',
      name: 'PalabrasForm',
      component: PalabrasForm,
      props: true  
    },

    {
      path: '/idiomas',
      name: 'IdiomasList',
      component:IdiomasList
     
    },

    {
      path: '/idiomas-form/:id?',
      name: 'IdiomasForm',
      component: IdiomasForm,
      props: true  
    },
  ]
})

export default router
