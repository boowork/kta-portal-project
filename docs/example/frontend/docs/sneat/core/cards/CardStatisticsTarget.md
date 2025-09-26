# CardStatisticsTarget Component

A target-style statistics card component with a centered layout, featuring an icon, statistics value, subtitle, and change indicator with an integrated dropdown menu.

## Location
`/src/@core/components/cards/CardStatisticsTarget.vue`

## Props Interface

```typescript
interface Props {
  title: string      // Header title text (required)
  color?: string     // Color theme for the icon avatar
  icon: string       // Icon identifier for the statistic (required)
  stats: string      // Main statistic value to display (required)
  change: number     // Percentage change value (required)
  subtitle: string   // Descriptive subtitle text (required)
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
- **Usage**: Controls the color and icon direction for the change indicator

## Usage Examples

### Basic Usage
```vue
<template>
  <CardStatisticsTarget
    title="Sales Target"
    icon="bx-dollar"
    stats="$24,895"
    :change="18.4"
    subtitle="Monthly Goal"
  />
</template>
```

### With Custom Color
```vue
<template>
  <CardStatisticsTarget
    title="User Engagement"
    icon="bx-user"
    stats="89.2%"
    :change="-2.1"
    subtitle="Active Users"
    color="info"
  />
</template>
```

### Dashboard Grid Layout
```vue
<template>
  <VRow>
    <VCol cols="12" md="6" lg="4">
      <CardStatisticsTarget
        title="Revenue"
        icon="bx-trending-up"
        stats="$156,892"
        :change="24.8"
        subtitle="Quarterly Growth"
        color="success"
      />
    </VCol>
    
    <VCol cols="12" md="6" lg="4">
      <CardStatisticsTarget
        title="Conversion"
        icon="bx-target-lock"
        stats="12.4%"
        :change="5.7"
        subtitle="Lead to Sale"
        color="warning"
      />
    </VCol>
    
    <VCol cols="12" md="6" lg="4">
      <CardStatisticsTarget
        title="Customer Satisfaction"
        icon="bx-happy"
        stats="94.7%"
        :change="-1.2"
        subtitle="Support Rating"
        color="primary"
      />
    </VCol>
  </VRow>
</template>
```

### Dynamic Data Example
```vue
<template>
  <div class="statistics-grid">
    <CardStatisticsTarget
      v-for="target in targets"
      :key="target.id"
      :title="target.title"
      :icon="target.icon"
      :stats="target.formattedStats"
      :change="target.change"
      :subtitle="target.subtitle"
      :color="target.color"
    />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const rawTargets = ref([
  {
    id: 1,
    title: 'Monthly Sales',
    icon: 'bx-cart',
    value: 48697.25,
    change: 32.1,
    subtitle: 'Target: $50K',
    color: 'success',
    format: 'currency'
  },
  {
    id: 2,
    title: 'New Customers',
    icon: 'bx-user-plus',
    value: 1847,
    change: -8.4,
    subtitle: 'This Quarter',
    color: 'primary',
    format: 'number'
  }
])

const targets = computed(() => 
  rawTargets.value.map(target => ({
    ...target,
    formattedStats: target.format === 'currency' 
      ? new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(target.value)
      : target.value.toLocaleString()
  }))
)
</script>

<style scoped>
.statistics-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 1rem;
}
</style>
```

## Component Dependencies

### Vue Composition API
- `withDefaults`: For prop defaults
- `defineProps`: For prop definition

### VueUse Composables
- `controlledComputed`: For reactive computed properties with control

### Vuetify Components
- **VCard**: Main container card component
- **VCardText**: Card content wrapper sections
- **VBtn**: Dropdown trigger button
- **VMenu**: Dropdown menu component
- **VList**: Menu list container
- **VListItem**: Individual menu items
- **VListItemTitle**: Menu item text
- **VSpacer**: Layout spacer component
- **VAvatar**: Circular icon container with styling effects
- **VIcon**: Icon display component for avatar and change indicator

## Layout Structure

The component uses a vertical centered layout with the following structure:

```
VCard
├── VCardText (Header Section)
│   ├── Title (text-base font-weight-regular)
│   ├── VSpacer
│   └── VBtn (Dropdown Menu)
│       └── VMenu
│           └── VList (Yesterday, Last Week, Last Month)
└── VCardText (Main Content)
    └── Centered Column (d-flex align-center flex-column)
        ├── VAvatar (Icon with shadow effect)
        ├── Stats Value (text-h4)
        ├── Subtitle (text-base mb-2)
        └── Change Indicator (d-flex align-center)
            ├── Change Percentage
            └── Direction Icon
```

## Styling Classes

### Layout Classes
- `d-flex align-center`: Header horizontal alignment
- `d-flex align-center flex-column`: Main content vertical centering
- `d-flex align-center`: Change indicator horizontal alignment

### Typography Classes
- `text-base font-weight-regular`: Header title styling
- `text-h4`: Large heading for statistics value
- `text-base mb-2`: Subtitle with bottom margin
- `text-base mb-0`: Change percentage without margin

### Color Classes
- `text-success` / `text-error`: Conditional coloring for change indicator
- Dynamic color theming through `color` prop

## Special Features

### Time Period Dropdown
The component includes a built-in dropdown menu in the header with predefined time periods:
- Today (default selection)
- Yesterday
- Last Week  
- Last Month

### Icon Avatar Styling
- **Size**: 46px diameter
- **Variant**: Tonal (colored background)
- **Shadow Effect**: Dynamic box-shadow based on color theme
- **Bottom Margin**: 3 units spacing from stats
- **Shadow Formula**: `box-shadow: rgb(var(--v-theme-{color}), 0.06) 0 0 0 4px`

### Change Indicator
- **Positive Changes**:
  - Color: Success green
  - Icon: `bx-chevron-up`
- **Negative Changes**:
  - Color: Error red  
  - Icon: `bx-chevron-down`
- **Display**: Shows absolute value with appropriate directional icon

## Visual Behavior

### Center-Aligned Layout
All main content elements are vertically centered within the card, creating a focused "target" presentation style.

### Color Theming
The component dynamically applies the color theme to:
- Icon avatar background
- Icon avatar shadow effect
- Change indicator (separate from theme color)

### Responsive Design
- Works well in grid layouts
- Maintains readability at different screen sizes
- Icon and text scale appropriately

## Accessibility

- Uses semantic button for dropdown interaction
- Proper color contrast for text elements
- Icon supplementation for change direction
- Keyboard-accessible dropdown menu

## Notes

- Designed for key performance indicators and target metrics
- The dropdown menu is cosmetic and doesn't affect the displayed data
- Change percentage uses absolute values with directional icons
- Icon avatar includes a subtle shadow effect for visual depth
- Best suited for highlighting important metrics in dashboard layouts