<script setup lang="ts">
import { useErrorHandler } from '@/composables/useErrorHandler'

const { toastState } = useErrorHandler()
</script>

<template>
  <VSnackbar
    v-model="toastState.show"
    :color="toastState.color"
    location="top right"
    :timeout="toastState.color === 'success' ? 3000 : 5000"
  >
    {{ toastState.message }}
    
    <template #actions>
      <VBtn
        color="white"
        variant="text"
        @click="toastState.show = false"
      >
        닫기
      </VBtn>
    </template>
  </VSnackbar>
</template>