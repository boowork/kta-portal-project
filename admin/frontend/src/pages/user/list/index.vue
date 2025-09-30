<script setup lang="ts">
import { useUserList } from './composables'
import AddNewUserDrawer from './AddNewUserDrawer.vue'
import ErrorDialog from '@/components/dialogs/ErrorDialog.vue'
import ToastContainer from '@/components/ToastContainer.vue'
import type { CreateUserRequest, User } from '@/api/types'

const {
  users, totalUsers, isLoading, isSubmitting, searchQuery, selectedRows,
  itemsPerPage, page, sortBy, orderBy,
  fetchUsers, searchUsers, createUser, deleteUser, updateOptions,
  resolveUserAvatarVariant,
} = useUserList()

const headers = [
  { title: 'User', key: 'user' },
  { title: 'Created At', key: 'createdAt' },
  { title: 'Actions', key: 'actions', sortable: false },
]

const isAddNewUserDrawerVisible = ref(false)

const addNewUser = async (userData: CreateUserRequest) => {
  const success = await createUser(userData)
  if (success) {
    isAddNewUserDrawerVisible.value = false
  }
}

const handleDeleteUser = async (id: number) => {
  const user = users.value.find((u: User) => u.id === id)
  const success = await deleteUser(id, user?.name)

  if (success) {
    const index = selectedRows.value.findIndex((row: number) => row === id)
    if (index !== -1)
      selectedRows.value.splice(index, 1)
  }
}

// 단일 watch로 모든 상태 변경 감지
watch([searchQuery, page, itemsPerPage, sortBy, orderBy], async () => {
  console.log('👀 Data fetch triggered by watch')
  if (searchQuery.value.trim()) {
    await searchUsers()
  } else {
    await fetchUsers()
  }
})
</script>

<template>
  <section>
    <VCard class="mb-6">
      <VCardItem class="pb-4">
        <VCardTitle>Filters</VCardTitle>
      </VCardItem>

      <VCardText>
        <VRow>
          <!-- Filters will be added here if needed -->
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
        :items="users || []"
        :loading="isLoading"
        item-value="id"
        :items-length="totalUsers"
        :headers="headers"
        class="text-no-wrap"
        show-select
        @update:options="updateOptions"
        @click:row="(_: any, { item }: { item: User }) => $router.push({ name: 'user-view-id', params: { id: item.id } })"
      >
        <!-- User -->
        <template #item.user="{ item }">
          <div class="d-flex align-center gap-x-4">
            <VAvatar
              size="34"
              variant="tonal"
              :color="resolveUserAvatarVariant().color"
            >
              <span>{{ avatarText(item.name) }}</span>
            </VAvatar>
            <div class="d-flex flex-column">
              <h6 class="text-base">
                <RouterLink
                  :to="{ name: 'user-view-id', params: { id: item.id } }"
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

        <!-- Created At -->
        <template #item.createdAt="{ item }">
          <div class="text-body-2">
            {{ new Date(item.createdAt).toLocaleDateString() }}
          </div>
        </template>

        <!-- Actions -->
        <template #item.actions="{ item }">
          <IconBtn @click="handleDeleteUser(item.id)">
            <VIcon icon="bx-trash" />
          </IconBtn>

          <IconBtn @click="$router.push({ name: 'user-view-id', params: { id: item.id } })">
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
                <VListItem :to="{ name: 'user-view-id', params: { id: item.id } }">
                  <template #prepend>
                    <VIcon icon="bx-show" />
                  </template>

                  <VListItemTitle>View</VListItemTitle>
                </VListItem>

                <VListItem @click="$router.push({ name: 'user-view-id', params: { id: item.id } })">
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
