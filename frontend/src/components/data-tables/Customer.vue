<template>
  <div>
    <data-table-customer class="mb-4"/>
  </div>
</template>

<script>
import DataTableCustomer from './scenarios/DataTableCustomer.vue'

export default {
  name: 'users',
  components: {
    DataTableCustomer,
  },
}
</script>

<style lang="scss">
</style>
