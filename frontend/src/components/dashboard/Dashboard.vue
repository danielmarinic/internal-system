<template>
  <div class="dashboard">
    <dashboard-charts />
  </div>
</template>

<script>
import DashboardCharts from './DashboardCharts'

export default {
  name: 'dashboard',
  components: {
    DashboardCharts,
  },
  methods: {
    addAddressToMap ({ city, country }) {
      this.$refs.dashboardMap.addAddress({ city: city.text, country })
    },
  },
}
</script>

<style lang="scss">
  .row-equal .flex {
    .va-card {
      height: 100%;
    }
  }

  .dashboard {
    .va-card {
      margin-bottom: 0 !important;
    }
  }
</style>
