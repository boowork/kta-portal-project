# ProductDescriptionEditor.vue

## Overview
A rich text editor specifically designed for product descriptions using TipTap. Features a comprehensive toolbar with formatting options and text alignment capabilities.

## Props Interface

```typescript
interface Props {
  modelValue: string     // Required - HTML content of the editor
  placeholder?: string   // Optional - Placeholder text for empty editor
}
```

## Events Emitted

```typescript
interface Emit {
  (e: 'update:modelValue', value: string): void  // HTML content changes
}
```

## Slots Available
None - component has a fixed toolbar and editor structure.

## Global State Dependencies
None - component manages its own editor state.

## Internal Methods and Computed Properties

### Reactive Properties
- `editorRef`: Reference to the editor DOM element
- `editor`: TipTap editor instance

### TipTap Configuration
- Uses `useEditor()` composable from TipTap Vue 3
- Configured with multiple extensions for rich text functionality

## Component Dependencies

### TipTap Extensions
- `StarterKit`: Core editing functionality
- `TextAlign`: Text alignment support (left, center, right, justify)
- `Underline`: Underline text formatting
- `Placeholder`: Placeholder text when editor is empty

### TipTap Vue Components
- `EditorContent`: Main editor content area
- `useEditor`: Composable for editor instance management

### Vuetify Components
- `VBtn`: Toolbar formatting buttons
- `VIcon`: Button icons
- `VDivider`: Separator between toolbar and content

## Usage Examples

### Basic Usage
```vue
<template>
  <ProductDescriptionEditor
    v-model="productDescription"
    placeholder="Enter product description..."
  />
</template>

<script setup>
const productDescription = ref('<p>Initial product description</p>')
</script>
```

### In Product Form
```vue
<template>
  <VForm>
    <VTextField
      v-model="productName"
      label="Product Name"
    />
    
    <VTextField
      v-model="productPrice"
      label="Price"
      type="number"
    />
    
    <div class="mt-4">
      <VLabel>Product Description</VLabel>
      <ProductDescriptionEditor
        v-model="productDescription"
        placeholder="Describe your product features, benefits, and specifications..."
      />
    </div>
    
    <VBtn type="submit">Save Product</VBtn>
  </VForm>
</template>

<script setup>
const productName = ref('')
const productPrice = ref(0)
const productDescription = ref('')

const saveProduct = () => {
  const product = {
    name: productName.value,
    price: productPrice.value,
    description: productDescription.value
  }
  // Save product logic
}
</script>
```

### With Validation
```vue
<template>
  <div>
    <ProductDescriptionEditor
      v-model="description"
      placeholder="Product description is required"
    />
    <div v-if="descriptionError" class="text-error text-caption mt-1">
      {{ descriptionError }}
    </div>
  </div>
</template>

<script setup>
const description = ref('')
const descriptionError = ref('')

watch(description, (newValue) => {
  if (!newValue || newValue === '<p></p>') {
    descriptionError.value = 'Product description is required'
  } else if (newValue.length < 50) {
    descriptionError.value = 'Description must be at least 50 characters'
  } else {
    descriptionError.value = ''
  }
})
</script>
```

### With Character Count
```vue
<template>
  <div>
    <ProductDescriptionEditor
      v-model="description"
      placeholder="Write a compelling product description..."
    />
    <div class="d-flex justify-end mt-2">
      <span class="text-caption text-medium-emphasis">
        {{ characterCount }} characters
      </span>
    </div>
  </div>
</template>

<script setup>
const description = ref('')

const characterCount = computed(() => {
  // Remove HTML tags for character count
  const textContent = description.value.replace(/<[^>]*>/g, '')
  return textContent.length
})
</script>
```

## Editor Features

### Text Formatting
- **Bold**: Toggle bold text (`bx-bold` icon)
- **Italic**: Toggle italic text (`bx-italic` icon)
- **Underline**: Toggle underline text (`bx-underline` icon)
- **Strikethrough**: Toggle strikethrough text (`bx-strikethrough` icon)

### Text Alignment
- **Left Align**: Align text to left (`bx-align-left` icon)
- **Center Align**: Center text (`bx-align-middle` icon)
- **Right Align**: Align text to right (`bx-align-right` icon)
- **Justify**: Justify text (`bx-align-justify` icon)

### Button States
- Active formatting shows tonal variant with primary color
- Inactive formatting shows text variant with default color
- Visual feedback for current formatting state

## TipTap Editor Configuration

```javascript
const editor = useEditor({
  content: props.modelValue,
  extensions: [
    StarterKit,                    // Basic editing features
    TextAlign.configure({          // Text alignment
      types: ['heading', 'paragraph'],
    }),
    Placeholder.configure({        // Placeholder text
      placeholder: props.placeholder ?? 'Write something here...',
    }),
    Underline,                     // Underline support
  ],
  onUpdate() {
    if (!editor.value) return
    emit('update:modelValue', editor.value.getHTML())
  },
})
```

## Styling Features

### Layout
- Padding: `pa-6` for consistent spacing
- Toolbar with gap between buttons (`d-flex gap-1 flex-wrap`)
- Divider separation between toolbar and editor

### Editor Content
- Minimum height: `12vh` for adequate editing space
- No padding on ProseMirror content
- Placeholder styling with proper opacity and positioning
- Focus outline removed for cleaner appearance

### Custom CSS Classes
```scss
.productDescriptionEditor {
  .ProseMirror {
    padding: 0 !important;
    min-block-size: 12vh;
    
    p {
      margin-block-end: 0;
    }
    
    // Placeholder styling
    p.is-editor-empty:first-child::before {
      block-size: 0;
      color: #adb5bd;
      content: attr(data-placeholder);
      float: inline-start;
      pointer-events: none;
    }
    
    &-focused {
      outline: none;
    }
  }
}
```

## Data Synchronization

### Reactive Content Updates
- Watches `modelValue` prop changes
- Compares current editor HTML with new prop value
- Updates editor content only when different to prevent cursor jumping

```javascript
watch(() => props.modelValue, () => {
  const isSame = editor.value?.getHTML() === props.modelValue
  if (isSame) return
  
  editor.value?.commands.setContent(props.modelValue)
})
```

## Best Practices

1. **Content Validation**: Validate HTML content and length
2. **Auto-save**: Implement periodic auto-saving for long descriptions
3. **Image Support**: Consider adding image upload capabilities
4. **SEO**: Provide plain text extraction for SEO purposes
5. **Accessibility**: Ensure proper heading structure and alt text
6. **Performance**: Handle large content efficiently

## Common Use Cases

- E-commerce product descriptions
- Blog post content
- Service descriptions
- Documentation content
- Marketing copy
- Feature descriptions
- Help text and instructions

## Integration Patterns

### With Image Upload
```javascript
// Could be extended to support image uploads
const uploadImage = async (file) => {
  const formData = new FormData()
  formData.append('image', file)
  
  const response = await api.post('/upload', formData)
  return response.data.url
}
```

### With Auto-save
```javascript
const { pause, resume } = useIntervalFn(() => {
  if (description.value !== lastSavedValue.value) {
    saveToDatabase(description.value)
    lastSavedValue.value = description.value
  }
}, 30000) // Auto-save every 30 seconds
```

This component provides a professional rich text editing experience specifically tailored for product descriptions with all essential formatting tools.