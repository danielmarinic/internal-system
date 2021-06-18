<template>
  <va-card :title="projectName">
    <va-data-table
      :fields="fields"
      :data="items"
      :per-page="10"
    >
      <template slot="calendar">
        <va-icon name="fa fa-calendar" color="secondary" />
      </template>
      <template slot="clock">
        <va-icon name="fa fa-clock-o" color="secondary" />
      </template>
      <template slot="actions" slot-scope="props">
        <va-button flat small color="gray" icon="fa fa-pencil" @click="edit(props.rowData.id)"/>
        <va-button flat small color="gray" icon="fa fa-trash" @click="deleteTimesheet(props.rowData.id)"/>
      </template>
    </va-data-table>
    <template slot="actions">
      <va-button icon="fa fa-plus" @click="createTimesheet"/>
    </template>
  </va-card>
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      items: [],
      projectName: '',
    }
  },
  computed: {
    fields () {
      return [{
        name: 'day',
        title: 'Deň',
        sortField: 'day',
        width: '15%',
      }, {
        name: '__slot:calendar',
        width: '30px',
        dataClass: 'text-center',
      }, {
        name: 'date',
        title: 'Dátum',
        sortField: 'date',
        width: '15%',
      }, {
        name: 'start',
        title: 'Čas začatia',
        sortField: 'start',
        width: '15%',
      }, {
        name: 'end',
        title: 'Čas ukončenia',
        sortField: 'end',
        width: '15%',
      }, {
        name: '__slot:clock',
        width: '30px',
        dataClass: 'text-center',
      }, {
        name: 'time',
        title: 'Čas',
        sortField: 'projectTime',
        width: '5%',
      }, {
        name: 'note',
        title: 'Poznámka',
        width: '30%',
      }, {
        name: '__slot:actions',
        dataClass: 'text-right',
      }]
    },
  },
  created () {
    this.readItems()
  },
  methods: {
    readItems () {
      axios.get('http://localhost:8080/timesheet/me/project/' + this.$route.params.id + '?from=' + this.$route.query.from + '&to=' + this.$route.query.to, { withCredentials: true })
        .then(response => {
          this.items = response.data
        })
    },
    createTimesheet () {
      return this.$router.push({ name: 'timesheet-edit', params: { id: 'new' } })
    },
    edit (id) {
      console.log('Edit id ' + id)
      return this.$router.push({ name: 'timesheet-edit', params: { id: id } })
    },
    deleteTimesheet (id) {
      axios.delete('http://localhost:8080/timesheet/' + id, { withCredentials: true })
        .then(response => {
          this.readItems()
        })
        .catch(reason => {
          console.warn('Chyba pri delete', reason)
        })
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
</style>
