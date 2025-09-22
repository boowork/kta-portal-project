# AppTextField Component

## Overview
A wrapper component around Vuetify's `VTextField` that provides consistent styling and label handling across the Sneat admin template.

## Props Interface
The component inherits all props from `VTextField` through `useAttrs()`. Key props include:

| Prop | Type | Default | Description |
|------|------|---------|-------------|
| `label` | `string \| undefined` | `undefined` | Label text displayed above the text field |
| `class` | `string \| undefined` | `undefined` | CSS classes for the wrapper div |
| `id` | `string \| undefined` | Auto-generated | Element ID for accessibility |
| `variant` | `string` | `'outlined'` | Vuetify field variant (overridden to 'outlined') |
| `type` | `string` | `'text'` | Input type (text, password, email, etc.) |

**Note**: All standard `VTextField` props are supported and passed through.

## Events Emitted
All events from `VTextField` are passed through automatically via `v-bind="$attrs"`.

Common events include:
- `update:modelValue` - Input value changed
- `focus` - Input focused
- `blur` - Input lost focus
- `input` - Input event
- `keydown` - Key pressed
- `click:clear` - Clear button clicked

## Slots Available
All slots from `VTextField` are forwarded using dynamic slot forwarding:

```vue
<template v-for="(_, name) in $slots" #[name]="slotProps">
  <slot :name="name" v-bind="slotProps || {}" />
</template>
```

Common slots include:
- `prepend-icon` - Icon before the input
- `append-icon` - Icon after the input
- `prepend-inner` - Inner prepend content
- `append-inner` - Inner append content
- `details` - Custom details/hint content

## Global State Dependencies
None - this is a pure wrapper component.

## Internal Methods and Computed Properties

### Computed Properties
- `elementId`: Generates unique ID for the text field element
  - Uses provided `id` attribute with prefix `app-text-field-`
  - Falls back to auto-generated ID via `useId()`
- `label`: Extracts label from attributes for conditional rendering

## Usage Examples

### Basic Text Field
```vue
<AppTextField
  v-model="username"
  label="Username"
  placeholder="Enter username"
/>
```

### Password Field
```vue
<AppTextField
  v-model="password"
  label="Password"
  type="password"
  placeholder="Enter password"
  append-icon="mdi-eye-off"
  @click:append="togglePasswordVisibility"
/>
```

### Email Field with Validation
```vue
<AppTextField
  v-model="email"
  label="Email Address"
  type="email"
  placeholder="Enter email"
  :rules="[
    v => !!v || 'Email is required',
    v => /.+@.+\..+/.test(v) || 'Email must be valid'
  ]"
/>
```

### Search Field
```vue
<AppTextField
  v-model="searchQuery"
  label="Search"
  prepend-inner-icon="mdi-magnify"
  placeholder="Search items..."
  clearable
/>
```

## Component Dependencies
- `VLabel` - Vuetify label component
- `VTextField` - Vuetify text field component
- `useAttrs()` - Vue composition function
- `useId()` - Vue composition function for unique IDs

## Styling
- Uses `.app-text-field` class for the wrapper
- Applies `flex-grow-1` for flexible layout
- Label styling: `mb-1 text-body-2 text-wrap` with custom line-height
- Text wrapping enabled for long labels

## Input Types Supported
Through VTextField inheritance, supports all HTML input types:
- `text` - Standard text input
- `password` - Password input
- `email` - Email input with validation
- `number` - Numeric input
- `tel` - Telephone input
- `url` - URL input
- `search` - Search input
- `date` - Date input
- `time` - Time input
- And more...

## Key Features
- Single-line text input
- Various input types
- Real-time validation
- Character counting
- Clear functionality
- Icon integration
- Prefix/suffix support
- Placeholder text
- Disabled/readonly states

## Accessibility Features
- Proper label association via `for` attribute
- Unique element IDs for screen readers
- Keyboard navigation support
- ARIA attributes from Vuetify
- Screen reader compatibility
- Focus management
- Input type announcements

## Validation Support
Inherits comprehensive validation from VTextField:
- Rules-based validation
- Real-time error display
- Custom error messages
- Required field support
- Pattern matching
- Custom validators

## File Location
`src/@core/components/app-form-elements/AppTextField.vue`