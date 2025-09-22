# CustomCheckboxesWithImage Component

## Overview
A specialized checkbox group component that renders multiple checkboxes with background images as the primary visual element. This component provides an image-centric selection interface where users can select options by clicking on visually appealing image cards, perfect for theme selection, template choices, or visual category picking in the Sneat admin template.

## Props Interface

| Prop | Type | Required | Description |
|------|------|----------|-------------|
| `selectedCheckbox` | `string[]` | Yes | Array of selected checkbox values |
| `checkboxContent` | `ImageCheckboxContent[]` | Yes | Array of checkbox configuration objects with image data |
| `gridColumn` | `GridColumn` | No | Responsive grid column configuration |

### ImageCheckboxContent Interface
```typescript
interface ImageCheckboxContent {
  bgImage: string        // URL or path to background image
  value: string          // Unique identifier for checkbox
  label?: string         // Optional label text below image
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

### Label Slot
Provides customization of the label content displayed below each image.

```vue
<template #label="{ label }">
  <!-- Custom label rendering -->
</template>
```

**Slot Props:**
- `label: string` - The label text for the current checkbox item

## Internal Methods

### updateSelectedOption(value: string[] | null)
Handles checkbox selection changes and emits the updated selection array.
- Validates input type before emitting
- Filters out boolean and null values
- Emits `update:selectedCheckbox` event

## Usage Examples

### Basic Image Checkbox Group
```vue
<CustomCheckboxesWithImage
  v-model:selectedCheckbox="selectedThemes"
  :checkbox-content="[
    {
      bgImage: '/images/themes/light-theme.jpg',
      value: 'light',
      label: 'Light Theme'
    },
    {
      bgImage: '/images/themes/dark-theme.jpg',
      value: 'dark',
      label: 'Dark Theme'
    },
    {
      bgImage: '/images/themes/auto-theme.jpg',
      value: 'auto',
      label: 'Auto Theme'
    }
  ]"
  :grid-column="{ cols: '12', sm: '6', md: '4' }"
/>
```

### Template Selection
```vue
<CustomCheckboxesWithImage
  v-model:selectedCheckbox="selectedTemplates"
  :checkbox-content="[
    {
      bgImage: '/templates/dashboard.png',
      value: 'dashboard',
      label: 'Dashboard Template'
    },
    {
      bgImage: '/templates/ecommerce.png', 
      value: 'ecommerce',
      label: 'E-commerce Template'
    },
    {
      bgImage: '/templates/blog.png',
      value: 'blog',
      label: 'Blog Template'
    },
    {
      bgImage: '/templates/portfolio.png',
      value: 'portfolio',
      label: 'Portfolio Template'
    }
  ]"
  :grid-column="{ cols: '6', md: '3' }"
/>
```

### Layout Options without Labels
```vue
<CustomCheckboxesWithImage
  v-model:selectedCheckbox="selectedLayouts"
  :checkbox-content="[
    {
      bgImage: '/layouts/sidebar-left.svg',
      value: 'sidebar-left'
    },
    {
      bgImage: '/layouts/sidebar-right.svg',
      value: 'sidebar-right'
    },
    {
      bgImage: '/layouts/no-sidebar.svg',
      value: 'no-sidebar'
    }
  ]"
  :grid-column="{ cols: '4' }"
/>
```

### Custom Label Rendering
```vue
<CustomCheckboxesWithImage
  v-model:selectedCheckbox="selectedDesigns"
  :checkbox-content="designOptions"
  :grid-column="{ cols: '12', sm: '6', lg: '4' }"
>
  <template #label="{ label }">
    <div class="d-flex align-center justify-space-between mt-2">
      <h6 class="text-h6">{{ label }}</h6>
      <VChip
        v-if="isPopularDesign(label)"
        size="small"
        color="primary"
        variant="tonal"
      >
        Popular
      </VChip>
    </div>
  </template>
