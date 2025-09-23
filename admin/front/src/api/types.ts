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
  role: 'ADMIN' | 'USER'
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
  role: 'ADMIN' | 'USER'
  createdAt: string
  updatedAt: string
}

export interface CreateUserRequest {
  userid: string
  name: string
  password: string
  role: 'ADMIN' | 'USER'
}

export interface UpdateUserRequest {
  userid?: string
  name?: string
  password?: string
  role?: 'ADMIN' | 'USER'
}

export interface GetUsersResponse {
  users: User[]
  total?: number
  page?: number
  size?: number
}

// Pagination Types
export interface PaginationParams {
  page?: number
  size?: number
  sort?: string
  direction?: 'ASC' | 'DESC'
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

// Form Validation Types
export interface ValidationError {
  field: string
  message: string
}

export interface FormState {
  isSubmitting: boolean
  errors: ValidationError[]
}