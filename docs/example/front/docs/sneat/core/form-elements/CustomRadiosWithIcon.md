# CustomRadiosWithIcon Component

## Overview
A specialized radio button group component that renders multiple radio options in a grid layout with prominent icon displays and center-aligned content. This component provides a visually appealing icon-based single-selection interface, perfect for configuration options, service selection, or feature choices in the Sneat admin template.

## Props Interface

| Prop | Type | Required | Description |
|------|------|----------|-------------|
| `selectedRadio` | `string` | Yes | Currently selected radio button value |
| `radioContent` | `CustomInputContent[]` | Yes | Array of radio button configuration objects with icon data |
| `gridColumn` | `GridColumn` | No | Responsive grid column configuration |

### CustomInputContent Interface (Icon Variant)
```typescript
interface CustomInputContent {
  title: string                    // Main radio button label
  desc?: string                   // Description text below title
  value: string                   // Unique identifier for radio button
  subtitle?: string               // Optional subtitle (not used in icon variant)
  icon: string | VIconProps       // Icon string or icon props object
  images?: string                 // Optional (not used in icon variant)
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
Provides complete customization of radio button content display with icon support.

```vue
<template #default="{ item }">
  <!-- Custom content rendering with icon -->
</template>
```

**Slot Props:**
- `item: CustomInputContent` - The current radio button item data including icon information

## Internal Methods

### updateSelectedOption(value: string | null)
Handles radio button selection changes and emits the updated selection.
- Validates input is not null before emitting
- Emits `update:selectedRadio` event

## Usage Examples

### Basic Icon Radio Group
```vue
<CustomRadiosWithIcon
  v-model:selectedRadio="selectedPlan"
  :radio-content="[
    {
      title: 'Basic',
      desc: 'Essential features for getting started',
      value: 'basic',
      icon: 'mdi-rocket-launch-outline'
    },
    {
      title: 'Pro',
      desc: 'Advanced features for professionals',
      value: 'pro',
      icon: 'mdi-star-outline'
    },
    {
      title: 'Enterprise',
      desc: 'Complete solution for organizations',
      value: 'enterprise',
      icon: 'mdi-office-building-outline'
    }
  ]"
  :grid-column="{ cols: '12', sm: '6', md: '4' }"
/>
```

### Hosting Plan Selection
```vue
<CustomRadiosWithIcon
  v-model:selectedRadio="selectedHosting"
  :radio-content="[
    {
      title: 'Shared Hosting',
      desc: 'Perfect for small websites and blogs',
      value: 'shared',
      icon: 'mdi-server-network'
    },
    {
      title: 'VPS Hosting',
      desc: 'Scalable virtual private servers',
      value: 'vps',
      icon: 'mdi-server'
    },
    {
      title: 'Dedicated Server',
      desc: 'Full control with dedicated resources',
      value: 'dedicated',
      icon: 'mdi-server-plus'
    },
    {
      title: 'Cloud Hosting',
      desc: 'Flexible and scalable cloud infrastructure',
      value: 'cloud',
      icon: 'mdi-cloud-outline'
    }
  ]"
  :grid-column="{ cols: '6', lg: '3' }"
/>
```

### Icon Props Configuration
```vue
<CustomRadiosWithIcon
  v-model:selectedRadio="selectedTheme"
  :radio-content="[
    {
      title: 'Light Theme',
      desc: 'Bright and clean interface',
      value: 'light',
      icon: {
        icon: 'mdi-weather-sunny',
        color: 'warning',
        size: 'large'
      }
    },
    {
      title: 'Dark Theme',
      desc: 'Easy on the eyes for long sessions',
      value: 'dark',
      icon: {
        icon: 'mdi-weather-night',
        color: 'info',
        size: 'large'
      }
    },
    {
      title: 'Auto Theme',
      desc: 'Adapts to system preferences',
      value: 'auto',
      icon: {
        icon: 'mdi-theme-light-dark',
        color: 'primary',
        size: 'large'
      }
    }
  ]"
  :grid-column="{ cols: '4' }"
/>
```

### Custom Content with Enhanced Icons
```vue
<CustomRadiosWithIcon
  v-model:selectedRadio="selectedService"
  :radio-content="serviceOptions"
  :grid-column="{ cols: '12', sm: '6', lg: '4' }"
