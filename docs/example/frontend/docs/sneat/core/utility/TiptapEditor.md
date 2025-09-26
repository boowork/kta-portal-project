# TiptapEditor.vue

## Overview
A flexible rich text editor component built with TipTap that provides essential text formatting tools and alignment options. Features a clean toolbar interface with optional divider and customizable placeholder text.

## Props Interface

```typescript
interface Props {
  modelValue: string     // Required - HTML content of the editor
  placeholder?: string   // Optional - Placeholder text for empty editor
  isDivider?: boolean    // Optional - Show divider between toolbar and content (default: true)
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

### Default Values
- `isDivider`: Defaults to `true` for visual separation

## Component Dependencies

### TipTap Extensions
- `StarterKit`: Core editing functionality (headings, paragraphs, bold, italic, etc.)
- `TextAlign`: Text alignment support for headings and paragraphs
- `Underline`: Underline text formatting
- `Placeholder`: Placeholder text when editor is empty

### TipTap Vue Components
- `EditorContent`: Main editor content area
- `useEditor`: Composable for editor instance management

### Vuetify Components
- `VDivider`: Optional separator between toolbar and content

### Custom Components
- `IconBtn`: Toolbar formatting buttons

## Usage Examples

### Basic Usage
```vue
<template>
  <TiptapEditor
    v-model="content"
    placeholder="Start writing..."
  />
</template>

<script setup>
const content = ref('<p>Initial content</p>')
</script>
```

### Without Divider
```vue
<template>
  <TiptapEditor
    v-model="content"
    placeholder="Enter text here..."
    :is-divider="false"
  />
</template>
```

### In Form Context
```vue
<template>
  <VForm @submit="handleSubmit">
    <VTextField
      v-model="title"
      label="Title"
    />
    
    <div class="mt-4">
      <VLabel>Content</VLabel>
      <TiptapEditor
        v-model="articleContent"
        placeholder="Write your article content here..."
      />
    </div>
    
    <VBtn type="submit" class="mt-4">
      Save Article
    </VBtn>
  </VForm>
</template>

<script setup>
const title = ref('')
const articleContent = ref('')

const handleSubmit = () => {
  const article = {
    title: title.value,
    content: articleContent.value
  }
  // Submit logic
}
</script>
```

### With Validation
```vue
<template>
  <div>
    <TiptapEditor
      v-model="content"
      placeholder="Content is required"
      class="editor-with-validation"
      :class="{ 'error-border': hasError }"
    />
    <div v-if="errorMessage" class="text-error text-caption mt-1">
      {{ errorMessage }}
    </div>
  </div>
</template>

<script setup>
const content = ref('')
const errorMessage = ref('')

const hasError = computed(() => !!errorMessage.value)

watch(content, (newContent) => {
  if (!newContent || newContent === '<p></p>') {
    errorMessage.value = 'Content is required'
  } else if (newContent.length < 100) {
    errorMessage.value = 'Content must be at least 100 characters'
  } else {
    errorMessage.value = ''
  }
})
</script>

<style scoped>
.error-border {
  border: 1px solid rgb(var(--v-theme-error));
  border-radius: 4px;
}
</style>
```

### Blog Post Editor
```vue
<template>
  <div class="blog-editor">
    <VCard>
      <VCardTitle>Create New Post</VCardTitle>
      <VCardText>
        <VTextField
          v-model="blogPost.title"
          label="Post Title"
          class="mb-4"
        />
        
        <VTextField
          v-model="blogPost.excerpt"
          label="Excerpt"
          class="mb-4"
        />
        
        <TiptapEditor
          v-model="blogPost.content"
          placeholder="Write your blog post content here. Use the toolbar to format your text..."
        />
        
        <div class="d-flex justify-end mt-4 gap-2">
          <VBtn variant="outlined" @click="saveDraft">
            Save Draft
          </VBtn>
          <VBtn color="primary" @click="publishPost">
            Publish
          </VBtn>
        </div>
      </VCardText>
    </VCard>
  </div>
</template>

<script setup>
const blogPost = ref({
  title: '',
  excerpt: '',
  content: ''
})

const saveDraft = () => {
  // Save as draft logic
}

