# Admin Component

KTA Portal admin interface component with full-stack architecture: Spring Boot backend + Vue.js frontend.

## Components

### Backend (Spring Boot)
- **Location**: `admin/backend/`
- **Port**: 8080 (default)
- **Tech Stack**: Spring Boot 4.0.0, Java 25, PostgreSQL
- **Features**: JWT auth, User management API, Spring Security

### Frontend (Vue.js)
- **Location**: `admin/front/`
- **Port**: 5173 (dev), 5050 (preview)
- **Tech Stack**: Vue.js 3.5.14, Vuetify 3.8.5, TypeScript 5.8.3
- **UI Template**: Sneat Vuetify Admin Template v2.2.0

## Quick Start

### Backend Development
```bash
cd admin/backend
./gradlew bootRun
```

### Frontend Development
```bash
cd admin/front
pnpm install
pnpm dev
```

## API Documentation
Complete API docs: [`backend/docs/api/index.md`](backend/docs/api/index.md)

### Key API Endpoints
- **Auth**: `/api/auth/login`, `/api/auth/refresh`, `/api/auth/logout`
- **User Management**: `/api/users` (CRUD operations with pagination)
- **Base URL**: `http://localhost:8080`

### API Features (v0.2539.11)
- **Pagination**: Spring Data JDBC with configurable page size and sorting
- **Role-free Architecture**: Simplified authorization for enhanced development experience
- **DEV_AUTH Support**: Development-friendly authentication bypass
- **Comprehensive Testing**: TestContainers with real database integration

### Authentication
- **Production**: JWT Bearer Token authentication
  - Access Token (24h) + Refresh Token (30d)
  - Service identifier: `kta-portal-admin`
- **Development**: DEV_AUTH header support for simplified development
  - Header format: `DEV_AUTH: {id}:{userid}:{name}`
  - Active in test/dev/local profiles only

## Development Scripts

### Frontend
```bash
pnpm dev        # Start dev server
pnpm build      # Production build
pnpm preview    # Preview build
pnpm typecheck  # TypeScript validation
pnpm lint       # Code linting
```

### Backend
```bash
./gradlew bootRun     # Start dev server
./gradlew build       # Build project
./gradlew test        # Run tests
```

## Docker Support

### Development Environment
```bash
cd admin/front
docker-compose -f docker-compose.dev.yml up
```

### Production Environment
```bash
cd admin/front
docker-compose -f docker-compose.prod.yml up
```

## Key Features

### Frontend Features (v0.2539.11)
- **State Management**: Pinia 3.0.2 with TypeScript integration
- **Routing**: Vue Router 4.5.1 with route guards
- **UI Components**: Vuetify 3.8.5 (Material Design)
- **Architecture**: Page-specific composables with structured return objects
- **Type Safety**: Comprehensive TypeScript coverage with proper interface definitions
- **Pagination**: Integrated frontend pagination matching backend API
- **Icons**: Material Design, Font Awesome, Tabler
- **Charts**: ApexCharts, Chart.js
- **Editor**: TipTap rich text editor

### Backend Features
- **Security**: Spring Security with JWT
- **Database**: Spring Data JDBC (no JPA)
- **Build Tool**: Gradle
- **Health Check**: Spring Boot Actuator
- **Dev Tools**: Lombok

## Directory Structure

```
admin/
├── backend/                 # Spring Boot backend
│   ├── src/main/java/      # Main source code
│   ├── src/test/java/      # Test code
│   ├── docs/api/           # API documentation
│   └── build.gradle        # Build configuration
└── front/                  # Vue.js frontend
    ├── src/                # Source code
    ├── public/             # Static files
    ├── package.json        # Package configuration
    └── vite.config.ts      # Vite configuration
```

## Development Guidelines
See project root [`CLAUDE.md`](../CLAUDE.md) for overall architecture and development principles.

### Core Principles
- **KISS**: Keep It Simple, Stupid
- **DRY**: Don't Repeat Yourself
- **YAGNI**: You Aren't Gonna Need It

### Backend Development Rules
**MUST** refer to [`backend/devrule.md`](backend/devrule.md) when developing backend.

- **Architecture**: Single Class All Component pattern
- **Testing**: TestContainers + real DB, no mocks
- **Data Access**: Spring Data JDBC (no JPA)
- **Workflow**: HeadVer-based automated development cycle

## Related Documentation
- [Project Architecture](../CLAUDE.md)
- [API Documentation Index](../docs/api/index.md)
- [Backend API Details](backend/docs/api/index.md)