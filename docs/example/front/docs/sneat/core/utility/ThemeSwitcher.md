# ThemeSwitcher.vue

## Overview
A dropdown theme selector component that allows users to switch between different theme modes (light, dark, system). Features automatic icon updates, tooltips, and reactive state management.

## Props Interface

```typescript
interface ThemeSwitcherTheme {
  name: string    // Theme identifier (e.g., 'light', 'dark', 'system')
  icon: string    // Icon identifier for the theme
}

interface Props {
  themes: ThemeSwitcherTheme[]  // Array of available theme options
}
```

## Events Emitted
None - component updates global theme state directly through the config store.

## Slots Available
None - component has a structured dropdown interface.

## Global State Dependencies

### Configuration Store
- `useConfigStore()`: Manages global theme configuration
- Reactive to `configStore.theme` changes
- Updates theme state when selection changes

## Internal Methods and Computed Properties

### Reactive Properties
- `selectedItem`: Tracks currently selected theme (array format for VList)

### Watchers
- Monitors `configStore.theme` changes to update selected state
- Ensures UI stays synchronized with external theme changes

## Component Dependencies

### Custom Components
- `IconBtn`: Icon button for the dropdown trigger

### Vuetify Components
- `VIcon`: Theme icons and selected theme display
- `VTooltip`: Theme name tooltip on hover
- `VMenu`: Dropdown menu container
- `VList`: Theme options list
- `VListItem`: Individual theme options
- `VListItemTitle`: Theme name display

## Usage Examples

### Basic Usage
```vue
<template>
  <ThemeSwitcher :themes="availableThemes" />
</template>

<script setup>
const availableThemes = [
  { name: 'light', icon: 'bx-sun' },
  { name: 'dark', icon: 'bx-moon' },
  { name: 'system', icon: 'bx-desktop' }
]
</script>
```

### In App Header
```vue
<template>
  <VAppBar>
    <VAppBarTitle>My Application</VAppBarTitle>
    <VSpacer />
    
    <!-- Theme switcher in header -->
    <ThemeSwitcher :themes="themeOptions" />
    
    <!-- Other header items -->
    <UserMenu />
  </VAppBar>
</template>

<script setup>
const themeOptions = [
  { name: 'light', icon: 'bx-sun' },
  { name: 'dark', icon: 'bx-moon' },
  { name: 'system', icon: 'bx-desktop' }
]
</script>
```

### With Custom Themes
```vue
<template>
  <ThemeSwitcher :themes="customThemes" />
</template>

<script setup>
const customThemes = [
  { name: 'light', icon: 'bx-sun' },
  { name: 'dark', icon: 'bx-moon' },
  { name: 'auto', icon: 'bx-time' },
  { name: 'high-contrast', icon: 'bx-accessibility' }
]
</script>
```

### In Settings Panel
```vue
<template>
  <VCard>
    <VCardTitle>Appearance Settings</VCardTitle>
    <VCardText>
      <div class="d-flex align-center justify-space-between">
        <div>
          <VLabel class="text-h6">Theme</VLabel>
          <p class="text-body-2 mb-0">
            Choose your preferred theme
          </p>
        </div>
        <ThemeSwitcher :themes="themes" />
      </div>
    </VCardText>
  </VCard>
</template>
```

### With Theme Preview
```vue
<template>
  <div class="theme-selector">
    <div class="d-flex align-center gap-4">
      <div>
        <h3>Theme Settings</h3>
        <p>Current theme: {{ currentThemeName }}</p>
      </div>
      <ThemeSwitcher :themes="themes" />
    </div>
    
    <!-- Theme preview -->
    <div class="theme-preview mt-4">
      <VCard :color="previewColor">
        <VCardText>
          <h4>Preview</h4>
          <p>This is how your theme will look</p>
        </VCardText>
      </VCard>
    </div>
  </div>
</template>

<script setup>
const { configStore } = useConfigStore()

const currentThemeName = computed(() => {
  return configStore.theme.charAt(0).toUpperCase() + 
         configStore.theme.slice(1)
})

const previewColor = computed(() => {
  return configStore.theme === 'dark' ? 'grey-darken-3' : 'grey-lighten-4'
})
</script>
```

