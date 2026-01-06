# 🏦 Banking System (OOP Final Project)

This project is a **console-based Banking System** developed as an Object-Oriented Programming (OOP) final project.
The system allows users to log in, manage multiple bank accounts, perform transactions, and persist data using CSV files.

---

## 🎯 Project Goals

* Apply **Object-Oriented Programming principles** (Encapsulation, Inheritance, Polymorphism)
* Implement a **realistic banking workflow**
* Use **CSV-based persistence** instead of a database
* Practice **unit testing with JUnit**
* Follow a **task-based Agile/Kanban workflow**

---

## 🧱 System Features

### 👤 User Management

* User login with username & password
* Each user can own **multiple accounts**

### 💳 Account Types

* **Account (Base Class)**
* **SavingsAccount**
* **CheckingAccount**


### 💸 Banking Operations

* Deposit money
* Withdraw money
* Transfer money between accounts

  * Own accounts
  * Different users' accounts

### 🧾 Transaction System

* All operations generate a **Transaction record**
* Supported transaction types:

  * DEPOSIT
  * WITHDRAW
  * TRANSFER
* Users can view **their own transaction history only**

### 💾 Data Persistence (CSV)

* Accounts are saved to `accounts.csv`
* Transactions are saved to `transactions.csv`
* Data is automatically restored when the application starts

---

## 🧪 Testing

* JUnit 5 is used for unit testing
* `BankTest` covers:

  * Deposit operations
  * Withdraw operations (success & failure)
  * Transfer operations (success & failure)
  * Transaction creation validation

All tests pass successfully ✅

---

## 📁 Project Structure

```
src/
├── data/
│   ├── users.csv
│   ├── accounts.csv
│   └── transactions.csv
│
├── model/
│   ├── Account.java
│   ├── SavingsAccount.java
│   ├── CheckingAccount.java
│   ├── Bank.java
│   ├── Transaction.java
│   ├── TransactionType.java
│   ├── User.java
│   └── AuthService.java
│
├── test/
│   └── BankTest.java
│
└── Main.java
```

---

## ▶️ How to Run

1. Open the project in **IntelliJ IDEA** or any Java IDE
2. Make sure JDK 17+ is configured
3. Run `Main.java`
4. Log in using a user from `users.csv`
5. Use the menu to perform banking operations

---

## 📐 Diagrams

* Use Case Diagram ✔️
* Class Diagram ✔️

Both diagrams are included in the project documentation.

---

## 🛠 Technologies Used

* Java
* Object-Oriented Programming (OOP)
* JUnit 5
* CSV File I/O
* IntelliJ IDEA
* Git & GitHub

---

## 👤 Author

* **Yılmaz Utku Koçer**
  Computer Engineering Student

---

## 📌 Notes

This project was developed for educational purposes as part of an OOP course final assignment.
