# EnableOneTimePasswordDialog.vue

## Overview
A dialog component for enabling one-time password (OTP) authentication via SMS. Allows users to enter their mobile phone number with country code to receive verification codes for two-factor authentication setup.

## Props Interface

```typescript
interface Props {
  mobileNumber?: string
  isDialogVisible: boolean
}
```

### Props Details
- **mobileNumber** (optional): `string` - Pre-filled mobile phone number
- **isDialogVisible** (required): `boolean` - Controls dialog visibility

## Events Emitted

```typescript
interface Emit {
  (e: 'update:isDialogVisible', value: boolean): void
  (e: 'submit', value: string): void
}
```

### Events Details
- **update:isDialogVisible**: Emitted when dialog visibility should change (v-model support)
- **submit**: Emitted when phone number is submitted with the entered phone number value

## Slots Available
This component does not expose any custom slots.

## Global State Dependencies
- No global state dependencies (Pinia stores, etc.)
- Uses Vue's built-in reactivity system only

## Internal Methods and Computed Properties

### Methods
- **formSubmit()**: Handles form submission
  - Validates that phoneNumber has a value
  - Emits 'submit' event with the phone number
  - Closes dialog by emitting 'update:isDialogVisible' with false

- **resetPhoneNumber()**: Resets phone number and closes dialog
  - Resets phoneNumber to original prop value using structuredClone
  - Closes dialog

- **dialogModelValueUpdate(val: boolean)**: Updates dialog visibility
  - Emits 'update:isDialogVisible' with the provided value

### Reactive Data
- **phoneNumber**: `ref<string>` - Local reactive copy of the mobileNumber prop using structuredClone

## Usage Examples

### Basic Usage
```vue
<template>
  <EnableOneTimePasswordDialog
    v-model:is-dialog-visible="showDialog"
    @submit="handlePhoneSubmit"
  />
</template>

<script setup lang="ts">
const showDialog = ref(false)

const handlePhoneSubmit = (phoneNumber: string) => {
  console.log('Phone number for OTP:', phoneNumber)
  // Send verification code to phone number
  sendVerificationCode(phoneNumber)
}

const enableOTP = () => {
  showDialog.value = true
}
</script>
```

### With Pre-filled Phone Number
```vue
<template>
  <EnableOneTimePasswordDialog
    v-model:is-dialog-visible="showDialog"
    :mobile-number="userPhone"
    @submit="handleOTPSetup"
  />
</template>

<script setup lang="ts">
const showDialog = ref(false)
const userPhone = ref('+1 555 123 4567')

const handleOTPSetup = async (phoneNumber: string) => {
  try {
    await setupOTPForPhone(phoneNumber)
    // Show success message
  } catch (error) {
    // Handle error
  }
}
</script>
```

### Complete OTP Setup Flow
```vue
<template>
  <div>
    <VBtn @click="startOTPSetup">
      Enable SMS Authentication
    </VBtn>
    
    <EnableOneTimePasswordDialog
      v-model:is-dialog-visible="showOTPDialog"
      :mobile-number="currentUser.phone"
      @submit="handleOTPRequest"
    />
    
    <!-- Follow-up verification dialog could go here -->
  </div>
</template>

<script setup lang="ts">
const showOTPDialog = ref(false)
const currentUser = ref({ phone: '' })

const startOTPSetup = () => {
  showOTPDialog.value = true
}

const handleOTPRequest = async (phoneNumber: string) => {
  try {
    // Send verification code
    await sendOTPCode(phoneNumber)
    
    // Update user's phone number
    currentUser.value.phone = phoneNumber
    
    // Could open verification code dialog next
    // showVerificationDialog.value = true
    
  } catch (error) {
    console.error('Failed to send OTP:', error)
  }
}
</script>
```

## Component Dependencies

### Vue Components
- **VDialog**: Vuetify dialog wrapper
- **VCard**: Vuetify card container
- **VCardText**: Vuetify card content area
- **VForm**: Vuetify form wrapper
- **VBtn**: Vuetify button component
- **AppTextField**: Custom text field component
- **DialogCloseBtn**: Custom dialog close button component

### Vue Composition API
- `ref`, `toRaw`, `defineProps`, `defineEmits`

## UI/UX Features
- **Clear Instructions**: Explains the SMS verification process
- **Country Code Support**: Placeholder shows international format (+1 123 456 7890)
- **Number Input Type**: Ensures numeric keyboard on mobile devices
- **Responsive Design**: Auto width on mobile, 900px on desktop
- **Form Validation**: Basic validation checks for phone number presence
- **Reset Functionality**: Cancel button restores original value

## Form Fields
1. **Phone Number**
   - Type: number
   - Label: "Phone Number"
   - Placeholder: "+1 123 456 7890"
   - Name: "mobile"
   - Required validation

## User Flow
1. User opens dialog to enable OTP
2. Dialog explains SMS verification process
3. User enters phone number with country code
4. User clicks Submit
5. System sends verification code to phone number
6. Dialog closes and verification process continues

## Styling Notes
- Uses Vuetify's spacing classes (pa-2, pa-sm-10, mb-6)
- Flex layout for action buttons with gap spacing
- Consistent button styling (secondary/tonal for cancel, success for submit)
- Responsive wrapping for button layout

## Security Considerations
- **Phone Number Validation**: Client-side validation for required field
- **Country Code Requirement**: Instructions specify need for country code
- **SMS Delivery**: Designed for secure SMS-based 2FA setup
- **Data Handling**: Uses structuredClone for safe data manipulation

## Accessibility
- Proper heading hierarchy (h5)
- Form labels for screen readers
- Descriptive placeholder text
- Button types and semantic structure
- Clear instructions for user guidance

## Integration Points
- **SMS Service**: Component expects external SMS sending functionality
- **2FA Setup**: Part of larger two-factor authentication setup flow
- **User Profile**: Can be integrated with user profile management
- **Verification Flow**: Typically followed by verification code entry dialog

## Data Handling
- **Structured Clone**: Uses structuredClone for deep copying to avoid reference issues
- **Prop Sync**: Local state stays synchronized with prop changes
- **Form Reset**: Proper cleanup when dialog is cancelled
- **Validation**: Ready for additional validation rules

## Error Handling Ready
- Form structure supports validation error display
- Submit button only triggers with valid phone number
- Parent component receives clear success/error feedback
- Dialog state management supports error recovery