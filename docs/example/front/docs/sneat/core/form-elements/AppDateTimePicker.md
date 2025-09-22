# AppDateTimePicker Component

## Overview
A sophisticated date and time picker component built on top of Vuetify's form field system and FlatPickr library. It provides a consistent, theme-aware date/time selection interface with extensive customization options for the Sneat admin template.

## Props Interface
The component combines props from VInput and VField with custom configuration:

| Prop | Type | Default | Description |
|------|------|---------|-------------|
| `autofocus` | `boolean` | `false` | Auto-focus the input on mount |
| `counter` | `boolean \| number \| string` | `false` | Show character counter |
| `counterValue` | `Function` | `undefined` | Custom counter value function |
| `prefix` | `string` | `undefined` | Text prefix inside the input |
| `placeholder` | `string` | `undefined` | Placeholder text |
| `persistentPlaceholder` | `boolean` | `false` | Keep placeholder visible when focused |
| `persistentCounter` | `boolean` | `false` | Keep counter visible |
| `suffix` | `string` | `undefined` | Text suffix inside the input |
| `type` | `string` | `'text'` | Input type |
| `modelModifiers` | `object` | `undefined` | v-model modifiers |
| `density` | `string` | `'comfortable'` | Input density (VInput prop) |
| `hideDetails` | `boolean \| string` | `'auto'` | Hide validation details |
| `variant` | `string` | `'outlined'` | Field variant (VField prop) |
| `color` | `string` | `'primary'` | Field color theme |
| `config` | `object` | `{}` | FlatPickr configuration options |

**Note**: Inherits all VInput and VField props through `makeVInputProps()` and `makeVFieldProps()`.

## Events Emitted
Custom events specific to the date picker:

- `update:modelValue` - Date/time value changed
- `click:control` - Control area clicked
- `mousedown:control` - Mouse down on control
- `update:focused` - Focus state changed
- `click:clear` - Clear button clicked

## Slots Available
Inherits all slots from VInput and VField components. Common slots include:
- `prepend-icon` - Icon before the input field
- `append-icon` - Icon after the input field
- `prepend-inner` - Content inside field before input
- `append-inner` - Content inside field after input
- `details` - Custom validation/hint content

## Global State Dependencies
- `useConfigStore()` - For theme state management
- `useTheme()` - Vuetify theme integration for calendar styling

## Internal Methods and Computed Properties

### Computed Properties
- `elementId`: Generates unique ID for the picker element
  - Uses field ID, label, or input ID with prefix `app-picker-field-`
  - Falls back to auto-generated ID via `useId()`

### Key Methods
- `onClear(el)`: Handles clear button functionality
- `emitModelValue(val)`: Emits model value updates
- `updateThemeClassInCalendar()`: Applies current theme to FlatPickr calendar

### Reactive State
- `isCalendarOpen`: Tracks calendar visibility state
- `isInlinePicker`: Determines if picker should render inline
- `focused`: Focus state from `useFocus()` composable

## Configuration & Theming

### FlatPickr Configuration
The component automatically configures FlatPickr with:
- Custom navigation arrows using Boxicons
- Theme integration with Vuetify themes
- Responsive calendar positioning
- Custom styling classes

### Theme Integration
- Automatically applies current Vuetify theme to calendar
- Watches for theme changes and updates calendar styling
- Supports both light and dark themes
- Custom CSS variables for consistent theming

## Usage Examples

### Basic Date Picker
```vue
<AppDateTimePicker
  v-model="selectedDate"
  label="Select Date"
  placeholder="Choose a date"
/>
```

### Date Range Picker
```vue
<AppDateTimePicker
  v-model="dateRange"
  label="Date Range"
  :config="{
    mode: 'range',
    dateFormat: 'Y-m-d'
  }"
/>
```

### DateTime Picker with Time
```vue
<AppDateTimePicker
  v-model="appointmentTime"
  label="Appointment Date & Time"
  :config="{
    enableTime: true,
    dateFormat: 'Y-m-d H:i',
    time_24hr: true
  }"
/>
```

