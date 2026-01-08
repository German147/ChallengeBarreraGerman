# Test Plan
## QA Automation Framework – API + Web E2E Validation

---

## 1. Introduction

This Test Plan describes the testing approach, scope, resources, schedule,
and acceptance criteria for validating the Trello-based automation challenge.

The goal is to ensure that critical business workflows function correctly
across backend APIs and Web UI, with stable execution in both local and CI
environments.

---

## 2. Objectives

The main objectives of this test plan are:

- Validate critical business workflows end-to-end
- Ensure data consistency between API and Web UI
- Detect integration issues early
- Provide reliable regression coverage in CI

Secondary objectives:

- Validate API behavior independently
- Validate UI behavior for core user flows

---

## 3. Test Scope

### 3.1 In Scope

#### API Testing

- Create board
- Get board by ID
- Update board name
- Validate negative responses:
    - Invalid board ID format (400)
    - Non-existing board (404)

#### Web UI Testing

- User authentication (login)
- Board visibility in dashboard
- Board creation from UI
- Board deletion from UI

#### End-to-End Integration

- API create → UI validate
- UI create → API validate
- API create → UI delete → API validate deletion

---

### 3.2 Out of Scope

The following areas are excluded:

- UI visual design validation
- Performance and load testing
- Security and penetration testing
- Accessibility compliance validation
- Cross-browser compatibility testing

---

## 4. Test Items

Components under test:

- Trello REST API endpoints related to boards
- Web UI dashboard and board management features
- Automation framework components:
    - API Service layer
    - Web Page Objects
    - Flow layer
    - Driver management

---

## 5. Test Environment

### 5.1 Local Environment

Used for:

- Development
- Debugging
- Full automation execution

Configuration:

- `config.properties` file
- Local browser (Chrome by default)

---

### 5.2 CI Environment (Jenkins)

Used for:

- Regression validation
- Integration checks

Configuration:

- Environment variables only
- No local configuration files

Execution scope:

- API tests
- Web tests

Mobile tests are excluded from CI.

---

## 6. Test Data Strategy

- Test data is created dynamically using API calls
- No dependency on static or shared data
- Each test is responsible for:
    - Creating its own data
    - Cleaning up after execution

Benefits:

- Tests can run in parallel
- No environment pollution
- Predictable results

---

## 7. Test Approach

### 7.1 API Testing

API tests validate:

- HTTP status codes
- Response payload correctness
- Data persistence

API is also used for:

- Data setup for UI tests
- Validation of UI actions
- Cleanup of test data

---

### 7.2 Web UI Testing

UI automation validates:

- User interaction flows
- Correct navigation
- Presence of business data

UI tests focus only on:

- High-value business scenarios
- Not on visual or cosmetic validations

---

### 7.3 Integration Testing

Integration tests validate:

- Backend changes reflected in UI
- UI actions persisted in backend

These tests provide the highest business confidence and are prioritized.

---

## 8. Entry and Exit Criteria

### 8.1 Entry Criteria

Testing can start when:

- Application environment is available
- API credentials are valid
- UI access is functional
- Automation framework builds successfully

---

### 8.2 Exit Criteria

Testing is considered complete when:

- All critical API tests pass
- All critical Web tests pass
- All E2E integration tests pass
- No blocking defects remain open

---

## 9. Defect Management

Defects are identified by:

- Automated test failures
- Manual investigation if needed

Defects are reported with:

- Test logs
- Screenshots (for UI failures)
- API responses when applicable

Severity classification:

- Blocker: prevents business flow
- Major: business function incorrect
- Minor: non-critical behavior

---

## 10. Risks and Mitigation

| Risk | Impact | Mitigation |
|--------|--------|------------|
SPA UI re-render causes flaky tests | High | Explicit waits and DOM re-location |
API latency in CI | Medium | Polling instead of fixed waits |
Credential expiration | Medium | Centralized config and validation |
Environment instability | Medium | CI isolation and retry strategies |

---

## 11. Roles and Responsibilities

In a real team scenario:

- QA Automation Engineer:
    - Framework development
    - Test implementation
    - CI maintenance

- Developers:
    - Fix defects
    - Provide technical support

- Product / QA Leads:
    - Define acceptance criteria
    - Approve releases

For this challenge, all roles are simulated by the author.

---

## 12. Schedule

Testing is executed:

- Continuously during development
- On every CI build
- Before code integration to main branch

Regression execution time target:

- API tests: fast (< 1 minute)
- Web tests: moderate
- Full suite: under acceptable CI limits

---

## 13. Deliverables

Testing deliverables include:

- Automation framework source code
- CI execution logs
- Allure reports
- Test documentation:
    - README
    - Architecture Document
    - Test Strategy
    - Test Plan

---

## 14. Approval

This Test Plan is approved when:

- Test scope is aligned with business objectives
- CI pipeline runs successfully
- Stakeholders agree on exit criteria

---

