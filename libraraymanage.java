import java.io.*;
import java.util.*;

// ------------------------ BOOK CLASS ------------------------
class Book implements Comparable<Book> {
    int bookId;
    String title;
    String author;
    String category;
    boolean isIssued;

    public Book(int bookId, String title, String author, String category, boolean isIssued) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isIssued = isIssued;
    }

    public void displayBookDetails() {
        System.out.println("Book ID: " + bookId);
        System.out.println("Title  : " + title);
        System.out.println("Author : " + author);
        System.out.println("Category: " + category);
        System.out.println("Issued : " + (isIssued ? "Yes" : "No"));
        System.out.println("--------------------------------------");
    }

    public void markAsIssued() {
        isIssued = true;
    }

    public void markAsReturned() {
        isIssued = false;
    }

    // Sort by title (Comparable)
    @Override
    public int compareTo(Book b) {
        return this.title.compareToIgnoreCase(b.title);
    }
}


// ------------------------ MEMBER CLASS ------------------------
class Member {
    int memberId;
    String name;
    String email;
    List<Integer> issuedBooks = new ArrayList<>();

    public Member(int memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    public void displayMemberDetails() {
        System.out.println("Member ID : " + memberId);
        System.out.println("Name      : " + name);
        System.out.println("Email     : " + email);
        System.out.println("Issued Books: " + issuedBooks);
        System.out.println("--------------------------------------");
    }

    public void addIssuedBook(int bookId) {
        issuedBooks.add(bookId);
    }

    public void returnIssuedBook(int bookId) {
        issuedBooks.remove(Integer.valueOf(bookId));
    }
}


// ------------------------ LIBRARY MANAGER ------------------------
public class LibraryManager {

    Map<Integer, Book> books = new HashMap<>();
    Map<Integer, Member> members = new HashMap<>();
    Set<String> categories = new HashSet<>();

    Scanner sc = new Scanner(System.in);

    File bookFile = new File("books.txt");
    File memberFile = new File("members.txt");


    // ------------------- LOAD DATA FROM FILE -------------------
    public void loadFromFile() {

        try {
            if (!bookFile.exists()) bookFile.createNewFile();
            if (!memberFile.exists()) memberFile.createNewFile();

            BufferedReader br1 = new BufferedReader(new FileReader(bookFile));
            String line;

            while ((line = br1.readLine()) != null) {
                String[] arr = line.split(",");
                int id = Integer.parseInt(arr[0]);
                String t = arr[1];
                String a = arr[2];
                String c = arr[3];
                boolean issued = Boolean.parseBoolean(arr[4]);

                books.put(id, new Book(id, t, a, c, issued));
                categories.add(c);
            }
            br1.close();

            BufferedReader br2 = new BufferedReader(new FileReader(memberFile));
            while ((line = br2.readLine()) != null) {
                String[] arr = line.split(",");
                int id = Integer.parseInt(arr[0]);
                String name = arr[1];
                String email = arr[2];

                Member m = new Member(id, name, email);

                if (arr.length > 3) {
                    String[] bidArray = arr[3].split(";");
                    for (String b : bidArray) {
                        if (!b.isEmpty()) m.issuedBooks.add(Integer.parseInt(b));
                    }
                }
                members.put(id, m);
            }
            br2.close();

        } catch (Exception e) {
            System.out.println("Error loading data.");
        }
    }


    // ------------------- SAVE DATA TO FILE -------------------
    public void saveToFile() {
        try {
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(bookFile));

            for (Book b : books.values()) {
                bw1.write(b.bookId + "," + b.title + "," + b.author + "," + b.category + "," + b.isIssued);
                bw1.newLine();
            }
            bw1.close();

            BufferedWriter bw2 = new BufferedWriter(new FileWriter(memberFile));
            for (Member m : members.values()) {

                StringBuilder issuedList = new StringBuilder();
                for (int id : m.issuedBooks) {
                    issuedList.append(id).append(";");
                }

                bw2.write(m.memberId + "," + m.name + "," + m.email + "," + issuedList);
                bw2.newLine();
            }
            bw2.close();

        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }


    // ------------------- ADD BOOK -------------------
    public void addBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Author: ");
        String author = sc.nextLine();

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        Book b = new Book(id, title, author, category, false);
        books.put(id, b);
        categories.add(category);

        saveToFile();
        System.out.println("Book added successfully!");
    }


    // ------------------- ADD MEMBER -------------------
    public void addMember() {
        System.out.print("Enter Member ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        Member m = new Member(id, name, email);
        members.put(id, m);

        saveToFile();
        System.out.println("Member added successfully!");
    }


    // ------------------- ISSUE BOOK -------------------
    public void issueBook() {
        System.out.print("Enter Book ID: ");
        int bid = sc.nextInt();

        System.out.print("Enter Member ID: ");
        int mid = sc.nextInt();

        if (!books.containsKey(bid)) {
            System.out.println("Book not found!");
            return;
        }
        if (!members.containsKey(mid)) {
            System.out.println("Member not found!");
            return;
        }

        Book b = books.get(bid);
        if (b.isIssued) {
            System.out.println("Book already issued!");
            return;
        }

        b.markAsIssued();
        members.get(mid).addIssuedBook(bid);

        saveToFile();
        System.out.println("Book issued successfully!");
    }


    // ------------------- RETURN BOOK -------------------
    public void returnBook() {
        System.out.print("Enter Book ID: ");
        int bid = sc.nextInt();

        if (!books.containsKey(bid)) {
            System.out.println("Book not found!");
            return;
        }

        Book b = books.get(bid);
        if (!b.isIssued) {
            System.out.println("Book is not issued!");
            return;
        }

        b.markAsReturned();

        for (Member m : members.values()) {
            if (m.issuedBooks.contains(bid)) {
                m.returnIssuedBook(bid);
            }
        }

        saveToFile();
        System.out.println("Book returned successfully!");
    }


    // ------------------- SEARCH BOOKS -------------------
    public void searchBooks() {
        sc.nextLine();
        System.out.print("Enter search keyword (title/author/category): ");
        String key = sc.nextLine().toLowerCase();

        for (Book b : books.values()) {
            if (b.title.toLowerCase().contains(key) ||
                b.author.toLowerCase().contains(key) ||
                b.category.toLowerCase().contains(key)) {
                b.displayBookDetails();
            }
        }
    }


    // ------------------- SORT BOOKS -------------------
    public void sortBooks() {
        List<Book> list = new ArrayList<>(books.values());

        System.out.println("1. Sort by Title");
        System.out.println("2. Sort by Author");
        int ch = sc.nextInt();

        if (ch == 1) Collections.sort(list);
        else Collections.sort(list, Comparator.comparing(b -> b.author));

        for (Book b : list) b.displayBookDetails();
    }


    // ------------------- MAIN MENU -------------------
    public void menu() {
        loadFromFile();

        while (true) {
            System.out.println("\n===== City Library Digital Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Books");
            System.out.println("6. Sort Books");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1: addBook(); break;
                case 2: addMember(); break;
                case 3: issueBook(); break;
                case 4: returnBook(); break;
                case 5: searchBooks(); break;
                case 6: sortBooks(); break;
                case 7:
                    saveToFile();
                    System.out.println("Exiting... Data Saved.");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }


    public static void main(String[] args) {
        LibraryManager lm = new LibraryManager();
        lm.menu();
    }
}
