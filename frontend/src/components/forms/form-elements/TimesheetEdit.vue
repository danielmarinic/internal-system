<template>
  <div class="form-elements">
    <div class="row">
      <div class="flex xs12">
        <va-card :title="$t('forms.inputs.title')">
          <form>
            <div class="row">
              <div class="flex xs12">
                <va-select
                  v-model="form.project"
                  label="Projekt"
                  :options="projects"
                  textBy="description"
                  searchable
                />
              </div>
              <div class="flex md4 sm6 xs12">
                <va-date-picker
                  v-model="form.workDate"
                  label="Dátum">
                  <va-icon
                    slot="prepend"
                    color="gray"
                    name="fa fa-calendar"
                  />
                </va-date-picker>
              </div>
              <div class="flex md4 sm6 xs12">
                <va-date-picker
                  v-model="form.startTime"
                  :config="{enableTime: true}"
                  label="Čas začatia">
                  <va-icon
                    slot="prepend"
                    color="gray"
                    name="fa fa-clock-o"
                  />
                </va-date-picker>
              </div>
              <div class="flex md4 sm6 xs12">
                <va-date-picker
                  v-model="form.endTime"
                  :config="{enableTime: true}"
                  label="Čas ukončenia">
                  <va-icon
                    slot="prepend"
                    color="gray"
                    name="fa fa-clock-o"
                  />
                </va-date-picker>
              </div>
              <div class="flex xs12">
                <va-input
                  v-model="form.note"
                  label="Poznámka"
                  type="textarea"/>
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
        project: [],
        workDate: '',
        startTime: '',
        endTime: '',
        note: '',
      },
      projects: [],
    }
  },
  methods: {
    clear (field) {
      this[field] = ''
    },
    back () {
      return this.$router.go(-1)
    },
    getTimesheet () {
      if (Number.isInteger(parseInt(this.$route.params.id))) {
        axios.get('http://localhost:8080/timesheet/' + this.$route.params.id, { withCredentials: true })
          .then(response => {
            this.form.project = response.data.project
            this.form.workDate = response.data.workDate
            this.form.startTime = response.data.startTime
            this.form.endTime = response.data.endTime
            this.form.note = response.data.note
          })
      }
    },
    getProjects () {
      axios.get('http://localhost:8080/project/me', { withCredentials: true })
        .then(response => {
          this.projects = response.data
        })
    },
    edit (id) {
      axios.put('http://localhost:8080/timesheet/' + id, this.form, { withCredentials: true })
        .then(response => {
          console.log('OK')
          // todo: go to the user detail
          return this.$router.push({ name: 'timesheets', params: { success: true } })
        })
        .catch(response => {
          console.error('Chyba pri edit()')
        })
    },
    createTimesheet () {
      axios.post('http://localhost:8080/timesheet', this.form, { withCredentials: true })
        .then(response => {
          console.log('OK')
          // todo: go to the user detail
          return this.$router.push({ name: 'timesheets', query: { success: true } })
        })
        .catch(response => {
          console.error('Chbz pri odoslani')
        })
    },
    submit () {
      if (this.$route.params.id === 'new') {
        this.createTimesheet()
      } else {
        this.edit(this.$route.params.id)
      }
      return false
    },
  },
  created () {
    this.getProjects()
    this.getTimesheet()
  },
}
</script>

<style>
  .row.row-inside {
    max-width: none;
  }
</style>