>
  <template #default="{ item }">
    <div class="d-flex flex-column align-center text-center gap-3">
      <div class="position-relative">
        <VIcon
          v-bind="item.icon"
          size="56"
          class="text-primary"
        />
        <VBadge
          v-if="isPopularService(item.value)"
          content="Popular"
          color="success"
          offset-x="-10"
          offset-y="-10"
        />
      </div>
      
      <div>
        <h6 class="text-h6 mb-2">{{ item.title }}</h6>
        <p class="text-body-2 text-medium-emphasis mb-2">
          {{ item.desc }}
        </p>
        <VChip
          v-if="getServicePrice(item.value)"
          color="primary"
          variant="tonal"
          size="small"
        >
          {{ getServicePrice(item.value) }}
        </VChip>
      </div>
    </div>
  </template>
</CustomRadiosWithIcon>
```

### Development Environment Selection
```vue
<template>
  <CustomRadiosWithIcon
    v-model:selectedRadio="selectedEnvironment"
    :radio-content="environmentOptions"
    :grid-column="{ cols: '12', md: '6', xl: '3' }"
  />
</template>

<script setup>
const selectedEnvironment = ref('development')

const environmentOptions = [
  {
    title: 'Development',
    desc: 'Local development environment with debug tools',
    value: 'development',
    icon: 'mdi-laptop'
  },
  {
    title: 'Staging',
    desc: 'Pre-production testing environment',
    value: 'staging',
    icon: 'mdi-test-tube'
  },
  {
    title: 'Production',
    desc: 'Live production environment',
    value: 'production',
    icon: 'mdi-rocket'
  },
  {
    title: 'Demo',
    desc: 'Demonstration environment for showcasing',
    value: 'demo',
    icon: 'mdi-presentation'
  }
]
</script>
```

### Communication Channel Selection
```vue
<CustomRadiosWithIcon
  v-model:selectedRadio="selectedChannel"
  :radio-content="[
    {
      title: 'Email',
      desc: 'Traditional email communication',
      value: 'email',
      icon: 'mdi-email-outline'
    },
    {
      title: 'Slack',
      desc: 'Team collaboration platform',
      value: 'slack',
      icon: 'mdi-slack'
    },
    {
      title: 'Teams',
      desc: 'Microsoft Teams integration',
      value: 'teams',
      icon: 'mdi-microsoft-teams'
    },
    {
      title: 'Discord',
      desc: 'Discord server notifications',
      value: 'discord',
      icon: 'mdi-discord'
    },
    {
      title: 'Webhook',
      desc: 'Custom webhook endpoint',
      value: 'webhook',
      icon: 'mdi-webhook'
    }
  ]"
  :grid-column="{ cols: '6', md: '4', lg: '3' }"
/>
```

### Database Type Selection
```vue
<CustomRadiosWithIcon
  v-model:selectedRadio="selectedDatabase"
  :radio-content="[
    {
      title: 'MySQL',
      desc: 'Popular relational database',
      value: 'mysql',
      icon: 'mdi-database'
    },
    {
      title: 'PostgreSQL',
      desc: 'Advanced open-source database',
      value: 'postgresql',
      icon: 'mdi-elephant'
    },
    {
      title: 'MongoDB',
      desc: 'Flexible document database',
      value: 'mongodb',
      icon: 'mdi-leaf'
    },
    {
      title: 'SQLite',
      desc: 'Lightweight embedded database',
      value: 'sqlite',
      icon: 'mdi-database-outline'
    }
  ]"
  :grid-column="{ cols: '12', sm: '6' }"
/>
```

### Backup Strategy Selection
```vue
<CustomRadiosWithIcon
  v-model:selectedRadio="selectedBackupStrategy"
  :radio-content="backupStrategies"
  :grid-column="{ cols: '12', md: '6', lg: '4' }"
>
  <template #default="{ item }">
    <div class="d-flex flex-column align-center text-center gap-3">
      <VIcon
        v-bind="item.icon"
        size="48"
        :color="getStrategyColor(item.value)"
      />
      
      <div>
        <h6 class="text-h6 mb-1">{{ item.title }}</h6>
        <p class="text-body-2 text-medium-emphasis mb-2">
          {{ item.desc }}
        </p>
        <div class="d-flex align-center justify-center gap-1">
          <VIcon size="small" color="info">mdi-clock-outline</VIcon>
          <small class="text-disabled">{{ getBackupFrequency(item.value) }}</small>
        </div>
      </div>
    </div>
  </template>
</CustomRadiosWithIcon>

