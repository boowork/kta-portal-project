<script setup lang="ts">
interface Props {
  isDialogVisible: boolean
  title?: string
  message: string
  confirmText?: string
  cancelText?: string
}

interface Emit {
  (e: 'update:isDialogVisible', value: boolean): void
  (e: 'confirm'): void
}

const props = withDefaults(defineProps<Props>(), {
  title: '확인',
  confirmText: '삭제',
  cancelText: '취소',
})

const emit = defineEmits<Emit>()

const updateModelValue = (val: boolean) => {
  emit('update:isDialogVisible', val)
}

const onConfirm = () => {
  emit('confirm')
  updateModelValue(false)
}

const onCancel = () => {
  updateModelValue(false)
}
</script>

<template>
  <VDialog
    max-width="500"
    :model-value="props.isDialogVisible"
    @update:model-value="updateModelValue"
  >
    <VCard>
      <VCardTitle class="text-h5 pa-6 pb-4">
        {{ props.title }}
      </VCardTitle>

      <VCardText class="px-6 pb-2">
        {{ props.message }}
      </VCardText>

      <VCardActions class="pa-6 pt-4">
        <VSpacer />
        <VBtn
          color="secondary"
          variant="outlined"
          @click="onCancel"
        >
          {{ props.cancelText }}
        </VBtn>
        <VBtn
          color="error"
          variant="elevated"
          @click="onConfirm"
        >
          {{ props.confirmText }}
        </VBtn>
      </VCardActions>
    </VCard>
  </VDialog>
</template>
