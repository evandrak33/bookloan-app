package myapps.bookloan_app.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import myapps.bookloan_app.core.Role;
import myapps.bookloan_app.model.Book;
import myapps.bookloan_app.model.User;
import myapps.bookloan_app.repository.BookRepository;
import myapps.bookloan_app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {

        // Prevent duplicate inserts on restart
        if (userRepository.count() > 0 || bookRepository.count() > 0) {
            return;
        }

        /* ================= ADMIN ================= */

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ADMIN);
        admin.setFirstname("Library");
        admin.setLastname("Administrator");
        admin.setEmail("admin@library.com");
        userRepository.save(admin);

        /* ================= USERS ================= */

        createUser("Maria", "Kappa", "userlib1@dom.com",
                "maria.kappa", "user123");

        createUser("Nick", "Lambda", "userlib2@dom.com",
                "nick.lambda", "user234");

        createUser("John", "Omikron", "userlib3@dom.com",
                "john.omikron", "user345");

        createUser("Kate", "Omega", "userlib4@dom.com",
                "kate.omega", "user456");

        createUser("Sue", "Sigma", "userlib5@dom.com",
                "sue.sigma", "user567");

        /* ================= BOOKS ================= */

        createBook("The Silent River", "A. Brown", 2015,
                "Penguin", "Drama");

        createBook("Lost Horizons", "J. Smith", 2018,
                "HarperCollins", "Adventure");

        createBook("Deep Space", "L. Stone", 2020,
                "Orbit", "Sci-Fi");

        createBook("Ancient Myths", "K. Hall", 2012,
                "Oxford", "History");

        createBook("Modern Java", "P. White", 2021,
                "TechPress", "Education");

        createBook("The Last Kingdom", "B. North", 2017,
                "Viking", "Historical");

        createBook("Hidden Truths", "M. Green", 2019,
                "Anchor", "Thriller");

        createBook("Ocean Depths", "S. Blue", 2014,
                "Marine", "Nature");

        createBook("Philosophy 101", "T. Black", 2010,
                "UniBooks", "Philosophy");

        createBook("World Economics", "R. Gold", 2016,
                "Global", "Economics");

        createBook("Art of Design", "E. Gray", 2013,
                "Visual", "Art");

        createBook("Cyber Security", "D. Fox", 2022,
                "SecureIT", "Technology");
    }

    /* ================= HELPERS ================= */

    private void createUser(String firstName,
                            String lastName,
                            String email,
                            String username,
                            String rawPassword) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(Role.USER);
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setEmail(email);

        userRepository.save(user);
    }

    private void createBook(String title,
                            String author,
                            int publicationYear,
                            String publisher,
                            String genre) {

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublicationYear(publicationYear);
        book.setPublisher(publisher);
        book.setGenre(genre);
        book.setAvailable(true);

        bookRepository.save(book);
    }
}
