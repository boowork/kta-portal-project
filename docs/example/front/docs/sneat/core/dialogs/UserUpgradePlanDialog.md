# UserUpgradePlanDialog.vue

## Overview
A user plan upgrade dialog that allows users to select and upgrade to different subscription plans. Features plan selection, current plan display with pricing, and subscription cancellation functionality with confirmation dialog integration.

## Props Interface

```typescript
interface Prop {
  isDialogVisible: boolean
}
```

### Props Details
- **isDialogVisible** (required): `boolean` - Controls dialog visibility

## Events Emitted

```typescript
interface Emit {
  (e: 'update:isDialogVisible', val: boolean): void
}
```

### Events Details
- **update:isDialogVisible**: Emitted when dialog visibility should change (v-model support)

## Slots Available
This component does not expose any custom slots.

## Global State Dependencies
- No global state dependencies (Pinia stores, etc.)
- Uses Vue's built-in reactivity system only

## Internal Methods and Computed Properties

### Methods
- **dialogModelValueUpdate(val: boolean)**: Updates dialog visibility by emitting the update event

### Reactive Data
- **selectedPlan**: `ref<string>` - Currently selected plan ('standard' by default)
- **isConfirmDialogVisible**: `ref<boolean>` - Controls subscription cancellation confirmation dialog

### Static Data
- **plansList**: Array of available subscription plans with descriptions and values

## Available Plans

```typescript
const plansList = [
  { desc: 'Standard - $99/month', title: 'Standard', value: 'standard' },
  { desc: 'Basic - $0/month', title: 'Basic', value: 'basic' },
  { desc: 'Enterprise - $499/month', title: 'Enterprise', value: 'enterprice' },
  { desc: 'Company - $999/month', title: 'Company', value: 'company' },
]
```

## Usage Examples

### Basic Usage
```vue
<template>
  <UserUpgradePlanDialog v-model:is-dialog-visible="showUpgrade" />
</template>

<script setup lang="ts">
const showUpgrade = ref(false)

const openUpgradeDialog = () => {
  showUpgrade.value = true
}
</script>
```

### Account Management Integration
```vue
<template>
  <div>
    <VCard title="Current Plan">
      <VCardText>
        <div class="d-flex justify-space-between align-center">
          <div>
            <h6>Standard Plan</h6>
            <p>$99/month</p>
          </div>
          <VBtn @click="upgradeUserPlan">
            Upgrade Plan
          </VBtn>
        </div>
      </VCardText>
    </VCard>
    
    <UserUpgradePlanDialog
      v-model:is-dialog-visible="showPlanUpgrade"
      @plan-upgraded="handlePlanUpgrade"
    />
  </div>
</template>

<script setup lang="ts">
const showPlanUpgrade = ref(false)

const upgradeUserPlan = () => {
  showPlanUpgrade.value = true
}

// Note: This dialog doesn't emit plan events by default
// but could be extended for plan upgrade tracking
const handlePlanUpgrade = (plan: string) => {
  console.log('Plan upgraded to:', plan)
}
</script>
```

### Billing Dashboard
```vue
<template>
  <div>
    <VRow>
      <VCol cols="12" md="6">
        <VCard title="Subscription">
          <VCardText>
            <p>Current: Standard Plan ($99/month)</p>
            <VBtn @click="managePlan">Manage Plan</VBtn>
          </VCardText>
        </VCard>
      </VCol>
    </VRow>
    
    <UserUpgradePlanDialog
      v-model:is-dialog-visible="planDialogVisible"
      :current-plan="userPlan"
    />
  </div>
</template>

<script setup lang="ts">
const planDialogVisible = ref(false)
const userPlan = ref('standard')

const managePlan = () => {
  planDialogVisible.value = true
}
</script>
```

## Component Dependencies

### Vue Components
- **VDialog**: Vuetify dialog wrapper
- **VCard**: Vuetify card container
- **VCardText**: Vuetify card content area
- **VBtn**: Vuetify button component
- **VDivider**: Section divider
- **AppSelect**: Custom select component for plan selection
- **ConfirmDialog**: Custom confirmation dialog for cancellation
- **DialogCloseBtn**: Custom dialog close button component

