<script setup lang="ts">
import { PerfectScrollbar } from 'vue3-perfect-scrollbar'
import type { VForm } from 'vuetify/components/VForm'
import type { CreateUserRequest } from '@/api/types'

// Required validator
const requiredValidator = (value: string) => !!value || '이 필드는 필수입니다'

interface Emit {
  (e: 'update:isDrawerOpen', value: boolean): void
  (e: 'user-data', value: CreateUserRequest): void
}

interface Props {
  isDrawerOpen: boolean
}

const props = defineProps<Props>()
const emit = defineEmits<Emit>()

// Form fields
const isFormValid = ref(false)
const refForm = ref<VForm>()
const name = ref('')
const userid = ref('')
const password = ref('')
const role = ref<'ADMIN' | 'USER'>()

// Computed property for button state
const isSubmitDisabled = computed(() => {
  return !name.value?.trim() || !userid.value?.trim() || !password.value?.trim() || !role.value
})

// Role options
const roleOptions = [
  { title: 'Admin', value: 'ADMIN' as const },
  { title: 'User', value: 'USER' as const },
]

// Close drawer
const closeNavigationDrawer = () => {
  emit('update:isDrawerOpen', false)

  nextTick(() => {
    refForm.value?.reset()
    refForm.value?.resetValidation()
  })
}

// Submit form
const onSubmit = () => {
  refForm.value?.validate().then(({ valid }) => {
    if (valid) {
      emit('user-data', {
        userid: userid.value,
        name: name.value,
        password: password.value,
        role: role.value!,
      })
      
      closeNavigationDrawer()
    }
  })
}

// Handle drawer model value update
const handleDrawerModelValueUpdate = (val: boolean) => {
  emit('update:isDrawerOpen', val)
}
</script>

<template>
  <VNavigationDrawer
    temporary
    :width="400"
    location="end"
    border="none"
    class="scrollable-content"
    :model-value="props.isDrawerOpen"
    @update:model-value="handleDrawerModelValueUpdate"
  >
    <!-- Title -->
    <AppDrawerHeaderSection
      title="사용자 추가"
      @cancel="closeNavigationDrawer"
    />

    <VDivider />

    <PerfectScrollbar :options="{ wheelPropagation: false }">
      <VCard flat>
        <VCardText>
          <!-- Form -->
          <VForm
            ref="refForm"
            v-model="isFormValid"
            @submit.prevent="onSubmit"
          >
            <VRow>
              <!-- 이름 -->
              <VCol cols="12">
                <AppTextField
                  v-model="name"
                  :rules="[requiredValidator]"
                  label="이름"
                  placeholder="홍길동"
                />
              </VCol>

              <!-- 사용자 ID -->
              <VCol cols="12">
                <AppTextField
                  v-model="userid"
                  :rules="[requiredValidator]"
                  label="사용자 ID"
                  placeholder="hong123"
                />
              </VCol>

              <!-- 비밀번호 -->
              <VCol cols="12">
                <AppTextField
                  v-model="password"
                  :rules="[requiredValidator]"
                  label="비밀번호"
                  type="password"
                  placeholder="비밀번호를 입력하세요"
                />
              </VCol>

              <!-- 역할 -->
              <VCol cols="12">
                <AppSelect
                  v-model="role"
                  label="역할"
                  placeholder="역할을 선택하세요"
                  :rules="[requiredValidator]"
                  :items="roleOptions"
                />
              </VCol>

              <!-- Submit and Cancel -->
              <VCol cols="12">
                <VBtn
                  type="submit"
                  class="me-4"
                  :disabled="isSubmitDisabled"
                >
                  추가
                </VBtn>
                <VBtn
                  variant="tonal"
                  color="error"
                  @click="closeNavigationDrawer"
                >
                  취소
                </VBtn>
              </VCol>
            </VRow>
          </VForm>
        </VCardText>
      </VCard>
    </PerfectScrollbar>
  </VNavigationDrawer>
</template>