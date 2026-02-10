package myapps.bookloan_app.controller;

import myapps.bookloan_app.dto.BookReadOnlyDTO;
import myapps.bookloan_app.mapper.Mapper;
import myapps.bookloan_app.model.User;
import myapps.bookloan_app.service.LoanService;
import myapps.bookloan_app.service.UserService;
import myapps.bookloan_app.service.WishlistService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;
    private final LoanService loanService;
    private final Mapper mapper;

    public WishlistController(WishlistService wishlistService,
                              UserService userService,
                              LoanService loanService,
                              Mapper mapper) {
        this.wishlistService = wishlistService;
        this.userService = userService;
        this.loanService = loanService;
        this.mapper = mapper;
    }

    /* -------------------- VIEW WISHLIST -------------------- */

    @GetMapping
    public String myWishlist(Authentication authentication, Model model) {

        User user = userService.findByUsername(authentication.getName());

        List<BookReadOnlyDTO> books =
                wishlistService.findByUser(user)
                        .stream()
                        .map(w -> mapper.mapToBookReadOnlyDTO(w.getBook()))
                        .toList();

        model.addAttribute("books", books);
        model.addAttribute("borrowedByMeIds",
                loanService.findLoansByUser(user)
                        .stream()
                        .map(l -> l.getBook().getId())
                        .toList()
        );

        return "wishlist";
    }

    /* -------------------- ADD -------------------- */

    @PostMapping("/{bookId}")
    public String addToWishlist(@PathVariable Long bookId,
                                Authentication authentication) {

        User user = userService.findByUsername(authentication.getName());
        wishlistService.add(user, bookId);

        return "redirect:/books";
    }

    /* -------------------- REMOVE -------------------- */

    @DeleteMapping("/{bookId}")
    public String removeFromWishlist(@PathVariable Long bookId,
                                     Authentication authentication) {

        User user = userService.findByUsername(authentication.getName());
        wishlistService.remove(user, bookId);

        return "redirect:/wishlist";
    }
}
