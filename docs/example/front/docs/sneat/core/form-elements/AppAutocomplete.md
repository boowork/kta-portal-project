# AppAutocomplete Component

## Overview
A wrapper component around Vuetify's `VAutocomplete` that provides consistent styling and label handling across the Sneat admin template.

## Props Interface
The component inherits all props from `VAutocomplete` through `useAttrs()`. Key props include:

| Prop | Type | Default | Description |
|------|------|---------|-------------|
| `label` | `string \| undefined` | `undefined` | Label text displayed above the autocomplete |
| `class` | `string \| undefined` | `undefined` | CSS classes for the wrapper div |
| `id` | `string \| undefined` | Auto-generated | Element ID for accessibility |
| `variant` | `string` | `'outlined'` | Vuetify field variant (overridden to 'outlined') |

**Note**: All standard `VAutocomplete` props are supported and passed through.

## Events Emitted
All events from `VAutocomplete` are passed through automatically via `v-bind="$attrs"`.

## Slots Available
All slots from `VAutocomplete` are forwarded using dynamic slot forwarding:

```vue
<template v-for="(_, name) in $slots" #[name]="slotProps">
  <slot :name="name" v-bind="slotProps || {}" />
</template>
```

Common slots include:
- `item` - Customize individual autocomplete items
- `selection` - Customize selected value display
- `prepend-icon` - Icon before the input
- `append-icon` - Icon after the input
- `no-data` - Content when no data is available

## Global State Dependencies
None - this is a pure wrapper component.

## Internal Methods and Computed Properties

### Computed Properties
- `elementId`: Generates unique ID for the autocomplete element
  - Uses provided `id` attribute with prefix `app-autocomplete-`
  - Falls back to auto-generated ID via `useId()`
- `label`: Extracts label from attributes for conditional rendering

## Usage Examples

### Basic Usage
```vue
<AppAutocomplete
  v-model="selectedValue"
  :items="autocompleteItems"
  label="Select an option"
  placeholder="Type to search..."
/>
```

### With Custom Item Slot
```vue
<AppAutocomplete
  v-model="selectedValue"
  :items="users"
  label="Select User"
  item-title="name"
  item-value="id"
>
  <template #item="{ props, item }">
    <VListItem v-bind="props">
      <template #prepend>
        <VAvatar :image="item.raw.avatar" />
      </template>
      <VListItemTitle>{{ item.raw.name }}</VListItemTitle>
      <VListItemSubtitle>{{ item.raw.email }}</VListItemSubtitle>
    </VListItem>
  </template>
</AppAutocomplete>
```

### Multiple Selection
```vue
<AppAutocomplete
  v-model="selectedValues"
  :items="options"
  label="Multiple Selection"
  multiple
  chips
  closable-chips
/>
```

## Component Dependencies
- `VLabel` - Vuetify label component
- `VAutocomplete` - Vuetify autocomplete component
- `useAttrs()` - Vue composition function
- `useId()` - Vue composition function for unique IDs

## Styling
- Uses `.app-autocomplete` class for the wrapper
- Applies `flex-grow-1` for flexible layout
- Label styling: `mb-1 text-body-2` with custom line-height
- Menu content classes: `app-inner-list`, `app-autocomplete__content`, `v-autocomplete__content`

## Accessibility Features
- Proper label association via `for` attribute
- Unique element IDs for screen readers
- Maintains all Vuetify accessibility features

## File Location
`src/@core/components/app-form-elements/AppAutocomplete.vue`