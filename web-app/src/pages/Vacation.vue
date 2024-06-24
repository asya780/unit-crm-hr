<template>
  <v-card title="Vacation" flat color="primary-darken-1">
    <template v-slot:text>
      <v-text-field v-model="search" label="Search" prepend-inner-icon="mdi-magnify" variant="outlined" hide-details
        single-line></v-text-field>
    </template>
    <v-data-table :headers="headers" :items="vacations" :items-length="totalItems" :loading="loading" :search="search" density="compact">
      <template v-slot:top>
        <v-toolbar>
          <v-spacer></v-spacer>
          <v-dialog v-model="addDialog" max-width="1000">
            <template v-slot:activator="{ props }">
              <v-btn icon="mdi-plus" variant="text" color="green" @click="openAddDialog">
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="text-h5">New vacation</span>
              </v-card-title>
              <v-card-text>
                <v-container>
                  <v-row>
                    <v-select label="Employee" variant="outlined" v-model="newVacation.employee" :items="employees"
                      return-object>
                      <template v-slot:item="{ props, item }">
                        <v-list-item v-bind="props"
                          :title="`${item.raw.surname} ${item.raw.name} ${item.raw.middleName}`">
                        </v-list-item>
                      </template>
                      <template v-slot:selection="{ item }">
                        {{ item.raw.surname }} {{ item.raw.name }} {{ item.raw.middleName }}
                      </template>
                    </v-select>
                  </v-row>
                  <v-row>
                    <v-col>
                      <span class="text-h5">Start</span>
                      <v-date-picker show-adjacent-months v-model="newVacation.start" color="primary"></v-date-picker>
                    </v-col>
                    <v-col>
                      <span class="text-h5">End</span>
                      <v-date-picker show-adjacent-months v-model="newVacation.end" color="primary"></v-date-picker>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-checkbox-btn label="Approved" v-model="newVacation.approved" color="primary">
                    </v-checkbox-btn>
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
      <template v-slot:item.creationTime="{ item }">
        {{  new Date(item.creationTime).toLocaleString() }}
      </template>
      <template v-slot:item.lastUpdateTime="{ item }">
        {{  new Date(item.creationTime).toLocaleString() }}
      </template>
      <template v-slot:item.actions="{ item }">
        <v-btn icon="mdi-delete" variant="text" color="red" @click="onDelete(item)">
        </v-btn>
        <v-btn icon="mdi-pencil" variant="text" color="blue" @click="openEditDialog(item)">
        </v-btn>
      </template>
    </v-data-table>
    <v-dialog v-model="editDialog" max-width="1000">
      <v-card>
        <v-card-title>
          <span class="text-h5">Edit vacation</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-select label="Employee" variant="outlined" v-model="editVacation.employee" :items="employees"
                return-object>
                <template v-slot:item="{ props, item }">
                  <v-list-item v-bind="props" :title="`${item.raw.surname} ${item.raw.name} ${item.raw.middleName}`">
                  </v-list-item>
                </template>
                <template v-slot:selection="{ item }">
                  {{ item.raw.surname }} {{ item.raw.name }} {{ item.raw.middleName }}
                </template>
              </v-select>
            </v-row>
            <v-row>
              <v-col>
                <span class="text-h5">Start</span>
                <v-date-picker show-adjacent-months v-model="editVacation.start" color="primary" @update:model-value=""></v-date-picker>
              </v-col>
              <v-col>
                <span class="text-h5">End</span>
                <v-date-picker show-adjacent-months v-model="editVacation.end" color="primary"></v-date-picker>
              </v-col>
            </v-row>
            <v-row>
              <v-checkbox-btn label="Approved" v-model="editVacation.approved" color="primary">
              </v-checkbox-btn>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue-darken-1" variant="text" @click="closeEdit">
            Cancel
          </v-btn>
          <v-btn color="blue-darken-1" variant="text" @click="onEdit(editVacation)">
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
import { Vacation } from '@/model';
import { inject, onMounted, ref } from 'vue';

const axios = inject('axios');

