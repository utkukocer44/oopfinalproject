# 🏦 Banking System – Kanban Board

This Kanban board is used to track the development process of the Banking System project.
The project follows basic Agile principles and task-based development.

---

## 🟦 Backlog

- Monthly report generation
- Interest calculation system
- Credit / loan system
- Transaction type enum implementation
- Custom exception handling (e.g. InsufficientBalanceException)

---

## 🟨 To Do
 
 - Added login system and refactor main.java 

### 📐 Diagrams & Documentation
- Final review of class diagram
- Final review of use case diagram
- Create README.md documentation

### 🧱 Core Classes
- Create Account base class
- Create SavingsAccount class
- Create CheckingAccount class
- Create Transferable interface
- Create Transaction class
- Create Bank class

### 🧪 Testing
- Create Main test class
- Test deposit and withdraw operations
- Test transfer operation
- Test transaction history listing

---

## 🟧 In Progress

 - Added deposit and withdraw features in Bank class

---

## 🟩 Done 

- Create Main test class
- Create Bank class
- Add CSV export for transactions
- Design CSV structure for accounts
- Create data files for persistence
- Create Transaction class
- Create Transferable interface
- Create CheckingAccount class
- Create SavingsAccount class
- Create Account base class
- GitHub repository created
- Use case diagram completed
- Class diagram completed

---

## 🔁 Workflow Rules

- Only one task can be in progress at a time
- Each completed task must have a related Git commit
- Commit messages should follow this format:
  - `feat: description`
  - `docs: description`
  - `fix: description`

---

## 🧩 Example Commit Messages

```bash
feat: add Account base class
feat: add SavingsAccount and CheckingAccount
feat: implement Transferable interface
feat: add Transaction model
feat: add Bank class and transfer logic
docs: add diagrams and README
