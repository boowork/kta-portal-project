# ErrorHeader Component

## Overview
The `ErrorHeader` component provides a clean, centered display for error pages with status codes, titles, and descriptions. It's designed for 404 pages, 500 errors, and other error states with responsive typography and consistent styling.

## Location
`/src/components/ErrorHeader.vue`

## Props Interface

```typescript
interface Props {
  statusCode?: string | number
  title?: string
  description?: string
}
```

### Props Details

#### `statusCode?: string | number`
- **Default**: `undefined`
- **Description**: HTTP status code or error code to display prominently
- **Usage**: Typically "404", "500", "403", etc.
- **Styling**: Large, responsive font size with clamp function

#### `title?: string`
- **Default**: `undefined`
- **Description**: Main error title or heading
- **Usage**: Brief, descriptive error message like "Page Not Found"
- **Styling**: h4 typography with medium font weight

#### `description?: string`
- **Default**: `undefined`
- **Description**: Detailed explanation or instructions for the user
- **Usage**: Helpful message explaining what happened or what to do next
- **Styling**: Body text with margin bottom for spacing

## Events Emitted
This component does not emit any events.

## Slots Available
This component does not provide any slots. All content is controlled through props.

## Global State Dependencies
- None - The component is completely self-contained

## Internal Methods and Computed Properties
This component uses a simple template-based approach with no internal methods or computed properties. All rendering is controlled through conditional prop display.

## Usage Examples

### Basic 404 Error
```vue
<template>
  <ErrorHeader
    status-code="404"
    title="Page Not Found ⚠️"
    description="We couldn't find the page you are looking for."
  />
</template>
```

### 500 Server Error
```vue
<template>
  <ErrorHeader
    status-code="500"
    title="Internal Server Error"
    description="Something went wrong on our end. Please try again later."
  />
</template>
```

### Custom Error with String Code
```vue
<template>
  <ErrorHeader
    status-code="NETWORK_ERROR"
    title="Connection Failed"
    description="Please check your internet connection and try again."
  />
</template>
```

### Minimal Error (Title Only)
```vue
<template>
  <ErrorHeader
    title="Access Denied"
    description="You don't have permission to access this resource."
  />
</template>
```

### Complete Error Page Layout
```vue
<script setup lang="ts">
import misc404 from '@images/pages/404.png'

definePage({
  meta: {
    layout: 'blank',
    public: true,
  },
})
</script>

<template>
  <div class="misc-wrapper">
    <ErrorHeader
      status-code="404"
      title="Page Not Found ⚠️"
      description="We couldn't find the page you are looking for."
    />

    <VBtn
      to="/"
      class="mb-6"
    >
      Back to Home
    </VBtn>

    <div class="misc-avatar w-100 text-center">
      <VImg
        :src="misc404"
        alt="404 Error"
        max-width="400"
        class="mx-auto"
      />
    </div>
  </div>
</template>
```

### Different Status Code Types
```vue
<template>
  <div>
    <!-- Numeric status codes -->
    <ErrorHeader
      :status-code="403"
      title="Forbidden"
      description="You don't have access to this resource."
    />

    <!-- String status codes -->
    <ErrorHeader
      status-code="MAINTENANCE"
      title="Under Maintenance"
      description="We're currently updating our system."
    />
  </div>
</template>
```

### Conditional Rendering
```vue
<script setup lang="ts">
interface ErrorState {
  code?: string | number
  message?: string
  details?: string
}

const error = ref<ErrorState>({
  code: 404,
  message: "Page Not Found",
  details: "The requested page could not be found."
})
</script>

<template>
  <ErrorHeader
    :status-code="error.code"
    :title="error.message"
    :description="error.details"
  />
</template>
```

## Component Dependencies

### Vuetify Components
- None - Uses only standard HTML elements with Vuetify classes

### Vue Composition API
- `defineProps` - Props definition interface

## Styling

### CSS Classes and Structure

#### `.header-title`
```scss
.header-title {
  font-size: clamp(3rem, 5vw, 6rem);
  line-height: clamp(3rem, 5vw, 6rem);
}
```

**Responsive Typography**: 
- Minimum size: `3rem` (48px)
- Preferred size: `5vw` (5% of viewport width)
- Maximum size: `6rem` (96px)

### Applied Classes

#### Status Code
- `.header-title` - Custom responsive sizing
- `.font-weight-medium` - Medium font weight
- `.mb-2` - Margin bottom spacing

#### Title
- `.text-h4` - Vuetify h4 typography
- `.font-weight-medium` - Medium font weight
- `.mb-2` - Margin bottom spacing

#### Description
- `.text-body-1` - Vuetify body text typography
- `.mb-6` - Larger margin bottom for spacing

#### Container
- `.text-center` - Center-aligned text

### Visual Hierarchy
1. **Status Code**: Largest, most prominent element
2. **Title**: Secondary heading providing context
3. **Description**: Supporting text with details

## Implementation Notes

1. **Conditional Rendering**: Each element only renders if its corresponding prop has a value
2. **Type Flexibility**: Status code accepts both string and number types
3. **Responsive Design**: Uses clamp() function for fluid typography
4. **Scoped Styling**: Uses scoped CSS to prevent style conflicts
5. **Accessibility**: Maintains proper heading hierarchy and semantic structure

## Best Practices

1. **Provide Clear Messages**: Use descriptive titles and helpful descriptions
2. **Include Status Codes**: Show relevant HTTP status codes when applicable
3. **Add Action Buttons**: Pair with navigation buttons or retry options
4. **Consider Context**: Tailor messages to the specific error scenario
5. **Test Responsiveness**: Verify typography scales well across devices
6. **Maintain Consistency**: Use consistent error messaging patterns across your app

## Accessibility Considerations

1. **Semantic HTML**: Uses proper heading elements (h1, h4, p)
2. **Text Contrast**: Inherits theme colors for proper contrast
3. **Screen Readers**: Clear content hierarchy and meaningful text
4. **Focus Management**: Consider focus handling when used in error pages
5. **Alternative Text**: When paired with images, ensure proper alt text

## Common Use Cases

1. **404 Pages**: Page not found errors
2. **403 Pages**: Access denied/forbidden
3. **500 Pages**: Server error pages
4. **Network Errors**: Connection failure messages
5. **Maintenance Pages**: Planned downtime notices
6. **Custom Errors**: Application-specific error states

## Error Page Pattern

The component is commonly used in this pattern:

```vue
<template>
  <div class="error-page-container">
    <ErrorHeader
      :status-code="errorCode"
      :title="errorTitle"
      :description="errorDescription"
    />
    
    <div class="error-actions">
      <VBtn @click="goBack">Go Back</VBtn>
      <VBtn to="/" variant="outlined">Home</VBtn>
    </div>
    
    <div class="error-illustration">
      <VImg :src="errorImage" alt="Error illustration" />
    </div>
  </div>
</template>
```

This pattern provides a complete error page experience with clear messaging, actionable options, and visual feedback.