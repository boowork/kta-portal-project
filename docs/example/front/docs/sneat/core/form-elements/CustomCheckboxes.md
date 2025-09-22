# CustomCheckboxes Component

## Overview
A specialized checkbox group component that renders multiple checkboxes in a grid layout with custom styling and enhanced visual presentation. Each checkbox includes a title, description, and optional subtitle, providing a rich selection interface for the Sneat admin template.

## Props Interface

| Prop | Type | Required | Description |
|------|------|----------|-------------|
| `selectedCheckbox` | `string[]` | Yes | Array of selected checkbox values |
| `checkboxContent` | `CustomInputContent[]` | Yes | Array of checkbox configuration objects |
| `gridColumn` | `GridColumn` | No | Responsive grid column configuration |

### CustomInputContent Interface
```typescript
interface CustomInputContent {
  title: string          // Main checkbox label
  desc?: string          // Description text below title
  value: string          // Unique identifier for checkbox
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
| `update:selectedCheckbox` | `string[]` | Emitted when checkbox selection changes |

## Slots Available

### Default Slot
Provides complete customization of checkbox content display.

```vue
<template #default="{ item }">
  <!-- Custom content rendering -->
</template>
```

**Slot Props:**
- `item: CustomInputContent` - The current checkbox item data

## Internal Methods

### updateSelectedOption(value: string[] | null)
Handles checkbox selection changes and emits the updated selection array.
- Validates input type before emitting
- Filters out boolean and null values
- Emits `update:selectedCheckbox` event

## Usage Examples

### Basic Checkbox Group
```vue
<CustomCheckboxes
  v-model:selectedCheckbox="selectedOptions"
  :checkbox-content="[
    {
      title: 'Option 1',
      desc: 'Description for option 1',
      value: 'option1'
    },
    {
      title: 'Option 2', 
      desc: 'Description for option 2',
      value: 'option2',
      subtitle: 'Optional'
    }
  ]"
/>
```

### Grid Layout Configuration
```vue
<CustomCheckboxes
  v-model:selectedCheckbox="selectedFeatures"
  :checkbox-content="featureList"
  :grid-column="{
    cols: '12',
    md: '6',
    lg: '4'
  }"
