# PaymentProvidersDialog.vue

## Overview
A dialog component that displays third-party payment providers and their supported payment methods. Shows a comprehensive list of payment service providers with their accepted credit card types, featuring theme-aware payment method logos.

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
- **visa**: Theme-aware image variant for Visa cards
- **masterCard**: Theme-aware image variant for Mastercard
- **americanEx**: Theme-aware image variant for American Express
- **jcb**: Theme-aware image variant for JCB cards
- **dc**: Theme-aware image variant for Diners Club cards
- **paymentProvidersData**: Static array of payment provider configurations

## Payment Providers Data

```typescript
const paymentProvidersData = [
  {
    title: 'Adyen',
    providers: [visa, masterCard, americanEx, jcb, dc], // All cards
  },
  {
    title: '2Checkout',
    providers: [visa, americanEx, jcb, dc], // No Mastercard
  },
  {
    title: 'Airpay',
    providers: [visa, americanEx, masterCard, jcb], // No Diners Club
  },
  {
    title: 'Authorize.net',
    providers: [americanEx, jcb, dc], // Limited selection
  },
  {
    title: 'Bambora',
    providers: [masterCard, americanEx, jcb], // No Visa/DC
  },
  {
    title: 'Bambora', // Duplicate entry
    providers: [visa, masterCard, americanEx, jcb, dc], // All cards
  },
  {
    title: 'Chase Paymentech',
    providers: [visa, americanEx, jcb, dc], // No Mastercard
  },
  {
    title: 'Checkout.com',
    providers: [visa, masterCard], // Only major cards
  },
]
```

## Usage Examples

### Basic Usage
```vue
<template>
  <PaymentProvidersDialog v-model:is-dialog-visible="showDialog" />
</template>

<script setup lang="ts">
const showDialog = ref(false)

const openProvidersDialog = () => {
  showDialog.value = true
}
</script>
```

### Triggered by Payment Setup
```vue
<template>
  <div>
    <VBtn @click="showPaymentOptions">
      View Payment Providers
    </VBtn>
    
    <PaymentProvidersDialog v-model:is-dialog-visible="showProviders" />
  </div>
</template>

<script setup lang="ts">
const showProviders = ref(false)

const showPaymentOptions = () => {
  showProviders.value = true
}

// Could be used to help users select a payment provider
// before setting up payment methods
</script>
```

### In Payment Setup Flow
```vue
<template>
  <div>
    <!-- Step 1: Choose Provider -->
    <VBtn @click="selectProvider">
      Choose Payment Provider
    </VBtn>
    
    <PaymentProvidersDialog 
      v-model:is-dialog-visible="showProviders"
    />
    
    <!-- Step 2: Configure Payment Method -->
    <!-- Payment method setup would follow -->
  </div>
</template>

<script setup lang="ts">
const showProviders = ref(false)

const selectProvider = () => {
  showProviders.value = true
}

// This dialog helps users understand which payment methods
// are supported by different providers
</script>
```

## Component Dependencies

### Vue Components
- **VDialog**: Vuetify dialog wrapper
- **VCard**: Vuetify card container
- **VCardText**: Vuetify card content area
- **VDivider**: Vuetify divider component between providers
- **DialogCloseBtn**: Custom dialog close button component

### External Dependencies
- **Payment Method Images**: Theme-aware images for each payment type
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
- **Provider Overview**: Clear list of major payment service providers
- **Supported Methods**: Visual display of accepted payment methods per provider
- **Theme-Aware Images**: Payment method logos switch automatically with theme
- **Responsive Design**: Auto width on mobile, 900px on desktop
- **Visual Separation**: Dividers between provider entries
- **Flexible Layout**: Responsive flex layout adapts to screen size

## Payment Providers Included

### Major Payment Processors
1. **Adyen** - Full payment method support
2. **2Checkout** - Most payment methods (no Mastercard)
3. **Airpay** - Most payment methods (no Diners Club)
4. **Authorize.net** - Limited selection (AmEx, JCB, Diners Club)
5. **Bambora** - Two entries with different configurations
6. **Chase Paymentech** - Most methods (no Mastercard)
7. **Checkout.com** - Major cards only (Visa, Mastercard)

### Supported Payment Methods
- **Visa**: Supported by most providers
- **Mastercard**: Widely supported
- **American Express**: Available on most platforms
- **JCB**: Good coverage across providers
- **Diners Club**: Limited support

## Image Assets
The component uses theme-aware images from:
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
- Uses Vuetify's spacing classes (pa-2, pa-sm-10, mb-6)
- Responsive flex layout with appropriate gaps (gap-4)
- Consistent image sizing (50x30px for payment method logos)
- Responsive text alignment (align-start to align-sm-center)
- Flex wrapping for mobile optimization

## Theme Support
- **Automatic Theme Detection**: Uses `useGenerateImageVariant` for theme switching
- **Brand Consistency**: Maintains payment method brand recognition across themes
- **Seamless Switching**: Images update automatically with theme changes

## Accessibility
- Proper heading hierarchy (h4, h6)
- Semantic structure with provider names and supported methods
- Image alt text through standard img elements
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

## Data Structure Notes
- **Provider Flexibility**: Easy to add/remove payment providers
- **Method Customization**: Each provider can support different payment method combinations
- **Visual Clarity**: Clear association between providers and their supported methods
- **Maintenance**: Centralized data structure for easy updates

## Integration Use Cases
- **Payment Setup**: Help users understand provider capabilities before selection
- **Merchant Onboarding**: Show available payment processing options
- **Comparison Tool**: Compare payment method support across providers
- **Documentation**: Reference for supported payment methods per provider

## Performance Considerations
- **Image Loading**: Theme-aware images loaded efficiently
- **Minimal Rendering**: Simple list structure with minimal computation
- **Responsive Images**: Consistent sizing reduces layout shift