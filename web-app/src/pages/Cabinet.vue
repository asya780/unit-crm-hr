<template>
  <v-container>
    <h1>Cabinet</h1>
    <v-data-table
      :headers="headers"
      :items="cabinets"
      :items-length="totalItems"
      :loading="loading"
    >
    </v-data-table>
  </v-container>
</template>

<script setup>
import { inject, onMounted, ref } from 'vue';

const axios = inject('axios');

const cabinets = ref([])
const loading = ref(true)
const totalItems = ref(0)
const headers = ref([
    { title: 'ID', key: 'id', sortable: true },
    { title: 'Name', key: 'name', sortable: true },
    { title: 'Floor', key: 'floor', sortable: true },
    { title: 'Creation Time', key: 'creationTime', sortable: true },
    { title: 'Last Update Time', key: 'lastUpdateTime', sortable: true }
])

onMounted(() => {
  axios.get('/cabinet')
    .then(function (res) {
      cabinets.value = res.data
      totalItems.value = cabinets.value.length
    })
    .catch(function (err) {
      console.log("Error: " + err)
    })
    .finally(()=> {
      loading.value = false
    })
})
</script>
