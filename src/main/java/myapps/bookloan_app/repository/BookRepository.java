package myapps.bookloan_app.repository;

import myapps.bookloan_app.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAvailableTrue();
}
