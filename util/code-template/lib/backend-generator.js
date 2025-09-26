const fs = require('fs-extra');
const path = require('path');
const Handlebars = require('handlebars');
const { parsePath, getProjectRoot, capitalize, camelCase } = require('./utils');
const chalk = require('chalk');

class BackendGenerator {
  constructor() {
    this.methods = ['GET', 'POST', 'PUT', 'DELETE'];
    this.controllerTypes = [
      { method: 'GET', plural: true, name: 'GetAll' },
      { method: 'GET', plural: false, name: 'GetById' },
      { method: 'POST', plural: false, name: 'Create' },
      { method: 'PUT', plural: false, name: 'Update' },
      { method: 'DELETE', plural: false, name: 'Delete' }
    ];
  }

  async generate(inputPath) {
    const pathInfo = parsePath(inputPath);
    const baseDir = path.join(
      getProjectRoot(),
      'admin/backend/src/main/java/com/kta/portal/admin/feature/api',
      pathInfo.path
    );
    
    const testDir = path.join(
      getProjectRoot(),
      'admin/backend/src/test/java/com/kta/portal/admin/feature/api',
      pathInfo.path
    );
    
    const docsDir = path.join(
      getProjectRoot(),
      'admin/backend/docs/api/feature',
      pathInfo.path
    );

    // Create directories
    await fs.ensureDir(baseDir);
    await fs.ensureDir(testDir);
    await fs.ensureDir(docsDir);

    // Generate controllers
    for (const controller of this.controllerTypes) {
      await this.generateController(baseDir, pathInfo, controller);
      await this.generateTest(testDir, pathInfo, controller);
      await this.generateDocs(docsDir, pathInfo, controller);
    }
  }

  async generateController(baseDir, pathInfo, controller) {
    const template = await this.getControllerTemplate(controller);
    const compiled = Handlebars.compile(template);
    
    const controllerName = controller.plural ? 
      `Get${pathInfo.domain}sController` : 
      `${controller.name}${pathInfo.domain}Controller`;
    
    const data = {
      package: pathInfo.path.replace(/\//g, '.'),
      Domain: pathInfo.domain,
      domain: pathInfo.domainLower,
      domains: pathInfo.domainLower + 's',
      method: controller.method,
      methodName: camelCase(controller.name) + pathInfo.domain,
      controllerName,
      serviceName: controllerName.replace('Controller', 'Service'),
      isPlural: controller.plural,
      isPaginated: controller.plural && controller.method === 'GET'
    };
    
    const content = compiled(data);
    const filePath = path.join(baseDir, `${controllerName}.java`);
    
    await fs.writeFile(filePath, content);
    console.log(chalk.gray(`  Created: ${controllerName}.java`));
  }

  async generateTest(testDir, pathInfo, controller) {
    const template = await this.getTestTemplate();
    const compiled = Handlebars.compile(template);
    
    const controllerName = controller.plural ? 
      `Get${pathInfo.domain}sController` : 
      `${controller.name}${pathInfo.domain}Controller`;
    
    const data = {
      package: pathInfo.path.replace(/\//g, '.'),
      Domain: pathInfo.domain,
      domain: pathInfo.domainLower,
      domains: pathInfo.domainLower + 's',
      controllerName,
      endpoint: controller.plural ? `/${pathInfo.domainLower}s` : `/${pathInfo.domainLower}`,
      method: controller.method.toLowerCase()
    };
    
    const content = compiled(data);
    const filePath = path.join(testDir, `${controllerName}Test.java`);
    
    await fs.writeFile(filePath, content);
    console.log(chalk.gray(`  Created: ${controllerName}Test.java`));
  }

  async generateDocs(docsDir, pathInfo, controller) {
    const template = await this.getDocsTemplate();
    const compiled = Handlebars.compile(template);
    
    const controllerName = controller.plural ? 
      `Get${pathInfo.domain}sController` : 
      `${controller.name}${pathInfo.domain}Controller`;
    
    const data = {
      METHOD: controller.method,
      domain: pathInfo.domainLower,
      domains: pathInfo.domainLower + 's',
      endpoint: controller.plural ? `/${pathInfo.domainLower}s` : `/${pathInfo.domainLower}`,
      description: this.getDescription(controller),
      isPaginated: controller.plural && controller.method === 'GET'
    };
    
    const content = compiled(data);
    const filePath = path.join(docsDir, `${controllerName}.md`);
    
    await fs.writeFile(filePath, content);
    console.log(chalk.gray(`  Created: ${controllerName}.md`));
  }

  getDescription(controller) {
    if (controller.plural) return `Retrieve paginated ${controller.name.toLowerCase()} list`;
    switch (controller.name) {
      case 'GetById': return 'Retrieve single item by ID';
      case 'Create': return 'Create new item';
      case 'Update': return 'Update existing item';
      case 'Delete': return 'Delete item by ID';
      default: return 'Perform operation';
    }
  }

  async getControllerTemplate(controller) {
    const templateFile = controller.plural && controller.method === 'GET' ? 
      'get-list-controller.hbs' : 'simple-controller.hbs';
    const templatePath = path.join(__dirname, 'templates/backend', templateFile);
    
    if (!await fs.pathExists(templatePath)) {
      // Fallback to simple template
      const fallbackPath = path.join(__dirname, 'templates/backend/simple-controller.hbs');
      if (await fs.pathExists(fallbackPath)) {
        return await fs.readFile(fallbackPath, 'utf-8');
      }
      throw new Error(`Template not found: ${templatePath}`);
    }
    
    return await fs.readFile(templatePath, 'utf-8');
  }

  async getTestTemplate() {
    const templatePath = path.join(__dirname, 'templates/backend/test.hbs');
    if (!await fs.pathExists(templatePath)) {
      throw new Error(`Template not found: ${templatePath}`);
    }
    return await fs.readFile(templatePath, 'utf-8');
  }

  async getDocsTemplate() {
    const templatePath = path.join(__dirname, 'templates/backend/docs.hbs');
    if (!await fs.pathExists(templatePath)) {
      throw new Error(`Template not found: ${templatePath}`);
    }
    return await fs.readFile(templatePath, 'utf-8');
  }
}

module.exports = new BackendGenerator();