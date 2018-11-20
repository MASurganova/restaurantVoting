package ru.voting.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.voting.model.Role;
import ru.voting.model.User;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Date;

@Controller
@RequestMapping(value = "/users")
public class UsersController extends AbstractController {

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        userService.delete(getId(request));
        return "redirect:/users";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("user", userService.get(getId(request)));
        return "userForm";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new User());
        return "userForm";
    }

    @PostMapping("/userForm")
    public String updateOrCreate(HttpServletRequest request) {
       User user =
                new User(request.getParameter("id").isEmpty()? null : getId(request),
                    request.getParameter("name"), request.getParameter("email"),
                        request.getParameter("password"),
                        request.getParameter("restaurantId").isEmpty()? null
                                : votingService.getRestaurantById(Integer.valueOf(request.getParameter("restaurantId"))),
                        Role.ROLE_USER);
        user.setRegistered(LocalDate.parse((request.getParameter("registered"))));
        if (user.getId() == null) {
            userService.create(user);
        } else {
            userService.update(user);
        }
        return "redirect:/users";
    }
}
