const fs = require('fs-extra');
const path = require('path');
const Handlebars = require('handlebars');
const { parsePath, getProjectRoot, capitalize, camelCase } = require('./utils');
const chalk = require('chalk');

class FrontendGenerator {
  async generate(inputPath) {
    const pathInfo = parsePath(inputPath);
    const baseDir = path.join(
      getProjectRoot(),
      'admin/frontend/src/pages/apps',
      pathInfo.path
    );

    // Create directories
    const listDir = path.join(baseDir, 'list');
    const viewDir = path.join(baseDir, 'view');

    await fs.ensureDir(listDir);
    await fs.ensureDir(viewDir);

    // Generate list page
    await this.generateListPage(listDir, pathInfo);
    await this.generateListComposable(listDir, pathInfo);

    // Generate view page
    await this.generateViewPage(viewDir, pathInfo);
    await this.generateViewComposable(viewDir, pathInfo);
  }

  async generateListPage(dir, pathInfo) {
    const template = await fs.readFile(
      path.join(__dirname, 'templates/frontend/list.vue.hbs'),
      'utf-8'
    );
    const compiled = Handlebars.compile(template);

    const data = {
      Domain: pathInfo.domain,
      domain: pathInfo.domainLower,
      domains: pathInfo.domainLower + 's'
    };

    const content = compiled(data);
    const filePath = path.join(dir, 'index.vue');

    await fs.writeFile(filePath, content);
    console.log(chalk.gray(`  Created: list/index.vue`));
  }

  async generateListComposable(dir, pathInfo) {
    const template = await fs.readFile(
      path.join(__dirname, 'templates/frontend/list-composable.hbs'),
      'utf-8'
    );
    const compiled = Handlebars.compile(template);

    const data = {
      Domain: pathInfo.domain,
      domain: pathInfo.domainLower,
      domains: pathInfo.domainLower + 's'
    };

    const content = compiled(data);
    const filePath = path.join(dir, 'composables.ts');

    await fs.writeFile(filePath, content);
    console.log(chalk.gray(`  Created: list/composables.ts`));
  }

  async generateViewPage(dir, pathInfo) {
    const template = await fs.readFile(
      path.join(__dirname, 'templates/frontend/view.vue.hbs'),
      'utf-8'
    );
    const compiled = Handlebars.compile(template);

    const data = {
      Domain: pathInfo.domain,
      domain: pathInfo.domainLower
    };

    const content = compiled(data);
    const filePath = path.join(dir, '[id].vue');

    await fs.writeFile(filePath, content);
    console.log(chalk.gray(`  Created: view/[id].vue`));
  }

  async generateViewComposable(dir, pathInfo) {
    const template = await fs.readFile(
      path.join(__dirname, 'templates/frontend/view-composable.hbs'),
      'utf-8'
    );
    const compiled = Handlebars.compile(template);

    const data = {
      Domain: pathInfo.domain,
      domain: pathInfo.domainLower
    };

    const content = compiled(data);
    const filePath = path.join(dir, 'composables.ts');

    await fs.writeFile(filePath, content);
    console.log(chalk.gray(`  Created: view/composables.ts`));
  }
}

module.exports = new FrontendGenerator();