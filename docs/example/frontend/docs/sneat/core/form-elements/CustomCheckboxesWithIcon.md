# CustomCheckboxesWithIcon Component

## Overview
A specialized checkbox group component that renders multiple checkboxes in a grid layout with prominent icon displays and center-aligned content. This component provides a visually appealing icon-based selection interface, perfect for feature selection, service choices, or category picking in the Sneat admin template.

## Props Interface

| Prop | Type | Required | Description |
|------|------|----------|-------------|
| `selectedCheckbox` | `string[]` | Yes | Array of selected checkbox values |
| `checkboxContent` | `CustomInputContent[]` | Yes | Array of checkbox configuration objects with icon data |
| `gridColumn` | `GridColumn` | No | Responsive grid column configuration |

### CustomInputContent Interface (Icon Variant)
```typescript
interface CustomInputContent {
  title: string                    // Main checkbox label
  desc?: string                   // Description text below title
  value: string                   // Unique identifier for checkbox
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
| `update:selectedCheckbox` | `string[]` | Emitted when checkbox selection changes |

## Slots Available

### Default Slot
Provides complete customization of checkbox content display with icon support.

```vue
<template #default="{ item }">
  <!-- Custom content rendering with icon -->
</template>
```

**Slot Props:**
- `item: CustomInputContent` - The current checkbox item data including icon information

## Internal Methods

### updateSelectedOption(value: string[] | null)
Handles checkbox selection changes and emits the updated selection array.
- Validates input type before emitting
- Filters out boolean and null values
- Emits `update:selectedCheckbox` event

## Usage Examples

### Basic Icon Checkbox Group
```vue
<CustomCheckboxesWithIcon
  v-model:selectedCheckbox="selectedFeatures"
  :checkbox-content="[
    {
      title: 'Analytics',
      desc: 'Advanced data insights and reporting',
      value: 'analytics',
      icon: 'mdi-chart-line'
    },
    {
      title: 'Security',
      desc: 'Enhanced security features',
      value: 'security', 
      icon: 'mdi-shield-check'
    },
    {
      title: 'API Access',
      desc: 'Programmatic data access',
      value: 'api',
      icon: 'mdi-api'
    }
  ]"
  :grid-column="{ cols: '12', md: '4' }"
/>
```

### Service Selection with Icons
```vue
<CustomCheckboxesWithIcon
  v-model:selectedCheckbox="selectedServices"
  :checkbox-content="[
    {
      title: 'Cloud Storage',
      desc: 'Secure cloud storage for your files',
      value: 'storage',
      icon: 'mdi-cloud'
    },
    {
      title: 'Email Marketing',
      desc: 'Automated email campaigns',
      value: 'email',
      icon: 'mdi-email-outline'
    },
    {
      title: 'Social Media',
      desc: 'Social media management tools',
      value: 'social',
      icon: 'mdi-account-group'
    },
    {
      title: 'E-commerce',
      desc: 'Online store functionality',
      value: 'ecommerce',
      icon: 'mdi-shopping'
    }
  ]"
  :grid-column="{ cols: '6', md: '3' }"
/>
```

### Icon Props Configuration
```vue
<CustomCheckboxesWithIcon
  v-model:selectedCheckbox="selectedTools"
  :checkbox-content="[
    {
      title: 'Database',
      desc: 'Database management and queries',
      value: 'database',
      icon: {
        icon: 'mdi-database',
        color: 'primary',
        size: 'large'
      }
    },
    {
      title: 'Monitoring',
      desc: 'System monitoring and alerts',
      value: 'monitoring',
      icon: {
        icon: 'mdi-monitor-dashboard',
        color: 'success',
        size: 'large'
      }
    },
    {
      title: 'Backup',
      desc: 'Automated backup solutions',
      value: 'backup',
      icon: {
        icon: 'mdi-backup-restore',
        color: 'warning',
        size: 'large'
      }
    }
  ]"
/>
```

### Custom Content with Icon Enhancement
```vue
<CustomCheckboxesWithIcon
  v-model:selectedCheckbox="selectedCategories"
  :checkbox-content="categoryOptions"
  :grid-column="{ cols: '12', sm: '6', lg: '4' }"
>
  <template #default="{ item }">
    <div class="d-flex flex-column align-center text-center gap-3">
      <div class="position-relative">
        <VIcon
          v-bind="item.icon"
          size="48"
          class="text-primary"
        />
        <VBadge
          v-if="getCategoryCount(item.value) > 0"
          :content="getCategoryCount(item.value)"
          color="error"
          offset-x="-8"
          offset-y="-8"
        />
      </div>
      
      <div>
        <h6 class="text-h6 mb-1">{{ item.title }}</h6>
        <p class="text-body-2 text-medium-emphasis mb-0">
          {{ item.desc }}
        </p>
      </div>
      
      <VProgressLinear
        :model-value="getCategoryProgress(item.value)"
        color="primary"
        height="4"
        rounded
        class="w-100"
      />
    </div>
  </template>
</CustomCheckboxesWithIcon>
```

### Developer Tools Selection
```vue
<template>
  <CustomCheckboxesWithIcon
    v-model:selectedCheckbox="selectedDevTools"
    :checkbox-content="devToolOptions"
    :grid-column="{ cols: '12', sm: '6', md: '4', xl: '3' }"
  />
