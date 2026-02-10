package myapps.bookloan_app.service;

import myapps.bookloan_app.model.Book;
import myapps.bookloan_app.model.User;
import myapps.bookloan_app.model.Wishlist;
import myapps.bookloan_app.repository.BookRepository;
import myapps.bookloan_app.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final BookRepository bookRepository;

    public WishlistService(WishlistRepository wishlistRepository,
                           BookRepository bookRepository) {
        this.wishlistRepository = wishlistRepository;
        this.bookRepository = bookRepository;
    }

    /* -------------------- ADD -------------------- */

    public void add(User user, Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (wishlistRepository.existsByUserAndBook(user, book)) {
            return; // silently ignore duplicates
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setBook(book);

        wishlistRepository.save(wishlist);
    }

    /* -------------------- REMOVE -------------------- */

    public void remove(User user, Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        wishlistRepository.findByUserAndBook(user, book)
                .ifPresent(wishlistRepository::delete);
    }

    /* -------------------- QUERY -------------------- */

    public List<Wishlist> findByUser(User user) {
        return wishlistRepository.findByUser(user);
    }
}
