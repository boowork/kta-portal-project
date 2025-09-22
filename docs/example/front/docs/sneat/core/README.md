# Sneat Core Components Documentation

This documentation provides comprehensive information about all core components available in the Sneat Vue.js Admin Template. Each component is thoroughly documented with props, events, usage examples, and best practices.

## 📁 Component Categories

### 🎨 [Form Elements](./form-elements/) (13 components)
Enhanced form components with consistent styling and validation support.

| Component | Description | Key Features |
|-----------|-------------|--------------|
| [AppAutocomplete](./form-elements/AppAutocomplete.md) | Autocomplete input with search | Search filtering, custom items, multiple selection |
| [AppCombobox](./form-elements/AppCombobox.md) | Combobox with free text input | Custom values, search, multiple selection |
| [AppDateTimePicker](./form-elements/AppDateTimePicker.md) | Date/time picker with FlatPickr | Theme integration, range selection, inline mode |
| [AppFileInput](./form-elements/AppFileInput.md) | File upload component | Multiple files, drag & drop, validation |
| [AppSelect](./form-elements/AppSelect.md) | Enhanced select dropdown | Object support, custom display, grouping |
| [AppTextarea](./form-elements/AppTextarea.md) | Multi-line text input | Auto-grow, character counter, validation |
| [AppTextField](./form-elements/AppTextField.md) | Single-line text input | Various input types, icons, validation |
| [CustomCheckboxes](./form-elements/CustomCheckboxes.md) | Rich content checkboxes | Grid layout, title/description, multiple selection |
| [CustomCheckboxesWithIcon](./form-elements/CustomCheckboxesWithIcon.md) | Icon-based checkboxes | Centered icons, responsive grid |
| [CustomCheckboxesWithImage](./form-elements/CustomCheckboxesWithImage.md) | Image-based checkboxes | Background images, overlay selection |
| [CustomRadios](./form-elements/CustomRadios.md) | Rich content radio buttons | Single selection, detailed content |
| [CustomRadiosWithIcon](./form-elements/CustomRadiosWithIcon.md) | Icon-based radio buttons | Centered icons, single selection |
| [CustomRadiosWithImage](./form-elements/CustomRadiosWithImage.md) | Image-based radio buttons | Background images, component support |

### 🃏 [Cards](./cards/) (5 components)
Specialized card components for data display and code examples.

| Component | Description | Key Features |
|-----------|-------------|--------------|
| [AppCardActions](./cards/AppCardActions.md) | Interactive card with actions | Collapsible, loading states, refresh/remove |
| [AppCardCode](./cards/AppCardCode.md) | Code display card | Syntax highlighting, language switching, copy function |
| [CardStatisticsHorizontal](./cards/CardStatisticsHorizontal.md) | Horizontal stats layout | Icon avatar, change indicators |
| [CardStatisticsTarget](./cards/CardStatisticsTarget.md) | Target-style statistics | Centered layout, dropdown menu |
| [CardStatisticsVertical](./cards/CardStatisticsVertical.md) | Vertical stats with avatar | Image avatar, more actions menu |

### 🔧 [Utility](./utility/) (18 components)
General-purpose utility components for various UI needs.

| Component | Description | Key Features |
|-----------|-------------|--------------|
| [AppBarSearch](./utility/AppBarSearch.md) | Advanced search dialog | Autocomplete, keyboard navigation, recent searches |
| [AppDrawerHeaderSection](./utility/AppDrawerHeaderSection.md) | Drawer header component | Title display, close functionality |
| [AppStepper](./utility/AppStepper.md) | Multi-step process | Validation, layout options, progress tracking |
| [BuyNow](./utility/BuyNow.md) | Promotional button | Animated gradient, marketplace detection |
| [CardStatisticsVerticalSimple](./utility/CardStatisticsVerticalSimple.md) | Simple vertical stats | Dashboard metrics, clean layout |
| [CustomizerSection](./utility/CustomizerSection.md) | Section organizer | Customizer interfaces, collapsible sections |
| [DialogCloseBtn](./utility/DialogCloseBtn.md) | Standardized close button | Consistent dialog closing |
| [DropZone](./utility/DropZone.md) | Drag-and-drop file upload | Image preview, file validation |
| [I18n](./utility/I18n.md) | Language selector | Vue I18n integration, flag icons |
| [MoreBtn](./utility/MoreBtn.md) | Three-dot menu button | Optional dropdown, consistent styling |
| [Notifications](./utility/Notifications.md) | Notifications system | Badge count, management, marking read |
| [ProductDescriptionEditor](./utility/ProductDescriptionEditor.md) | Rich text editor | Product descriptions, TipTap integration |
| [ScrollToTop](./utility/ScrollToTop.md) | Scroll-to-top button | Smooth animations, visibility threshold |
| [Shortcuts](./utility/Shortcuts.md) | Application shortcuts | Grid layout, keyboard shortcuts |
| [TablePagination](./utility/TablePagination.md) | Data table pagination | Standardized pagination controls |
| [TheCustomizer](./utility/TheCustomizer.md) | Theme customization panel | Complete theme controls, settings persistence |
| [ThemeSwitcher](./utility/ThemeSwitcher.md) | Theme mode switcher | Light/dark/auto modes, dropdown selection |
| [TiptapEditor](./utility/TiptapEditor.md) | Flexible rich text editor | Formatting toolbar, extensible |

### 💬 [Dialogs](./dialogs/) (16 components)
Modal dialog components for user interactions and forms.

