package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.DisabledCredentials;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("disableCredentials", new DisabledCredentials());
        return "UsersPage";
    }

    @PostMapping("users/all")
    public String changeDisabled(@Valid @ModelAttribute("disableCredentials") DisabledCredentials disabledCredentials,
                        HttpSession httpSession, Model model){
        model.addAttribute("users", userService.findAll());
        userService.changeDisabled(disabledCredentials.getId(), disabledCredentials.isDisabled());
        return "redirect:/users/all";
    }
}
