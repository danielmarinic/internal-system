<template>
  <div class="notification">
    <va-list fit class="mb-2">
      <va-list-label>Oznamy</va-list-label>
      <template v-for="(notification, i) in notifications">
        <va-item :key="'item' + notification.id" clickable @click="open(notification)">
          <va-item-section avatar>
            <va-icon name="material-icons" :color="colorByStatus(notification.level)" large>{{notification.level}}</va-icon>
          </va-item-section>

          <va-item-section>
            <va-item-label>
              {{ notification.title }}
            </va-item-label>

            <va-item-label caption>
              {{ notification.message }}
            </va-item-label>
          </va-item-section>

          <va-item-section side>
            <va-icon name="fa fa-eye" color="gray" />
          </va-item-section>
        </va-item>

        <va-list-separator v-if="i < notifications.length - 1" :key="'separator' + notification.id" />
      </template>
    </va-list>

    <va-modal
      v-model="modal.show"
      size="large"
      :title=" modal.title"
      :message=" modal.message "
      okText="Prečítané"
      :cancelText=" $t('modal.cancel') "
    />
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      notifications: [],
      modal: {
        show: false,
        title: '',
        message: '',
        level: '',
        createdAt: '',
        createdBy: '',
      },
    }
  },
  methods: {
    getGenderIcon (gender) {
      return gender === 'male' ? 'fa fa-mars' : 'fa fa-venus'
    },
    getGenderColor (gender) {
      return gender === 'male' ? 'blue' : 'pink'
    },
    open (notification) {
      this.modal.title = notification.title
      this.modal.message = notification.message
      this.modal.show = true
    },
    toggleStar (customer) {
      customer.starred = !customer.starred
    },
    colorByStatus (level) {
      if (level === 'info') {
        return 'blue'
      }
      if (level === 'warning') {
        return 'orange'
      }
      return 'red'
    },
    getActualNotification () {
      axios.get('http://localhost:8080/notice/actual', { withCredentials: true })
        .then(response => {
          this.notifications = response.data
        })
    },
  },
  created () {
    this.getActualNotification()
  },
}
</script>
