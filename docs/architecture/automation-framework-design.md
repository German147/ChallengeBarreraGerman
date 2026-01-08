# Software Design Document (SDD)
## QA Automation Framework – API + Web E2E Validation

---

## 1. Purpose

The purpose of this document is to describe the architecture, design decisions,
and technical strategies used in this QA Automation Framework.

The framework is designed to validate **real end-to-end business flows**
across backend APIs and Web UI, ensuring system integration instead of
isolated UI validation.

This document explains:
- Why API and UI automation are combined
- How the framework is structured
- How scalability and stability are achieved
- How CI integration is handled

---

## 2. Design Goals

The main design goals of the framework are:

- ✔ End-to-End system validation (not only UI)
- ✔ Separation of responsibilities
- ✔ Reusability and maintainability
- ✔ CI stability and environment independence
- ✔ Easy extension to new platforms (mobile, new APIs)

---

## 3. High-Level Architecture

The framework follows a layered architecture:

Tests
↓
Flow Layer (Business Workflows)
↓
Page Objects (Web) API Services
↓ ↓
Selenium RestAssured
↓ ↓
Web Application Backend API


Each layer has a single responsibility and does not leak logic to other layers.

---

## 4. API + UI Strategy (Why Both Layers Are Used)

### Problem with UI-Only Automation

UI-only tests:
- Are slower
- Are more fragile
- Do not validate backend behavior
- Often validate only visual state

### Solution: API + UI Integration

The framework uses API automation to:

- Prepare test data
- Validate backend state
- Cleanup data after tests

And UI automation to:

- Validate real user interactions
- Validate frontend behavior

### Benefits

- Faster and more reliable test execution
- Reduced UI flakiness
- True end-to-end system validation

### Example Patterns

- API → UI validation  
  Create resource via API, validate it appears in UI

- UI → API validation  
  Perform action in UI, validate backend reflects the change

---

## 5. Flow Layer Design (Why Flows Are Used)

### Problem with Direct Page Object Usage in Tests

When tests interact directly with Page Objects:
- Tests become long and fragile
- Business intent is hidden by technical steps
- Reuse becomes difficult

### Solution: Flow Layer

Flow classes encapsulate business workflows such as:

- Login and open dashboard
- Create board from UI
- Delete board from UI

### Benefits

- Tests express business intent
- UI changes impact only Flow and Page layers
- Test methods remain short and readable

### Example

Instead of:

home.open()
login.enterUser()
login.enterPassword()
dashboard.clickCreate()

The test uses:

new BoardWebFlow().loginAndCreateBoard(name)


---

## 6. Page Object Model Design

### Responsibilities of Page Objects

Page Objects are responsible only for:

- Locating UI elements
- Performing UI actions
- Handling waits and synchronization

They must NOT contain:
- Assertions
- Business logic
- Cross-page workflows

### Locator Strategy

To improve stability, locators prioritize:

- `data-testid` attributes
- Accessibility attributes (`aria-label`)
- Visible text when stable

Dynamic CSS classes are avoided.

---

## 7. Driver Management and Thread Safety

### Problem

Parallel execution and CI environments require:
- Isolated browser sessions
- No shared WebDriver state

### Solution: ThreadLocal WebDriver

Each test thread receives its own WebDriver instance:

- Prevents cross-test interference
- Enables safe parallel execution
- Improves CI scalability

### Driver Factory Responsibilities

- Browser parameter handling
- Driver initialization
- Driver cleanup
- Centralized configuration

---

## 8. Configuration Management Strategy

### Problem

Local execution and CI environments require different configuration sources.

CI tools may:
- Inject environment variables
- Intercept localhost traffic
- Block local API calls

### Solution

The framework uses:

- `config.properties` for local execution
- Environment variables when `CI=true`

Decision logic:

- If running in CI → read ENV variables only
- Otherwise → read from properties file

### Benefits

- Prevents leaking secrets to repository
- Avoids CI network conflicts
- Allows flexible deployment

---

## 9. CI Integration Design

### Pipeline as Code

The project uses Jenkins Pipeline as Code:

- Build
- Test execution
- Allure report generation
- Artifact archiving

### CI Test Scope

CI executes:
- API tests
- Web tests

Mobile tests are excluded due to:
- Device dependency
- Infrastructure constraints

### Stability Techniques

- Explicit waits
- DOM re-location for dynamic pages
- Polling for backend validation

This reduces flaky failures in CI environments.

---

## 10. Error Handling and Debuggability

### Logging

- Log4j2 is used for structured logs
- API requests and responses are logged when failures occur

### Screenshots

- Automatic screenshot capture on UI failures
- Attached to Allure reports

### Benefits

- Faster root cause analysis
- CI-friendly debugging
- Clear failure diagnostics

---

## 11. Scalability and Extensibility

The framework is designed to support:

- New API services
- Additional web workflows
- Mobile automation extensions
- Parallel execution strategies

Adding new features does not require modifying:
- Existing tests
- Core driver logic
- Configuration management

---

## 12. Risks and Mitigations

| Risk | Mitigation |
|--------|------------|
UI flakiness due to SPA re-render | Explicit waits and re-locating elements |
Backend latency | API polling strategies |
CI environment variability | Environment-based configuration |
Data pollution | API-based cleanup |

---

## 13. Summary

This framework is designed to validate real business behavior across system layers,
not only UI state.

It demonstrates:

- Professional test architecture
- Integration of backend and frontend validation
- CI-ready execution
- Maintainable and scalable design

This approach aligns with SDET and Senior QA Automation engineering practices
used in enterprise-level automation teams.

---
