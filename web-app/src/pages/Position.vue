<template>
  <v-card title="Position" flat>
    <template v-slot:text>
      <v-text-field v-model="search" label="Search" prepend-inner-icon="mdi-magnify" variant="outlined" hide-details
        single-line></v-text-field>
    </template>
    <v-data-table :headers="headers" :items="positions" :items-length="totalItems" :loading="loading" :search="search">
      <template v-slot:top>
        <v-toolbar>
          <v-spacer></v-spacer>
          <v-dialog v-model="addDialog" max-width="500">
            <template v-slot:activator="{ props }">
              <v-btn icon="mdi-plus" variant="text" color="green" @click="openAddDialog">
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="text-h5">New position</span>
              </v-card-title>
              <v-card-text>
                <v-container>
                  <v-row>
                    <v-text-field label="Name" variant="outlined" v-model="newPosition.name" clearable>
                    </v-text-field>
                  </v-row>
                  <v-row>
                    <v-text-field label="Salary" variant="outlined" v-model="newPosition  .salary" clearable>
                    </v-text-field>
                  </v-row>
                </v-container>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue-darken-1" variant="text" @click="closeAdd">
                  Cancel
                </v-btn>
                <v-btn color="blue-darken-1" variant="text" @click="onAdd">
                  Save
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-btn icon="mdi-delete" variant="text" color="red" @click="onDelete(item)">
        </v-btn>
        <v-btn icon="mdi-pencil" variant="text" color="blue" @click="openEditDialog(item)">
        </v-btn>
      </template>
    </v-data-table>
    <v-dialog v-model="editDialog" max-width="500">
      <v-card>
        <v-card-title>
          <span class="text-h5">Edit position</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-text-field label="Name" variant="outlined" v-model="editPosition.name" clearable>
              </v-text-field>
            </v-row>
            <v-row>
              <v-text-field label="Salary" variant="outlined" v-model="editPosition.salary" clearable>
              </v-text-field>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue-darken-1" variant="text" @click="closeEdit">
            Cancel
          </v-btn>
          <v-btn color="blue-darken-1" variant="text" @click="onEdit(editPosition)">
            Save
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-snackbar v-model="snackbar" timeout="3000" color="primary" rounded="pill">
      {{ snackbarText }}
      <template v-slot:actions>
        <v-btn color="white" variant="text" @click="snackbar = false">
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </v-card>
</template>

<script setup>
import { Position } from '@/model';
import { inject, onMounted, ref } from 'vue';

const axios = inject('axios');

const search = ref('')
const positions = ref([])
const loading = ref(true)
const totalItems = ref(0)
const snackbarText = ref("")
const snackbar = ref(false)

const newPosition = ref(new Position(null, "", 40000, null, null))
const editPosition = ref(null)

const addDialog = ref(false)
const editDialog = ref(false)
const headers = ref([
  { title: 'ID', value: 'id', sortable: true },
  { title: 'Name', value: 'name', sortable: true },
  { title: 'Salary', value: 'salary', sortable: true },
  { title: 'Creation Time', value: 'creationTime', sortable: true },
  { title: 'Last Update Time', value: 'lastUpdateTime', sortable: true },
  { title: 'Actions', value: "actions" }
])

function updateTable() {
  loading.value = true
  axios.get('/position')
    .then(function (res) {
      positions.value = res.data
      totalItems.value = positions.value.length
    })
    .catch(function (err) {
      console.log("Error: " + err)
    })
    .finally(() => {
      loading.value = false
    })
}

function onDelete(item) {
  axios.delete(`/position/${item.id}`)
    .then((res) => {
      snackbarText.value = "Deleted successfully"
      snackbar.value = true
      console.info(`Item#${item.id} deleted successfully.`)
    })
    .catch((err) => {
      snackbarText.value = "Error while deleting"
      snackbar.value = true
      console.error(`Error while deleting ${item.id}: ` + err.toString());
    })
    .finally(() => {
      updateTable()
    })
}

function onEdit(position) {
  position.lastUpdateTime = new Date().toISOString().slice(0, 23)
  axios.post(`/position/${position.id}`, position)
    .then((res) => {
      snackbarText.value = "Changes saved successfully"
      snackbar.value = true
      console.info(`Item#${position.id} deleted successfully.`)
    })
    .catch((err) => {
      snackbarText.value = "Error while saving changes"
      snackbar.value = true
      console.error(`Error while deleting ${position.id}: ` + err.toString());
    })
    .finally(() => {
      editDialog.value = false
      updateTable()
    })
}

function onAdd() {
  newPosition.value.id = -1
  newPosition.value.creationTime = new Date().toISOString().slice(0, 23)
  newPosition.value.lastUpdateTime = new Date().toISOString().slice(0, 23)
  axios.post(`/position`, newPosition.value)
    .then((res) => {
      snackbarText.value = "New position created"
      snackbar.value = true
      console.info(`Created new position.`)
    })
    .catch((err) => {
      snackbarText.value = "Error while creating new position"
      snackbar.value = true
      console.error(`Error while creating new position` + err.toString());
    })
    .finally(() => {
      addDialog.value = false
      updateTable()
    })
}

function openAddDialog() {
  addDialog.value = true
}

function openEditDialog(item) {
  editPosition.value = Object.assign({}, item)
  editDialog.value = true
}

function closeAdd() {
  addDialog.value = false
  newPosition.value = new Position(null, null, null, null, null)
}

function closeEdit() {
  editDialog.value = false
  editPosition.value = new Position(null, null, null, null, null)
}

onMounted(() => {
  updateTable()
})
</script>
