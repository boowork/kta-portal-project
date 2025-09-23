<script setup lang="ts">
import AddNewUserDrawer from '@/views/apps/user/list/AddNewUserDrawer.vue'
import ErrorDialog from '@/components/dialogs/ErrorDialog.vue'
import ToastContainer from '@/components/ToastContainer.vue'
import { useErrorHandler } from '@/composables/useErrorHandler'
import { userApi } from '@/api/user'
import type { User, CreateUserRequest, PaginationParams } from '@/api/types'

//  Error handler
const { handleApiError, showSuccessToast, clearErrors } = useErrorHandler()

//  Component state
const users = ref<User[]>([])
const isLoading = ref(false)
const isSubmitting = ref(false)

//  Search and filter state
const searchQuery = ref('')
const selectedRole = ref()
const selectedRows = ref([])

//  Data table options
const itemsPerPage = ref(10)
const page = ref(1)
const sortBy = ref()
const orderBy = ref()

//  Table headers
const headers = [
  { title: 'User', key: 'user' },
  { title: 'Role', key: 'role' },
  { title: 'Created At', key: 'createdAt' },
  { title: 'Actions', key: 'actions', sortable: false },
]

//  Update data table options
const updateOptions = (options: any) => {
  sortBy.value = options.sortBy[0]?.key
  orderBy.value = options.sortBy[0]?.order
  fetchUsers()
}

//  Fetch users from API
const fetchUsers = async () => {
  
  try {
    isLoading.value = true
    clearErrors()

    const params: PaginationParams = {
      page: page.value,
      size: itemsPerPage.value,
      sort: sortBy.value || 'createdAt',
      direction: orderBy.value === 'desc' ? 'DESC' : 'ASC',
    }

    const response = await userApi.getUsers(params)
    
    if (response.success && response.data) {
      users.value = response.data
    } else {
      handleApiError({ response: { data: response } })
    }
  } catch (error) {
    handleApiError(error)
  } finally {
    isLoading.value = false
  }
}

//  Initial fetch
await fetchUsers()

//  Computed values
const totalUsers = computed(() => users.value.length)

//  search filters
const roles = [
  { title: 'Admin', value: 'ADMIN' },
  { title: 'User', value: 'USER' },
]

const resolveUserRoleVariant = (role: string) => {
  const roleLowerCase = role.toLowerCase()

  if (roleLowerCase === 'admin')
    return { color: 'primary', icon: 'bx-crown' }
  if (roleLowerCase === 'user')
    return { color: 'success', icon: 'bx-user' }

  return { color: 'primary', icon: 'bx-user' }
}

const isAddNewUserDrawerVisible = ref(false)

//  Create new user
const addNewUser = async (userData: CreateUserRequest) => {
  try {
    isSubmitting.value = true
    clearErrors()

    const response = await userApi.createUser(userData)
    
    if (response.success) {
      showSuccessToast('사용자가 생성되었습니다.')
      isAddNewUserDrawerVisible.value = false
      await fetchUsers() // Refresh list
    } else {
      handleApiError({ response: { data: response } })
    }
  } catch (error) {
    handleApiError(error)
  } finally {
    isSubmitting.value = false
  }
}

//  Delete user with confirmation
const deleteUser = async (id: number) => {
  const user = users.value.find(u => u.id === id)
  const userName = user ? user.name : `ID ${id}`
  
  if (confirm(`정말로 "${userName}" 사용자를 삭제하시겠습니까?`)) {
    try {
      isSubmitting.value = true

      const response = await userApi.deleteUser(id)
      
      if (response.success) {
        showSuccessToast('사용자가 삭제되었습니다.')
        
        // Remove from selected rows
        const index = selectedRows.value.findIndex(row => row === id)
        if (index !== -1) {
          selectedRows.value.splice(index, 1)
        }
        
        await fetchUsers() // Refresh list
      } else {
        handleApiError({ response: { data: response } })
      }
    } catch (error) {
      handleApiError(error)
    } finally {
      isSubmitting.value = false
    }
  }
}