<script setup>
const backupStrategies = [
  {
    title: 'Daily Backup',
    desc: 'Automated daily backups at midnight',
    value: 'daily',
    icon: 'mdi-backup-restore'
  },
  {
    title: 'Real-time Sync',
    desc: 'Continuous data synchronization',
    value: 'realtime',
    icon: 'mdi-sync'
  },
  {
    title: 'Weekly Archive',
    desc: 'Weekly full system archive',
    value: 'weekly',
    icon: 'mdi-archive'
  }
]
</script>
```

## Component Dependencies
- `VRadioGroup` - Vuetify radio group wrapper component
- `VRow` - Vuetify row component for layout
- `VCol` - Vuetify column component for responsive grid
- `VLabel` - Vuetify label component for custom styling
- `VRadio` - Vuetify radio button component
- `VIcon` - Vuetify icon component for display

## Styling
- Uses `.custom-input-wrapper` class for the radio group container
- Uses `.custom-input`, `.custom-radio-icon` classes for individual items
- Applies `.active` class when radio button is selected
- Vertical flexbox layout with column direction
- Center-aligned content with proper spacing
- 2px border width with rounded corners

## CSS Classes
```scss
.custom-radio-icon {
  display: flex;
  flex-direction: column;
  border-width: 2px;
  gap: 0.5rem;
  
  .v-radio {
    margin-block-end: -0.5rem;
    
    .v-selection-control__wrapper {
      margin-inline-start: 0;
    }
  }
}
```

## Icon Configuration

### String Icons
```javascript
// Simple icon string
icon: 'mdi-home'

// Framework icons
icon: 'fas fa-user'        // Font Awesome
icon: 'bi-house'           // Bootstrap Icons  
icon: 'tabler-settings'    // Tabler Icons
```

### Icon Props Object
```javascript
icon: {
  icon: 'mdi-star',
  color: 'primary',        // Vuetify color
  size: 'large',           // Icon size
  variant: 'outlined'      // Icon variant
}
```

## Key Features
- **Icon-Centric Design**: Prominent icon display with visual emphasis
- **Single Selection**: Enforced single selection through radio button group
- **Responsive Grid**: Automatic responsive layout with customizable columns
- **Center Alignment**: Content centered both horizontally and vertically
- **Rich Content**: Support for titles, descriptions, and icons
- **Custom Styling**: Enhanced visual design with borders and hover effects
- **Flexible Layout**: Slot-based customization for content rendering
- **Type Safety**: Full TypeScript support with defined interfaces

## Accessibility Features
- Proper radio button group association
- Keyboard navigation support (Arrow keys, Tab, Space)
- Screen reader compatibility with icon descriptions
- Focus management with visible indicators
- Selection state announcements
- Semantic HTML structure
- High contrast support

## Visual Design Patterns
The component follows a card-like design pattern:
1. **Icon**: Large, prominent icon at the top
2. **Title**: Bold title below the icon
3. **Description**: Descriptive text with proper spacing
4. **Radio Button**: Positioned at the bottom of the card

## Performance Considerations
- Icons are rendered efficiently using Vuetify's VIcon
- Use `key` attributes for optimal re-rendering
- Consider icon loading strategies for custom icons
- Implement lazy loading for dynamic content
- Optimize for touch interactions on mobile

## Common Use Cases
- **Configuration Options**: Choose system or application settings
- **Service Selection**: Select from available services or features
- **Theme Selection**: Choose visual themes with representative icons
- **Integration Options**: Select third-party integrations
- **Environment Selection**: Choose deployment environments
- **Tool Selection**: Select development tools or utilities

## Integration with Forms
```vue
<!-- With form validation -->
<VForm @submit="handleSubmit">
  <CustomRadiosWithIcon
    v-model:selectedRadio="formData.plan"
    :radio-content="availablePlans"
    :rules="[v => !!v || 'A plan selection is required']"
  />
</VForm>
```

## Comparison with Similar Components

| Feature | CustomRadiosWithIcon | CustomRadios | CustomCheckboxesWithIcon |
|---------|---------------------|--------------|-------------------------|
| Selection Type | Single | Single | Multiple |
| Model Value | `string` | `string` | `string[]` |
| Visual Layout | Icon-centered | Text-focused | Icon-centered |
| HTML Element | Radio buttons | Radio buttons | Checkboxes |

## Responsive Behavior
The component automatically adapts to different screen sizes:
- **Mobile**: Stack items in single or double columns
- **Tablet**: 2-3 items per row depending on content
- **Desktop**: 4-6 items per row for optimal viewing
- **Large screens**: Maximum items per row with proper spacing

## State Management
The component manages selection state through:
- Two-way binding with `v-model:selectedRadio`
- Single value selection tracking
- Automatic updates on radio button interactions
- Type validation for emitted values

## File Location
`src/@core/components/app-form-elements/CustomRadiosWithIcon.vue`