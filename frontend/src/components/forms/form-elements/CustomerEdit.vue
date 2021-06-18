<template>
  <div class="form-elements">
    <div class="row">
      <div class="flex xs12">
        <va-card title="Zákaznik">
          <form>
            <div class="row">
              <div class="flex md6 xs12">
                <va-input
                  v-model="form.registrationNo"
                  label="IČO"
                  @keydown.tab="findCustomer"
                />
              </div>
              <div class="flex md2 xs2">
                <va-input
                  v-model="form.nameShort"
                  label="Skratka"
                />
              </div>
              <div class="flex md4 sm6 xs12">
                <va-input
                  v-model="form.name"
                  label="Názov"
                />
              </div>
              <div class="flex md6 xs12">
                <va-input
                  v-model="form.vatNo"
                  label="DIČ"
                />
              </div>
              <div class="flex md6 xs12">
                <va-input
                  v-model="form.icDph"
                  label="IČ DPH"
                />
              </div>
              <div class="flex md6 xs12">
                <va-input
                  v-model="form.registration"
                  label="Registrátor"
                />
              </div>
              <div class="flex md6 xs12">
                <va-input
                  v-model="form.registrationSection"
                  label="Registráčne číslo"
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
              <div class="flex md4 sm6 xs12">
                <va-input
                  v-model="form.address.postalCode"
                  label="PSČ"
                />
              </div>
              <div class="flex md4 sm6 xs12">
                <va-input
                  v-model="form.address.city"
                  label="Obec"
                />
              </div>
              <div class="flex md4 sm6 xs12">
                <va-input
                  v-model="form.address.country"
                  label="Krajina"
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
        name: '',
        nameShort: '',
        registrationNo: '',
        vatNo: '',
        icDph: '',
        address: {
          street: '',
          number: '',
          postalCode: '',
          city: '',
          country: '',
        },
        registration: '',
        registrationSection: '',
      },
    }
  },
  methods: {
    clear (field) {
      this[field] = ''
    },
    back () {
      return this.$router.go(-1)
    },
    getCustomer () {
      if (Number.isInteger(parseInt(this.$route.params.id))) {
        axios.get('http://localhost:8080/customer/' + this.$route.params.id, { withCredentials: true })
          .then(response => {
            this.form.name = response.data.name
            this.form.nameShort = response.data.nameShort
            this.form.registrationNo = response.data.registrationNo
            this.form.vatNo = response.data.vatNo
            this.form.icDph = response.data.icDph
            this.form.address = response.data.address
            this.form.registration = response.data.registration
            this.form.registrationSection = response.data.registrationSection
          })
      }
    },
    getUsers () {
      axios.get('http://localhost:8080/user', { withCredentials: true })
        .then(response => {
          this.users = response.data
        })
    },
    getCustomers () {
      axios.get('http://localhost:8080/customer', { withCredentials: true })
        .then(response => {
          this.customers = response.data
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
      axios.post('http://localhost:8080/customer', this.form, { withCredentials: true })
        .then(response => {
          console.log('OK')
          // todo: go to the user detail
          // return this.$router.push({ name: 'project', params: { success: true } })
          return this.$router.push({ name: 'customers' })
        })
        .catch(response => {
          console.error('Chyba pri odoslani projektu')
          return false
        })
    },
    edit () {
      axios.put('http://localhost:8080/customer/' + this.$route.params.id, this.form, { withCredentials: true })
        .then(response => {
          console.log('OK')
          // todo: go to the user detail
          // return this.$router.push({ name: 'project', params: { success: true } })
          return this.$router.push({ name: 'customers' })
        })
        .catch(response => {
          console.error('Chyba pri odoslani projektu')
          return false
        })
    },
    findCustomer () {
      this.form.registrationNo = this.form.registrationNo.replace(/\s/g, '')
      if (this.form.registrationNo.length === 8) {
        axios.get('http://localhost:8080/customer/load/' + this.form.registrationNo, { withCredentials: true })
          .then(response => {
            this.form.name = response.data.name
            this.form.nameShort = response.data.nameShort
            this.form.registrationNo = response.data.registrationNo
            this.form.vatNo = response.data.vatNo
            this.form.icDph = response.data.icDph
            this.form.address = response.data.address
            this.form.registration = response.data.registration
            this.form.registrationSection = response.data.registrationSection
          })
      }
    },
  },
  created () {
    this.getCustomer()
    this.getUsers()
    this.getCustomers()
  },
}
</script>

<style>
  .row.row-inside {
    max-width: none;
  }
</style>
