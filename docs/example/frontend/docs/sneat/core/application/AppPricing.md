# AppPricing Component

## Overview
The `AppPricing` component displays pricing plans in a card-based layout with monthly/yearly toggle functionality. It provides a complete pricing section with plan comparison, features, and call-to-action buttons.

## Location
`/src/components/AppPricing.vue`

## Props Interface

```typescript
interface Pricing {
  title?: string
  cols?: number | string
  xs?: number | string
  sm?: number | string
  md?: string | number
  lg?: string | number
  xl?: string | number
}
```

### Props Details

#### `title?: string`
- **Default**: `'Pricing Plans'`
- **Description**: Main heading text for the pricing section
- **Usage**: Can be overridden via the `heading` slot

#### `cols?: number | string`
- **Default**: `12` (inherited from VCol)
- **Description**: Number of columns for all breakpoints
- **Usage**: Controls grid layout for pricing cards

#### `xs?: number | string`
- **Default**: `undefined`
- **Description**: Columns for extra small screens (< 600px)
- **Usage**: Responsive layout control

#### `sm?: number | string`
- **Default**: `undefined`
- **Description**: Columns for small screens (≥ 600px)
- **Usage**: Responsive layout control

#### `md?: string | number`
- **Default**: `undefined`
- **Description**: Columns for medium screens (≥ 960px)
- **Usage**: Responsive layout control

#### `lg?: string | number`
- **Default**: `undefined`
- **Description**: Columns for large screens (≥ 1280px)
- **Usage**: Responsive layout control

#### `xl?: string | number`
- **Default**: `undefined`
- **Description**: Columns for extra large screens (≥ 1920px)
- **Usage**: Responsive layout control

## Events Emitted
This component does not emit any events.

## Slots Available

### `heading`
**Purpose**: Customize the main heading section
**Default Content**: 
```vue
<h3 class="text-h3 mb-2">
  {{ props.title ? props.title : 'Pricing Plans' }}
</h3>
```

**Usage Example**:
```vue
<AppPricing>
  <template #heading>
    <h2 class="text-h2 mb-2">
      Custom Pricing Plans
    </h2>
  </template>
</AppPricing>
```

### `subtitle`
**Purpose**: Customize the subtitle/description section
**Default Content**:
```vue
<p class="mb-0">
  All plans include 40+ advanced tools and features to boost your product.
  <br>
  Choose the best plan to fit your needs.
</p>
```

**Usage Example**:
```vue
<AppPricing>
  <template #subtitle>
    <p class="mb-0">
      Choose the perfect plan for your business needs.
    </p>
  </template>
</AppPricing>
```

## Global State Dependencies
- None - The component is completely self-contained with internal state

## Internal Methods and Computed Properties

### Reactive Properties

#### `annualMonthlyPlanPriceToggler: Ref<boolean>`
- **Initial value**: `true`
- **Description**: Controls monthly/yearly pricing display
- **Usage**: `true` shows yearly pricing, `false` shows monthly pricing

#### `pricingPlans: Array`
Hard-coded array of pricing plan objects with the following structure:

```typescript
interface PricingPlan {
  name: string          // Plan name (Basic, Standard, Enterprise)
  tagLine: string       // Descriptive tagline
  logo: string          // Image path for plan icon
  monthlyPrice: number  // Monthly price in USD
  yearlyPrice: number   // Yearly price in USD
  isPopular: boolean    // Highlights plan as "Popular"
  current: boolean      // Marks as current user's plan
  features: string[]    // Array of feature descriptions
}
```

**Current Plans**:
1. **Basic Plan**
   - Free ($0/month, $0/year)
   - 100 responses a month
   - Unlimited forms and surveys
   - Basic features

2. **Standard Plan** ⭐ Popular
   - $49/month, $499/year
   - Unlimited responses
   - Instagram integration
   - Google Docs integration

3. **Enterprise Plan**
   - $99/month, $999/year
   - PayPal payments
   - File upload with 5GB storage
   - Custom domain support

