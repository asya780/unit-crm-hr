<template>
  <v-app>
    <v-navigation-drawer v-model="drawer" permanent class="bg-primary-darken-3">
      <v-img :src="logo" height="128" class="align-center ma-2">
      </v-img>
      <v-divider></v-divider>
      <v-list-item link title="Main" prepend-icon="mdi-home" @click="onNavigationLink('/main')">
      </v-list-item>
      <v-list v-model:opened="openedTables">
        <v-list-group value="Tables" >
        <template v-slot:activator="{ props }">
          <v-list-item
            v-bind="props"
            prepend-icon="mdi-table"
            title="Tables"
          ></v-list-item>
        </template>

        <v-list-item
            v-for="(table, i) in tables"
            :key="i"
            :prepend-icon="table.icon"
            :title="table.name"
            :value="table.name"
            @click="onNavigationLink(table.route)"
          ></v-list-item>
      </v-list-group>
      </v-list>

      <v-list-item link title="About" prepend-icon="mdi-information-outline" @click="onNavigationLink('/about')">
      </v-list-item>
    </v-navigation-drawer>

    <v-app-bar class="bg-primary">
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>

      <v-app-bar-title>UnIT Control Resource Management System</v-app-bar-title>

      <v-spacer></v-spacer>

      <span class="ma-2"> {{ new Date().toLocaleDateString() }} {{ clock.currentTime.value.toLocaleTimeString() }}</span>
      <div class="ma-4">
        <v-icon icon="mdi-account" class="ma-2"> </v-icon>
        <span>HR User</span>
      </div>
    </v-app-bar>
    <v-main>
      <RouterView />
    </v-main>
  </v-app>
</template>

<script setup>
import { ref } from 'vue'
import router from './router';
import logo from '@/assets/logo.png';
import { useCurrentTime } from './composables/useCurrentDate';

const drawer = ref(null);
const openedTables = ref(['Tables'])
const clock = useCurrentTime();

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
        icon: "mdi-account"
      },
      {
        name: "Vacation",
        route: "/vacation",
        icon: "mdi-palm-tree"
      },
      {
        name: "Cabinet",
        route: "/cabinet",
        icon: "mdi-door"
      },
      {
        name: "Department",
        route: "/department",
        icon: "mdi-comment"
      },
      {
        name: "Position",
        route: "/position",
        icon: "mdi-file-document-edit-outline"
      }
    ]
  }),
}
</script>
