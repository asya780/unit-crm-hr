/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

// Plugins
import { registerPlugins } from '@/plugins'

// Components
import App from './App.vue'

// Composables
import { createApp } from 'vue'
import axiosInstance from './plugins/axios'

const app = createApp(App)

app.provide('axios', axiosInstance)

registerPlugins(app)

app.mount('#app')
