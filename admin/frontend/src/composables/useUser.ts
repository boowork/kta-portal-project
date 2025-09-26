import { computed, reactive, ref } from 'vue'
import { useErrorHandler } from './useErrorHandler'
import { userApi } from '@/api/user'
import type {
  CreateUserRequest,
  PaginationParams,
  UpdateUserRequest,
  User,
} from '@/api/types'

interface UserState {
  users: User[]
  selectedUser: User | null
  currentPage: number
  itemsPerPage: number
  totalItems: number
  searchQuery: string
  sortBy: string
  sortOrder: 'ASC' | 'DESC'
}

export const useUser = () => {
  const { handleApiError, showSuccessToast, clearErrors } = useErrorHandler()

  const isLoading = ref(false)
  const isSubmitting = ref(false)

  const state = reactive<UserState>({
    users: [],
    selectedUser: null,
    currentPage: 1,
    itemsPerPage: 10,
    totalItems: 0,
    searchQuery: '',
    sortBy: 'createdAt',
    sortOrder: 'DESC',
  })

  // Computed
  const totalPages = computed(() => Math.ceil(state.totalItems / state.itemsPerPage))
  const hasUsers = computed(() => state.users.length > 0)
  const hasNextPage = computed(() => state.currentPage < totalPages.value)
  const hasPrevPage = computed(() => state.currentPage > 1)

  /**
   * 사용자 목록 조회
   */
  const fetchUsers = async (params?: Partial<PaginationParams>) => {
    try {
      clearErrors()
      isLoading.value = true

      const requestParams: PaginationParams = {
        page: state.currentPage,
        size: state.itemsPerPage,
        sort: state.sortBy,
        direction: state.sortOrder,
        ...params,
      }

      const response = await userApi.getUsers(requestParams)

      if (response.success && response.data) {
        state.users = response.data
        state.totalItems = response.data.length
      }
    }
    catch (error) {
      handleApiError(error)
    }
    finally {
      isLoading.value = false
    }
  }

  /**
   * 특정 사용자 조회
   */
  const fetchUser = async (id: number) => {
    try {
      clearErrors()
      isLoading.value = true

      const response = await userApi.getUser(id)

      if (response.success && response.data) {
        state.selectedUser = response.data

        return response.data
      }
    }
    catch (error) {
      handleApiError(error)

      return null
    }
    finally {
      isLoading.value = false
    }
  }

  /**
   * 사용자 생성
   */
  const createUser = async (userData: CreateUserRequest): Promise<boolean> => {
    try {
      clearErrors()
      isSubmitting.value = true

      const response = await userApi.createUser(userData)

      if (response.success && response.data) {
        showSuccessToast('사용자가 생성되었습니다.')

        // 목록 새로고침
        await fetchUsers()

        return true
      }
      else {
        handleApiError({ response: { data: response } })

        return false
      }
    }
    catch (error) {
      handleApiError(error)

      return false
    }
    finally {
      isSubmitting.value = false
    }
  }

  /**
   * 사용자 정보 수정
   */
  const updateUser = async (id: number, userData: Partial<UpdateUserRequest>): Promise<boolean> => {
    try {
      clearErrors()
      isSubmitting.value = true

      const response = await userApi.updateUser(id, userData)

      if (response.success && response.data) {
        showSuccessToast('사용자 정보가 수정되었습니다.')

        // 목록에서 해당 사용자 정보 업데이트
        const index = state.users.findIndex(user => user.id === id)
        if (index !== -1)
          state.users[index] = response.data

        // 선택된 사용자가 수정된 사용자라면 업데이트
        if (state.selectedUser?.id === id)
          state.selectedUser = response.data

        return true
      }
      else {
        handleApiError({ response: { data: response } })

        return false
      }
    }
    catch (error) {
      handleApiError(error)

      return false
    }
    finally {
      isSubmitting.value = false
    }
  }

  /**
   * 사용자 삭제
   */
  const deleteUser = async (id: number): Promise<boolean> => {
    try {
      clearErrors()
      isSubmitting.value = true

      const response = await userApi.deleteUser(id)

      if (response.success) {
        showSuccessToast('사용자가 삭제되었습니다.')

        // 목록에서 제거
        state.users = state.users.filter(user => user.id !== id)

        // 선택된 사용자가 삭제된 사용자라면 초기화
        if (state.selectedUser?.id === id)
          state.selectedUser = null

        return true
      }
      else {
        handleApiError({ response: { data: response } })

        return false
      }
    }
    catch (error) {
      handleApiError(error)

      return false
    }
    finally {
      isSubmitting.value = false
    }
  }

  /**
   * 사용자 검색
   */
  const searchUsers = async (query: string) => {
    try {
      clearErrors()
      isLoading.value = true

      state.searchQuery = query

      if (query.trim()) {
        const response = await userApi.searchUsers(query, {
          page: state.currentPage,
          size: state.itemsPerPage,
          sort: state.sortBy,
          direction: state.sortOrder,
        })

        if (response.success && response.data)
          state.users = response.data
      }
      else {
        // 검색어가 없으면 전체 목록 조회
        await fetchUsers()
      }
    }
    catch (error) {
      handleApiError(error)
    }
    finally {
      isLoading.value = false
    }
  }

  /**
   * 페이지 변경
   */
  const changePage = async (page: number) => {
    state.currentPage = page

    if (state.searchQuery.trim())
      await searchUsers(state.searchQuery)
    else
      await fetchUsers()
  }

  /**
   * 페이지 크기 변경
   */
  const changePageSize = async (size: number) => {
    state.itemsPerPage = size
    state.currentPage = 1

    if (state.searchQuery.trim())
      await searchUsers(state.searchQuery)
    else
      await fetchUsers()
  }

  /**
   * 정렬 변경
   */
  const changeSort = async (sortBy: string, sortOrder: 'ASC' | 'DESC' = 'ASC') => {
    state.sortBy = sortBy
    state.sortOrder = sortOrder
    state.currentPage = 1

    if (state.searchQuery.trim())
      await searchUsers(state.searchQuery)
    else
      await fetchUsers()
  }

  /**
   * 선택된 사용자 설정
   */
  const selectUser = (user: User | null) => {
    state.selectedUser = user
  }

  /**
   * 상태 초기화
   */
  const resetState = () => {
    state.users = []
    state.selectedUser = null
    state.currentPage = 1
    state.searchQuery = ''
  }

  return {
    // 상태
    isLoading: readonly(isLoading),
    isSubmitting: readonly(isSubmitting),
    users: readonly(state.users),
    selectedUser: readonly(state.selectedUser),
    currentPage: readonly(state.currentPage),
    itemsPerPage: readonly(state.itemsPerPage),
    totalItems: readonly(state.totalItems),
    searchQuery: readonly(state.searchQuery),

    // Computed
    totalPages,
    hasUsers,
    hasNextPage,
    hasPrevPage,

    // 메서드
    fetchUsers,
    fetchUser,
    createUser,
    updateUser,
    deleteUser,
    searchUsers,
    changePage,
    changePageSize,
    changeSort,
    selectUser,
    resetState,
  }
}
