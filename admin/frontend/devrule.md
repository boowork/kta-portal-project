# Development Rules - KTA Portal Admin Frontend

## Architecture Pattern

**Page-specific Composables with Structured Return Objects**

### Directory Structure
```
src/pages/apps/{domain}/
├── list/                      # List page
│   ├── index.vue              # UI only
│   ├── composables.ts         # use{Domain}List
│   └── components/            # Page-specific components
└── view/                      # Detail/Edit page
    ├── [id].vue               # UI only
    ├── composables.ts         # use{Domain}Detail
    └── components/            # Page-specific components
```

### Separation of Concerns
- **Page Component**: UI rendering and user interactions only
- **Composables**: Business logic, API calls, state management
- **Components**: Reusable UI elements

## Core Development Rules

### 1. State Initialization
All reactive state must have explicit types and default values.

**Reference**: See `admin/front/src/pages/apps/user/list/composables.ts:9-21`

### 2. Error Handling
Use `useErrorHandler` pattern for all API calls with proper try-catch blocks.

**Reference**: See `admin/front/src/composables/useErrorHandler.ts`

### 3. TypeScript Safety
- No implicit `any` types
- All API responses properly typed
- Computed properties with error safety (try-catch)

### 4. API Integration Pattern
Standard pattern: `clearErrors()` → API call → success/error handling → `finally` block

### 5. Watch Pattern
Single watch for multiple reactive dependencies to trigger data fetching.

**Reference**: See `admin/front/src/pages/apps/user/list/index.vue:42-49`

## CRUD Standards

### List Page Requirements
- Pagination support with Spring Data JDBC response format
- Search functionality
- Widget data display with safe fallbacks
- Loading states for all async operations

### Detail Page Requirements
- View/Edit mode toggle
- Form state management with cancel functionality
- Delete confirmation dialogs
- Navigation back to list page

## Composable Structure

### Naming Convention
- List: `use{Domain}List`
- Detail: `use{Domain}Detail`

### Return Object Structure
```typescript
return {
  // State refs (direct return for reactivity)
  // Computed (readonly)
  // Actions (functions)
  // UI helpers
}
```

## UI/UX Standards

### Component Usage
- Prefer project standard components: `AppTextField`, `AppSelect`, `IconBtn`
- Use Vuetify components directly only when necessary

### User Feedback
- Success toasts for CRUD operations
- Confirmation dialogs for destructive actions
- Loading states on buttons and tables

### Error Display
- Field-level errors: Below input fields (automatic via useErrorHandler)
- General errors: Toast notifications
- Critical errors: Dialog modals (optional)

## Code Quality

### Required Tools
- `npm run typecheck` - Must pass before commit
- `npm run lint` - Must pass before commit

### Performance
- Error-safe computed properties
- Single watch for multiple dependencies
- Memory leak prevention (Vue 3 handles most automatically)

### Naming Rules
- Files/Directories: lowercase, singular domain names
- Composables: `use{Domain}{Action}`
- State: camelCase nouns
- Actions: camelCase verbs
- Types: PascalCase

## Development Workflow

1. Copy templates from existing user pages
2. Replace domain names throughout
3. Define API types in `src/api/types.ts`
4. Implement API functions in `src/api/{domain}.ts`
5. Configure routing
6. Run typecheck and lint

**Reference Templates**:
- `admin/front/src/pages/apps/user/list/`
- `admin/front/src/pages/apps/user/view/`

## Related Documentation
- [Project Architecture](../../CLAUDE.md)
- [Backend Development Rules](../backend/devrule.md)
- [API Documentation](../backend/docs/api/index.md)
