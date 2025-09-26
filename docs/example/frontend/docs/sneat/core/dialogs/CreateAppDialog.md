# CreateAppDialog.vue

## Overview
A comprehensive multi-step dialog component for creating applications. Features a 5-step wizard with stepper navigation, including app details, framework selection, database configuration, billing information, and final submission. Uses visual selection interfaces and form validation.

## Props Interface

```typescript
interface Props {
  isDialogVisible: boolean
}
```

### Props Details
- **isDialogVisible** (required): `boolean` - Controls dialog visibility

## Events Emitted

```typescript
interface Emit {
  (e: 'update:isDialogVisible', val: boolean): void
  (e: 'updatedData', val: unknown): void
}
```

### Events Details
- **update:isDialogVisible**: Emitted when dialog visibility should change (v-model support)
- **updatedData**: Emitted when form is submitted with the complete application data

## Slots Available
This component does not expose any custom slots.

## Global State Dependencies
- No global state dependencies (Pinia stores, etc.)
- Uses Vue's built-in reactivity system only

## Internal Methods and Computed Properties

### Methods
- **dialogVisibleUpdate(val: boolean)**: Updates dialog visibility and resets stepper to first step
- **onSubmit()**: Handles form submission with alert notification and data emission

### Reactive Data
- **currentStep**: `ref<number>` - Current stepper position (0-4)
- **createAppData**: `ref<object>` - Form data object containing all application settings

### Static Data Arrays
- **createApp**: Stepper configuration with 5 steps
- **categories**: Application category options
- **frameworks**: Development framework options  
- **databases**: Database engine options

## Stepper Configuration

### Step 1: Details
- **Icon**: bx-home
- **Purpose**: Application name input and category selection
- **Fields**: Application name, category selection (CRM, Ecommerce, Learning)

### Step 2: Frameworks
- **Icon**: bx-box
- **Purpose**: Development framework selection
- **Options**: React Native, Angular, Vue, HTML

### Step 3: Database
- **Icon**: bx-data
- **Purpose**: Database name and engine selection
- **Fields**: Database name input
- **Options**: Firebase, AWS, MySQL

### Step 4: Billing
- **Icon**: bx-credit-card
- **Purpose**: Payment information collection
- **Fields**: Card number, name, expiry, CVV, save option

### Step 5: Submit
- **Icon**: bx-check
- **Purpose**: Final submission with confirmation
- **Content**: Submit illustration and confirmation message

## Form Data Structure

```typescript
const createAppData = ref({
  category: 'crm-application',      // Selected app category
  framework: 'js-framework',       // Selected framework
  database: 'firebase-database',   // Selected database
  cardNumber: null,                // Payment card number
  cardName: '',                    // Cardholder name
  cardExpiry: '',                  // Card expiry date
  cardCvv: '',                     // Card CVV code
  isSave: true,                    // Save card option
})
```

## Usage Examples

### Basic Usage
```vue
<template>
  <CreateAppDialog
    v-model:is-dialog-visible="showDialog"
    @updated-data="handleAppCreation"
  />
</template>

<script setup lang="ts">
const showDialog = ref(false)

const handleAppCreation = (appData: any) => {
  console.log('New app data:', appData)
  // Process application creation
}

const openCreateApp = () => {
  showDialog.value = true
}
</script>
```

### With Progress Tracking
```vue
<template>
  <div>
    <VBtn @click="startAppCreation">
      Create New App
    </VBtn>
    
    <CreateAppDialog
      v-model:is-dialog-visible="showCreateDialog"
      @updated-data="handleAppSubmission"
    />
  </div>
</template>

<script setup lang="ts">
const showCreateDialog = ref(false)
const isProcessing = ref(false)

const startAppCreation = () => {
  showCreateDialog.value = true
}

const handleAppSubmission = async (appData: any) => {
  isProcessing.value = true
  
  try {
    await createApplication(appData)
    showCreateDialog.value = false
    // Show success message
  } catch (error) {
    // Handle error
  } finally {
    isProcessing.value = false
  }
}
</script>
```

