<template>
  <div class="form-elements">
    <div class="row">
      <div class="flex xs12">
        <va-card title="Projekt">
          <form>
            <div>
              <div class="row">
                <div class="flex xs12">
                  <va-tabs grow v-model="tabValue" style="width: 100%;">
                    <va-tab title="Základné informácie">Základné informácie</va-tab>
                    <va-tab title="Adresa">Adresa</va-tab>
                    <va-tab title="Nastavenie uživateľa">Nastavenie uživateľa</va-tab>
                  </va-tabs>
                </div>
              </div>
            </div>
            <div v-show="tabValue === 0" class="row">
              <div class="flex md6 xs12">
                <va-input
                  v-model="form.firstname"
                  label="Meno"
                />
              </div>
              <div class="flex md6 xs12">
                <va-input
                  label="Priezvisko"
                  v-model="form.surname"/>
              </div>
              <div class="flex md6 xs12">
                <va-input
                  label="Alternatívny email"
                  type="email"
                  v-model="form.altEmail">
                  <va-icon
                    slot="prepend"
                    color="gray"
                    name="fa fa-envelope-o"
                  />
                </va-input>
              </div>
              <div class="flex md6 xs12">
                <va-input
                  label="Tel. číslo"
                  v-model="form.phone">
                  <va-icon
                    slot="prepend"
                    color="gray"
                    name="fa fa-phone"
                  />
                </va-input>
              </div>
              <div class="flex md6 xs12">
                <va-date-picker
                  label="Dátum narodenia"
                  v-model="form.birthdate">
                  <va-icon
                    slot="prepend"
                    color="gray"
                    name="fa fa-calendar"
                  />
                </va-date-picker>
              </div>
              <div class="flex md6 xs12">
                <va-input
                  label="Miesto narodenia"
                  v-model="form.birthplace">
                  <va-icon
                    slot="prepend"
                    color="gray"
                    name="fa fa-hospital-o"
                  />
                </va-input>
              </div>
              <div class="flex md6 xs12">
                <va-radio-button label="Muž" option="1" v-model="form.gender"/>
                <va-radio-button label="Žena" option="2" v-model="form.gender"/>
              </div>
            </div>
            <div v-show="tabValue === 1" class="row">
              <div class="flex md4 sm6 xs12">
                <va-input
                  v-model="form.address.country"
                  label="Krajina"
                />
              </div>
              <div class="flex md4 sm6 xs12">
                <va-input
                  v-model="form.address.city"
                  label="Mesto"
                />
              </div>
              <div class="flex md4 sm6 xs12">
                <va-input
                  v-model="form.address.postalCode"
                  label="PSČ"
                />
              </div>
              <div class="flex md6 xs12">
                <va-input
                  v-model="form.address.street"
                  label="Ulica"
                />
              </div>
              <div class="flex md6 xs12">
                <va-input
                  v-model="form.address.number"
                  label="Číslo"
                />
              </div>
            </div>
            <div v-show="tabValue === 2" class="row">
              <div class="flex md6 xs12">
                <va-select
                  label="Oddelenie"
                  v-model="form.setting.department"
                  textBy="name"
                  :options="departments"
                />
              </div>
              <div class="flex md6 xs12">
                <va-select
                  label="Pozícia"
                  v-model="form.setting.position"
                  textBy="description"
                  :options="positions"
                />
              </div>
              <div class="flex md4 sm6 xs12">
                <va-select
                  label="Manažér"
                  v-model="form.setting.manager"
                  textBy="name"
                  :options="users"
                  searchable
                />
              </div>
              <div class="flex md4 sm6 xs12">
                <va-select
                  label="Typ zamestnanca"
                  v-model="form.setting.type"
                  textBy="name"
                  :options="types"
                />
              </div>
              <div class="flex md4 sm6 xs12">
                <va-select
                  label="Priradené skupiny"
                  v-model="form.setting.groups"
                  textBy="description"
                  multiple
                  searchable
                  :options="groups"
                />
              </div>
            </div>
            <div class="row justify--center">
              <va-button color="gray" @click="back">Späť</va-button>
              <va-button @click="submit">Uložiť</va-button>
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
  data () {
    return {
      form: {
        firstname: '',
        surname: '',
        altEmail: '',
        phone: '',
        birthdate: '',
        birthplace: '',
        gender: 0,
        address: {
          country: '',
          city: '',
          postalCode: '',
          street: '',
          number: '',
        },
        setting: {
          department: {},
          position: {},
          manager: {},
          type: {},
          groups: [],
        },
      },
      departments: [],
      positions: [],
      users: [],
      types: [{ id: 1, name: 'Brigádnik' }, { id: 2, name: 'Živnostník' }, { id: 3, name: 'Zamestnanec' }],
      groups: [],
      tabValue: 0,
    }
  },
  methods: {
    clear (field) {
      this[field] = ''
    },
    back () {
      return this.$router.go(-1)
    },
    getUserProfile () {
      if (Number.isInteger(parseInt(this.$route.params.id))) {
        axios.get('http://localhost:8080/user/' + this.$route.params.id + '/profile', { withCredentials: true })
          .then(response => {
            this.form = response.data
          })
      }
    },
    getDepartments () {
      axios.get('http://localhost:8080/department', { withCredentials: true })
        .then(response => {
          this.departments = response.data
        })
    },
    getPositions () {
      axios.get('http://localhost:8080/position', { withCredentials: true })
        .then(response => {
          this.positions = response.data
        })
    },
    getUsers () {
      axios.get('http://localhost:8080/user/list', { withCredentials: true })
        .then(response => {
          this.users = response.data
        })
    },
    getGroups () {
      axios.get('http://localhost:8080/user/group', { withCredentials: true })
        .then(response => {
          this.groups = response.data
        })
    },
    submit () {
      if (this.$route.params.id === 'new') {
        this.create()
      } else {
        this.edit()
      }

      return false
    },
    create () {
      axios.post('http://localhost:8080/project', this.form, { withCredentials: true })
        .then(response => {
          console.log('OK')
          // todo: go to the user detail
          return this.$router.push({ name: 'projects', params: { success: true } })
        })
        .catch(response => {
          console.error('Chyba pri odoslani projektu')
        })

      return false
    },
    edit () {
      axios.put('http://localhost:8080/user/' + this.$route.params.id + '/profile', this.form, { withCredentials: true })
        .then(response => {
          console.log('OK')
          // todo: go to the project detail
          // return this.$router.push({ name: 'project', params: { success: true } })
          return this.$router.push({ name: 'user-edit', query: { id: 1 }, params: { success: true } })
        })
        .catch(response => {
          console.error('Chyba pri odoslani projektu')
          return false
        })
    },

  },
  created () {
    this.getDepartments()
    this.getPositions()
    this.getUsers()
    this.getGroups()
    this.getUserProfile()
  },
}
</script>

<style>
  .row.row-inside {
    max-width: none;
  }
</style>
