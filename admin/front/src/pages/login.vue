<script setup lang="ts">
import { useAuthStore } from '@/stores/auth'
import { useErrorHandler } from '@/composables/useErrorHandler'
import ToastContainer from '@/components/ToastContainer.vue'
import AuthProvider from '@/views/pages/authentication/AuthProvider.vue'
import { VNodeRenderer } from '@layouts/components/VNodeRenderer'
import { themeConfig } from '@themeConfig'

import authV2LoginIllustration from '@images/pages/auth-v2-login-illustration.png'

definePage({
  meta: {
    layout: 'blank',
    public: true,
  },
})

const router = useRouter()
const authStore = useAuthStore()
const { errorState, getFieldError, clearErrors } = useErrorHandler()

const form = ref({
  userid: '',
  password: '',
  remember: false,
})

const isPasswordVisible = ref(false)

// Handle login
const handleLogin = async () => {
  if (!form.value.userid || !form.value.password)
    return

  clearErrors()

  const success = await authStore.login({
    userid: form.value.userid,
    password: form.value.password,
  })

  if (success) {
    // Redirect to dashboard
    router.push('/dashboards/analytics')
  }
}
</script>

<template>
  <a href="javascript:void(0)">
    <div class="auth-logo d-flex align-center gap-x-2">
      <VNodeRenderer :nodes="themeConfig.app.logo" />
      <h1 class="auth-title">
        {{ themeConfig.app.title }}
      </h1>
    </div>
  </a>

  <VRow
    no-gutters
    class="auth-wrapper bg-surface"
  >
    <VCol
      md="8"
      class="d-none d-md-flex"
    >
      <!-- illustration -->
      <div class="position-relative bg-background w-100 pa-8">
        <div class="d-flex align-center justify-center w-100 h-100">
          <VImg
            max-width="700"
            :src="authV2LoginIllustration"
            class="auth-illustration"
          />
        </div>
      </div>
    </VCol>

    <VCol
      cols="12"
      md="4"
      class="auth-card-v2 d-flex align-center justify-center"
    >
      <VCard
        flat
        :max-width="500"
        class="mt-12 mt-sm-0 pa-6"
      >
        <VCardText>
          <h4 class="text-h4 mb-1">
            Welcome to
            <span class="text-capitalize">{{ themeConfig.app.title }}</span>! 👋🏻
          </h4>
          <p class="mb-0">
            Please sign-in to your account and start the adventure
          </p>
        </VCardText>
        <VCardText>
          <VForm @submit.prevent="handleLogin">
            <VRow>
              <!-- userid -->
              <VCol cols="12">
                <AppTextField
                  v-model="form.userid"
                  autofocus
                  label="User ID"
                  type="text"
                  placeholder="admin"
                  :error-messages="getFieldError('userid')"
                />
              </VCol>

              <!-- password -->
              <VCol cols="12">
                <AppTextField
                  v-model="form.password"
                  label="Password"
                  placeholder="············"
                  :type="isPasswordVisible ? 'text' : 'password'"
                  autocomplete="current-password"
                  :append-inner-icon="isPasswordVisible ? 'bx-hide' : 'bx-show'"
                  :error-messages="getFieldError('password')"
                  @click:append-inner="isPasswordVisible = !isPasswordVisible"
                />

                <div class="d-flex align-center flex-wrap justify-space-between my-6">
                  <VCheckbox
                    v-model="form.remember"
                    label="Remember me"
                  />
                  <a
                    class="text-primary"
                    href="javascript:void(0)"
                  >
                    Forgot Password?
                  </a>
                </div>

                <!-- Login Error Message -->
                <VAlert
                  v-if="errorState.hasError && errorState.message"
                  type="error"
                  variant="tonal"
                  class="mb-4"
                  closable
                  @click:close="clearErrors"
                >
                  {{ errorState.message }}
                </VAlert>

                <VBtn
                  block
                  type="submit"
                  :loading="authStore.isLoading"
                  :disabled="authStore.isLoading"
                >
                  Login
                </VBtn>
              </VCol>

              <!-- create account -->
              <VCol
                cols="12"
                class="text-body-1 text-center"
              >
                <span class="d-inline-block"> New on our platform? </span>
                <a
                  class="text-primary ms-1 d-inline-block text-body-1"
                  href="javascript:void(0)"
                >
                  Create an account
                </a>
              </VCol>

              <VCol
                cols="12"
                class="d-flex align-center"
              >
                <VDivider />
                <span class="mx-4">or</span>
                <VDivider />
              </VCol>

              <!-- auth providers -->
              <VCol
                cols="12"
                class="text-center"
              >
                <AuthProvider />
              </VCol>
            </VRow>
          </VForm>
        </VCardText>
      </VCard>
    </VCol>
  </VRow>

  <!--  Toast Container -->
  <ToastContainer />
</template>

<style lang="scss">
@use "@core/scss/template/pages/page-auth";
</style>
