addDialog<template>
  <v-card title="Cabinet" flat color="primary-darken-1">
    <template v-slot:text>
      <v-text-field v-model="search" label="Search" prepend-inner-icon="mdi-magnify" variant="outlined" hide-details
        single-line></v-text-field>
    </template>
    <v-data-table :headers="headers" :items="cabinets" :items-length="totalItems" :loading="loading" :search="search"
      density="compact">
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
                <span class="text-h5">New cabinet</span>
              </v-card-title>
              <v-card-text>
                <v-form v-model="valid" @submit.prevent>
                  <v-container>
                    <v-row>
                      <v-text-field label="Name" variant="outlined" v-model="newCabinet.name" clearable
                        :rules="nameRules">
                      </v-text-field>
                    </v-row>
                    <v-row>
                      <v-text-field label="Floor" variant="outlined" v-model="newCabinet.floor" clearable
                        :rules="floorRules">
                      </v-text-field>
                    </v-row>
                  </v-container>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue-darken-1" variant="tonal" @click="closeAdd">
                  Cancel
                </v-btn>
                <v-btn color="blue-darken-1" variant="tonal" @click="onAdd" :disabled="!valid">
                  Save
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:item.creationTime="{ item }">
        {{ new Date(item.creationTime).toLocaleString() }}
      </template>
      <template v-slot:item.lastUpdateTime="{ item }">
        {{ new Date(item.creationTime).toLocaleString() }}
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
          <span class="text-h5">Edit cabinet</span>
        </v-card-title>
        <v-card-text>
          <v-form v-model="valid" @submit.prevent>
            <v-container>
              <v-row>
                <v-text-field label="Name" variant="outlined" v-model="editCabinet.name" clearable :rules="stringRules('Name', 50)">
                </v-text-field>
              </v-row>
              <v-row>
                <v-text-field label="Floor" variant="outlined" v-model="editCabinet.floor" clearable :rules="floorRules">
                </v-text-field>
              </v-row>
            </v-container>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue-darken-1" variant="tonal" @click="closeEdit">
            Cancel
          </v-btn>
          <v-btn color="blue-darken-1" variant="tonal" @click="onEdit(editCabinet)" :disabled="!valid">
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
import { Cabinet } from '@/model';
import { stringRules } from '@/util/validators';
import { inject, onMounted, ref } from 'vue';

const axios = inject('axios');

const search = ref('')
const cabinets = ref([])
const loading = ref(true)
const totalItems = ref(0)
const snackbarText = ref("")
const snackbar = ref(false)

const newCabinet = ref(new Cabinet(null, "", 1, null, null))
const editCabinet = ref(null)

const addDialog = ref(false)
const editDialog = ref(false)
const headers = ref([
  { title: 'ID', value: 'id', sortable: true },
  { title: 'Name', value: 'name', sortable: true },
  { title: 'Floor', value: 'floor', sortable: true },
  { title: 'Creation Time', value: 'creationTime', sortable: true },
  { title: 'Last Update Time', value: 'lastUpdateTime', sortable: true },
  { title: 'Actions', value: "actions" }
])

const valid = ref(false)
const floorRules = [
  value => {
    if (value)
      return true

    return 'Floor is required'
  },
  value => {
    if (Number(value))
      return true;

    return 'Floor should be a number'
  },
]

function updateTable() {
  loading.value = true
  axios.get('/cabinet')
    .then(function (res) {
      cabinets.value = res.data
      totalItems.value = cabinets.value.length
    })
    .catch(function (err) {
      console.log("Error: " + err)
    })
    .finally(() => {
      loading.value = false
    })
}

function onDelete(item) {
  axios.delete(`/cabinet/${item.id}`)
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

function onEdit(cabinet) {
  if (!valid.value) return
  cabinet.lastUpdateTime = new Date().toISOString().slice(0, 23)
  axios.post(`/cabinet/${cabinet.id}`, cabinet)
    .then((res) => {
      snackbarText.value = "Changes saved successfully"
      snackbar.value = true
      console.info(`Item#${cabinet.id} edited successfully.`)
    })
    .catch((err) => {
      snackbarText.value = "Error while saving changes"
      snackbar.value = true
      console.error(`Error while deleting ${cabinet.id}: ` + err.toString());
    })
    .finally(() => {
      editDialog.value = false
      updateTable()
    })
}

function onAdd() {
  if (!valid.value) return
  newCabinet.value.id = -1
  newCabinet.value.creationTime = new Date().toISOString().slice(0, 23)
  newCabinet.value.lastUpdateTime = new Date().toISOString().slice(0, 23)
  axios.post(`/cabinet`, newCabinet.value)
    .then((res) => {
      snackbarText.value = "New cabinet created"
      snackbar.value = true
      console.info(`Created new cabinet.`)
    })
    .catch((err) => {
      snackbarText.value = "Error while creating new cabinet"
      snackbar.value = true
      console.error(`Error while creating new cabinet` + err.toString());
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
  editCabinet.value = Object.assign({}, item)
  editDialog.value = true
}

function closeAdd() {
  addDialog.value = false
  newCabinet.value = new Cabinet(null, null, null, null, null)
}

function closeEdit() {
  editDialog.value = false
  editCabinet.value = new Cabinet(null, null, null, null, null)
}

onMounted(() => {
  updateTable()
})
</script>
