# CustomizerSection.vue

## Overview
A layout component for organizing sections within customizer interfaces. Provides consistent spacing, optional dividers, and labeled sections with colored chips.

## Props Interface

```typescript
interface Props {
  title: string      // Required - Section title displayed in chip
  divider?: boolean  // Optional - Show divider above section (default: true)
}
```

## Events Emitted
None - component is purely structural.

## Slots Available

### default (unnamed slot)
- **Purpose**: Contains the main content of the customizer section
- **Usage**: Place form controls, options, or any customizer content

## Global State Dependencies
None - component is fully self-contained.

## Internal Methods and Computed Properties

### Default Values
- `divider`: Defaults to `true` if not provided

## Component Dependencies

### Vuetify Components
- `VDivider`: Optional section separator
- `VChip`: Title display with primary color

## Usage Examples

### Basic Usage
```vue
<template>
  <CustomizerSection title="Theme Options">
    <VSwitch
      v-model="darkMode"
      label="Dark Mode"
    />
    <VSelect
      v-model="primaryColor"
      :items="colors"
      label="Primary Color"
    />
  </CustomizerSection>
</template>
```

### Without Divider
```vue
<template>
  <CustomizerSection
    title="General Settings"
    :divider="false"
  >
    <VCheckbox
      v-model="notifications"
      label="Enable Notifications"
    />
  </CustomizerSection>
</template>
```

### Multiple Sections
```vue
<template>
  <div class="customizer">
    <CustomizerSection
      title="Appearance"
      :divider="false"
    >
      <VRadioGroup v-model="theme">
        <VRadio label="Light" value="light" />
        <VRadio label="Dark" value="dark" />
      </VRadioGroup>
    </CustomizerSection>

    <CustomizerSection title="Layout">
      <VSlider
        v-model="sidebarWidth"
        label="Sidebar Width"
        min="200"
        max="400"
      />
    </CustomizerSection>

    <CustomizerSection title="Advanced">
      <VSwitch
        v-model="animations"
        label="Enable Animations"
      />
      <VSwitch
        v-model="rtl"
        label="Right-to-Left"
      />
    </CustomizerSection>
  </div>
</template>
```

### Theme Customizer Implementation
```vue
<template>
  <VNavigationDrawer class="customizer-drawer">
    <div class="customizer-header">
      <h2>Theme Customizer</h2>
    </div>

    <CustomizerSection
      title="Colors"
      :divider="false"
    >
      <div class="color-picker-grid">
        <div 
          v-for="color in colorOptions" 
          :key="color.name"
          class="color-option"
          @click="setThemeColor(color.value)"
        >
          <div 
            class="color-circle"
            :style="{ backgroundColor: color.value }"
          />
          <span>{{ color.name }}</span>
        </div>
      </div>
    </CustomizerSection>

    <CustomizerSection title="Typography">
      <VSelect
        v-model="fontFamily"
        :items="fonts"
        label="Font Family"
      />
      <VSlider
        v-model="fontSize"
        label="Base Font Size"
        min="12"
        max="18"
        step="1"
      />
    </CustomizerSection>

    <CustomizerSection title="Layout Options">
      <VRadioGroup v-model="layoutType">
        <VRadio label="Boxed" value="boxed" />
        <VRadio label="Full Width" value="full" />
      </VRadioGroup>
    </CustomizerSection>
  </VNavigationDrawer>
</template>
```

## Layout Structure

```
┌─────────────────────────┐
│ ─────── (optional)      │  ← VDivider (if divider=true)
│                         │
│ [Primary Chip: Title]   │  ← Section title in colored chip
│                         │
│ [Slot Content]          │  ← Default slot content
│                         │
└─────────────────────────┘
```

## Styling Features

### Section Layout
- Uses `.customizer-section` CSS class
- Flex column layout for vertical content stacking
- Consistent internal spacing

### Title Chip
- Small size chip with primary color
- Medium font weight for emphasis
- Colored background for visual hierarchy

### Divider
- Conditional rendering based on `divider` prop
- Provides visual separation between sections
- Consistent with Vuetify design system

## CSS Class Structure

```scss
.customizer-section {
  // Section-specific styles
  // Usually defined in parent customizer component
}
```

## Common Use Cases

1. **Theme Customizers**: Organizing theme options into logical groups
2. **Settings Panels**: Grouping related configuration options
3. **Preference Dialogs**: Categorizing user preferences
4. **Admin Panels**: Organizing administrative controls
5. **Dashboard Customization**: Grouping dashboard configuration options

## Best Practices

1. **Logical Grouping**: Group related options together
2. **Clear Titles**: Use descriptive section titles
3. **Visual Hierarchy**: Use dividers to separate major sections
4. **Consistent Spacing**: Rely on component's built-in spacing
5. **Accessibility**: Ensure proper heading structure in complex customizers

## Integration Patterns

### With VNavigationDrawer
```vue
<VNavigationDrawer class="customizer">
  <CustomizerSection title="Section 1" :divider="false">
    <!-- First section content -->
  </CustomizerSection>
  
  <CustomizerSection title="Section 2">
    <!-- Subsequent sections with dividers -->
  </CustomizerSection>
</VNavigationDrawer>
```

### With VExpansionPanels
```vue
<VExpansionPanels>
  <VExpansionPanel>
    <VExpansionPanelTitle>Advanced Options</VExpansionPanelTitle>
    <VExpansionPanelText>
      <CustomizerSection title="Performance" :divider="false">
        <!-- Performance options -->
      </CustomizerSection>
    </VExpansionPanelText>
  </VExpansionPanel>
</VExpansionPanels>
```

This component is essential for creating organized, user-friendly customization interfaces with consistent visual styling and proper content hierarchy.