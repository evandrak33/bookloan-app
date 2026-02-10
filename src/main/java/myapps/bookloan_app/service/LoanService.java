package myapps.bookloan_app.service;

import myapps.bookloan_app.core.SystemConstants;
import myapps.bookloan_app.model.Book;
import myapps.bookloan_app.model.Loan;
import myapps.bookloan_app.model.User;
import myapps.bookloan_app.repository.BookRepository;
import myapps.bookloan_app.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository,
                       BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    /* -------------------- BORROW -------------------- */

    public void borrowBook(Book book, User user) {

        if (!book.isAvailable()) {
            throw new RuntimeException("Book is already borrowed");
        }

        long borrowedCount = loanRepository.countByUser(user);
        if (borrowedCount >= SystemConstants.MAX_BORROWED_BOOKS) {
            throw new RuntimeException("User has reached maximum borrowed books");
        }

        book.setAvailable(false);
        bookRepository.save(book);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(SystemConstants.LOAN_DURATION_DAYS));

        loanRepository.save(loan);
    }

    /* -------------------- RETURN -------------------- */

    public void returnBook(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        Book book = loan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        loanRepository.delete(loan);
    }

    /* -------------------- QUERIES -------------------- */

    public List<Loan> findAllLoans() {
        return loanRepository.findAll();
    }

    public List<Loan> findLoansByUser(User user) {
        return loanRepository.findByUser(user);
    }
}
