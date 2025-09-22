# AddPaymentMethodDialog.vue

## Overview
A dialog component that displays supported payment methods for adding to a user's account. Shows a list of major credit card providers with their brand images and card types, optimized for both light and dark themes.

## Props Interface

```typescript
interface Props {
  isDialogVisible: boolean
}
```

### Props Details
- **isDialogVisible** (required): `boolean` - Controls dialog visibility

## Events Emitted

```typescript
interface Emit {
  (e: 'update:isDialogVisible', val: boolean): void
}
```

### Events Details
- **update:isDialogVisible**: Emitted when dialog visibility should change (v-model support)

## Slots Available
This component does not expose any custom slots.

## Global State Dependencies
- **useGenerateImageVariant**: Global composable for theme-aware image variants

## Internal Methods and Computed Properties

### Methods
- **dialogVisibleUpdate(val: boolean)**: Updates dialog visibility by emitting the update event

### Reactive Data
- **visa**: Theme-aware image variant for Visa card
- **masterCard**: Theme-aware image variant for Mastercard
- **americanEx**: Theme-aware image variant for American Express
- **jcb**: Theme-aware image variant for JCB card
- **dc**: Theme-aware image variant for Diners Club card
- **paymentMethodsData**: Static array of payment method configurations

### Payment Methods Data
```typescript
const paymentMethodsData = [
  { title: 'Visa', type: 'Credit Card', img: visa },
  { title: 'American Express', type: 'Credit Card', img: americanEx },
  { title: 'Mastercard', type: 'Credit Card', img: masterCard },
  { title: 'JCB', type: 'Credit Card', img: jcb },
  { title: 'Diners Club', type: 'Credit Card', img: dc },
]
```

## Usage Examples

### Basic Usage
```vue
<template>
  <AddPaymentMethodDialog v-model:is-dialog-visible="showDialog" />
</template>

<script setup lang="ts">
const showDialog = ref(false)

const openPaymentMethods = () => {
  showDialog.value = true
}
</script>
```

### Triggered by Button
```vue
<template>
  <div>
    <VBtn @click="showPaymentDialog">
      Add Payment Method
    </VBtn>
    
    <AddPaymentMethodDialog v-model:is-dialog-visible="showDialog" />
  </div>
</template>

<script setup lang="ts">
const showDialog = ref(false)

const showPaymentDialog = () => {
  showDialog.value = true
}
</script>
```

## Component Dependencies

### Vue Components
- **VDialog**: Vuetify dialog wrapper
- **VCard**: Vuetify card container
- **VCardText**: Vuetify card content area
- **VImg**: Vuetify image component for payment method logos
- **VDivider**: Vuetify divider component between payment methods
- **DialogCloseBtn**: Custom dialog close button component

### External Dependencies
- **Payment Method Images**: Theme-aware images for each payment provider
  - Visa (light/dark variants)
  - Mastercard (light/dark variants)
  - American Express (light/dark variants)
  - JCB (light/dark variants)
  - Diners Club (light/dark variants)

### Composables
- **useGenerateImageVariant**: For theme-aware image switching

### Vue Composition API
- `defineProps`, `defineEmits`

## UI/UX Features
- **Theme-Aware Images**: Payment method logos automatically switch between light and dark variants
- **Responsive Design**: Auto width on mobile, 750px on desktop
- **Payment Method List**: Clean list display of supported payment providers
- **Visual Hierarchy**: Card logos with consistent sizing and spacing
- **Type Display**: Shows "Credit Card" type (hidden on small screens for space)
- **Dividers**: Visual separation between payment method options

## Image Assets
The component uses the following image assets:
- `@images/icons/payments/img/visa-light.png`
- `@images/icons/payments/img/visa-dark.png`
- `@images/icons/payments/img/mastercard.png`
- `@images/icons/payments/img/master-dark.png`
- `@images/icons/payments/img/american-express.png`
- `@images/icons/payments/img/ae-dark.png`
- `@images/icons/payments/img/jcb-light.png`
- `@images/icons/payments/img/jcb-dark.png`
- `@images/icons/payments/img/dc-light.png`
- `@images/icons/payments/img/dc-dark.png`

## Styling Notes
- Uses Vuetify's spacing classes (pa-2, pa-sm-10, mb-6, etc.)
- Responsive layout with flex alignment
- Payment method images sized at 50x30px
- Type information hidden on small screens (d-none d-sm-block)
- Dividers between items (except last item)

## Theme Support
- **Automatic Theme Detection**: Uses `useGenerateImageVariant` to switch between light and dark payment method logos
- **Consistent Branding**: Maintains brand recognition across theme changes

## Accessibility
- Proper heading hierarchy (h4, h6)
- Image alt text through Vuetify VImg component
- Semantic structure with proper content hierarchy
- Responsive design considerations

## Custom Styles
Includes unused CSS for refer-link-input styling:
```scss
.refer-link-input {
  .v-field--appended {
    padding-inline-end: 0;
  }
  .v-field__append-inner {
    padding-block-start: 0.125rem;
  }
}
```

## Integration Notes
- This is a display-only dialog - no form functionality
- Designed to show supported payment methods before redirecting to actual payment form
- Could be extended with click handlers for method selection
- Optimized for credit card providers but extensible for other payment types