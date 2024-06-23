<template>
  <v-container>
    <h1>Vacation</h1>
    <v-data-table
      :headers="headers"
      :items="vacations"
      :items-length="totalItems"
      :loading="loading"
    >
    </v-data-table>
  </v-container>
</template>

<script setup>
import { inject, onMounted, ref } from 'vue';

const axios = inject('axios');

const vacations = ref([])
const loading = ref(true)
const totalItems = ref(0)
const headers = ref([
    { title: 'ID', key: 'id', sortable: true },
    { title: 'Start', key: 'start', sortable: true },
    { title: 'End', key: 'end', sortable: true },
    { title: 'Approved', key: 'approved', sortable: true },
    { title: 'Creation Time', key: 'creationTime', sortable: true },
    { title: 'Last Update Time', key: 'lastUpdateTime', sortable: true },
    { title: 'Employee', key: 'employee.name', sortable: true }
])

onMounted(() => {
  axios.get('/vacation')
    .then(function (res) {
      vacations.value = res.data
      totalItems.value = vacations.value.length
    })
    .catch(function (err) {
      console.log("Error: " + err)
    })
    .finally(()=> {
      loading.value = false
    })
})
</script>
