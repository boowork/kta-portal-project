# AppBarSearch.vue

## Overview
A powerful search dialog component that provides autocomplete functionality with keyboard navigation and hotkey support. Features a generic type system for handling different types of search results.

## Props Interface

```typescript
interface Props {
  isDialogVisible: boolean        // Controls dialog visibility
  searchResults: T[]             // Generic array of search results  
  isLoading?: boolean           // Optional loading state indicator
}
```

## Events Emitted

```typescript
interface Emit {
  (e: 'update:isDialogVisible', value: boolean): void  // Dialog visibility state
  (e: 'search', value: string): void                   // Search query changes
}
```

## Slots Available

### suggestions
- **Purpose**: Custom content shown when no search query is entered
- **Condition**: Visible when `searchResults` exists and `searchQueryLocal` is empty
- **Usage**: Display suggested searches, recent searches, or categories

### searchResult
- **Purpose**: Custom rendering for each search result item
- **Props**: `{ item: T }` - The search result item
- **Default**: Simple text display of the item
- **Usage**: Customize how search results are displayed

### noData
- **Purpose**: Custom content when no search results are found
- **Condition**: Visible when search query exists but no results found
- **Default**: Shows "No Result For" message with search icon
- **Usage**: Custom empty state messaging

### noDataSuggestion
- **Purpose**: Additional content below the no data message
- **Usage**: Suggest alternative searches or actions

## Global State Dependencies
None - component is fully self-contained and relies only on props.

## Internal Methods and Computed Properties

### Methods
- `clearSearchAndCloseDialog()`: Resets search and closes dialog
- `getFocusOnSearchList(e: KeyboardEvent)`: Handles arrow key navigation in search results
- `dialogModelValueUpdate(val: boolean)`: Updates dialog visibility and clears search

### Reactive Properties
- `searchQueryLocal`: Local search input value
- `refSearchList`: Reference to VList for focus management
- `refSearchInput`: Reference to search input field

### Composables Used
- `useMagicKeys()`: Handles Ctrl+K/Cmd+K hotkey detection
- `watch()`: Monitors hotkey presses and dialog visibility

## Keyboard Shortcuts

- **Ctrl+K / Cmd+K**: Opens search dialog
- **Escape**: Closes dialog and clears search
- **Arrow Down**: Navigate to next search result
- **Arrow Up**: Navigate to previous search result

## Component Dependencies

### Vuetify Components
- `VDialog`: Main dialog container
- `VCard`, `VCardText`: Dialog content structure
- `VTextField`: Search input field
- `VIcon`: Search and close icons
- `VDivider`: Visual separators
- `VList`, `VListItem`: Search results list
- `VSkeletonLoader`: Loading state animation

### External Libraries
- `vue3-perfect-scrollbar`: Custom scrollbar for results list

## Usage Examples

### Basic Usage
```vue
<template>
  <AppBarSearch
    v-model:isDialogVisible="searchDialog"
    :search-results="searchResults"
    :is-loading="isSearching"
    @search="handleSearch"
  />
</template>

<script setup>
const searchDialog = ref(false)
const searchResults = ref([])
const isSearching = ref(false)

const handleSearch = async (query) => {
  isSearching.value = true
  searchResults.value = await searchAPI(query)
  isSearching.value = false
}
</script>
```

### Custom Search Results
```vue
<template>
  <AppBarSearch
    v-model:isDialogVisible="searchDialog"
    :search-results="searchResults"
    @search="handleSearch"
  >
    <template #searchResult="{ item }">
      <VListItem>
        <VListItemTitle>{{ item.title }}</VListItemTitle>
        <VListItemSubtitle>{{ item.category }}</VListItemSubtitle>
      </VListItem>
    </template>
  </AppBarSearch>
</template>
```

### With Suggestions
```vue
<template>
  <AppBarSearch
    v-model:isDialogVisible="searchDialog"
    :search-results="searchResults"
    @search="handleSearch"
  >
    <template #suggestions>
      <VList>
        <VListItem v-for="suggestion in suggestions" :key="suggestion">
          <VListItemTitle>{{ suggestion }}</VListItemTitle>
        </VListItem>
      </VList>
    </template>
  </AppBarSearch>
</template>
```

## Styling Notes

The component includes custom CSS classes:
- `.app-bar-search-dialog`: Main dialog styling
- `.app-bar-search-input`: Search input customization
- `.app-bar-search-list`: Results list styling
- `.app-bar-search-suggestions`: Suggestions area styling

## Accessibility Features

- Automatic focus on search input when dialog opens
- Keyboard navigation support for search results
- Proper ARIA attributes and semantic HTML structure
- Focus management with VList navigation