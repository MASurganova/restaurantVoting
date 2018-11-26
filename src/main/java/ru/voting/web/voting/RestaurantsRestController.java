package ru.voting.web.voting;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.model.User;
import ru.voting.util.exception.TimeDelayException;

import java.net.URI;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(RestaurantsRestController.REST_URL)
public class RestaurantsRestController extends AbstractVotingController {
    static final String REST_URL = "/rest/admin/restaurants";

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id) {
        return super.getWithLunch(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllRestaurants() {
        return super.getAllRestaurants();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        Restaurant created = super.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }


    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Restaurant restaurant, @PathVariable("id") int id) {
        super.update(restaurant, id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}/enabled", produces = MediaType.APPLICATION_JSON_VALUE)
    public void addRestaurantToVote(@RequestBody Restaurant restaurant) {
        super.addRestaurantToVote(restaurant);
    }

    @PostMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createDishWithLocation(@PathVariable("id") int restaurantId, @RequestBody Dish dish) {
        Dish created = super.createDish(dish, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @DeleteMapping(value = "/{id}/{dishId}")
    public void deleteDish(@PathVariable("id") int id, @PathVariable("dishId") int dishId) {
        super.deleteDish(id, dishId);
    }

    @Override
    @GetMapping(value = "/{id}/{dishId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish getDish(@PathVariable("id") int id, @PathVariable("dishId") int dishId) {
        return super.getDish(dishId, id);
    }


    @Override
    @PutMapping(value = "/{id}/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateDish(@PathVariable("id") int restaurantId, @RequestBody Dish dish, @PathVariable("dishId") int id) {
        super.updateDish(restaurantId, dish, id);
    }

    @PutMapping(value = "/end")
    public void end() {
        super.endVoting();
    }
}