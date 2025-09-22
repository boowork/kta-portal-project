# AppTextarea Component

## Overview
A wrapper component around Vuetify's `VTextarea` that provides consistent styling and label handling across the Sneat admin template. This component offers multi-line text input functionality with automatic height adjustment and extensive customization options.

## Props Interface
The component inherits all props from `VTextarea` through `useAttrs()`. Key props include:

| Prop | Type | Default | Description |
|------|------|---------|-------------|
| `label` | `string \| undefined` | `undefined` | Label text displayed above the textarea |
| `class` | `string \| undefined` | `undefined` | CSS classes for the wrapper div |
| `id` | `string \| undefined` | Auto-generated | Element ID for accessibility |
| `variant` | `string` | `'outlined'` | Vuetify field variant (overridden to 'outlined') |
| `rows` | `string \| number` | `5` | Initial number of visible rows |
| `auto-grow` | `boolean` | `false` | Automatically grow height with content |
| `no-resize` | `boolean` | `false` | Disable manual resizing |
| `counter` | `boolean \| number \| string` | `false` | Show character counter |
| `clearable` | `boolean` | `false` | Show clear button |

**Note**: All standard `VTextarea` props are supported and passed through.

## Events Emitted
All events from `VTextarea` are passed through automatically via `v-bind="$attrs"`.

Common events include:
- `update:modelValue` - Textarea content changed
- `focus` - Textarea focused
- `blur` - Textarea lost focus
- `input` - Input event on text change
- `keydown` - Key pressed
- `click:clear` - Clear button clicked

## Slots Available
All slots from `VTextarea` are forwarded using dynamic slot forwarding:

```vue
<template v-for="(_, name) in $slots" #[name]="slotProps">
  <slot :name="name" v-bind="slotProps || {}" />
</template>
```

Common slots include:
- `prepend-icon` - Icon before the textarea
- `append-icon` - Icon after the textarea
- `prepend-inner` - Inner prepend content
- `append-inner` - Inner append content
- `details` - Custom details/hint content
- `counter` - Custom counter display

## Global State Dependencies
None - this is a pure wrapper component.

## Internal Methods and Computed Properties

### Computed Properties
- `elementId`: Generates unique ID for the textarea element
  - Uses provided `id` attribute with prefix `app-textarea-`
  - Falls back to auto-generated ID via `useId()`
- `label`: Extracts label from attributes for conditional rendering

## Usage Examples

### Basic Textarea
```vue
<AppTextarea
  v-model="description"
  label="Description"
  placeholder="Enter description..."
  rows="4"
/>
```

### Auto-Growing Textarea
```vue
<AppTextarea
  v-model="expandingText"
  label="Comments"
  placeholder="Type your comments..."
  auto-grow
  rows="2"
/>
```

### Textarea with Character Counter
```vue
<AppTextarea
  v-model="limitedText"
  label="Limited Text"
  placeholder="Max 500 characters"
  counter="500"
  :rules="[v => v.length <= 500 || 'Maximum 500 characters allowed']"
/>
```

### Read-only Textarea
```vue
<AppTextarea
  v-model="readOnlyContent"
  label="Terms & Conditions"
  readonly
  no-resize
  rows="10"
  variant="outlined"
/>
```

### Textarea with Custom Icons
```vue
<AppTextarea
  v-model="messageText"
  label="Message"
  placeholder="Write your message..."
  prepend-inner-icon="mdi-message-text"
  append-inner-icon="mdi-send"
  clearable
  @click:append-inner="sendMessage"
/>
```

### Textarea for Code Input
```vue
<AppTextarea
  v-model="codeSnippet"
  label="Code Snippet"
  placeholder="Paste your code here..."
  rows="8"
  no-resize
  class="font-monospace"
  variant="outlined"
>
  <template #prepend-inner>
    <VIcon color="primary">mdi-code-tags</VIcon>
  </template>
</AppTextarea>
```

