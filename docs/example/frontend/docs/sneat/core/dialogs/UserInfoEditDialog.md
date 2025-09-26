# UserInfoEditDialog.vue

## Overview
A comprehensive user information editing dialog with extensive form fields for managing user profiles. Features a complete user data interface including personal information, contact details, preferences, and account settings with proper form validation structure.

## Props Interface

```typescript
interface UserData {
  id: number | null
  fullName: string
  firstName: string
  lastName: string
  company: string
  username: string
  role: string
  country: string
  contact: string | undefined
  email: string | undefined
  currentPlan: string
  status: string | undefined
  avatar: string
  taskDone: number | null
  projectDone: number | null
  taxId: string
  language: string
}

interface Props {
  userData?: UserData
  isDialogVisible: boolean
}
```

### Props Details
- **userData** (optional): `UserData` - Complete user information object
- **isDialogVisible** (required): `boolean` - Controls dialog visibility

### Default Values
Comprehensive defaults for all UserData fields:
```typescript
{
  id: 0,
  fullName: '',
  firstName: '',
  lastName: '',
  company: '',
  role: '',
  username: '',
  country: '',
  contact: '',
  email: '',
  currentPlan: '',
  status: '',
  avatar: '',
  taskDone: null,
  projectDone: null,
  taxId: '',
  language: '',
}
```

## Events Emitted

```typescript
interface Emit {
  (e: 'submit', value: UserData): void
  (e: 'update:isDialogVisible', val: boolean): void
}
```

### Events Details
- **submit**: Emitted when form is submitted with the complete UserData object
- **update:isDialogVisible**: Emitted when dialog visibility should change (v-model support)

## Slots Available
This component does not expose any custom slots.

## Global State Dependencies
- No global state dependencies (Pinia stores, etc.)
- Uses Vue's built-in reactivity system only

## Internal Methods and Computed Properties

### Methods
- **onFormSubmit()**: Handles form submission
  - Closes dialog by emitting 'update:isDialogVisible' with false
  - Emits 'submit' event with current userData values

- **onFormReset()**: Resets form data and closes dialog
  - Resets userData to original prop values using structuredClone
  - Closes dialog

- **dialogModelValueUpdate(val: boolean)**: Updates dialog visibility
  - Emits 'update:isDialogVisible' with the provided value

### Reactive Data
- **userData**: `ref<UserData>` - Local reactive copy of the userData prop using structuredClone
- **isUseAsBillingAddress**: `ref<boolean>` - Toggle for billing address option

### Watchers
- **props watcher**: Updates userData when props change, maintaining reactivity with parent data

## Usage Examples

### Basic User Editing
```vue
<template>
  <UserInfoEditDialog
    v-model:is-dialog-visible="showEdit"
    :user-data="selectedUser"
    @submit="handleUserUpdate"
  />
</template>

<script setup lang="ts">
const showEdit = ref(false)
const selectedUser = ref<UserData>({
  id: 1,
  firstName: 'John',
  lastName: 'Doe',
  email: 'john.doe@example.com',
  // ... other user fields
})

const handleUserUpdate = async (userData: UserData) => {
  try {
    await updateUser(userData)
    // Show success message
  } catch (error) {
    // Handle error
  }
}
</script>
```

### Admin User Management
```vue
<template>
  <div>
    <VDataTable
      :items="users"
      :headers="userHeaders"
    >
      <template #item.actions="{ item }">
        <VBtn @click="editUser(item)" size="small">
          Edit
        </VBtn>
      </template>
    </VDataTable>
    
    <UserInfoEditDialog
      v-model:is-dialog-visible="showUserEdit"
      :user-data="currentUser"
      @submit="saveUserChanges"
    />
  </div>
</template>

<script setup lang="ts">
const showUserEdit = ref(false)
const currentUser = ref<UserData | null>(null)
const users = ref<UserData[]>([])

const editUser = (user: UserData) => {
  currentUser.value = { ...user }
  showUserEdit.value = true
}

const saveUserChanges = async (userData: UserData) => {
  await updateUserInDatabase(userData)
  // Refresh users list
  await fetchUsers()
}
</script>
```

### Profile Self-Edit
```vue
<template>
  <div>
    <VCard title="My Profile">
      <VCardText>
        <VBtn @click="editProfile">
          Edit Profile
        </VBtn>
      </VCardText>
    </VCard>
    
    <UserInfoEditDialog
      v-model:is-dialog-visible="showProfileEdit"
      :user-data="userProfile"
      @submit="updateProfile"
    />
  </div>
</template>

<script setup lang="ts">
const showProfileEdit = ref(false)
const userProfile = ref<UserData>(getCurrentUser())

const editProfile = () => {
  showProfileEdit.value = true
}

const updateProfile = async (userData: UserData) => {
  await updateCurrentUser(userData)
  userProfile.value = userData
}
</script>
```

