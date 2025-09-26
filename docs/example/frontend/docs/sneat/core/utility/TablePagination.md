# TablePagination.vue

## Overview
A standardized pagination component for data tables with page information display and navigation controls. Features responsive design and automatic page calculation.

## Props Interface

```typescript
interface Props {
  page: number          // Current page number (1-based)
  itemsPerPage: number  // Number of items per page
  totalItems: number    // Total number of items across all pages
}
```

## Events Emitted

```typescript
interface Emit {
  (e: 'update:page', value: number): void  // Page change event
}
```

## Slots Available
None - component has a fixed pagination layout.

## Global State Dependencies

### External Functions
- `paginationMeta()`: Utility function to generate pagination description text

## Internal Methods and Computed Properties

### Methods
- `updatePage(value: number)`: Emits page update event

## Component Dependencies

### Vuetify Components
- `VDivider`: Separates pagination from table content
- `VPagination`: Main pagination control
- `VCard`-like structure with flex layout

### Utility Functions
- `paginationMeta({ page, itemsPerPage }, totalItems)`: Generates pagination information text

## Usage Examples

### Basic Usage
```vue
<template>
  <div>
    <!-- Data table content -->
    <VDataTable
      :items="tableItems"
      :headers="headers"
    />
    
    <!-- Pagination -->
    <TablePagination
      :page="currentPage"
      :items-per-page="itemsPerPage"
      :total-items="totalItems"
      @update:page="currentPage = $event"
    />
  </div>
</template>

<script setup>
const currentPage = ref(1)
const itemsPerPage = ref(10)
const totalItems = ref(150)
const tableItems = ref([])
</script>
```

### With Data Fetching
```vue
<template>
  <div>
    <VDataTable
      :items="users"
      :loading="loading"
    />
    
    <TablePagination
      :page="pagination.page"
      :items-per-page="pagination.itemsPerPage"
      :total-items="pagination.totalItems"
      @update:page="handlePageChange"
    />
  </div>
</template>

<script setup>
const users = ref([])
const loading = ref(false)
const pagination = ref({
  page: 1,
  itemsPerPage: 20,
  totalItems: 0
})

const fetchUsers = async (page) => {
  loading.value = true
  try {
    const response = await api.get('/users', {
      params: {
        page: page,
        limit: pagination.value.itemsPerPage
      }
    })
    
    users.value = response.data.items
    pagination.value.totalItems = response.data.total
  } finally {
    loading.value = false
  }
}

const handlePageChange = async (newPage) => {
  pagination.value.page = newPage
  await fetchUsers(newPage)
}

// Initial load
onMounted(() => fetchUsers(1))
</script>
```

### With URL Synchronization
```vue
<template>
  <div>
    <VDataTable :items="items" />
    
    <TablePagination
      :page="currentPage"
      :items-per-page="itemsPerPage"
      :total-items="totalItems"
      @update:page="updateUrlAndFetch"
    />
  </div>
</template>

<script setup>
const route = useRoute()
const router = useRouter()

const currentPage = ref(Number(route.query.page) || 1)
const itemsPerPage = ref(20)
const totalItems = ref(0)

const updateUrlAndFetch = async (newPage) => {
  currentPage.value = newPage
  
  // Update URL
  await router.push({
    query: { ...route.query, page: newPage }
  })
  
  // Fetch new data
  await fetchData(newPage)
}
</script>
```

### Custom Items Per Page
```vue
<template>
  <div>
    <div class="d-flex align-center mb-4">
      <VSelect
        v-model="itemsPerPage"
        :items="[10, 25, 50, 100]"
        label="Items per page"
        class="me-4"
        style="max-width: 150px;"
        @update:model-value="handleItemsPerPageChange"
      />
    </div>
    
    <VDataTable :items="items" />
    
    <TablePagination
      :page="currentPage"
      :items-per-page="itemsPerPage"
      :total-items="totalItems"
      @update:page="handlePageChange"
    />
  </div>
</template>

<script setup>
const currentPage = ref(1)
const itemsPerPage = ref(25)
const totalItems = ref(0)

const handleItemsPerPageChange = () => {
  // Reset to first page when changing items per page
  currentPage.value = 1
  fetchData(1)
}
</script>
```

