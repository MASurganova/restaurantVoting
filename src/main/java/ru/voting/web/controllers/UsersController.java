package ru.voting.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.voting.model.Role;
import ru.voting.model.User;
import ru.voting.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping(value = "/users")
public class UsersController {

    @Autowired
    UserService service;

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        service.delete(getId(request));
        return "redirect:/users";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("user", service.get(getId(request)));
        return "userForm";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new User());
        return "userForm";
    }

    @PostMapping
    public String updateOrCreate(HttpServletRequest request) {
       User user =
                new User(request.getParameter("id").isEmpty()? null : getId(request),
                    request.getParameter("name"), request.getParameter("email"),
                        request.getParameter("password"), Role.ROLE_USER);

        if (user.getId() == null) {
            service.create(user);
        } else {
            service.update(user);
        }
        return "redirect:/users";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
