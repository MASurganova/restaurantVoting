package ru.voting.web.voting;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.to.DishTo;
import ru.voting.util.DishUtil;
import ru.voting.util.UserUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ajax/admin/restaurants")
public class RestaurantsAjaxController extends AbstractVotingController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        return super.getAllRestaurants();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getRestaurantLunch(@PathVariable("id") int id) {
        return super.getWithLunch(id).getLunch();
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
    public void create(@RequestParam("name") String name) {

        Restaurant restaurant = new Restaurant(null, name);
        super.create(restaurant);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> createOrUpdateDish(@PathVariable("id") int id, @Valid DishTo dishTo, BindingResult result) {

        if (result.hasErrors()) {
            String errorsMessage = getErrorsMessage(result);
            return new ResponseEntity<>(errorsMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (dishTo.isNew()) {
            super.createDish(DishUtil.createNewFromTo(dishTo), id);
        } else super.updateDish(id, dishTo, dishTo.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/update")
    public void updateRestaurant(@PathVariable ("id") int id, @RequestParam("name") String name) {
        Restaurant restaurant = new Restaurant(id, name);
        super.update(restaurant, id);
    }


    @PostMapping(value = "/{id}/enabled")
    public void enable(@PathVariable("id") int id) {
        super.addRestaurantToVote(id);
    }
}