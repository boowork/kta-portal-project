<script setup lang="ts">
import { useUserDetail } from './composables'
import { avatarText } from '@core/utils/formatters'
import ErrorDialog from '@/components/dialogs/ErrorDialog.vue'
import ToastContainer from '@/components/ToastContainer.vue'

const route = useRoute('apps-user-view-id')
const router = useRouter()

const {
  // State
  user,
  isLoading,
  isSubmitting,
  isEditing,
  editForm,

  // Methods
  fetchUser,
  updateUser,
  deleteUser,
  cancelEdit,
  resolveUserAvatarVariant,
} = useUserDetail()

const loadUser = async () => {
  await fetchUser(Number(route.params.id))
}

const handleUpdateUser = async () => {
  await updateUser()
}

const handleDeleteUser = async () => {
  const success = await deleteUser()
  if (success)
    router.push('/apps/user/list')
}

onMounted(loadUser)
</script>

<template>
  <div>
    <!-- Loading state -->
    <VRow v-if="isLoading">
      <VCol cols="12">
        <VCard>
          <VCardText class="text-center">
            <VProgressCircular
              indeterminate
              color="primary"
            />
            <div class="mt-4">
              사용자 정보를 불러오는 중...
            </div>
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
                :color="resolveUserAvatarVariant().color"
              >
                <span class="text-2xl">
                  {{ avatarText(user.name) }}
                </span>
              </VAvatar>
              <div>
                <h2 class="text-h4">
                  {{ user.name }}
                </h2>
                <div class="text-body-1 text-medium-emphasis">
                  {{ user.userid }}
                </div>
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
                  @click="handleDeleteUser"
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
              <VList class="card-list text-medium-emphasis">
                <VListItem>
                  <VListItemTitle>
                    <span class="d-flex align-center">
                      <VIcon
                        icon="bx-user"
                        size="20"
                        class="me-2"
                      />
                      <div class="text-body-1 font-weight-medium me-2">이름:</div>
                      <div>{{ user.name }}</div>
                    </span>
                  </VListItemTitle>
                </VListItem>
                
                <VListItem>
                  <VListItemTitle>
                    <span class="d-flex align-center">
                      <VIcon
                        icon="bx-id-card"
                        size="20"
                        class="me-2"
                      />
                      <div class="text-body-1 font-weight-medium me-2">사용자 ID:</div>
                      <div>{{ user.userid }}</div>
                    </span>
                  </VListItemTitle>
                </VListItem>
                
                <VListItem>
                  <VListItemTitle>
                    <span class="d-flex align-center">
                      <VIcon
                        icon="bx-calendar-plus"
                        size="20"
                        class="me-2"
                      />
                      <div class="text-body-1 font-weight-medium me-2">생성일:</div>
                      <div>{{ new Date(user.createdAt).toLocaleDateString() }}</div>
                    </span>
                  </VListItemTitle>
                </VListItem>
                
                <VListItem v-if="user.updatedAt">
                  <VListItemTitle>
                    <span class="d-flex align-center">
                      <VIcon
                        icon="bx-calendar-edit"
                        size="20"
                        class="me-2"
                      />
                      <div class="text-body-1 font-weight-medium me-2">수정일:</div>
                      <div>{{ new Date(user.updatedAt).toLocaleDateString() }}</div>
                    </span>
                  </VListItemTitle>
                </VListItem>
              </VList>
            </div>

            <!-- Edit Mode -->
            <div v-else>
              <VForm @submit.prevent="handleUpdateUser">
                <VRow>
                  <VCol
                    cols="12"
                    md="6"
                  >
                    <h6 class="text-h6 mb-4">
                      사용자 정보 편집
                    </h6>

                    <AppTextField
                      v-model="editForm.name"
                      label="이름"
                      placeholder="사용자 이름을 입력하세요"
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

<style lang="scss" scoped>
.card-list {
  --v-card-list-gap: 16px;
}
</style>
