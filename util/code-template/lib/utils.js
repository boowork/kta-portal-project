const path = require('path');

function parsePath(input) {
  // Convert both . and / to file path
  const normalizedPath = input.replace(/\./g, '/');
  const parts = normalizedPath.split('/');
  const domain = parts[parts.length - 1];
  
  return {
    path: normalizedPath,
    domain: capitalize(domain),
    domainLower: domain.toLowerCase(),
    parts
  };
}

function capitalize(str) {
  return str.charAt(0).toUpperCase() + str.slice(1);
}

function camelCase(str) {
  return str.charAt(0).toLowerCase() + str.slice(1);
}

function getProjectRoot() {
  // Navigate up from util/project_helper to root
  return path.resolve(__dirname, '../../../');
}

module.exports = {
  parsePath,
  capitalize,
  camelCase,
  getProjectRoot
};