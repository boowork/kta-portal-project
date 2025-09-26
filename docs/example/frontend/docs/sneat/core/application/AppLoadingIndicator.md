# AppLoadingIndicator Component

## Overview
The `AppLoadingIndicator` component provides a visual loading indicator using a progress bar positioned at the top of the screen. It's designed to work with Vue's Suspense component to show loading states during page navigation or async operations.

## Location
`/src/components/AppLoadingIndicator.vue`

## Props Interface
This component does not accept any props.

## Events Emitted
This component does not emit any events.

## Slots Available
This component does not provide any slots.

## Global State Dependencies
- None - The component is completely self-contained

## Exposed Methods
The component exposes two methods via `defineExpose`:

### `fallbackHandle()`
Initiates the loading indicator when a fallback state is triggered.
- Sets `showProgress` to `true`
- Resets `progressValue` to 10
- Sets `isFallbackState` to `true`
- Starts the progress animation

### `resolveHandle()`
Completes the loading indicator when the async operation resolves.
- Sets `isFallbackState` to `false`
- Sets `progressValue` to 100
- After 300ms delay, resets all values and hides the indicator

## Internal Methods and Computed Properties

### Methods

#### `startBuffer()`
Controls the progress animation by incrementally updating both progress and buffer values.
- Clears any existing interval
- Updates `progressValue` and `bufferValue` with random increments every 800ms
- Progress increases by 5-15 units per interval
- Buffer increases by 6-15 units per interval

### Reactive Properties

#### `bufferValue: Ref<number>`
- Initial value: `20`
- Controls the buffer value of the VProgressLinear component

#### `progressValue: Ref<number>`
- Initial value: `10`
- Controls the main progress value
- Capped at 82 when in fallback state to prevent completion before resolution

#### `isFallbackState: Ref<boolean>`
- Initial value: `false`
- Tracks whether the component is in a loading fallback state

#### `interval: Ref<ReturnType<typeof setInterval>>`
- Stores the interval reference for progress updates
- Used for cleanup when stopping animation

#### `showProgress: Ref<boolean>`
- Initial value: `false`
- Controls visibility of the progress bar

### Watchers

#### Progress Control Watcher
Watches `[progressValue, isFallbackState]` and:
- Prevents progress from exceeding 82% during fallback state
- Restarts the buffer animation when values change

## Usage Examples

### Basic Usage with Suspense
```vue
<script setup lang="ts">
const refLoadingIndicator = ref<any>(null)
const isFallbackStateActive = ref(false)

watch([isFallbackStateActive, refLoadingIndicator], () => {
  if (isFallbackStateActive.value && refLoadingIndicator.value)
    refLoadingIndicator.value.fallbackHandle()
  
  if (!isFallbackStateActive.value && refLoadingIndicator.value)
    refLoadingIndicator.value.resolveHandle()
}, { immediate: true })
</script>

<template>
  <AppLoadingIndicator ref="refLoadingIndicator" />
  
  <RouterView #="{Component}">
    <Suspense
      :timeout="0"
      @fallback="isFallbackStateActive = true"
      @resolve="isFallbackStateActive = false"
    >
      <Component :is="Component" />
    </Suspense>
  </RouterView>
</template>
```

### Manual Control
```vue
<script setup lang="ts">
const loadingIndicator = ref<any>(null)

function startLoading() {
  loadingIndicator.value?.fallbackHandle()
}

function stopLoading() {
  loadingIndicator.value?.resolveHandle()
}
</script>

<template>
  <AppLoadingIndicator ref="loadingIndicator" />
  <VBtn @click="startLoading">Start Loading</VBtn>
  <VBtn @click="stopLoading">Stop Loading</VBtn>
</template>
```

## Component Dependencies

### Vuetify Components
- `VProgressLinear` - Core progress bar component

### Vue Composition API
- `ref` - Reactive references
- `watch` - Reactive watchers
- `defineExpose` - Method exposure

## Styling
The component uses fixed positioning with:
- `z-index: 9999` - Ensures visibility above other content
- `inset-block-start: 0` - Positioned at top of viewport
- `inset-inline: 0 0` - Full width across viewport
- `height="2"` - Thin 2px height progress bar
- `color="primary"` - Uses theme primary color
- `bg-color="background"` - Uses theme background color

## Implementation Notes

1. **Animation Behavior**: The progress smoothly animates with random increments to simulate realistic loading
2. **Fallback State Protection**: Progress is capped at 82% during fallback to prevent premature completion appearance
3. **Cleanup**: Intervals are properly cleared to prevent memory leaks
4. **Timing**: 300ms delay on completion provides smooth transition
5. **Template Ref Access**: Component methods must be accessed through template refs due to `defineExpose` usage

## Best Practices

1. **Always use template refs** when accessing exposed methods
2. **Pair with Suspense** for automatic loading state management
3. **Handle both fallback and resolve states** in watchers
4. **Consider timeout values** in Suspense to prevent indefinite loading
5. **Test loading states** during development to ensure proper behavior