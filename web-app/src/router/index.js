
/**
 * router/index.ts
 *
 * Automatic routes for `./src/pages/*.vue`
 */

// Composables
import { createRouter, createWebHistory } from 'vue-router/auto'
import Main from '@/pages/Main.vue'
import Position from '@/pages/Position.vue'
import Employee from '@/pages/Employee.vue'
import Vacation from '@/pages/Vacation.vue'
import Cabinet from '@/pages/Cabinet.vue'
import About from '@/pages/About.vue'
import Department from '@/pages/Department.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: "main",
      component: Main,
      meta: {
        title: "Main Page",
      }
    },
    {
      path: '/about',
      name: "about",
      component: About,
      meta: {
        title: "About Page",
      }
    },
    {
      path: '/position',
      name: "position",
      component: Position,
      meta: {
        title: "Position",
      }
    },
    {
      path: '/employee',
      name: "employee",
      component: Employee,
      meta: {
        title: "Employee",
      }
    },
    {
      path: '/vacation',
      name: "vacation",
      component: Vacation,
      meta: {
        title: "Vacation",
      }
    },
    {
      path: '/cabinet',
      name: "cabinet",
      component: Cabinet,
      meta: {
        title: "Cabinet",
      }
    },
    {
      path: '/department',
      name: "department",
      component: Department,
      meta: {
        title: "Department",
      }
    },
  ]
})

export default router
