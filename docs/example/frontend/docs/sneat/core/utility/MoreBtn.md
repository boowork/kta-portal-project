# MoreBtn.vue

## Overview
A versatile "more actions" button component with an optional dropdown menu. Features a three-dot vertical icon and supports dynamic menu items with customizable properties.

## Props Interface

```typescript
interface Props {
  menuList?: unknown[]    // Optional array of menu items
  itemProps?: boolean     // Enable item properties for menu items
  iconSize?: string       // Custom icon size
  class?: string          // CSS classes (default: 'text-disabled')
}
```

## Events Emitted
Inherits all events from the underlying `IconBtn` component, including click events.

## Slots Available
None - component uses props for menu configuration.

## Global State Dependencies
None - component is fully self-contained.

## Internal Methods and Computed Properties

### Default Values
- `class`: Defaults to `'text-disabled'` for subtle appearance

## Component Dependencies

### Custom Components
- `IconBtn`: Core icon button functionality

### Vuetify Components
- `VIcon`: Three-dot vertical icon (bx-dots-vertical-rounded)
- `VMenu`: Dropdown menu (conditional rendering)
- `VList`: Menu items container

## Usage Examples

### Basic Icon Only
```vue
<template>
  <MoreBtn @click="handleMoreClick" />
</template>

<script setup>
const handleMoreClick = () => {
  console.log('More button clicked')
}
</script>
```

### With Menu Items
```vue
<template>
  <MoreBtn :menu-list="actionItems" />
</template>

<script setup>
const actionItems = [
  {
    title: 'Edit',
    value: 'edit',
    props: {
      prependIcon: 'bx-edit',
      onClick: () => handleEdit()
    }
  },
  {
    title: 'Delete',
    value: 'delete',
    props: {
      prependIcon: 'bx-trash',
      color: 'error',
      onClick: () => handleDelete()
    }
  },
  {
    title: 'Share',
    value: 'share',
    props: {
      prependIcon: 'bx-share',
      onClick: () => handleShare()
    }
  }
]

const handleEdit = () => console.log('Edit action')
const handleDelete = () => console.log('Delete action')
const handleShare = () => console.log('Share action')
</script>
```

### In Data Table
```vue
<template>
  <VDataTable :items="tableItems">
    <template #item.actions="{ item }">
      <MoreBtn :menu-list="getItemActions(item)" />
    </template>
  </VDataTable>
</template>

<script setup>
const getItemActions = (item) => [
  {
    title: 'View Details',
    value: 'view',
    props: {
      prependIcon: 'bx-show',
      onClick: () => viewItem(item.id)
    }
  },
  {
    title: 'Edit',
    value: 'edit',
    props: {
      prependIcon: 'bx-edit',
      onClick: () => editItem(item.id)
    }
  },
  {
    title: 'Delete',
    value: 'delete',
    props: {
      prependIcon: 'bx-trash',
      color: 'error',
      onClick: () => deleteItem(item.id)
    }
  }
]
</script>
```

### Custom Styling
```vue
<template>
  <MoreBtn 
    :menu-list="menuItems"
    class="text-primary"
    icon-size="20"
  />
</template>
```

### In Card Headers
```vue
<template>
  <VCard>
    <VCardTitle class="d-flex align-center">
      <span>Card Title</span>
      <VSpacer />
      <MoreBtn :menu-list="cardActions" />
    </VCardTitle>
    <VCardText>
      Card content here...
    </VCardText>
  </VCard>
</template>

<script setup>
const cardActions = [
  {
    title: 'Refresh',
    value: 'refresh',
    props: {
      prependIcon: 'bx-refresh',
      onClick: () => refreshCard()
    }
  },
  {
    title: 'Settings',
    value: 'settings',
    props: {
      prependIcon: 'bx-cog',
      onClick: () => openSettings()
    }
  },
  {
    title: 'Remove',
    value: 'remove',
    props: {
      prependIcon: 'bx-x',
      color: 'error',
      onClick: () => removeCard()
    }
  }
]
</script>
```

### Dynamic Menu Items
```vue
<template>
  <MoreBtn :menu-list="dynamicMenuItems" />
</template>

<script setup>
const user = ref({ role: 'admin' })

const dynamicMenuItems = computed(() => {
  const baseItems = [
    {
      title: 'View',
      value: 'view',
      props: { onClick: () => view() }
    }
  ]

  if (user.value.role === 'admin') {
    baseItems.push(
      {
        title: 'Edit',
        value: 'edit',
        props: { onClick: () => edit() }
      },
      {
        title: 'Delete',
        value: 'delete',
        props: { 
          color: 'error',
          onClick: () => deleteItem() 
        }
      }
    )
  }

  return baseItems
})
</script>
```

## Menu Item Structure

Menu items should follow this structure:

```typescript
interface MenuItem {
  title: string           // Display text
  value: string | number  // Unique identifier
  props?: {              // VListItem props
    prependIcon?: string    // Icon before text
    appendIcon?: string     // Icon after text
    color?: string          // Item color
    disabled?: boolean      // Disable item
    onClick?: () => void    // Click handler
    // ... other VListItem props
  }
}
```

## Conditional Menu Rendering

The menu is only rendered when `menuList` prop is provided:

```vue
<VMenu v-if="props.menuList">
  <!-- Menu content -->
</VMenu>
```

This allows the component to function as:
1. **Simple Icon Button**: When no `menuList` is provided
2. **Dropdown Menu Button**: When `menuList` contains items

## Styling Features

### Icon Styling
- Uses `bx-dots-vertical-rounded` icon
- Customizable size via `iconSize` prop
- Default class: `text-disabled` for subtle appearance

### Menu Properties
- Activates on parent click (`activator="parent"`)
- Uses VList with items binding
- Supports `itemProps` for enhanced item functionality

## Best Practices

1. **Contextual Actions**: Provide relevant actions for the context
2. **Action Grouping**: Group related actions together
3. **Destructive Actions**: Use error color for delete/remove actions
4. **Icons**: Include meaningful icons for better UX
5. **Permissions**: Show only actions the user can perform
6. **Feedback**: Provide feedback after action execution

## Common Patterns

### Confirmation for Destructive Actions
```vue
<script setup>
const getMenuItems = (item) => [
  // ... other actions
  {
    title: 'Delete',
    value: 'delete',
    props: {
      color: 'error',
      onClick: async () => {
        const confirmed = await confirmDelete()
        if (confirmed) {
          await deleteItem(item.id)
        }
      }
    }
  }
]
</script>
```

### Loading States
```vue
<script setup>
const isLoading = ref(false)

const menuItems = computed(() => [
  {
    title: 'Sync',
    value: 'sync',
    props: {
      disabled: isLoading.value,
      onClick: async () => {
        isLoading.value = true
        try {
          await syncData()
        } finally {
          isLoading.value = false
        }
      }
    }
  }
])
</script>
```

This component provides a flexible way to add contextual actions to any interface element while maintaining design consistency across the application.