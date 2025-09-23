import { defineStore } from 'pinia'
import { useUser } from '@/composables/useUser'
import type { User, CreateUserRequest, UpdateUserRequest } from '@/api/types'

export const useUserStore = defineStore('user', () => {
  const userComposable = useUser()

  // Re-export user management state and methods from composable
  const isLoading = userComposable.isLoading
  const isSubmitting = userComposable.isSubmitting
  const users = userComposable.users
  const selectedUser = userComposable.selectedUser
  const currentPage = userComposable.currentPage
  const itemsPerPage = userComposable.itemsPerPage
  const totalItems = userComposable.totalItems
  const searchQuery = userComposable.searchQuery

  // Computed values
  const totalPages = userComposable.totalPages
  const hasUsers = userComposable.hasUsers
  const hasNextPage = userComposable.hasNextPage
  const hasPrevPage = userComposable.hasPrevPage

  // User management methods
  const fetchUsers = userComposable.fetchUsers
  const fetchUser = userComposable.fetchUser
  const createUser = userComposable.createUser
  const updateUser = userComposable.updateUser
  const deleteUser = userComposable.deleteUser
  const searchUsers = userComposable.searchUsers
  const changePage = userComposable.changePage
  const changePageSize = userComposable.changePageSize
  const changeSort = userComposable.changeSort
  const selectUser = userComposable.selectUser
  const resetState = userComposable.resetState

  // Additional store-specific methods
  const getUserById = (id: number): User | undefined => {
    if (!users.value || !Array.isArray(users.value)) return undefined
    return users.value.find(user => user.id === id)
  }

  const getUserByUserid = (userid: string): User | undefined => {
    if (!users.value || !Array.isArray(users.value)) return undefined
    return users.value.find(user => user.userid === userid)
  }

  const getFilteredUsers = (role?: 'ADMIN' | 'USER'): User[] => {
    if (!users.value || !Array.isArray(users.value)) return []
    if (!role) return users.value
    return users.value.filter(user => user.role === role)
  }

  return {
    // State
    isLoading,
    isSubmitting,
    users,
    selectedUser,
    currentPage,
    itemsPerPage,
    totalItems,
    searchQuery,

    // Computed
    totalPages,
    hasUsers,
    hasNextPage,
    hasPrevPage,

    // Methods
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

    // Store-specific methods
    getUserById,
    getUserByUserid,
    getFilteredUsers,
  }
})