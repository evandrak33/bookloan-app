package myapps.bookloan_app.repository;

import myapps.bookloan_app.model.Book;
import myapps.bookloan_app.model.User;
import myapps.bookloan_app.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByUser(User user);

    boolean existsByUserAndBook(User user, Book book);

    Optional<Wishlist> findByUserAndBook(User user, Book book);
}