//  Search users
const searchUsers = async () => {
  try {
    isLoading.value = true
    clearErrors()

    if (searchQuery.value.trim()) {
      const response = await userApi.searchUsers(searchQuery.value)
      
      if (response.success && response.data) {
        users.value = response.data
      } else {
        handleApiError({ response: { data: response } })
      }
    } else {
      await fetchUsers()
    }
  } catch (error) {
    handleApiError(error)
  } finally {
    isLoading.value = false
  }
}

//  Watchers
watch(searchQuery, async () => {
  await searchUsers()
})

watch([page, itemsPerPage], async () => {
  await fetchUsers()
})

//  Widget data
const widgetData = computed(() => {
  const allUsers = users.value || []
  const totalUsersCount = allUsers.length
  const adminCount = allUsers.filter(user => user.role === 'ADMIN').length
  const userCount = allUsers.filter(user => user.role === 'USER').length
  
  return [
    { title: 'Total Users', value: totalUsersCount.toString(), change: 0, desc: 'All registered users', icon: 'bx-group', iconColor: 'primary' },
    { title: 'Administrators', value: adminCount.toString(), change: 0, desc: 'Admin users', icon: 'bx-crown', iconColor: 'error' },
    { title: 'Regular Users', value: userCount.toString(), change: 0, desc: 'Standard users', icon: 'bx-user', iconColor: 'success' },
    { title: 'Loading', value: isLoading.value ? 'Yes' : 'No', change: 0, desc: 'Current status', icon: 'bx-loader-alt', iconColor: 'warning' },
  ]
})
</script>

