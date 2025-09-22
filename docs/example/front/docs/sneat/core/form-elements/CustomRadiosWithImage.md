# CustomRadiosWithImage Component

## Overview
A specialized radio button group component that renders multiple radio options with background images as the primary visual element for single selection. This component provides an image-centric selection interface where users can select one option by clicking on visually appealing image cards, perfect for theme selection, layout choices, or visual configuration options in the Sneat admin template.

## Props Interface

| Prop | Type | Required | Description |
|------|------|----------|-------------|
| `selectedRadio` | `string` | Yes | Currently selected radio button value |
| `radioContent` | `ImageRadioContent[]` | Yes | Array of radio button configuration objects with image data |
| `gridColumn` | `GridColumn` | No | Responsive grid column configuration |

### ImageRadioContent Interface
```typescript
interface ImageRadioContent {
  bgImage: string | Component | undefined  // URL, path to image, or Vue component
  value: string                            // Unique identifier for radio button
  label?: string                           // Optional label text below image
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

### Content Slot
Provides customization of the image/visual content displayed in each radio card.

```vue
<template #content="{ item }">
  <!-- Custom content rendering -->
</template>
```

### Label Slot
Provides customization of the label content displayed below each image.

```vue
<template #label="{ label }">
  <!-- Custom label rendering -->
</template>
```

**Slot Props:**
- `item: ImageRadioContent` - The current radio button item data
- `label: string` - The label text for the current radio button item

## Internal Methods

### updateSelectedOption(value: string | null)
Handles radio button selection changes and emits the updated selection.
- Validates input is not null before emitting
- Emits `update:selectedRadio` event

## Usage Examples

### Basic Image Radio Group
```vue
<CustomRadiosWithImage
  v-model:selectedRadio="selectedTheme"
  :radio-content="[
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

### Dashboard Layout Selection
```vue
<CustomRadiosWithImage
  v-model:selectedRadio="selectedLayout"
  :radio-content="[
    {
      bgImage: '/layouts/sidebar-left.png',
      value: 'sidebar-left',
      label: 'Left Sidebar'
    },
    {
      bgImage: '/layouts/sidebar-right.png', 
      value: 'sidebar-right',
      label: 'Right Sidebar'
    },
    {
      bgImage: '/layouts/no-sidebar.png',
      value: 'no-sidebar',
      label: 'No Sidebar'
    },
    {
      bgImage: '/layouts/dual-sidebar.png',
      value: 'dual-sidebar',
      label: 'Dual Sidebar'
    }
  ]"
  :grid-column="{ cols: '6', md: '3' }"
/>
```

### Website Template Selection
```vue
<CustomRadiosWithImage
  v-model:selectedRadio="selectedTemplate"
  :radio-content="websiteTemplates"
  :grid-column="{ cols: '12', sm: '6', lg: '4' }"
>
  <template #label="{ label }">
    <div class="d-flex align-center justify-space-between mt-2">
      <h6 class="text-h6">{{ label }}</h6>
      <VChip
        v-if="isPopularTemplate(label)"
        size="small"
        color="primary"
        variant="tonal"
      >
        Popular
      </VChip>
    </div>
  </template>
</CustomRadiosWithImage>

<script setup>
const websiteTemplates = [
  {
    bgImage: '/templates/business-preview.jpg',
    value: 'business',
    label: 'Business Template'
  },
  {
    bgImage: '/templates/portfolio-preview.jpg',
    value: 'portfolio',
    label: 'Portfolio Template'
  },
  {
    bgImage: '/templates/blog-preview.jpg',
    value: 'blog',
    label: 'Blog Template'
  }
]
</script>
```

### Component-Based Content
```vue
<CustomRadiosWithImage
  v-model:selectedRadio="selectedVisualization"
  :radio-content="[
    {
      bgImage: ChartComponent,
      value: 'chart',
      label: 'Chart View'
    },
    {
      bgImage: TableComponent,
      value: 'table',
      label: 'Table View'
    },
    {
      bgImage: CardComponent,
      value: 'card',
      label: 'Card View'
    }
  ]"
>
  <template #content="{ item }">
    <div class="position-relative">
      <Component :is="item.bgImage" />
      <VOverlay
        :model-value="selectedVisualization !== item.value"
        class="align-center justify-center"
        contained
      >
        <VBtn
          color="primary"
          variant="elevated"
          @click="selectedVisualization = item.value"
        >
          Select
        </VBtn>
      </VOverlay>
    </div>
  </template>
</CustomRadiosWithImage>
```

