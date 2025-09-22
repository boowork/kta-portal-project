<script setup lang="ts">
import { useTheme } from 'vuetify'
import { useAuthStore } from '@/stores/auth'
import ScrollToTop from '@core/components/ScrollToTop.vue'
import initCore from '@core/initCore'
import { initConfigStore, useConfigStore } from '@core/stores/config'
import { hexToRgb } from '@core/utils/colorConverter'

const { global } = useTheme()
const router = useRouter()
const authStore = useAuthStore()

// ℹ️ Sync current theme with initial loader theme
initCore()
initConfigStore()

const configStore = useConfigStore()

// Initialize auth state
authStore.initAuth()

// Route guard
router.beforeEach((to, from, next) => {
  const publicPages = ['/login', '/register', '/forgot-password']
  const authRequired = !publicPages.includes(to.path)

  if (authRequired && !authStore.isAuthenticated) {
    // Redirect to login if not authenticated
    next('/login')
  }
  else if (to.path === '/login' && authStore.isAuthenticated) {
    // Redirect to dashboard if already authenticated and trying to access login
    next('/dashboards/analytics')
  }
  else {
    next()
  }
})
</script>

<template>
  <VLocaleProvider :rtl="configStore.isAppRTL">
    <!-- ℹ️ This is required to set the background color of active nav link based on currently active global theme's primary -->
    <VApp :style="`--v-global-theme-primary: ${hexToRgb(global.current.value.colors.primary)}`">
      <RouterView />

      <ScrollToTop />
    </VApp>
  </VLocaleProvider>
</template>