### Vue Composition API
- `ref`, `defineProps`, `defineEmits`

## UI/UX Features
- **Plan Selection**: Dropdown with all available plans and pricing
- **Current Plan Display**: Shows current subscription with pricing
- **Upgrade Action**: Direct upgrade button for selected plan
- **Cancellation Option**: Cancel subscription with confirmation
- **Responsive Design**: Auto width on mobile, 650px on desktop
- **Price Display**: Large, prominent pricing display ($99/month)

## Dialog Sections

### 1. Plan Selection
- **Dropdown**: AppSelect with plan options
- **Label**: "Choose a plan"
- **Default**: Basic plan
- **Action**: Upgrade button (full width on mobile)

### 2. Current Plan Information
- **Divider**: Visual separation
- **Description**: "User current plan is standard plan"
- **Price Display**: Large price format with currency symbol
- **Currency**: $ symbol as superscript
- **Price**: Large h1 format (99)
- **Period**: "/month" as subscript

### 3. Subscription Management
- **Cancel Button**: Error color, tonal variant
- **Action**: Opens confirmation dialog
- **Confirmation**: Integrated ConfirmDialog component

## Pricing Display Format
```
$ 99 / month
↑ ↑  ↑
sup h1 sub
```

## Confirmation Dialog Integration
The component includes a ConfirmDialog for subscription cancellation:

```vue
<ConfirmDialog
  v-model:is-dialog-visible="isConfirmDialogVisible"
  cancel-title="Cancelled"
  confirm-title="Unsubscribed!"
  confirm-msg="Your subscription cancelled successfully."
  confirmation-question="Are you sure to cancel your subscription?"
  cancel-msg="Unsubscription Cancelled!!"
/>
```

## Plan Options
1. **Basic**: $0/month (Free tier)
2. **Standard**: $99/month (Current default)
3. **Enterprise**: $499/month (Business tier)
4. **Company**: $999/month (Enterprise tier)

## Styling Notes
- Uses Vuetify's spacing classes (pa-2, pa-sm-10, my-6)
- Responsive flex layout for plan selection
- Large typography for pricing display (text-h1)
- Color coding for price (text-primary) and cancel button (error)
- Proper spacing between sections with dividers

## Subscription Flow
1. **Plan Selection**: User chooses new plan from dropdown
2. **Upgrade Action**: Click upgrade to process change
3. **Billing Update**: Plan change processed (external logic)
4. **Confirmation**: Success feedback (external handling)

## Cancellation Flow
1. **Cancel Button**: User clicks cancel subscription
2. **Confirmation Dialog**: ConfirmDialog opens with warning
3. **User Choice**: Confirm or cancel the cancellation
4. **Result Feedback**: Success or cancellation message

## Accessibility
- Proper heading hierarchy (h4, h1)
- Form labels for plan selection
- Button types and semantic structure
- Color contrast for pricing display
- Clear action button labels

## Integration Points
- **Billing System**: Connect upgrade action to payment processing
- **User Management**: Update user plan in database
- **Notification System**: Send plan change confirmations
- **Analytics**: Track plan upgrade/downgrade patterns
- **Support System**: Log subscription changes for support

## Business Logic Ready
- Plan selection structure supports API integration
- Upgrade button ready for payment processing
- Cancellation confirmation prevents accidental cancellations
- Price display can be dynamically updated
- Plan validation can be added

## Security Considerations
- **Confirmation Required**: Cancellation requires user confirmation
- **Plan Validation**: Client-side plan selection validation
- **Billing Integration**: Secure payment processing integration points
- **Access Control**: Can be restricted based on user permissions

## Customization Options
- **Plan Addition**: Easy to add new subscription tiers
- **Pricing Updates**: Dynamic pricing from API
- **Currency Support**: Internationalization ready
- **Feature Comparison**: Can be extended with plan feature comparison
- **Trial Periods**: Structure supports trial plan integration

## Performance Considerations
- **Static Plan Data**: Compile-time plan configuration
- **Minimal State**: Simple reactive data management
- **Lazy Confirmation**: ConfirmDialog renders only when needed
- **Efficient Rendering**: Vuetify components optimized for performance