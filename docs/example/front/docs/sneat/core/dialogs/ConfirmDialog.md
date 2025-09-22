# ConfirmDialog.vue

## Overview
A comprehensive confirmation dialog system with three-state workflow: initial confirmation, success feedback, and cancellation feedback. Features customizable messages and titles for each state, with visual icons and appropriate color schemes.

## Props Interface

```typescript
interface Props {
  confirmationQuestion: string
  isDialogVisible: boolean
  confirmTitle: string
  confirmMsg: string
  cancelTitle: string
  cancelMsg: string
}
```

### Props Details
- **confirmationQuestion** (required): `string` - The main question/prompt shown in confirmation dialog
- **isDialogVisible** (required): `boolean` - Controls dialog visibility
- **confirmTitle** (required): `string` - Title shown in success dialog after confirmation
- **confirmMsg** (required): `string` - Message shown in success dialog after confirmation
- **cancelTitle** (required): `string` - Title shown in cancellation dialog after cancel
- **cancelMsg** (required): `string` - Message shown in cancellation dialog after cancel

## Events Emitted

```typescript
interface Emit {
  (e: 'update:isDialogVisible', value: boolean): void
  (e: 'confirm', value: boolean): void
}
```

### Events Details
- **update:isDialogVisible**: Emitted when dialog visibility should change (v-model support)
- **confirm**: Emitted with boolean value (true for confirmed, false for cancelled)

## Slots Available
This component does not expose any custom slots.

## Global State Dependencies
- No global state dependencies (Pinia stores, etc.)
- Uses Vue's built-in reactivity system only

## Internal Methods and Computed Properties

### Methods
- **updateModelValue(val: boolean)**: Updates dialog visibility by emitting update event

- **onConfirmation()**: Handles user confirmation
  - Emits 'confirm' event with true
  - Closes main dialog
  - Shows success dialog (unsubscribed state)

- **onCancel()**: Handles user cancellation
  - Emits 'confirm' event with false
  - Closes main dialog
  - Shows cancellation dialog (cancelled state)

### Reactive Data
- **unsubscribed**: `ref<boolean>` - Controls success dialog visibility
- **cancelled**: `ref<boolean>` - Controls cancellation dialog visibility

## Dialog States

### 1. Confirmation Dialog (Primary)
- **Purpose**: Show initial confirmation question
- **Icon**: Warning exclamation mark (!)
- **Color**: Warning (orange/yellow)
- **Actions**: Confirm, Cancel

### 2. Success Dialog (After Confirmation)
- **Purpose**: Show success feedback after confirmation
- **Icon**: Check mark (✓)
- **Color**: Success (green)
- **Content**: Custom confirmTitle and confirmMsg
- **Action**: OK button

### 3. Cancellation Dialog (After Cancel)
- **Purpose**: Show feedback after cancellation
- **Icon**: X mark
- **Color**: Error (red)
- **Content**: Custom cancelTitle and cancelMsg
- **Action**: OK button

## Usage Examples

### Basic Confirmation
```vue
<template>
  <ConfirmDialog
    v-model:is-dialog-visible="showConfirm"
    confirmation-question="Are you sure you want to delete this item?"
    confirm-title="Deleted!"
    confirm-msg="The item has been successfully deleted."
    cancel-title="Cancelled"
    cancel-msg="The item deletion was cancelled."
    @confirm="handleConfirmation"
  />
</template>

<script setup lang="ts">
const showConfirm = ref(false)

const handleConfirmation = (confirmed: boolean) => {
  if (confirmed) {
    console.log('User confirmed action')
    // Perform the confirmed action
  } else {
    console.log('User cancelled action')
    // Handle cancellation
  }
}

const deleteItem = () => {
  showConfirm.value = true
}
</script>
```

### Subscription Confirmation
```vue
<template>
  <ConfirmDialog
    v-model:is-dialog-visible="showUnsubscribe"
    confirmation-question="Are you sure you want to unsubscribe?"
    confirm-title="Unsubscribed!"
    confirm-msg="You have been successfully unsubscribed from our newsletter."
    cancel-title="Still Subscribed"
    cancel-msg="You remain subscribed to our newsletter."
    @confirm="handleUnsubscribe"
  />
</template>

<script setup lang="ts">
const showUnsubscribe = ref(false)

const handleUnsubscribe = async (confirmed: boolean) => {
  if (confirmed) {
    try {
      await unsubscribeUser()
      // Success dialog will show automatically
    } catch (error) {
      // Handle error
    }
  }
  // Cancellation dialog will show automatically
}
</script>
```

### Account Deletion Confirmation
```vue
<template>
  <ConfirmDialog
    v-model:is-dialog-visible="showDeleteAccount"
    confirmation-question="Are you sure you want to delete your account? This action cannot be undone."
    confirm-title="Account Deleted"
    confirm-msg="Your account has been permanently deleted. We're sorry to see you go!"
    cancel-title="Account Safe"
    cancel-msg="Your account deletion was cancelled. Your account remains active."
    @confirm="handleAccountDeletion"
  />
</template>

<script setup lang="ts">
const showDeleteAccount = ref(false)

const handleAccountDeletion = async (confirmed: boolean) => {
  if (confirmed) {
    await deleteUserAccount()
    // Redirect to goodbye page after success dialog
  }
}
</script>
```

## Component Dependencies

### Vue Components
- **VDialog**: Vuetify dialog wrapper (used 3 times)
- **VCard**: Vuetify card container
- **VCardText**: Vuetify card content area
- **VBtn**: Vuetify button component
- **VIcon**: Vuetify icon component (for check mark)

### Vue Composition API
- `ref`, `defineProps`, `defineEmits`

## UI/UX Features
- **Three-State Workflow**: Confirmation → Feedback (Success/Cancel)
- **Visual Feedback**: Color-coded icons and buttons for each state
- **Consistent Sizing**: All dialogs use 500px max width
- **Center Alignment**: All content is centered for emphasis
- **Icon Buttons**: Large circular icon buttons for visual impact
- **Customizable Content**: Fully customizable titles and messages
- **Non-Interactive Icons**: Icons set to pointer-events: none

## Icon Design
- **Warning Icon**: Large exclamation mark (!) in warning color
- **Success Icon**: Check mark icon (bx-check) in success color
- **Error Icon**: Large X mark in error color
- **Size**: All icons in 88x88px containers with appropriate font sizes

## Color Scheme
- **Warning**: Orange/yellow for initial confirmation
- **Success**: Green for positive confirmation
- **Error**: Red for cancellation
- **Secondary**: Gray for cancel buttons

## Styling Notes
- Uses Vuetify's spacing classes (px-10, py-6, my-4)
- Center text alignment throughout
- Large icon buttons with consistent sizing
- Gap spacing for button groups (gap-2)
- Font weight and size classes for typography

## Accessibility
- Proper heading hierarchy
- Icon alternatives with text/symbols
- Color coding with icon reinforcement
- Semantic button types and actions
- Clear visual hierarchy

## State Management
- **Local State**: Uses refs for dialog state management
- **Automatic Transitions**: Smooth transitions between dialog states
- **Event-Driven**: Parent component receives clear confirmation events
- **Reset Behavior**: Dialogs automatically reset when closed

## Customization Options
- **Fully Customizable Text**: All user-facing text can be customized
- **Icon Options**: Icons can be modified by changing icon names or symbols
- **Color Themes**: Colors follow Vuetify theme system
- **Size Options**: Dialog max-width can be adjusted
- **Button Text**: Confirm/Cancel button text can be customized