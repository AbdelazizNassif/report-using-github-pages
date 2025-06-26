# Petfinder login & registration automated E2E tests..

## Used tools and libraries:
```bash
- Java-17, maven, Junit5.
- major used libs > Selenium allure report.
- Json reader, Property file reader.
- using .env file for credentials information
- Running tests in docker containers
```

## Design patterns used:
```bash
- Page object model design pattern
- Data-driven design pattern
  - Getting environment variables .env file locally and from pipelines/.env.prod file remotely
  - Getting test data from Json file
```

## How to run the project:
### to run all tests: 
```bash
- Run mvn clean test -Dtest="com.petfinder.tests.regressionE2eTests.*.**"
```
### Open allure report locally from cmd
```bash
Allure serve
```

## How to contribute:
### Before adding to the project
```bash
- Switch to test or main branch
- Pull latest test or main branch: git pull
- Take a copy of test or main branch: git checkout -b new-branch-name main
```
### After finishing follow the below steps:
```bash
- git add .
- git commit -m "Adding related message to your PR"
- git push origin branch-name
- From GitHub: Create PR by Comparing your branch to test or main branch
```
