package myapps.bookloan_app.service;

import myapps.bookloan_app.model.Book;
import myapps.bookloan_app.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAvailableBooks() {
        return bookRepository.findByAvailableTrue();
    }
}