### Form Textarea with Validation
```vue
<AppTextarea
  v-model="validatedContent"
  label="Required Content"
  placeholder="This field is required..."
  :rules="[
    v => !!v || 'Content is required',
    v => v.length >= 10 || 'Minimum 10 characters required',
    v => v.length <= 1000 || 'Maximum 1000 characters allowed'
  ]"
  counter="1000"
  auto-grow
/>
```

### Textarea with Custom Styling
```vue
<AppTextarea
  v-model="styledText"
  label="Styled Textarea"
  placeholder="Custom styled textarea..."
  rows="6"
  class="custom-textarea"
  :style="{
    '--v-field-input-padding-top': '16px',
    '--v-field-input-padding-bottom': '16px'
  }"
/>
```

### Multi-language Support
```vue
<AppTextarea
  v-model="localizedText"
  :label="$t('form.description')"
  :placeholder="$t('form.descriptionPlaceholder')"
  :dir="$i18n.locale === 'ar' ? 'rtl' : 'ltr'"
  auto-grow
/>
```

## Component Dependencies
- `VLabel` - Vuetify label component
- `VTextarea` - Vuetify textarea component
- `useAttrs()` - Vue composition function
- `useId()` - Vue composition function for unique IDs

## Styling
- Uses `.app-textarea` class for the wrapper
- Applies `flex-grow-1` for flexible layout
- Label styling: `mb-1 text-body-2` with proper spacing
- Consistent with other form element styling

## Key Features
- **Multi-line Input**: Support for multiple lines of text
- **Auto-grow**: Automatically expand height based on content
- **Resizable**: Manual resize capabilities (can be disabled)
- **Character Counter**: Track and limit character count
- **Validation**: Built-in validation support
- **Clear Button**: Optional clear functionality
- **Custom Icons**: Prepend and append icon support
- **Flexible Height**: Configurable initial rows

## Height Management

### Fixed Height
```vue
<AppTextarea
  v-model="fixedHeight"
  rows="5"
  no-resize
/>
```

### Auto-growing with Limits
```vue
<AppTextarea
  v-model="limitedGrowth"
  auto-grow
  :style="{
    'max-height': '200px',
    'overflow-y': 'auto'
  }"
/>
```

### Minimum Height
```vue
<AppTextarea
  v-model="minHeight"
  auto-grow
  rows="3"
  :style="{ 'min-height': '120px' }"
/>
```

## Accessibility Features
- Proper label association via `for` attribute
- Unique element IDs for screen readers
- Keyboard navigation support (Tab, Shift+Tab)
- ARIA attributes from Vuetify
- Screen reader compatibility
- Focus management
- Content change announcements
- Character count announcements

## Validation Support
Inherits comprehensive validation from VTextarea:
- Rules-based validation
- Real-time error display
- Custom error messages
- Required field support
- Length validation (min/max)
- Pattern matching
- Custom validators
- Character counter integration

## Responsive Behavior
- Adapts to container width
- Mobile-friendly touch interactions
- Proper virtual keyboard support
- Responsive font sizing
- Touch-optimized controls

## Performance Considerations
- Use `auto-grow` judiciously for very long content
- Consider virtual scrolling for extremely large text
- Debounce validation for better performance
- Optimize re-renders with proper key usage

## Common Use Cases
- **Form Comments**: User feedback and comments
- **Content Creation**: Blog posts, articles, descriptions
- **Code Input**: Code snippets and configurations
- **Message Composition**: Chat messages, emails
- **Settings**: Multi-line configuration values
- **Notes**: User notes and documentation

## Best Practices
- Use appropriate `rows` for expected content length
- Enable `auto-grow` for variable-length content
- Set character limits for database constraints
- Provide clear placeholder text
- Use validation for required fields
- Consider accessibility for all users

## Integration with Forms
Works seamlessly with Vue form libraries:

```vue
<!-- With VeeValidate -->
<Field name="description" v-slot="{ field, errors }">
  <AppTextarea
    v-bind="field"
    label="Description"
    :error-messages="errors"
  />
</Field>

<!-- With Vuetify Forms -->
<VForm ref="form">
  <AppTextarea
    v-model="content"
    :rules="contentRules"
    required
  />
</VForm>
```

## File Location
`src/@core/components/app-form-elements/AppTextarea.vue`