<template>
  <section>
    <!--  Widgets -->
    <div class="d-flex mb-6">
      <VRow>
        <template
          v-for="(data, id) in widgetData"
          :key="id"
        >
          <VCol
            cols="12"
            md="3"
            sm="6"
          >
            <VCard>
              <VCardText>
                <div class="d-flex justify-space-between">
                  <div class="d-flex flex-column gap-y-1">
                    <div class="text-body-1 text-high-emphasis">
                      {{ data.title }}
                    </div>
                    <div class="d-flex gap-x-2 align-center">
                      <h4 class="text-h4">
                        {{ data.value }}
                      </h4>
                      <div
                        class="text-base"
                        :class="data.change > 0 ? 'text-success' : 'text-error'"
                      >
                        ({{ prefixWithPlus(data.change) }}%)
                      </div>
                    </div>
                    <div class="text-sm">
                      {{ data.desc }}
                    </div>
                  </div>
                  <VAvatar
                    :color="data.iconColor"
                    variant="tonal"
                    rounded
                    size="40"
                  >
                    <VIcon
                      :icon="data.icon"
                      size="24"
                    />
                  </VAvatar>
                </div>
              </VCardText>
            </VCard>
          </VCol>
        </template>
      </VRow>
    </div>

    <VCard class="mb-6">
      <VCardItem class="pb-4">
        <VCardTitle>Filters</VCardTitle>
      </VCardItem>

      <VCardText>
        <VRow>
          <!--  Select Role -->
          <VCol
            cols="12"
            sm="4"
          >
            <AppSelect
              v-model="selectedRole"
              placeholder="Select Role"
              :items="roles"
              clearable
              clear-icon="bx-x"
            />
          </VCol>
        </VRow>
      </VCardText>

      <VDivider />

      <VCardText class="d-flex flex-wrap gap-4">
        <div class="me-3 d-flex gap-3">
          <AppSelect
            :model-value="itemsPerPage"
            :items="[
              { value: 10, title: '10' },
              { value: 25, title: '25' },
              { value: 50, title: '50' },
              { value: 100, title: '100' },
              { value: -1, title: 'All' },
            ]"
            style="inline-size: 6.25rem;"
            @update:model-value="itemsPerPage = parseInt($event, 10)"
          />
        </div>
        <VSpacer />

        <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
          <!--  Search  -->
          <div style="inline-size: 15.625rem;">
            <AppTextField
              v-model="searchQuery"
              placeholder="Search User"
            />
          </div>

          <!--  Export button -->
          <VBtn
            variant="tonal"
            color="secondary"
            prepend-icon="bx-export"
          >
            Export
          </VBtn>

          <!--  Add user button -->
          <VBtn
            prepend-icon="bx-plus"
            @click="isAddNewUserDrawerVisible = true"
          >
            Add New User
          </VBtn>
        </div>
      </VCardText>
      <VDivider />

      <!-- SECTION datatable -->
      <VDataTableServer
        v-model:items-per-page="itemsPerPage"
        v-model:model-value="selectedRows"
        v-model:page="page"
        :items="users"
        :loading="isLoading"
        item-value="id"
        :items-length="totalUsers"
        :headers="headers"
        class="text-no-wrap"
        show-select
        @update:options="updateOptions"
        @click:row="(event, { item }) => $router.push({ name: 'apps-user-view-id', params: { id: item.id } })"
      >
        <!-- User -->
        <template #item.user="{ item }">
          <div class="d-flex align-center gap-x-4">
            <VAvatar
              size="34"
              variant="tonal"
              :color="resolveUserRoleVariant(item.role).color"
            >
              <span>{{ avatarText(item.name) }}</span>
            </VAvatar>
            <div class="d-flex flex-column">
              <h6 class="text-base">
                <RouterLink
                  :to="{ name: 'apps-user-view-id', params: { id: item.id } }"
                  class="font-weight-medium text-link"
                >
                  {{ item.name }}
                </RouterLink>
              </h6>
              <div class="text-sm text-medium-emphasis">
                {{ item.userid }}
              </div>
            </div>
          </div>
        </template>

        <!--  Role -->
        <template #item.role="{ item }">
          <div class="d-flex align-center gap-x-2">
            <VIcon
              :size="20"
              :icon="resolveUserRoleVariant(item.role).icon"
              :color="resolveUserRoleVariant(item.role).color"
            />

            <div class="text-capitalize text-high-emphasis text-body-1">
              {{ item.role }}
            </div>
          </div>
        </template>

        <!-- Created At -->
        <template #item.createdAt="{ item }">
          <div class="text-body-2">
            {{ new Date(item.createdAt).toLocaleDateString() }}
          </div>
        </template>

        <!-- Actions -->
        <template #item.actions="{ item }">
          <IconBtn @click="deleteUser(item.id)">
            <VIcon icon="bx-trash" />
          </IconBtn>

          <IconBtn @click="$router.push({ name: 'apps-user-view-id', params: { id: item.id } })">
            <VIcon icon="bx-show" />
          </IconBtn>

          <VBtn
            icon
            variant="text"
            color="medium-emphasis"
          >
            <VIcon icon="bx-dots-vertical-rounded" />
            <VMenu activator="parent">
              <VList>
                <VListItem :to="{ name: 'apps-user-view-id', params: { id: item.id } }">
                  <template #prepend>
                    <VIcon icon="bx-show" />
                  </template>

                  <VListItemTitle>View</VListItemTitle>
                </VListItem>

                <VListItem @click="$router.push({ name: 'apps-user-view-id', params: { id: item.id } })">
                  <template #prepend>
                    <VIcon icon="bx-pencil" />
                  </template>
                  <VListItemTitle>Edit</VListItemTitle>
                </VListItem>
              </VList>
            </VMenu>
          </VBtn>
        </template>

        <!-- pagination -->
        <template #bottom>
          <TablePagination
            v-model:page="page"
            :items-per-page="itemsPerPage"
            :total-items="totalUsers"
          />
        </template>
      </VDataTableServer>
      <!-- SECTION -->
    </VCard>
    <!--  Add New User -->
    <AddNewUserDrawer
      v-model:is-drawer-open="isAddNewUserDrawerVisible"
      @user-data="addNewUser"
    />

    <!--  Error Dialog -->
    <ErrorDialog />

    <!--  Toast Container -->
    <ToastContainer />
  </section>
</template>
