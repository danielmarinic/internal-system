<template>
  <div class="form-elements">
    <div class="row">
      <div class="flex xs12">
        <va-card title="Registrácia uživateľa">
          <form>
            <div class="row">
              <div class="flex md6 xs12">
                <va-input
                  v-model="form.firstname"
                  label="Meno"
                />
              </div>
              <div class="flex md6 xs12">
                <va-input
                  v-model="form.surname"
                  label="Priezvisko"
                >
                </va-input>
              </div>
              <div class="flex md6 xs12">
                <va-input
                  v-model="form.alternateEmail"
                  type="email"
                  label="Email"
                  :messages="emailMessages">
                  <va-icon
                    slot="prepend"
                    color="gray"
                    name="fa fa-envelope-o"
                  />
                </va-input>
              </div>
              <div class="flex md6 xs12">
                <va-input
                  v-model="form.phoneNo"
                  label="Tel. číslo">
                  <va-icon
                    slot="prepend"
                    color="gray"
                    name="fa fa-phone"
                  />
                </va-input>
              </div>
              <div class="flex md4 sm6 xs12">
                <va-select
                  label="Oddelenie"
                  v-model="form.department"
                  textBy="description"
                  :options="departments"
                  @input="getDepartmentGroup( form.department.id )" />
              </div>
              <div class="flex md4 sm6 xs12">
                <va-select
                  label="Pozícia"
                  v-model="form.position"
                  textBy="description"
                  :options="positions"/>
              </div>
              <div class="flex md4 sm6 xs12">
                <va-select
                  label="Priradiť skupiny"
                  v-model="form.groups"
                  textBy="description"
                  searchable
                  multiple
                  :options="groupsList"
                />
              </div>
            </div>
            <div class="row justify--center">
              <va-button color="gray" @click="back">Späť</va-button>
              <va-button @click="submit">Vytvoriť</va-button>
            </div>
          </form>
        </va-card>
      </div>
    </div>
  </div>
</template>

<script>

import axios from 'axios'

export default {
  name: 'user-registration',
  data () {
    return {
      form: {
        firstname: '',
        surname: '',
        alternateEmail: '',
        phoneNo: '+421',
        department: {},
        position: '',
        groups: [],
      },
      emailMessages: ['Je potrebné zadať osobný email na ktorý príde uživivateľovi pozvánka.'],
      departments: [],
      positions: [],
      groupsList: [],
    }
  },
  methods: {
    clear (field) {
      this[field] = ''
    },
    back () {
      return this.$router.go(-1)
    },
    getDepartments () {
      axios.get('http://localhost:8080/department', { withCredentials: true })
        .then(response => {
          this.departments = response.data.map(obj => {
            const department = {}
            department.id = obj.id
            department.description = obj.name
            return department
          })
        })
    },
    getPositions () {
      axios.get('http://localhost:8080/position', { withCredentials: true })
        .then(response => {
          this.positions = response.data
        })
    },
    getDepartmentGroup (id) {
      if (id) {
        axios.get('http://localhost:8080/department/' + id + '/group', { withCredentials: true })
          .then(response => {
            this.form.groups = response.data
          })
      }
    },
    getGroups () {
      axios.get('http://localhost:8080/user/group', { withCredentials: true })
        .then(response => {
          this.groupsList = response.data
        })
    },
    submit () {
      axios.post('http://localhost:8080/user', this.form, { withCredentials: true })
        .then(response => {
          console.log('OK')
          // todo: go to the user detail
          return this.$router.push({ name: 'users', params: { success: true } })
        })
        .catch(response => {
          console.error('Chbz pri odoslani')
        })

      return false
    },
  },
  created () {
    this.getDepartments()
    this.getPositions()
    this.getGroups()
  },
}
</script>

<style>
  .row.row-inside {
    max-width: none;
  }
</style>
