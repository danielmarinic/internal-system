<template>
  <va-card title="Zákaznici">
    <va-data-table
      :fields="fields"
      :data="items"
      :per-page="10"
    >
      <template slot="photo" slot-scope="props">
        <img :src="props.rowData.photo" class="data-table-server-pagination---avatar">
      </template>
      <template slot="actions" slot-scope="props">
        <va-button flat small color="gray" icon="fa fa-pencil" @click="edit(props.rowData.id)"/>

        <va-button flat small color="gray" icon="fa fa-trash" @click="deleteCustomer(props.rowData.id)" />
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
        name: 'name',
        title: 'Názov',
        sortField: 'name',
        width: '30%',
      }, {
        name: 'address.city',
        title: 'Mesto',
        sortField: 'address.city',
        width: '20%',
      }, {
        name: 'address.street',
        title: 'Ulica',
        sortField: 'address.street',
        width: '20%',
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
      axios.get('http://localhost:8080/customer', { withCredentials: true })
        .then(response => {
          this.items = response.data
        })
    },
    register () {
      return this.$router.push({ name: 'customer-edit', params: { id: 'new' } })
    },
    edit (id) {
      console.log('Edit id ' + id)
      return this.$router.push({ name: 'customer-edit', params: { id: id } })
    },
    deleteCustomer (id) {
      console.log('Delete id ' + id)
      axios.delete('http://localhost:8080/customer/' + id, { withCredentials: true })
        .then(response => {
          this.items = response.data
          this.readItems()
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
