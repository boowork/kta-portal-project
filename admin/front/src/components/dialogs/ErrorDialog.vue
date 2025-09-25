<script setup lang="ts">
import { useErrorHandler } from '@/composables/useErrorHandler'

const { isDialogVisible, dialogTitle, dialogMessage } = useErrorHandler()
</script>

<template>
  <VDialog
    v-model="isDialogVisible"
    width="500"
    @update:model-value="isDialogVisible = $event"
  >
    <!-- Dialog close btn -->
    <DialogCloseBtn @click="isDialogVisible = false" />

    <!-- Dialog Content -->
    <VCard :title="dialogTitle">
      <VCardText>
        {{ dialogMessage }}
      </VCardText>

      <VCardText class="d-flex justify-end">
        <VBtn
          color="error"
          @click="isDialogVisible = false"
        >
          확인
        </VBtn>
      </VCardText>
    </VCard>
  </VDialog>
</template>
