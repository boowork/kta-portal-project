<script setup lang="ts">
import UserBioPanel from '@/views/apps/user/view/UserBioPanel.vue'
import UserTabAccount from '@/views/apps/user/view/UserTabAccount.vue'
import UserTabSecurity from '@/views/apps/user/view/UserTabSecurity.vue'

const route = useRoute('apps-user-view-id')

const userTab = ref(null)

const tabs = [
  { icon: 'bx-user', title: 'Account' },
  { icon: 'bx-lock-alt', title: 'Security' },
]

// Mock user data - use store in real implementation
const userData = ref<any>({
  id: route.params.id,
  fullName: 'John Doe',
  firstName: 'John',
  lastName: 'Doe',
  email: 'john.doe@example.com',
  role: 'admin',
  status: 'active',
  avatar: '',
  country: 'USA',
  contact: '(555) 123-4567',
  company: 'Example Corp',
  currentPlan: 'enterprise',
  billing: 'Auto Debit',
})
</script>

<template>
  <VRow v-if="userData">
    <VCol
      cols="12"
      md="5"
      lg="4"
    >
      <UserBioPanel :user-data="userData" />
    </VCol>

    <VCol
      cols="12"
      md="7"
      lg="8"
    >
      <VTabs
        v-model="userTab"
        class="v-tabs-pill"
      >
        <VTab
          v-for="tab in tabs"
          :key="tab.icon"
        >
          <VIcon
            :size="18"
            :icon="tab.icon"
            class="me-1"
          />
          <span>{{ tab.title }}</span>
        </VTab>
      </VTabs>

      <VWindow
        v-model="userTab"
        class="mt-6 disable-tab-transition"
        :touch="false"
      >
        <VWindowItem>
          <UserTabAccount />
        </VWindowItem>

        <VWindowItem>
          <UserTabSecurity />
        </VWindowItem>
      </VWindow>
    </VCol>
  </VRow>
  <div v-else>
    <VAlert
      type="error"
      variant="tonal"
    >
      User with ID {{ route.params.id }} not found!
    </VAlert>
  </div>
</template>
