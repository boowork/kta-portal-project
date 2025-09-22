# AppDrawerHeaderSection.vue

## Overview
A simple header component designed for drawer interfaces that displays a title with an optional close button. Commonly used in navigation drawers, modals, and sidebar panels.

## Props Interface

```typescript
interface Props {
  title: string  // Required title text to display
}
```

## Events Emitted

```typescript
interface Emit {
  (e: 'cancel', el: MouseEvent): void  // Emitted when close button is clicked
}
```

## Slots Available

### beforeClose
- **Purpose**: Custom content displayed before the close button
- **Position**: Between the title and close button
- **Usage**: Add additional actions, status indicators, or navigation elements

## Global State Dependencies
None - component is fully self-contained and relies only on props.

## Internal Methods and Computed Properties
None - component uses direct prop access and event emission.

## Component Dependencies

### Vuetify Components
- `VSpacer`: Pushes close button to the right edge
- `VIcon`: Close button icon (bx-x)

### Custom Components
- `IconBtn`: Custom icon button component for the close action

## Usage Examples

### Basic Usage
```vue
<template>
  <AppDrawerHeaderSection
    title="Settings"
    @cancel="closeDrawer"
  />
</template>

<script setup>
const closeDrawer = (event) => {
  // Handle drawer close logic
  console.log('Drawer closed:', event)
}
</script>
```

### With Additional Actions
```vue
<template>
  <AppDrawerHeaderSection
    title="User Profile"
    @cancel="closeDrawer"
  >
    <template #beforeClose>
      <VBtn
        variant="text"
        size="small"
        @click="saveProfile"
      >
        Save
      </VBtn>
    </template>
  </AppDrawerHeaderSection>
</template>

<script setup>
const closeDrawer = () => {
  // Close drawer logic
}

const saveProfile = () => {
  // Save profile logic
}
</script>
```

### In Navigation Drawer
```vue
<template>
  <VNavigationDrawer v-model="drawer">
    <AppDrawerHeaderSection
      title="Navigation Menu"
      @cancel="drawer = false"
    />
    
    <!-- Drawer content -->
    <VList>
      <!-- Navigation items -->
    </VList>
  </VNavigationDrawer>
</template>
```

## Styling Notes

The component uses:
- Flexbox layout for proper spacing
- Padding: `px-6 py-5` for consistent spacing
- Text styling: `text-h5` for the title
- Proper alignment with `d-flex align-center`

## Layout Structure

```
┌─────────────────────────────────────┐
│  [Title]        [beforeClose] [×]   │
└─────────────────────────────────────┘
```

The component creates a horizontal layout where:
- Title is left-aligned
- VSpacer pushes remaining content to the right
- Optional beforeClose slot content
- Close button (×) is right-aligned

## Accessibility Features

- Semantic heading structure with proper text hierarchy
- Click event provides MouseEvent for potential accessibility enhancements
- Proper button semantics through IconBtn component