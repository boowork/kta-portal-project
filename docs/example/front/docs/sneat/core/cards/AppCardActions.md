# AppCardActions Component

A versatile card wrapper component that provides built-in action buttons, loading states, collapse functionality, and content management.

## Location
`/src/@core/components/cards/AppCardActions.vue`

## Props Interface

```typescript
interface Props {
  collapsed?: boolean           // Initial collapsed state
  noActions?: boolean          // Disable all action buttons
  actionCollapsed?: boolean    // Show only collapse action
  actionRefresh?: boolean      // Show only refresh action
  actionRemove?: boolean       // Show only remove action
  loading?: boolean | undefined // External loading state control
  title?: string              // Card title text
}
```

### Default Values
- `collapsed`: `false`
- `noActions`: `false`
- `actionCollapsed`: `false`
- `actionRefresh`: `false`
- `actionRemove`: `false`
- `loading`: `undefined`
- `title`: `undefined`

## Events Emitted

```typescript
interface Emit {
  (e: 'collapsed', isContentCollapsed: boolean): void  // Emitted when card is collapsed/expanded
  (e: 'refresh', stopLoading: () => void): void        // Emitted when refresh button is clicked
  (e: 'trash'): void                                   // Emitted when remove button is clicked
  (e: 'initialLoad'): void                             // Emitted on component initialization
  (e: 'update:loading', loading: boolean): void        // Emitted for v-model:loading support
}
```

## Slots Available

### Default Slot
- **Purpose**: Main card content area
- **Description**: Content rendered inside the collapsible card body

### Named Slots

#### `title`
- **Purpose**: Custom title content
- **Fallback**: Uses `title` prop value
- **Usage**: For custom title rendering with HTML or components

#### `before-actions`
- **Purpose**: Additional content before action buttons
- **Description**: Rendered in the card header before the built-in action buttons

## Global State Dependencies

**None** - This component is self-contained and doesn't depend on any global state.

## Internal Methods and Computed Properties

### Computed Properties

#### `$loading`
- **Type**: `ComputedRef<boolean>`
- **Purpose**: Reactive loading state management
- **Behavior**: 
  - If `loading` prop is provided, uses external control
  - Otherwise uses internal `_loading` ref
  - Supports v-model pattern for loading state

#### `isPositive` (Referenced but not present)
- **Note**: This seems to be referenced in other card components but not used in AppCardActions

### Reactive References

#### `isContentCollapsed`
- **Type**: `Ref<boolean>`
- **Initial Value**: `props.collapsed`
- **Purpose**: Tracks the current collapsed state of the card content

#### `isCardRemoved`
- **Type**: `Ref<boolean>`
- **Initial Value**: `false`
- **Purpose**: Controls card visibility when remove action is triggered

#### `_loading`
- **Type**: `Ref<boolean>`
- **Initial Value**: `false`
- **Purpose**: Internal loading state when no external loading control is provided

### Methods

#### `stopLoading()`
- **Purpose**: Utility function to stop loading state
- **Usage**: Passed as callback to 'refresh' event

#### `triggerCollapse()`
- **Purpose**: Toggles card content collapse state
- **Side Effects**: Emits 'collapsed' event with new state

#### `triggerRefresh()`
- **Purpose**: Initiates refresh operation
- **Side Effects**: 
  - Sets loading state to true
  - Emits 'refresh' event with stopLoading callback

#### `triggeredRemove()`
- **Purpose**: Handles card removal
- **Side Effects**:
  - Sets `isCardRemoved` to true (hides card)
  - Emits 'trash' event

## Usage Examples

### Basic Usage
```vue
<template>
  <AppCardActions title="Sample Card">
    <p>Your card content here</p>
  </AppCardActions>
</template>
```

### With All Actions
```vue
<template>
  <AppCardActions 
    title="Interactive Card"
    :action-collapsed="true"
    :action-refresh="true" 
    :action-remove="true"
    @collapsed="onCollapsed"
    @refresh="onRefresh"
    @trash="onRemove"
  >
    <p>Card content with all actions enabled</p>
  </AppCardActions>
</template>

<script setup>
const onCollapsed = (isCollapsed) => {
  console.log('Card collapsed:', isCollapsed)
}

const onRefresh = (stopLoading) => {
  // Simulate API call
  setTimeout(() => {
    stopLoading()
  }, 2000)
}

const onRemove = () => {
  console.log('Card removed')
}
</script>
```

### With External Loading Control
```vue
<template>
  <AppCardActions 
    v-model:loading="isLoading"
    title="Controlled Loading"
    :action-refresh="true"
    @refresh="handleRefresh"
  >
    <p>Content with external loading control</p>
  </AppCardActions>
</template>

<script setup>
import { ref } from 'vue'

const isLoading = ref(false)

const handleRefresh = (stopLoading) => {
  // Loading state is controlled externally
  fetchData().finally(() => {
    isLoading.value = false
  })
}
</script>
```

### Custom Title Slot
```vue
<template>
  <AppCardActions>
    <template #title>
      <div class="d-flex align-center gap-2">
        <VIcon icon="mdi-chart-line" />
        <span>Analytics Dashboard</span>
      </div>
    </template>
    
    <template #before-actions>
      <VChip size="small" color="success">Live</VChip>
    </template>
    
    <p>Dashboard content</p>
  </AppCardActions>
</template>
```

## Component Dependencies

### External Components
- **VCard**: Vuetify card component (auto-imported)
- **VCardItem**: Vuetify card item component (auto-imported)
- **VCardTitle**: Vuetify card title component (auto-imported)
- **VIcon**: Vuetify icon component (auto-imported)
- **VOverlay**: Vuetify overlay component (auto-imported)
- **VProgressCircular**: Vuetify progress indicator (auto-imported)
- **VExpandTransition**: Vuetify expand transition component (auto-imported)
- **IconBtn**: Custom icon button component (auto-imported from @core/components)

### Vue Composition API
- `ref`: For reactive references
- `computed`: For computed properties
- `withDefaults`: For prop defaults
- `defineProps`: For prop definition
- `defineEmits`: For event definition
- `defineOptions`: For component options

## Styling

### SCSS Styles
The component includes custom styles for:
- Card content spacing adjustment when following card items
- Proper padding management for nested card text elements

```scss
.v-card-item {
  +.v-card-content {
    .v-card-text:first-child {
      padding-block-start: 0;
    }
  }
}
```

## Notes

- Uses `inheritAttrs: false` to prevent automatic attribute inheritance
- Supports Vuetify's VCard attributes through `v-bind="$attrs"`
- Loading overlay is contained within the card boundaries
- Collapse animation uses Vuetify's VExpandTransition
- Action buttons use conditional rendering based on prop configuration