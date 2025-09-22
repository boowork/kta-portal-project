# DialogCloseBtn.vue

## Overview
A standardized close button component for dialogs with consistent styling and behavior. Features elevated appearance and customizable icon properties.

## Props Interface

```typescript
interface Props {
  icon?: string       // Optional - Icon to display (default: 'bx-x')
  iconSize?: string   // Optional - Size of the icon (default: '20')
}
```

## Events Emitted
Inherits all events from the underlying `IconBtn` component, including standard button events like `click`.

## Slots Available
None - component uses props for customization.

## Global State Dependencies
None - component is fully self-contained.

## Internal Methods and Computed Properties

### Default Values
- `icon`: Defaults to `'bx-x'` (close/X icon)
- `iconSize`: Defaults to `'20'` pixels

## Component Dependencies

### Custom Components
- `IconBtn`: Core icon button component that provides the button functionality

### Vuetify Components
- `VIcon`: Icon display within the button

## Usage Examples

### Basic Usage
```vue
<template>
  <VDialog v-model="dialog">
    <VCard>
      <VCardTitle>
        Dialog Title
        <VSpacer />
        <DialogCloseBtn @click="dialog = false" />
      </VCardTitle>
      <VCardText>
        Dialog content here...
      </VCardText>
    </VCard>
  </VDialog>
</template>

<script setup>
const dialog = ref(false)
</script>
```

### Custom Icon
```vue
<template>
  <VDialog v-model="dialog">
    <VCard>
      <VCardTitle class="d-flex align-center">
        Settings
        <VSpacer />
        <DialogCloseBtn 
          icon="bx-cog"
          @click="toggleSettings"
        />
      </VCardTitle>
    </VCard>
  </VDialog>
</template>
```

### Custom Size
```vue
<template>
  <VDialog v-model="dialog">
    <VCard>
      <VCardTitle>
        Large Dialog
        <VSpacer />
        <DialogCloseBtn 
          icon-size="24"
          @click="dialog = false"
        />
      </VCardTitle>
    </VCard>
  </VDialog>
</template>
```

### In Different Dialog Types
```vue
<template>
  <!-- Fullscreen Dialog -->
  <VDialog 
    v-model="fullscreenDialog"
    fullscreen
  >
    <VCard>
      <VToolbar color="primary">
        <VToolbarTitle>Fullscreen Dialog</VToolbarTitle>
        <VSpacer />
        <DialogCloseBtn 
          icon-size="22"
          @click="fullscreenDialog = false"
        />
      </VToolbar>
    </VCard>
  </VDialog>

  <!-- Modal Dialog -->
  <VDialog 
    v-model="modalDialog"
    max-width="500"
  >
    <VCard>
      <VCardTitle class="d-flex align-center">
        Confirmation
        <VSpacer />
        <DialogCloseBtn @click="modalDialog = false" />
      </VCardTitle>
      <VCardText>
        Are you sure you want to continue?
      </VCardText>
    </VCard>
  </VDialog>
</template>
```

### With Event Handlers
```vue
<template>
  <VDialog v-model="dialog">
    <VCard>
      <VCardTitle>
        Form Dialog
        <VSpacer />
        <DialogCloseBtn @click="handleClose" />
      </VCardTitle>
      <VCardText>
        <VForm ref="form">
          <!-- Form content -->
        </VForm>
      </VCardText>
    </VCard>
  </VDialog>
</template>

<script setup>
const dialog = ref(false)
const form = ref()

const handleClose = async () => {
  // Check for unsaved changes
  const hasChanges = await checkUnsavedChanges()
  
  if (hasChanges) {
    const confirmed = await confirmClose()
    if (!confirmed) return
  }
  
  dialog.value = false
}
</script>
```

## Styling Features

### Fixed Properties
- **Variant**: `elevated` - provides raised appearance
- **Size**: `30` - consistent small button size
- **Ripple**: `false` - disabled for cleaner interaction
- **CSS Class**: `v-dialog-close-btn` - for custom styling

### Visual Characteristics
- Elevated appearance with shadow
- Compact 30px size for minimal space usage
- No ripple effect for cleaner visual feedback
- Consistent icon sizing

## CSS Customization

The component applies the `.v-dialog-close-btn` class, allowing for custom styling:

```scss
.v-dialog-close-btn {
  // Custom dialog close button styles
  position: absolute;
  top: 8px;
  right: 8px;
  
  // Override elevation
  &.v-btn--elevated {
    box-shadow: none;
  }
  
  // Custom hover effects
  &:hover {
    background-color: rgba(var(--v-theme-error), 0.1);
  }
}
```

## Common Patterns

### Positioned Close Button
```vue
<template>
  <VDialog v-model="dialog">
    <VCard class="position-relative">
      <DialogCloseBtn 
        class="position-absolute"
        style="top: 8px; right: 8px; z-index: 1;"
        @click="dialog = false"
      />
      <VCardText class="pt-12">
        Content with positioned close button
      </VCardText>
    </VCard>
  </VDialog>
</template>
```

### With Confirmation
```vue
<template>
  <VDialog v-model="dialog">
    <VCard>
      <VCardTitle>
        Important Action
        <VSpacer />
        <DialogCloseBtn @click="confirmClose" />
      </VCardTitle>
    </VCard>
  </VDialog>
</template>

<script setup>
const confirmClose = async () => {
  const result = await $dialog.confirm({
    title: 'Unsaved Changes',
    text: 'You have unsaved changes. Are you sure you want to close?'
  })
  
  if (result) {
    dialog.value = false
  }
}
</script>
```

## Accessibility Features

- Inherits accessibility features from `IconBtn`
- Proper button semantics for screen readers
- Keyboard navigation support
- Clear visual indication of interactive element

## Best Practices

1. **Consistent Placement**: Position consistently across dialogs (usually top-right)
2. **Clear Indication**: Use recognizable close icon (X)
3. **Proper Sizing**: Use default sizing for consistency
4. **Event Handling**: Always handle the click event appropriately
5. **Accessibility**: Ensure keyboard navigation works properly
6. **Confirmation**: Consider confirmation for dialogs with unsaved changes

## Integration with Dialog Systems

Works seamlessly with:
- VDialog (Vuetify)
- Custom dialog components
- Modal management systems
- Popup libraries
- Overlay components

This component provides a standardized way to add close functionality to any dialog interface while maintaining design consistency across the application.