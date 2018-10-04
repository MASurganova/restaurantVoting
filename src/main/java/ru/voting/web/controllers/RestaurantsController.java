package ru.voting.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.voting.model.Restaurant;
import ru.voting.service.VotingService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping(value = "/restaurants")
public class RestaurantsController {

    @Autowired
    VotingService service;

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        service.deleteRestaurant(getId(request));
        return "redirect:/restaurants";
    }

    @GetMapping("/enabled")
    public String enabled(HttpServletRequest request) {
        service.addRestaurantToVote(getId(request));
        return "redirect:/restaurants";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("restaurant", service.getRestaurantById(getId(request)));
        return "restaurantForm";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("restaurant", new Restaurant(null, ""));
        return "restaurantForm";
    }

    @PostMapping
    public String updateOrCreate(HttpServletRequest request) {
        String name = request.getParameter("name");

        if (request.getParameter("id").isEmpty()) {
            service.createRestaurant(new Restaurant(null, name));
        } else {
            Restaurant restaurant = service.getRestaurantByIdWithLunch(getId(request));
            restaurant.setName(name);
            service.updateRestaurant(restaurant);
        }
        return "redirect:/restaurants";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
