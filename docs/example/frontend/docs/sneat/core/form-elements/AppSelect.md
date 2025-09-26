# AppSelect Component

## Overview
A wrapper component around Vuetify's `VSelect` that provides consistent styling, label handling, and dropdown menu configuration across the Sneat admin template. It offers a standardized select interface with enhanced menu styling and accessibility features.

## Props Interface
The component inherits all props from `VSelect` through `useAttrs()`. Key props include:

| Prop | Type | Default | Description |
|------|------|---------|-------------|
| `label` | `string \| undefined` | `undefined` | Label text displayed above the select |
| `class` | `string \| undefined` | `undefined` | CSS classes for the wrapper div |
| `id` | `string \| undefined` | Auto-generated | Element ID for accessibility |
| `variant` | `string` | `'outlined'` | Vuetify field variant (overridden to 'outlined') |
| `items` | `Array` | `[]` | Array of selectable items |
| `multiple` | `boolean` | `false` | Enable multiple selection |
| `chips` | `boolean` | `false` | Display selected items as chips |
| `clearable` | `boolean` | `false` | Show clear button |
| `item-title` | `string \| Function` | `'title'` | Property or function for item display text |
| `item-value` | `string \| Function` | `'value'` | Property or function for item value |

**Note**: All standard `VSelect` props are supported and passed through.

## Events Emitted
All events from `VSelect` are passed through automatically via `v-bind="$attrs"`.

Common events include:
- `update:modelValue` - Selected value(s) changed
- `update:menu` - Dropdown menu visibility changed
- `focus` - Select focused
- `blur` - Select lost focus
- `click:clear` - Clear button clicked
- `update:search` - Search query changed (if searchable)

## Slots Available
All slots from `VSelect` are forwarded using dynamic slot forwarding:

```vue
<template v-for="(_, name) in $slots" #[name]="slotProps">
  <slot :name="name" v-bind="slotProps || {}" />
</template>
```

Common slots include:
- `prepend-icon` - Icon before the select
- `append-icon` - Icon after the select
- `prepend-inner` - Inner prepend content
- `append-inner` - Inner append content
- `selection` - Custom selected item display
- `item` - Custom item template
- `no-data` - Content when no items available
- `chip` - Custom chip template for multiple selection

## Global State Dependencies
None - this is a pure wrapper component.

## Internal Methods and Computed Properties

### Computed Properties
- `elementId`: Generates unique ID for the select element
  - Uses provided `id` attribute with prefix `app-select-`
  - Falls back to auto-generated ID via `useId()`
- `label`: Extracts label from attributes for conditional rendering

### Menu Configuration
- **contentClass**: Custom classes applied to dropdown menu
  - `app-inner-list` - General inner list styling
  - `app-select__content` - Select-specific content styling
  - `v-select__content` - Vuetify select content styling
  - `v-list-select-multiple` - Applied when multiple selection is enabled

## Usage Examples

### Basic Select
```vue
<AppSelect
  v-model="selectedOption"
  label="Choose Option"
  :items="['Option 1', 'Option 2', 'Option 3']"
  placeholder="Select an option"
/>
```

### Object Items with Custom Properties
```vue
<AppSelect
  v-model="selectedUser"
  label="Select User"
  :items="users"
  item-title="name"
  item-value="id"
  placeholder="Choose user"
/>
```

### Multiple Selection with Chips
```vue
<AppSelect
  v-model="selectedCategories"
  label="Categories"
  :items="categories"
  multiple
  chips
  clearable
  placeholder="Select categories"
/>
```

### Custom Item Template
```vue
<AppSelect
  v-model="selectedCountry"
  label="Country"
  :items="countries"
  item-title="name"
  item-value="code"
>
  <template #item="{ props, item }">
    <VListItem v-bind="props">
      <template #prepend>
        <VAvatar :image="item.raw.flag" size="small" />
      </template>
      <VListItemTitle>{{ item.raw.name }}</VListItemTitle>
      <VListItemSubtitle>{{ item.raw.code }}</VListItemSubtitle>
    </VListItem>
  </template>
</AppSelect>
```

### Grouped Items
```vue
<AppSelect
  v-model="selectedProduct"
  label="Product"
  :items="groupedProducts"
>
  <template #item="{ props, item }">
    <VListItem v-if="item.type === 'header'" class="text-primary font-weight-medium">
      {{ item.title }}
    </VListItem>
    <VListItem v-else v-bind="props">
      <VListItemTitle>{{ item.title }}</VListItemTitle>
    </VListItem>
  </template>
</AppSelect>
```

