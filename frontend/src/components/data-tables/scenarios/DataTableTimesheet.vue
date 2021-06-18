<template>
  <div class="row row-equal">
    <div class="flex xs9">
      <div v-if="showMonthPicker" class="datepicker-foreground">
        <template>
          <month-picker v-model="date" lang="sk" @input="setMonth"></month-picker>
        </template>
      </div>
      <div>
        <va-card :title="title">
          <va-data-table
            :fields="fields"
            :data="items"
            :per-page="10"
          >
            <template slot="actions" slot-scope="props">
              <va-button flat small color="gray" icon="fa fa-eye" @click="show(props.rowData.id)"/>
            </template>
          </va-data-table>
          <template slot="actions">
            <va-button icon="fa fa-calendar" @click="changeMonthPicker"/>
            <va-button icon="fa fa-plus" @click="createTimesheet"/>
          </template>
        </va-card>
      </div>
    </div>
    <div class="flex xs3">
      <div class="row">
        <div class="flex xs12 md12">
          <va-card>
            <div class="row row-separated">
              <div class="flex xs6">
                <p class="display-2 mb-1 text--center" :style="{color: this.$themes.primary}">{{ workedDays }}</p>
                <p class="text--center mb-1">Odpracované dni</p>
              </div>
              <div class="flex xs6">
                <p class="display-2 mb-1 text--center" :style="{color: this.$themes.info}">{{ freeDays }}</p>
                <p class="text--center no-wrap mb-1">Voľné dni</p>
              </div>
            </div>
          </va-card>
        </div>
      </div>
      <div class="row">
        <div class="flex xs12 md12">
          <va-card>
            <div class="row row-separated">
              <div class="flex xs9">
                <p class="display-2 mb-1" :style="{color: this.$themes.primary}">{{ timeTotal }}</p>
                <p class="no-wrap">Odpracovaných hodín</p>
              </div>
              <div class="flex xs3">
                <va-icon
                  slot="prepend"
                  color="gray"
                  name="fa fa-clock-o" size="60px"
                />
              </div>
            </div>
          </va-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { MonthPicker } from 'vue-month-picker'
export default {
  components: {
    MonthPicker,
  },
  data () {
    const today = new Date()
    return {
      titleConst: 'Dochádzka - ',
      title: 'Dochádzka - Máj 2021',
      items: [],
      date: { from: new Date(today.getFullYear(), today.getMonth(), 1), to: new Date(today.getFullYear(), today.getMonth() + 1, 0) },
      showMonthPicker: false,
      timeTotal: 160,
      workedDays: 16,
      freeDays: 2,
    }
  },
  computed: {
    fields () {
      return [{
        name: 'name',
        title: 'Projekt',
        sortField: 'name',
        width: '40%',
      }, {
        name: 'time',
        title: 'Čas',
        sortField: 'projectTime',
        width: '30%',
      }, {
        name: '__slot:actions',
        dataClass: 'text-right',
      }]
    },
  },
  /* mounted () {
    if (localStorage.timesheetFrom) {
      this.date.from = localStorage.timesheetFrom
    }
    if (localStorage.timesheetTo) {
      this.date.to = localStorage.timesheetTo
    }
  }, */
  /*  watch: {
    date (newFrom) {
      localStorage.timesheetFrom = newFrom.from
      localStorage.timesheetTo = newFrom.to
    },
  }, */
  created () {
    this.readItems()
    this.readStatistics()
    this.setDefaultDate()
  },
  methods: {
    readItems (month) {
      const param = month || { from: '2021-04-30T22:00:00.000Z', to: '2021-05-31T22:00:00.000Z' }
      axios.get('http://localhost:8080/timesheet/me/date?from=' + param.from + '&to=' + param.to, { withCredentials: true })
        .then(response => {
          this.items = response.data
        })
    },
    readStatistics () {
      axios.get('http://localhost:8080/timesheet/me/statistic?from=' + this.date.from.toISOString() + '&to=' + this.date.to.toISOString(), { withCredentials: true })
        .then(response => {
          this.timeTotal = response.data.timeTotal
          this.workedDays = response.data.workedDays
          this.freeDays = response.data.workDays - response.data.workedDays
        })
    },
    createTimesheet () {
      return this.$router.push({ name: 'timesheet-edit', params: { id: 'new' } })
    },
    show (id) {
      console.log('Edit id ' + id)
      return this.$router.push({ name: 'project-timesheets', params: { id: id }, query: { from: this.date.from.toISOString(), to: this.date.to.toISOString() } })
    },
    changeMonthPicker () {
      this.showMonthPicker = !this.showMonthPicker
    },
    setMonth () {
      this.title = this.titleConst + this.date.month + ' ' + this.date.year
      this.showMonthPicker = false
      this.readItems({ from: this.date.from.toISOString(), to: this.date.to.toISOString() })
      this.readStatistics()
    },
    setDefaultDate () {
      const date = new Date()
      this.date = { from: new Date(date.getFullYear(), date.getMonth(), 1), to: new Date(date.getFullYear(), date.getMonth() + 1, 0) }
    },
  },
}
</script>

<style lang="scss">
  .data-table-server-pagination---avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
  }

  .cards-container {
    .va-card {
      margin: 0;
    }
  }

  .datepicker-foreground {
    position: absolute;
    overflow: visible;
    z-index: 999;
    right: 0;
    top: 30px;
  }
</style>
