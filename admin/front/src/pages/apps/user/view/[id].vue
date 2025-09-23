<script setup lang="ts">
import ErrorDialog from '@/components/dialogs/ErrorDialog.vue'
import ToastContainer from '@/components/ToastContainer.vue'
import { useErrorHandler } from '@/composables/useErrorHandler'
import { userApi } from '@/api/user'
import type { User, UpdateUserRequest } from '@/api/types'

const route = useRoute('apps-user-view-id')
const router = useRouter()

// Error handler
const { handleApiError, showSuccessToast, clearErrors } = useErrorHandler()

// Component state
const user = ref<User | null>(null)
const isLoading = ref(false)
const isEditing = ref(false)
const isSubmitting = ref(false)

// Form data for editing
const editForm = ref({
  name: '',
  userid: '',
  password: '',
  role: ''
})

const roles = [
  { title: 'Admin', value: 'ADMIN' },
  { title: 'User', value: 'USER' },
]

// Fetch user data
const fetchUser = async () => {
  try {
    isLoading.value = true
    clearErrors()

    const response = await userApi.getUser(Number(route.params.id))
    
    if (response.success && response.data) {
      user.value = response.data
      // Initialize edit form with current data
      editForm.value = {
        name: response.data.name,
        userid: response.data.userid,
        password: '', // 비밀번호는 빈 값으로 시작
        role: response.data.role
      }
    } else {
      handleApiError({ response: { data: response } })
    }
  } catch (error) {
    handleApiError(error)
  } finally {
    isLoading.value = false
  }
}

// Update user
const updateUser = async () => {
  if (!user.value) return

  try {
    isSubmitting.value = true
    clearErrors()

    const updateData: Partial<UpdateUserRequest> = {
      name: editForm.value.name,
      userid: editForm.value.userid,
      role: editForm.value.role
    }

    // 비밀번호가 입력된 경우에만 포함
    if (editForm.value.password.trim()) {
      updateData.password = editForm.value.password
    }

    const response = await userApi.updateUser(user.value.id, updateData)
    
    if (response.success && response.data) {
      user.value = response.data
      isEditing.value = false
      showSuccessToast('사용자 정보가 수정되었습니다.')
    } else {
      handleApiError({ response: { data: response } })
    }
  } catch (error) {
    handleApiError(error)
  } finally {
    isSubmitting.value = false
  }
}

