# CustomRadios Component

## Overview
A specialized radio button group component that renders multiple radio options in a responsive grid layout with enhanced visual presentation. Each radio option includes a title, description, and optional subtitle, providing a rich single-selection interface for the Sneat admin template.

## Props Interface

| Prop | Type | Required | Description |
|------|------|----------|-------------|
| `selectedRadio` | `string` | Yes | Currently selected radio button value |
| `radioContent` | `CustomInputContent[]` | Yes | Array of radio button configuration objects |
| `gridColumn` | `GridColumn` | No | Responsive grid column configuration |

### CustomInputContent Interface
```typescript
interface CustomInputContent {
  title: string          // Main radio button label
  desc?: string          // Description text below title
  value: string          // Unique identifier for radio button
  subtitle?: string      // Optional subtitle shown on the right
  icon?: string | object // Optional icon (used in icon variant)
  images?: string        // Optional image (used in image variant)
}
```

### GridColumn Interface
```typescript
interface GridColumn {
  cols?: string    // Default column width
  sm?: string      // Small screen column width
  md?: string      // Medium screen column width  
  lg?: string      // Large screen column width
  xl?: string      // Extra large screen column width
  xxl?: string     // Extra extra large screen column width
}
```

## Events Emitted

| Event | Payload | Description |
|-------|---------|-------------|
| `update:selectedRadio` | `string` | Emitted when radio selection changes |

## Slots Available

### Default Slot
Provides complete customization of radio button content display.

```vue
<template #default="{ item }">
  <!-- Custom content rendering -->
</template>
```

**Slot Props:**
- `item: CustomInputContent` - The current radio button item data

## Internal Methods

### updateSelectedOption(value: string | null)
Handles radio button selection changes and emits the updated selection.
- Validates input is not null before emitting
- Emits `update:selectedRadio` event

## Usage Examples

### Basic Radio Group
```vue
<CustomRadios
  v-model:selectedRadio="selectedPlan"
  :radio-content="[
    {
      title: 'Basic Plan',
      desc: 'Perfect for individuals getting started',
      value: 'basic',
      subtitle: 'Free'
    },
    {
      title: 'Pro Plan',
      desc: 'Ideal for professionals and small teams',
      value: 'pro',
      subtitle: '$29/month'
    },
    {
      title: 'Enterprise Plan',
      desc: 'Advanced features for large organizations',
      value: 'enterprise',
      subtitle: '$99/month'
    }
  ]"
  :grid-column="{ cols: '12', md: '4' }"
/>
```

### Subscription Tiers
```vue
<CustomRadios
  v-model:selectedRadio="selectedTier"
  :radio-content="[
    {
      title: 'Starter',
      desc: 'Great for trying out our service',
      value: 'starter',
      subtitle: '$9/month'
    },
    {
      title: 'Professional',
      desc: 'Most popular choice for businesses',
      value: 'professional',
      subtitle: '$39/month'
    },
    {
      title: 'Premium',
      desc: 'Full access to all features',
      value: 'premium', 
      subtitle: '$79/month'
    }
  ]"
  :grid-column="{ cols: '12', sm: '6', lg: '4' }"
/>
```

### Delivery Options
```vue
<CustomRadios
  v-model:selectedRadio="selectedDelivery"
  :radio-content="[
    {
      title: 'Standard Delivery',
      desc: 'Delivered within 5-7 business days',
      value: 'standard',
      subtitle: 'Free'
    },
    {
      title: 'Express Delivery',
      desc: 'Delivered within 2-3 business days',
      value: 'express',
      subtitle: '+$5.99'
    },
    {
      title: 'Same Day Delivery',
      desc: 'Delivered within 24 hours',
      value: 'same-day',
      subtitle: '+$15.99'
    }
  ]"
  :grid-column="{ cols: '12', md: '6' }"
/>
```

