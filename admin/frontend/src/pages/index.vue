<script setup lang="ts">
// Redirect to dashboard
const router = useRouter()

onMounted(() => {
  router.replace('/dashboards/analytics')
})
</script>

<template>
  <div>
    <VCard
      class="mb-6"
      title="Redirecting to Dashboard..."
    >
      <VCardText>
        <VProgressCircular
          indeterminate
          color="primary"
          class="me-3"
        />
        Loading dashboard...
      </VCardText>
    </VCard>

    <VCard title="Want to integrate JWT? 🔒">
      <VCardText>We carefully crafted JWT flow so you can implement JWT with ease and with minimum efforts.</VCardText>
      <VCardText>Please read our JWT Documentation to get more out of JWT authentication.</VCardText>
    </VCard>
  </div>
</template>
