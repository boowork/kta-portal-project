#!/usr/bin/env node

const { program } = require('commander');
const backendGenerator = require('./lib/backend-generator');
const frontendGenerator = require('./lib/frontend-generator');
const chalk = require('chalk');

program
  .name('kta-helper')
  .description('KTA Portal boilerplate code generator')
  .version('1.0.0');

program
  .command('backend <path>')
  .description('Generate backend CRUD controllers')
  .action(async (path) => {
    try {
      console.log(chalk.blue('Generating backend CRUD for:'), path);
      await backendGenerator.generate(path);
      console.log(chalk.green('✓ Backend generation complete'));
    } catch (error) {
      console.error(chalk.red('Error:'), error.message);
      process.exit(1);
    }
  });

program
  .command('frontend <path>')
  .description('Generate frontend pages')
  .action(async (path) => {
    try {
      console.log(chalk.blue('Generating frontend pages for:'), path);
      await frontendGenerator.generate(path);
      console.log(chalk.green('✓ Frontend generation complete'));
    } catch (error) {
      console.error(chalk.red('Error:'), error.message);
      process.exit(1);
    }
  });

program.parse(process.argv);

// Show help if no command provided
if (!process.argv.slice(2).length) {
  program.outputHelp();
}