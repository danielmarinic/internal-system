<template>
  <div class="form-elements">
    <div class="row">
      <div class="flex xs12 lg6">
        <va-card title="Oddelenie">
          <form>
            <div class="row">
              <div class="flex md6 xs12">
                <va-input
                  v-model="form.name"
                  label="Názov"
                />
              </div>
              <div class="flex md6 xs12">
                <va-select
                  label="Manažér"
                  v-model="form.manager"
                  textBy="name"
                  searchable
                  :options="users"
                />
              </div>
              <div class="flex xs12">
                <va-select
                  label="Priradiť skupiny"
                  v-model="form.groups"
                  textBy="description"
                  searchable
                  multiple
                  :options="groups"
                />
              </div>
            </div>
            <div class="row justify--center">
              <va-button color="gray" @click="back">Späť</va-button>
              <va-button @click="submit">Uložiť</va-button>
            </div>
          </form>
        </va-card>
      </div>
      <div class="flex xs12 lg6">
        <va-list fit class="mb-2">
          <va-list-label>Členovia</va-list-label>
          <va-item>
            <va-item-section>
              <va-select
                v-model="newMembers"
                label="Uživateľ"
                multiple
                searchable
                textBy="name"
                :options="users"
              />
            </va-item-section>
          </va-item>
          <va-item clickable @click="addMember">
            <va-item-section>
              <va-item-label>
                Pridať členov
              </va-item-label>

              <va-item-label caption>
                Vyberte zo zoznamu uživateľov.
              </va-item-label>
            </va-item-section>
          </va-item>
          <template v-for="(member, i) in form.members">
            <va-item :key="'item' + member.id" clickable @click="removeMember(member.id)">
              <va-item-section avatar>
                <va-avatar>
                  <img :src="member.photo" :alt="member.fullname">
                </va-avatar>
              </va-item-section>

              <va-item-section>
                <va-item-label>
                  {{ member.fullname }}
                </va-item-label>

                <va-item-label caption>
                  {{ member.position }}
                </va-item-label>
              </va-item-section>

              <va-item-section side>
                <va-icon name="fa fa-trash" color="gray" />
              </va-item-section>
            </va-item>

            <va-list-separator v-if="i < member.length - 1" :key="'separator' + member.id" />
          </template>

        </va-list>
      </div>
    </div>
  </div>
</template>

<script>

import axios from 'axios'

export default {
  name: 'department-edit',
  data () {
    return {
      form: {
        name: '',
        manager: '',
        groups: [],
        members: [],
      },
      assignedGroups: [],
      groups: [],
      users: [],
      newMembers: [],
    }
  },
  methods: {
    clear (field) {
      this[field] = ''
    },
    back () {
      return this.$router.go(-1)
    },
    getDepartment () {
      if (Number.isInteger(parseInt(this.$route.params.id))) {
        axios.get('http://localhost:8080/department/' + this.$route.params.id, { withCredentials: true })
          .then(response => {
            this.form.name = response.data.name
            this.form.manager = response.data.manager
            this.form.groups = response.data.assignedGroups
          })
      }
    },
    getUsers () {
      axios.get('http://localhost:8080/user/list', { withCredentials: true })
        .then(response => {
          this.users = response.data
        })
    },
    getGroups () {
      axios.get('http://localhost:8080/user/group', { withCredentials: true })
        .then(response => {
          this.groups = response.data
        })
    },
    getDepartmentMembers () {
      if (Number.isInteger(parseInt(this.$route.params.id))) {
        axios.get('http://localhost:8080/department/' + this.$route.params.id + '/member', { withCredentials: true })
          .then(response => {
            this.form.members = response.data
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
    createDepartment () {
      axios.post('http://localhost:8080/department', this.form, { withCredentials: true })
        .then(response => {
          // todo: go to the user detail
          return this.$router.push({ name: 'departments', params: { success: true } })
        })
        .catch(response => {
          console.error('Chyba pri odoslani')
        })

      return false
    },
    edit () {
      axios.put('http://localhost:8080/department/' + this.$route.params.id, this.form, { withCredentials: true })
        .then(response => {
          // todo: go to the user detail
          return this.$router.push({ name: 'departments', params: { success: true } })
        })
        .catch(response => {
          console.error('Chyba pri odoslani')
        })

      return false
    },
    removeMember (id) {
      console.log('Remove user with id: ' + id)
      axios.delete('http://localhost:8080/department/' + this.$route.params.id + '/member/' + id, { withCredentials: true })
        .then(response => {
          // todo: go to the user detail
          this.form.members = response.data
        })
        .catch(response => {
          console.error('Chyba pri vymazani clena')
        })
      return false
    },
    addMember () {
      axios.post('http://localhost:8080/department/' + this.$route.params.id + '/member', this.newMembers, { withCredentials: true })
        .then(response => {
          // todo: go to the user detail
          this.form.members = response.data
          this.newMembers = []
        })
        .catch(response => {
          console.error('Chyba pri pridavani clenov')
        })
    },
  },
  created () {
    this.getUsers()
    this.getGroups()
    this.getDepartmentMembers()
    this.getDepartment()
  },
}
</script>

<style>
  .row.row-inside {
    max-width: none;
  }
</style>
