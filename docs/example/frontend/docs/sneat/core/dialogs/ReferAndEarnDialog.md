# ReferAndEarnDialog.vue

## Overview
A comprehensive referral program dialog that guides users through the refer-and-earn process. Features a 3-step visual guide, email invitation form, and social media sharing options for referral links. Integrates with the application's branding through theme configuration.

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
- **themeConfig**: Global theme configuration for app title display

## Internal Methods and Computed Properties

### Methods
- **dialogVisibleUpdate(val: boolean)**: Updates dialog visibility by emitting the update event

### Static Data
- **referAndEarnSteps**: Array defining the 3-step referral process with icons and descriptions

## Referral Process Steps

```typescript
const referAndEarnSteps = [
  {
    icon: 'bx-message-square-dots',
    title: 'Send Invitation 👍🏻',
    subtitle: 'Send your referral link to your friend',
  },
  {
    icon: 'bx-detail',
    title: 'Registration 😎',
    subtitle: 'Let them register to our services',
  },
  {
    icon: 'bx-gift',
    title: 'Free Trial  🎉',
    subtitle: 'Your friend will get 30 days free trial',
  },
]
```

## Usage Examples

### Basic Usage
```vue
<template>
  <ReferAndEarnDialog v-model:is-dialog-visible="showReferral" />
</template>

<script setup lang="ts">
const showReferral = ref(false)

const openReferralProgram = () => {
  showReferral.value = true
}
</script>
```

### Triggered from Account Menu
```vue
<template>
  <div>
    <VMenu>
      <template #activator="{ props }">
        <VBtn v-bind="props">Account</VBtn>
      </template>
      <VList>
        <VListItem @click="showReferAndEarn">
          Refer & Earn
        </VListItem>
      </VList>
    </VMenu>
    
    <ReferAndEarnDialog v-model:is-dialog-visible="referralVisible" />
  </div>
</template>

<script setup lang="ts">
const referralVisible = ref(false)

const showReferAndEarn = () => {
  referralVisible.value = true
}
</script>
```

### With Form Handling
```vue
<template>
  <ReferAndEarnDialog 
    v-model:is-dialog-visible="showReferral"
    @referral-sent="handleReferralSent"
  />
</template>

<script setup lang="ts">
const showReferral = ref(false)

// Note: This dialog doesn't emit referral events by default
// but could be extended to do so
const handleReferralSent = (email: string) => {
  console.log('Referral sent to:', email)
  // Handle referral tracking
}
</script>
```

## Component Dependencies

### Vue Components
- **VDialog**: Vuetify dialog wrapper
- **VCard**: Vuetify card container
- **VCardText**: Vuetify card content area
- **VRow** / **VCol**: Vuetify grid system
- **VAvatar**: Avatar components for step icons
- **VIcon**: Icon components
- **VDivider**: Section divider
- **VForm**: Form wrapper components
- **VBtn**: Button components for social sharing
- **AppTextField**: Custom text field component
- **DialogCloseBtn**: Custom dialog close button component

### External Dependencies
- **@themeConfig**: Application theme configuration

### Vue Composition API
- `defineProps`, `defineEmits`

## UI/UX Features
- **Visual Process Guide**: 3-step visual explanation with icons and emojis
- **Dual Input Methods**: Email invitation and link sharing
- **Social Media Integration**: Facebook, Twitter, LinkedIn sharing buttons
- **Responsive Design**: Auto width on mobile, 800px on desktop
- **Brand Integration**: Uses app title from theme configuration
- **Clear Visual Hierarchy**: Sections separated with dividers

## Form Sections

### 1. Process Overview
- **Step 1**: Send Invitation (message icon)
- **Step 2**: Friend Registration (detail icon)  
- **Step 3**: Free Trial Reward (gift icon)
- Visual avatars with tonal primary color scheme

### 2. Email Invitation
- **Field**: Friend's email address
- **Placeholder**: "johnDoe@gmail.com"
- **Label**: Friendly invitation message
- **Action**: Send button

### 3. Link Sharing
- **Field**: Referral link display
- **Placeholder**: "http://themeselection.link"
- **Label**: Social media sharing instruction
- **Actions**: Social media buttons

## Social Media Buttons

### Facebook
- **Color**: #3B5998 (Facebook blue)
- **Icon**: bx-bxl-facebook
- **Size**: 38px

### Twitter
- **Color**: #55ACEE (Twitter blue)
- **Icon**: bx-bxl-twitter
- **Size**: 38px

### LinkedIn
- **Color**: #007BB6 (LinkedIn blue)
- **Icon**: bx-bxl-linkedin
- **Size**: 38px

## Content Features
- **Dynamic App Name**: Uses `themeConfig.app.title` for personalization
- **Emoji Enhancement**: Friendly emojis in step titles
- **Reward Messaging**: Clear 30-day free trial benefit communication
- **Instructional Text**: Step-by-step guidance for users

## Styling Notes
- Uses Vuetify's spacing classes and grid system
- Centered text alignment for process steps
- Custom gap spacing for form elements (gap-4)
- Rounded social media buttons with brand colors
- Responsive column layout (sm="4" for steps)

## Custom Styles
```scss
.refer-link-input {
  .v-field--appended {
    padding-inline-end: 0;
  }
}
```

## Accessibility
- Proper heading hierarchy (h4, h5)
- Icon alternatives with descriptive text
- Form labels and placeholders
- Button color contrast considerations
- Semantic structure with clear sections

## Integration Points
- **Referral Tracking**: Can be extended to track referral submissions
- **Email Service**: Connect email form to sending service
- **Social APIs**: Link social buttons to sharing APIs
- **Analytics**: Track referral program engagement
- **Reward System**: Connect to user reward/credit system

## Customization Options
- **Step Content**: Easy to modify process steps
- **Reward Amount**: Update free trial duration messaging
- **Social Platforms**: Add/remove social sharing options
- **Form Fields**: Extend with additional referral options
- **Branding**: Automatically uses theme configuration

## Business Logic Ready
- Form structure supports validation implementation
- Social buttons ready for API integration
- Email sending can be connected to backend
- Referral tracking can be added
- Reward system integration points available

## Performance Considerations
- **Minimal Dependencies**: Only uses necessary components
- **Static Data**: Process steps are compile-time constants
- **Lazy Loading**: Dialog only renders when visible
- **Efficient Layout**: Uses Vuetify's optimized grid system