# login & registration automated E2E tests..

## Used tools and libraries:
```bash
- Java-17, maven, testNG.
- major used libs > Selenium allure report.
- Json reader, Property file reader.
- using .env file for credentials information
- Running tests in docker containers
```

## A short explanation of the provided solution:
```bash
- Page object model design pattern is used
- Data-driven design pattern
  - Getting environment variables .env file locally and from pipelines/.env.prod file remotely
  - data provider is used to validate invaid login cases
  - Getting test data from Json file
- automated tests are done on registration and login test cases
- for registration > Create utiliy class to create unique email each new run
- Github actions is used to run the pipeline 
- Allure reporting is used to keep logs and test results and the report is automatically deployed on github pages after the pipeline https://abdelaziznassif.github.io/abdelaziz-samir-ca-challenge/
```

## How to run the project:
### to run all tests: 
```bash
- Run mvn clean test -Dtest="com.petfinder.tests.regressionE2eTests.*.**"
```
### Open allure report locally or from github page
```bash
Allure serve
Find the report here: https://abdelaziznassif.github.io/abdelaziz-samir-ca-challenge/
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
