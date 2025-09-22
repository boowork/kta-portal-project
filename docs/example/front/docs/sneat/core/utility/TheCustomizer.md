# TheCustomizer.vue

## Overview
A comprehensive theme customization panel that allows users to modify appearance, layout, and style settings in real-time. Features primary color selection, theme modes, layout options, and complete theme reset functionality.

## Props Interface
None - component manages its own state and integrates with global theme configuration.

## Events Emitted
None - component updates global theme state directly.

## Slots Available
None - component has a structured customization interface.

## Global State Dependencies

### Configuration Stores
- `useConfigStore()`: Layout and app configuration management
- `useTheme()`: Vuetify theme management
- `useI18n()`: Internationalization state

### Theme Configuration
- `themeConfig`: Global theme configuration object
- `staticPrimaryColor`: Default primary color values
- Cookie-based persistence for theme preferences

## Internal Methods and Computed Properties

### Reactive Properties
- `isNavDrawerOpen`: Controls customizer drawer visibility
- `customPrimaryColor`: Custom color picker value
- `currentLayout`: Layout type selection
- `currentDir`: Text direction (LTR/RTL)
- `isCookieHasAnyValue`: Tracks if customizations exist

### Computed Properties
- `themeMode`: Available theme modes (light, dark, system)
- `themeSkin`: Available skin variants (default, bordered)
- `layouts`: Layout options (vertical, collapsed, horizontal)
- `contentWidth`: Content width options (boxed, fluid)
- `direction`: Text direction options (LTR, RTL)
- `isActiveLangRTL`: Checks if current language requires RTL

### Methods
- `setPrimaryColor()`: Updates primary theme color with debouncing
- `resetCustomizer()`: Resets all theme settings to defaults

## Component Dependencies

### External Libraries
- `vue3-perfect-scrollbar`: Scrollable customization content
- `@vueuse/core`: Storage utilities for persistence

### Vuetify Components
- `VNavigationDrawer`: Main customizer panel
- `VBtn`: Action buttons and toggles
- `VColorPicker`: Custom color selection
- `VSwitch`: Toggle switches
- `VBadge`: Reset indicator

### Custom Components
- `CustomizerSection`: Section organization component
- `CustomRadiosWithImage`: Visual radio button selections

## Usage Examples

### Basic Integration
```vue
<template>
  <VApp>
    <!-- Main app content -->
    <VMain>
      <router-view />
    </VMain>
    
    <!-- Global customizer -->
    <TheCustomizer />
  </VApp>
</template>
```

### Conditional Display
```vue
<template>
  <div>
    <!-- Main content -->
    <div class="app-content">
      <!-- Your app -->
    </div>
    
    <!-- Show customizer only for admins or in development -->
    <TheCustomizer v-if="showCustomizer" />
  </div>
</template>

<script setup>
const { user } = useAuth()

const showCustomizer = computed(() => {
  return process.env.NODE_ENV === 'development' || 
         user.value?.role === 'admin' ||
         user.value?.permissions?.includes('theme-customization')
})
</script>
```

### With Custom Restrictions
```vue
<template>
  <TheCustomizer v-if="canCustomizeTheme" />
</template>

<script setup>
const { user } = useAuth()
const { subscription } = useSubscription()

const canCustomizeTheme = computed(() => {
  return subscription.value?.plan === 'premium' ||
         user.value?.role === 'admin'
})
</script>
```

## Customization Features

### Primary Color Selection
- **Predefined Colors**: 5 preset color options
- **Custom Color Picker**: Full color picker with hex values
- **Real-time Preview**: Instant color updates across the app
- **Persistence**: Color preferences saved in cookies

```javascript
const colors = [
  { main: '#8B5CF6', darken: '#7C3AED' }, // Purple
  { main: '#0D9394', darken: '#0C8485' }, // Teal
  { main: '#FFB400', darken: '#E6A200' }, // Orange
  { main: '#FF4C51', darken: '#E64449' }, // Red
  { main: '#16B1FF', darken: '#149FE6' }  // Blue
]
```

### Theme Modes
- **Light Mode**: Standard light theme
- **Dark Mode**: Dark theme variant
- **System Mode**: Follows OS preference

### Layout Options
- **Vertical**: Standard vertical navigation
- **Collapsed**: Collapsed sidebar navigation
- **Horizontal**: Top horizontal navigation

### Content Width
- **Boxed**: Constrained content width
- **Fluid**: Full-width content

### Text Direction
- **LTR**: Left-to-right text direction
- **RTL**: Right-to-left text direction

