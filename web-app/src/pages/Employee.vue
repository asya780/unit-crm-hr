<template>
  <v-card title="Employee" flat color="primary-darken-1">
    <template v-slot:text>
      <v-text-field v-model="search" label="Search" prepend-inner-icon="mdi-magnify" variant="outlined" hide-details
        single-line></v-text-field>
    </template>
    <v-data-table :headers="headers" :items="employees" :items-length="totalItems" :loading="loading" :search="search"
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
                <span class="text-h5">New employee</span>
              </v-card-title>
              <v-card-text>
                <v-form v-model="valid" @submit.prevent>
                  <v-container>
                    <v-row>
                      <v-text-field label="Name" variant="outlined" v-model="newEmployee.name" clearable
                        :rules="stringRules('Name', 50)">
                      </v-text-field>
                    </v-row>
                    <v-row>
                      <v-text-field label="Surname" variant="outlined" v-model="newEmployee.surname" clearable
                        :rules="stringRules('Surname', 50)">
                      </v-text-field>
                    </v-row>
                    <v-row>
                      <v-text-field label="Middle Name" variant="outlined" v-model="newEmployee.middleName" clearable
                        :rules="stringRules('Middle Name', 255)">
                      </v-text-field>
                    </v-row>
                    <v-row>
                      <span class="text-h5">Birth Date</span>
                      <v-date-picker show-adjacent-months v-model="newEmployee.birthDate"
                        color="primary"></v-date-picker>
                    </v-row>
                    <v-row>
                      <v-text-field label="Salary Multiplier" variant="outlined" v-model="newEmployee.multiplier"
                        clearable :rules="multiplierRules">
                      </v-text-field>
                    </v-row>
                    <v-row>
                      <v-checkbox-btn label="Active" variant="outlined" v-model="newEmployee.active"
                        color="primary"></v-checkbox-btn>
                    </v-row>
                    <v-row>
                      <span class="text-h5">Dismissal Date</span>
                      <v-date-picker show-adjacent-months v-model="newEmployee.dismissalDate"
                        color="primary"></v-date-picker>
                    </v-row>
                    <v-row>
                      <v-select label="Position" variant="outlined" v-model="newEmployee.position" :items="positions"
                        return-object :rules="selectionRules('Position')">
                        <template v-slot:item="{ props, item }">
                          <v-list-item v-bind="props" :title="`${item.raw.name} (${item.raw.salary})`">
                          </v-list-item>
                        </template>
                        <template v-slot:selection="{ item }">
                          {{ item.raw.name }} ({{ item.raw.salary }})
                        </template>
                      </v-select>
                    </v-row>
                    <v-row>
                      <v-select label="Department" variant="outlined" v-model="newEmployee.department"
                        :items="departments" return-object :rules="selectionRules('Departament')">
                        <template v-slot:item="{ props, item }">
                          <v-list-item v-bind="props" :title="`${item.raw.name}`">
                          </v-list-item>
                        </template>
                        <template v-slot:selection="{ item }">
                          {{ item.raw.name }}
                        </template>
                      </v-select>
                    </v-row>
                    <v-row>
                      <v-select label="Cabinet" variant="outlined" v-model="newEmployee.cabinet" :items="cabinets"
                        return-object :rules="selectionRules('Cabinet')">
                        <template v-slot:item="{ props, item }">
                          <v-list-item v-bind="props" :title="`${item.raw.name}`">
                          </v-list-item>
                        </template>
                        <template v-slot:selection="{ item }">
                          {{ item.raw.name }}
                        </template>
                      </v-select>
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
          <span class="text-h5">Edit employee</span>
        </v-card-title>
        <v-card-text>
          <v-form v-model="valid" @submit.prevent>
            <v-container>
              <v-row>
                <v-text-field label="Name" variant="outlined" v-model="editEmployee.name" clearable
                  :rules="stringRules('Name', 50)">
                </v-text-field>
              </v-row>
              <v-row>
                <v-text-field label="Surname" variant="outlined" v-model="editEmployee.surname" clearable
                  :rules="stringRules('Surname', 50)">
                </v-text-field>
              </v-row>
              <v-row>
                <v-text-field label="Middle Name" variant="outlined" v-model="editEmployee.middleName" clearable
                  :rules="stringRules('Middle Name', 255)">
                </v-text-field>
              </v-row>
              <v-row>
                <span class="text-h5">Birth Date</span>
                <v-date-picker show-adjacent-months v-model="editEmployee.birthDate" color="primary"></v-date-picker>
              </v-row>
              <v-row>
                <v-text-field label="Salary Multiplier" variant="outlined" v-model="editEmployee.multiplier" clearable>
                </v-text-field>
              </v-row>
              <v-row>
                <v-checkbox-btn label="Active" variant="outlined" v-model="editEmployee.active"
                  color="primary"></v-checkbox-btn>
              </v-row>
              <v-row>
                <span class="text-h5">Dismissal Date</span>
                <v-date-picker show-adjacent-months v-model="editEmployee.dismissalDate"
                  color="primary"></v-date-picker>
              </v-row>
              <v-row>
                <v-select label="Position" variant="outlined" v-model="editEmployee.position" :items="positions"
                  return-object :rules="selectionRules('Position')">
                  <template v-slot:item="{ props, item }">
                    <v-list-item v-bind="props" :title="`${item.raw.name} (${item.raw.salary})`">
                    </v-list-item>
                  </template>
                  <template v-slot:selection="{ item }">
                    {{ item.raw.name }} ({{ item.raw.salary }})
                  </template>
                </v-select>
              </v-row>
              <v-row>
                <v-select label="Department" variant="outlined" v-model="editEmployee.department" :items="departments"
                  return-object :rules="selectionRules('Department')">
                  <template v-slot:item="{ props, item }">
                    <v-list-item v-bind="props" :title="`${item.raw.name}`">
                    </v-list-item>
                  </template>
                  <template v-slot:selection="{ item }">
                    {{ item.raw.name }}
                  </template>
                </v-select>
              </v-row>
              <v-row>
                <v-select label="Cabinet" variant="outlined" v-model="editEmployee.cabinet" :items="cabinets"
                  return-object :rules="selectionRules('Cabinet')">
                  <template v-slot:item="{ props, item }">
                    <v-list-item v-bind="props" :title="`${item.raw.name}`">
                    </v-list-item>
                  </template>
                  <template v-slot:selection="{ item }">
                    {{ item.raw.name }}
                  </template>
                </v-select>
              </v-row>
            </v-container>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue-darken-1" variant="tonal" @click="closeEdit">
            Cancel
          </v-btn>
          <v-btn color="blue-darken-1" variant="tonal" @click="onEdit(editEmployee)" :disabled="!valid">
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
import { Employee } from '@/model';
import { selectionRules, stringRules } from '@/util/validators';
import { inject, onMounted, ref } from 'vue';

