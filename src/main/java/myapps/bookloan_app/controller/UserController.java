package myapps.bookloan_app.controller;

import myapps.bookloan_app.dto.UserReadOnlyDTO;
import myapps.bookloan_app.mapper.Mapper;
import myapps.bookloan_app.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final Mapper mapper;

    public UserController(UserService userService, Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public String listUsers(Model model) {
        List<UserReadOnlyDTO> users = userService.findAll()
                .stream()
                .map(mapper::mapToUserReadOnlyDTO)
                .toList();

        model.addAttribute("users", users);
        return "users";
    }
}
