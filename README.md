# Saucedemo UI + API Automation Framework

A beginner-friendly but complete test automation framework built with **Java, Selenium WebDriver, TestNG, Maven, Page Object Model, Apache POI and REST Assured**.

- **UI application under test:** [Saucedemo](https://www.saucedemo.com/) (public demo e-commerce site)
- **API under test:** [JSONPlaceholder](https://jsonplaceholder.typicode.com/) (free public REST API)

## Tech stack

| Area | Tool |
|---|---|
| Language | Java 17 |
| UI automation | Selenium WebDriver 4 |
| API automation | REST Assured |
| Test runner | TestNG |
| Build tool | Maven |
| Design pattern | Page Object Model (POM) |
| Data-driven testing | Apache POI (Excel) + TestNG DataProvider |
| Reporting | Extent Reports (HTML) with screenshots on failure |
| CI | GitHub Actions (headless Chrome) |

## Project structure

```
src
├── main
│   ├── java/com/qa/framework
│   │   ├── config      ConfigReader (reads config.properties)
│   │   ├── driver      DriverFactory (Chrome / Firefox / Edge, ThreadLocal)
│   │   ├── pages       BasePage, LoginPage, ProductsPage, CartPage, CheckoutPage
│   │   └── utils       ExcelUtils (Apache POI), ScreenshotUtils
│   └── resources       config.properties
└── test
    ├── java/com/qa/framework
    │   ├── base        BaseTest (UI), ApiBaseTest (API)
    │   ├── listeners   TestListener (Extent report + screenshot on failure)
    │   ├── utils       ExtentManager
    │   └── tests
    │       ├── ui      LoginTests, ProductTests, CheckoutTests
    │       └── api     PostsApiTests, UsersApiTests
    └── resources/testdata   TestData.xlsx (LoginData, PostData sheets)
testng.xml               Suite file
.github/workflows/ci.yml GitHub Actions pipeline
```

## Test coverage

**UI (Saucedemo)**
- Data-driven login test with 6 scenarios read from Excel (valid, locked out, wrong password, invalid user, empty username, empty password)
- Product list count, add to cart (single and multiple), sort by price and name, logout
- End-to-end purchase with item total validation, remove item from cart, checkout validation error

**API (JSONPlaceholder)**
- GET all / single / filtered by query param / invalid id (404)
- POST (data-driven from Excel), PUT, PATCH, DELETE
- Validations: status code, response body, content type, response time

## How to run

Prerequisites: JDK 17+, Maven 3.8+, and Chrome installed. Selenium Manager downloads the browser driver automatically.

```bash
# Run everything (UI + API)
mvn clean test

# Run headless
mvn clean test -Dheadless=true

# Use a different browser
mvn clean test -Dbrowser=firefox

# Run only one test class
mvn clean test -Dtest=PostsApiTests
```

After the run:
- HTML report: `reports/ExtentReport.html`
- Failure screenshots: `screenshots/`
- TestNG/Surefire reports: `target/surefire-reports/`

## Configuration

Edit `src/main/resources/config.properties` (URLs, browser, headless, wait time). Any value can also be overridden from the command line with `-Dkey=value`.

## Author

Pooja Vilaskar, QA / Software Testing
