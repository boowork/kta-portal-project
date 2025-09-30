import { ref } from 'vue'
import { partnerApi } from '@/api/partner'
import { useErrorHandler } from '@/composables/useErrorHandler'
import type { CreatePartnerRequest, PaginationParams, Partner } from '@/api/types'

export const usePartnerList = () => {
  const { handleApiError, showSuccessToast, clearErrors } = useErrorHandler()

  // State - all initialized with default values
  const partners = ref<Partner[]>([])
  const totalPartners = ref<number>(0)
  const isLoading = ref<boolean>(false)
  const isSubmitting = ref<boolean>(false)
  const searchQuery = ref<string>('')
  const selectedRows = ref<string[]>([])

  // Pagination - all initialized with default values
  const itemsPerPage = ref<number>(10)
  const page = ref<number>(1)
  const sortBy = ref<any[]>([])

  // API calls
  const fetchPartners = async () => {
    try {
      isLoading.value = true
      clearErrors()

      const params: PaginationParams = {
        page: page.value - 1,
        size: itemsPerPage.value,
        sortBy: sortBy.value?.length > 0 ? sortBy.value[0].key : 'id',
        sortDir: sortBy.value?.length > 0 ? sortBy.value[0].order : 'asc',
      }

      const response = await partnerApi.getPartners(params)

      if (response.success && response.data) {
        partners.value = response.data.content
        totalPartners.value = response.data.totalElements
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

  const searchPartners = async () => {
    try {
      isLoading.value = true
      clearErrors()

      const response = await partnerApi.searchPartners(searchQuery.value)

      if (response.success && response.data) {
        partners.value = response.data.content
        totalPartners.value = response.data.totalElements
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

  const createPartner = async (partnerData: CreatePartnerRequest) => {
    try {
      isSubmitting.value = true
      clearErrors()

      const response = await partnerApi.createPartner(partnerData)

      if (response.success) {
        showSuccessToast('파트너가 생성되었습니다.')
        // Reload the partners list after successful creation
        await fetchPartners()
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

  const deletePartner = async (id: string, partnerName?: string) => {
    try {
      isSubmitting.value = true
      clearErrors()

      const response = await partnerApi.deletePartner(id)

      if (response.success) {
        showSuccessToast(`파트너 "${partnerName || id}"가 삭제되었습니다.`)
        // Reload the partners list after successful deletion
        await fetchPartners()
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

  const updateOptions = (options: any) => {
    if (options.page !== undefined)
      page.value = options.page

    if (options.itemsPerPage !== undefined)
      itemsPerPage.value = options.itemsPerPage

    if (options.sortBy) {
      sortBy.value = options.sortBy
    }

    // 이 함수는 watch가 자동으로 fetchPartners를 호출하므로
    // 여기서는 데이터를 가져오지 않습니다
  }

  // Utilities
  const resolvePartnerStatusVariant = (status: boolean) => {
    return status ? 'success' : 'secondary'
  }

  return {
    partners,
    totalPartners,
    isLoading,
    isSubmitting,
    searchQuery,
    selectedRows,
    itemsPerPage,
    page,
    sortBy,
    fetchPartners,
    searchPartners,
    createPartner,
    deletePartner,
    updateOptions,
    resolvePartnerStatusVariant,
  }
}
