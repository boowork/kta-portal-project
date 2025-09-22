# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Structure

This repository contains two versions of the Sneat Vuetify Vue.js admin template:

- **`full-version/`** - Complete admin template with all features, components, and demo pages
- **`starter-kit/`** - Minimal starter template with core framework only

Both versions share identical commands and architectural patterns. Choose the appropriate directory based on development needs.

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

### Core Framework Stack
- **Vue 3** with Composition API and `<script setup>`
- **Vuetify 3** for Material Design UI components
- **Pinia** for state management
- **Vue Router** with file-based routing (unplugin-vue-router)
- **TypeScript** throughout
- **Vite** for build tooling and development server

### Modular Architecture

**Core Framework (`src/@core/`):**
- Reusable components, utilities, and stores
- Framework-level functionality shared across projects
- Cannot be imported from `@layouts` directory (enforced rule)

**Layout System (`src/@layouts/`):**
- Layout components, stores, and configuration
- Uses `vite-plugin-vue-meta-layouts` with layouts in `src/layouts/`
- Isolated module - cannot import from `@core`

**Application Layer:**
- `src/components/` - Project-specific components
- `src/views/` - Page-level Vue components  
- `src/pages/` - File-based routing pages
- `src/plugins/` - Vue plugins and configuration

### Key Architectural Rules

1. **Module Isolation**: `@layouts` cannot import from `@core`
2. **Path Aliases**: Must use `@images` instead of `@/assets/images` and `@styles` instead of `@/assets/styles`
3. **Layout Composables**: `useLayouts()` only allowed in `@layouts` & `@core`; use `useThemeConfig()` elsewhere
4. **Auto-imports**: Vuetify components auto-imported - do not manually import

### Path Aliases
- `@` → `src/`
- `@core` → `src/@core`
- `@layouts` → `src/@layouts`
- `@images` → `src/assets/images/`
- `@styles` → `src/assets/styles/`
- `@themeConfig` → `themeConfig.ts`
- `@db` → `src/plugins/fake-api/handlers/` (full-version only)
- `@api-utils` → `src/plugins/fake-api/utils/` (full-version only)

### Development Tools & Features

**Auto-imports:**
- Vue composables and utilities
- Component auto-registration from specified directories
- i18n composables

**Styling & Theming:**
- SCSS with Vuetify variables in `src/assets/styles/variables/_vuetify.scss`
- Theme configuration in `themeConfig.ts`
- Multi-language and RTL support
- Skin system for visual themes

**Mock API (full-version):**
- MSW (Mock Service Worker) for API mocking
- Fake API handlers in `src/plugins/fake-api/handlers/`

### Package Manager
Uses **pnpm** - ensure you have pnpm installed and use `pnpm install` instead of `npm install`

### Version Differences

**Starter Kit:**
- Minimal setup with core framework only
- Basic routing and layout structure
- Essential plugins and utilities

**Full Version:**
- Complete admin template with demo pages
- Advanced components and features
- Comprehensive fake API with MSW
- Full feature showcase and examples