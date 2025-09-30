<script setup lang="ts">
import { usePartnerList } from './composables'
import AddNewPartnerDrawer from './AddNewPartnerDrawer.vue'
import ConfirmDialog from '@/components/dialogs/ConfirmDialog.vue'
import ErrorDialog from '@/components/dialogs/ErrorDialog.vue'
import ToastContainer from '@/components/ToastContainer.vue'
import type { CreatePartnerRequest, Partner } from '@/api/types'

const {
  partners, totalPartners, isLoading, isSubmitting, searchQuery, selectedRows,
  itemsPerPage, page, sortBy,
  fetchPartners, searchPartners, createPartner, deletePartner, updateOptions,
  resolvePartnerStatusVariant,
} = usePartnerList()

const headers = [
  { title: '파트너', key: 'partner' },
  { title: '상태', key: 'status' },
  { title: '생성일', key: 'createdAt' },
  { title: '작업', key: 'actions', sortable: false },
]

const isAddNewPartnerDrawerVisible = ref(false)
const isDeleteDialogVisible = ref(false)
const partnerToDelete = ref<Partner | null>(null)

const addNewPartner = async (partnerData: CreatePartnerRequest) => {
  const success = await createPartner(partnerData)
  if (success) {
    isAddNewPartnerDrawerVisible.value = false
  }
}

const handleDeletePartner = (id: string) => {
  const partner = partners.value.find((p: Partner) => p.id === id)
  if (partner) {
    partnerToDelete.value = partner
    isDeleteDialogVisible.value = true
  }
}

const confirmDelete = async () => {
  if (!partnerToDelete.value) return
  
  const success = await deletePartner(partnerToDelete.value.id, partnerToDelete.value.partnerName)

  if (success) {
    const index = selectedRows.value.findIndex((row: string) => row === partnerToDelete.value?.id)
    if (index !== -1)
      selectedRows.value.splice(index, 1)
    
    partnerToDelete.value = null
  }
}

// Initial data fetch on component mount
onMounted(async () => {
  console.log('Component mounted - fetching initial partners')
  await fetchPartners()
})

// 단일 watch로 모든 상태 변경 감지
watch([searchQuery, page, itemsPerPage, sortBy], async () => {
  console.log('👀 Data fetch triggered by watch')
  if (searchQuery.value.trim()) {
    await searchPartners()
  } else {
    await fetchPartners()
  }
})
</script>

<template>
  <section>
    <VCard
      title="파트너 목록"
      class="mb-6"
    >
      <VCardText>
        <VRow>
          <!-- Search filter -->
          <VCol
            cols="12"
            md="9"
          >
            <AppTextField
              v-model="searchQuery"
              placeholder="파트너 검색"
              clearable
              prepend-inner-icon="bx-search"
            />
          </VCol>

          <!-- Add button -->
          <VCol
            cols="12"
            md="3"
            class="text-right"
          >
            <VBtn
              block
              @click="isAddNewPartnerDrawerVisible = true"
            >
              파트너 추가
            </VBtn>
          </VCol>
        </VRow>
      </VCardText>

      <VDataTableServer
        v-model="selectedRows"
        v-model:items-per-page="itemsPerPage"
        v-model:page="page"
        v-model:sort-by="sortBy"
        :headers="headers"
        :items="partners"
        :items-length="totalPartners"
        :loading="isLoading"
        show-select
        class="text-no-wrap rounded-0"
        @update:options="updateOptions"
      >
        <!-- Partner column -->
        <template #item.partner="{ item }">
          <div class="d-flex align-center gap-x-3">
            <VAvatar
              size="34"
              color="primary"
              variant="tonal"
            >
              <span class="text-sm">{{ item.partnerName.substring(0, 2).toUpperCase() }}</span>
            </VAvatar>
            <div class="d-flex flex-column">
              <span class="text-sm text-high-emphasis">
                {{ item.partnerName }}
              </span>
            </div>
          </div>
        </template>


        <!-- Status column -->
        <template #item.status="{ item }">
          <VChip
            :color="resolvePartnerStatusVariant(item.isActive)"
            density="comfortable"
          >
            {{ item.isActive ? '활성' : '비활성' }}
          </VChip>
        </template>

        <!-- Created At column -->
        <template #item.createdAt="{ item }">
          <span class="text-sm">
            {{ new Date(item.createdAt).toLocaleDateString('ko-KR') }}
          </span>
        </template>

        <!-- Actions column -->
        <template #item.actions="{ item }">
          <IconBtn
            size="small"
            @click="handleDeletePartner(item.id)"
          >
            <VIcon
              size="22"
              icon="bx-trash"
            />
          </IconBtn>
        </template>

        <!-- Loading slot -->
        <template #loading>
          <VSkeletonLoader
            v-for="i in itemsPerPage"
            :key="i"
            type="table-row-divider"
          />
        </template>

        <!-- pagination -->
        <template #bottom>
          <TablePagination
            v-model:page="page"
            :items-per-page="itemsPerPage"
            :total-items="totalPartners"
          />
        </template>
      </VDataTableServer>
    </VCard>

    <!-- Add New Partner Drawer -->
    <AddNewPartnerDrawer
      v-model:isDrawerOpen="isAddNewPartnerDrawerVisible"
      @partner-data="addNewPartner"
    />

    <!-- Delete Confirmation Dialog -->
    <ConfirmDialog
      v-model:isDialogVisible="isDeleteDialogVisible"
      title="파트너 삭제"
      :message="`정말로 파트너 '${partnerToDelete?.partnerName}'을(를) 삭제하시겠습니까?`"
      confirm-text="삭제"
      cancel-text="취소"
      @confirm="confirmDelete"
    />

    <!-- Error Dialog -->
    <ErrorDialog />

    <!-- Toast Container -->
    <ToastContainer />
  </section>
</template>