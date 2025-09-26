# Notifications.vue

## Overview
A comprehensive notifications dropdown component with badge indicator, read/unread management, and customizable notification display. Features perfect scrollbar, bulk actions, and interactive notification management.

## Props Interface

```typescript
interface Props {
  notifications: Notification[]  // Array of notification objects
  badgeProps?: object           // Optional badge customization props
  location?: any                // Menu position (default: 'bottom end')
}

interface Notification {
  id: number               // Unique identifier
  title: string           // Notification title
  subtitle: string        // Notification description
  time: string           // Time/date string
  isSeen: boolean        // Read/unread status
  color?: string         // Avatar/icon color
  icon?: string          // Icon identifier
  img?: string           // Avatar image URL
  text?: string          // Avatar text (initials)
}
```

## Events Emitted

```typescript
interface Emit {
  (e: 'read', value: number[]): void              // Mark notifications as read
  (e: 'unread', value: number[]): void            // Mark notifications as unread
  (e: 'remove', value: number): void              // Remove single notification
  (e: 'click:notification', value: Notification): void  // Notification clicked
}
```

## Slots Available
None - component has a structured notification layout.

## Global State Dependencies

### External Functions
- `avatarText()`: Utility function to generate avatar text from names

## Internal Methods and Computed Properties

### Computed Properties
- `isAllMarkRead`: Checks if any notifications are unread
- `totalUnseenNotifications`: Count of unread notifications

### Methods
- `markAllReadOrUnread()`: Toggles all notifications read/unread state
- `toggleReadUnread(isSeen: boolean, Id: number)`: Toggle individual notification state

## Component Dependencies

### External Libraries
- `vue3-perfect-scrollbar`: Custom scrollbar for notification list

### Vuetify Components
- `VBadge`: Unread notification indicator
- `VIcon`: Bell icon and action icons
- `VMenu`: Dropdown container
- `VCard`: Notification panel container
- `VList`, `VListItem`: Notification list structure
- `VAvatar`: User/notification avatars
- `VChip`: Unread count display
- `VTooltip`: Action button tooltips
- `VBtn`: Action buttons

### Custom Components
- `IconBtn`: Icon buttons for actions

## Usage Examples

### Basic Usage
```vue
<template>
  <Notifications
    :notifications="userNotifications"
    @read="markAsRead"
    @unread="markAsUnread"
    @remove="removeNotification"
    @click:notification="handleNotificationClick"
  />
</template>

<script setup>
const userNotifications = ref([
  {
    id: 1,
    title: 'New Message',
    subtitle: 'You have received a new message from John',
    time: '2 minutes ago',
    isSeen: false,
    icon: 'bx-message',
    color: 'primary'
  },
  {
    id: 2,
    title: 'System Update',
    subtitle: 'System maintenance completed successfully',
    time: '1 hour ago',
    isSeen: true,
    icon: 'bx-cog',
    color: 'success'
  }
])

const markAsRead = (ids) => {
  // Update notification read status
  console.log('Mark as read:', ids)
}

const markAsUnread = (ids) => {
  // Update notification unread status
  console.log('Mark as unread:', ids)
}

const removeNotification = (id) => {
  // Remove notification
  console.log('Remove notification:', id)
}

const handleNotificationClick = (notification) => {
  // Handle notification click
  console.log('Notification clicked:', notification)
}
</script>
```

### With Custom Badge Props
```vue
<template>
  <Notifications
    :notifications="notifications"
    :badge-props="{ 
      color: 'error',
      variant: 'dot',
      offsetX: 5,
      offsetY: 5 
    }"
  />
</template>
```

### Custom Menu Position
```vue
<template>
  <Notifications
    :notifications="notifications"
    location="top start"
  />
</template>
```

### In App Bar
```vue
<template>
  <VAppBar>
    <VAppBarTitle>My App</VAppBarTitle>
    <VSpacer />
    
    <Notifications
      :notifications="notifications"
      @read="handleRead"
      @unread="handleUnread"
      @remove="handleRemove"
      @click:notification="navigateToNotification"
    />
    
    <!-- Other app bar items -->
  </VAppBar>
</template>
```