## Component Dependencies

### Vue Components
- **VDialog**: Vuetify dialog wrapper
- **VCard**: Vuetify card container
- **VCardText**: Vuetify card content area
- **VRow** / **VCol**: Vuetify grid system
- **VWindow** / **VWindowItem**: Vuetify stepper content panels
- **VRadioGroup** / **VRadio**: Radio button controls
- **VList** / **VListItem**: List components for option selection
- **VAvatar**: Avatar components for option icons
- **VIcon**: Icon components
- **VForm**: Form wrapper for billing step
- **VBtn**: Button components for navigation
- **VSwitch**: Switch component for save option
- **VImg**: Image component for final illustration
- **AppTextField**: Custom text field component
- **AppStepper**: Custom stepper component
- **DialogCloseBtn**: Custom dialog close button

### External Dependencies
- **@images/illustrations/illustration-john.png**: Final step illustration

### Vue Composition API
- `ref`, `watch`, `defineProps`, `defineEmits`

## Selection Options

### Application Categories
1. **CRM Application** (info color)
   - Icon: bx-file
   - Description: "Scales with any business"

2. **Ecommerce Platforms** (success color)
   - Icon: bx-cart
   - Description: "Grow Your Business With App"

3. **Online Learning Platform** (error color)
   - Icon: bx-laptop
   - Description: "Start learning today"

### Development Frameworks
1. **React Native** (info color)
   - Icon: bx-bxl-react
   - Description: "Create truly native apps"

2. **Angular** (error color)
   - Icon: bx-bxl-angular
   - Description: "Most suited for your application"

3. **Vue** (success color)
   - Icon: bx-bxl-vuejs
   - Description: "JS web frameworks"

4. **HTML** (warning color)
   - Icon: bx-bxl-html5
   - Description: "Progressive Framework"

### Database Engines
1. **Firebase** (error color)
   - Icon: bx-bxl-firebase
   - Description: "Cloud Firestore"

2. **AWS** (warning color)
   - Icon: bx-bxl-amazon
   - Description: "Amazon Fast NoSQL Database"

3. **MySQL** (info color)
   - Icon: bx-data
   - Description: "Basic MySQL database"

## UI/UX Features
- **Vertical Stepper**: Clear progress indication with labeled steps
- **Visual Selection**: Icon-based selection for categories, frameworks, and databases
- **Responsive Layout**: Adaptive grid system for different screen sizes
- **Navigation Controls**: Previous/Next buttons with appropriate states
- **Form Validation**: Structure ready for validation implementation
- **Progress Tracking**: Visual stepper shows current position
- **Conditional Buttons**: Submit button only appears on final step

## Stepper Navigation
- **Previous Button**: Disabled on first step
- **Next Button**: Advances to next step
- **Submit Button**: Only visible on final step
- **Auto-Reset**: Returns to first step when dialog closes

## Styling Notes
- Uses Vuetify's spacing classes and grid system
- Custom stepper styling with `stepper-icon-step-bg` class
- Card list styling with custom gap configuration
- Responsive padding (pa-5, pa-sm-16)
- Icon sizing (22px for stepper, 30px for selection options)

## Form Fields by Step

### Step 1 - Details
- Application Name (text input)
- Category (radio selection with visual options)

### Step 2 - Frameworks  
- Framework (radio selection with visual options)

### Step 3 - Database
- Database Name (text input)
- Database Engine (radio selection with visual options)

### Step 4 - Billing
- Card Number (number input)
- Name on Card (text input)
- Expiry Date (text input, MM/YY format)
- CVV (text input)
- Save Card (switch)

### Step 5 - Submit
- Confirmation message
- Illustration
- Submit button

## Custom Styles
```scss
.stepper-content .card-list {
  --v-card-list-gap: 16px;
}
```

## Accessibility
- Proper heading hierarchy (h4, h5, h6)
- Form labels and semantic structure
- Icon alternatives with descriptive text
- Keyboard navigation support through Vuetify components
- Screen reader friendly stepper navigation