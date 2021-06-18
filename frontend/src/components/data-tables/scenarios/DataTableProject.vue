<template>
  <va-card :title="$t('tables.projects')">
    <va-data-table
      :fields="fields"
      :data="items"
      :per-page="10"
    >
      <template v-slot:status="props">
        <va-badge :color="getStatusColor(props.rowData.active)">
          {{ getStatus(props.rowData.active) }}
        </va-badge>
      </template>
      <template slot="actions" slot-scope="props">
        <va-popover :message="`${$t('tables.edit')} ${props.rowData.name}`" placement="top">
          <va-button flat small color="gray" icon="fa fa-pencil" @click="edit(props.rowData.id)" />
        </va-popover>

        <va-popover message="Uživatelia" placement="top">
          <va-button flat small color="gray" icon="fa fa-users" @click="member(props.rowData.id)" />
        </va-popover>

        <va-popover :message="`${$t('tables.delete')} ${props.rowData.name}`" placement="top">
          <va-button flat small color="gray" icon="fa fa-trash" @click="deleteProject(props.rowData.id)" />
        </va-popover>
      </template>
    </va-data-table>
    <template slot="actions">
      <va-button icon="fa fa-plus" @click="createProject"/>
    </template>
  </va-card>
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      items: [],
    }
  },
  computed: {
    fields () {
      return [{
        name: 'customer.name',
        title: 'Zákaznik',
        sortField: 'customer.name',
        width: '30%',
      }, {
        name: 'name',
        title: 'Názov',
        sortField: 'name',
        width: '20%',
      }, {
        name: 'type.description',
        title: 'Typ',
        width: '15%',
      }, {
        name: '__slot:status',
        title: 'Stav',
        width: '20%',
        // sortField: 'active',
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
      axios.get('http://localhost:8080/project', { withCredentials: true })
        .then(response => {
          this.items = response.data
        })
    },
    edit (id) {
      return this.$router.push({ name: 'project-edit', params: { id: id } })
    },
    createProject () {
      return this.$router.push({ name: 'project-edit', params: { id: 'new' } })
    },
    deleteProject (id) {
      axios.delete('http://localhost:8080/project/' + id, { withCredentials: true })
        .then(response => {
          console.log('Delete OK')
          this.readItems()
        })
        .catch(reason => {
          console.error('Delete FAIL')
        })
    },
    getStatus (active) {
      return active ? 'Prebieha' : 'Uzavretný'
    },
    getStatusColor (status) {
      if (status) {
        return 'success'
      }

      return 'danger'
    },
    member (id) {
      return this.$router.push({ name: 'project-member', params: { id: id } })
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
</style>
