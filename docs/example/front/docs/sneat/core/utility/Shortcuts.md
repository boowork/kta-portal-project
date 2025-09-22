# Shortcuts.vue

## Overview
A dropdown menu component that displays application shortcuts in a grid layout. Features customizable icons, navigation integration, and scrollable content for quick access to key application areas.

## Props Interface

```typescript
interface Shortcut {
  icon: string           // Icon identifier for the shortcut
  title: string         // Display title for the shortcut
  subtitle: string      // Description text for the shortcut
  to: object | string   // Route destination (router-link compatible)
}

interface Props {
  togglerIcon?: string  // Optional custom toggler icon (default: 'bx-grid-alt')
  shortcuts: Shortcut[] // Array of shortcut objects
}
```

## Events Emitted
None - component handles navigation internally through Vue Router.

## Slots Available
None - component has a structured grid layout for shortcuts.

## Global State Dependencies

### Vue Router Integration
- Uses `useRouter()` for programmatic navigation
- Supports both string and object route destinations

## Internal Methods and Computed Properties

### Composables Used
- `useRouter()`: Vue Router instance for navigation

### Default Values
- `togglerIcon`: Defaults to `'bx-grid-alt'` (grid icon)

## Component Dependencies

### External Libraries
- `vue3-perfect-scrollbar`: Custom scrollbar for shortcut content

### Vuetify Components
- `VMenu`: Dropdown container
- `VCard`: Shortcuts panel container
- `VCardItem`: Header section
- `VRow`, `VCol`: Grid layout for shortcuts
- `VAvatar`: Shortcut icons
- `VIcon`: Icons and action buttons
- `VDivider`: Visual separator

### Custom Components
- `IconBtn`: Icon buttons for toggler and actions

## Usage Examples

### Basic Usage
```vue
<template>
  <Shortcuts :shortcuts="appShortcuts" />
</template>

<script setup>
const appShortcuts = [
  {
    icon: 'bx-home',
    title: 'Dashboard',
    subtitle: 'Main overview',
    to: '/dashboard'
  },
  {
    icon: 'bx-user',
    title: 'Users',
    subtitle: 'Manage users',
    to: '/users'
  },
  {
    icon: 'bx-chart',
    title: 'Analytics',
    subtitle: 'View reports',
    to: '/analytics'
  },
  {
    icon: 'bx-cog',
    title: 'Settings',
    subtitle: 'Configuration',
    to: { name: 'settings', params: { tab: 'general' } }
  }
]
</script>
```

### Custom Toggler Icon
```vue
<template>
  <Shortcuts 
    :shortcuts="shortcuts"
    toggler-icon="bx-apps"
  />
</template>
```

### In App Header
```vue
<template>
  <VAppBar>
    <VAppBarTitle>My Application</VAppBarTitle>
    <VSpacer />
    
    <!-- Quick access shortcuts -->
    <Shortcuts :shortcuts="quickActions" />
    
    <!-- Other header items -->
    <UserMenu />
  </VAppBar>
</template>

<script setup>
const quickActions = [
  {
    icon: 'bx-plus',
    title: 'New Project',
    subtitle: 'Create project',
    to: '/projects/new'
  },
  {
    icon: 'bx-message',
    title: 'Messages',
    subtitle: 'View inbox',
    to: '/messages'
  },
  {
    icon: 'bx-bell',
    title: 'Notifications',
    subtitle: 'Recent alerts',
    to: '/notifications'
  },
  {
    icon: 'bx-help-circle',
    title: 'Help',
    subtitle: 'Get support',
    to: '/help'
  }
]
</script>
```

### Dynamic Shortcuts Based on User Role
```vue
<template>
  <Shortcuts :shortcuts="userShortcuts" />
</template>

<script setup>
const { user } = useAuth()

const userShortcuts = computed(() => {
  const baseShortcuts = [
    {
      icon: 'bx-home',
      title: 'Dashboard',
      subtitle: 'Main view',
      to: '/dashboard'
    }
  ]

  if (user.value?.role === 'admin') {
    baseShortcuts.push(
      {
        icon: 'bx-shield',
        title: 'Admin Panel',
        subtitle: 'System admin',
        to: '/admin'
      },
      {
        icon: 'bx-users',
        title: 'User Management',
        subtitle: 'Manage users',
        to: '/admin/users'
      }
    )
  }

  if (user.value?.permissions?.includes('analytics')) {
    baseShortcuts.push({
      icon: 'bx-line-chart',
      title: 'Analytics',
      subtitle: 'View metrics',
      to: '/analytics'
    })
  }

  return baseShortcuts
})
</script>
```

