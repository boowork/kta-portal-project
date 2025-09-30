import { api } from './index'
import type { ApiResponse, CreatePartnerRequest, GetPartnersResponse, PaginationParams, Partner, UpdatePartnerRequest } from './types'

export const partnerApi = {
  getPartners: async (params: PaginationParams = {}): Promise<ApiResponse<GetPartnersResponse>> => {
    const response = await api.get('/partners', { params })

    return response.data
  },

  getPartner: async (id: string): Promise<ApiResponse<Partner>> => {
    const response = await api.get(`/partners/${id}`)

    return response.data
  },

  createPartner: async (data: CreatePartnerRequest): Promise<ApiResponse<Partner>> => {
    const response = await api.post('/partners', data)

    return response.data
  },

  updatePartner: async (id: string, data: UpdatePartnerRequest): Promise<ApiResponse<Partner>> => {
    const response = await api.put(`/partners/${id}`, data)

    return response.data
  },

  deletePartner: async (id: string): Promise<ApiResponse<void>> => {
    const response = await api.delete(`/partners/${id}`)

    return response.data
  },

  searchPartners: async (query: string): Promise<ApiResponse<GetPartnersResponse>> => {
    const response = await api.get('/partners', { params: { q: query } })

    return response.data
  },
}