#!/bin/bash
# Progressive Development Initialization Script
# Creates new HeadVer branch with worktree for development

set -e  # Exit on any error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print colored output
log_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if we're in a git repository
if ! git rev-parse --git-dir > /dev/null 2>&1; then
    log_error "Not in a git repository"
    exit 1
fi

# Configuration
# Extract project name from git remote URL
if git remote get-url origin > /dev/null 2>&1; then
    REMOTE_URL=$(git remote get-url origin)
    PROJECT_NAME=$(basename "${REMOTE_URL}" .git)
    log_info "Detected project name: ${PROJECT_NAME}"
else
    # Fallback to directory name if no remote
    PROJECT_NAME=$(basename "$(pwd)")
    log_warn "No remote origin found, using directory name: ${PROJECT_NAME}"
fi

WORKTREE_BASE="${HOME}/.claude/prompt_helper/worktree/${PROJECT_NAME}"
MAIN_BRANCH="main"

log_info "Starting progressive development initialization..."

# Step 1: Git stash and checkout main
log_info "Step 1: Preparing git workspace"
if git diff --quiet && git diff --staged --quiet; then
    log_info "Working directory is clean"
else
    log_warn "Stashing current changes"
    git stash push -m "Auto-stash before new development cycle $(date)"
fi

# Checkout main branch
log_info "Checking out ${MAIN_BRANCH} branch"
if git show-ref --verify --quiet refs/heads/${MAIN_BRANCH}; then
    git checkout ${MAIN_BRANCH}
    # Only try to pull if remote origin exists
    if git remote get-url origin > /dev/null 2>&1; then
        git pull origin ${MAIN_BRANCH} || log_warn "Could not pull from origin"
    else
        log_info "No remote origin configured, skipping pull"
    fi
else
    log_error "Main branch '${MAIN_BRANCH}' does not exist"
    exit 1
fi

# Step 2: Increment HeadVer
log_info "Step 2: Incrementing HeadVer"
cd versions
CURRENT_HEADVER=$(python3 header.py current | grep "Current version:" | cut -d' ' -f3)
log_info "Current version: ${CURRENT_HEADVER}"

# Use simple default description (no user input needed)
DESCRIPTION="Development cycle"
NEW_HEADVER=$(python3 header.py next "${DESCRIPTION}")
NEW_VERSION=$(echo "${NEW_HEADVER}" | grep "Generated version:" | cut -d' ' -f3)

if [ -z "${NEW_VERSION}" ]; then
    log_error "Failed to generate new HeadVer"
    exit 1
fi

log_info "New version: ${NEW_VERSION}"

# Commit the headver.txt changes to main branch
log_info "Committing HeadVer changes"
git add headver.txt
if ! git diff --staged --quiet; then
    git commit -m "${NEW_VERSION}: Update HeadVer

- Generated new version: ${NEW_VERSION}
- Description: ${DESCRIPTION}
- Previous version: ${CURRENT_HEADVER}
"
else
    log_info "No HeadVer changes to commit"
fi

cd ..

# Step 3: Create worktree
log_info "Step 3: Creating worktree"
WORKTREE_PATH="${WORKTREE_BASE}/${NEW_VERSION}"

# Create worktree base directory if it doesn't exist
mkdir -p "${WORKTREE_BASE}"

# Remove existing worktree if it exists (this also removes the branch if it only exists in worktree)
if [ -d "${WORKTREE_PATH}" ]; then
    log_warn "Removing existing worktree at ${WORKTREE_PATH}"
    git worktree remove "${WORKTREE_PATH}" --force || true
fi

# Also remove the branch if it exists (since we're creating a fresh development cycle)
if git show-ref --verify --quiet refs/heads/"${NEW_VERSION}"; then
    log_warn "Removing existing branch ${NEW_VERSION}"
    git branch -D "${NEW_VERSION}" || true
fi

