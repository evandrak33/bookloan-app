package myapps.bookloan_app.mapper;

import myapps.bookloan_app.dto.*;
import myapps.bookloan_app.model.Book;
import myapps.bookloan_app.model.Loan;
import myapps.bookloan_app.model.User;
import org.springframework.stereotype.Component;
@Component
public class Mapper {

    /* -------------------- BOOK -------------------- */

    public Book mapToBookEntity(BookInsertDTO dto) {
        Book book = new Book();

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPublicationYear(dto.getPublicationYear());
        book.setPublisher(dto.getPublisher());
        book.setGenre(dto.getGenre());
        book.setAvailable(true);

        return book;
    }

    public Book mapToBookEntity(BookUpdateDTO dto, Book book) {
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPublicationYear(dto.getPublicationYear());
        book.setPublisher(dto.getPublisher());
        book.setGenre(dto.getGenre());
        return book;
    }

    public BookReadOnlyDTO mapToBookReadOnlyDTO(Book book) {
        BookReadOnlyDTO dto = new BookReadOnlyDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setPublisher(book.getPublisher());
        dto.setGenre(book.getGenre());
        dto.setAvailable(book.isAvailable());
        return dto;
    }

    /* -------------------- LOAN -------------------- */

    public LoanReadOnlyDTO mapToLoanReadOnlyDTO(Loan loan) {
        LoanReadOnlyDTO dto = new LoanReadOnlyDTO();

        dto.setId(loan.getId());

        dto.setBookTitle(loan.getBook().getTitle());
        dto.setBookAuthor(loan.getBook().getAuthor());
        dto.setBookPublicationYear(loan.getBook().getPublicationYear());
        dto.setBookPublisher(loan.getBook().getPublisher());
        dto.setBookGenre(loan.getBook().getGenre());

        dto.setLoanDate(loan.getLoanDate());
        dto.setDueDate(loan.getDueDate());

        dto.setUsername(loan.getUser().getUsername());

        return dto;
    }


    /* -------------------- USER -------------------- */

    public UserReadOnlyDTO mapToUserReadOnlyDTO(User user) {
        UserReadOnlyDTO dto = new UserReadOnlyDTO();
        dto.setId(user.getId());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());
        return dto;
    }

}