</CustomCheckboxesWithImage>
```

### Website Theme Selection
```vue
<template>
  <CustomCheckboxesWithImage
    v-model:selectedCheckbox="selectedWebsiteThemes"
    :checkbox-content="websiteThemes"
    :grid-column="{ cols: '12', sm: '6', md: '4', xl: '3' }"
  />
</template>

<script setup>
const selectedWebsiteThemes = ref(['modern'])

const websiteThemes = [
  {
    bgImage: '/themes/modern-preview.jpg',
    value: 'modern',
    label: 'Modern Business'
  },
  {
    bgImage: '/themes/creative-preview.jpg',
    value: 'creative',
    label: 'Creative Agency'
  },
  {
    bgImage: '/themes/minimal-preview.jpg', 
    value: 'minimal',
    label: 'Minimal Portfolio'
  },
  {
    bgImage: '/themes/corporate-preview.jpg',
    value: 'corporate',
    label: 'Corporate'
  },
  {
    bgImage: '/themes/startup-preview.jpg',
    value: 'startup',
    label: 'Startup Landing'
  },
  {
    bgImage: '/themes/ecommerce-preview.jpg',
    value: 'shop',
    label: 'E-commerce Store'
  }
]
</script>
```

### Color Scheme Selection
```vue
<CustomCheckboxesWithImage
  v-model:selectedCheckbox="selectedColorSchemes"
  :checkbox-content="[
    {
      bgImage: '/color-schemes/blue-gradient.svg',
      value: 'blue',
      label: 'Ocean Blue'
    },
    {
      bgImage: '/color-schemes/purple-gradient.svg',
      value: 'purple',
      label: 'Purple Haze'
    },
    {
      bgImage: '/color-schemes/green-gradient.svg',
      value: 'green',
      label: 'Nature Green'
    },
    {
      bgImage: '/color-schemes/orange-gradient.svg',
      value: 'orange',
      label: 'Sunset Orange'
    }
  ]"
  :grid-column="{ cols: '6', sm: '3' }"
>
  <template #label="{ label }">
    <div class="text-center mt-2">
      <VChip
        :color="getColorFromLabel(label)"
        variant="tonal"
        size="small"
      >
        {{ label }}
      </VChip>
    </div>
  </template>
</CustomCheckboxesWithImage>
```

### Dashboard Layout Selection
```vue
<CustomCheckboxesWithImage
  v-model:selectedCheckbox="selectedDashboardLayout"
  :checkbox-content="dashboardLayouts"
  :grid-column="{ cols: '12', md: '6', lg: '4' }"
>
  <template #label="{ label }">
    <div class="mt-3 text-center">
      <h6 class="text-subtitle-1 font-weight-medium">
        {{ label }}
      </h6>
      <p class="text-body-2 text-medium-emphasis mb-0">
        {{ getLayoutDescription(label) }}
      </p>
    </div>
  </template>
</CustomCheckboxesWithImage>

<script setup>
const selectedDashboardLayout = ref([])

const dashboardLayouts = [
  {
    bgImage: '/layouts/classic-dashboard.png',
    value: 'classic',
    label: 'Classic Dashboard'
  },
  {
    bgImage: '/layouts/modern-dashboard.png',
    value: 'modern', 
    label: 'Modern Dashboard'
  },
  {
    bgImage: '/layouts/analytics-dashboard.png',
    value: 'analytics',
    label: 'Analytics Dashboard'
  }
]

