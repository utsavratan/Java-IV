# City Library Digital Management System – Java (Assignment 4)

This project is developed as part of **Java Programming Assignment 4**.  
It implements a complete digital library management system using **File Handling** and the **Java Collections Framework**.

The system supports:
- Adding books and members  
- Issuing and returning books  
- Searching and sorting book records  
- Saving and loading data using text files  
- Using Maps, Lists, Sets, Comparable & Comparator  

---

## 🚀 Features

### 📚 Book Management
- Add new books  
- Search books by title, author, or category  
- Sort books by:
  - Title (Comparable)
  - Author (Comparator)

### 👤 Member Management
- Add new members  
- Maintain issued books list for every member  

### 🔄 Transactions
- Issue a book  
- Return a book  
- Prevent duplicate issuance  
- Automatically update both book and member records  

### 💾 File Handling
- Uses `books.txt` and `members.txt` for persistent storage  
- Uses:
  - `FileReader` / `FileWriter`
  - `BufferedReader` / `BufferedWriter`
- Automatically loads data at startup and saves after every update  

### 🧰 Collections Framework Used
- `Map<Integer, Book>`  
- `Map<Integer, Member>`  
- `List<Integer>` for tracking issued books  
- `Set<String>` for unique categories  

---

## 📂 File Included
- **LibraryManager.java**  
  → Contains:
  - Book class  
  - Member class  
  - Library Manager class  
  - Full menu system  
  - File handling  
  - Sorting, searching, issuing, returning  
  - Main method  


---

## ✔ Assignment Requirements Covered

- File Handling (BufferedReader, BufferedWriter, Streams)  
- Collections (List, Set, Map)  
- Comparable (Sort by Title)  
- Comparator (Sort by Author)  
- Searching functionality  
- Menu-driven program  
- Data persistence  
- Good modular structure  
- Clean and readable code  

---

## 📜 Notes
- Works on any Java version (8+)  
- All files auto-created on first run  
- Designed as per academic requirements  

---

## 📌 License
This project is for educational and academic submission purposes only.


