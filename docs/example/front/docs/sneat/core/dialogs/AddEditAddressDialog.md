# AddEditAddressDialog.vue

## Overview
A comprehensive dialog component for adding or editing billing/delivery addresses. The dialog includes address type selection (Home/Office), complete address form fields, and billing address options.

## Props Interface

```typescript
interface BillingAddress {
  firstName: string | undefined
  lastName: string | undefined
  selectedCountry: string | null
  addressLine1: string
  addressLine2: string
  landmark: string
  contact: string
  country: string | null
  city: string
  state: string
  zipCode: number | null
}

interface Props {
  billingAddress?: BillingAddress
  isDialogVisible: boolean
}
```

### Props Details
- **billingAddress** (optional): `BillingAddress` - Address data object with comprehensive address fields
- **isDialogVisible** (required): `boolean` - Controls dialog visibility

### Default Values
The component provides comprehensive defaults for all BillingAddress fields:
```typescript
{
  firstName: '',
  lastName: '',
  selectedCountry: null,
  addressLine1: '',
  addressLine2: '',
  landmark: '',
  contact: '',
  country: null,
  city: '',
  state: '',
  zipCode: null,
}
```

## Events Emitted

```typescript
interface Emit {
  (e: 'update:isDialogVisible', value: boolean): void
  (e: 'submit', value: BillingAddress): void
}
```

### Events Details
- **update:isDialogVisible**: Emitted when dialog visibility should change (v-model support)
- **submit**: Emitted when form is submitted with the complete BillingAddress object

## Slots Available
This component does not expose any custom slots.

## Global State Dependencies
- No global state dependencies (Pinia stores, etc.)
- Uses Vue's built-in reactivity system only

## Internal Methods and Computed Properties

### Methods
- **resetForm()**: Resets form data and closes dialog
  - Closes dialog by emitting 'update:isDialogVisible' with false
  - Resets billingAddress to original prop values using structuredClone

- **onFormSubmit()**: Handles form submission
  - Closes dialog by emitting 'update:isDialogVisible' with false
  - Emits 'submit' event with current billingAddress values

### Reactive Data
- **billingAddress**: `ref<BillingAddress>` - Local reactive copy of the billingAddress prop
- **selectedAddress**: `ref<string>` - Selected address type ('Home' or 'Office')
- **addressTypes**: Static array defining address type options with icons and descriptions

### Address Types Configuration
```typescript
const addressTypes = [
  {
    icon: { icon: 'bx-home', size: '28' },
    title: 'Home',
    desc: 'Delivery Time (9am - 9pm)',
    value: 'Home',
  },
  {
    icon: { icon: 'bx-briefcase', size: '28' },
    title: 'Office',
    desc: 'Delivery Time (9am - 5pm)',
    value: 'Office',
  },
]
```

## Usage Examples

### Basic Usage (Add New Address)
```vue
<template>
  <AddEditAddressDialog
    v-model:is-dialog-visible="showDialog"
    @submit="handleAddressSubmit"
  />
</template>

<script setup lang="ts">
const showDialog = ref(false)

const handleAddressSubmit = (address: BillingAddress) => {
  console.log('New address:', address)
  // Save address to database
}
</script>
```

### Edit Existing Address
```vue
<template>
  <AddEditAddressDialog
    v-model:is-dialog-visible="showDialog"
    :billing-address="existingAddress"
    @submit="handleAddressUpdate"
  />
</template>

<script setup lang="ts">
const showDialog = ref(false)
const existingAddress = ref({
  firstName: 'John',
  lastName: 'Doe',
  selectedCountry: 'USA',
  addressLine1: '123 Main St',
  addressLine2: 'Apt 4B',
  landmark: 'Near Central Park',
  contact: '+1234567890',
  country: 'USA',
  city: 'New York',
  state: 'NY',
  zipCode: 10001
})

const handleAddressUpdate = (address: BillingAddress) => {
  console.log('Updated address:', address)
  // Update address in database
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
- **CustomRadiosWithIcon**: Custom radio component with icons
- **DialogCloseBtn**: Custom dialog close button component

### Vue Composition API
- `ref`, `toRaw`, `defineProps`, `defineEmits`, `withDefaults`

## UI/UX Features
- **Dynamic Title**: Shows "Add New" or "Edit" based on existing address data
- **Address Type Selection**: Visual radio selection for Home/Office with delivery time info
- **Comprehensive Form**: All standard address fields with proper validation
- **Responsive Design**: Auto width on mobile, 900px on desktop
- **Billing Address Option**: Switch to use address as billing address
- **Form Reset**: Cancel button restores original values
- **Grid Layout**: Responsive column layout for form fields

## Form Fields
1. **First Name** - Text input
2. **Last Name** - Text input
3. **Country** - Select dropdown (USA, Aus, Canada, NZ)
4. **Address Line 1** - Text input
5. **Address Line 2** - Text input
6. **Landmark** - Text input
7. **City** - Text input
8. **State** - Text input
9. **Zip Code** - Number input
10. **Billing Address** - Switch option

## Styling Notes
- Uses Vuetify's responsive grid system
- Consistent spacing with Vuetify classes (pa-sm-10, pa-2, mb-6)
- Centered title and description
- Responsive column layout (md="6" for some fields)
- Button styling with primary and secondary variants

## Accessibility
- Proper heading hierarchy (h4)
- Form labels for all input fields
- Placeholder text for guidance
- Button types and roles
- Responsive design considerations