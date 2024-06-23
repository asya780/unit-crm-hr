<template>
  <v-app>
    <v-navigation-drawer v-model="drawer" permanent class="bd-deep-blue">
      <v-img :width="300" aspect-ratio="16/9" cover src="https://cdn.vuetifyjs.com/images/parallax/material.jpg">
      </v-img>
      <v-divider></v-divider>
      <v-list-item link title="Main" @click="onNavigationLink('/main')">
      </v-list-item>
      <v-list-group value="Tables">
        <template v-slot:activator="{ props }">
          <v-list-item
            v-bind="props"
            prepend-icon="mdi-table"
            title="Tables"
          ></v-list-item>
          <v-list-item
            v-for="(table, i) in tables"
            :key="i"
            :prepend-icon="table.icon"
            :title="table.name"
            :value="table.name"
            @click="onNavigationLink(table.route)"
          ></v-list-item>
        </template>

      </v-list-group>
      <v-list-item link title="About" @click="onNavigationLink('/about')">
      </v-list-item>
    </v-navigation-drawer>

    <v-app-bar>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>

      <v-app-bar-title>Unit</v-app-bar-title>
    </v-app-bar>
    <v-main>
      <RouterView />
    </v-main>
  </v-app>
</template>

<script setup>
import { ref } from 'vue'
import router from './router';

const drawer = ref(null);

function onNavigationLink(route) {
  router.push(route)
}

</script>


<script>
export default {
  data: () => ({
    drawer: null,
    tables: [
      {
        name: "Employee",
        route: "/employee",
        icon: ""
      },
      {
        name: "Vacation",
        route: "/vacation",
        icon: ""
      },
      {
        name: "Cabinet",
        route: "/cabinet",
        icon: ""
      },
      {
        name: "Department",
        route: "/department",
        icon: ""
      },
      {
        name: "Position",
        route: "/position",
        icon: ""
      }
    ]
  }),
}
</script>
