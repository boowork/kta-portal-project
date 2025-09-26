# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

### Development
- `npm run dev` - Start development server with hot reload
- `npm run preview` - Preview production build locally on port 5050

### Build & Type Checking  
- `npm run build` - Build for production
- `npm run typecheck` - Run TypeScript type checking without emitting files

### Linting & Code Quality
- `npm run lint` - Run ESLint with auto-fix on TypeScript, JavaScript, Vue files
- Uses custom ESLint rules in `eslint-internal-rules/` directory

### Icon & Setup Tasks
- `npm run build:icons` - Build iconify icon bundle
- `npm run msw:init` - Initialize Mock Service Worker in public directory
- `npm run postinstall` - Runs automatically after install (builds icons + MSW setup)

## Architecture

### Core Structure
This is a Vue 3 + Vuetify admin template with a modular architecture:

- **`src/@core/`** - Core framework components, utilities, and stores (reusable across projects)
- **`src/@layouts/`** - Layout system with components, stores, and configuration
- **`src/components/`** - Project-specific components
- **`src/views/`** - Page-level Vue components
- **`src/pages/`** - File-based routing pages (handled by unplugin-vue-router)
- **`src/plugins/`** - Vue plugins including i18n, iconify, fake API handlers

### Key Technologies & Patterns

**Framework Stack:**
- Vue 3 with Composition API
- Vuetify 3 for UI components  
- Pinia for state management
- Vue Router with file-based routing
- TypeScript throughout

**Development Tools:**
- Vite for build tooling and dev server
- Auto-imports for Vue, composables, and utilities
- Component auto-registration from specified directories
- MSW (Mock Service Worker) for API mocking

**Styling & Theming:**
- SCSS with Vuetify variables in `src/assets/styles/variables/_vuetify.scss`
- Theme configuration in `themeConfig.ts` with multi-language and RTL support
- Skin system for different visual themes

### Path Aliases
- `@` → `src/`
- `@core` → `src/@core`
- `@layouts` → `src/@layouts`  
- `@images` → `src/assets/images/`
- `@styles` → `src/assets/styles/`
- `@themeConfig` → `themeConfig.ts`
- `@db` → `src/plugins/fake-api/handlers/`
- `@api-utils` → `src/plugins/fake-api/utils/`

### Important Architectural Rules
- **@layouts module isolation**: Cannot import from `@core` when working in `@layouts`
- **Path alias enforcement**: Must use `@images` instead of `@/assets/images` and `@styles` instead of `@/assets/styles`
- **Layout composable restriction**: `useLayouts()` only allowed in `@layouts` & `@core` directories; use `useThemeConfig()` elsewhere
- **Component imports**: Vuetify components should not be directly imported due to auto-import setup

### Layout System
- Uses `vite-plugin-vue-meta-layouts` with layouts in `src/layouts/`
- Default layout: `default`
- Supports nested layouts and meta-based layout switching
- Configurable navbar, footer, and navigation patterns via theme config

### Internationalization
- Vue I18n integration with locale files in `src/plugins/i18n/locales/`
- RTL support configured in theme config
- Auto-import of i18n composables