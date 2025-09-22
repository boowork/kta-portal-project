# CardStatisticsVerticalSimple.vue

## Overview
A simple vertical statistics card component that displays an icon, statistic value, and title in a clean, centered layout. Perfect for dashboard KPIs and metric displays.

## Props Interface

```typescript
interface Props {
  title: string      // Required - The descriptive title/label for the statistic
  color?: string     // Optional - Color theme for the icon (default: 'primary')
  icon: string       // Required - Icon identifier for the statistic
  stats: string      // Required - The numeric value or statistic to display
}
```

## Events Emitted
None - component is purely presentational.

## Slots Available
None - component uses props for all content.

## Global State Dependencies
None - component is fully self-contained.

## Internal Methods and Computed Properties

### Default Values
- `color`: Defaults to 'primary' if not provided

## Component Dependencies

### Vuetify Components
- `VCard`: Main container
- `VCardText`: Content wrapper with flex layout
- `VAvatar`: Icon container with tonal variant
- `VIcon`: Icon display

## Usage Examples

### Basic Usage
```vue
<template>
  <CardStatisticsVerticalSimple
    title="Total Users"
    icon="bx-user"
    stats="1,234"
  />
</template>
```

### Custom Color
```vue
<template>
  <CardStatisticsVerticalSimple
    title="Revenue"
    icon="bx-dollar"
    stats="$45,678"
    color="success"
  />
</template>
```

### Multiple Statistics Grid
```vue
<template>
  <VRow>
    <VCol cols="12" sm="6" md="3">
      <CardStatisticsVerticalSimple
        title="Total Orders"
        icon="bx-cart"
        stats="856"
        color="primary"
      />
    </VCol>
    <VCol cols="12" sm="6" md="3">
      <CardStatisticsVerticalSimple
        title="Customers"
        icon="bx-user"
        stats="1,234"
        color="info"
      />
    </VCol>
    <VCol cols="12" sm="6" md="3">
      <CardStatisticsVerticalSimple
        title="Revenue"
        icon="bx-dollar"
        stats="$45,678"
        color="success"
      />
    </VCol>
    <VCol cols="12" sm="6" md="3">
      <CardStatisticsVerticalSimple
        title="Growth"
        icon="bx-trending-up"
        stats="+23%"
        color="warning"
      />
    </VCol>
  </VRow>
</template>
```

### Dashboard Implementation
```vue
<template>
  <div class="dashboard-stats">
    <VRow>
      <VCol 
        v-for="stat in statistics" 
        :key="stat.title"
        cols="12" 
        sm="6" 
        lg="3"
      >
        <CardStatisticsVerticalSimple
          :title="stat.title"
          :icon="stat.icon"
          :stats="stat.value"
          :color="stat.color"
        />
      </VCol>
    </VRow>
  </div>
</template>

<script setup>
const statistics = ref([
  {
    title: 'Active Users',
    icon: 'bx-user-check',
    value: '2,456',
    color: 'primary'
  },
  {
    title: 'Total Sales',
    icon: 'bx-shopping-bag',
    value: '$89,234',
    color: 'success'
  },
  {
    title: 'Pending Orders',
    icon: 'bx-time',
    value: '124',
    color: 'warning'
  },
  {
    title: 'Conversion Rate',
    icon: 'bx-trending-up',
    value: '3.2%',
    color: 'info'
  }
])
</script>
```

## Layout Structure

```
┌─────────────────────────┐
│                         │
│         [Icon]          │
│                         │
│       [Statistics]      │
│                         │
│        [Title]          │
│                         │
└─────────────────────────┘
```

## Styling Features

### Layout
- Vertical flex column layout with center alignment
- Consistent padding through VCardText
- Centered content alignment

### Icon Styling
- 40px avatar size for consistent visual weight
- Tonal variant for subtle background color
- Rounded avatar shape
- Color customization support

### Typography
- Statistics: `text-h5` for prominence
- Title: `text-body-1` for readability
- Proper spacing with padding and margins

## Color Theme Support

The component supports Vuetify's color system:

- `primary` (default)
- `secondary`
- `success`
- `info`
- `warning`
- `error`
- Custom color values

## Responsive Considerations

- Card automatically adjusts to container width
- Icon size remains consistent across devices
- Typography scales with Vuetify's responsive typography
- Works well in grid layouts with responsive columns

## Accessibility Features

- Semantic card structure
- Proper heading hierarchy
- Icon provides visual context
- High contrast with tonal avatar variants

## Best Practices

1. **Consistent Sizing**: Use in grid layouts for uniform appearance
2. **Meaningful Icons**: Choose icons that clearly represent the data
3. **Color Coding**: Use colors consistently across similar metrics
4. **Number Formatting**: Format statistics for readability (commas, currency)
5. **Loading States**: Consider skeleton loaders while data loads

## Common Use Cases

- Dashboard KPI displays
- Analytics summaries
- Performance metrics
- Business statistics
- User engagement metrics
- Financial indicators
- System monitoring stats