const axios = inject('axios');

const search = ref('')
const employees = ref([])
const loading = ref(true)
const totalItems = ref(0)
const snackbarText = ref("")
const snackbar = ref(false)

const newEmployee = ref(new Employee(null, "", "", "", new Date(), 1.0, true, null, new Date(), new Date(), null, null, null, null))
const editEmployee = ref(null)

const addDialog = ref(false)
const editDialog = ref(false)
const headers = ref([
  { title: 'ID', key: 'id', sortable: true },
  { title: 'Name', key: 'name', sortable: true },
  { title: 'Surname', key: 'surname', sortable: true },
  { title: 'Middle Name', key: 'middleName', sortable: true },
  { title: 'Birth Date', key: 'birthDate', sortable: true },
  { title: 'Position', key: 'position.name', sortable: true },
  { title: 'Department', key: 'department.name', sortable: true },
  { title: 'Cabinet', key: 'cabinet.name', sortable: true },
  { title: 'Creation Time', key: 'creationTime', sortable: true },
  { title: 'Last Update Time', key: 'lastUpdateTime', sortable: true },
  { title: 'Actions', key: 'actions' }
])

const departments = ref([])
const cabinets = ref([])
const positions = ref([])

const valid = ref(false)
const middleNameRules = [
  value => {
    if (value)
      return true;

    return 'Middle name is required.'
  },
  value => {
    if (value.length <= 255)
      return true

    return 'Middle name should be less than 255 characters.'
  }
]
const multiplierRules = [
  value => {
    if (value)
      return true

    return 'Salary multiplier is required.'
  },
  value => {
    if (Number(value))
      return true;

    return 'Salary multiplier should be a number.'
  },
  value => {
    var num = Number(value)
    if (num > 0)
      return true;

    return 'Salary multiplier should be > 0.'
  },
]

function updateTable() {
  loading.value = true
  axios.get('/employee')
    .then(function (res) {
      employees.value = res.data
      totalItems.value = employees.value.length
    })
    .catch(function (err) {
      console.log("Error: " + err)
    })
    .finally(() => {
      loading.value = false
    })
}

