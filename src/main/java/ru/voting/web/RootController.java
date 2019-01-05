package ru.voting.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("votingEvents", votingService.getVotingHistory());
        return "history";

    }

    @GetMapping("/voting")
    public String voting(Model model) {
        model.addAttribute("restaurants", votingService.getCurrentRestaurants());
        return "voting";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("restaurants/{param}")
    public String getRestaurant(@PathVariable("param") String param, Model model) {
        try {
            int id = Integer.valueOf(param);
            model.addAttribute("restaurant", votingService.getRestaurantByIdWithLunch(id));
        } catch (NumberFormatException e) {
            model.addAttribute("restaurant", votingService.getRestaurantByName(param));
        }
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
