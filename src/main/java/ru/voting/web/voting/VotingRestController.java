package ru.voting.web.voting;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.voting.AuthorizedUser;
import ru.voting.model.Restaurant;
import ru.voting.util.exception.TimeDelayException;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(VotingRestController.REST_URL)
public class VotingRestController extends AbstractVotingController {
    static final String REST_URL = "/rest/profile/voting";


    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getEnabledRestaurants() {
        return super. getEnabledRestaurants();
    }


    //Время нужно изменить на null
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addVote(@RequestBody Restaurant restaurant) throws TimeDelayException {
        super.addVoice(AuthorizedUser.id(), restaurant.getId(), LocalTime.of(10, 20));
    }

}