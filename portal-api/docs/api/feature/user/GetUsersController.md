# GET /users

## Overview
- **Endpoint**: `GET /api/users`
- **Function**: Retrieve paginated users list with sorting support

## Request
### Query Parameters
- `page` (optional): Page number (0-based, default: 0)
- `size` (optional): Page size (default: 20)
- `sortBy` (optional): Field to sort by (default: "id")
- `sortDir` (optional): Sort direction "asc" or "desc" (default: "asc")

### Available Sort Fields
- `id`: User ID
- `userid`: User login ID
- `name`: User display name
- `createdAt`: Creation timestamp
- `updatedAt`: Last update timestamp

## Response
### Success Response (200 OK)
```json
{
  "success": true,
  "data": {
    "content": [
      {
        "id": 1,
        "userid": "user123",
        "name": "John Doe",
        "createdAt": "2025-09-22T14:30:00",
        "updatedAt": "2025-09-22T14:30:00"
      }
    ],
    "page": 0,
    "size": 20,
    "totalElements": 100,
    "totalPages": 5,
    "first": true,
    "last": false
  },
  "errors": null
}
```

### Response Fields
- `content`: Array of user objects
- `page`: Current page number (0-based)
- `size`: Number of items per page
- `totalElements`: Total number of users
- `totalPages`: Total number of pages
- `first`: True if this is the first page
- `last`: True if this is the last page

## cURL Examples

### Basic request (first page, default settings)
```bash
curl -X GET \
  -H "DEV_AUTH: 1:admin:관리자" \
  "http://localhost:8080/api/users"
```

### Get second page with 10 items
```bash
curl -X GET \
  -H "DEV_AUTH: 1:admin:관리자" \
  "http://localhost:8080/api/users?page=1&size=10"
```

### Sort by name in descending order
```bash
curl -X GET \
  -H "DEV_AUTH: 1:admin:관리자" \
  "http://localhost:8080/api/users?sortBy=name&sortDir=desc"
```

### Complex pagination with sorting
```bash
curl -X GET \
  -H "DEV_AUTH: 1:admin:관리자" \
  "http://localhost:8080/api/users?page=2&size=15&sortBy=createdAt&sortDir=desc"
```