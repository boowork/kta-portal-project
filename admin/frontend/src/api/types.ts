// Common API Response Types
export interface ApiResponse<T> {
  success: boolean
  data?: T
  message?: string
  errors?: ApiError[]
  timestamp?: string
}

export interface ApiError {
  field?: string
  message: string
  code: string
}

// Authentication Types
export interface LoginRequest {
  userid: string
  password: string
}

export interface LoginResponse {
  accessToken: string
  refreshToken: string
  userid: string
  name: string
}

export interface RefreshTokenRequest {
  refreshToken: string
}

export interface RefreshTokenResponse {
  accessToken: string
}

// User Management Types
export interface User {
  id: number
  userid: string
  name: string
  createdAt: string
  updatedAt: string
}

export interface CreateUserRequest {
  userid: string
  name: string
  password: string
}

export interface UpdateUserRequest {
  name?: string
  password?: string
}

export interface GetUsersResponse {
  content: User[]
  page: number
  size: number
  totalElements: number
  totalPages: number
  first: boolean
  last: boolean
}

// Pagination Types
export interface PaginationParams {
  page?: number
  size?: number
  sortBy?: string
  sortDir?: 'asc' | 'desc'
}

// Error Codes
export enum ErrorCode {
  VALIDATION_ERROR = 'VALIDATION_ERROR',
  UNAUTHORIZED = 'UNAUTHORIZED',
  FORBIDDEN = 'FORBIDDEN',
  NOT_FOUND = 'NOT_FOUND',
  INVALID_CREDENTIALS = 'INVALID_CREDENTIALS',
  INVALID_TOKEN = 'INVALID_TOKEN',
  INTERNAL_SERVER_ERROR = 'INTERNAL_SERVER_ERROR',
}

// Partner Management Types
export interface Partner {
  id: string
  partnerName: string
  isActive: boolean
  createdAt: string
}

export interface CreatePartnerRequest {
  partnerName: string
  isActive?: boolean
}

export interface UpdatePartnerRequest {
  partnerName?: string
  isActive?: boolean
}

export interface GetPartnersResponse {
  content: Partner[]
  page: number
  size: number
  totalElements: number
  totalPages: number
  first: boolean
  last: boolean
}

// Form Validation Types
export interface ValidationError {
  field: string
  message: string
}

export interface FormState {
  isSubmitting: boolean
  errors: ValidationError[]
}
