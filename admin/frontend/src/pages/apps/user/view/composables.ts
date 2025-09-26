import { ref } from 'vue'
import { userApi } from '@/api/user'
import { useErrorHandler } from '@/composables/useErrorHandler'
import type { UpdateUserRequest, User } from '@/api/types'

export const useUserDetail = () => {
  const { handleApiError, showSuccessToast, clearErrors } = useErrorHandler()

  // State - all initialized with default values
  const user = ref<User | null>(null)
  const isLoading = ref<boolean>(false)
  const isSubmitting = ref<boolean>(false)
  const isEditing = ref<boolean>(false)

  // Form - initialized with default values
  const editForm = ref<{ name: string; password: string }>({
    name: '',
    password: '',
  })

  // API calls
  const fetchUser = async (id: number) => {
    try {
      isLoading.value = true
      clearErrors()

      const response = await userApi.getUser(id)

      if (response.success && response.data) {
        user.value = response.data
        editForm.value = {
          name: response.data.name,
          password: '',
        }

        return response.data
      }
      else {
        handleApiError({ response: { data: response } })

        return null
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

  const updateUser = async () => {
    if (!user.value)
      return null

    try {
      isSubmitting.value = true
      clearErrors()

      const updateData: Partial<UpdateUserRequest> = {
        name: editForm.value.name,
      }

      if (editForm.value.password.trim())
        updateData.password = editForm.value.password

      const response = await userApi.updateUser(user.value.id, updateData)

      if (response.success && response.data) {
        showSuccessToast('사용자 정보가 수정되었습니다.')
        user.value = response.data
        isEditing.value = false

        return response.data
      }
      else {
        handleApiError({ response: { data: response } })

        return null
      }
    }
    catch (error) {
      handleApiError(error)

      return null
    }
    finally {
      isSubmitting.value = false
    }
  }

  const deleteUser = async () => {
    if (!user.value)
      return false

    if (confirm('정말로 이 사용자를 삭제하시겠습니까?')) {
      try {
        isSubmitting.value = true

        const response = await userApi.deleteUser(user.value.id)

        if (response.success) {
          showSuccessToast('사용자가 삭제되었습니다.')

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
  const cancelEdit = () => {
    if (user.value) {
      editForm.value = {
        name: user.value.name,
        password: '',
      }
    }
    isEditing.value = false
  }

  const resolveUserAvatarVariant = () => ({ color: 'primary' as const, icon: 'bx-user' })

  return {
    // State refs - direct return for proper reactivity
    user,
    isLoading,
    isSubmitting,
    isEditing,
    editForm,

    // Actions
    fetchUser,
    updateUser,
    deleteUser,
    cancelEdit,

    // UI helpers
    resolveUserAvatarVariant,
  }
}
