<template>
  <div class="form-elements">
    <div class="row">
      <div class="flex xs12 lg6">
        <va-list fit class="mb-2">
          <va-list-label>Lídri</va-list-label>
          <va-item>
            <va-item-section>
              <va-select
                v-model="newLeaders"
                label="Uživateľ"
                multiple
                searchable
                textBy="name"
                :options="users"
              />
            </va-item-section>
          </va-item>
          <va-item clickable @click="addLeader">
            <va-item-section>
              <va-item-label>
                Pridať lídrov
              </va-item-label>

              <va-item-label caption>
                Vyberte zo zoznamu uživateľov.
              </va-item-label>
            </va-item-section>
          </va-item>
          <template v-for="(leader, i) in project.leaders">
            <va-item :key="'item' + leader.id" clickable @click="removeLeader(leader.id)">
              <va-item-section avatar>
                <va-avatar>
                  <img :src="leader.photo" :alt="leader.fullname">
                </va-avatar>
              </va-item-section>

              <va-item-section>
                <va-item-label>
                  {{ leader.fullname }}
                </va-item-label>

                <va-item-label caption>
                  {{ leader.position }}
                </va-item-label>
              </va-item-section>

              <va-item-section side>
                <va-icon name="fa fa-trash" color="gray" />
              </va-item-section>
            </va-item>

            <va-list-separator v-if="i < leader.length - 1" :key="'separator' + leader.id" />
          </template>

        </va-list>
      </div>
      <div class="flex xs12 lg6">
        <va-list fit class="mb-2">
          <va-list-label>Členovia</va-list-label>
          <va-item>
            <va-item-section>
              <va-select
                v-model="newUsers"
                label="Uživateľ"
                multiple
                searchable
                textBy="name"
                :options="users"
              />
            </va-item-section>
          </va-item>
          <va-item clickable @click="addUser">
            <va-item-section>
              <va-item-label>
                Pridať členov
              </va-item-label>

              <va-item-label caption>
                Vyberte zo zoznamu uživateľov.
              </va-item-label>
            </va-item-section>
          </va-item>
          <template v-for="(user, i) in project.users">
            <va-item :key="'item' + user.id" clickable @click="removeUser(user.id)">
              <va-item-section avatar>
                <va-avatar>
                  <img :src="user.photo" :alt="user.fullname">
                </va-avatar>
              </va-item-section>

              <va-item-section>
                <va-item-label>
                  {{ user.fullname }}
                </va-item-label>

                <va-item-label caption>
                  {{ user.position }}
                </va-item-label>
              </va-item-section>

              <va-item-section side>
                <va-icon name="fa fa-trash" color="gray" />
              </va-item-section>
            </va-item>

            <va-list-separator v-if="i < user.length - 1" :key="'separator' + user.id" />
          </template>

        </va-list>
      </div>
    </div>
  </div>
</template>

<script>

import axios from 'axios'

export default {
  data () {
    return {
      project: {
        name: '',
        users: [],
        leaders: [],
      },
      users: [],
      newUsers: [],
      newLeaders: [],
    }
  },
  methods: {
    clear (field) {
      this[field] = ''
    },
    back () {
      return this.$router.go(-1)
    },
    getUsers () {
      axios.get('http://localhost:8080/user/list', { withCredentials: true })
        .then(response => {
          this.users = response.data
        })
    },
    getProjectUsers () {
      if (Number.isInteger(parseInt(this.$route.params.id))) {
        axios.get('http://localhost:8080/project/' + this.$route.params.id + '/user', { withCredentials: true })
          .then(response => {
            this.project.users = response.data
          })
      }
    },
    getProjectLeaders () {
      if (Number.isInteger(parseInt(this.$route.params.id))) {
        axios.get('http://localhost:8080/project/' + this.$route.params.id + '/leader', { withCredentials: true })
          .then(response => {
            this.project.leaders = response.data
          })
      }
    },
    submit () {
      if (this.$route.params.id === 'new') {
        return this.createDepartment()
      } else {
        return this.edit()
      }
    },
    removeUser (id) {
      console.log('Remove user with id: ' + id)
      axios.delete('http://localhost:8080/project/' + this.$route.params.id + '/user/' + id, { withCredentials: true })
        .then(response => {
          // todo: go to the user detail
          this.project.users = response.data
        })
        .catch(response => {
          console.error('Chyba pri vymazani clena')
        })
      return false
    },
    addUser () {
      axios.post('http://localhost:8080/project/' + this.$route.params.id + '/user', this.newUsers, { withCredentials: true })
        .then(response => {
          // todo: go to the user detail
          this.project.users = response.data
          this.newUsers = []
        })
        .catch(response => {
          console.error('Chyba pri pridavani clenov')
        })
    },
    removeLeader (id) {
      console.log('Remove user with id: ' + id)
      axios.delete('http://localhost:8080/project/' + this.$route.params.id + '/leader/' + id, { withCredentials: true })
        .then(response => {
          // todo: go to the user detail
          this.project.leaders = response.data
        })
        .catch(response => {
          console.error('Chyba pri vymazani clena')
        })
      return false
    },
    addLeader () {
      axios.post('http://localhost:8080/project/' + this.$route.params.id + '/leader', this.newLeaders, { withCredentials: true })
        .then(response => {
          // todo: go to the user detail
          this.project.leaders = response.data
          this.newLeaders = []
        })
        .catch(response => {
          console.error('Chyba pri pridavani clenov')
        })
    },
  },
  created () {
    this.getUsers()
    this.getProjectUsers()
    this.getProjectLeaders()
  },
}
</script>

<style>
  .row.row-inside {
    max-width: none;
  }
</style>
