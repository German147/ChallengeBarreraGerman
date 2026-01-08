# PinApp Automation Challenge – Germán Barrera

End-to-end QA Automation Framework validating real business flows using
API and Web UI integration, with CI-ready architecture and professional
test design principles.

This project focuses on validating **system behavior across layers** instead
of isolated UI checks.

---

## 🧪 Tech Stack

- **Java 21**
- **Maven**
- **TestNG**
- **Selenium 4 (Web UI)**
- **RestAssured 5.x (API)**
- **Appium 2 (Mobile – local only)**
- **Log4j2 (Logging)**
- **Allure Reports**
- **Jenkins (CI)**

> Mobile automation is supported locally but excluded from CI execution.

---

## 🏗️ Architecture Overview

The framework follows a layered and scalable architecture:

### ✅ API Layer
Responsible for all backend communication using RestAssured.

Used for:
- Test data setup
- Backend validations
- Cleanup after tests

Implemented through:
- `TrelloService`
- POJO mapping
- Centralized configuration

---

### ✅ Web Layer (UI Automation)

Implemented using Selenium with Page Object Model (POM):

- Clean separation of UI interactions
- Explicit waits
- Robust locators using `data-testid` and accessibility attributes
- No business logic inside Page Objects

Pages:
- `HomePage`
- `SignInPage`
- `BoardPage`

---

### ✅ Flow Layer

Encapsulates business workflows instead of raw UI steps.

Examples:
- Login and open boards
- Create board from UI
- Delete board from UI

This keeps test methods:
- Clean
- Readable
- Focused on business behavior

---

### ✅ Base Tests & Driver Management

- Centralized driver initialization
- `ThreadLocal<WebDriver>` for parallel-ready execution
- Browser parameterization (Chrome / Edge / Firefox)
- Default browser fallback

---

### ✅ Reporting & Listeners

- Allure TestNG integration
- Automatic screenshots on failure
- Centralized logging with Log4j2

---

## 🔁 End-to-End Testing Strategy

This framework validates **real integration flows** between backend and frontend.

Instead of validating only UI state, it verifies:

- API → UI consistency
- UI → API consistency
- Full business flows across layers

### Example E2E Scenarios

- Create board via API → validate board appears in Web UI
- Create board via Web UI → validate board exists via API
- Delete board via Web UI → validate deletion via API

This approach:
- Reduces UI flakiness
- Increases confidence in system behavior
- Uses API as validation oracle

---

## 🧪 Test Coverage

### ✔ API Tests

- Board creation
- Board retrieval
- Board update
- Negative scenarios:
    - Invalid ID format
    - Non-existing resource (404)
- POJO mapping and clean service abstraction

---

### ✔ Web Tests

- Full Atlassian login flow
- Board visibility validation
- Dynamic scrolling for boards outside viewport
- E2E integration with API data

---

### ✔ Mobile Tests (Local Only)

- Appium 2 with AndroidDriver
- PageFactory implementation
- UiScrollable for dynamic lists
- Validation of board presence inside mobile app

> Mobile tests are excluded from CI execution.

---

## ⚙️ Configuration Management

The framework supports execution in:

- Local environments
- CI environments (Jenkins)

### 🔹 Local Execution

Uses `config.properties` (ignored by Git):

trello.baseUrl=https://api.trello.com

- trello.key=YOUR_KEY
- trello.token=YOUR_TOKEN
- web.username=YOUR_USER
- web.password=YOUR_PASSWORD
- browser=chrome


---

### 🔹 CI Execution (Jenkins)

When `CI=true`, configuration is loaded from environment variables only.

| Variable | Description |
|--------|------------|
| CI | true |
| TRELLO_BASEURL | Trello API base URL |
| TRELLO_KEY | API Key |
| TRELLO_TOKEN | API Token |
| WEB_USERNAME | UI username |
| WEB_PASSWORD | UI password |
| BROWSER | Browser type |

This avoids:
- Local config leaks
- Jenkins intercepting API requests (Jetty / CSRF issues)

---

## 🏷️ Test Groups (TestNG Tags)

Tests are organized using TestNG groups:

- `api` – API tests
- `web` – Web UI tests
- `mobile` – Mobile tests
- `smoke` – Critical flows
- `regression` – Full regression suite
- `negative` – Negative scenarios
- `e2e` – Cross-layer integration flows

---

## ▶️ How to Run

### 🔹 Run Full Suite Locally

```bash
mvn clean test


## Run Only API Tests
mvn clean test -Dgroups=api


Allure Reporting

All tests generate Allure results.

Local Report
allure serve target/allure-results

Jenkins

Allure reports are published automatically by the pipeline.

🚀 CI Pipeline (Jenkins)

The project uses Pipeline as Code (Jenkinsfile):

Pipeline stages:

Checkout

Build

Test execution

Allure report publication

Artifact collection (screenshots)

This ensures:

Reproducible builds

Environment-independent execution

📌 Future Improvements

Planned enhancements:

API response schema validation

Advanced retry and polling strategies for async systems

Parallel execution

Test data service abstraction

Integration with mobile device cloud

👤 Author

Germán Barrera
Senior QA Automation Engineer / SDET Profile