### Custom Content with Slot
```vue
<CustomRadios
  v-model:selectedRadio="selectedHosting"
  :radio-content="hostingPlans"
  :grid-column="{ cols: '12', lg: '6' }"
>
  <template #default="{ item }">
    <div class="d-flex flex-column flex-grow-1">
      <div class="d-flex align-center justify-space-between mb-3">
        <h6 class="text-h6 text-primary">
          {{ item.title }}
        </h6>
        <div class="text-right">
          <div class="text-h5 font-weight-bold text-success">
            {{ item.subtitle }}
          </div>
          <small class="text-disabled">per month</small>
        </div>
      </div>
      
      <p class="text-body-2 text-medium-emphasis mb-3">
        {{ item.desc }}
      </p>
      
      <VList density="compact" class="bg-transparent">
        <VListItem
          v-for="feature in getHostingFeatures(item.value)"
          :key="feature"
          class="px-0"
        >
          <template #prepend>
            <VIcon
              icon="mdi-check"
              color="success"
              size="small"
            />
          </template>
          <VListItemTitle class="text-body-2">
            {{ feature }}
          </VListItemTitle>
        </VListItem>
      </VList>
    </div>
  </template>
</CustomRadios>
```

### Payment Methods
```vue
<template>
  <CustomRadios
    v-model:selectedRadio="selectedPayment"
    :radio-content="paymentMethods"
    :grid-column="{ cols: '12', sm: '6' }"
  />
</template>

<script setup>
const selectedPayment = ref('credit-card')

const paymentMethods = [
  {
    title: 'Credit Card',
    desc: 'Pay securely with your credit or debit card',
    value: 'credit-card',
    subtitle: 'Visa, MasterCard'
  },
  {
    title: 'PayPal',
    desc: 'Fast and secure payments with PayPal',
    value: 'paypal',
    subtitle: 'Instant'
  },
  {
    title: 'Bank Transfer',
    desc: 'Transfer funds directly from your bank account',
    value: 'bank-transfer',
    subtitle: '1-3 business days'
  },
  {
    title: 'Digital Wallet',
    desc: 'Apple Pay, Google Pay, Samsung Pay',
    value: 'digital-wallet',
    subtitle: 'Touch/Face ID'
  }
]
</script>
```

### Configuration Options
```vue
<CustomRadios
  v-model:selectedRadio="selectedConfig"
  :radio-content="configOptions"
  :grid-column="{ cols: '12' }"
>
  <template #default="{ item }">
    <div class="d-flex align-center flex-grow-1">
      <div class="flex-grow-1">
        <h6 class="text-subtitle-1 font-weight-medium mb-1">
          {{ item.title }}
        </h6>
        <p class="text-body-2 text-medium-emphasis mb-0">
          {{ item.desc }}
        </p>
      </div>
      <VChip
        v-if="item.subtitle"
        :color="getConfigChipColor(item.value)"
        variant="tonal"
        size="small"
      >
        {{ item.subtitle }}
      </VChip>
    </div>
  </template>
</CustomRadios>

<script setup>
const selectedConfig = ref('recommended')

const configOptions = [
  {
    title: 'Minimal Configuration',
    desc: 'Basic setup with essential features only',
    value: 'minimal',
    subtitle: 'Lightweight'
  },
  {
    title: 'Recommended Configuration',
    desc: 'Balanced setup with commonly used features',
    value: 'recommended',
    subtitle: 'Popular'
  },
  {
    title: 'Full Configuration',
    desc: 'Complete setup with all available features',
    value: 'full',
    subtitle: 'Feature-rich'
  }
]
</script>
```

### Notification Preferences
```vue
<CustomRadios
  v-model:selectedRadio="notificationFrequency"
  :radio-content="[
    {
      title: 'Real-time',
      desc: 'Get notified immediately for all activities',
      value: 'realtime',
      subtitle: 'Instant'
    },
    {
      title: 'Daily Digest',
      desc: 'Receive a summary of activities once per day',
      value: 'daily',
      subtitle: 'Once daily'
    },
    {
      title: 'Weekly Summary',
      desc: 'Get a comprehensive weekly report',
      value: 'weekly',
      subtitle: 'Every Monday'
    },
    {
      title: 'No Notifications',
      desc: 'Disable all email notifications',
      value: 'none',
      subtitle: 'Silent'
    }
  ]"
  :grid-column="{ cols: '12', sm: '6' }"
/>
```

