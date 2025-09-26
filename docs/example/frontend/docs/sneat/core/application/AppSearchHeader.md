# AppSearchHeader Component

## Overview
The `AppSearchHeader` component provides a banner-style search interface with customizable title, subtitle, and search input field. It features a background image and responsive design suitable for landing pages, search pages, and content discovery interfaces.

## Location
`/src/components/AppSearchHeader.vue`

## Props Interface

```typescript
interface Props {
  title?: string
  subtitle?: string
  customClass?: string
  placeholder?: string
  density?: 'comfortable' | 'compact' | 'default'
  isReverse?: boolean
}
```

### Props Details

#### `title?: string`
- **Default**: `undefined`
- **Description**: Main heading text displayed in the search banner
- **Usage**: Can be overridden via the `title` slot

#### `subtitle?: string`
- **Default**: `undefined`
- **Description**: Descriptive text displayed below/above the search input
- **Usage**: Provides context or instructions for the search functionality

#### `customClass?: string`
- **Default**: `undefined`
- **Description**: Additional CSS classes to apply to the main card container
- **Usage**: Allows custom styling and layout modifications

#### `placeholder?: string`
- **Default**: `undefined`
- **Description**: Placeholder text for the search input field
- **Usage**: Guides users on what they can search for

#### `density?: 'comfortable' | 'compact' | 'default'`
- **Default**: `'comfortable'`
- **Description**: Controls the spacing and size of the search input
- **Usage**: Adjusts input field density for different layout requirements

#### `isReverse?: boolean`
- **Default**: `false`
- **Description**: Controls the order of subtitle and search input
- **Usage**: 
  - `false`: subtitle appears below search input
  - `true`: subtitle appears above search input

## Events Emitted
This component does not emit any events directly. However, it passes through all events from the underlying `AppTextField` component via `v-bind="$attrs"`.

### Inherited Events (from AppTextField)
- `input` - When user types in the search field
- `focus` - When search field gains focus
- `blur` - When search field loses focus
- `keyup.enter` - When user presses Enter key
- All other standard input events

## Slots Available

### `title`
**Purpose**: Customize the main heading section
**Default Content**: 
```vue
<h4 class="text-h4 mb-2 font-weight-medium">
  {{ props.title }}
</h4>
```

**Usage Example**:
```vue
<AppSearchHeader>
  <template #title>
    <h2 class="text-h2 mb-3 text-primary">
      Find What You Need
    </h2>
  </template>
</AppSearchHeader>
```

## Global State Dependencies
- None - The component is completely self-contained

## Internal Methods and Computed Properties
This component uses a simple template-based approach with no internal methods or computed properties beyond the default props handling.

## Usage Examples

### Basic Usage
```vue
<template>
  <AppSearchHeader
    title="Search Our Knowledge Base"
    subtitle="Find answers to common questions and browse our documentation"
    placeholder="What are you looking for?"
  />
</template>
```

### With Custom Styling
```vue
<template>
  <AppSearchHeader
    title="Product Search"
    subtitle="Browse our catalog of products"
    placeholder="Search products..."
    custom-class="my-custom-search-header"
    density="compact"
  />
</template>
```

### With Reversed Layout
```vue
<template>
  <AppSearchHeader
    title="Advanced Search"
    subtitle="Use keywords to find specific content"
    placeholder="Enter search terms..."
    :is-reverse="true"
  />
</template>
```

### With Custom Title Slot
```vue
<template>
  <AppSearchHeader
    subtitle="Discover amazing content tailored for you"
    placeholder="Search..."
  >
    <template #title>
      <div class="d-flex align-center justify-center mb-2">
        <VIcon icon="mdi-magnify" class="me-2" />
        <h3 class="text-h3">Smart Search</h3>
      </div>
    </template>
  </AppSearchHeader>
</template>
```

### With Event Handling
```vue
<script setup lang="ts">
const searchQuery = ref('')

function handleSearch(event: Event) {
  const target = event.target as HTMLInputElement
  searchQuery.value = target.value
  // Perform search logic
}

function handleEnterKey() {
  // Handle search submission
  console.log('Searching for:', searchQuery.value)
}
</script>

<template>
  <AppSearchHeader
    v-model="searchQuery"
    title="Find Anything"
    subtitle="Search through our vast collection"
    placeholder="Type to search..."
    @input="handleSearch"
    @keyup.enter="handleEnterKey"
  />
</template>
```

### With Dense Layout
```vue
<template>
  <AppSearchHeader
    title="Quick Search"
    subtitle="Fast and efficient search"
    placeholder="Quick search..."
    density="compact"
    custom-class="compact-search-header"
  />
</template>
```

## Component Dependencies

### Images
- `@images/pages/app-search-header-bg.png` - Background image for the search banner

### Components
- `AppTextField` - Custom text field component from `@core/components/app-form-elements/AppTextField.vue`

### Vuetify Components
- `VCard` - Container card component
- `VCardText` - Card content wrapper

### Vue Composition API
- `defineOptions` - Component configuration with `inheritAttrs: false`
- `withDefaults` - Default props handling
- `defineProps` - Props definition

## Styling

### Component Structure
```scss
.search-header {
  padding: 4.1875rem !important;
  background-size: cover !important;
}

.search-header-input {
  border-radius: 0.375rem !important;
  background-color: rgb(var(--v-theme-surface));
  max-inline-size: 28.125rem !important;
}

@media (max-width: 37.5rem) {
  .search-header {
    padding: 1.5rem !important;
  }
}
```

### Visual Features
- **Background Image**: Full-cover background with the provided image
- **Centered Layout**: Content is centered both horizontally and vertically
- **Responsive Padding**: Reduced padding on mobile devices
- **Input Styling**: Custom styling for the search input with theme colors
- **Maximum Width**: Search input has a maximum width constraint

### Layout Behavior
- **isReverse = false**: Title → Search Input → Subtitle
- **isReverse = true**: Title → Subtitle → Search Input

## Implementation Notes

1. **Attribute Inheritance**: Uses `inheritAttrs: false` and manually passes attributes to `AppTextField`
2. **Background Styling**: Inline styles are used for background image application
3. **Responsive Design**: Padding adjusts automatically for mobile screens
4. **Theme Integration**: Uses CSS custom properties for theme colors
5. **Accessibility**: Maintains proper heading hierarchy and form accessibility

## Best Practices

1. **Use Descriptive Placeholders**: Provide clear guidance on what users can search for
2. **Handle Search Events**: Implement proper event handling for search functionality
3. **Consider Layout Options**: Use `isReverse` to match your design requirements
4. **Test Responsiveness**: Verify appearance across different screen sizes
5. **Customize Styling**: Use `customClass` for additional styling needs
6. **Optimize Background Image**: Ensure the background image is optimized for web use

## Accessibility Considerations

1. **Heading Structure**: Uses proper heading hierarchy (h4 by default)
2. **Form Labels**: Relies on AppTextField for proper labeling
3. **Focus Management**: Search input is properly focusable
4. **Color Contrast**: Uses theme colors for proper contrast
5. **Responsive Text**: Text scales appropriately across devices

## Performance Notes

1. **Image Loading**: Background image should be optimized for fast loading
2. **Component Reusability**: Stateless design promotes reusability
3. **Event Delegation**: Efficiently passes through events without additional overhead