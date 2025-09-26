import axios, { type AxiosResponse } from 'axios'
import type { ApiResponse } from './types'

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json',
  },
})

// Request interceptor
axiosInstance.interceptors.request.use(
  config => {
    const accessToken = useCookie('accessToken').value
    if (accessToken)
      config.headers.Authorization = `Bearer ${accessToken}`

    return config
  },
  error => {
    return Promise.reject(error)
  },
)

// Response interceptor
axiosInstance.interceptors.response.use(
  (response: AxiosResponse<ApiResponse<any>>) => {
    return response
  },
  async error => {
    const originalRequest = error.config

    // Handle 401 (Unauthorized) - attempt token refresh
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true

      try {
        const refreshToken = useCookie('refreshToken').value
        if (refreshToken) {
          const response = await axios.post('/api/refresh', {
            refreshToken,
          })

          if (response.data.success) {
            const { accessToken } = response.data.data

            useCookie('accessToken').value = accessToken

            // Retry original request with new token
            originalRequest.headers.Authorization = `Bearer ${accessToken}`

            return axiosInstance(originalRequest)
          }
        }
      }
      catch (refreshError) {
        // Refresh failed, redirect to login
        useCookie('accessToken').value = null
        useCookie('refreshToken').value = null

        // Navigate to login page
        await navigateTo('/login')
      }
    }

    // Handle 403 (Forbidden) - insufficient permissions, redirect to login
    if (error.response?.status === 403) {
      useCookie('accessToken').value = null
      useCookie('refreshToken').value = null

      // Navigate to login page
      await navigateTo('/login')
    }

    return Promise.reject(error)
  },
)

export { axiosInstance as api }
export default axiosInstance