### Email Template Selection
```vue
<template>
  <CustomRadiosWithImage
    v-model:selectedRadio="selectedEmailTemplate"
    :radio-content="emailTemplates"
    :grid-column="{ cols: '12', md: '6', xl: '4' }"
  />
</template>

<script setup>
const selectedEmailTemplate = ref('newsletter')

const emailTemplates = [
  {
    bgImage: '/email-templates/newsletter-preview.png',
    value: 'newsletter',
    label: 'Newsletter Template'
  },
  {
    bgImage: '/email-templates/promotional-preview.png',
    value: 'promotional',
    label: 'Promotional Email'
  },
  {
    bgImage: '/email-templates/welcome-preview.png',
    value: 'welcome',
    label: 'Welcome Email'
  },
  {
    bgImage: '/email-templates/transactional-preview.png',
    value: 'transactional',
    label: 'Transactional Email'
  }
]
</script>
```

### Color Scheme Selection with SVG
```vue
<CustomRadiosWithImage
  v-model:selectedRadio="selectedColorScheme"
  :radio-content="[
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
    }
  ]"
  :grid-column="{ cols: '4' }"
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
</CustomRadiosWithImage>
```

### Page Layout Selection
```vue
<CustomRadiosWithImage
  v-model:selectedRadio="selectedPageLayout"
  :radio-content="pageLayouts"
  :grid-column="{ cols: '12', sm: '6', md: '4' }"
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
</CustomRadiosWithImage>

<script setup>
const pageLayouts = [
  {
    bgImage: '/layouts/full-width.png',
    value: 'full-width',
    label: 'Full Width'
  },
  {
    bgImage: '/layouts/boxed.png',
    value: 'boxed',
    label: 'Boxed Layout'
  },
  {
    bgImage: '/layouts/fluid.png',
    value: 'fluid',
    label: 'Fluid Layout'
  }
]

const getLayoutDescription = (label) => {
  const descriptions = {
    'Full Width': 'Content spans the entire viewport width',
    'Boxed Layout': 'Content contained within a centered container',
    'Fluid Layout': 'Responsive layout that adapts to screen size'
  }
  return descriptions[label] || ''
}
</script>
```

### Invoice Template Selection
```vue
<CustomRadiosWithImage
  v-model:selectedRadio="selectedInvoiceTemplate"
  :radio-content="invoiceTemplates"
  :grid-column="{ cols: '12', lg: '6' }"
>
  <template #content="{ item }">
    <div class="position-relative">
      <img
        :src="item.bgImage"
        alt="Invoice template preview"
        class="custom-radio-image"
      />
      <VOverlay
        :model-value="false"
        class="align-center justify-center"
        contained
      >
        <VBtn
          icon="mdi-eye"
          color="white"
          variant="elevated"
          size="small"
          @click="previewTemplate(item.value)"
        />
      </VOverlay>
    </div>
  </template>
  
  <template #label="{ label }">
    <div class="d-flex align-center justify-space-between mt-2">
      <span>{{ label }}</span>
      <VBtn
        size="small"
        variant="text"
        color="primary"
        @click="customizeTemplate(item.value)"
      >
        Customize
      </VBtn>
    </div>
  </template>
</CustomRadiosWithImage>
```

## Component Dependencies
- `VRadioGroup` - Vuetify radio group wrapper component
- `VRow` - Vuetify row component for layout
- `VCol` - Vuetify column component for responsive grid
- `VLabel` - Vuetify label component for clickable areas and labels
- `VRadio` - Vuetify radio button component (hidden by default)

## Styling
- Uses `.custom-input-wrapper` class for the radio group container
- Uses `.custom-input`, `.custom-radio` classes for individual items
- Applies `.active` class when radio button is selected
- Image fills entire container with `block-size: 100%` and `inline-size: 100%`
- Radio button is hidden with `visibility: hidden`
- Border appears only when active state is triggered