### Select with Search
```vue
<AppSelect
  v-model="searchableSelection"
  label="Search & Select"
  :items="largeItemList"
  item-title="name"
  item-value="id"
  :search="searchQuery"
  @update:search="searchQuery = $event"
  placeholder="Type to search..."
/>
```

### Custom Selection Display
```vue
<AppSelect
  v-model="customSelection"
  label="Custom Display"
  :items="items"
  multiple
  item-title="name"
  item-value="id"
>
  <template #selection="{ item, index }">
    <VChip
      v-if="index < 3"
      size="small"
      closable
      @click:close="removeItem(item.value)"
    >
      <VIcon start>{{ item.raw.icon }}</VIcon>
      {{ item.title }}
    </VChip>
    <span v-if="index === 3" class="text-caption text-disabled">
      (+{{ customSelection.length - 3 }} more)
    </span>
  </template>
</AppSelect>
```

### Select with Validation
```vue
<AppSelect
  v-model="validatedSelection"
  label="Required Selection"
  :items="options"
  :rules="[
    v => !!v || 'Selection is required',
    v => v !== 'invalid' || 'This option is not allowed'
  ]"
  placeholder="Make a selection"
/>
```

### Disabled Options
```vue
<AppSelect
  v-model="selectedWithDisabled"
  label="Select with Disabled Options"
  :items="[
    { title: 'Available', value: 'available' },
    { title: 'Disabled', value: 'disabled', disabled: true },
    { title: 'Another Available', value: 'available2' }
  ]"
/>
```

## Component Dependencies
- `VLabel` - Vuetify label component
- `VSelect` - Vuetify select component
- `useAttrs()` - Vue composition function
- `useId()` - Vue composition function for unique IDs

## Styling
- Uses `.app-select` class for the wrapper
- Applies `flex-grow-1` for flexible layout
- Label styling: `mb-1 text-body-2` with custom line-height
- Menu content classes for consistent dropdown styling
- Special styling for multiple selection mode

## Data Format Options

### Simple String Array
```javascript
const items = ['Apple', 'Banana', 'Orange']
```

### Object Array with Default Props
```javascript
const items = [
  { title: 'Apple', value: 'apple' },
  { title: 'Banana', value: 'banana' },
  { title: 'Orange', value: 'orange' }
]
```

### Object Array with Custom Props
```javascript
const items = [
  { name: 'Apple', id: 1, category: 'fruit' },
  { name: 'Banana', id: 2, category: 'fruit' }
]
// Use with item-title="name" item-value="id"
```

### Grouped Items
```javascript
const items = [
  { type: 'header', title: 'Fruits' },
  { title: 'Apple', value: 'apple', group: 'fruits' },
  { title: 'Banana', value: 'banana', group: 'fruits' },
  { type: 'header', title: 'Vegetables' },
  { title: 'Carrot', value: 'carrot', group: 'vegetables' }
]
```

## Key Features
- **Single/Multiple Selection**: Support for both single and multiple item selection
- **Custom Item Display**: Flexible item templates and display options
- **Search Integration**: Optional search functionality for large lists
- **Chip Display**: Show selected items as removable chips
- **Validation Support**: Built-in form validation integration
- **Accessibility**: Full keyboard navigation and screen reader support
- **Custom Styling**: Consistent menu and item styling

## Accessibility Features
- Proper label association via `for` attribute
- Unique element IDs for screen readers
- Keyboard navigation support (Arrow keys, Enter, Escape)
- ARIA attributes from Vuetify
- Screen reader compatibility
- Focus management
- Selection announcements
- Searchable with keyboard input

## Validation Support
Inherits comprehensive validation from VSelect:
- Rules-based validation
- Real-time error display
- Custom error messages
- Required field support
- Custom validators
- Min/max selection limits for multiple mode

## Performance Considerations
- Use `item-value` and `item-title` functions for computed properties
- Consider virtual scrolling for very large lists (1000+ items)
- Implement search/filtering for better UX with many options
- Use object references for better change detection

## Common Use Cases
- **Status Selection**: Choose from predefined status options
- **Category Selection**: Select categories or tags
- **User Assignment**: Choose users or team members
- **Configuration Options**: Select application settings
- **Filter Controls**: Multi-select filters for data tables
- **Form Controls**: Any dropdown selection in forms

## Advanced Patterns

### Dynamic Items Loading
```vue
<AppSelect
  v-model="dynamicSelection"
  label="Dynamic Items"
  :items="dynamicItems"
  :loading="isLoading"
  @update:search="loadItems"
/>
```

### Conditional Item Display
```vue
<AppSelect
  v-model="conditionalSelection"
  label="Conditional Items"
  :items="items.filter(item => shouldShow(item))"
/>
```

## File Location
`src/@core/components/app-form-elements/AppSelect.vue`