function onDelete(item) {
  axios.delete(`/employee/${item.id}`)
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

function onEdit(employee) {
  if (!valid.value) return
  var employeeToSend = Object.assign({}, employee)
  employeeToSend.birthDate = newEmployee.value.birthDate.toISOString().slice(0, 10)
  if (employeeToSend.dismissalDate instanceof Date)
    employeeToSend.dismissalDate = newEmployee.value.dismissalDate.toISOString().slice(0, 10)
  employeeToSend.lastUpdateTime = new Date().toISOString().slice(0, 23)
  axios.post(`/employee/${employeeToSend.id}`, employeeToSend)
    .then((res) => {
      snackbarText.value = "Changes saved successfully"
      snackbar.value = true
      console.info(`Item#${employeeToSend.id} edited successfully.`)
    })
    .catch((err) => {
      snackbarText.value = "Error while saving changes"
      snackbar.value = true
      console.error(`Error while deleting ${employeeToSend.id}: ` + err.toString());
    })
    .finally(() => {
      editDialog.value = false
      updateTable()
    })
}

function onAdd() {
  if (!valid.value) return
  var employeeToSend = Object.assign({}, newEmployee.value)
  employeeToSend.id = -1
  employeeToSend.creationTime = new Date().toISOString().slice(0, 23)
  employeeToSend.lastUpdateTime = new Date().toISOString().slice(0, 23)
  employeeToSend.birthDate = newEmployee.value.birthDate.toISOString().slice(0, 10)
  if (employeeToSend.dismissalDate instanceof Date)
    employeeToSend.dismissalDate = newEmployee.value.dismissalDate.toISOString().slice(0, 10)
  axios.post(`/employee`, employeeToSend)
    .then((res) => {
      snackbarText.value = "New employee created"
      snackbar.value = true
      console.info(`Created new employee.`)
      addDialog.value = false
    })
    .catch((err) => {
      snackbarText.value = "Error while creating new employee"
      snackbar.value = true
      console.error(`Error while creating new employee` + err.toString());
    })
    .finally(() => {
      updateTable()
    })
}

function openAddDialog() {
  axios.get("/position")
    .then((res) => {
      positions.value = res.data
    }).catch((err) => {
      snackbar.value = true
      snackbarText.value = "Cannot receive positions."
      console.error("Cannot get positions -> cannot add new employees.")
    }).finally(() => {

    })
  axios.get("/cabinet")
    .then((res) => {
      cabinets.value = res.data
    }).catch((err) => {
      snackbar.value = true
      snackbarText.value = "Cannot receive cabinets."
      console.error("Cannot get cabinets -> cannot add new employees.")
    }).finally(() => {

    })
  axios.get("/department")
    .then((res) => {
      departments.value = res.data
    }).catch((err) => {
      snackbar.value = true
      snackbarText.value = "Cannot receive departments."
      console.error("Cannot get departments -> cannot add new employee.")
    }).finally(() => {

    })
  addDialog.value = true
}

function openEditDialog(item) {
  axios.get("/position")
    .then((res) => {
      positions.value = res.data
    }).catch((err) => {
      snackbar.value = true
      snackbarText.value = "Cannot receive positions."
      console.error("Cannot get positions -> cannot add new employees.")
    }).finally(() => {

    })
  axios.get("/cabinet")
    .then((res) => {
      cabinets.value = res.data
    }).catch((err) => {
      snackbar.value = true
      snackbarText.value = "Cannot receive cabinets."
      console.error("Cannot get cabinets -> cannot add new employees.")
    }).finally(() => {

    })
  axios.get("/department")
    .then((res) => {
      departments.value = res.data
    }).catch((err) => {
      snackbar.value = true
      snackbarText.value = "Cannot receive departments."
      console.error("Cannot get departments -> cannot add new employee.")
    }).finally(() => {

    })
  editEmployee.value = Object.assign({}, item)
  editEmployee.value.birthDate = new Date(editEmployee.value.birthDate)
  if (editEmployee.value.dismissalDate instanceof Date)
    editEmployee.value.dismissalDate = new Date(editEmployee.value.dismissalDate)
  editDialog.value = true
}

function closeAdd() {
  addDialog.value = false
  newEmployee.value = new Employee(null, "", "", "", new Date(), 1.0, true, new Date(), new Date(), new Date(), null, null, null, null)
}

function closeEdit() {
  editDialog.value = false
  editEmployee.value = new Employee(null, "", "", "", new Date(), 1.0, true, new Date(), new Date(), new Date(), null, null, null, null)

}

onMounted(() => {
  updateTable()
})
</script>