const publishPost = () => {
  // Publish logic
}
</script>
```

### With Auto-save
```vue
<template>
  <div>
    <div class="d-flex align-center justify-space-between mb-2">
      <VLabel>Document Content</VLabel>
      <VChip 
        v-if="autoSaveStatus"
        size="small"
        :color="autoSaveStatus === 'saving' ? 'warning' : 'success'"
      >
        {{ autoSaveStatus === 'saving' ? 'Saving...' : 'Saved' }}
      </VChip>
    </div>
    
    <TiptapEditor
      v-model="document.content"
      placeholder="Start writing your document..."
    />
  </div>
</template>

<script setup>
const document = ref({ content: '' })
const autoSaveStatus = ref('')

// Auto-save functionality
const { pause, resume } = useIntervalFn(async () => {
  if (document.value.content && document.value.content !== lastSavedContent.value) {
    autoSaveStatus.value = 'saving'
    
    try {
      await api.saveDocument(document.value)
      lastSavedContent.value = document.value.content
      autoSaveStatus.value = 'saved'
      
      setTimeout(() => {
        autoSaveStatus.value = ''
      }, 2000)
    } catch (error) {
      autoSaveStatus.value = 'error'
    }
  }
}, 10000) // Auto-save every 10 seconds

const lastSavedContent = ref('')
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
- Visual feedback for current cursor position formatting

## TipTap Configuration

```javascript
const editor = useEditor({
  content: props.modelValue,
  extensions: [
    StarterKit,                    // Core editing features
    TextAlign.configure({          // Text alignment options
      types: ['heading', 'paragraph'],
    }),
    Placeholder.configure({        // Placeholder when empty
      placeholder: props.placeholder ?? 'Write something here...',
    }),
    Underline,                     // Underline formatting
  ],
  onUpdate() {
    if (!editor.value) return
    emit('update:modelValue', editor.value.getHTML())
  },
})
```

## Layout and Styling

### Toolbar Layout
- Flex layout with gap between buttons (`d-flex gap-2 py-2 px-6`)
- Wrap-enabled for responsive behavior
- Small rounded icon buttons for compact interface

### Editor Content
- Minimum height: `15vh` for adequate editing space
- Padding: `0.5rem` for comfortable editing
- Focus outline removed for clean appearance

### Custom CSS Classes
```scss
.ProseMirror {
  padding: 0.5rem;
  min-block-size: 15vh;
  outline: none;

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
}
```

## Data Synchronization

### Reactive Content Updates
```javascript
watch(() => props.modelValue, () => {
  const isSame = editor.value?.getHTML() === props.modelValue
  if (isSame) return
  
  editor.value?.commands.setContent(props.modelValue)
})
```

Prevents infinite loops and cursor jumping by only updating when content actually differs.

## Differences from ProductDescriptionEditor

### Simplified Toolbar
- More compact button layout
- Reduced spacing (gap-2 vs gap-1)
- Different padding (py-2 px-6 vs pa-6)

### Optional Divider
- Configurable divider via `isDivider` prop
- Allows seamless integration in different layouts

### Different Styling
- Smaller minimum height (15vh vs 12vh)
- Different padding structure
- More condensed overall appearance

## Best Practices

1. **Content Validation**: Validate HTML content appropriately
2. **Performance**: Handle large content efficiently
3. **Accessibility**: Ensure proper keyboard navigation
4. **Mobile**: Test on mobile devices for touch interaction
5. **Auto-save**: Implement for longer content editing sessions
6. **Sanitization**: Sanitize HTML output for security

## Common Use Cases

- Comment systems
- Forum posts
- Short articles
- Documentation editing
- Note-taking applications
- Email composition
- Social media posts
- Quick content editing

## Integration Patterns

### With Markdown Output
```javascript
import { generateHTML } from '@tiptap/html'
import { generateMarkdown } from '@tiptap/markdown'

const getMarkdown = () => {
  return generateMarkdown(editor.value.getJSON())
}
```

### With Character Count
```javascript
const characterCount = computed(() => {
  if (!editor.value) return 0
  return editor.value.storage.characterCount?.characters() || 0
})
```

### With Word Count
```javascript
const wordCount = computed(() => {
  if (!editor.value) return 0
  return editor.value.storage.characterCount?.words() || 0
})
```

This component provides a clean, efficient rich text editing experience suitable for a wide variety of content creation scenarios with minimal overhead and maximum flexibility.