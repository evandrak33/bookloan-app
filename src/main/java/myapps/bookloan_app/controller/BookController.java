package myapps.bookloan_app.controller;

import myapps.bookloan_app.dto.LoanCreateDTO;
import myapps.bookloan_app.dto.BookInsertDTO;
import myapps.bookloan_app.dto.BookReadOnlyDTO;
import myapps.bookloan_app.dto.BookUpdateDTO;
import myapps.bookloan_app.mapper.Mapper;
import myapps.bookloan_app.model.Book;
import myapps.bookloan_app.model.User;
import myapps.bookloan_app.service.BookService;
import myapps.bookloan_app.service.UserService;
import myapps.bookloan_app.service.WishlistService;
import myapps.bookloan_app.validator.BookInsertValidator;
import myapps.bookloan_app.validator.BookUpdateValidator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final UserService userService;
    private final WishlistService wishlistService;
    private final Mapper mapper;
    private final BookInsertValidator bookInsertValidator;
    private final BookUpdateValidator bookUpdateValidator;

    public BookController(BookService bookService,
                          UserService userService,
                          WishlistService wishlistService,
                          Mapper mapper,
                          BookInsertValidator bookInsertValidator,
                          BookUpdateValidator bookUpdateValidator) {
        this.bookService = bookService;
        this.userService = userService;
        this.wishlistService = wishlistService;
        this.mapper = mapper;
        this.bookInsertValidator = bookInsertValidator;
        this.bookUpdateValidator = bookUpdateValidator;
    }

    /* -------------------- LIST -------------------- */

    @GetMapping
    public String listBooks(Authentication authentication, Model model) {

        List<BookReadOnlyDTO> books = bookService.findAll()
                .stream()
                .map(mapper::mapToBookReadOnlyDTO)
                .toList();

        model.addAttribute("books", books);


        Set<Long> wishlistBookIds = Set.of();

        if (authentication != null &&
                authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {

            User user = userService.findByUsername(authentication.getName());

            wishlistBookIds = wishlistService.findByUser(user)
                    .stream()
                    .map(w -> w.getBook().getId())
                    .collect(Collectors.toSet());
        }

        model.addAttribute("wishlistBookIds", wishlistBookIds);

        return "books";
    }

    /* -------------------- ADD -------------------- */

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new BookInsertDTO());
        return "add-book";
    }

    @PostMapping("/add")
    public String addBook(
            @Valid @ModelAttribute("book") BookInsertDTO dto,
            BindingResult bindingResult) {

        bookInsertValidator.validate(dto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "add-book";
        }

        Book book = mapper.mapToBookEntity(dto);
        bookService.save(book);

        return "redirect:/books";
    }

    /* -------------------- EDIT -------------------- */

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {

        Book book = bookService.findById(id);

        BookUpdateDTO dto = new BookUpdateDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setPublisher(book.getPublisher());
        dto.setGenre(book.getGenre());

        model.addAttribute("book", dto);
        return "edit-book";
    }

    @PostMapping("/edit")
    public String editBook(
            @ModelAttribute("book") BookUpdateDTO dto,
            BindingResult bindingResult) {

        bookUpdateValidator.validate(dto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "edit-book";
        }

        Book book = bookService.findById(dto.getId());
        mapper.mapToBookEntity(dto, book);
        bookService.save(book);

        return "redirect:/books";
    }

    /* -------------------- BORROW (ADMIN) -------------------- */

    @GetMapping("/borrow/{id}")
    public String showBorrowForm(@PathVariable Long id, Model model) {

        Book book = bookService.findById(id);

        LoanCreateDTO dto = new LoanCreateDTO();
        dto.setBookId(book.getId());

        model.addAttribute("loan", dto);
        model.addAttribute("book", mapper.mapToBookReadOnlyDTO(book));
        model.addAttribute("users",
                userService.findAll()
                        .stream()
                        .map(mapper::mapToUserReadOnlyDTO)
                        .toList()
        );

        return "borrow-book";
    }
}