### Inline Calendar
```vue
<AppDateTimePicker
  v-model="calendarDate"
  label="Calendar View"
  :config="{
    inline: true,
    defaultDate: 'today'
  }"
/>
```

### Custom Date Format
```vue
<AppDateTimePicker
  v-model="formattedDate"
  label="Formatted Date"
  :config="{
    dateFormat: 'd/m/Y',
    altInput: true,
    altFormat: 'F j, Y'
  }"
/>
```

### Multiple Date Selection
```vue
<AppDateTimePicker
  v-model="multipleDates"
  label="Multiple Dates"
  :config="{
    mode: 'multiple',
    conjunction: ', '
  }"
/>
```

### Date Picker with Validation
```vue
<AppDateTimePicker
  v-model="validatedDate"
  label="Birth Date"
  :rules="[
    v => !!v || 'Date is required',
    v => new Date(v) <= new Date() || 'Date must be in the past'
  ]"
  :config="{
    maxDate: 'today',
    dateFormat: 'Y-m-d'
  }"
/>
```

### Disabled Dates Example
```vue
<AppDateTimePicker
  v-model="workingDay"
  label="Select Working Day"
  :config="{
    disable: [
      function(date) {
        // Disable weekends
        return date.getDay() === 0 || date.getDay() === 6;
      }
    ],
    dateFormat: 'Y-m-d'
  }"
/>
```

## Component Dependencies
- `VInput` - Vuetify input wrapper component
- `VField` - Vuetify field component
- `VLabel` - Vuetify label component
- `FlatPickr` - Vue FlatPickr component for date/time picking
- `useTheme()` - Vuetify theme composable
- `useConfigStore()` - Config store for theme management
- `useFocus()` - Focus state management
- `useId()` - Unique ID generation

## Styling
- Uses `.app-picker-field` class for the wrapper
- Integrates with Vuetify's field system styling
- Custom FlatPickr theme styling with SCSS variables
- Responsive calendar design
- Theme-aware color system
- Custom elevation and shadows
- Support for RTL layouts

## FlatPickr Configuration Options
The component supports all FlatPickr configuration options through the `config` prop:

### Date Selection
- `mode: 'single' | 'multiple' | 'range'` - Selection mode
- `dateFormat: string` - Date format string
- `defaultDate: Date | string` - Default selected date
- `minDate: Date | string` - Minimum selectable date
- `maxDate: Date | string` - Maximum selectable date

### Time Selection  
- `enableTime: boolean` - Enable time picker
- `noCalendar: boolean` - Show only time picker
- `time_24hr: boolean` - Use 24-hour format
- `minuteIncrement: number` - Minute step increment

### Display Options
- `inline: boolean` - Display calendar inline
- `altInput: boolean` - Show formatted date in separate input
- `altFormat: string` - Format for alt input
- `clickOpens: boolean` - Open calendar on click

### Localization
- `locale: string | object` - Locale settings
- `weekNumbers: boolean` - Show week numbers
- `firstDayOfWeek: number` - First day of week (0-6)

## Accessibility Features
- Proper label association with `for` attribute
- Unique element IDs for screen readers
- Keyboard navigation support (arrows, enter, escape)
- ARIA attributes from Vuetify field system
- Screen reader compatible
- Focus management and announcements
- Date format announcements
- Accessible calendar navigation

## Validation Support
Integrates with Vuetify's validation system:
- Rules-based validation with real-time feedback
- Custom error messages and hints
- Required field validation
- Date range validation
- Custom validator functions
- Validation state visual indicators

## Key Features
- **Multi-format Support**: Date, time, datetime, range selection
- **Theme Integration**: Automatic light/dark theme support
- **Localization**: Full internationalization support
- **Accessibility**: Complete keyboard and screen reader support
- **Validation**: Comprehensive form validation integration
- **Customization**: Extensive styling and behavior options
- **Responsive**: Works on desktop and mobile devices
- **Performance**: Efficient rendering and updates

## Browser Compatibility
- Modern browsers with ES6+ support
- Mobile Safari and Chrome
- Responsive touch interactions
- Fallback for older browsers

## File Location
`src/@core/components/app-form-elements/AppDateTimePicker.vue`