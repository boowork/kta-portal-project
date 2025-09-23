import { defineStore } from 'pinia'
import { useAuth } from '@/composables/useAuth'
import type { LoginResponse } from '@/api/types'

export interface AuthUser {
  userid: string
  name: string
  role: 'ADMIN' | 'USER'
}

export const useAuthStore = defineStore('auth', () => {
  const authComposable = useAuth()

  // Re-export authentication state and methods from composable
  const user = computed<AuthUser | null>(() => {
    if (!authComposable.user.value) return null
    
    return {
      userid: authComposable.user.value.userid,
      name: authComposable.user.value.name,
      role: authComposable.user.value.role,
    }
  })

  const isAuthenticated = authComposable.isAuthenticated
  const isLoading = authComposable.isLoading
  const userRole = authComposable.userRole
  const userName = authComposable.userName
  const isAdmin = authComposable.isAdmin

  // Authentication methods
  const login = authComposable.login
  const logout = authComposable.logout
  const refreshToken = authComposable.refreshToken
  const initAuth = authComposable.initAuth
  const verifyToken = authComposable.verifyToken
  const checkAuth = authComposable.checkAuth

  return {
    // State
    user,
    isAuthenticated,
    isLoading,
    userRole,
    userName,
    isAdmin,

    // Methods
    login,
    logout,
    refreshToken,
    initAuth,
    verifyToken,
    checkAuth,
  }
})
