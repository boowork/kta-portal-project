# PricingPlanDialog.vue

## Overview
A simple dialog component wrapper that displays the application's pricing plans. This component acts as a modal container for the AppPricing component, providing a clean dialog interface for pricing plan selection.

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
- No global state dependencies (Pinia stores, etc.)
- Uses Vue's built-in reactivity system only

## Internal Methods and Computed Properties

### Methods
- **dialogVisibleUpdate(val: boolean)**: Updates dialog visibility by emitting the update event

### Reactive Data
- No internal reactive data (component is a simple wrapper)

## Usage Examples

### Basic Usage
```vue
<template>
  <PricingPlanDialog v-model:is-dialog-visible="showPricing" />
</template>

<script setup lang="ts">
const showPricing = ref(false)

const openPricing = () => {
  showPricing.value = true
}
</script>
```

### Triggered by Button
```vue
<template>
  <div>
    <VBtn @click="viewPricing">
      View Pricing Plans
    </VBtn>
    
    <PricingPlanDialog v-model:is-dialog-visible="showPricingDialog" />
  </div>
</template>

<script setup lang="ts">
const showPricingDialog = ref(false)

const viewPricing = () => {
  showPricingDialog.value = true
}
</script>
```

### In User Upgrade Flow
```vue
<template>
  <div>
    <VCard>
      <VCardText>
        <p>Upgrade to unlock premium features</p>
        <VBtn @click="showPricingOptions">
          See Pricing Plans
        </VBtn>
      </VCardText>
    </VCard>
    
    <PricingPlanDialog v-model:is-dialog-visible="pricingVisible" />
  </div>
</template>

<script setup lang="ts">
const pricingVisible = ref(false)

const showPricingOptions = () => {
  pricingVisible.value = true
}
</script>
```

## Component Dependencies

### Vue Components
- **VDialog**: Vuetify dialog wrapper
- **VCard**: Vuetify card container
- **VCardText**: Vuetify card content area
- **AppPricing**: Custom pricing component (main content)
- **DialogCloseBtn**: Custom dialog close button component

### Vue Composition API
- `defineProps`, `defineEmits`

## UI/UX Features
- **Large Dialog**: 1200px width on desktop for pricing table display
- **Responsive Design**: Auto width on mobile devices
- **Modal Wrapper**: Clean modal interface around pricing content
- **Close Button**: Standard dialog close functionality
- **Full Content Display**: Spacious layout for pricing comparison

## Content Display
- **AppPricing Component**: 
  - Configured with `md="4"` prop for column layout
  - Displays pricing plans in organized format
  - Handles plan selection and interaction

## Styling Notes
- Uses `pricing-dialog` CSS class for potential custom styling
- Consistent spacing with Vuetify classes (pa-2, pa-sm-10)
- Large dialog width optimized for pricing table display
- Responsive behavior for mobile devices

## Integration Points
- **Subscription Management**: Can be integrated with subscription workflows
- **User Onboarding**: Part of user upgrade journey
- **Marketing**: Display pricing options to potential customers
- **Account Management**: Help users understand available plans

## Accessibility
- Dialog accessibility through Vuetify VDialog
- Close button for keyboard navigation
- Proper modal behavior
- Focus management handled by Vuetify

## Performance Considerations
- **Lazy Loading**: Dialog only renders when visible
- **Minimal Overhead**: Simple wrapper with no complex logic
- **Component Reuse**: AppPricing component can be used elsewhere

## Customization Options
- **Dialog Size**: Width can be adjusted for different layouts
- **Pricing Configuration**: Content controlled by AppPricing component
- **Styling**: Can be customized via pricing-dialog CSS class
- **Responsiveness**: Mobile behavior can be fine-tuned

## Data Flow
- **No Internal State**: Component doesn't manage pricing data
- **Pass-through**: All pricing logic handled by AppPricing
- **Event Bubbling**: Dialog events handled at parent level
- **Clean Separation**: UI container separated from business logic

## Use Cases
1. **Plan Comparison**: Show all available pricing tiers
2. **Upgrade Prompts**: When users hit feature limits
3. **Marketing Pages**: Pricing display in admin panels
4. **Account Settings**: Allow plan changes
5. **Onboarding**: Help new users choose appropriate plans

## Technical Notes
- **Simple Wrapper**: Minimal component complexity
- **Reusable**: Can wrap any pricing component
- **Flexible**: Easy to modify for different pricing displays
- **Maintainable**: Clear separation of concerns