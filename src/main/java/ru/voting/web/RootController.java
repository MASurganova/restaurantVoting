package ru.voting.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.voting.AuthorizedUser;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController extends AbstractController{

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/users")
    public String users() {
        return "users";
    }

    @GetMapping("/restaurants")
    public String restaurants(Model model) {
        model.addAttribute("restaurants", votingService.getAllRestaurants());
        return "restaurants";

    }@GetMapping("/voting")
    public String voting(Model model) {
        model.addAttribute("restaurants", votingService.getCurrentRestaurants());
        model.addAttribute("userId", AuthorizedUser.id());
        return "voting";
    }

    @PostMapping("/start")
    public String setUser(HttpServletRequest request, Model model) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        model.addAttribute("userId", AuthorizedUser.id());
        return "start";
    }

    @GetMapping("/index")
    public String index() {
        return "start";
    }

    @GetMapping("restaurants/{id}")
    public String getRestaurant(@PathVariable("id") int id, Model model) {
        model.addAttribute("restaurant", votingService.getRestaurantByIdWithLunch(id));
        return "restaurantForm";
    }

}
