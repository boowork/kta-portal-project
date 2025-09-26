# KTA Helper - Boilerplate Code Generator

## Installation

```bash
cd util/code-template
npm install
```

## Usage

### Generate Backend CRUD

```bash
node index.js backend <domain>
```

Examples:
```bash
node index.js backend product
node index.js backend order.item
node index.js backend admin/config
```

Generates:
- Controllers (GET list, GET by ID, POST, PUT, DELETE)
- Tests for each controller
- API documentation

### Generate Frontend Pages

```bash
node index.js frontend <domain>
```

Examples:
```bash
node index.js frontend product
node index.js frontend order/item
node index.js frontend admin.config
```

Generates:
- List page (`list/index.vue`, `list/composables.ts`)
- View/Edit page (`view/[id].vue`, `view/composables.ts`)

## Path Support

Both `.` and `/` are supported as path separators:
- `user.admin` → `user/admin`
- `auth/refresh` → `auth/refresh`

## Generated Files Location

### Backend
- **Java files**: `/admin/backend/src/main/java/com/kta/portal/admin/feature/api/{path}/`
- **Test files**: `/admin/backend/src/test/java/com/kta/portal/admin/feature/api/{path}/`
- **Docs**: `/admin/backend/docs/api/feature/{path}/`

### Frontend
- **Pages**: `/admin/front/src/pages/apps/{path}/`

## Help

```bash
node index.js --help
```