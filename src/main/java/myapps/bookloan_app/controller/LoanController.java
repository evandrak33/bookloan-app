package myapps.bookloan_app.controller;

import myapps.bookloan_app.dto.LoanCreateDTO;
import myapps.bookloan_app.dto.LoanReadOnlyDTO;
import myapps.bookloan_app.mapper.Mapper;
import myapps.bookloan_app.model.Book;
import myapps.bookloan_app.model.User;
import myapps.bookloan_app.service.BookService;
import myapps.bookloan_app.service.LoanService;
import myapps.bookloan_app.service.UserService;
import myapps.bookloan_app.validator.LoanCreateValidator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;
    private final BookService bookService;
    private final UserService userService;
    private final Mapper mapper;
    private final LoanCreateValidator loanCreateValidator;

    public LoanController(LoanService loanService,
                          BookService bookService,
                          UserService userService,
                          Mapper mapper,
                          LoanCreateValidator loanCreateValidator) {
        this.loanService = loanService;
        this.bookService = bookService;
        this.userService = userService;
        this.mapper = mapper;
        this.loanCreateValidator = loanCreateValidator;
    }

    /* -------------------- USER: MY LOANS -------------------- */

    @GetMapping("/my")
    public String myLoans(Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName());

        List<LoanReadOnlyDTO> loans = loanService.findLoansByUser(user)
                .stream()
                .map(mapper::mapToLoanReadOnlyDTO)
                .toList();

        model.addAttribute("loans", loans);
        return "loans";
    }

    /* -------------------- ADMIN: ALL LOANS -------------------- */

    @GetMapping
    public String allLoans(Model model) {
        List<LoanReadOnlyDTO> loans = loanService.findAllLoans()
                .stream()
                .map(mapper::mapToLoanReadOnlyDTO)
                .toList();

        model.addAttribute("loans", loans);
        return "loans";
    }

    /* -------------------- ADMIN: BORROW -------------------- */

    @PostMapping("/borrow")
    public String borrowBook(
            @ModelAttribute LoanCreateDTO dto,
            BindingResult bindingResult) {

        loanCreateValidator.validate(dto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "redirect:/books";
        }

        Book book = bookService.findById(dto.getBookId());
        User user = userService.findById(dto.getUserId());

        loanService.borrowBook(book, user);
        return "redirect:/loans";
    }


    /* -------------------- ADMIN: RETURN -------------------- */

    @PostMapping("/return/{id}")
    public String returnBook(@PathVariable Long id) {
        loanService.returnBook(id);
        return "redirect:/loans";
    }

}
