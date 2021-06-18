<template>
  <div class="form-elements">
    <div class="row">
      <div class="flex xs12">
        <va-card title="Oznam">
          <form>
            <div class="row">
              <div class="flex xs12">
                <va-input
                  v-model="form.title"
                  label="Názov"
                />
              </div>
              <div class="flex md4 sm6 xs12">
                <va-date-picker
                  label="Zobratiť od"
                  v-model="form.startDate"
                />
              </div>
              <div class="flex md4 sm6 xs12">
                <va-date-picker
                  label="Zobratiť do"
                  v-model="form.endDate"
                />
              </div>
              <div class="flex md4 sm6 xs12">
                <va-select
                  v-model="form.level"
                  label="Priorita"
                  text-by="description"
                  :options="levels"
                />
              </div>
              <div class="flex xs12">
                <va-medium-editor
                  @initialized="handleEditorInitialization"
                >
                  <h1>Select Text To Open Editor</h1>

                  <p>
                    You enter into your favorite local bar looking
                    <span class="default-selection"><b>good</b></span> as hell, but you know the only
                    heads you want to turn—spicy & stylish alpha bitches — are heavily
                    fixated on the D. The hot girl talks to you, but she only wants to
                    be your best friend. Her nonthreatening and attentive best friend.
                    Receiver of sexy selfies, listener of stories. Meanwhile, you
                    attract unwanted attention from straight men, pudgy and greasy
                    moths to your emotionally distant flame.
                  </p>

                  <p>
                    Read the full article on <a href="https://medium.com/@dorn.anna/girl-no-you-dont-2e21e826c62c">Medium</a>
                  </p>
                </va-medium-editor>
              </div>
            </div>
            <div class="row justify--center">
              <va-button color="gray" @click="back">Späť</va-button>
              <va-button @click="submit">Uložiť</va-button>
            </div>
          </form>
        </va-card>
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
        title: '',
        message: '',
        startDate: '',
        endDate: '',
        level: '',
      },
      levels: [{ id: 'info', description: 'Info' }, { id: 'warning', description: 'Warning' }, { id: 'critical', description: 'Cricital' }],
      groups: [],
      users: [],
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
        axios.get('http://localhost:8080/department/' + this.$route.params.id + '/members', { withCredentials: true })
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
      return false
    },
    addMember () {
      console.log('Adding user..')
      return false
    },
    handleEditorInitialization (editor) {
      this.editor = editor
      this.$nextTick(() => {
        this.highlightSampleText()
      })
    },

    highlightSampleText () {
      const sampleText = document.getElementsByClassName('default-selection')[0]
      this.editor.selectElement(sampleText)
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
