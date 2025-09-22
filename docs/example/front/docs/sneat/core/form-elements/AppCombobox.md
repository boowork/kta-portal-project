# AppCombobox Component

## Overview
A wrapper component around Vuetify's `VCombobox` that provides consistent styling, label handling, and menu content styling across the Sneat admin template. The combobox allows users to select from a list of options or enter custom values.

## Props Interface
The component inherits all props from `VCombobox` through `useAttrs()`. Key props include:

| Prop | Type | Default | Description |
|------|------|---------|-------------|
| `label` | `string \| undefined` | `undefined` | Label text displayed above the combobox |
| `class` | `string \| undefined` | `undefined` | CSS classes for the wrapper div |
| `id` | `string \| undefined` | Auto-generated | Element ID for accessibility |
| `variant` | `string` | `'outlined'` | Vuetify field variant (overridden to 'outlined') |
| `items` | `Array` | `[]` | Array of selectable items |
| `multiple` | `boolean` | `false` | Enable multiple selection |
| `chips` | `boolean` | `false` | Display selected items as chips |
| `clearable` | `boolean` | `false` | Show clear button |
| `searchable` | `boolean` | `true` | Enable search filtering |

**Note**: All standard `VCombobox` props are supported and passed through.

## Events Emitted
All events from `VCombobox` are passed through automatically via `v-bind="$attrs"`.

Common events include:
- `update:modelValue` - Selected value(s) changed
- `update:search` - Search input changed
- `focus` - Input focused
- `blur` - Input lost focus
- `click:clear` - Clear button clicked
- `update:menu` - Menu visibility changed

## Slots Available
All slots from `VCombobox` are forwarded using dynamic slot forwarding:

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
- `selection` - Custom selected item display
- `item` - Custom item template
- `no-data` - Content when no items match
- `chip` - Custom chip template for multiple selection

## Global State Dependencies
None - this is a pure wrapper component.

## Internal Methods and Computed Properties

### Computed Properties
- `elementId`: Generates unique ID for the combobox element
  - Uses provided `id` attribute with prefix `app-combobox-`
  - Falls back to auto-generated ID via `useId()`
- `label`: Extracts label from attributes for conditional rendering

### Menu Configuration
- **contentClass**: Custom classes applied to dropdown menu
  - `app-inner-list` - General inner list styling
  - `app-combobox__content` - Combobox-specific content styling  
  - `v-combobox__content` - Vuetify combobox content styling
  - `v-list-select-multiple` - Applied when multiple selection is enabled

## Usage Examples

### Basic Combobox
```vue
<AppCombobox
  v-model="selectedFruit"
  label="Select Fruit"
  :items="['Apple', 'Banana', 'Orange', 'Grape']"
  placeholder="Choose or type a fruit"
/>
```

### Multiple Selection with Chips
```vue
<AppCombobox
  v-model="selectedTags"
  label="Tags"
  :items="availableTags"
  multiple
  chips
  clearable
  placeholder="Select or create tags"
/>
```

### Object Items with Custom Display
```vue
<AppCombobox
  v-model="selectedUser"
  label="Assign User"
  :items="users"
  item-title="name"
  item-value="id"
  placeholder="Select user"
>
  <template #item="{ props, item }">
    <VListItem v-bind="props">
      <template #prepend>
        <VAvatar :image="item.raw.avatar" size="small" />
      </template>
      <VListItemTitle>{{ item.raw.name }}</VListItemTitle>
      <VListItemSubtitle>{{ item.raw.email }}</VListItemSubtitle>
    </VListItem>
  </template>
</AppCombobox>
```

### Searchable with Custom No Data Message
```vue
<AppCombobox
  v-model="selectedCountry"
  label="Country"
  :items="countries"
  placeholder="Search country"
  clearable
>
  <template #no-data>
    <VListItem>
      <VListItemTitle>
        No countries found. Try a different search term.
      </VListItemTitle>
    </VListItem>
  </template>
</AppCombobox>
```

### Custom Selection Display
```vue
<AppCombobox
  v-model="selectedProducts"
  label="Products"
  :items="products"
  multiple
  item-title="name"
  item-value="id"
>
  <template #selection="{ item, index }">
    <VChip
      v-if="index < 2"
      size="small"
      label
      closable
      @click:close="removeProduct(item.value)"
    >
      {{ item.title }}
    </VChip>
    <span v-if="index === 2" class="text-caption text-disabled">
      (+{{ selectedProducts.length - 2 }} others)
    </span>
  </template>
</AppCombobox>
```

## Component Dependencies
- `VLabel` - Vuetify label component
- `VCombobox` - Vuetify combobox component
- `useAttrs()` - Vue composition function
- `useId()` - Vue composition function for unique IDs

## Styling
- Uses `.app-combobox` class for the wrapper
- Applies `flex-grow-1` for flexible layout
- Label styling: `mb-1 text-body-2` with proper spacing
- Menu content classes for consistent dropdown styling
- Special styling for multiple selection mode

## Key Features
- **Flexible Input**: Accept both predefined options and custom values
- **Search Functionality**: Filter options as you type
- **Multiple Selection**: Select multiple items with chip display
- **Custom Items**: Support for complex object items
- **Validation**: Built-in validation support
- **Clearable**: Optional clear button functionality
- **Custom Templates**: Extensive slot system for customization

## Accessibility Features
- Proper label association via `for` attribute
- Unique element IDs for screen readers
- Keyboard navigation support (Arrow keys, Enter, Escape)
- ARIA attributes from Vuetify
- Screen reader compatibility
- Focus management
- Announced selection changes
- Searchable with keyboard input

## Validation Support
Inherits comprehensive validation from VCombobox:
- Rules-based validation
- Real-time error display
- Custom error messages
- Required field support
- Custom validators
- Min/max selection limits for multiple mode

## Use Cases
- **Tag Selection**: Adding tags to posts or items
- **User Assignment**: Selecting users from a list or adding new ones
- **Category Selection**: Choosing from predefined categories or creating new ones
- **Search with Suggestions**: Autocomplete-style search with custom input
- **Multi-select Filters**: Filtering data with multiple criteria
- **Dynamic Options**: Lists that can grow with user input

## File Location
`src/@core/components/app-form-elements/AppCombobox.vue`