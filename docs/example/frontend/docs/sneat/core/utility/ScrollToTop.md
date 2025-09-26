# ScrollToTop.vue

## Overview
A floating action button that appears when users scroll down and allows them to smoothly scroll back to the top of the page. Features smooth animations and responsive visibility.

## Props Interface
None - component uses internal scroll detection.

## Events Emitted
None - component handles scroll behavior internally.

## Slots Available
None - component has a fixed button structure.

## Global State Dependencies

### Window Scroll Position
- Uses `useWindowScroll()` composable to track scroll position
- Reactive to `y` scroll coordinate changes

## Internal Methods and Computed Properties

### Reactive Properties
- `y`: Current vertical scroll position from `useWindowScroll()`

### Methods
- `scrollToTop()`: Smoothly scrolls to top of page

### Composables Used
- `useWindowScroll()`: VueUse composable for scroll position tracking

## Component Dependencies

### Vuetify Components
- `VScaleTransition`: Smooth scale animation for button appearance
- `VBtn`: Floating action button
- `VIcon`: Up arrow icon (bx-up-arrow-alt)

### VueUse Composables
- `useWindowScroll`: Tracks window scroll position

## Usage Examples

### Basic Usage
```vue
<template>
  <div>
    <!-- Page content -->
    <div class="content">
      <!-- Long content that requires scrolling -->
    </div>
    
    <!-- Scroll to top button -->
    <ScrollToTop />
  </div>
</template>
```

### In Layout Component
```vue
<template>
  <VApp>
    <VAppBar>
      <!-- App bar content -->
    </VAppBar>
    
    <VMain>
      <router-view />
    </VMain>
    
    <!-- Global scroll to top button -->
    <ScrollToTop />
  </VApp>
</template>
```

### With Custom Scroll Threshold
```vue
<template>
  <div>
    <div class="long-content">
      <!-- Content -->
    </div>
    <CustomScrollToTop :threshold="500" />
  </div>
</template>

<!-- Custom implementation with threshold prop -->
<script setup>
const props = defineProps({
  threshold: { type: Number, default: 200 }
})

const { y } = useWindowScroll()

const scrollToTop = () => {
  window.scrollTo({
    top: 0,
    behavior: 'smooth',
  })
}
</script>

<template>
  <VScaleTransition>
    <VBtn
      v-show="y > threshold"
      icon
      @click="scrollToTop"
    >
      <VIcon icon="bx-up-arrow-alt" />
    </VBtn>
  </VScaleTransition>
</template>
```

### In Blog/Article Layout
```vue
<template>
  <article class="blog-post">
    <header>
      <h1>{{ article.title }}</h1>
    </header>
    
    <div class="article-content">
      {{ article.content }}
    </div>
    
    <footer>
      <!-- Article footer -->
    </footer>
    
    <ScrollToTop />
  </article>
</template>
```

### With Analytics Tracking
```vue
<template>
  <ScrollToTop @click="trackScrollToTop" />
</template>

<script setup>
// Custom implementation with analytics
const { y } = useWindowScroll()

const scrollToTop = () => {
  // Track analytics event
  gtag('event', 'scroll_to_top', {
    scroll_position: y.value
  })
  
  window.scrollTo({
    top: 0,
    behavior: 'smooth',
  })
}
</script>
```

## Visibility Behavior

### Scroll Threshold
- Button becomes visible when `y > 200` pixels
- Uses `v-show` directive for efficient DOM updates
- Smooth scale transition for appearance/disappearance

### Animation Details
- `VScaleTransition` provides smooth scale effect
- `transform-origin: center` for centered scaling
- Respects user motion preferences

## Styling Features

### Positioning
- Fixed position: `position: fixed !important`
- Bottom right corner: `inset-block-end: 5%`, `inset-inline-end: 25px`
- High z-index (999) to stay above other content
- Print hidden: `d-print-none` class

### Button Styling
- Icon button with comfortable density
- 22px icon size for optimal visibility
- Consistent with design system button styles

### CSS Structure
```scss
.scroll-to-top {
  position: fixed !important;
  z-index: 999;
  inset-block-end: 5%;
  inset-inline-end: 25px;
}
```

## Smooth Scrolling Implementation

### Native Smooth Scroll
```javascript
const scrollToTop = () => {
  window.scrollTo({
    top: 0,
    behavior: 'smooth',
  })
}
```

Benefits:
- Native browser implementation
- Respects user accessibility preferences
- Consistent with browser behavior
- No additional dependencies

## Accessibility Features

- Semantic button element
- Clear icon indicating upward navigation
- Respects `prefers-reduced-motion` settings
- Keyboard accessible
- Proper focus management

## Performance Considerations

### Efficient Scroll Tracking
- Uses VueUse's optimized scroll tracking
- Reactive updates only when needed
- No manual event listeners to clean up

### Minimal DOM Manipulation
- `v-show` instead of `v-if` for better performance
- Single button element with minimal structure
- Smooth CSS transitions

## Browser Compatibility

### Smooth Scroll Support
- Modern browsers support `scroll-behavior: smooth`
- Fallback to instant scroll in older browsers
- Progressive enhancement approach

### CSS Features
- Fixed positioning (widely supported)
- CSS transitions (graceful degradation)
- Logical properties (modern browsers)

## Best Practices

1. **Consistent Placement**: Use same position across all pages
2. **Appropriate Threshold**: 200px is good for most layouts
3. **Performance**: Use efficient scroll tracking
4. **Accessibility**: Ensure keyboard accessibility
5. **Mobile**: Test on mobile devices for thumb reach
6. **Print Styles**: Hide in print layouts

## Common Variations

### Different Positioning
```scss
// Left side placement
.scroll-to-top {
  inset-inline-start: 25px;
  inset-inline-end: auto;
}

// Higher position
.scroll-to-top {
  inset-block-end: 10%;
}
```

### Custom Animations
```vue
<VFadeTransition>
  <VBtn v-show="y > 200">
    <VIcon>bx-up-arrow-alt</VIcon>
  </VBtn>
</VFadeTransition>
```

### Progress Indicator
```vue
<VBtn 
  v-show="y > 200"
  class="scroll-progress"
  :style="{ '--progress': scrollProgress + '%' }"
>
  <VIcon>bx-up-arrow-alt</VIcon>
</VBtn>
```

This component provides a standard, accessible way to improve navigation on long pages while maintaining good performance and user experience.