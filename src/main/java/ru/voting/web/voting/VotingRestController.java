package ru.voting.web.voting;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.util.exception.TimeDelayException;

import java.net.URI;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(VotingRestController.REST_URL)
public class VotingRestController extends AbstractVotingController {
    static final String REST_URL = "/rest/profile/voting";


    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getEnabledRestaurants() {
        return super. getEnabledRestaurants();
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addVote(@PathVariable("id") int id, @RequestBody Restaurant restaurant) throws TimeDelayException {
        super.addVoice(id, restaurant, LocalTime.of(10, 20));
    }

}