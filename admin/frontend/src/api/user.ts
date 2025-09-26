import type {
  ApiResponse,
  CreateUserRequest,
  GetUsersResponse,
  PaginationParams,
  UpdateUserRequest,
  User,
} from './types'
import { api } from './index'

export const userApi = {
  /**
   * 사용자 목록 조회 (페이징 지원)
   */
  async getUsers(params?: PaginationParams): Promise<ApiResponse<GetUsersResponse>> {
    const response = await api.get<ApiResponse<GetUsersResponse>>('/users', { params })

    return response.data
  },

  /**
   * 특정 사용자 조회
   */
  async getUser(id: number): Promise<ApiResponse<User>> {
    const response = await api.get<ApiResponse<User>>(`/users/${id}`)

    return response.data
  },

  /**
   * 사용자 생성
   */
  async createUser(userData: CreateUserRequest): Promise<ApiResponse<User>> {
    const response = await api.post<ApiResponse<User>>('/users', userData)

    return response.data
  },

  /**
   * 사용자 정보 수정
   */
  async updateUser(id: number, userData: Partial<UpdateUserRequest>): Promise<ApiResponse<User>> {
    const response = await api.put<ApiResponse<User>>(`/users/${id}`, userData)

    return response.data
  },

  /**
   * 사용자 삭제
   */
  async deleteUser(id: number): Promise<ApiResponse<null>> {
    const response = await api.delete<ApiResponse<null>>(`/users/${id}`)

    return response.data
  },

  /**
   * 사용자 검색 (페이징 지원)
   */
  async searchUsers(query: string, params?: PaginationParams): Promise<ApiResponse<GetUsersResponse>> {
    const response = await api.get<ApiResponse<GetUsersResponse>>('/users/search', {
      params: { ...params, q: query },
    })

    return response.data
  },
}
