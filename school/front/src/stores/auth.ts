import { defineStore } from 'pinia'

export interface User {
  id: number
  email: string
  name: string
  role: string
}

export const useAuthStore = defineStore('auth', () => {
  const user = ref<User | null>(null)
  const isAuthenticated = ref(false)
  const token = ref<string | null>(null)

  // Initialize from localStorage
  const initAuth = () => {
    const savedToken = localStorage.getItem('auth_token')
    const savedUser = localStorage.getItem('auth_user')

    if (savedToken && savedUser) {
      token.value = savedToken
      user.value = JSON.parse(savedUser)
      isAuthenticated.value = true
    }
  }

  // Login function - accepts any id/password
  const login = async (email: string, password: string): Promise<boolean> => {
    try {
      // Simulate API call delay
      await new Promise(resolve => setTimeout(resolve, 1000))

      // Accept any credentials for demo purposes
      if (email && password) {
        const mockUser: User = {
          id: 1,
          email,
          name: email.split('@')[0] || 'User',
          role: 'admin',
        }

        const mockToken = `mock_token_${Date.now()}`

        // Save to state
        user.value = mockUser
        token.value = mockToken
        isAuthenticated.value = true

        // Save to localStorage
        localStorage.setItem('auth_token', mockToken)
        localStorage.setItem('auth_user', JSON.stringify(mockUser))

        return true
      }

      return false
    }
    catch (error) {
      console.error('Login failed:', error)

      return false
    }
  }

  // Logout function
  const logout = () => {
    user.value = null
    token.value = null
    isAuthenticated.value = false

    // Clear localStorage
    localStorage.removeItem('auth_token')
    localStorage.removeItem('auth_user')
  }

  return {
    user: readonly(user),
    isAuthenticated: readonly(isAuthenticated),
    token: readonly(token),
    initAuth,
    login,
    logout,
  }
})
