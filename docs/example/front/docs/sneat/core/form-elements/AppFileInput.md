# AppFileInput Component

## Overview
A wrapper component around Vuetify's `VFileInput` that provides consistent styling, label handling, and file upload functionality across the Sneat admin template. It maintains the same design patterns as other form elements while offering robust file selection capabilities.

## Props Interface
The component inherits all props from `VFileInput` through `useAttrs()`. Key props include:

| Prop | Type | Default | Description |
|------|------|---------|-------------|
| `label` | `string \| undefined` | `undefined` | Label text displayed above the file input |
| `class` | `string \| undefined` | `undefined` | CSS classes for the wrapper div |
| `id` | `string \| undefined` | Auto-generated | Element ID for accessibility |
| `variant` | `string` | `'outlined'` | Vuetify field variant (overridden to 'outlined') |
| `multiple` | `boolean` | `false` | Allow multiple file selection |
| `accept` | `string` | `undefined` | Accepted file types (MIME types) |
| `chips` | `boolean` | `false` | Display selected files as chips |
| `counter` | `boolean` | `false` | Show file count |
| `show-size` | `boolean` | `false` | Display file sizes |
| `clearable` | `boolean` | `false` | Show clear button |

**Note**: All standard `VFileInput` props are supported and passed through.

## Events Emitted
All events from `VFileInput` are passed through automatically via `v-bind="$attrs"`.

Common events include:
- `update:modelValue` - Selected file(s) changed
- `change` - File selection changed
- `click:clear` - Clear button clicked
- `click:control` - Control area clicked
- `focus` - Input focused
- `blur` - Input lost focus

## Slots Available
All slots from `VFileInput` are forwarded using dynamic slot forwarding:

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
- `selection` - Custom file selection display
- `counter` - Custom counter display

## Global State Dependencies
None - this is a pure wrapper component.

## Internal Methods and Computed Properties

### Computed Properties
- `elementId`: Generates unique ID for the file input element
  - Uses provided `id` or `label` attribute with prefix `app-file-input-`
  - Falls back to auto-generated ID via `useId()`
- `label`: Extracts label from attributes for conditional rendering

## Usage Examples

### Basic File Input
```vue
<AppFileInput
  v-model="selectedFile"
  label="Upload File"
  placeholder="Choose file..."
/>
```

### Multiple File Selection
```vue
<AppFileInput
  v-model="selectedFiles"
  label="Upload Images"
  multiple
  chips
  counter
  show-size
  accept="image/*"
/>
```

### Document Upload with File Type Restriction
```vue
<AppFileInput
  v-model="documentFile"
  label="Upload Document"
  accept=".pdf,.doc,.docx"
  placeholder="Choose document (PDF, DOC, DOCX)"
  clearable
/>
```

### Image Upload with Preview
```vue
<AppFileInput
  v-model="imageFile"
  label="Profile Picture"
  accept="image/jpeg,image/png,image/gif"
  show-size
  prepend-icon="mdi-camera"
>
  <template #selection="{ fileNames }">
    <template v-for="fileName in fileNames" :key="fileName">
      <VChip
        size="small"
        label
        color="primary"
        class="me-2"
      >
        <VIcon start icon="mdi-paperclip" />
        {{ fileName }}
      </VChip>
    </template>
  </template>
</AppFileInput>
```

### File Upload with Validation
```vue
<AppFileInput
  v-model="validatedFile"
  label="Upload Resume"
  accept=".pdf"
  :rules="[
    v => !!v || 'Resume is required',
    v => !v || v.size < 5000000 || 'Resume must be less than 5MB'
  ]"
  placeholder="Choose PDF resume"
/>
```

### Drag and Drop File Input
```vue
<AppFileInput
  v-model="droppedFiles"
  label="Drag & Drop Files"
  multiple
  chips
  counter
  show-size
  placeholder="Drop files here or click to browse"
/>
```

### Custom File Selection Display
```vue
<AppFileInput
  v-model="customFiles"
  label="Project Files"
  multiple
>
  <template #selection="{ fileNames }">
    <div class="d-flex flex-column">
      <div v-for="fileName in fileNames" :key="fileName" class="d-flex align-center">
        <VIcon color="primary" class="me-2">
          {{ getFileIcon(fileName) }}
        </VIcon>
        <span class="text-truncate">{{ fileName }}</span>
      </div>
    </div>
  </template>
</AppFileInput>
```

## Component Dependencies
- `VLabel` - Vuetify label component
- `VFileInput` - Vuetify file input component
- `useAttrs()` - Vue composition function
- `useId()` - Vue composition function for unique IDs

## Styling
- Uses `.app-file-input` class for the wrapper
- Applies `flex-grow-1` for flexible layout
- Label styling: `mb-1 text-body-2 text-wrap` with custom line-height
- Text wrapping enabled for long labels
- Consistent with other form element styling

## File Handling Features
- **Single/Multiple Selection**: Support for selecting one or multiple files
- **File Type Filtering**: Restrict selectable file types via `accept` prop
- **File Size Display**: Optional file size information
- **Chip Display**: Show selected files as removable chips
- **Counter**: Display count of selected files
- **Clear Functionality**: Remove all selected files
- **Drag & Drop**: Native drag and drop support
- **Preview Support**: Custom slots for file previews

## Accepted File Types
Use the `accept` prop to restrict file types:

### Image Files
```vue
accept="image/*"                    <!-- All images -->
accept="image/jpeg,image/png"       <!-- Specific image types -->
accept=".jpg,.jpeg,.png,.gif"       <!-- By extension -->
```

### Document Files
```vue
accept=".pdf"                       <!-- PDF only -->
accept=".doc,.docx"                 <!-- Word documents -->
accept=".pdf,.doc,.docx,.txt"       <!-- Multiple document types -->
```

### Media Files
```vue
accept="video/*"                    <!-- All videos -->
accept="audio/*"                    <!-- All audio -->
accept="video/mp4,audio/mp3"        <!-- Specific media types -->
```

### Archive Files
```vue
accept=".zip,.rar,.7z"              <!-- Archive files -->
accept="application/zip"            <!-- By MIME type -->
```

## Accessibility Features
- Proper label association via `for` attribute
- Unique element IDs for screen readers
- Keyboard navigation support (Tab, Enter, Space)
- ARIA attributes from Vuetify
- Screen reader compatibility
- Focus management
- File selection announcements
- Accessible drag and drop

## Validation Support
Inherits comprehensive validation from VFileInput:
- Rules-based validation
- File size validation
- File type validation
- Required file validation
- Custom validators
- Real-time error display
- Custom error messages

## File Size Utilities
When working with file sizes, you can use these validation patterns:

```javascript
// File size validation examples
const fileSizeRules = [
  // Max 5MB
  v => !v || v.size < 5 * 1024 * 1024 || 'File must be less than 5MB',
  
  // Min 1KB
  v => !v || v.size > 1024 || 'File must be larger than 1KB',
  
  // Multiple files total size
  v => !v || v.reduce((total, file) => total + file.size, 0) < 10 * 1024 * 1024 || 'Total files must be less than 10MB'
]
```

## Security Considerations
- Always validate file types on the server side
- Scan uploaded files for malware
- Implement file size limits
- Use secure file storage
- Validate file contents, not just extensions
- Consider Content Security Policy restrictions

## Performance Tips
- Use `show-size` judiciously with large file lists
- Implement lazy loading for file previews
- Consider virtual scrolling for many files
- Debounce file validation for better UX

## File Location
`src/@core/components/app-form-elements/AppFileInput.vue`