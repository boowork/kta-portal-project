# CardStatisticsHorizontal Component

A horizontal layout statistics card component that displays key metrics with an icon, title, statistics value, and percentage change indicator.

## Location
`/src/@core/components/cards/CardStatisticsHorizontal.vue`

## Props Interface

```typescript
interface Props {
  title: string      // Main title/label for the statistic (required)
  subtitle: string   // Descriptive subtitle text (required)
  color?: string     // Color theme for the icon avatar
  icon: string       // Icon identifier for the statistic (required)
  stats: string      // Main statistic value to display (required)
  change: number     // Percentage change value (required)
}
```

### Default Values
- `color`: `'primary'`

## Events Emitted

**None** - This component is purely presentational and doesn't emit events.

## Slots Available

**None** - This component uses a fixed layout with no customizable slots.

## Global State Dependencies

**None** - This component is self-contained and doesn't depend on any global state.

## Internal Methods and Computed Properties

### Computed Properties

#### `isPositive`
- **Type**: `ComputedRef<boolean>`
- **Purpose**: Determines if the change value represents a positive trend
- **Implementation**: Uses `controlledComputed` from VueUse
- **Logic**: `Math.sign(props.change) === 1`
- **Usage**: Controls the color and visual indicators for the change percentage

## Usage Examples

### Basic Usage
```vue
<template>
  <CardStatisticsHorizontal
    title="Total Sales"
    subtitle="Last 30 days"
    icon="bx-dollar"
    stats="$12,426"
    :change="15.8"
  />
</template>
```

### With Custom Color
```vue
<template>
  <CardStatisticsHorizontal
    title="New Users"
    subtitle="This week"
    icon="bx-user-plus"
    stats="1,249"
    :change="-5.2"
    color="info"
  />
</template>
```

### Multiple Statistics Grid
```vue
<template>
  <VRow>
    <VCol cols="12" md="6" lg="3">
      <CardStatisticsHorizontal
        title="Revenue"
        subtitle="Monthly Total"
        icon="bx-trending-up"
        stats="$48,697"
        :change="42.9"
        color="success"
      />
    </VCol>
    
    <VCol cols="12" md="6" lg="3">
      <CardStatisticsHorizontal
        title="Orders"
        subtitle="Weekly Orders"
        icon="bx-cart"
        stats="2,856"
        :change="-18.6"
        color="warning"
      />
    </VCol>
    
    <VCol cols="12" md="6" lg="3">
      <CardStatisticsHorizontal
        title="Customers"
        subtitle="Active Users"
        icon="bx-group"
        stats="8,652"
        :change="62.1"
        color="primary"
      />
    </VCol>
    
    <VCol cols="12" md="6" lg="3">
      <CardStatisticsHorizontal
        title="Conversion"
        subtitle="Success Rate"
        icon="bx-target-lock"
        stats="3.2%"
        :change="12.4"
        color="error"
      />
    </VCol>
  </VRow>
</template>
```

### Dynamic Data Example
```vue
<template>
  <CardStatisticsHorizontal
    v-for="stat in statistics"
    :key="stat.id"
    :title="stat.title"
    :subtitle="stat.subtitle"
    :icon="stat.icon"
    :stats="formatNumber(stat.value)"
    :change="stat.change"
    :color="stat.color"
  />
</template>

<script setup>
import { ref } from 'vue'

const statistics = ref([
  {
    id: 1,
    title: 'Total Revenue',
    subtitle: 'Last Quarter',
    icon: 'bx-dollar-circle',
    value: 145698.50,
    change: 23.5,
    color: 'success'
  },
  {
    id: 2,
    title: 'New Subscribers',
    subtitle: 'This Month',
    icon: 'bx-user-plus',
    value: 8946,
    change: -7.2,
    color: 'primary'
  }
])

const formatNumber = (value) => {
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 0
  }).format(value)
}
</script>
```

## Component Dependencies

### Vue Composition API
- `withDefaults`: For prop defaults
- `defineProps`: For prop definition

### VueUse Composables
- `controlledComputed`: For reactive computed properties with control

### Vuetify Components
- **VCard**: Main container card component
- **VCardText**: Card content wrapper with proper spacing
- **VAvatar**: Circular icon container with color theming
- **VIcon**: Icon display component

## Layout Structure

The component uses a horizontal flexbox layout with the following structure:

```
VCard
└── VCardText (d-flex justify-space-between align-start)
    ├── Left Section (d-flex flex-column gap-y-1)
    │   ├── Title (text-high-emphasis)
    │   ├── Stats & Change (d-flex align-center flex-wrap gap-2)
    │   │   ├── Stats Value (text-h4)
    │   │   └── Change Percentage (conditional, colored)
    │   └── Subtitle (text-base)
    └── Right Section
        └── Icon Avatar (size="40", rounded, tonal variant)
```

## Styling Classes

### Layout Classes
- `d-flex justify-space-between align-start`: Main content layout
- `d-flex flex-column gap-y-1`: Left section vertical stacking
- `d-flex align-center flex-wrap gap-2`: Stats and change horizontal layout

### Typography Classes
- `text-high-emphasis`: High contrast title text
- `text-h4`: Large heading for statistics value
- `text-base`: Standard text size for subtitle and change
- `text-success` / `text-error`: Conditional coloring for change percentage

### Color Variants
Supports all Vuetify color variants for the icon avatar:
- `primary` (default)
- `secondary`
- `success`
- `info`
- `warning`
- `error`
- Custom color names

## Visual Behavior

### Change Indicator
- **Positive values**: Display with `+` prefix and green color (`text-success`)
- **Negative values**: Display with `-` prefix and red color (`text-error`)
- **Zero values**: No special styling applied

### Icon Avatar
- **Size**: 40px diameter
- **Shape**: Rounded corners
- **Variant**: Tonal (colored background with transparency)
- **Icon Size**: 24px

## Accessibility

- Uses semantic HTML structure
- Proper text contrast with `text-high-emphasis` for titles
- Color coding supplemented with `+`/`-` symbols for change indicators
- Icon provides visual context for the statistic type

## Notes

- Designed for displaying KPI and metrics data
- Change percentage is always displayed as absolute value with appropriate sign
- Icon should be chosen to represent the type of statistic being displayed
- Color prop affects only the icon avatar, not the text elements
- Component is fully responsive and works well in grid layouts