## Component Dependencies

### Vue Components
- **VDialog**: Vuetify dialog wrapper
- **VCard**: Vuetify card container
- **VCardText**: Vuetify card content area
- **VForm**: Vuetify form wrapper
- **VRow** / **VCol**: Vuetify grid system
- **VBtn**: Vuetify button component
- **VSwitch**: Vuetify switch component
- **AppTextField**: Custom text field component
- **AppSelect**: Custom select component
- **DialogCloseBtn**: Custom dialog close button component

### Vue Composition API
- `ref`, `watch`, `toRaw`, `defineProps`, `defineEmits`, `withDefaults`

## Form Fields

### Personal Information
1. **First Name** (Half width on md+)
   - Placeholder: "John"
2. **Last Name** (Half width on md+)
   - Placeholder: "Doe"
3. **Username** (Full width)
   - Placeholder: "john.doe.007"

### Contact Information
4. **Email** (Half width on md+)
   - Placeholder: "johndoe@email.com"
5. **Phone Number** (Half width on md+)
   - Placeholder: "+1 9876543210"

### Account Settings
6. **Status** (Half width on md+)
   - Options: Active, Inactive, Pending
7. **Tax ID** (Half width on md+)
   - Placeholder: "123456789"

### Preferences
8. **Language** (Half width on md+)
   - Multiple selection with chips
   - Options: English, Spanish, French
9. **Country** (Half width on md+)
   - Options: United States, United Kingdom, France

### Additional Options
10. **Billing Address** (Full width)
    - Switch: "Use as a billing address?"

## Form Layout
- **Row 1**: First Name (md:6), Last Name (md:6)
- **Row 2**: Username (full width)
- **Row 3**: Email (md:6), Status (md:6)
- **Row 4**: Tax ID (md:6), Phone Number (md:6)
- **Row 5**: Language (md:6), Country (md:6)
- **Row 6**: Billing address switch (full width)
- **Row 7**: Action buttons (centered)

## UI/UX Features
- **Privacy Notice**: "Updating user details will receive a privacy audit"
- **Responsive Design**: Auto width on mobile, 900px on desktop
- **Grid Layout**: Responsive column layout for optimal field arrangement
- **Form Reset**: Cancel button restores original values
- **Multi-select Language**: Chip-based language selection
- **Comprehensive Fields**: All essential user profile information

## Styling Notes
- Uses Vuetify's responsive grid system
- Consistent spacing with Vuetify classes (pa-sm-10, pa-2, mt-6)
- Centered title and action buttons
- Flex layout for centered buttons with gap spacing
- Responsive column layout (md="6" for paired fields)

## Data Handling
- **Structured Clone**: Uses structuredClone for deep copying to avoid reference issues
- **Reactive Updates**: Watches props for changes and updates local form data
- **Type Safety**: Complete TypeScript interface definitions
- **Default Values**: Comprehensive defaults for all form fields

## Validation Ready
- Form structure supports adding validation rules
- All required fields identified in interface
- Error handling can be added to form submission
- Input types configured appropriately

## Security Considerations
- **Privacy Audit Notice**: Informs users about audit process
- **Data Protection**: Uses structured cloning for data safety
- **Field Validation**: Structure ready for input validation
- **Access Control**: Can be extended with permission checks

## Accessibility
- Proper heading hierarchy (h4)
- Form labels for all input fields
- Placeholder text for user guidance
- Button types and semantic structure
- Responsive design considerations

## Integration Points
- **User Management**: Complete user profile editing
- **Admin Dashboard**: User administration interface
- **Profile Settings**: Self-service profile editing
- **HR Systems**: Employee information management
- **Customer Support**: Customer profile updates

## Performance Considerations
- **Structured Clone**: Efficient deep copying for form data
- **Reactive Updates**: Optimized change detection
- **Conditional Rendering**: Dialog only renders when visible
- **Minimal Dependencies**: Uses standard Vuetify components

## Customization Options
- **Field Addition**: Easy to add new user fields
- **Validation Rules**: Can be extended with form validation
- **Field Grouping**: Logical grouping of related fields
- **Permission Levels**: Can be customized based on user roles
- **Localization**: All labels ready for internationalization