</template>

<script setup>
const selectedDevTools = ref(['git', 'docker'])

const devToolOptions = [
  {
    title: 'Git Integration',
    desc: 'Version control and collaboration',
    value: 'git',
    icon: 'mdi-git'
  },
  {
    title: 'Docker Support',
    desc: 'Containerization and deployment',
    value: 'docker',
    icon: 'mdi-docker'
  },
  {
    title: 'CI/CD Pipeline',
    desc: 'Automated build and deployment',
    value: 'cicd',
    icon: 'mdi-pipe'
  },
  {
    title: 'Code Analysis',
    desc: 'Static code analysis and quality checks',
    value: 'analysis',
    icon: 'mdi-code-braces'
  },
  {
    title: 'Testing Suite',
    desc: 'Automated testing framework',
    value: 'testing',
    icon: 'mdi-test-tube'
  },
  {
    title: 'Performance Monitoring',
    desc: 'Application performance insights',
    value: 'monitoring',
    icon: 'mdi-speedometer'
  }
]
</script>
```

### Payment Methods Selection
```vue
<CustomCheckboxesWithIcon
  v-model:selectedCheckbox="selectedPaymentMethods"
  :checkbox-content="[
    {
      title: 'Credit Card',
      desc: 'Visa, MasterCard, American Express',
      value: 'credit',
      icon: 'mdi-credit-card'
    },
    {
      title: 'PayPal',
      desc: 'Secure PayPal payments',
      value: 'paypal',
      icon: 'mdi-paypal'
    },
    {
      title: 'Bank Transfer',
      desc: 'Direct bank account transfer',
      value: 'bank',
      icon: 'mdi-bank'
    },
    {
      title: 'Cryptocurrency',
      desc: 'Bitcoin, Ethereum, and more',
      value: 'crypto',
      icon: 'mdi-bitcoin'
    }
  ]"
  :grid-column="{ cols: '6', lg: '3' }"
/>
```

## Component Dependencies
- `VRow` - Vuetify row component for layout
- `VCol` - Vuetify column component for responsive grid
- `VLabel` - Vuetify label component for custom styling
- `VCheckbox` - Vuetify checkbox component
- `VIcon` - Vuetify icon component for display

## Styling
- Uses `.custom-input-wrapper` class for the row container
- Uses `.custom-input`, `.custom-checkbox-icon` classes for individual items
- Applies `.active` class when checkbox is selected
- Vertical flexbox layout with column direction
- Center-aligned content with proper spacing
- 2px border width with rounded corners

## CSS Classes
```scss
.custom-checkbox-icon {
  display: flex;
  flex-direction: column;
  border-width: 2px;
  gap: 0.5rem;
  
  .v-checkbox {
    margin-block-end: -0.375rem;
    
    .v-selection-control__wrapper {
      margin-inline-start: 0;
    }
  }
  
  .cr-title {
    font-weight: 500;
    line-height: 1.375rem;
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
- **Responsive Grid**: Automatic responsive layout with customizable columns
- **Center Alignment**: Content centered both horizontally and vertically
- **Rich Content**: Support for titles, descriptions, and icons
- **Custom Styling**: Enhanced visual design with borders and hover effects
- **Flexible Layout**: Slot-based customization for content rendering
- **Type Safety**: Full TypeScript support with defined interfaces

## Accessibility Features
- Proper label association with checkboxes
- Keyboard navigation support (Tab, Space, Enter)
- Screen reader compatibility with icon descriptions
- Focus management with visible indicators
- Selection state announcements
- Semantic HTML structure
- High contrast support

## Visual Design Patterns
The component follows a card-like design pattern:
1. **Icon**: Large, prominent icon at the top
2. **Title**: Bold title below the icon
3. **Description**: Descriptive text with truncation
4. **Checkbox**: Positioned at the bottom of the card

## Performance Considerations
- Icons are rendered efficiently using Vuetify's VIcon
- Use `key` attributes for optimal re-rendering
- Consider icon loading strategies for custom icons
- Implement lazy loading for dynamic content
- Optimize for touch interactions on mobile

## Common Use Cases
- **Feature Selection**: Choose product features with visual icons
- **Service Categories**: Select service types or categories
- **Tool Selection**: Choose development tools or utilities
- **Payment Methods**: Select payment options with brand icons
- **Integration Options**: Choose third-party integrations
- **Permission Categories**: Visual permission selection

## Integration with Forms
```vue
<!-- With form validation -->
<VForm @submit="handleSubmit">
  <CustomCheckboxesWithIcon
    v-model:selectedCheckbox="formData.services"
    :checkbox-content="availableServices"
    :rules="[v => v.length > 0 || 'At least one service must be selected']"
  />
</VForm>
```

## Responsive Behavior
The component automatically adapts to different screen sizes:
- **Mobile**: Stack items in single or double columns
- **Tablet**: 2-3 items per row depending on content
- **Desktop**: 4-6 items per row for optimal viewing
- **Large screens**: Maximum items per row with proper spacing

## File Location
`src/@core/components/app-form-elements/CustomCheckboxesWithIcon.vue`