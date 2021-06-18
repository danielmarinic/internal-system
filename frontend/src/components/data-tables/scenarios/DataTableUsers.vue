<template>
  <va-card :title="$t('tables.users')">
    <va-data-table
      :fields="fields"
      :data="items"
      :per-page="10"
    >
      <template slot="photo" slot-scope="props">
        <img :src="props.rowData.photo" class="data-table-server-pagination---avatar">
      </template>
      <template slot="actions" slot-scope="props">
        <va-popover :message="`${$t('tables.edit')} ${props.rowData.firstname} ${props.rowData.surname}`" placement="top">
          <va-button flat small color="gray" icon="fa fa-pencil" />
        </va-popover>

        <va-popover :message="`${$t('tables.delete')} ${props.rowData.firstname} ${props.rowData.surname}`" placement="top">
          <va-button flat small color="gray" icon="fa fa-trash" />
        </va-popover>
      </template>
    </va-data-table>
    <template slot="actions">
      <va-button icon="fa fa-user-plus" @click="register"/>
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
        name: '__slot:photo',
        width: '60px',
      }, {
        name: 'firstname',
        title: 'First Name',
        sortField: 'firstname',
        width: '10%',
      }, {
        name: 'surname',
        title: 'Surname',
        sortField: 'surname',
        width: '10%',
      }, {
        name: 'email',
        title: 'E-mail',
        sortField: 'email',
        width: '15%',
      }, {
        name: 'department',
        title: 'Department',
        sortField: 'department',
        width: '15%',
      }, {
        name: 'position',
        title: 'Position',
        sortField: 'position',
        width: '15%',
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
      axios.get('http://localhost:8080/user', { withCredentials: true })
        .then(response => {
          this.items = response.data
        })
    },
    register () {
      return this.$router.push({ name: 'user-register' })
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