### With Search/Filtering
```vue
<template>
  <div>
    <VTextField
      v-model="searchQuery"
      label="Search"
      prepend-inner-icon="bx-search"
      @input="handleSearch"
    />
    
    <VDataTable :items="filteredItems" />
    
    <TablePagination
      :page="currentPage"
      :items-per-page="itemsPerPage"
      :total-items="totalFilteredItems"
      @update:page="handlePageChange"
    />
  </div>
</template>

<script setup>
const searchQuery = ref('')
const currentPage = ref(1)
const itemsPerPage = ref(20)
const filteredItems = ref([])
const totalFilteredItems = ref(0)

const handleSearch = useDebounceFn(async () => {
  currentPage.value = 1 // Reset to first page on search
  await fetchData()
}, 500)

const fetchData = async () => {
  const response = await api.get('/items', {
    params: {
      page: currentPage.value,
      limit: itemsPerPage.value,
      search: searchQuery.value
    }
  })
  
  filteredItems.value = response.data.items
  totalFilteredItems.value = response.data.total
}
</script>
```

## Pagination Information

### Meta Function
The `paginationMeta()` function generates descriptive text such as:
- "Showing 1 to 10 of 150 entries"
- "Showing 21 to 30 of 150 entries"
- "Showing 1 to 5 of 5 entries"

### Page Calculation
```javascript
const totalPages = Math.ceil(totalItems / itemsPerPage)
```

## Layout Structure

```
─────────────────────────────────────────────────── ← VDivider
                                                   
Showing 1 to 10 of 150 entries    [1] 2 3 ... 15   ← Pagination info and controls
                                                   
```

### Responsive Layout
- Flexbox layout with space-between alignment
- Responsive text/control stacking on small screens
- Proper spacing and alignment

## Styling Features

### Layout Classes
- `d-flex align-center justify-sm-space-between justify-center`
- `flex-wrap gap-3 px-6 py-3`
- Responsive behavior for different screen sizes

### Pagination Control
- Primary color for active page
- Responsive visible page count:
  - Mobile (xs): 1 visible page button
  - Desktop: Up to 5 visible page buttons
- Smart total visible calculation: `Math.min(Math.ceil(totalItems / itemsPerPage), 5)`

## Responsive Behavior

### Mobile Optimization
- Reduced visible page numbers on extra small screens
- Responsive text alignment (center on mobile, space-between on larger)
- Proper touch targets for mobile interaction

### Desktop Features
- Full pagination information display
- Multiple visible page numbers
- Optimal spacing and layout

## Accessibility Features

- Semantic navigation structure
- Proper ARIA attributes through VPagination
- Keyboard navigation support
- Clear visual hierarchy
- High contrast text and controls

## Performance Considerations

### Efficient Calculations
- Page calculations performed only when needed
- Reactive updates to pagination state
- Minimal re-renders on prop changes

### State Management
- Simple prop-based state management
- Event-driven page updates
- Clean separation of concerns

## Integration with Data Sources

### API Integration
```javascript
const fetchData = async (page) => {
  const offset = (page - 1) * itemsPerPage.value
  const response = await api.get('/data', {
    params: { offset, limit: itemsPerPage.value }
  })
  return response.data
}
```

### State Management
```javascript
// Pinia store example
export const useDataStore = defineStore('data', {
  state: () => ({
    items: [],
    pagination: {
      page: 1,
      itemsPerPage: 20,
      totalItems: 0
    }
  }),
  
  actions: {
    async fetchItems(page) {
      this.pagination.page = page
      // Fetch logic
    }
  }
})
```

## Best Practices

1. **Page Reset**: Reset to page 1 when filtering or searching
2. **URL Sync**: Synchronize pagination state with URL parameters
3. **Loading States**: Show loading indicators during data fetching
4. **Error Handling**: Handle pagination errors gracefully
5. **Performance**: Implement server-side pagination for large datasets
6. **Accessibility**: Ensure keyboard navigation and screen reader support

## Common Patterns

### Infinite Scroll Alternative
This component provides traditional pagination as an alternative to infinite scroll, which is better for:
- Data analysis workflows
- Bookmarkable page states
- Predictable navigation
- Better performance with large datasets

### Table Integration
Works seamlessly with:
- VDataTable (Vuetify)
- Custom table components
- List views
- Grid layouts
- Any paginated data display

This component provides a standardized, accessible, and user-friendly pagination solution for data-heavy applications.