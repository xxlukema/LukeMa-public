# Cypress Configuration

## 1. `/cypress/environments/environment.ts`

This is my creational idea.

## 2. `cypress.config.ts`

N.B.: Three tricks: **Trick 1, Trick 2, Trick 3**

## 3. `/cypress/support/commands.ts` does not allow `import { env } from 'cypress/environments/environment';`

Therefore, **Trick 2** in `/cypress.config.ts` has been created.

## 4. `/cypress/support/commands.ts`

N.B.: Two Tricks: **Trick 1, Trick 2**

## 5. `/cypress/support/e2e.ts`

This file was named as `/cypress/support/index.ts`. But based on
[Cypress 10 Configuration Guide](https://docs.cypress.io/guides/references/configuration#Configuration-File),
the correct default `supportFile` value is `/cypress/support/e2e.ts`

[Most Important! Cypress 10 Configuration Guide]<https://docs.cypress.io/guides/references/configuration#Configuration-File>