# Create new worktree
log_info "Creating worktree at ${WORKTREE_PATH}"
# Use HEAD instead of branch name to avoid conflict when already on main
git worktree add "${WORKTREE_PATH}" HEAD

# Step 4: Switch to worktree and create branch
log_info "Step 4: Creating development branch"
cd "${WORKTREE_PATH}"

# Create and checkout new branch
git checkout -b "${NEW_VERSION}"

# Step 5: Initial commit
log_info "Step 5: Creating initial commit"
git add .
if git diff --staged --quiet; then
    log_info "No changes to commit for initial setup"
else
    git commit -m "${NEW_VERSION}: Initial development setup

- HeadVer: ${NEW_VERSION}
- Description: ${DESCRIPTION}
- Worktree: ${WORKTREE_PATH}
- Created: $(date)
"
fi

# Step 6: Create documentation structure
log_info "Step 6: Creating documentation structure"

# Create empty documentation files following the pattern
DOC_DIR="versions/history"
mkdir -p "${DOC_DIR}"

# Create HeadVer documentation file (simple version naming)
DOC_FILE="${DOC_DIR}/${NEW_VERSION}.md"
cat > "${DOC_FILE}" << EOF
## 개발 목표
[이 버전에서 구현할 기능 요약]

## 구현 상세
[TODO]


# Design Rule
KISS, DRY, YAGNI

# HeadVer ${NEW_VERSION}: ${DESCRIPTION}

- **Developer(s)**: boojongmin
- **Started**: $(date +%Y-%m-%d) (Week $(date +%U))
- **Completed**: [To be filled]
- **Status**: Development
- **Dependencies**: ${CURRENT_HEADVER}
- **Head Category**: [To be determined]
- **Build**: $(echo ${NEW_VERSION} | cut -d. -f3)

## Current Development Environment
- **HeadVer**: ${NEW_VERSION}
- **Worktree**: ${WORKTREE_PATH}
- **Branch**: ${NEW_VERSION}
- **Description**: ${DESCRIPTION}
EOF


DOC_DIR="versions/history"
mkdir -p "${DOC_DIR}"

# Create HeadVer documentation file (simple version naming)
mkdir -p versions/todo
touch "versions/todo/${NEW_VERSION}.md"


# Copy previous version tests and update current link
log_info "Setting up test environment"
if [ -d "test/headver/${CURRENT_HEADVER}" ]; then
    cp -r "test/headver/${CURRENT_HEADVER}" "test/headver/${NEW_VERSION}"
    log_info "Copied tests from ${CURRENT_HEADVER} to ${NEW_VERSION}"
else
    mkdir -p test/headver/${NEW_VERSION}/{unit,integration,performance}
    log_info "Created new test directories for ${NEW_VERSION}"
fi

# Update current link
cd test
rm -f current
ln -sf "headver/${NEW_VERSION}" current
cd ..

# Setup complete - no additional files needed

# Final commit with documentation
git add .
if ! git diff --staged --quiet; then
    git commit -m "${NEW_VERSION}: Add documentation structure

- Created HeadVer documentation file
- Added development notes
- Set up directory structure for development
"
else
    log_info "No documentation changes to commit"
fi

# Summary
log_info "========================================="
log_info "Progressive Development Setup Complete!"
log_info "========================================="
log_info "New HeadVer: ${NEW_VERSION}"
log_info "Description: ${DESCRIPTION}"
log_info "Worktree: ${WORKTREE_PATH}"
log_info "Branch: ${NEW_VERSION}"
log_info "Documentation: ${DOC_FILE}"
log_info ""
log_info "Next steps:"
log_info "1. Start development in: ${WORKTREE_PATH}"
log_info "2. Edit documentation: ${DOC_FILE}"
log_info "3. Update TODO section in: ${DOC_FILE}"
log_info ""
log_info "To switch to development environment:"
log_info "cd ${WORKTREE_PATH}"
log_info ""
log_info "Opening worktree in VS Code..."
code "${WORKTREE_PATH}"