## Component Dependencies
- `VRadioGroup` - Vuetify radio group wrapper component
- `VRow` - Vuetify row component for layout
- `VCol` - Vuetify column component for responsive grid
- `VLabel` - Vuetify label component for custom styling
- `VRadio` - Vuetify radio button component
- `VSpacer` - Vuetify spacer component for layout

## Styling
- Uses `.custom-input-wrapper` class for the radio group container
- Uses `.custom-input`, `.custom-radio` classes for individual items
- Applies `.active` class when radio button is selected
- Custom styling with 2px border width and rounded corners
- Flexible layout with proper gap spacing
- Responsive design through grid system

## CSS Classes
```scss
.custom-radio {
  display: flex;
  align-items: flex-start;
  border-width: 2px;
  gap: 0.25rem;
  
  .v-radio {
    margin-block-start: -0.45rem;
  }
  
  .cr-title {
    font-weight: 500;
    line-height: 1.375rem;
  }
}
```

## Key Features
- **Single Selection**: Enforced single selection through radio button group
- **Responsive Grid**: Automatic responsive layout with customizable columns
- **Rich Content**: Support for titles, descriptions, and subtitles
- **Custom Styling**: Enhanced visual design with borders and hover effects
- **Flexible Layout**: Slot-based customization for content rendering
- **Type Safety**: Full TypeScript support with defined interfaces
- **Accessibility**: Proper radio button grouping and labeling

## Accessibility Features
- Proper radio button group association
- Keyboard navigation support (Arrow keys, Tab, Space)
- Screen reader compatibility
- Focus management within radio group
- Selection state announcements
- Semantic HTML structure
- Proper labeling with meaningful descriptions

## Responsive Behavior
Grid columns automatically adjust based on screen size:
- Default: Uses provided `cols` value
- Small screens: Uses `sm` breakpoint configuration
- Medium screens: Uses `md` breakpoint configuration
- Large screens: Uses `lg` breakpoint configuration
- Extra large: Uses `xl` and `xxl` configurations

## State Management
The component manages selection state through:
- Two-way binding with `v-model:selectedRadio`
- Single value selection tracking
- Automatic updates on radio button interactions
- Type validation for emitted values

## Performance Considerations
- Use `key` attributes for optimal re-rendering
- Consider virtualization for very large option lists
- Implement lazy loading for dynamic content
- Optimize for touch interactions on mobile

## Common Use Cases
- **Plan Selection**: Choose subscription plans or pricing tiers
- **Preference Settings**: Single-choice user preferences
- **Configuration Options**: System or application configuration
- **Payment Methods**: Select payment option
- **Delivery Options**: Choose shipping methods
- **Notification Settings**: Select notification frequency
- **Theme Selection**: Choose application themes

## Validation Integration
```vue
<!-- With form validation -->
<VForm @submit="handleSubmit">
  <CustomRadios
    v-model:selectedRadio="formData.plan"
    :radio-content="availablePlans"
    :rules="[v => !!v || 'A plan selection is required']"
  />
</VForm>
```

## Comparison with CustomCheckboxes
| Feature | CustomRadios | CustomCheckboxes |
|---------|-------------|------------------|
| Selection | Single | Multiple |
| Model Value | `string` | `string[]` |
| Use Case | Mutually exclusive options | Multiple selections |
| HTML Element | Radio buttons | Checkboxes |

## Integration with Forms
Works seamlessly with Vue form libraries:

```vue
<!-- With VeeValidate -->
<Field name="plan" v-slot="{ field, errors }">
  <CustomRadios
    v-bind="field"
    :radio-content="planOptions"
    :error-messages="errors"
  />
</Field>

<!-- With Vuetify Forms -->
<VForm ref="form">
  <CustomRadios
    v-model:selectedRadio="selectedPlan"
    :radio-content="plans"
    :rules="planRules"
    required
  />
</VForm>
```

## File Location
`src/@core/components/app-form-elements/CustomRadios.vue`