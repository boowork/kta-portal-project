# BuyNow.vue

## Overview
A promotional call-to-action button with animated gradient background that links to the template purchase page. Features marketplace detection and conditional URL routing.

## Props Interface
None - component is self-contained with no configurable props.

## Events Emitted
None - component handles navigation internally through direct links.

## Slots Available
None - component displays fixed "Buy Now" text.

## Global State Dependencies

### Window Object Detection
- Checks for `window.isMarketplace` property to determine the appropriate purchase URL
- Marketplace detection affects which URL is used for the purchase link

## Internal Methods and Computed Properties

### Computed Properties
- `buyNowUrl`: Determines the target URL based on marketplace detection
  - **Marketplace URL**: `https://store.vuetifyjs.com/products/sneat-vuetify-vuejs-admin-template`
  - **Default URL**: `https://themeselection.com/item/sneat-vuetify-vuejs-admin-template/`

## Component Dependencies
None - pure HTML/CSS component with no external dependencies.

## Usage Examples

### Basic Usage
```vue
<template>
  <div>
    <!-- Other page content -->
    <BuyNow />
  </div>
</template>
```

### Conditional Display
```vue
<template>
  <div>
    <BuyNow v-if="showBuyButton" />
  </div>
</template>

<script setup>
const showBuyButton = ref(true)

// Hide in certain environments
onMounted(() => {
  if (process.env.NODE_ENV === 'development') {
    showBuyButton.value = false
  }
})
</script>
```

## Styling Features

### Gradient Animation
- Animated gradient background with multiple colors: `#ffa63d`, `#ff3d77`, `#338aff`, `#3cf0c5`
- 12-second linear infinite animation cycle
- 600% background size for smooth gradient movement

### Visual Effects
- Blur effect on hover with inner shadow element
- Fixed positioning in bottom-right corner
- Smooth opacity transitions on hover
- Rounded corners (6px border-radius)

### Responsive Positioning
- Fixed position: `bottom: 5%`, `right: 87px`
- High z-index (999) to stay above other content
- Print styles: `d-print-none` class hides in print media

## Link Attributes

### Accessibility & Security
- `role="button"`: Semantic button role
- `rel="noopener noreferrer"`: Security attributes for external links
- `target="_blank"`: Opens in new tab/window

## CSS Animation

### Keyframes
```css
@keyframes anime {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}
```

### Hover Effects
- Inner glow element with blur filter
- Opacity transitions for smooth interactions
- Color changes on hover state

## Environment Considerations

### Marketplace Detection
The component detects if it's running in a marketplace environment:

```javascript
const buyNowUrl = (typeof window !== 'undefined' && 'isMarketplace' in window && window.isMarketplace)
  ? 'https://store.vuetifyjs.com/products/sneat-vuetify-vuejs-admin-template'
  : 'https://themeselection.com/item/sneat-vuetify-vuejs-admin-template/'
```

This allows for different purchase flows depending on the distribution platform.

## Customization Options

While the component doesn't accept props, it can be customized through:

1. **CSS Variables**: Override the gradient colors or animation timing
2. **Environment Variables**: Set `window.isMarketplace` for URL routing
3. **CSS Classes**: Add additional styling through parent components
4. **Conditional Rendering**: Show/hide based on application state

## Best Practices

1. **Placement**: Position strategically to avoid interfering with main content
2. **Environment**: Consider hiding in development or demo environments
3. **Analytics**: Track clicks for conversion measurement
4. **Accessibility**: Ensure proper contrast and keyboard navigation
5. **Performance**: Animation is CSS-based for optimal performance