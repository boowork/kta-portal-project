# AppStepper.vue

## Overview
A comprehensive stepper component for multi-step processes with support for both horizontal and vertical layouts, validation states, and custom icons. Features automatic step navigation, completion tracking, and flexible styling options.

## Props Interface

```typescript
interface Item {
  title: string                    // Step title
  icon?: string | object          // Optional icon (string or Vue component)
  size?: string                   // Optional custom icon size
  subtitle?: string               // Optional step description
}

type Direction = 'vertical' | 'horizontal'

interface Props {
  items: Item[]                   // Array of step definitions
  currentStep?: number            // Active step index (0-based)
  direction?: Direction           // Layout orientation
  iconSize?: string | number      // Default icon size
  isActiveStepValid?: boolean     // Validation state for current step
  align?: 'start' | 'center' | 'end' | 'default'  // Alignment of steps
}
```

## Events Emitted

```typescript
interface Emit {
  (e: 'update:currentStep', value: number): void  // Current step change
}
```

## Slots Available
None - component is fully self-contained with customizable props.

## Global State Dependencies
None - component manages its own state through reactive properties.

## Internal Methods and Computed Properties

### Reactive Properties
- `currentStep`: Internal step tracking that syncs with props

### Computed Properties
- `activeOrCompletedStepsClasses(index: number)`: Returns CSS classes for step states
  - 'stepper-steps-completed': For completed steps
  - 'stepper-steps-active': For current active step
- `isHorizontalAndNotLastStep(index: number)`: Determines if chevron should be shown
- `isValidationEnabled`: Checks if validation is enabled for the stepper

### Watchers
- `watchEffect()`: Syncs internal step state with props and emits updates

## Step States

### Visual States
1. **Completed**: Steps before current step (shows step number in primary color)
2. **Active**: Current step (highlighted with primary color and optional validation)
3. **Pending**: Steps after current step (default styling)
4. **Invalid**: Active step with validation error (error color styling)

### Validation System
- When `isActiveStepValid` is defined, validation mode is enabled
- Invalid steps show error styling and prevent automatic navigation
- Valid steps show normal active styling

## Component Dependencies

### Vuetify Components
- `VSlideGroup`, `VSlideGroupItem`: Horizontal scrolling container
- `VAvatar`: Step number/icon containers
- `VIcon`: Icons and chevron indicators
- `VBtn`: Not directly used but referenced in click handlers

## Usage Examples

### Basic Horizontal Stepper
```vue
<template>
  <AppStepper
    v-model:current-step="currentStep"
    :items="steps"
    direction="horizontal"
  />
</template>

<script setup>
const currentStep = ref(0)
const steps = [
  { title: 'Account Details', subtitle: 'Setup Account' },
  { title: 'Personal Info', subtitle: 'Add Info' },
  { title: 'Review', subtitle: 'Confirm Details' }
]
</script>
```

### Stepper with Icons
```vue
<template>
  <AppStepper
    v-model:current-step="currentStep"
    :items="iconSteps"
    direction="horizontal"
    icon-size="40"
  />
</template>

<script setup>
const iconSteps = [
  { 
    title: 'Account Details', 
    subtitle: 'Setup Account',
    icon: 'bx-user'
  },
  { 
    title: 'Personal Info', 
    subtitle: 'Add Info',
    icon: 'bx-info-circle'
  },
  { 
    title: 'Review', 
    subtitle: 'Confirm Details',
    icon: 'bx-check'
  }
]
</script>
```

### Vertical Stepper with Validation
```vue
<template>
  <AppStepper
    v-model:current-step="currentStep"
    :items="steps"
    :is-active-step-valid="isCurrentStepValid"
    direction="vertical"
  />
</template>

<script setup>
const currentStep = ref(0)
const isCurrentStepValid = ref(true)

const validateStep = () => {
  // Your validation logic
  isCurrentStepValid.value = validateCurrentStep()
}
</script>
```

### Custom Component Icons
```vue
<template>
  <AppStepper
    v-model:current-step="currentStep"
    :items="customIconSteps"
  />
</template>

<script setup>
import CustomIcon from './CustomIcon.vue'

const customIconSteps = [
  { 
    title: 'Custom Step',
    icon: CustomIcon  // Vue component as icon
  }
]
</script>
```

## Styling and Themes

### CSS Classes
- `.app-stepper`: Main container
- `.app-stepper-icons`: Applied when steps have icons
- `.stepper-icon-step-bg`: Background variant for icon steps
- `.stepper-steps-active`: Active step styling
- `.stepper-steps-completed`: Completed step styling
- `.stepper-steps-invalid`: Invalid step styling

### Alignment Options
- `align="start"`: Left-aligned steps
- `align="center"`: Center-aligned steps  
- `align="end"`: Right-aligned steps
- `align="default"`: Natural flex layout

## Navigation Behavior

### Automatic Navigation
- When validation is disabled, clicking any step navigates to it
- When validation is enabled, automatic navigation is prevented

### Step Progression
- Steps automatically update completion state
- Chevron indicators show between horizontal steps
- Visual feedback for current, completed, and pending states

## Accessibility Features

- Proper semantic structure with step indicators
- Keyboard navigation support through VSlideGroup
- Visual state indicators for screen readers
- Proper color contrast for different step states

## Responsive Design

- Horizontal layout adapts to available space
- VSlideGroup provides scrolling for overflow
- Vertical layout stacks naturally on small screens
- Flexible icon and text sizing options