import { ref, reactive } from 'vue'
import type { ApiError, ValidationError, ErrorCode } from '@/api/types'

export interface ErrorState {
  hasError: boolean
  message: string
  fieldErrors: Record<string, string>
}

export interface ToastState {
  show: boolean
  message: string
  color: 'success' | 'error' | 'info' | 'warning'
}

// Global state for error handling
const globalErrorState = reactive<ErrorState>({
  hasError: false,
  message: '',
  fieldErrors: {},
})

const globalToastState = reactive<ToastState>({
  show: false,
  message: '',
  color: 'info',
})

const globalIsDialogVisible = ref(false)
const globalDialogTitle = ref('오류')
const globalDialogMessage = ref('')

export const useErrorHandler = () => {

  /**
   * 에러 상태 초기화
   */
  const clearErrors = () => {
    globalErrorState.hasError = false
    globalErrorState.message = ''
    globalErrorState.fieldErrors = {}
  }

  /**
   * 필드별 에러 설정
   */
  const setFieldError = (field: string, message: string) => {
    globalErrorState.fieldErrors[field] = message
    globalErrorState.hasError = true
  }

  /**
   * 필드별 에러 가져오기
   */
  const getFieldError = (field: string): string | undefined => {
    return globalErrorState.fieldErrors[field]
  }

  /**
   * API 에러 처리
   */
  const handleApiError = (error: any, showDialog = false) => {
    clearErrors()

    if (error.response?.data) {
      const apiResponse = error.response.data
      
      if (apiResponse.errors && Array.isArray(apiResponse.errors)) {
        const hasFieldErrors = apiResponse.errors.some((err: ApiError) => err.field && err.field.trim() !== '')

        if (hasFieldErrors) {
          // 필드 레벨 에러는 입력 필드 하단에 표시
          apiResponse.errors.forEach((err: ApiError) => {
            if (err.field && err.field.trim() !== '') {
              setFieldError(err.field, err.message)
            }
          })
        }
        
        // field가 비어있는 에러나 일반 에러는 공통 메시지로 처리
        const generalErrors = apiResponse.errors.filter((err: ApiError) => !err.field || err.field.trim() === '')
        if (generalErrors.length > 0) {
          globalErrorState.message = generalErrors[0].message
          globalErrorState.hasError = true
        }
        
        // 일반 에러가 있고 다이얼로그 표시가 요청된 경우
        if (generalErrors.length > 0 && showDialog) {
          showErrorDialog('오류', generalErrors[0].message)
        }
      } else if (apiResponse.message) {
        globalErrorState.message = apiResponse.message
        globalErrorState.hasError = true
        
        if (showDialog) {
          showErrorDialog('오류', apiResponse.message)
        } else {
          showErrorToast(apiResponse.message)
        }
      }
    } else {
      // 네트워크 에러나 기타 에러
      const defaultMessage = '네트워크 오류가 발생했습니다. 다시 시도해주세요.'
      globalErrorState.message = defaultMessage
      globalErrorState.hasError = true
      
      if (showDialog) {
        showErrorDialog('네트워크 오류', defaultMessage)
      } else {
        showErrorToast(defaultMessage)
      }
    }
  }

  /**
   * HTTP 상태 코드별 에러 처리
   */
  const handleHttpError = (status: number, showDialog = false) => {
    let message = '알 수 없는 오류가 발생했습니다.'
    
    switch (status) {
      case 400:
        message = '잘못된 요청입니다.'
        break
      case 401:
        message = '인증이 필요합니다. 다시 로그인해주세요.'
        // 로그인 페이지로 리다이렉트
        navigateTo('/login')
        return
      case 403:
        message = '접근 권한이 없습니다.'
        break
      case 404:
        message = '요청한 리소스를 찾을 수 없습니다.'
        break
      case 500:
        message = '서버 내부 오류가 발생했습니다.'
        break
      case 503:
        message = '서비스를 일시적으로 사용할 수 없습니다.'
        break
    }

    if (showDialog) {
      showErrorDialog('오류', message)
    } else {
      showErrorToast(message)
    }
  }

  /**
   * 에러 다이얼로그 표시
   */
  const showErrorDialog = (title: string, message: string) => {
    globalDialogTitle.value = title
    globalDialogMessage.value = message
    globalIsDialogVisible.value = true
  }

  /**
   * 에러 토스트 표시
   */
  const showErrorToast = (message: string) => {
    globalToastState.message = message
    globalToastState.color = 'error'
    globalToastState.show = true
    
    // Auto hide after 5 seconds
    setTimeout(() => {
      globalToastState.show = false
    }, 5000)
  }

  /**
   * 성공 토스트 표시
   */
  const showSuccessToast = (message: string) => {
    globalToastState.message = message
    globalToastState.color = 'success'
    globalToastState.show = true
    
    // Auto hide after 3 seconds
    setTimeout(() => {
      globalToastState.show = false
    }, 3000)
  }

  /**
   * 정보 토스트 표시
   */
  const showInfoToast = (message: string) => {
    globalToastState.message = message
    globalToastState.color = 'info'
    globalToastState.show = true
    
    // Auto hide after 4 seconds
    setTimeout(() => {
      globalToastState.show = false
    }, 4000)
  }

  /**
   * 폼 검증 에러 처리
   */
  const handleValidationErrors = (errors: ValidationError[]) => {
    clearErrors()
    
    errors.forEach(error => {
      setFieldError(error.field, error.message)
    })
  }

  return {
    // 상태
    errorState: readonly(globalErrorState),
    toastState: readonly(globalToastState),
    isDialogVisible: globalIsDialogVisible,
    dialogTitle: readonly(globalDialogTitle),
    dialogMessage: readonly(globalDialogMessage),

    // 메서드
    clearErrors,
    setFieldError,
    getFieldError,
    handleApiError,
    handleHttpError,
    showErrorDialog,
    showErrorToast,
    showSuccessToast,
    showInfoToast,
    handleValidationErrors,
  }
}