### With Real-time Updates
```vue
<template>
  <Notifications
    :notifications="notifications"
    @read="updateNotificationStatus"
    @remove="deleteNotification"
  />
</template>

<script setup>
import { useWebSocket } from '@vueuse/core'

const notifications = ref([])

// WebSocket for real-time notifications
const { data } = useWebSocket('ws://localhost:8080/notifications')

watch(data, (newData) => {
  if (newData) {
    const notification = JSON.parse(newData)
    notifications.value.unshift(notification)
  }
})

const updateNotificationStatus = async (ids) => {
  await api.post('/notifications/mark-read', { ids })
  
  // Update local state
  notifications.value.forEach(notif => {
    if (ids.includes(notif.id)) {
      notif.isSeen = true
    }
  })
}
</script>
```

## Notification Types and Avatars

### Icon Notifications
```javascript
{
  id: 1,
  title: 'System Alert',
  subtitle: 'Server maintenance scheduled',
  time: '5 minutes ago',
  isSeen: false,
  icon: 'bx-server',
  color: 'warning'
}
```

### Image Notifications
```javascript
{
  id: 2,
  title: 'New Follower',
  subtitle: 'John Doe started following you',
  time: '10 minutes ago',
  isSeen: false,
  img: '/avatars/john-doe.jpg'
}
```

### Text Avatar Notifications
```javascript
{
  id: 3,
  title: 'Team Update',
  subtitle: 'Project milestone completed',
  time: '1 hour ago',
  isSeen: true,
  text: 'Design Team',
  color: 'success'
}
```

## Styling Features

### Badge Styling
- Dot variant for unread indicator
- Error color by default
- Customizable through `badgeProps`
- Positioned on bell icon

### Menu Styling
- 380px width for optimal content display
- Perfect scrollbar with max height of 23.75rem
- Clean list styling with dividers
- Hover effects for interactive elements

### Notification Items
- Avatar with multiple display options (icon, image, text)
- Primary content with title and subtitle
- Time display with proper formatting
- Read/unread indicators
- Action buttons (visible on hover)

## Interactive Features

### Bulk Actions
- Mark all as read/unread toggle
- Smart button text based on current state
- Batch processing of notification IDs

### Individual Actions
- Click notification to trigger custom action
- Toggle individual read/unread status
- Remove individual notifications
- Hover effects for better UX

### Visual Feedback
- Unread count chip (shown when unread notifications exist)
- Color-coded read/unread indicators
- Hover states for actions
- Smooth animations and transitions

## Accessibility Features

- Proper ARIA attributes for screen readers
- Keyboard navigation support
- High contrast indicators
- Semantic HTML structure
- Tooltip descriptions for icon buttons

## Performance Considerations

### Scrolling
- Perfect scrollbar for smooth scrolling experience
- Fixed height prevents layout shifts
- Efficient list rendering

### State Management
- Computed properties for reactive filtering
- Efficient array operations
- Minimal re-renders on state changes

## Best Practices

1. **Real-time Updates**: Implement WebSocket or polling for live notifications
2. **Pagination**: Consider pagination for large notification lists
3. **Categories**: Group notifications by type or priority
4. **Persistence**: Save read/unread state to backend
5. **Cleanup**: Implement notification cleanup/archiving
6. **Performance**: Optimize for large notification lists
7. **Customization**: Allow theme-based styling

## Common Integration Patterns

### With Pinia Store
```javascript
// store/notifications.js
export const useNotificationsStore = defineStore('notifications', {
  state: () => ({
    notifications: []
  }),
  
  actions: {
    markAsRead(ids) {
      this.notifications.forEach(notif => {
        if (ids.includes(notif.id)) {
          notif.isSeen = true
        }
      })
    }
  }
})
```

### With API Integration
```javascript
const { $api } = useNuxtApp()

const markAsRead = async (ids) => {
  await $api.notifications.markRead(ids)
  // Update local state
}
```

This component provides a complete notification management system suitable for modern web applications with real-time capabilities and comprehensive user interaction features.