const getLayoutDescription = (label) => {
  const descriptions = {
    'Classic Dashboard': 'Traditional layout with sidebar navigation',
    'Modern Dashboard': 'Clean, card-based modern interface',
    'Analytics Dashboard': 'Data-focused with advanced charts'
  }
  return descriptions[label] || ''
}
</script>
```

## Component Dependencies
- `VRow` - Vuetify row component for layout
- `VCol` - Vuetify column component for responsive grid
- `VLabel` - Vuetify label component for clickable areas and labels
- `VCheckbox` - Vuetify checkbox component (positioned absolutely)

## Styling
- Uses `.custom-input-wrapper` class for the row container
- Uses `.custom-input`, `.custom-checkbox` classes for individual items
- Applies `.active` class when checkbox is selected
- Image fills entire container with `block-size: 100%` and `inline-size: 100%`
- Checkbox positioned absolutely in top-right corner
- Hidden by default, visible on hover or when active

## CSS Classes
```scss
.custom-checkbox {
  position: relative;
  padding: 0;
  
  .custom-checkbox-image {
    block-size: 100%;
    inline-size: 100%;
    min-inline-size: 100%;
  }
  
  .v-checkbox {
    position: absolute;
    inset-block-start: 0;
    inset-inline-end: 0;
    visibility: hidden;
  }
  
  &:hover,
  &.active {
    border-width: 2px;
    
    .v-checkbox {
      visibility: visible;
    }
  }
}
```

## Visual Behavior
1. **Default State**: Image displayed with no visible checkbox
2. **Hover State**: Border appears and checkbox becomes visible
3. **Active State**: Border remains visible and checkbox shows selection
4. **Label**: Optional label displayed below the image card

## Image Requirements
- **Formats**: JPG, PNG, SVG, WebP supported
- **Aspect Ratio**: Maintain consistent aspect ratios across images
- **Size**: Optimize images for web (consider responsive loading)
- **Alt Text**: Default alt text is "bg-img" (consider customization)

## Key Features
- **Image-Centric Design**: Images as primary visual selection elements
- **Hidden Checkbox**: Checkbox only visible on interaction
- **Responsive Grid**: Automatic responsive layout with customizable columns
- **Optional Labels**: Flexible label system with slot customization
- **Hover Effects**: Visual feedback on interaction
- **Border Indication**: Clear visual selection state
- **Full Image Coverage**: Images fill entire selection area

## Accessibility Features
- Proper label association with `for` attribute using unique IDs
- Keyboard navigation support (Tab, Space, Enter)
- Screen reader compatibility with meaningful alt text
- Focus management with visible indicators
- Click area extends to entire image
- Semantic HTML structure

## Performance Considerations
- Optimize images for web delivery
- Use appropriate image formats (WebP for modern browsers)
- Consider lazy loading for large image sets
- Implement proper image caching strategies
- Use responsive images with `srcset` when needed

## Common Use Cases
- **Theme Selection**: Choose website or application themes
- **Template Selection**: Pick from available design templates
- **Layout Options**: Select dashboard or page layouts
- **Color Schemes**: Choose color palettes or gradients
- **Design Patterns**: Select UI design patterns or styles
- **Background Selection**: Choose background images or patterns

## Responsive Behavior
Images automatically scale to fit their grid containers:
- **Mobile**: Single column or 2-column layout
- **Tablet**: 2-3 columns depending on content
- **Desktop**: 3-4 columns for optimal viewing
- **Large screens**: Up to 6 columns with proper spacing

## Image Optimization Tips
```javascript
// Consider using dynamic imports for better performance
const imageOptions = [
  {
    bgImage: () => import('/images/theme-1.jpg'),
    value: 'theme1',
    label: 'Theme 1'
  }
]

// Or with lazy loading
const lazyImageOptions = computed(() => 
  baseOptions.map(option => ({
    ...option,
    bgImage: `${baseImagePath}/${option.value}.jpg`
  }))
)
```

## Integration with Forms
```vue
<!-- With form validation -->
<VForm @submit="handleSubmit">
  <CustomCheckboxesWithImage
    v-model:selectedCheckbox="formData.themes"
    :checkbox-content="availableThemes"
    :rules="[v => v.length > 0 || 'At least one theme must be selected']"
  />
</VForm>
```

## File Location
`src/@core/components/app-form-elements/CustomCheckboxesWithImage.vue`