### With Categories/Sections
```vue
<template>
  <div>
    <!-- Main shortcuts -->
    <Shortcuts 
      :shortcuts="mainShortcuts"
      toggler-icon="bx-grid-alt"
    />
    
    <!-- Admin shortcuts -->
    <Shortcuts 
      v-if="isAdmin"
      :shortcuts="adminShortcuts"
      toggler-icon="bx-shield"
    />
  </div>
</template>

<script setup>
const mainShortcuts = [
  // Main app shortcuts
]

const adminShortcuts = [
  {
    icon: 'bx-server',
    title: 'System Status',
    subtitle: 'Server health',
    to: '/admin/system'
  },
  {
    icon: 'bx-data',
    title: 'Database',
    subtitle: 'Manage data',
    to: '/admin/database'
  }
]
</script>
```

### With External Links
```vue
<template>
  <Shortcuts :shortcuts="mixedShortcuts" />
</template>

<script setup>
const router = useRouter()

const mixedShortcuts = [
  {
    icon: 'bx-home',
    title: 'Dashboard',
    subtitle: 'Main view',
    to: '/dashboard'
  },
  {
    icon: 'bx-help-circle',
    title: 'Documentation',
    subtitle: 'View docs',
    to: 'https://docs.example.com' // External link
  },
  {
    icon: 'bx-support',
    title: 'Support',
    subtitle: 'Get help',
    to: 'mailto:support@example.com' // Email link
  }
]

// Custom navigation handler for external links
const handleShortcutClick = (shortcut) => {
  if (typeof shortcut.to === 'string' && shortcut.to.startsWith('http')) {
    window.open(shortcut.to, '_blank')
  } else if (typeof shortcut.to === 'string' && shortcut.to.startsWith('mailto:')) {
    window.location.href = shortcut.to
  } else {
    router.push(shortcut.to)
  }
}
</script>
```

## Layout Structure

### Menu Layout
```
┌─────────────────────────────────────┐
│ Shortcuts                    [+]    │ ← Header with add button
├─────────────────────────────────────┤
│ ┌─────────┐  ┌─────────┐            │
│ │ [Icon]  │  │ [Icon]  │            │ ← 2-column grid
│ │ Title   │  │ Title   │            │
│ │Subtitle │  │Subtitle │            │
│ └─────────┘  └─────────┘            │
│ ┌─────────┐  ┌─────────┐            │
│ │ [Icon]  │  │ [Icon]  │            │
│ │ Title   │  │ Title   │            │
│ │Subtitle │  │Subtitle │            │
│ └─────────┘  └─────────┘            │
└─────────────────────────────────────┘
```

### Grid Configuration
- 2 columns per row (`cols="6"`)
- Responsive width: 330px on small screens, 380px on larger
- Maximum height: 560px with scrolling
- Centered content with proper spacing

## Styling Features

### Menu Styling
- Dynamic width based on screen size
- Perfect scrollbar for overflow content
- Clean card design with proper spacing
- Border styling for visual separation

### Shortcut Items
- 50px tonal avatar for icons
- High emphasis icons (size 26)
- Proper typography hierarchy
- Hover effects for interactivity

### Grid Layout
```scss
.shortcut-icon:hover {
  background-color: rgba(var(--v-theme-on-surface), var(--v-hover-opacity));
}
```

## Navigation Behavior

### Route Handling
- Supports Vue Router route objects
- Handles string paths
- Programmatic navigation via `router.push()`
- Closes menu after navigation

### Navigation Types
1. **Internal Routes**: Vue Router navigation
2. **Route Objects**: Complex routing with params/query
3. **External Links**: Could be extended for external navigation

## Accessibility Features

- Keyboard navigation through menu items
- Proper semantic structure with headings
- Clear visual hierarchy
- High contrast icons and text
- Descriptive labels for screen readers

## Performance Considerations

### Efficient Rendering
- Perfect scrollbar for smooth scrolling
- Minimal re-renders with reactive shortcuts
- Optimized grid layout

### Memory Management
- Clean component unmounting
- No memory leaks from event listeners
- Efficient router navigation

## Best Practices

1. **Limit Items**: Keep shortcuts to essential actions (max 8-12)
2. **Clear Labels**: Use descriptive titles and subtitles
3. **Consistent Icons**: Use consistent icon style/library
4. **Role-Based**: Show relevant shortcuts for user roles
5. **Performance**: Avoid complex route objects when simple strings work
6. **Analytics**: Track shortcut usage for optimization

## Common Use Cases

- Quick navigation menu
- Application launcher
- Admin quick actions
- Feature discovery
- Workflow shortcuts
- Tool palette
- Command center

## Integration Patterns

### With Permissions
```javascript
const shortcuts = computed(() => 
  allShortcuts.filter(shortcut => 
    hasPermission(shortcut.permission)
  )
)
```

### With Analytics
```javascript
const trackShortcutUsage = (shortcut) => {
  analytics.track('shortcut_clicked', {
    title: shortcut.title,
    destination: shortcut.to
  })
}
```

This component provides an efficient way to give users quick access to key application features while maintaining a clean and organized interface.