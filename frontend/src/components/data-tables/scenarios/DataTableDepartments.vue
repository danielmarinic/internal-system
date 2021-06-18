<template>
  <va-card :title="$t('tables.departments')">
    <va-data-table
      :fields="fields"
      :data="items"
      :per-page="10"
    >
      <template slot="manager.photo" slot-scope="props">
        <img :src="props.rowData.manager.photo" class="data-table-server-pagination---avatar">
      </template>
      <template slot="actions" slot-scope="props">
        <va-popover :message="`${$t('tables.edit')} ${props.rowData.name}`" placement="top">
          <va-button flat small color="gray" icon="fa fa-pencil" @click="edit(props.rowData.id)" />
        </va-popover>

        <va-popover :message="`${$t('tables.delete')} ${props.rowData.name}`" placement="top">
          <va-button flat small color="gray" icon="fa fa-trash" />
        </va-popover>
      </template>
    </va-data-table>
    <template slot="actions">
      <va-button icon="fa fa-plus" @click="createDepartment"/>
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
        name: 'name',
        title: 'Name',
        sortField: 'name',
        width: '40%',
      }, {
        name: '__slot:manager.photo',
        width: '60px',
      }, {
        name: 'manager.fullname',
        title: 'Leader',
        width: '25%',
      }, {
        name: 'count_users',
        title: 'Users',
        sortField: 'count_users',
        width: '5%',
      }, {
        name: 'count_groups',
        title: 'Groups',
        sortField: 'count_groups',
        width: '5%',
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
      axios.get('http://localhost:8080/department', { withCredentials: true })
        .then(response => {
          this.items = response.data
        })
    },
    edit (id) {
      return this.$router.push({ name: 'department-edit', params: { id: id } })
    },
    createDepartment () {
      return this.$router.push({ name: 'department-edit', params: { id: 'new' } })
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
