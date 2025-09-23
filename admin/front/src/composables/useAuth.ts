import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { authApi } from '@/api/auth'
import { useErrorHandler } from './useErrorHandler'
import type { LoginRequest, LoginResponse } from '@/api/types'

export const useAuth = () => {
  const router = useRouter()
  const { handleApiError, showSuccessToast, clearErrors } = useErrorHandler()
  
  const isLoading = ref(false)
  const isAuthenticated = ref(false)
  const user = ref<LoginResponse | null>(null)

  // Computed
  const userRole = computed(() => user.value?.role)
  const userName = computed(() => user.value?.name)
  const isAdmin = computed(() => user.value?.role === 'ADMIN')

  /**
   * 토큰 저장
   */
  const saveTokens = (accessToken: string, refreshToken: string) => {
    const accessTokenCookie = useCookie('accessToken', {
      default: () => null,
      httpOnly: false,
      secure: true,
      sameSite: 'strict',
      maxAge: 60 * 60 * 24, // 24 hours
    })
    
    const refreshTokenCookie = useCookie('refreshToken', {
      default: () => null,
      httpOnly: false,
      secure: true,
      sameSite: 'strict',
      maxAge: 60 * 60 * 24 * 30, // 30 days
    })

    accessTokenCookie.value = accessToken
    refreshTokenCookie.value = refreshToken
  }

  /**
   * 토큰 제거
   */
  const removeTokens = () => {
    const accessTokenCookie = useCookie('accessToken')
    const refreshTokenCookie = useCookie('refreshToken')
    
    accessTokenCookie.value = null
    refreshTokenCookie.value = null
  }

  /**
   * 사용자 정보 저장
   */
  const saveUserInfo = (userData: LoginResponse) => {
    user.value = userData
    isAuthenticated.value = true
    
    // localStorage에도 저장 (새로고침 시 복원용)
    localStorage.setItem('user', JSON.stringify(userData))
  }

  /**
   * 사용자 정보 제거
   */
  const removeUserInfo = () => {
    user.value = null
    isAuthenticated.value = false
    localStorage.removeItem('user')
  }

  /**
   * 로그인
   */
  const login = async (credentials: LoginRequest): Promise<boolean> => {
    try {
      clearErrors()
      isLoading.value = true

      const response = await authApi.login(credentials)
      
      if (response.success && response.data) {
        const { accessToken, refreshToken, ...userData } = response.data
        
        // 토큰 저장
        saveTokens(accessToken, refreshToken)
        
        // 사용자 정보 저장
        saveUserInfo(response.data)
        
        showSuccessToast('로그인되었습니다.')
        
        // 대시보드로 이동
        await router.push('/dashboards/analytics')
        
        return true
      } else {
        handleApiError({ response: { data: response } })
        return false
      }
    } catch (error: any) {
      if (error.code === 'ERR_NETWORK' || error.message?.includes('fetch')) {
        handleApiError({
          response: {
            data: {
              success: false,
              errors: [{
                message: '백엔드 서버에 연결할 수 없습니다. 서버가 실행 중인지 확인해주세요.',
                code: 'NETWORK_ERROR'
              }]
            }
          }
        })
      } else {
        handleApiError(error)
      }
      return false
    } finally {
      isLoading.value = false
    }
  }

  /**
   * 로그아웃
   */
  const logout = async () => {
    try {
      isLoading.value = true
      
      await authApi.logout()
      
      // 토큰 및 사용자 정보 제거
      removeTokens()
      removeUserInfo()
      
      showSuccessToast('로그아웃되었습니다.')
      
      // 로그인 페이지로 이동
      await router.push('/login')
    } catch (error) {
      // 로그아웃 실패해도 로컬 정보는 제거
      removeTokens()
      removeUserInfo()
      
      await router.push('/login')
    } finally {
      isLoading.value = false
    }
  }

  /**
   * 토큰 갱신
   */
  const refreshToken = async (): Promise<boolean> => {
    try {
      const refreshTokenCookie = useCookie('refreshToken')
      
      if (!refreshTokenCookie.value) {
        return false
      }

      const response = await authApi.refreshToken({
        refreshToken: refreshTokenCookie.value,
      })

      if (response.success && response.data) {
        // 새로운 Access Token 저장
        const accessTokenCookie = useCookie('accessToken')
        accessTokenCookie.value = response.data.accessToken
        
        return true
      }
      
      return false
    } catch (error) {
      return false
    }
  }

  /**
   * 초기화 - 앱 시작 시 실행
   */
  const initAuth = () => {
    const accessTokenCookie = useCookie('accessToken')
    const savedUser = localStorage.getItem('user')

    if (accessTokenCookie.value && savedUser) {
      try {
        const userData = JSON.parse(savedUser) as LoginResponse
        saveUserInfo(userData)
      } catch (error) {
        console.error('Failed to parse saved user data:', error)
        removeTokens()
        removeUserInfo()
      }
    }
  }

  /**
   * 토큰 검증
   */
  const verifyToken = async (): Promise<boolean> => {
    try {
      const response = await authApi.verifyToken()
      return response.success && response.data?.valid === true
    } catch (error) {
      return false
    }
  }

  /**
   * 인증 확인
   */
  const checkAuth = async (): Promise<boolean> => {
    const accessTokenCookie = useCookie('accessToken')
    
    if (!accessTokenCookie.value) {
      return false
    }

    const isValid = await verifyToken()
    
    if (!isValid) {
      // Access Token이 만료된 경우 Refresh Token으로 갱신 시도
      const refreshed = await refreshToken()
      
      if (!refreshed) {
        // Refresh도 실패한 경우 로그아웃
        await logout()
        return false
      }
    }

    return true
  }

  /**
   * 페이지 레벨 인증 가드 - 인증되지 않은 경우 로그인 페이지로 리디렉션
   */
  const requireAuth = () => {
    if (!isAuthenticated.value) {
      router.push('/login')
      return false
    }
    return true
  }

  return {
    // 상태
    isLoading: readonly(isLoading),
    isAuthenticated: readonly(isAuthenticated),
    user: readonly(user),
    userRole,
    userName,
    isAdmin,

    // 메서드
    login,
    logout,
    refreshToken,
    initAuth,
    verifyToken,
    checkAuth,
    requireAuth,
  }
}