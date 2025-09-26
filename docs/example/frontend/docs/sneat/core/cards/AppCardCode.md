# AppCardCode Component

A specialized card component for displaying code examples with syntax highlighting, language switching, and copy functionality.

## Location
`/src/@core/components/cards/AppCardCode.vue`

## Props Interface

```typescript
type CodeLanguages = 'ts' | 'js'
type CodeProp = Record<CodeLanguages, string>

interface Props {
  title: string              // Card title text (required)
  code: CodeProp            // Code content for each language (required)
  codeLanguage?: string     // Language for syntax highlighting
  noPadding?: boolean       // Disable default card padding
}
```

### Default Values
- `codeLanguage`: `'markup'`
- `noPadding`: `false`

## Events Emitted

**None** - This component doesn't emit custom events.

## Slots Available

### Default Slot
- **Purpose**: Main card content area
- **Description**: Content displayed above the code section
- **Conditional Rendering**: 
  - If `noPadding` is `true`: Rendered directly without VCardText wrapper
  - If `noPadding` is `false`: Rendered inside VCardText with default padding

## Global State Dependencies

### Cookies
- **`preferredCodeLanguage`**: Persistent user preference for code language (ts/js)
  - **Type**: `Ref<CodeLanguages>`
  - **Default**: `'ts'`
  - **Max Age**: `COOKIE_MAX_AGE_1_YEAR`
  - **Purpose**: Remembers user's preferred code language across sessions

## Internal Methods and Computed Properties

### Reactive References

#### `isCodeShown`
- **Type**: `Ref<boolean>`
- **Initial Value**: `false`
- **Purpose**: Controls visibility of the code section

#### `preferredCodeLanguage`
- **Type**: `UseCookieReturn<CodeLanguages>`
- **Purpose**: Persistent storage of user's preferred code language
- **Values**: `'ts'` | `'js'`

### Computed Properties

#### `codeSnippet`
- **Type**: `ComputedRef<string>`
- **Purpose**: Generates syntax-highlighted HTML from the selected code
- **Dependencies**: 
  - `props.code[preferredCodeLanguage.value]`
  - Shiki highlighter with Dracula theme
- **Returns**: HTML string with syntax highlighting

### Composables Used

#### `useClipboard`
- **Source**: `props.code[preferredCodeLanguage.value]`
- **Returns**: `{ copy, copied }`
- **Purpose**: Provides clipboard functionality for code copying

#### `getSingletonHighlighter`
- **Config**: 
  - **Themes**: `['dracula', 'dracula-soft']`
  - **Languages**: `['vue']`
- **Purpose**: Syntax highlighting engine

## Usage Examples

### Basic Usage
```vue
<template>
  <AppCardCode
    title="Vue Component Example"
    :code="{ 
      ts: '<script setup lang=\"ts\">\n// TypeScript code here\n</script>',
      js: '<script setup>\n// JavaScript code here\n</script>'
    }"
  >
    <p>This component demonstrates Vue 3 syntax</p>
  </AppCardCode>
</template>
```

### With No Padding
```vue
<template>
  <AppCardCode
    title="Full Width Content"
    :code="codeExample"
    no-padding
  >
    <VImg 
      src="/example-image.jpg"
      height="200"
    />
  </AppCardCode>
</template>

<script setup>
const codeExample = {
  ts: `<template>
  <VImg src="/example-image.jpg" height="200" />
</template>

<script setup lang="ts">
// TypeScript implementation
</script>`,
  js: `<template>
  <VImg src="/example-image.jpg" height="200" />
</template>

<script setup>
// JavaScript implementation
</script>`
}
</script>
```

### Complex Code Example
```vue
<template>
  <AppCardCode
    title="Advanced Vue Component"
    :code="advancedExample"
  >
    <div class="pa-4">
      <h6 class="text-h6 mb-3">Component Features</h6>
      <ul>
        <li>Reactive data binding</li>
        <li>Event handling</li>
        <li>Computed properties</li>
      </ul>
    </div>
  </AppCardCode>
</template>

<script setup>
const advancedExample = {
  ts: `<template>
  <div>
    <VTextField 
      v-model="inputValue"
      label="Enter text"
      @input="handleInput"
    />
    <p>Computed: {{ computedValue }}</p>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'

const inputValue = ref<string>('')

const computedValue = computed(() => 
  inputValue.value.toUpperCase()
)

const handleInput = (event: Event) => {
  console.log('Input changed:', event)
}
</script>`,
  js: `<template>
  <div>
    <VTextField 
      v-model="inputValue"
      label="Enter text"
      @input="handleInput"
    />
    <p>Computed: {{ computedValue }}</p>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const inputValue = ref('')

const computedValue = computed(() => 
  inputValue.value.toUpperCase()
)

const handleInput = (event) => {
  console.log('Input changed:', event)
}
</script>`
}
</script>
```

## Component Dependencies

### External Libraries
- **shiki**: Syntax highlighting engine
  - `getSingletonHighlighter`: Core highlighting functionality
- **vue3-perfect-scrollbar**: Scrollbar component for code area
  - `PerfectScrollbar`: Provides custom scrollbars

### Vue Composition API
- `ref`: For reactive references
- `computed`: For computed properties
- `withDefaults`: For prop defaults
- `defineProps`: For prop definition

### Custom Composables
- `useCookie`: For persistent preference storage (auto-imported)
- `useClipboard`: For clipboard functionality (auto-imported)

### Auto-imported Constants
- `COOKIE_MAX_AGE_1_YEAR`: Cookie expiration constant

### Vuetify Components
- **VCard**: Main card container
- **VCardItem**: Card header section
- **VCardTitle**: Card title display
- **VCardText**: Card content wrapper
- **VExpandTransition**: Collapsible code section animation
- **VDivider**: Visual separator between content and code
- **VBtnToggle**: Language selector toggle buttons
- **VBtn**: Individual toggle buttons and copy button
- **VIcon**: Icons for language indicators and copy button

### Custom Components
- **IconBtn**: Custom icon button component (auto-imported from @core/components)

## Styling

### SCSS Styles

#### Code Typography
```scss
code[class*="language-"],
pre[class*="language-"] {
  font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace;
  font-size: 14px;
}
```

#### Code Block Styling
```scss
:not(pre) > code[class*="language-"],
pre[class*="language-"] {
  border-radius: vuetify.$card-border-radius;
  max-block-size: 500px;
}
```

#### Copy Button Positioning
```scss
.app-card-code-copy-icon {
  inset-block-start: 1.2em;
  inset-inline-end: 0.8em;
}
```

#### Shiki Code Block
```scss
.app-card-code {
  .shiki {
    padding: 0.75rem;
    text-wrap: wrap;
  }
}
```

## Features

### Language Switching
- Toggle between TypeScript and JavaScript versions
- Persistent preference storage via cookies
- Visual indicators for active language

### Code Highlighting
- Powered by Shiki with Dracula theme
- Vue.js syntax support
- Automatic HTML generation

### Copy Functionality
- One-click code copying to clipboard
- Visual feedback with check icon
- Automatic clipboard API integration

### Responsive Design
- Perfect scrollbar for long code blocks
- Maximum height constraint (500px)
- Text wrapping for better readability

## Notes

- Requires both TypeScript and JavaScript versions of code
- Uses `v-html` for syntax-highlighted code rendering
- Implements proper scrolling constraints for long code blocks
- Copy button positioned absolutely over the code area
- Language preference persists across browser sessions