# CardStatisticsVertical Component

A compact vertical layout statistics card component featuring an avatar image, statistics value, title, and change indicator with an integrated more actions menu.

## Location
`/src/@core/components/cards/CardStatisticsVertical.vue`

## Props Interface

```typescript
interface Props {
  title: string     // Main title/label for the statistic (required)
  image: string     // Avatar image URL for the statistic (required)
  color: string     // Color theme for the avatar (required)
  stats: string     // Main statistic value to display (required)
  change: number    // Percentage change value (required)
}
```

### Default Values
**None** - All props are required

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

### Static Data

#### `moreList`
- **Type**: `Array<{ title: string, value: string }>`
- **Purpose**: Predefined menu items for the more actions dropdown
- **Items**:
  - `{ title: 'Yesterday', value: 'yesterday' }`
  - `{ title: 'Last Week', value: 'lastWeek' }`
  - `{ title: 'Last Month', value: 'lastMonth' }`

## Usage Examples

### Basic Usage
```vue
<template>
  <CardStatisticsVertical
    title="Sales Revenue"
    image="/images/avatars/sales-avatar.png"
    color="primary"
    stats="$42,845"
    :change="23.7"
  />
</template>
```

### Team Performance Cards
```vue
<template>
  <VRow>
    <VCol cols="12" md="6" lg="3">
      <CardStatisticsVertical
        title="Marketing Team"
        image="/avatars/marketing-team.jpg"
        color="primary"
        stats="156"
        :change="18.2"
      />
    </VCol>
    
    <VCol cols="12" md="6" lg="3">
      <CardStatisticsVertical
        title="Development Team"
        image="/avatars/dev-team.jpg"
        color="success"
        stats="89"
        :change="-5.1"
      />
    </VCol>
    
    <VCol cols="12" md="6" lg="3">
      <CardStatisticsVertical
        title="Design Team"
        image="/avatars/design-team.jpg"
        color="warning"
        stats="47"
        :change="12.8"
      />
    </VCol>
    
    <VCol cols="12" md="6" lg="3">
      <CardStatisticsVertical
        title="Support Team"
        image="/avatars/support-team.jpg"
        color="info"
        stats="298"
        :change="7.4"
      />
    </VCol>
  </VRow>
</template>
```

### Dynamic User Statistics
```vue
<template>
  <div class="user-stats-grid">
    <CardStatisticsVertical
      v-for="user in userStats"
      :key="user.id"
      :title="user.name"
      :image="user.avatar"
      :color="user.performanceColor"
      :stats="user.formattedMetric"
      :change="user.change"
    />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const users = ref([
  {
    id: 1,
    name: 'John Smith',
    avatar: '/avatars/john.jpg',
    metric: 1247,
    change: 15.3,
    metricType: 'sales'
  },
  {
    id: 2,
    name: 'Sarah Johnson',
    avatar: '/avatars/sarah.jpg',
    metric: 892,
    change: -3.7,
    metricType: 'leads'
  },
  {
    id: 3,
    name: 'Mike Wilson',
    avatar: '/avatars/mike.jpg',
    metric: 2156,
    change: 28.9,
    metricType: 'calls'
  }
])

const userStats = computed(() => 
  users.value.map(user => ({
    ...user,
    formattedMetric: user.metric.toLocaleString(),
    performanceColor: user.change > 0 ? 'success' : user.change < -10 ? 'error' : 'warning'
  }))
)
</script>

<style scoped>
.user-stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 1rem;
}
</style>
```

### Product Performance Example
```vue
<template>
  <CardStatisticsVertical
    v-for="product in products"
    :key="product.id"
    :title="product.name"
    :image="product.thumbnail"
    :color="getProductColor(product.category)"
    :stats="product.salesCount"
    :change="product.monthlyChange"
  />
</template>

<script setup>
const products = ref([
  {
    id: 1,
    name: 'Premium Headphones',
    thumbnail: '/products/headphones.jpg',
    category: 'electronics',
    salesCount: '1,247',
    monthlyChange: 34.2
  },
  {
    id: 2,
    name: 'Smart Watch',
    thumbnail: '/products/smartwatch.jpg',
    category: 'wearables',
    salesCount: '892',
    monthlyChange: -8.1
  }
])

const getProductColor = (category) => {
  const colors = {
    electronics: 'primary',
    wearables: 'info',
    accessories: 'success',
    software: 'warning'
  }
  return colors[category] || 'secondary'
}
</script>
```

## Component Dependencies

### Vue Composition API
- `defineProps`: For prop definition

### VueUse Composables
- `controlledComputed`: For reactive computed properties with control

### Vuetify Components
- **VCard**: Main container card component
- **VCardText**: Card content wrapper sections
- **VAvatar**: Image avatar with color theming
- **VIcon**: Icon display for change direction indicator

### Custom Components
- **MoreBtn**: Custom more actions button component
  - **Props Used**:
    - `size="small"`: Compact button size
    - `:menu-list="moreList"`: Dropdown menu items

## Layout Structure

The component uses a vertical layout with the following structure:

```
VCard
├── VCardText (Header Section - d-flex align-center justify-space-between pb-4)
│   ├── VAvatar (Image display)
│   └── MoreBtn (Actions menu)
└── VCardText (Main Content)
    ├── Title (mb-0)
    ├── Stats Value (text-h4 text-no-wrap mb-3)
    └── Change Indicator (d-flex align-center gap-1)
        ├── Change Percentage
        └── Direction Icon
```

## Styling Classes

### Layout Classes
- `d-flex align-center justify-space-between pb-4`: Header layout with bottom padding
- `d-flex align-center gap-1 text-sm font-weight-medium`: Change indicator layout

### Typography Classes
- `mb-0`: Title with no bottom margin
- `text-h4 text-no-wrap mb-3`: Large stats with no wrapping and bottom margin
- `text-sm font-weight-medium`: Small, medium-weight text for change indicator

### Color Classes
- `text-success` / `text-error`: Conditional coloring for change indicator

## Special Features

### Avatar Display
- **Size**: 40px diameter
- **Shape**: Rounded corners
- **Variant**: Text variant with color theming
- **Image**: Displays provided image URL
- **Color**: Applies theme color as background/accent

### More Actions Menu
Integrated dropdown menu with predefined time period options:
- Yesterday
- Last Week
- Last Month

### Change Indicator
- **Positive Changes**:
  - Color: Success green
  - Icon: `bx-up-arrow-alt`
- **Negative Changes**:
  - Color: Error red
  - Icon: `bx-down-arrow-alt`
- **Display**: Shows absolute value with directional arrow

## Visual Behavior

### Compact Design
Optimized for displaying multiple statistics in a grid layout while maintaining readability.

### Image-Centric
The avatar image is prominently displayed, making this component ideal for:
- User/team performance metrics
- Product statistics
- Category-based analytics
- Profile-related data

### No-Wrap Stats
The statistics value uses `text-no-wrap` to prevent line breaks and maintain clean layout.

## Accessibility

- Avatar images should include proper alt text when implemented
- Color coding supplemented with directional arrows
- Semantic button interaction for more menu
- Proper color contrast for text elements

## Notes

- Requires all props to be provided (no defaults)
- Designed for grid layouts with multiple instances
- Avatar image should be optimized for 40px display
- Change percentage uses absolute values with directional arrows
- More menu is purely cosmetic and doesn't affect displayed data
- Best suited for user-centric or product-centric statistics
- Color prop affects avatar styling and should match Vuetify color palette