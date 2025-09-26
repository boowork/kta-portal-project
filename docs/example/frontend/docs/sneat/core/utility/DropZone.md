# DropZone.vue

## Overview
A drag-and-drop file upload component that supports image files with preview functionality. Features both drag-and-drop and click-to-browse interfaces with file management capabilities.

## Props Interface
None - component is self-contained and manages its own state.

## Events Emitted
None - component handles file management internally.

## Slots Available
None - component has a fixed UI structure.

## Global State Dependencies
None - component uses local reactive state.

## Internal Methods and Computed Properties

### Reactive Properties
- `fileData`: Array of `FileData` objects containing file and URL information

### Interfaces
```typescript
interface FileData {
  file: File   // The actual File object
  url: string  // Object URL for preview
}
```

### Methods
- `onDrop(DroppedFiles: File[] | null)`: Handles dropped files
- `onChange(selectedFiles: any)`: Handles file dialog selection

### Composables Used
- `useDropZone()`: Provides drag-and-drop functionality
- `useFileDialog()`: Handles file selection dialog
- `useObjectUrl()`: Creates object URLs for file previews

## Component Dependencies

### VueUse Composables
- `useDropZone`: Drag-and-drop zone functionality
- `useFileDialog`: File selection dialog management
- `useObjectUrl`: File URL generation for previews

### Vuetify Components
- `VBtn`: Browse button and remove buttons
- `VIcon`: Upload icon and remove actions
- `VCard`, `VCardText`, `VCardActions`: File preview cards
- `VImg`: Image preview display
- `VRow`, `VCol`: Grid layout for file cards

### Custom Components
- `IconBtn`: Upload icon button

## Usage Examples

### Basic Usage
```vue
<template>
  <div class="file-upload-section">
    <h3>Upload Images</h3>
    <DropZone />
  </div>
</template>
```

### In Form Context
```vue
<template>
  <VForm>
    <VTextField 
      v-model="title"
      label="Title"
    />
    
    <div class="my-4">
      <VLabel>Attach Images</VLabel>
      <DropZone />
    </div>
    
    <VBtn type="submit">Submit</VBtn>
  </VForm>
</template>
```

### With Custom Styling
```vue
<template>
  <div class="custom-upload-area">
    <DropZone class="custom-dropzone" />
  </div>
</template>

<style scoped>
.custom-dropzone {
  border-radius: 12px;
  border-style: solid;
}

.custom-dropzone .drop-zone {
  min-height: 200px;
  background: linear-gradient(45deg, #f5f5f5, #fafafa);
}
</style>
```

## File Handling Features

### File Validation
- **Image Only**: Accepts only files with MIME type starting with 'image/'
- **Automatic Rejection**: Shows alert for non-image files
- **Type Checking**: Validates file type on both drop and select

### File Management
- **Preview Generation**: Automatic image preview generation
- **File Information**: Displays filename and file size
- **Remove Functionality**: Individual file removal buttons
- **Size Display**: File size shown in KB

### User Interactions
- **Drag & Drop**: Full drag-and-drop support
- **Click to Browse**: Click anywhere in drop zone to open file dialog
- **Individual Removal**: Remove specific files from the collection

## UI States

### Empty State
```
┌─────────────────────────────────────┐
│                                     │
│            [Upload Icon]            │
│                                     │
│     Drag and drop your image here.  │
│                                     │
│                  or                 │
│                                     │
│          [Browse Images]            │
│                                     │
└─────────────────────────────────────┘
```

### With Files State
```
┌─────────────────────────────────────┐
│  ┌─────────┐  ┌─────────┐  ┌───────┐ │
│  │ [Image] │  │ [Image] │  │[Image]│ │
│  │filename │  │filename │  │ etc.  │ │
│  │ size KB │  │ size KB │  │       │ │
│  │[Remove] │  │[Remove] │  │[Remove│ │
│  └─────────┘  └─────────┘  └───────┘ │
└─────────────────────────────────────┘
```

## Technical Implementation

### File Drop Handler
```typescript
function onDrop(DroppedFiles: File[] | null) {
  DroppedFiles?.forEach(file => {
    if (file.type.slice(0, 6) !== 'image/') {
      alert('Only image files are allowed')
      return
    }

    fileData.value.push({
      file,
      url: useObjectUrl(file).value ?? '',
    })
  })
}
```

### File Dialog Handler
```typescript
onChange((selectedFiles: any) => {
  if (!selectedFiles) return

  for (const file of selectedFiles) {
    fileData.value.push({
      file,
      url: useObjectUrl(file).value ?? '',
    })
  }
})
```

## Styling Features

### Drop Zone Styling
- Dashed border: `border: 1px dashed rgba(var(--v-theme-on-surface), var(--v-border-opacity))`
- Responsive padding and spacing
- Cursor pointer for interactive feedback

### File Preview Cards
- Grid layout with responsive columns (12/sm-4)
- Image preview with fixed dimensions (200px x 150px)
- File information display
- Remove button in card actions

### Interactive States
- Visual feedback for drag-over states
- Hover effects on clickable areas
- Loading states during file processing

## Browser Compatibility

### File API Support
- Modern browsers with File API support
- Object URL generation for previews
- Drag and drop event handling

### Fallback Behavior
- Click-to-browse always available
- Graceful degradation for older browsers

## Performance Considerations

### Memory Management
- Object URLs are created for previews
- Consider cleanup for large numbers of files
- File size display helps users manage uploads

### Image Processing
- Client-side preview generation
- No server interaction during selection
- Efficient grid layout for multiple files

## Best Practices

1. **File Size Limits**: Consider implementing file size validation
2. **File Type Extension**: Could be extended for other file types
3. **Upload Progress**: Add progress indicators for actual uploads
4. **Error Handling**: Enhance error messaging for various scenarios
5. **Accessibility**: Add proper ARIA labels and keyboard navigation
6. **Cleanup**: Implement proper object URL cleanup when files are removed

## Common Use Cases

- Profile picture upload
- Gallery image management
- Document attachment interfaces
- Media content creation
- Product image uploads
- Portfolio submissions