## Usage Examples

### Basic Usage
```vue
<template>
  <AppPricing />
</template>
```

### With Custom Grid Layout
```vue
<template>
  <AppPricing 
    :md="4" 
    :sm="6" 
    :xs="12"
  />
</template>
```

### With Custom Heading
```vue
<template>
  <AppPricing title="Our Plans">
    <template #heading>
      <h2 class="text-h2 mb-2 text-primary">
        Choose Your Plan
      </h2>
    </template>
    <template #subtitle>
      <p class="mb-0 text-center">
        Flexible pricing for teams of all sizes
      </p>
    </template>
  </AppPricing>
</template>
```

### In Container Layout
```vue
<template>
  <VCard class="pricing-card">
    <VContainer>
      <AppPricing md="4">
        <template #heading>
          <h2 class="text-h2 mb-2">
            Pricing Plans
          </h2>
        </template>
      </AppPricing>
    </VContainer>
  </VCard>
</template>
```

## Component Dependencies

### Images
- `@images/misc/pricing-plan-basic.png`
- `@images/misc/pricing-plan-standard.png`
- `@images/misc/pricing-plan-enterprise.png`

### Vuetify Components
- `VLabel` - Toggle labels
- `VSwitch` - Monthly/yearly toggle
- `VIcon` - Arrow and check icons
- `VChip` - "Popular" and "Save up to 10%" badges
- `VRow` / `VCol` - Grid layout
- `VCard` / `VCardText` - Plan cards
- `VImg` - Plan logos
- `VList` / `VListItem` - Feature lists
- `VAvatar` - Feature check icons
- `VBtn` - Call-to-action buttons

### Vue Router
- Router integration for payment page navigation (`{ name: 'front-pages-payment' }`)

## Styling

### CSS Classes

#### `.card-list`
```scss
.card-list {
  --v-card-list-gap: 1rem;
}
```

#### `.save-upto-chip`
```scss
.save-upto-chip {
  inset-block-start: -2.75rem;
  inset-inline-end: -6rem;
}
```

#### `.annual-price-text`
```scss
.annual-price-text {
  inset-block-end: 3%;
  inset-inline-start: 50%;
  transform: translateX(-50%);
}
```

### Visual Features
- **Popular Plan Highlighting**: Border styling with primary color
- **Current Plan Button**: Success color for owned plans
- **Responsive Design**: Grid system adapts to different screen sizes
- **Toggle Animation**: Smooth switch between monthly/yearly pricing
- **RTL Support**: `flip-in-rtl` class for right-to-left layouts

## Business Logic

### Price Calculation
The component automatically calculates monthly equivalents for yearly plans:
```javascript
annualMonthlyPlanPriceToggler ? Math.floor(Number(plan.yearlyPrice) / 12) : plan.monthlyPrice
```

### Plan States
- **Popular**: Highlighted with border and badge
- **Current**: Shows "Your Current Plan" instead of "Upgrade"
- **Free**: Special handling for $0 plans

### Feature Presentation
Each plan displays features as a bulleted list with check icons, making it easy to compare capabilities across plans.

## Implementation Notes

1. **Static Data**: Plans are hard-coded in the component - consider moving to external configuration for production use
2. **Router Integration**: Upgrade buttons navigate to a payment page using Vue Router
3. **Responsive Grid**: Uses Vuetify's grid system with customizable breakpoints
4. **Toggle State**: Annual pricing shows monthly equivalent plus yearly total
5. **Accessibility**: Proper labeling for form controls and semantic HTML structure

## Best Practices

1. **Customize via Props**: Use grid props for responsive layouts
2. **Override via Slots**: Use slots for custom headings and descriptions
3. **Consider Data Source**: Move pricing data to external API or configuration
4. **Test Responsive**: Verify layout across different screen sizes
5. **Update Images**: Ensure pricing plan images are optimized and accessible