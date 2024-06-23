/**
 * plugins/vuetify.js
 *
 * Framework documentation: https://vuetifyjs.com`
 */

// Styles
import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'
import colors from 'vuetify/util/colors'

// Composables
import { createVuetify } from 'vuetify'

// https://vuetifyjs.com/en/introduction/why-vuetify/#feature-guides
export default createVuetify({
  theme: {
    defaultTheme: 'light',
    themes: {
      light: {
        dark: false,
        colors: {
          primary: colors.lightBlue.base,
          secondary: colors.teal.base,
          accent: colors.lime.base,
          error: colors.red.base,
          warning: colors.orange.base,
          info: colors.blue.base,
          success: colors.green.base
        }
      }
    }
  },
})
