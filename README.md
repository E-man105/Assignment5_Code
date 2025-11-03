# Assignment5_Code

[![SE333_CI](https://github.com/E-man105/Assignment5_Code/actions/workflows/SE333_CI.yml/badge.svg)](https://github.com/E-man105/Assignment5_Code/actions/workflows/SE333_CI.yml)

![SE333_CI](https://github.com/E-man105/Assignment5_Code/actions/workflows/SE333_CI.yml/badge.svg)

This project contains tests for BarnesAndNoble and Amazon packages, and a GitHub Actions workflow for CI.# Assignment 5 – Unit, Mocking, and Integration Testing

## Part 1 – Project Setup and Testing Practice

### Project Overview
This project is designed to simulate a bookstore system (`BarnesAndNoble`) where books are retrieved from a database and purchased via a process interface. The goal of this part was to practice writing **unit tests**, both **specification-based** and **structural-based**, for the `BarnesAndNoble` system.

### Tests Written

#### Specification-Based Tests
- `testAllBooksAvailable`: verifies total price calculation when all requested books are available.  
- `testMultipleBooksAvailable`: verifies total price and multiple books in the cart are handled correctly.  

#### Structural-Based Tests
- `testInsufficientStock`: verifies that the system correctly handles requests exceeding available stock and records unavailable quantities.  
- `testNullCart`: verifies that a `null` cart input returns `null`.  
- `testProcessCalledPerBook`: ensures that `BuyBookProcess.buyBook()` is called for each book in the cart.  

### Notes
- Mocks are used for `BookDatabase` and `BuyBookProcess` using Mockito.  
- JUnit 5 annotations (`@BeforeEach`, `@Test`, `@DisplayName`) are used for test organization and labeling.  
- All tests are located in:  
  `src/test/java/org/example/Barnes/BarnesAndNobleTest.java`  
