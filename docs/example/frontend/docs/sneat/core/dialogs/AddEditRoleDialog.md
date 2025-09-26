# AddEditRoleDialog.vue

## Overview
A comprehensive dialog component for adding or editing user roles with granular permission management. Features a permissions matrix table with Read, Write, and Create permissions for various system modules, plus a "Select All" functionality with indeterminate state support.

## Props Interface

```typescript
interface Permission {
  name: string
  read: boolean
  write: boolean
  create: boolean
}

interface Roles {
  name: string
  permissions: Permission[]
}

interface Props {
  rolePermissions?: Roles
  isDialogVisible: boolean
}
```

### Props Details
- **rolePermissions** (optional): `Roles` - Role object containing name and permissions array
- **isDialogVisible** (required): `boolean` - Controls dialog visibility

### Default Values
```typescript
{
  name: '',
  permissions: [],
}
```

## Events Emitted

```typescript
interface Emit {
  (e: 'update:isDialogVisible', value: boolean): void
  (e: 'update:rolePermissions', value: Roles): void
}
```

### Events Details
- **update:isDialogVisible**: Emitted when dialog visibility should change (v-model support)
- **update:rolePermissions**: Emitted when role is submitted with the complete Roles object

## Slots Available
This component does not expose any custom slots.

## Global State Dependencies
- No global state dependencies (Pinia stores, etc.)
- Uses Vue's built-in reactivity system only

## Internal Methods and Computed Properties

### Methods
- **onSubmit()**: Handles form submission
  - Creates rolePermissions object with current role name and permissions
  - Emits 'update:rolePermissions' with the data
  - Closes dialog and resets form state

- **onReset()**: Resets form and closes dialog
  - Closes dialog by emitting 'update:isDialogVisible' with false
  - Resets isSelectAll state
  - Calls form reset method

### Reactive Data
- **permissions**: `ref<Permission[]>` - Array of system permissions with default modules
- **isSelectAll**: `ref<boolean>` - Controls master checkbox state
- **role**: `ref<string>` - Role name input field
- **refPermissionForm**: `ref<VForm>` - Reference to Vuetify form component

### Computed Properties
- **checkedCount**: Calculates total number of checked permission checkboxes
- **isIndeterminate**: Determines if master checkbox should show indeterminate state (some but not all permissions checked)

### Watchers
- **isSelectAll watcher**: Updates all permissions when master checkbox changes
- **isIndeterminate watcher**: Resets master checkbox when indeterminate state becomes false
- **permissions watcher**: Sets master checkbox to true when all permissions are checked (deep watch)
- **props watcher**: Updates form fields when rolePermissions prop changes

## Permission Modules
The component includes the following predefined permission modules:

1. **User Management**
2. **Content Management**
3. **Disputes Management**
4. **Database Management**
5. **Financial Management**
6. **Reporting**
7. **API Control**
8. **Repository Management**
9. **Payroll**

Each module has three permission types:
- **Read**: View/access permissions
- **Write**: Edit/modify permissions
- **Create**: Create new items permissions

## Usage Examples

### Add New Role
```vue
<template>
  <AddEditRoleDialog
    v-model:is-dialog-visible="showDialog"
    @update:role-permissions="handleRoleAdd"
  />
</template>

<script setup lang="ts">
const showDialog = ref(false)

const handleRoleAdd = (roleData: Roles) => {
  console.log('New role:', roleData)
  // Save role to database
}
</script>
```

### Edit Existing Role
```vue
<template>
  <AddEditRoleDialog
    v-model:is-dialog-visible="showDialog"
    :role-permissions="existingRole"
    @update:role-permissions="handleRoleUpdate"
  />
</template>

<script setup lang="ts">
const showDialog = ref(false)
const existingRole = ref({
  name: 'Editor',
  permissions: [
    { name: 'Content Management', read: true, write: true, create: false },
    { name: 'User Management', read: true, write: false, create: false }
  ]
})

const handleRoleUpdate = (roleData: Roles) => {
  console.log('Updated role:', roleData)
  // Update role in database
}
</script>
```

### With v-model for Role Permissions
```vue
<template>
  <AddEditRoleDialog
    v-model:is-dialog-visible="showDialog"
    v-model:role-permissions="currentRole"
  />
</template>

<script setup lang="ts">
const showDialog = ref(false)
const currentRole = ref<Roles>({ name: '', permissions: [] })

// currentRole will be automatically updated when form is submitted
</script>
```

## Component Dependencies

### Vue Components
- **VDialog**: Vuetify dialog wrapper
- **VCard**: Vuetify card container
- **VCardText**: Vuetify card content area
- **VForm**: Vuetify form wrapper (with ref)
- **VTable**: Vuetify table component for permissions matrix
- **VCheckbox**: Vuetify checkbox component
- **VBtn**: Vuetify button component
- **AppTextField**: Custom text field component
- **DialogCloseBtn**: Custom dialog close button component

### Vue Composition API
- `ref`, `computed`, `watch`, `defineProps`, `defineEmits`, `withDefaults`

## UI/UX Features
- **Dynamic Title**: Shows "Add New" or "Edit" based on existing role data
- **Permissions Matrix**: Tabular layout for granular permission control
- **Master Checkbox**: "Select All" with indeterminate state support
- **Responsive Design**: Auto width on mobile, 900px on desktop
- **Form Validation Ready**: VForm structure supports adding validation rules
- **Proper Reset**: Form and state cleanup on cancel/close

## Permission Management Logic
- **Select All Functionality**: Master checkbox controls all permission checkboxes
- **Indeterminate State**: Shows partial selection state when some permissions are checked
- **Automatic State Management**: Master checkbox state updates based on individual permission selections
- **Deep Watching**: Monitors changes to nested permission objects

## Styling Notes
- Custom SCSS for permission table styling
- Consistent spacing with Vuetify classes
- Right-aligned checkboxes for better visual hierarchy
- Border styling using CSS custom properties
- Responsive padding and layout

## Custom Styles
```scss
.permission-table {
  td {
    border-block-end: 1px solid rgba(var(--v-border-color), var(--v-border-opacity));
    padding-block: 0.625rem;

    .v-checkbox {
      min-inline-size: 4.75rem;
    }

    &:not(:first-child) {
      padding-inline: 0.5rem;
    }

    .v-label {
      white-space: nowrap;
    }
  }
}
```

## State Management Features
- **Form State Persistence**: Maintains form state during dialog lifecycle
- **Prop Synchronization**: Updates form when props change
- **Clean Reset**: Proper cleanup on form submission or cancellation
- **Permission Mapping**: Intelligent mapping between prop permissions and form permissions

## Accessibility
- Proper heading hierarchy (h4, h5, h6)
- Table structure for permissions matrix
- Form labels and semantic structure
- Checkbox labels for screen readers
- Button types and roles