import { api } from './index'
import type { 
  ApiResponse, 
  LoginRequest, 
  LoginResponse, 
  RefreshTokenRequest, 
  RefreshTokenResponse 
} from './types'

export const authApi = {
  /**
   * 사용자 로그인
   */
  async login(credentials: LoginRequest): Promise<ApiResponse<LoginResponse>> {
    const response = await api.post<ApiResponse<LoginResponse>>('/login', credentials)
    return response.data
  },

  /**
   * 사용자 로그아웃
   */
  async logout(): Promise<ApiResponse<null>> {
    const response = await api.post<ApiResponse<null>>('/logout')
    return response.data
  },

  /**
   * Access Token 갱신
   */
  async refreshToken(refreshTokenData: RefreshTokenRequest): Promise<ApiResponse<RefreshTokenResponse>> {
    const response = await api.post<ApiResponse<RefreshTokenResponse>>('/refresh', refreshTokenData)
    return response.data
  },

  /**
   * 토큰 검증
   */
  async verifyToken(): Promise<ApiResponse<{ valid: boolean }>> {
    const response = await api.get<ApiResponse<{ valid: boolean }>>('/verify')
    return response.data
  },
}