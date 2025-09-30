<script setup lang="ts">
import { PerfectScrollbar } from 'vue3-perfect-scrollbar'
import type { VForm } from 'vuetify/components/VForm'
import type { CreatePartnerRequest } from '@/api/types'

const props = defineProps<Props>()

const emit = defineEmits<Emit>()

// Required validator
const requiredValidator = (value: string) => !!value || '이 필드는 필수입니다'

interface Emit {
  (e: 'update:isDrawerOpen', value: boolean): void
  (e: 'partner-data', value: CreatePartnerRequest): void
}

interface Props {
  isDrawerOpen: boolean
}

// Form fields
const isFormValid = ref(false)
const refForm = ref<VForm>()
const partnerName = ref('')
const isActive = ref(true)

// Computed property for button state
const isSubmitDisabled = computed(() => {
  return !partnerName.value?.trim()
})

// Close drawer
const closeNavigationDrawer = () => {
  emit('update:isDrawerOpen', false)

  nextTick(() => {
    refForm.value?.reset()
    refForm.value?.resetValidation()
    isActive.value = true
  })
}

// Submit form
const onSubmit = () => {
  refForm.value?.validate().then(({ valid }) => {
    if (valid) {
      emit('partner-data', {
        partnerName: partnerName.value,
        isActive: isActive.value,
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
      title="파트너 추가"
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
              <!-- Partner Name -->
              <VCol cols="12">
                <AppTextField
                  v-model="partnerName"
                  :rules="[requiredValidator]"
                  label="파트너명"
                  placeholder="파트너명을 입력하세요"
                />
              </VCol>

              <!-- Status -->
              <VCol cols="12">
                <VSwitch
                  v-model="isActive"
                  label="활성 상태"
                  color="primary"
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
                  variant="outlined"
                  color="secondary"
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

<style lang="scss">
.scrollable-content {
  .v-navigation-drawer__content {
    overflow-y: auto;
  }
}
</style>