### Programmatic Theme Control
```vue
<template>
  <div>
    <ThemeSwitcher :themes="themes" />
    
    <!-- Additional theme controls -->
    <div class="mt-4">
      <VBtn @click="setLightTheme">Force Light</VBtn>
      <VBtn @click="setDarkTheme">Force Dark</VBtn>
      <VBtn @click="setSystemTheme">Use System</VBtn>
    </div>
  </div>
</template>

<script setup>
const configStore = useConfigStore()

const setLightTheme = () => {
  configStore.theme = 'light'
}

const setDarkTheme = () => {
  configStore.theme = 'dark'
}

const setSystemTheme = () => {
  configStore.theme = 'system'
}
</script>
```

## Theme System Integration

### Configuration Store Integration
```javascript
const configStore = useConfigStore()

// Reading current theme
const currentTheme = computed(() => configStore.theme)

// Setting theme
const setTheme = (themeName) => {
  configStore.theme = themeName
}
```

### Theme Persistence
Themes are automatically persisted through the configuration store, typically using:
- Local storage
- Cookies
- User preferences API

## UI Components Breakdown

### Icon Button
- Displays current theme icon
- High emphasis color with proper opacity
- 22px icon size for consistency

### Tooltip
- Shows capitalized theme name on hover
- 1000ms open delay for non-intrusive UX
- Close on scroll for mobile compatibility

### Dropdown Menu
- 21px offset from trigger button
- 180px width for optimal text display
- Mandatory selection (always one selected)

### Theme List Items
- Prepend icon showing theme icon
- Capitalized theme names
- Primary color selection indicator
- Click handlers for theme switching

## Styling Features

### Icon Button Styling
```vue
<IconBtn color="rgba(var(--v-theme-on-surface), var(--v-high-emphasis-opacity))">
```
- Uses theme-aware high emphasis color
- Consistent with design system

### Menu Styling
- Fixed width for consistent layout
- Proper spacing and padding
- Clean list item styling

## Accessibility Features

- Semantic button structure
- Proper ARIA attributes through VList
- Keyboard navigation support
- Clear visual indication of selected theme
- High contrast icon visibility
- Tooltip for context

## State Synchronization

### Reactive Updates
```javascript
watch(
  () => configStore.theme,
  () => {
    selectedItem.value = [configStore.theme]
  },
  { deep: true }
)
```

This ensures the UI stays synchronized when theme changes occur from:
- Other components
- External state updates
- System theme changes
- URL parameters

## Theme Options

### Standard Themes
- **Light**: Traditional light theme
- **Dark**: Dark mode theme
- **System**: Follows OS preference

### Custom Theme Support
The component supports any theme defined in the configuration:
```javascript
const customThemes = [
  { name: 'corporate', icon: 'bx-building' },
  { name: 'nature', icon: 'bx-leaf' },
  { name: 'ocean', icon: 'bx-water' }
]
```

## Integration Patterns

### With Media Query Detection
```javascript
// Automatic system theme detection
const prefersDark = useMediaQuery('(prefers-color-scheme: dark)')

watch(prefersDark, (isDark) => {
  if (configStore.theme === 'system') {
    // Apply system preference
    applyTheme(isDark ? 'dark' : 'light')
  }
})
```

### With User Preferences
```javascript
// Save theme preference
watch(() => configStore.theme, async (newTheme) => {
  await api.updateUserPreferences({
    theme: newTheme
  })
})
```

### With Analytics
```javascript
const trackThemeChange = (theme) => {
  analytics.track('theme_changed', {
    theme: theme,
    timestamp: Date.now()
  })
}
```

## Best Practices

1. **Consistent Icons**: Use clear, recognizable icons for each theme
2. **System Integration**: Support system theme preference
3. **Persistence**: Save user's theme choice
4. **Performance**: Efficient theme switching without flicker
5. **Accessibility**: Ensure proper contrast in all themes
6. **Mobile**: Optimize for touch interactions

## Common Use Cases

- Application theme switching
- User preference management
- Accessibility compliance
- Brand theme variants
- Development/testing theme modes
- Seasonal theme changes

This component provides a clean, user-friendly interface for theme management while maintaining consistency with the overall design system and ensuring proper state management across the application.