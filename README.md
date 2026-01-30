# 📚 Library Book Lending System (JDBC)

A **console-based Java application** built using **JDBC** that allows a library administrator to manage books, issue and return transactions, and maintain inventory consistency using transactional control.

---

## 🚀 Features

- ➕ Add New Book  
- 🔍 View Book Details by Book ID  
- 📋 View All Books  
- 📤 Issue Book to Student (Transactional)  
- 📥 Return Book (Transactional)  
- ❌ Remove Book (Only if no active issues)

---

## 🛠️ Tech Stack

- **Language:** Java  
- **Database:** Oracle DB  
- **Connectivity:** JDBC  
- **Architecture:** Layered (App → Service → DAO → DB)

---

## 🗄️ Database Design

### BOOK_TBL
| Column | Type | Description |
|------|------|-------------|
| book_id | VARCHAR2(10) | Primary Key |
| title | VARCHAR2(120) | Book title |
| author | VARCHAR2(80) | Author name |
| total_copies | NUMBER(4) | Total copies |
| available_copies | NUMBER(4) | Available copies |

### ISSUE_TBL
| Column | Type | Description |
|------|------|-------------|
| issue_id | NUMBER(8) | Primary Key |
| book_id | VARCHAR2(10) | FK → BOOK_TBL |
| student_id | VARCHAR2(12) | Student ID |
| student_name | VARCHAR2(100) | Student Name |
| issue_date | DATE | Issue Date |
| return_date | DATE | Return Date (NULL if active) |

---

com.library.app
└── LibraryMain.java # Application entry point

com.library.service
└── LibraryService.java # Business logic & transactions

com.library.dao
├── BookDAO.java # Book persistence operations
└── IssueDAO.java # Issue transaction operations

com.library.bean
├── Book.java # Book entity
└── Issue.java # Issue entity

com.library.util
├── DBUtil.java
├── ValidationException.java
├── BookUnavailableException.java
└── ActiveIssueException.java


---

## 🔄 Transaction Management

### Issue Book (Atomic)
1. Validate inputs  
2. Check book availability  
3. Decrement available copies  
4. Create issue record  
5. Commit / Rollback on failure  

### Return Book (Atomic)
1. Validate issue ID  
2. Increment available copies  
3. Close issue record  
4. Commit / Rollback on failure  

---

## ⚠️ Custom Exceptions

- **ValidationException** – Invalid inputs  
- **BookUnavailableException** – No available copies  
- **ActiveIssueException** – Book has active issues  

---

## ▶️ Sample Execution

```java
libraryService.issueBook("BK101", "ST5001", "Meera Nair");
libraryService.returnBook(30001);


## 🧱 Project Structure

