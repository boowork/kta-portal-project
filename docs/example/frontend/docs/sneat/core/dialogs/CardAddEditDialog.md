# CardAddEditDialog.vue

## Overview
A comprehensive dialog component for adding or editing credit card information. Features dynamic titles based on edit/add mode, complete card form fields including number, name, expiry, CVV, and an option to save for future billing.

## Props Interface

```typescript
interface Details {
  number: string | number
  name: string
  expiry: string
  cvv: string
  isPrimary: boolean
  type: string
}

interface Props {
  cardDetails?: Details
  isDialogVisible: boolean
}
```

### Props Details
- **cardDetails** (optional): `Details` - Card information object for editing
- **isDialogVisible** (required): `boolean` - Controls dialog visibility

### Default Values
```typescript
{
  number: '',
  name: '',
  expiry: '',
  cvv: '',
  isPrimary: false,
  type: '',
}
```

## Events Emitted

```typescript
interface Emit {
  (e: 'submit', value: Details): void
  (e: 'update:isDialogVisible', value: boolean): void
}
```

### Events Details
- **submit**: Emitted when card form is submitted with the complete Details object
- **update:isDialogVisible**: Emitted when dialog visibility should change (v-model support)

## Slots Available
This component does not expose any custom slots.

## Global State Dependencies
- No global state dependencies (Pinia stores, etc.)
- Uses Vue's built-in reactivity system only

## Internal Methods and Computed Properties

### Methods
- **formSubmit()**: Handles form submission
  - Emits 'submit' event with current cardDetails values
  - Does not automatically close dialog (parent handles this)

- **dialogModelValueUpdate(val: boolean)**: Updates dialog visibility
  - Emits 'update:isDialogVisible' with the provided value

### Reactive Data
- **cardDetails**: `ref<Details>` - Local reactive copy of the cardDetails prop using structuredClone

### Watchers
- **props watcher**: Updates cardDetails when props change, maintaining reactivity with parent data

## Usage Examples

### Add New Card
```vue
<template>
  <CardAddEditDialog
    v-model:is-dialog-visible="showDialog"
    @submit="handleCardAdd"
  />
</template>

<script setup lang="ts">
const showDialog = ref(false)

const handleCardAdd = (card: Details) => {
  console.log('New card:', card)
  // Save card to database
  showDialog.value = false // Close dialog after successful save
}
</script>
```

### Edit Existing Card
```vue
<template>
  <CardAddEditDialog
    v-model:is-dialog-visible="showDialog"
    :card-details="existingCard"
    @submit="handleCardUpdate"
  />
</template>

<script setup lang="ts">
const showDialog = ref(false)
const existingCard = ref({
  number: '1234567890123456',
  name: 'John Doe',
  expiry: '12/25',
  cvv: '123',
  isPrimary: true,
  type: 'Visa'
})

const handleCardUpdate = (card: Details) => {
  console.log('Updated card:', card)
  // Update card in database
  showDialog.value = false
}
</script>
```

### With Form Validation
```vue
<template>
  <CardAddEditDialog
    v-model:is-dialog-visible="showDialog"
    :card-details="cardForm"
    @submit="handleCardSubmit"
  />
</template>

<script setup lang="ts">
const showDialog = ref(false)
const cardForm = ref<Details>({
  number: '',
  name: '',
  expiry: '',
  cvv: '',
  isPrimary: false,
  type: ''
})

const handleCardSubmit = async (card: Details) => {
  // Validate card details
  if (!card.number || !card.name || !card.expiry || !card.cvv) {
    // Show validation error
    return
  }
  
  try {
    await saveCard(card)
    showDialog.value = false
  } catch (error) {
    // Handle error
  }
}
</script>
```

## Component Dependencies

### Vue Components
- **VDialog**: Vuetify dialog wrapper
- **VCard**: Vuetify card container
- **VCardItem**: Vuetify card header section
- **VCardTitle**: Vuetify card title component
- **VCardText**: Vuetify card content area
- **VForm**: Vuetify form wrapper
- **VRow** / **VCol**: Vuetify grid system
- **VBtn**: Vuetify button component
- **VSwitch**: Vuetify switch component for save option
- **AppTextField**: Custom text field component
- **DialogCloseBtn**: Custom dialog close button component

### Vue Composition API
- `ref`, `watch`, `toRaw`, `defineProps`, `defineEmits`, `withDefaults`

## UI/UX Features
- **Dynamic Content**: Title and description change based on add/edit mode
  - Add Mode: "Add New Card" / "Add card for future billing"
  - Edit Mode: "Edit Card" / "Edit your saved card details"
- **Complete Form**: All essential card fields with proper input types
- **Responsive Design**: Auto width on mobile, 600px on desktop
- **Grid Layout**: Responsive column layout for optimal field arrangement
- **Save Option**: Switch to save card for future billing
- **Form Structure**: Proper form submission handling

## Form Fields

### Card Information
1. **Card Number** (Full width)
   - Type: number
   - Placeholder: "1356 3215 6548 7898"

2. **Name** (Half width on md+)
   - Type: text
   - Placeholder: "John Doe"

3. **Expiry Date** (Quarter width on md+)
   - Type: text
   - Placeholder: "MM/YY"

4. **CVV Code** (Quarter width on md+)
   - Type: number
   - Placeholder: "654"

5. **Save Option** (Full width)
   - Type: switch
   - Label: "Save Card for future billing?"

## Form Layout
- **Row 1**: Card Number (full width)
- **Row 2**: Name (md:6), Expiry (md:3), CVV (md:3)
- **Row 3**: Save switch (full width)
- **Row 4**: Action buttons (centered)

## Styling Notes
- Uses Vuetify's responsive grid system
- Consistent spacing with Vuetify classes (pa-2, pa-sm-10, pt-6)
- Centered title and description in card header
- Centered action buttons
- Button styling with margin (me-4)

## Data Handling
- **Structured Clone**: Uses structuredClone for deep copying to avoid reference issues
- **Reactive Updates**: Watches props for changes and updates local form data
- **Type Safety**: Full TypeScript interface definitions
- **Default Values**: Comprehensive defaults for all form fields

## Security Considerations
- **CVV Field**: Proper number input type for CVV
- **Card Number**: Number input type for card number
- **No Auto-Complete**: Consider adding autocomplete attributes for security
- **Client-Side Only**: No sensitive data persistence in component

## Accessibility
- Proper heading hierarchy (h4)
- Form labels for all input fields
- Placeholder text for user guidance
- Button types and semantic structure
- Responsive design considerations

## Validation Ready
- Form structure supports adding validation rules
- Input types configured for proper validation
- Required field structure in place
- Error handling can be added to form submission