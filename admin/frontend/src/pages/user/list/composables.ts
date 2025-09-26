import { computed, ref } from 'vue'
import { userApi } from '@/api/user'
import { useErrorHandler } from '@/composables/useErrorHandler'
import type { CreateUserRequest, PaginationParams, User } from '@/api/types'

export const useUserList = () => {
  const { handleApiError, showSuccessToast, clearErrors } = useErrorHandler()

  // State - all initialized with default values
  const users = ref<User[]>([])
  const totalUsers = ref<number>(0)
  const isLoading = ref<boolean>(false)
  const isSubmitting = ref<boolean>(false)
  const searchQuery = ref<string>('')
  const selectedRows = ref<number[]>([])

  // Pagination - all initialized with default values
  const itemsPerPage = ref<number>(10)
  const page = ref<number>(1)
  const sortBy = ref<string>('id')
  const orderBy = ref<'asc' | 'desc'>('asc')

  // API calls
  const fetchUsers = async () => {
    console.log('🔥 fetchUsers called from:', new Error().stack)
    try {
      isLoading.value = true
      clearErrors()

      const params: PaginationParams = {
        page: page.value - 1,
        size: itemsPerPage.value,
        sortBy: sortBy.value || 'id',
        sortDir: orderBy.value === 'desc' ? 'desc' : 'asc',
      }

      const response = await userApi.getUsers(params)

      if (response.success && response.data) {
        users.value = response.data.content
        totalUsers.value = response.data.totalElements
      }
      else {
        handleApiError({ response: { data: response } })
      }
    }
    catch (error) {
      handleApiError(error)
    }
    finally {
      isLoading.value = false
    }
  }

  const searchUsers = async () => {
    console.log('🔍 searchUsers called from:', new Error().stack)
    
    try {
      isLoading.value = true
      clearErrors()

      const response = await userApi.searchUsers(searchQuery.value)

      if (response.success && response.data) {
        users.value = response.data.content
        totalUsers.value = response.data.totalElements
      }
      else {
        handleApiError({ response: { data: response } })
      }
    }
    catch (error) {
      handleApiError(error)
    }
    finally {
      isLoading.value = false
    }
  }

  const createUser = async (userData: CreateUserRequest) => {
    try {
      isSubmitting.value = true
      clearErrors()

      const response = await userApi.createUser(userData)

      if (response.success) {
        showSuccessToast('사용자가 생성되었습니다.')
        // watch가 자동으로 목록을 새로고침하므로 제거
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

  const deleteUser = async (id: number, userName?: string) => {
    const displayName = userName || `ID ${id}`

    if (confirm(`정말로 "${displayName}" 사용자를 삭제하시겠습니까?`)) {
      try {
        isSubmitting.value = true

        const response = await userApi.deleteUser(id)

        if (response.success) {
          showSuccessToast('사용자가 삭제되었습니다.')
          // watch가 자동으로 목록을 새로고침하므로 제거
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

    return false
  }

  // UI helpers
  const updateOptions = (options: any) => {
    console.log('⚙️ updateOptions called from:', new Error().stack)
    const newSortBy = options.sortBy[0]?.key
    const newOrderBy = options.sortBy[0]?.order
    
    // 값이 실제로 변경된 경우에만 업데이트
    if (newSortBy !== sortBy.value || newOrderBy !== orderBy.value) {
      sortBy.value = newSortBy
      orderBy.value = newOrderBy
    }
  }

  const resolveUserAvatarVariant = () => ({ color: 'primary' as const, icon: 'bx-user' })

  // Widget data - always initialized with safe fallbacks
  const widgetData = computed(() => {
    try {
      return [
        { title: 'Total Users', value: totalUsers.value?.toString() || '0', change: 0, desc: 'All registered users', icon: 'bx-group', iconColor: 'primary' },
        { title: 'Current Page', value: users.value?.length?.toString() || '0', change: 0, desc: 'Users on current page', icon: 'bx-user', iconColor: 'success' },
        { title: 'Page Number', value: page.value?.toString() || '1', change: 0, desc: 'Current page number', icon: 'bx-file', iconColor: 'info' },
        { title: 'Loading', value: isLoading.value ? 'Yes' : 'No', change: 0, desc: 'Current status', icon: 'bx-loader-alt', iconColor: 'warning' },
      ]
    }
    catch (error) {
      console.error('Error in widgetData computed:', error)

      return []
    }
  })

  return {
    // State refs - direct return for proper reactivity
    users,
    totalUsers,
    isLoading,
    isSubmitting,
    searchQuery,
    selectedRows,

    // Pagination refs
    itemsPerPage,
    page,
    sortBy,
    orderBy,

    // Actions
    fetchUsers,
    searchUsers,
    createUser,
    deleteUser,
    updateOptions,

    // UI helpers
    resolveUserAvatarVariant,
    widgetData,
  }
}
