# CLAUDE.md
IMPORTANT: UTF8
IMPORTANT: read first => current version = versions/headver.txt, versions/history/{current version}.md, versions/todo/{current version}.md
IMPORTANT: you do not write like this "🤖 Generated with Claude Code".

# KTA Portal Project - Architecture Overview

## Project Structure
```
kta-portal-project/
├── admin/
│   ├── front/     # Vue.js + Vuetify admin interface
│   └── backend/   # Spring Boot REST API
├── school/
│   └── front/     # Vue.js + Vuetify school interface
├── portal/
│   └── front/     # Vue.js + Vuetify portal interface
└── versions/      # Version management system
```

## Technology Stack

### Frontend (All Components)
- **Framework**: Vue.js 3.5.14 with Composition API
- **UI Library**: Vuetify 3.8.5 (Material Design)
- **Build Tool**: Vite 6.3.5
- **Language**: TypeScript 5.8.3
- **State Management**: Pinia 3.0.2
- **Routing**: Vue Router 4.5.1
- **Package Manager**: pnpm 10.11.0

### Backend (Admin Only)
- **Framework**: Spring Boot 4.0.0-SNAPSHOT
- **Language**: Java 25
- **Database**: PostgreSQL
- **Build Tool**: Gradle
- **Security**: Spring Security
- **Additional**: Lombok, Actuator, Data JDBC

## Component Purposes

### Admin Component
- **Frontend**: Full-featured admin dashboard with authentication
- **Backend**: Complete Spring Boot API with database integration
- **Purpose**: Administrative interface for system management

### School Component  
- **Frontend**: School-specific portal interface
- **Backend**: Not implemented (frontend only)
- **Purpose**: Educational institution interface

### Portal Component
- **Frontend**: Main portal interface
- **Backend**: Not implemented (frontend only)  
- **Purpose**: General user portal

## Common Frontend Features
All frontend applications share identical:
- **Template**: Sneat Vuetify Admin Template v2.2.0
- **Authentication**: JWT-based with route guards
- **Development Setup**: Docker support (dev/prod)
- **Code Quality**: ESLint, TypeScript, Prettier
- **Icons**: Multiple icon sets (Material Design, Font Awesome, Tabler)
- **Charts**: ApexCharts, Chart.js integration
- **Rich Text**: TipTap editor
- **Maps**: Mapbox GL support

## Development Scripts (Frontend)
```bash
npm run dev        # Development server
npm run build      # Production build  
npm run preview    # Preview build
npm run typecheck  # TypeScript validation
npm run lint       # Code linting
```

## Docker Configuration
All frontend components support:
- Development: `docker-compose.dev.yml` (port 5173)
- Production: `docker-compose.prod.yml` + nginx

## Design Principles
- **KISS**: Keep It Simple, Stupid
- **DRY**: Don't Repeat Yourself  
- **YAGNI**: You Aren't Gonna Need It

