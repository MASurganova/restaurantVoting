package ru.voting.web.voting;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.model.Role;
import ru.voting.model.User;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ajax/admin/restaurants")
public class RestaurantsAjaxController extends AbstractVotingController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        return super.getAllRestaurants();
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @GetMapping(value = "/{id}/{dishId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish getDish(@PathVariable("id") int id, @PathVariable("dishId") int dishId) {
        return super.getDish(dishId, id);
    }

    @Override
    @DeleteMapping("/{id}/{dishId}")
    public void deleteDish (@PathVariable("id") int id, @PathVariable("dishId") int dishId) {
        super.deleteDish(id, dishId);
    }

    @PostMapping
    public void create(@RequestParam("id") Integer id,
                               @RequestParam("name") String name) {

        Restaurant restaurant = new Restaurant(id, name);
        if (restaurant.isNew()) {
            super.create(restaurant);
        }
    }

    @PostMapping("/{id}")
    public void createOrUpdateDish(@RequestParam("id") Integer id, @RequestParam("dishId") Integer dishId,
                               @RequestParam("description") String description, @RequestParam("price") Integer price) {

        Dish dish = new Dish(dishId, description, price );
        if (dish.isNew()) {
            super.createDish(dish, id);
        } else super.updateDish(id, dish, dishId);
    }
}