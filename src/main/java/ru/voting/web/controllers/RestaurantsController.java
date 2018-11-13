package ru.voting.web.controllers;

import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.service.VotingService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping(value = "/restaurants")
public class RestaurantsController extends AbstractController{

    @Autowired
    VotingService service;

    @GetMapping("/dishes/delete")
    public String deleteDish(Model model, HttpServletRequest request) {
        service.deleteDish(getId(request));
        model.addAttribute("restaurant", service.getRestaurantByIdWithLunch(
                Integer.valueOf(request.getParameter("restaurantId"))));
        model.addAttribute("id", request.getParameter("restaurantId"));
        return "redirect:/restaurants/update";
    }

    @GetMapping("/dishes/update")
    public String updateDish(Model model, HttpServletRequest request) {
        model.addAttribute("dish", service.getDishById(getId(request)));
        model.addAttribute("restaurantId", request.getParameter("restaurantId"));
        return "dishForm";
    }

    @GetMapping("/dishes/create")
    public String createDish(Model model, HttpServletRequest request) {
        model.addAttribute("dish", new Dish());
        model.addAttribute("restaurantId", request.getParameter("restaurantId"));
        return "dishForm";
    }

    @GetMapping("/delete")
    public String delete(Model model, HttpServletRequest request) {
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
        model.addAttribute("restaurant", service.getRestaurantByIdWithLunch(getId(request)));
        return "restaurantForm";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("restaurant", new Restaurant(null, ""));
        return "restaurantForm";
    }

    @PostMapping("/restaurantForm")
    public String updateOrCreate(Model model, HttpServletRequest request) {
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

    @PostMapping ("dishes/dishForm")
    public String updateOrCreateDish(Model model, HttpServletRequest request) {
        Dish dish = new Dish(request.getParameter("id").isEmpty() ? null : getId(request),
                request.getParameter("description"), Integer.parseInt(request.getParameter("price")));
        Restaurant restaurant = service.getRestaurantById(Integer.parseInt(request.getParameter("restaurantId")));
        dish.setRestaurant(restaurant);
        if (dish.isNew()) {
            service.createDish(dish);
        } else {
            service.updateDish(dish);
        }
        model.addAttribute("id", request.getParameter("restaurantId"));
        return "redirect:/restaurants/update";
    }
}
