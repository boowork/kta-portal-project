# KTA Portal Project Helper - Design

## Overview
Node.js CLI tool for generating boilerplate code following KTA Portal development rules.

## Usage

### Backend CRUD Generation
```bash
kta-helper backend user
kta-helper backend user.admin
kta-helper backend auth/refresh
```
Generates full CRUD in `/admin/backend/src/main/java/com/kta/portal/admin/feature/api/{path}/`:
- `GetUsersController.java`
- `GetUserController.java`
- `PostUserController.java`
- `PutUserController.java`
- `DeleteUserController.java`
- Tests for each controller
- API documentation

Path formats supported: `user`, `user.admin`, `user/admin`

### Frontend Page Generation
```bash
kta-helper frontend user
kta-helper frontend user/admin
kta-helper frontend user.admin
```
Generates pages in `/admin/front/src/pages/apps/{path}/`:
- `list/index.vue`
- `list/composables.ts`
- `view/[id].vue`
- `view/composables.ts`

Path formats supported: `user`, `user/admin`, `user.admin`

### Help
```bash
kta-helper -h
kta-helper --help
```

## Architecture

```
project_helper/
├── index.js                   # CLI entry point
├── lib/
│   ├── backend-generator.js   # Backend CRUD generator
│   ├── frontend-generator.js  # Frontend page generator
│   ├── utils.js              # Path parsing utilities
│   └── templates/
│       ├── backend/
│       │   ├── controller.hbs
│       │   └── test.hbs
│       └── frontend/
│           ├── list.vue.hbs
│           ├── view.vue.hbs
│           └── composables.hbs
└── package.json
```

### Path Parsing
```javascript
// utils.js
function parsePath(input) {
  // Convert both . and / to file path
  // user.admin → user/admin
  // user/admin → user/admin
  const path = input.replace(/\./g, '/')
  const parts = path.split('/')
  const domain = parts[parts.length - 1]
  
  return { path, domain, parts }
}
```

## Template Examples

### Backend GET List Controller Template
```java
package com.kta.portal.admin.feature.api.{{path}};

import com.kta.portal.admin.dto.ResponseDto;
import com.kta.portal.admin.feature.repository.{{Domain}}Repository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class Get{{Domain}}sController {
    
    private final Get{{Domain}}sService service;
    
    @GetMapping("/api/{{domain}}s")
    public ResponseEntity<ResponseDto<Get{{Domain}}sPageResponseDto>> getAll{{Domain}}s(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Get{{Domain}}sPageResponseDto result = service.getAll{{Domain}}s(page, size, sortBy, sortDir);
        return ResponseEntity.ok(ResponseDto.success(result));
    }
}

@Service
@RequiredArgsConstructor
class Get{{Domain}}sService {
    
    private final {{Domain}}Repository {{domainLower}}Repository;
    
    public Get{{Domain}}sPageResponseDto getAll{{Domain}}s(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        // TODO: Implement data fetching logic
        // Page<{{Domain}}> {{domainLower}}Page = {{domainLower}}Repository.findAll(pageable);
        
        throw new UnsupportedOperationException("Not implemented yet");
    }
}

@Data
class Get{{Domain}}sHttpResponseDto {
    private Long id;
    // TODO: Add response fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

@Data
class Get{{Domain}}sPageResponseDto {
    private List<Get{{Domain}}sHttpResponseDto> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
}
```

### Backend Test Template
```java
package com.kta.portal.admin.feature.api.{{path}};

import com.kta.portal.admin.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class Get{{Domain}}sControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAll{{Domain}}s_WithoutAuthentication_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/{{domain}}s"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetAll{{Domain}}s_WithUserRole_ShouldReturn200() throws Exception {
        mockMvc.perform(withUserAuth(get("/api/{{domain}}s")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    void testGetAll{{Domain}}s_WithAdminRole_ShouldReturn200() throws Exception {
        mockMvc.perform(withAdminAuth(get("/api/{{domain}}s")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
    
    @Test
    void testGetAll{{Domain}}s_WithPagination() throws Exception {
        mockMvc.perform(withUserAuth(get("/api/{{domain}}s")
                .param("page", "1")
                .param("size", "10")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size").value(10));
    }
}
```

### API Documentation Template
```markdown
# {{METHOD}} /{{domain}}s

## Overview
- **Endpoint**: `{{METHOD}} /api/{{domain}}s`
- **Function**: {{description}}

## Request
### Query Parameters
- `page` (optional): Page number (0-based, default: 0)
- `size` (optional): Page size (default: 20)
- `sortBy` (optional): Field to sort by (default: "id")
- `sortDir` (optional): Sort direction "asc" or "desc" (default: "asc")

## Response
### Success Response (200 OK)
\`\`\`json
{
  "success": true,
  "data": {
    "content": [...],
    "page": 0,
    "size": 20,
    "totalElements": 100,
    "totalPages": 5,
    "first": true,
    "last": false
  }
}
\`\`\`

## cURL Example
\`\`\`bash
curl -X {{METHOD}} \
  -H "DEV_AUTH: 1:admin:관리자" \
  "http://localhost:8080/api/{{domain}}s"
\`\`\`
```

### Frontend List Template
```vue
<template>
  <VCard>
    <VCardTitle>{{Domain}} List</VCardTitle>
    <VDataTableServer
      :headers="headers"
      :items="{{domain}}s"
      :loading="isLoading"
      @update:options="fetch{{Domain}}s"
    >
      <!-- TODO: Customize columns -->
    </VDataTableServer>
  </VCard>
</template>

<script setup lang="ts">
import { use{{Domain}}List } from './composables'

const {
  {{domain}}s,
  headers,
  isLoading,
  fetch{{Domain}}s,
} = use{{Domain}}List()
</script>
```

### Composable Template
```typescript
export function use{{Domain}}List() {
  const {{domain}}s = ref([])
  const isLoading = ref(false)
  
  const headers = [
    { title: 'ID', key: 'id' },
    // TODO: Add columns
  ]
  
  const fetch{{Domain}}s = async () => {
    isLoading.value = true
    try {
      // TODO: API call
      const response = await get{{Domain}}sApi()
      {{domain}}s.value = response.data
    } catch (error) {
      handleError(error)
    } finally {
      isLoading.value = false
    }
  }
  
  onMounted(fetch{{Domain}}s)
  
  return {
    {{domain}}s: readonly({{domain}}s),
    headers,
    isLoading: readonly(isLoading),
    fetch{{Domain}}s,
  }
}
```

## Implementation

### Phase 1: Core Setup
- CLI framework (Commander.js)
- Template engine (Handlebars)
- File generation logic

### Phase 2: Templates
- Backend: Controller + Service + DTO + Test
- Frontend: List/View pages + Composables

### Phase 3: Testing
- Generate sample projects
- Validate against devrule.md

## Dependencies
```json
{
  "commander": "^11.0.0",
  "handlebars": "^4.7.8",
  "fs-extra": "^11.1.0"
}
```