## CSS Classes
```scss
.custom-radio {
  padding: 0 !important;
  
  &.active {
    border-width: 2px;
  }
  
  .custom-radio-image {
    block-size: 100%;
    inline-size: 100%;
    min-inline-size: 100%;
  }
  
  .v-radio {
    visibility: hidden;
  }
}
```

## Visual Behavior
1. **Default State**: Image displayed with no visible radio button or border
2. **Selected State**: Border appears around the selected image
3. **Hidden Radio**: Radio button is functionally present but visually hidden
4. **Label**: Optional label displayed below the image card

## Image and Component Support

### Image Types
- **Static Images**: JPG, PNG, WebP, SVG
- **Dynamic Components**: Vue components for interactive content
- **Responsive Images**: Automatic scaling to container

### Image Requirements
- **Formats**: All common web image formats supported
- **Aspect Ratio**: Maintain consistent ratios across options
- **Optimization**: Compress images for web performance
- **Alt Text**: Default alt text provided, customize as needed

## Key Features
- **Image-Centric Design**: Images as primary visual selection elements
- **Hidden Radio Button**: Functionally present but visually hidden
- **Single Selection**: Enforced single selection through radio button group
- **Responsive Grid**: Automatic responsive layout with customizable columns
- **Component Support**: Support for Vue components as content
- **Optional Labels**: Flexible label system with slot customization
- **Border Indication**: Clear visual selection state
- **Full Image Coverage**: Images fill entire selection area

## Accessibility Features
- Proper radio button group association
- Unique IDs for each radio button with meaningful names
- Label association with `for` attribute
- Keyboard navigation support (Arrow keys, Tab, Space)
- Screen reader compatibility
- Focus management with visible indicators
- Semantic HTML structure

## Performance Considerations
- Optimize images for web delivery
- Use appropriate image formats (WebP for modern browsers)
- Consider lazy loading for large image sets
- Implement proper image caching strategies
- Use responsive images with `srcset` when applicable
- Optimize component rendering for dynamic content

## Common Use Cases
- **Theme Selection**: Choose website or application themes
- **Template Selection**: Pick from available design templates
- **Layout Options**: Select page or dashboard layouts
- **Color Schemes**: Choose color palettes represented visually
- **Design Patterns**: Select UI design patterns or styles
- **Content Types**: Choose content layout or display formats

## Responsive Behavior
Images automatically scale to fit their grid containers:
- **Mobile**: Single column or 2-column layout
- **Tablet**: 2-3 columns depending on content
- **Desktop**: 3-4 columns for optimal viewing
- **Large screens**: Up to 6 columns with proper spacing

## Integration with Forms
```vue
<!-- With form validation -->
<VForm @submit="handleSubmit">
  <CustomRadiosWithImage
    v-model:selectedRadio="formData.template"
    :radio-content="availableTemplates"
    :rules="[v => !!v || 'A template selection is required']"
  />
</VForm>
```

## Comparison with Related Components

| Feature | CustomRadiosWithImage | CustomRadios | CustomCheckboxesWithImage |
|---------|----------------------|--------------|--------------------------|
| Selection Type | Single | Single | Multiple |
| Model Value | `string` | `string` | `string[]` |
| Visual Focus | Image-centered | Text-focused | Image-centered |
| Radio Visibility | Hidden | Visible | N/A |
| HTML Element | Radio buttons | Radio buttons | Checkboxes |

## State Management
The component manages selection state through:
- Two-way binding with `v-model:selectedRadio`
- Single value selection tracking
- Automatic updates on image click interactions
- Type validation for emitted values

## Advanced Usage Patterns

### Dynamic Image Loading
```javascript
const templateOptions = computed(() =>
  baseTemplates.map(template => ({
    ...template,
    bgImage: `/templates/${template.value}-preview.jpg`
  }))
)
```

### Conditional Component Rendering
```vue
<CustomRadiosWithImage
  :radio-content="visualizationOptions.map(option => ({
    ...option,
    bgImage: getVisualizationComponent(option.type)
  }))"
/>
```

## File Location
`src/@core/components/app-form-elements/CustomRadiosWithImage.vue`