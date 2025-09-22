# ShareProjectDialog.vue

## Overview
A comprehensive project sharing dialog that allows users to add team members, manage permissions, and share project links. Features member search with autocomplete, permission management with dropdown menus, and public sharing options.

## Props Interface

```typescript
interface Props {
  isDialogVisible: boolean
}
```

### Props Details
- **isDialogVisible** (required): `boolean` - Controls dialog visibility

## Events Emitted

```typescript
interface Emit {
  (e: 'update:isDialogVisible', val: boolean): void
}
```

### Events Details
- **update:isDialogVisible**: Emitted when dialog visibility should change (v-model support)

## Slots Available
This component does not expose any custom slots.

## Global State Dependencies
- No global state dependencies (Pinia stores, etc.)
- Uses Vue's built-in reactivity system with static member data

## Internal Methods and Computed Properties

### Methods
- **dialogVisibleUpdate(val: boolean)**: Updates dialog visibility by emitting the update event

### Types and Interfaces
```typescript
type Permission = 'Owner' | 'Can Edit' | 'Can Comment' | 'Can View'

interface Member {
  avatar: string
  name: string
  email: string
  permission: Permission
}
```

### Static Data
- **membersList**: Array of 8 predefined team members with avatars, names, emails, and permissions

## Team Members Data

The component includes 8 predefined team members:

1. **Lester Palmer** (jerrod98@gmail.com) - Can Edit
2. **Mattie Blair** (prudence.boehm@yahoo.com) - Owner
3. **Marvin Wheeler** (rumet@jujpejah.net) - Can Comment
4. **Nannie Ford** (negza@nuv.io) - Can View
5. **Julian Murphy** (lunebame@umdomgu.net) - Can Edit
6. **Sophie Gilbert** (ha@sugit.gov) - Can View
7. **Chris Watkins** (zokap@mak.org) - Can Comment
8. **Adelaide Nichols** (ujinomu@jigo.com) - Can Edit

## Permission Levels

### Available Permissions
1. **Owner**: Full administrative access
2. **Can Edit**: Can modify project content
3. **Can Comment**: Can add comments and feedback
4. **Can View**: Read-only access

## Usage Examples

### Basic Usage
```vue
<template>
  <ShareProjectDialog v-model:is-dialog-visible="showShare" />
</template>

<script setup lang="ts">
const showShare = ref(false)

const openShareDialog = () => {
  showShare.value = true
}
</script>
```

### Project Collaboration
```vue
<template>
  <div>
    <VBtn @click="shareProject" prepend-icon="bx-share">
      Share Project
    </VBtn>
    
    <ShareProjectDialog 
      v-model:is-dialog-visible="shareVisible"
      @member-added="handleMemberAdded"
    />
  </div>
</template>

<script setup lang="ts">
const shareVisible = ref(false)

const shareProject = () => {
  shareVisible.value = true
}

// Note: This dialog doesn't emit member events by default
// but could be extended for real-time member management
const handleMemberAdded = (member: Member) => {
  console.log('New member:', member)
}
</script>
```

### With Permission Management
```vue
<template>
  <ShareProjectDialog 
    v-model:is-dialog-visible="showDialog"
    :project-id="currentProject.id"
    @permission-changed="updateMemberPermission"
  />
</template>

<script setup lang="ts">
const showDialog = ref(false)
const currentProject = ref({ id: 123, name: 'My Project' })

const updateMemberPermission = (memberId: string, permission: Permission) => {
  // Handle permission update in backend
  console.log(`Updated member ${memberId} to ${permission}`)
}
</script>
```

## Component Dependencies

### Vue Components
- **VDialog**: Vuetify dialog wrapper
- **VCard**: Vuetify card container
- **VCardText**: Vuetify card content area
- **VList** / **VListItem**: List components for member display
- **VAvatar**: Avatar components for member photos
- **VBtn**: Button components and dropdown triggers
- **VMenu**: Dropdown menu for permission selection
- **VIcon**: Icon components
- **AppAutocomplete**: Custom autocomplete component for member search
- **DialogCloseBtn**: Custom dialog close button component

### External Dependencies
- **Avatar Images**: 8 avatar images from @images/avatars/
  - avatar-1.png through avatar-8.png

### Vue Composition API
- `defineProps`, `defineEmits`

## UI/UX Features
- **Member Search**: Autocomplete field for adding new members
- **Avatar Display**: Visual member identification with photos
- **Permission Dropdowns**: Interactive permission management for each member
- **Member Count**: Dynamic display of total members (8 Members)
- **Public Sharing**: Project link sharing option
- **Responsive Design**: Auto width on mobile, 900px on desktop

## Form Sections

### 1. Add Members
- **Component**: AppAutocomplete
- **Data Source**: membersList array
- **Display**: Name with avatar in dropdown
- **Placeholder**: "Add project members..."

### 2. Current Members (8 Members)
- **List Display**: All existing project members
- **Avatar**: 30px member photos
- **Information**: Name and email for each member
- **Actions**: Permission dropdown for each member

### 3. Public Sharing
- **Display**: "Public to Sneat - Themeselection"
- **Action**: "Copy Project Link" button
- **Icon**: User icon and link icon

## Permission Management
- **Dropdown Menus**: Each member has permission dropdown
- **Current Selection**: Shows current permission level
- **Options**: Owner, Can Edit, Can Comment, Can View
- **Visual Indicator**: Selected permission highlighted
- **Mobile Responsive**: Icon-only on extra small screens

## Styling Features
- **Card List**: Custom gap spacing (--v-card-list-gap: 1rem)
- **Responsive Layout**: Flexible layout for different screen sizes
- **Avatar Sizing**: Consistent 30px avatars in autocomplete, standard size in list
- **Button Groups**: Proper spacing and alignment for action buttons

## Custom Styles
```scss
.share-project-dialog {
  .card-list {
    --v-card-list-gap: 1rem;
  }
}
```

## Accessibility
- Proper heading hierarchy (h4, h5, h6)
- Avatar alt text through image components
- Semantic list structure for members
- Button labels and dropdown menus
- Icon alternatives for screen readers

## Integration Points
- **User Management**: Connect to user directory/database
- **Permission System**: Integrate with project permission backend
- **Real-time Updates**: Can be extended for live collaboration
- **Notification System**: Send invitations to new members
- **Audit Trail**: Track permission changes and sharing activities

## Data Structure Ready
- **Member Interface**: Well-defined member object structure
- **Permission Types**: Strongly typed permission levels
- **Search Integration**: Autocomplete ready for API integration
- **State Management**: Structure supports real-time member updates

## Customization Options
- **Member Data**: Replace static data with API calls
- **Permission Levels**: Extend or modify available permissions
- **Avatar Sources**: Connect to user profile systems
- **Sharing Options**: Add more sharing methods (Slack, email, etc.)
- **Member Limits**: Add member count restrictions

## Security Considerations
- **Permission Validation**: Client-side permission display
- **Link Security**: Project link copying for secure sharing
- **Member Verification**: Structure supports email verification
- **Access Control**: Permission levels for granular access

## Performance Considerations
- **Static Data**: Current implementation uses static member data
- **Efficient Rendering**: Vuetify components optimized for lists
- **Lazy Loading**: Dialog renders only when visible
- **Minimal Dependencies**: Uses standard Vuetify components

## Business Logic Ready
- **Member Search**: Autocomplete structure for user lookup
- **Permission Updates**: Dropdown change handlers ready
- **Link Generation**: Project link copying functionality
- **Member Invitations**: Email-based invitation structure
- **Activity Tracking**: Framework for collaboration analytics