const search = ref('')
const vacations = ref([])
const loading = ref(true)
const totalItems = ref(0)
const snackbarText = ref("")
const snackbar = ref(false)

const newVacation = ref(new Vacation(null, new Date(), new Date(), false, null, null, null))
const editVacation = ref(null)

const addDialog = ref(false)
const editDialog = ref(false)
const headers = ref([
  { title: 'ID', key: 'id', sortable: true },
  { title: 'Employee', key: 'employee.name', sortable: true },
  { title: 'Start', key: 'start', sortable: true },
  { title: 'End', key: 'end', sortable: true },
  { title: 'Approved', key: 'approved', sortable: true },
  { title: 'Creation Time', key: 'creationTime', sortable: true },
  { title: 'Last Update Time', key: 'lastUpdateTime', sortable: true },
  { title: 'Actions', key: 'actions', sortable: true },
])


const employees = ref([])
function updateTable() {
  loading.value = true
  axios.get('/vacation')
    .then(function (res) {
      vacations.value = res.data
      totalItems.value = vacations.value.length
    })
    .catch(function (err) {
      console.log("Error: " + err)
    })
    .finally(() => {
      loading.value = false
    })
}

function onDelete(item) {
  axios.delete(`/vacation/${item.id}`)
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

function onEdit(vacation) {
  var vacationToSend = Object.assign({}, vacation)
  vacationToSend.start = vacation.start.toISOString().substring(0, 10)
  vacationToSend.end = vacation.end.toISOString().substring(0, 10)
  vacationToSend.lastUpdateTime = new Date().toISOString().slice(0, 23)
  axios.post(`/vacation/${vacationToSend.id}`, vacationToSend)
    .then((res) => {
      snackbarText.value = "Changes saved successfully"
      snackbar.value = true
      console.info(`Item#${vacationToSend.id} edited successfully.`)
    })
    .catch((err) => {
      snackbarText.value = "Error while saving changes"
      snackbar.value = true
      console.error(`Error while deleting ${vacationToSend.id}: ` + err.toString());
    })
    .finally(() => {
      editDialog.value = false
      updateTable()
    })
}

function onAdd() {
  var vacationToSend = Object.assign({}, newVacation.value)
  vacationToSend.id = -1
  vacationToSend.creationTime = new Date().toISOString().slice(0, 23)
  vacationToSend.lastUpdateTime = new Date().toISOString().slice(0, 23)
  vacationToSend.start = newVacation.value.start.toISOString().substring(0, 10)
  vacationToSend.end = newVacation.value.end.toISOString().substring(0, 10)

  axios.post(`/vacation`, vacationToSend)
    .then((res) => {
      snackbarText.value = "New vacation created"
      snackbar.value = true
      console.info(`Created new vacation.`)
      addDialog.value = false
    })
    .catch((err) => {
      snackbarText.value = "Error while creating new vacation"
      snackbar.value = true
      console.error(`Error while creating new vacation` + err.toString());
    })
    .finally(() => {
      updateTable()
    })


}

function openAddDialog() {
  axios.get("/employee")
    .then((res) => {
      addDialog.value = true
      employees.value = res.data
    }).catch((err) => {
      snackbar.value = true
      snackbarText.value = "Cannot receive employee."
      console.error("Cannot get employees -> cannot add new vacation.")
    }).finally(() => {

    })
}

function openEditDialog(item) {
  axios.get("/employee")
    .then((res) => {
      editVacation.value = Object.assign({}, item)
      editVacation.value.start = new Date(editVacation.value.start)
      editVacation.value.end = new Date(editVacation.value.end)
      employees.value = res.data
      editDialog.value = true
    }).catch((err) => {
      snackbar.value = true
      snackbarText.value = "Cannot receive employee."
      console.error("Cannot get employees -> cannot add new vacation.")
    }).finally(() => {

    })

}

function closeAdd() {
  addDialog.value = false
  newVacation.value = new Vacation(null, null, null, null, null, null, null)
}

function closeEdit() {
  editDialog.value = false
  editVacation.value = new Vacation(null, null, null, null,null, null, null)
}

onMounted(() => {
  updateTable()

})
</script>
