package myapps.bookloan_app.dto;

import java.time.LocalDate;

public class LoanReadOnlyDTO {

    private Long id;

    // -------- Book info --------
    private String bookTitle;
    private String bookAuthor;
    private Integer bookPublicationYear;
    private String bookPublisher;
    private String bookGenre;

    // -------- Loan info --------
    private LocalDate loanDate;
    private LocalDate dueDate;

    // -------- User info (ADMIN only in UI) --------
    private String username;

    public LoanReadOnlyDTO() {}

    // -------- getters & setters --------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Integer getBookPublicationYear() {
        return bookPublicationYear;
    }

    public void setBookPublicationYear(Integer bookPublicationYear) {
        this.bookPublicationYear = bookPublicationYear;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
