package ru.voting.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.voting.AuthorizedUser;
import ru.voting.service.VotingService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController extends AbstractController{

    @GetMapping("/")
    public String root() {
        return "redirect:start";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String users() {
        return "users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/restaurants")
    public String restaurants(Model model) {
        model.addAttribute("restaurants", votingService.getAllRestaurantsWithLunch());
        return "restaurants";

    }

    @GetMapping("/voting")
    public String voting(Model model) {
        model.addAttribute("restaurants", votingService.getCurrentRestaurants());
        return "voting";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("restaurants/{id}")
    public String getRestaurant(@PathVariable("id") int id, Model model) {
        model.addAttribute("restaurant", votingService.getRestaurantByIdWithLunch(id));
        model.addAttribute("restaurantId", id);
        return "restaurantForm";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/start")
    public String start() {
        return "start";
    }

}
