<template>
  <v-container>
    <h1>Employee</h1>
    <v-data-table
      :headers="headers"
      :items="employees"
      :items-length="totalItems"
      :loading="loading"
    >
    </v-data-table>
  </v-container>
</template>

<script setup>
import { inject, onMounted, ref } from 'vue';

const axios = inject('axios');

const employees = ref([])
const loading = ref(true)
const totalItems = ref(0)
const headers = ref([
    { title: 'ID', key: 'id', sortable: true },
    { title: 'Name', key: 'name', sortable: true },
    { title: 'Surname', key: 'surname', sortable: true },
    { title: 'Middle Name', key: 'middleName', sortable: true },
    { title: 'Birth Date', key: 'birthDate', sortable: true },
    { title: 'Multiplier', key: 'multiplier', sortable: true },
    { title: 'Active', key: 'active', sortable: true },
    { title: 'Dismissal Date', key: 'dismissalDate', sortable: true },
    { title: 'Creation Time', key: 'creationTime', sortable: true },
    { title: 'Last Update Time', key: 'lastUpdateTime', sortable: true },
    { title: 'Position', key: 'position.name', sortable: true },
    { title: 'Personal Information', key: 'personalInformation.personalNumber', sortable: true },
    { title: 'Department', key: 'department.name', sortable: true },
    { title: 'Cabinet', key: 'cabinet.name', sortable: true }
])

onMounted(() => {
  axios.get('/employee')
    .then(function (res) {
      employees.value = res.data
      totalItems.value = employees.value.length
    })
    .catch(function (err) {
      console.log("Error: " + err)
    })
    .finally(()=> {
      loading.value = false
    })
})
</script>
