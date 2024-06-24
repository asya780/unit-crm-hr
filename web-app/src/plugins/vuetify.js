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
          'primary-darken-1': colors.lightBlue.darken1,
          'primary-darken-2': colors.lightBlue.darken2,
          'primary-darken-3': colors.lightBlue.darken3,
          'primary-darken-4': colors.lightBlue.darken4,
          'primary-lighten-1': colors.lightBlue.lighten1,
          'primary-lighten-2': colors.lightBlue.lighten2,
          'primary-lighten-3': colors.lightBlue.lighten3,
          'primary-lighten-4': colors.lightBlue.lighten4,
          'primary-lighten-5': colors.lightBlue.lighten5,
          secondary: colors.teal.base,
          'secondary-darken-1': colors.teal.darken1,
          'secondary-darken-2': colors.teal.darken2,
          'secondary-darken-3': colors.teal.darken3,
          'secondary-darken-4': colors.teal.darken4,
          'secondary-lighten-1': colors.teal.lighten1,
          'secondary-lighten-2': colors.teal.lighten2,
          'secondary-lighten-3': colors.teal.lighten3,
          'secondary-lighten-4': colors.teal.lighten4,
          'secondary-lighten-5': colors.teal.lighten5,
          accent: colors.lime.base,
          'accent-darken-1': colors.lime.darken1,
          'accent-darken-2': colors.lime.darken2,
          'accent-darken-3': colors.lime.darken3,
          'accent-darken-4': colors.lime.darken4,
          'accent-lighten-1': colors.lime.lighten1,
          'accent-lighten-2': colors.lime.lighten2,
          'accent-lighten-3': colors.lime.lighten3,
          'accent-lighten-4': colors.lime.lighten4,
          'accent-lighten-5': colors.lime.lighten5,
          error: colors.red.base,
          'error-darken-1': colors.red.darken1,
          'error-darken-2': colors.red.darken2,
          'error-darken-3': colors.red.darken3,
          'error-darken-4': colors.red.darken4,
          'error-lighten-1': colors.red.lighten1,
          'error-lighten-2': colors.red.lighten2,
          'error-lighten-3': colors.red.lighten3,
          'error-lighten-4': colors.red.lighten4,
          'error-lighten-5': colors.red.lighten5,
          warning: colors.orange.base,
          'warning-darken-1': colors.orange.darken1,
          'warning-darken-2': colors.orange.darken2,
          'warning-darken-3': colors.orange.darken3,
          'warning-darken-4': colors.orange.darken4,
          'warning-lighten-1': colors.orange.lighten1,
          'warning-lighten-2': colors.orange.lighten2,
          'warning-lighten-3': colors.orange.lighten3,
          'warning-lighten-4': colors.orange.lighten4,
          'warning-lighten-5': colors.orange.lighten5,
          info: colors.blue.base,
          'info-darken-1': colors.blue.darken1,
          'info-darken-2': colors.blue.darken2,
          'info-darken-3': colors.blue.darken3,
          'info-darken-4': colors.blue.darken4,
          'info-lighten-1': colors.blue.lighten1,
          'info-lighten-2': colors.blue.lighten2,
          'info-lighten-3': colors.blue.lighten3,
          'info-lighten-4': colors.blue.lighten4,
          'info-lighten-5': colors.blue.lighten5,
          success: colors.green.base,
          'success-darken-1': colors.green.darken1,
          'success-darken-2': colors.green.darken2,
          'success-darken-3': colors.green.darken3,
          'success-darken-4': colors.green.darken4,
          'success-lighten-1': colors.green.lighten1,
          'success-lighten-2': colors.green.lighten2,
          'success-lighten-3': colors.green.lighten3,
          'success-lighten-4': colors.green.lighten4,
          'success-lighten-5': colors.green.lighten5
        }
      }
    }
  },
})