/>
```

### Subscription Plans Selection
```vue
<CustomCheckboxes
  v-model:selectedCheckbox="selectedPlans"
  :checkbox-content="[
    {
      title: 'Basic Plan',
      desc: 'Perfect for individuals and small projects',
      value: 'basic',
      subtitle: '$9/month'
    },
    {
      title: 'Pro Plan', 
      desc: 'Ideal for growing businesses and teams',
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

### Custom Content with Slot
```vue
<CustomCheckboxes
  v-model:selectedCheckbox="selectedServices"
  :checkbox-content="serviceOptions"
  :grid-column="{ cols: '12', sm: '6' }"
>
  <template #default="{ item }">
    <div class="d-flex flex-column flex-grow-1">
      <div class="d-flex align-center justify-space-between mb-2">
        <h6 class="text-h6 text-primary">
          {{ item.title }}
        </h6>
        <VChip
          v-if="item.subtitle"
          size="small" 
          color="success"
          variant="tonal"
        >
          {{ item.subtitle }}
        </VChip>
      </div>
      <p class="text-body-2 text-medium-emphasis mb-1">
        {{ item.desc }}
      </p>
      <VRating
        :model-value="getServiceRating(item.value)"
        readonly
        size="small"
        density="compact"
      />
    </div>
  </template>
</CustomCheckboxes>
```

### Features Selection
```vue
<template>
  <CustomCheckboxes
    v-model:selectedCheckbox="selectedFeatures"
    :checkbox-content="featureOptions"
    :grid-column="{ cols: '12', sm: '6', lg: '4' }"
  />
</template>

<script setup>
const selectedFeatures = ref(['seo', 'analytics'])

const featureOptions = [
  {
    title: 'SEO Optimization',
    desc: 'Improve your search engine rankings',
    value: 'seo',
    subtitle: 'Recommended'
  },
  {
    title: 'Advanced Analytics',
    desc: 'Detailed insights and reporting',
    value: 'analytics'
  },
  {
    title: 'Custom Domain',
    desc: 'Use your own domain name',
    value: 'domain',
    subtitle: 'Pro feature'
  },
  {
    title: 'Email Integration', 
    desc: 'Connect with email marketing tools',
    value: 'email'
  },
  {
    title: 'API Access',
    desc: 'Programmatic access to your data',
    value: 'api',
    subtitle: 'Developer'
  },
  {
    title: '24/7 Support',
    desc: 'Round-the-clock customer support',
    value: 'support',
    subtitle: 'Premium'
  }
]
</script>
```

### Dynamic Content Loading
```vue
<CustomCheckboxes
  v-model:selectedCheckbox="selectedItems"
  :checkbox-content="dynamicContent"
  :grid-column="responsiveColumns"
/>

<script setup>
const selectedItems = ref([])
const dynamicContent = ref([])

const responsiveColumns = {
  cols: '12',
  sm: '6', 
  md: '4',
  lg: '3'
}

onMounted(async () => {
  try {
    const response = await fetchCheckboxOptions()
    dynamicContent.value = response.data
  } catch (error) {
    console.error('Failed to load options:', error)
  }
})
</script>
```

## Component Dependencies
- `VRow` - Vuetify row component for layout
- `VCol` - Vuetify column component for responsive grid
- `VLabel` - Vuetify label component for custom styling
- `VCheckbox` - Vuetify checkbox component
- `VSpacer` - Vuetify spacer component for layout

## Styling
- Uses `.custom-input-wrapper` class for the row container
- Uses `.custom-input`, `.custom-checkbox` classes for individual items
- Applies `.active` class when checkbox is selected
- Custom styling with 2px border width and rounded corners
- Flexible layout with proper gap spacing
- Responsive design through grid system

## CSS Classes
```scss
.custom-checkbox {
  display: flex;
  align-items: flex-start;
  border-width: 2px;
  gap: 0.25rem;
  
  .v-checkbox {
    margin-block-start: -0.375rem;
  }
  
  .cr-title {
    font-weight: 500;
    line-height: 1.375rem;
  }
}
```

## Key Features
- **Responsive Grid**: Automatic responsive layout with customizable columns
- **Rich Content**: Support for titles, descriptions, and subtitles
- **Custom Styling**: Enhanced visual design with borders and hover effects
- **Flexible Layout**: Slot-based customization for content rendering
- **Type Safety**: Full TypeScript support with defined interfaces
- **Accessibility**: Proper labeling and keyboard navigation

## Accessibility Features
- Proper label association with checkboxes
- Keyboard navigation support (Tab, Space, Enter)
- Screen reader compatibility
- Focus management
- Selection state announcements
- Semantic HTML structure

## Responsive Behavior
Grid columns automatically adjust based on screen size:
- Default: Uses provided `cols` value
- Small screens: Uses `sm` breakpoint configuration
- Medium screens: Uses `md` breakpoint configuration
- Large screens: Uses `lg` breakpoint configuration
- Extra large: Uses `xl` and `xxl` configurations

## State Management
The component manages selection state through:
- Two-way binding with `v-model:selectedCheckbox`
- Array-based selection tracking
- Automatic updates on checkbox interactions
- Type validation for emitted values

## Performance Considerations
- Use `key` attributes for optimal re-rendering
- Consider virtualization for very large lists
- Implement lazy loading for dynamic content
- Optimize images if using image variants

## Common Use Cases
- **Feature Selection**: Choose product features or services
- **Preference Settings**: User preference configurations
- **Plan Selection**: Subscription or service plan choices
- **Category Filtering**: Multi-select category filters
- **Permission Management**: Role and permission assignments
- **Survey Responses**: Multiple choice survey questions

## Integration with Forms
```vue
<!-- With form validation -->
<VForm @submit="handleSubmit">
  <CustomCheckboxes
    v-model:selectedCheckbox="formData.features"
    :checkbox-content="availableFeatures"
    :rules="[v => v.length > 0 || 'At least one option must be selected']"
  />
</VForm>
```

## File Location
`src/@core/components/app-form-elements/CustomCheckboxes.vue`