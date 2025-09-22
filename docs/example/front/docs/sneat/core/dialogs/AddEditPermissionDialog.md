# AddEditPermissionDialog.vue

## Overview
A dialog component for adding or editing system permissions. The dialog provides a simple interface for managing permission names with warnings about system functionality impact. Features dynamic titles and descriptions based on edit/add mode.

## Props Interface

```typescript
interface Props {
  isDialogVisible: boolean
  permissionName?: string
}
```

### Props Details
- **isDialogVisible** (required): `boolean` - Controls dialog visibility
- **permissionName** (optional): `string` - Existing permission name for editing (defaults to empty string)

## Events Emitted

```typescript
interface Emit {
  (e: 'update:isDialogVisible', value: boolean): void
  (e: 'update:permissionName', value: string): void
}
```

### Events Details
- **update:isDialogVisible**: Emitted when dialog visibility should change (v-model support)
- **update:permissionName**: Emitted when permission name is submitted with the new/updated value

## Slots Available
This component does not expose any custom slots.

## Global State Dependencies
- No global state dependencies (Pinia stores, etc.)
- Uses Vue's built-in reactivity system only

## Internal Methods and Computed Properties

### Methods
- **onReset()**: Resets form and closes dialog
  - Closes dialog by emitting 'update:isDialogVisible' with false
  - Clears currentPermissionName field

- **onSubmit()**: Handles form submission
  - Closes dialog by emitting 'update:isDialogVisible' with false
  - Emits 'update:permissionName' with current permission name value

### Reactive Data
- **currentPermissionName**: `ref<string>` - Local reactive copy of the permission name being edited

### Watchers
- **props watcher**: Watches for changes in props to update currentPermissionName when permissionName prop changes

## Usage Examples

### Add New Permission
```vue
<template>
  <AddEditPermissionDialog
    v-model:is-dialog-visible="showDialog"
    @update:permission-name="handlePermissionAdd"
  />
</template>

<script setup lang="ts">
const showDialog = ref(false)

const handlePermissionAdd = (permissionName: string) => {
  console.log('New permission:', permissionName)
  // Add permission to system
}
</script>
```

### Edit Existing Permission
```vue
<template>
  <AddEditPermissionDialog
    v-model:is-dialog-visible="showDialog"
    :permission-name="existingPermission"
    @update:permission-name="handlePermissionUpdate"
  />
</template>

<script setup lang="ts">
const showDialog = ref(false)
const existingPermission = ref('manage-users')

const handlePermissionUpdate = (permissionName: string) => {
  console.log('Updated permission:', permissionName)
  // Update permission in system
}
</script>
```

### With v-model for Permission Name
```vue
<template>
  <AddEditPermissionDialog
    v-model:is-dialog-visible="showDialog"
    v-model:permission-name="currentPermission"
  />
</template>

<script setup lang="ts">
const showDialog = ref(false)
const currentPermission = ref('')

// currentPermission will be automatically updated when form is submitted
</script>
```

## Component Dependencies

### Vue Components
- **VDialog**: Vuetify dialog wrapper
- **VCard**: Vuetify card container
- **VCardText**: Vuetify card content area
- **VForm**: Vuetify form wrapper
- **VAlert**: Vuetify alert component for warnings
- **VBtn**: Vuetify button component
- **VCheckbox**: Vuetify checkbox component
- **AppTextField**: Custom text field component
- **DialogCloseBtn**: Custom dialog close button component

### Vue Composition API
- `ref`, `watch`, `defineProps`, `defineEmits`, `withDefaults`

## UI/UX Features
- **Dynamic Content**: Title, description, and button text change based on add/edit mode
- **Warning Alert**: Displays warning about potential system impact
- **Core Permission Option**: Checkbox to mark permission as core system permission
- **Responsive Design**: Auto width on mobile, 600px on desktop
- **Inline Form Layout**: Text field and submit button in same row on larger screens
- **Form Reset**: Proper cleanup when dialog is closed or cancelled

## Dynamic Text Content
The component intelligently changes its content based on whether it's in add or edit mode:

### Add Mode (when permissionName is empty)
- Title: "Add Permission"
- Description: "Add permission as per your requirements."
- Button: "Add"
- Warning: "By adding the permission name..."

### Edit Mode (when permissionName has value)
- Title: "Edit Permission"
- Description: "Edit permission as per your requirements."
- Button: "Update"
- Warning: "By editing the permission name..."

## Styling Notes
- Uses Vuetify's spacing classes (pa-2, pa-sm-10, mb-6, etc.)
- Flex layout for form elements with responsive wrapping
- Custom SCSS for permission table styling (though not used in current template)
- Responsive gap spacing between form elements

## Security Considerations
- **Warning Alert**: Explicitly warns users about potential system impacts
- **Core Permission Flag**: Provides option to mark critical system permissions
- **Validation Ready**: Structure supports adding validation rules

## Accessibility
- Proper heading hierarchy (h4)
- Alert component with warning type for screen readers
- Form labels and placeholders
- Button types and semantic structure
- Responsive design considerations

## Custom Styles
The component includes custom SCSS for a permission table (currently unused in template):
```scss
.permission-table {
  td {
    border-block-end: 1px solid rgba(var(--v-border-color), var(--v-border-opacity));
    padding-block: 0.5rem;
    padding-inline: 0;
  }
}
```