// Delete user with confirmation
const deleteUser = async () => {
  if (!user.value) return

  if (confirm('정말로 이 사용자를 삭제하시겠습니까?')) {
    try {
      isSubmitting.value = true

      const response = await userApi.deleteUser(user.value.id)
      
      if (response.success) {
        showSuccessToast('사용자가 삭제되었습니다.')
        router.push('/apps/user/list')
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

// Cancel editing
const cancelEdit = () => {
  if (user.value) {
    editForm.value = {
      name: user.value.name,
      userid: user.value.userid,
      password: '', // 비밀번호는 항상 빈 값으로 초기화
      role: user.value.role
    }
  }
  isEditing.value = false
}

// Resolve user role variant for display
const resolveUserRoleVariant = (role: string) => {
  const roleLowerCase = role.toLowerCase()

  if (roleLowerCase === 'admin')
    return { color: 'primary', icon: 'bx-crown' }
  if (roleLowerCase === 'user')
    return { color: 'success', icon: 'bx-user' }

  return { color: 'primary', icon: 'bx-user' }
}

// Initial fetch
await fetchUser()
</script>

<template>
  <div>
    <!-- Loading state -->
    <VRow v-if="isLoading">
      <VCol cols="12">
        <VCard>
          <VCardText class="text-center">
            <VProgressCircular indeterminate color="primary" />
            <div class="mt-4">사용자 정보를 불러오는 중...</div>
          </VCardText>
        </VCard>
      </VCol>
    </VRow>

    <!-- User found -->
    <VRow v-else-if="user">
      <VCol cols="12">
        <VCard>
          <VCardItem>
            <VCardTitle class="d-flex align-center gap-4">
              <VAvatar
                size="64"
                variant="tonal"
                :color="resolveUserRoleVariant(user.role).color"
              >
                <span class="text-2xl">{{ avatarText(user.name) }}</span>
              </VAvatar>
              <div>
                <h2 class="text-h4">{{ user.name }}</h2>
                <div class="text-body-1 text-medium-emphasis">{{ user.userid }}</div>
              </div>
              <VSpacer />
              <div class="d-flex gap-2">
                <VBtn
                  v-if="!isEditing"
                  color="primary"
                  variant="outlined"
                  prepend-icon="bx-edit"
                  @click="isEditing = true"
                >
                  편집
                </VBtn>
                <VBtn
                  color="error"
                  variant="outlined"
                  prepend-icon="bx-trash"
                  :loading="isSubmitting"
                  @click="deleteUser"
                >
                  삭제
                </VBtn>
                <VBtn
                  variant="outlined"
                  prepend-icon="bx-arrow-back"
                  @click="$router.push('/apps/user/list')"
                >
                  목록으로
                </VBtn>
              </div>
            </VCardTitle>
          </VCardItem>

          <VDivider />

          <VCardText>
            <!-- View Mode -->
            <div v-if="!isEditing">
              <VRow>
                <VCol cols="12" md="6">
                  <div class="mb-4">
                    <h6 class="text-h6 mb-2">사용자 정보</h6>
                    <VList>
                      <VListItem>
                        <VListItemTitle>이름</VListItemTitle>
                        <VListItemSubtitle>{{ user.name }}</VListItemSubtitle>
                      </VListItem>
                      <VListItem>
                        <VListItemTitle>사용자 ID</VListItemTitle>
                        <VListItemSubtitle>{{ user.userid }}</VListItemSubtitle>
                      </VListItem>
                      <VListItem>
                        <VListItemTitle>역할</VListItemTitle>
                        <VListItemSubtitle>
                          <div class="d-flex align-center gap-2">
                            <VIcon
                              :icon="resolveUserRoleVariant(user.role).icon"
                              :color="resolveUserRoleVariant(user.role).color"
                            />
                            {{ user.role }}
                          </div>
                        </VListItemSubtitle>
                      </VListItem>
                      <VListItem>
                        <VListItemTitle>생성일</VListItemTitle>
                        <VListItemSubtitle>{{ new Date(user.createdAt).toLocaleString() }}</VListItemSubtitle>
                      </VListItem>
                      <VListItem v-if="user.updatedAt">
                        <VListItemTitle>수정일</VListItemTitle>
                        <VListItemSubtitle>{{ new Date(user.updatedAt).toLocaleString() }}</VListItemSubtitle>
                      </VListItem>
                    </VList>
                  </div>
                </VCol>
              </VRow>
            </div>

            <!-- Edit Mode -->
            <div v-else>
              <VForm @submit.prevent="updateUser">
                <VRow>
                  <VCol cols="12" md="6">
                    <h6 class="text-h6 mb-4">사용자 정보 편집</h6>
                    
                    <AppTextField
                      v-model="editForm.name"
                      label="이름"
                      placeholder="사용자 이름을 입력하세요"
                      class="mb-4"
                      required
                    />

                    <AppTextField
                      v-model="editForm.userid"
                      label="사용자 ID"
                      placeholder="사용자 ID를 입력하세요"
                      class="mb-4"
                      required
                    />

                    <AppTextField
                      v-model="editForm.password"
                      label="새 비밀번호 (선택사항)"
                      type="password"
                      placeholder="새 비밀번호를 입력하세요 (변경하지 않으려면 비워두세요)"
                      class="mb-4"
                    />

                    <AppSelect
                      v-model="editForm.role"
                      label="역할"
                      placeholder="역할을 선택하세요"
                      :items="roles"
                      class="mb-4"
                      required
                    />

                    <div class="d-flex gap-2">
                      <VBtn
                        type="submit"
                        color="primary"
                        :loading="isSubmitting"
                      >
                        저장
                      </VBtn>
                      <VBtn
                        variant="outlined"
                        @click="cancelEdit"
                      >
                        취소
                      </VBtn>
                    </div>
                  </VCol>
                </VRow>
              </VForm>
            </div>
          </VCardText>
        </VCard>
      </VCol>
    </VRow>

    <!-- User not found -->
    <VRow v-else>
      <VCol cols="12">
        <VAlert
          type="error"
          variant="tonal"
        >
          ID {{ route.params.id }}인 사용자를 찾을 수 없습니다!
        </VAlert>
      </VCol>
    </VRow>

    <!-- Error Dialog -->
    <ErrorDialog />

    <!-- Toast Container -->
    <ToastContainer />
  </div>
</template>