## State Management

### Cookie Persistence
Theme preferences are persisted using cookies:
```javascript
// Color persistence
cookieRef(`${vuetifyTheme.name.value}ThemePrimaryColor`, null).value = color.main

// Configuration persistence
const configStore = useConfigStore()
// Settings automatically synced with cookies
```

### Reset Functionality
Complete theme reset restores all settings to defaults:
```javascript
const resetCustomizer = async () => {
  // Reset colors
  vuetifyTheme.themes.value.light.colors.primary = staticPrimaryColor
  vuetifyTheme.themes.value.dark.colors.primary = staticPrimaryColor
  
  // Reset configuration
  configStore.theme = themeConfig.app.theme
  configStore.skin = themeConfig.app.skin
  // ... other settings
  
  // Clear cookies
  cookieRef('lightThemePrimaryColor', null).value = null
  // ... other cookies
}
```

## Visual Layout

### Customizer Structure
```
┌─────────────────────────────────────┐
│ Theme Customizer         [reset][×] │ ← Header with actions
├─────────────────────────────────────┤
│                                     │
│ ■ Theming                          │ ← Primary section
│   • Primary Color [color swatches]  │
│   • Mode [light][dark][system]      │
│   • Skins [default][bordered]       │
│   • Semi Dark Menu [toggle]         │
│                                     │
│ ■ Layout                           │ ← Layout section
│   • Layout [vertical][collapsed]    │
│   • Content [boxed][wide]          │
│   • Direction [ltr][rtl]           │
│                                     │
└─────────────────────────────────────┘
```

### Interactive Elements
- Color swatches with selection indicators
- Visual radio buttons for layout options
- Toggle switches for binary settings
- Smooth animations and transitions

## Advanced Features

### Debounced Color Updates
Color changes are debounced to prevent excessive updates:
```javascript
const setPrimaryColor = useDebounceFn((color) => {
  vuetifyTheme.themes.value[vuetifyTheme.name.value].colors.primary = color.main
  // Update theme with 100ms debounce
}, 100)
```

### Smart Layout Management
Layout options intelligently manage related settings:
```javascript
watch(currentLayout, () => {
  if (currentLayout.value === 'collapsed') {
    configStore.isVerticalNavCollapsed = true
    configStore.appContentLayoutNav = AppContentLayoutNav.Vertical
  } else {
    configStore.isVerticalNavCollapsed = false
    configStore.appContentLayoutNav = currentLayout.value
  }
})
```

### RTL Support Integration
Automatic RTL detection based on selected language:
```javascript
const isActiveLangRTL = computed(() => {
  const lang = themeConfig.app.i18n.langConfig.find(l => l.i18nLang === locale.value)
  return lang?.isRTL ?? false
})
```

## Styling and Animations

### Drawer Animation
Custom slide animation for drawer entry/exit:
```scss
.app-customizer {
  &.v-navigation-drawer--temporary:not(.v-navigation-drawer--active) {
    transform: translateX(110%) !important;
    
    @include layoutMixins.rtl {
      transform: translateX(-110%) !important;
    }
  }
}
```

### Interactive States
- Hover effects on color swatches
- Active state indicators
- Smooth transitions between states

## Performance Considerations

### Efficient Updates
- Debounced color updates prevent excessive re-renders
- Smart watchers only trigger when necessary
- Optimized cookie operations

### Memory Management
- Proper cleanup on component unmount
- Efficient reactive property management
- Minimal DOM manipulations

## Best Practices

1. **Progressive Enhancement**: Works without customizer enabled
2. **Performance**: Debounced updates for smooth interaction
3. **Persistence**: Theme preferences survive page reloads
4. **Accessibility**: Keyboard navigation and screen reader support
5. **Responsive**: Works on mobile and desktop devices
6. **Validation**: Prevents invalid color values and configurations

## Integration Patterns

### With Authentication
```javascript
const canAccessCustomizer = computed(() => {
  return user.value?.permissions?.includes('theme-customization')
})
```

### With Feature Flags
```javascript
const showCustomizer = computed(() => {
  return featureFlags.value.themeCustomization && 
         !isProduction
})
```

### With Analytics
```javascript
watch(() => configStore.$state, (newState) => {
  analytics.track('theme_changed', {
    theme: newState.theme,
    layout: newState.appContentLayoutNav,
    // ... other properties
  })
}, { deep: true })
```

This component provides a complete theme customization solution that enhances user experience while maintaining performance and accessibility standards.