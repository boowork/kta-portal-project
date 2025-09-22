# TwoFactorAuthDialog.vue

## Overview
A dialog component for selecting two-factor authentication methods. Provides users with options between authenticator apps and SMS-based verification, then launches the appropriate setup dialog based on their selection. Features visual method selection with detailed descriptions.

## Props Interface

```typescript
interface Props {
  isDialogVisible: boolean
  smsCode?: string
  authAppCode?: string
}
```

### Props Details
- **isDialogVisible** (required): `boolean` - Controls dialog visibility
- **smsCode** (optional): `string` - Pre-filled SMS code for EnableOneTimePasswordDialog
- **authAppCode** (optional): `string` - Pre-filled authentication code for AddAuthenticatorAppDialog

### Default Values
```typescript
{
  isDialogVisible: false,
  smsCode: '',
  authAppCode: '',
}
```

## Events Emitted

```typescript
interface Emit {
  (e: 'update:isDialogVisible', value: boolean): void
}
```

### Events Details
- **update:isDialogVisible**: Emitted when dialog visibility should change (v-model support)

## Slots Available
This component does not expose any custom slots.

## Global State Dependencies
- No global state dependencies (Pinia stores, etc.)
- Uses Vue's built-in reactivity system only

## Internal Methods and Computed Properties

### Methods
- **openSelectedMethodDialog()**: Opens the appropriate setup dialog based on selected method
  - Routes to AddAuthenticatorAppDialog for 'authApp' selection
  - Routes to EnableOneTimePasswordDialog for 'sms' selection
  - Closes current dialog and manages dialog state

### Reactive Data
- **selectedMethod**: `ref<string>` - Currently selected authentication method ('authApp' or 'sms')
- **isAuthAppDialogVisible**: `ref<boolean>` - Controls authenticator app dialog visibility
- **isSmsDialogVisible**: `ref<boolean>` - Controls SMS dialog visibility

### Static Data
- **authMethods**: Array defining available authentication methods with descriptions

## Authentication Methods

```typescript
const authMethods = [
  {
    icon: 'bx-cog',
    title: 'Authenticator Apps',
    desc: 'Get code from an app like Google Authenticator or Microsoft Authenticator.',
    value: 'authApp',
  },
  {
    icon: 'bx-message',
    title: 'SMS',
    desc: 'We will send a code via SMS if you need to use your backup login method.',
    value: 'sms',
  },
]
```

## Usage Examples

### Basic Usage
```vue
<template>
  <TwoFactorAuthDialog v-model:is-dialog-visible="show2FA" />
</template>

<script setup lang="ts">
const show2FA = ref(false)

const enable2FA = () => {
  show2FA.value = true
}
</script>
```

### With Pre-filled Codes
```vue
<template>
  <TwoFactorAuthDialog
    v-model:is-dialog-visible="show2FA"
    :sms-code="userPhone"
    :auth-app-code="existingCode"
  />
</template>

<script setup lang="ts">
const show2FA = ref(false)
const userPhone = ref('+1 555 123 4567')
const existingCode = ref('123456')

const setup2FA = () => {
  show2FA.value = true
}
</script>
```

### In Security Settings
```vue
<template>
  <div>
    <VCard title="Security Settings">
      <VCardText>
        <VSwitch
          :model-value="user.has2FA"
          label="Two-Factor Authentication"
          @update:model-value="handle2FAToggle"
        />
      </VCardText>
    </VCard>
    
    <TwoFactorAuthDialog
      v-model:is-dialog-visible="show2FASetup"
      :sms-code="user.phone"
    />
  </div>
</template>

<script setup lang="ts">
const show2FASetup = ref(false)
const user = ref({
  has2FA: false,
  phone: '+1 555 123 4567'
})

const handle2FAToggle = (enabled: boolean) => {
  if (enabled && !user.value.has2FA) {
    show2FASetup.value = true
  } else if (!enabled) {
    // Handle 2FA disable
    disable2FA()
  }
}
</script>
```

## Component Dependencies

### Vue Components
- **VDialog**: Vuetify dialog wrapper
- **VCard**: Vuetify card container
- **VCardText**: Vuetify card content area
- **VBtn**: Button components for actions
- **VIcon**: Icon components for method indicators
- **CustomRadios**: Custom radio selection component
- **DialogCloseBtn**: Custom dialog close button component

### Child Dialog Components
- **AddAuthenticatorAppDialog**: For authenticator app setup
- **EnableOneTimePasswordDialog**: For SMS setup

### Vue Composition API
- `ref`, `defineProps`, `defineEmits`, `withDefaults`

## UI/UX Features
- **Method Selection**: Visual radio selection with icons and descriptions
- **Clear Instructions**: Detailed explanations for each authentication method
- **Seamless Flow**: Automatically transitions to selected method's setup dialog
- **Responsive Design**: Auto width on mobile, 800px on desktop
- **Method Comparison**: Side-by-side display of authentication options

## Authentication Flow

### 1. Method Selection
- User sees two authentication options
- Each method has icon, title, and detailed description
- Radio button selection for choosing preferred method

### 2. Transition to Setup
- Continue button opens appropriate setup dialog
- Current dialog closes smoothly
- Setup dialog receives any pre-filled data

### 3. Method-Specific Setup
- **Authenticator App**: QR code scanning and code verification
- **SMS**: Phone number entry and verification code sending

## Dialog State Management
- **Primary Dialog**: Method selection interface
- **Child Dialogs**: Method-specific setup dialogs
- **State Coordination**: Proper dialog switching without overlap
- **Data Passing**: Codes passed from parent to child dialogs

## Styling Notes
- Uses Vuetify's spacing classes (pa-2, pa-sm-10, mb-6)
- Icon and text layout with proper spacing
- Centered buttons with gap spacing (gap-4)
- Custom radio styling through CustomRadios component

## Method Descriptions

### Authenticator Apps
- **Icon**: Settings/cog (bx-cog)
- **Apps**: Google Authenticator, Microsoft Authenticator
- **Process**: QR code scanning for setup
- **Security**: Time-based one-time passwords (TOTP)

### SMS
- **Icon**: Message (bx-message)
- **Purpose**: Backup authentication method
- **Process**: Phone number verification
- **Delivery**: SMS code delivery

## Accessibility
- Proper heading hierarchy (h4, h6)
- Icon alternatives with descriptive text
- Radio button accessibility through CustomRadios
- Clear method descriptions for screen readers
- Semantic button structure

## Integration Points
- **Security Settings**: Part of account security configuration
- **User Onboarding**: Optional security enhancement during signup
- **Recovery Flow**: Method selection during account recovery
- **Admin Panel**: Bulk 2FA enforcement for organizations

## Security Considerations
- **Method Choice**: Allows users to select preferred security method
- **Backup Options**: SMS as fallback for authenticator apps
- **Code Validation**: Proper setup verification in child dialogs
- **Security Education**: Clear descriptions help users understand options

## Data Flow
- **Props to Children**: Passes codes to appropriate setup dialogs
- **State Management**: Coordinates multiple dialog visibility states
- **Event Handling**: Manages dialog transitions and closures
- **Method Routing**: Directs users to correct setup flow

## Customization Options
- **Method Addition**: Easy to add new authentication methods
- **Icon Customization**: Method icons can be changed
- **Description Updates**: Method descriptions easily modifiable
- **Dialog Sizing**: Responsive design easily adjustable

## Performance Considerations
- **Conditional Rendering**: Child dialogs only render when needed
- **State Optimization**: Minimal reactive data for smooth transitions
- **Component Loading**: Child dialogs loaded on demand
- **Memory Management**: Proper cleanup of dialog states