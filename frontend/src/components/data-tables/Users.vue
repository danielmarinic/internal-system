<template>
  <div>
    <data-table-users class="mb-4"/>
  </div>
</template>

<script>
import DataTableUsers from './scenarios/DataTableUsers.vue'

export default {
  name: 'users',
  components: {
    DataTableUsers,
  },
}
</script>

<style lang="scss">
</style>