| Component | Description | Key Features |
|-----------|-------------|--------------|
| [AddAuthenticatorAppDialog](./dialogs/AddAuthenticatorAppDialog.md) | 2FA setup dialog | QR code, authenticator apps |
| [AddEditAddressDialog](./dialogs/AddEditAddressDialog.md) | Address management | Comprehensive address forms |
| [AddEditPermissionDialog](./dialogs/AddEditPermissionDialog.md) | Permission management | System permissions, warnings |
| [AddEditRoleDialog](./dialogs/AddEditRoleDialog.md) | Role management | Permission matrix, granular controls |
| [AddPaymentMethodDialog](./dialogs/AddPaymentMethodDialog.md) | Payment method display | Theme-aware logos, provider info |
| [CardAddEditDialog](./dialogs/CardAddEditDialog.md) | Credit card forms | Card information, validation |
| [ConfirmDialog](./dialogs/ConfirmDialog.md) | Confirmation system | Three states: confirm/success/cancel |
| [CreateAppDialog](./dialogs/CreateAppDialog.md) | App creation wizard | Multi-step process, form validation |
| [EnableOneTimePasswordDialog](./dialogs/EnableOneTimePasswordDialog.md) | SMS 2FA setup | Phone verification, OTP |
| [PaymentProvidersDialog](./dialogs/PaymentProvidersDialog.md) | Payment provider info | Third-party provider details |
| [PricingPlanDialog](./dialogs/PricingPlanDialog.md) | Pricing plan display | Plan information wrapper |
| [ReferAndEarnDialog](./dialogs/ReferAndEarnDialog.md) | Referral program | Social sharing, referral codes |
| [ShareProjectDialog](./dialogs/ShareProjectDialog.md) | Project collaboration | Member management, permissions |
| [TwoFactorAuthDialog](./dialogs/TwoFactorAuthDialog.md) | 2FA method selection | Multiple authentication options |
| [UserInfoEditDialog](./dialogs/UserInfoEditDialog.md) | User profile editing | Comprehensive user data forms |
| [UserUpgradePlanDialog](./dialogs/UserUpgradePlanDialog.md) | Plan management | Subscription upgrades, billing |

### 🏢 [Application](./application/) (4 components)
Application-specific components for loading, pricing, search, and error handling.

| Component | Description | Key Features |
|-----------|-------------|--------------|
| [AppLoadingIndicator](./application/AppLoadingIndicator.md) | Loading progress indicator | Vue Suspense integration, exposed methods |
| [AppPricing](./application/AppPricing.md) | Pricing plans display | Plan comparison, monthly/yearly toggle |
| [AppSearchHeader](./application/AppSearchHeader.md) | Search banner | Background image, customizable layout |
| [ErrorHeader](./application/ErrorHeader.md) | Error page headers | Status codes, responsive typography |

## 🏗 Architecture Overview

### Component Patterns

1. **Wrapper Components**: Form elements wrap Vuetify components with consistent styling
2. **Composite Components**: Complex components combine multiple features (dialogs, cards)
3. **Utility Components**: Reusable components for common UI patterns
4. **Business Components**: Application-specific components with business logic

### Common Features

- **TypeScript Integration**: Full type safety with comprehensive interfaces
- **Vue 3 Composition API**: Modern reactive patterns with `<script setup>`
- **Vuetify 3 Integration**: Consistent design system and theming
- **Accessibility**: WCAG compliance and keyboard navigation
- **Responsive Design**: Mobile-first approach with breakpoint support
- **Theme Awareness**: Light/dark mode support throughout

### Global Dependencies

- **Vue 3**: Core framework with Composition API
- **Vuetify 3**: Material Design component library
- **TypeScript**: Type safety and enhanced developer experience
- **VueUse**: Composition utilities for common functionality
- **Vue I18n**: Internationalization support
- **TipTap**: Rich text editing capabilities

## 📋 Usage Guidelines

### Best Practices

1. **Import Components**: Use auto-imports or explicit imports as needed
2. **TypeScript Types**: Leverage provided interfaces for type safety
3. **Props Validation**: Follow component prop requirements and defaults
4. **Event Handling**: Implement proper event listeners for component interactions
5. **Accessibility**: Ensure proper labeling and keyboard navigation
6. **Theming**: Use consistent color props and theme integration

### Common Patterns

```vue
<!-- Basic component usage -->
<AppTextField
  v-model="value"
  label="Field Label"
  placeholder="Enter value..."
  :rules="validationRules"
/>

<!-- Dialog component pattern -->
<SomeDialog
  v-model:isDialogVisible="showDialog"
  @submit="handleSubmit"
  @update:isDialogVisible="showDialog = $event"
/>

<!-- Card component with actions -->
<AppCardActions
  title="Card Title"
  :loading="isLoading"
  @refresh="handleRefresh"
  @remove="handleRemove"
>
  <p>Card content goes here</p>
</AppCardActions>
```

## 🔗 Related Documentation

- [CLAUDE.md](../../CLAUDE.md) - Development guidelines and project structure
- [Vuetify 3 Documentation](https://vuetifyjs.com/) - UI framework documentation
- [Vue 3 Documentation](https://vuejs.org/) - Core framework documentation
- [TypeScript Documentation](https://www.typescriptlang.org/) - Type system documentation

## 📝 Contributing

When contributing to component documentation:

1. Follow the established documentation structure
2. Include comprehensive usage examples
3. Document all props, events, and slots
4. Provide accessibility considerations
5. Include TypeScript type definitions
6. Add component dependency information

---

**Total Components Documented**: 57 components across 5 categories

Each component documentation includes detailed props interfaces, usage examples, accessibility features, and integration guidelines to help developers efficiently implement and customize components within the Sneat admin template system.