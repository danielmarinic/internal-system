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
                    <va-tab title="Kontakty">Kontakty</va-tab>
                    <va-tab title="Interné">Interné</va-tab>
                  </va-tabs>
                </div>
              </div>
            </div>
            <div v-show="tabValue === 0" class="row">
              <div class="flex md6 xs12">
                <va-input
                  v-model="form.name"
                  label="Názov"
                />
              </div>
              <div class="flex md6 xs12">
                <va-select
                  label="Typ"
                  v-model="form.type"
                  text-by="description"
                  :options="types"/>
              </div>
              <div class="flex md6 xs12">
                <va-select
                  label="Líder"
                  v-model="form.leaders"
                  textBy="name"
                  searchable
                  multiple
                  :options="users"
                />
              </div>
              <div class="flex md6 xs12">
                <va-select
                  label="Zákaznik"
                  v-model="form.customer"
                  textBy="name"
                  searchable
                  :options="customers"
                />
              </div>
            </div>
            <div v-show="tabValue === 1" class="row">
              <div class="flex md6 xs12">
                <va-input
                  label="Notifikačné emaily"
                  v-model="form.notifyEmails"
                >
                  <va-icon
                    slot="prepend"
                    color="gray"
                    name="fa fa-envelope-o"
                  />
                </va-input>
              </div>
              <div class="flex md6 xs12">
                <va-checkbox
                  label="Zasielať notifikácie"
                  type="email"
                  v-model="form.notify"
                />
              </div>
              <div class="flex xs12">
                <va-input
                  label="Kontakt na zákaznika"
                  v-model="form.contactToCustomer"
                >
                  <va-icon
                    slot="prepend"
                    color="gray"
                    name="fa fa-user"
                  />
                </va-input>
              </div>
            </div>
            <div v-show="tabValue === 2" class="row">
              <div class="flex md4 sm6 xs12">
                <va-input
                  label="Počet hodnín"
                  v-model="form.requireHours"
                >
                  <va-icon
                    slot="prepend"
                    color="gray"
                    name="fa fa-clock-o"
                  />
                </va-input>
              </div>
              <div class="flex md4 sm6 xs12">
                <va-input
                  label="Počet ľudí"
                  v-model="form.requireMen"
                >
                  <va-icon
                    slot="prepend"
                    color="gray"
                    name="fa fa-users"
                  />
                </va-input>
              </div>
              <div class="flex md4 sm6 xs12">
                <va-checkbox
                  label="Interný projekt"
                  v-model="form.internal"
                />
              </div>
              <div class="flex md6 xs12">
                <va-input
                  label="Cenová ponuka"
                  v-model="form.urlProposal"
                >
                  <va-icon
                    slot="prepend"
                    color="gray"
                    name="fa fa-globe"
                  />
                </va-input>
              </div>
              <div class="flex md6 xs12">
                <va-input
                  label="Akceptačný protokol"
                  v-model="form.urlDeliveryAccept"
                >
                  <va-icon
                    slot="prepend"
                    color="gray"
                    name="fa fa-globe"
                  />
                </va-input>
              </div>
              <div class="flex xs12">
                <va-input
                  label="Poznámka"
                  type="textarea"
                  v-model="form.note"/>
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
        name: '',
        type: '',
        leaders: [],
        customer: {},
        // department: {},
        contactToCustomer: '',
        notify: false,
        notifyEmails: '',
        note: '',
        internal: false,
        requireMen: '',
        requireHours: '',
        urlProposal: 'https://',
        urlDeliveryAccept: 'https://',
      },
      types: [{ id: 'FIX_PRICE', description: 'Fix price' }, { id: 'BODY_LEASING', description: 'Body leasing' }],
      users: [],
      customers: [],
      departments: [],
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
    getUsers () {
      axios.get('http://localhost:8080/user/list', { withCredentials: true })
        .then(response => {
          this.users = response.data
        })
    },
    getCustomers () {
      axios.get('http://localhost:8080/customer/list', { withCredentials: true })
        .then(response => {
          this.customers = response.data
        })
    },
    getProject () {
      if (Number.isInteger(parseInt(this.$route.params.id))) {
        axios.get('http://localhost:8080/project/' + this.$route.params.id, { withCredentials: true })
          .then(response => {
            this.form.name = response.data.name
            this.form.type = response.data.type
            this.form.leaders = response.data.leaders
            this.form.customer = response.data.customer
            this.form.notify = response.data.notify
            this.form.notifyEmails = response.data.notifyEmails
            this.form.contactToCustomer = response.data.contactToCustomer
            this.form.requireHours = response.data.requireHours
            this.form.requireMen = response.data.requireMen
            this.form.internal = response.data.internal
            this.form.urlProposal = response.data.urlProposal
            this.form.urlDeliveryAccept = response.data.urlDeliveryAccept
            this.form.note = response.data.note
          })
      }
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
      axios.put('http://localhost:8080/project/' + this.$route.params.id, this.form, { withCredentials: true })
        .then(response => {
          console.log('OK')
          // todo: go to the project detail
          // return this.$router.push({ name: 'project', params: { success: true } })
          return this.$router.push({ name: 'projects', params: { success: true } })
        })
        .catch(response => {
          console.error('Chyba pri odoslani projektu')
          return false
        })
    },

  },
  created () {
    this.getUsers()
    this.getCustomers()
    this.getProject()
  },
}
</script>

<style>
  .row.row-inside {
    max-width: none;
  }
</style>
