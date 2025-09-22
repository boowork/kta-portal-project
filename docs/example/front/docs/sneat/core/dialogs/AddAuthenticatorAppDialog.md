# AddAuthenticatorAppDialog.vue

## Overview
A dialog component for adding an authenticator app to enable two-factor authentication. The dialog displays a QR code and manual secret key, allowing users to set up their authenticator app and enter the generated authentication code.

## Props Interface

```typescript
interface Props {
  authCode?: string      // Optional pre-filled authentication code
  isDialogVisible: boolean  // Controls dialog visibility
}
```

### Props Details
- **authCode** (optional): `string` - Pre-filled authentication code value
- **isDialogVisible** (required): `boolean` - Controls whether the dialog is visible

## Events Emitted

```typescript
interface Emit {
  (e: 'update:isDialogVisible', value: boolean): void
  (e: 'submit', value: string): void
}
```

### Events Details
- **update:isDialogVisible**: Emitted when dialog visibility should change (v-model support)
- **submit**: Emitted when user submits the authentication code with the entered code value

## Slots Available
This component does not expose any custom slots.

## Global State Dependencies
- No global state dependencies (Pinia stores, etc.)
- Uses Vue's built-in reactivity system only

## Internal Methods and Computed Properties

### Methods
- **formSubmit()**: Validates and submits the authentication code
  - Checks if authCode has a value
  - Emits 'submit' event with the code
  - Closes the dialog by emitting 'update:isDialogVisible' with false

- **resetAuthCode()**: Resets the authentication code and closes dialog
  - Resets authCode to original prop value using structuredClone
  - Closes the dialog

### Reactive Data
- **authCode**: `ref<string>` - Local reactive copy of the authCode prop using structuredClone

## Usage Examples

### Basic Usage
```vue
<template>
  <AddAuthenticatorAppDialog
    v-model:is-dialog-visible="showDialog"
    @submit="handleAuthCodeSubmit"
  />
</template>

<script setup lang="ts">
const showDialog = ref(false)

const handleAuthCodeSubmit = (code: string) => {
  console.log('Authentication code submitted:', code)
  // Handle the submitted code
}
</script>
```

### With Pre-filled Code
```vue
<template>
  <AddAuthenticatorAppDialog
    v-model:is-dialog-visible="showDialog"
    :auth-code="defaultCode"
    @submit="handleAuthCodeSubmit"
  />
</template>

<script setup lang="ts">
const showDialog = ref(false)
const defaultCode = ref('123456')

const handleAuthCodeSubmit = (code: string) => {
  // Process authentication code
}
</script>
```

## Component Dependencies

### Vue Components
- **VDialog**: Vuetify dialog wrapper
- **VCard**: Vuetify card container
- **VCardText**: Vuetify card content area
- **VImg**: Vuetify image component for QR code
- **VAlert**: Vuetify alert component for manual key display
- **VForm**: Vuetify form wrapper
- **VBtn**: Vuetify button component
- **AppTextField**: Custom text field component
- **DialogCloseBtn**: Custom dialog close button component

### External Dependencies
- **@images/pages/themeselection-qr.png**: QR code image asset

### Vue Composition API
- `ref`, `toRaw`, `defineProps`, `defineEmits`

## UI/UX Features
- Responsive design (auto width on mobile, 900px on desktop)
- QR code display for easy scanning
- Manual secret key fallback with warning alert
- Form validation (requires authentication code)
- Cancel and Submit action buttons
- Close button in dialog header

## Styling Notes
- Uses Vuetify's spacing classes (pa-2, pa-sm-10, mb-6, etc.)
- Responsive padding and margins
- Centered QR code image (mx-auto)
- Flex layout for button actions with gap spacing
- Typography classes for headings and body text

## Accessibility
- Proper heading hierarchy (h4, h5)
- Form labels and placeholders
- Button types and roles
- Alert component for important information