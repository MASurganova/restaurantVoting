package ru.voting.web.voting;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.voting.AuthorizedUser;
import ru.voting.model.Restaurant;
import ru.voting.util.exception.TimeDelayException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(VotingAjaxController.REST_URL)
public class VotingAjaxController extends AbstractVotingController {
    static final String REST_URL = "/ajax/profile/voting";


    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getEnabledRestaurants() {
        return super. getEnabledRestaurants();
    }


//    Не забудь поменять время на текущее
    @PostMapping(value="/{id}")
    public void addVote(@PathVariable int id) throws TimeDelayException {
        super.addVoice(AuthorizedUser.id(), id, LocalTime.of(10, 20));
    }

    @GetMapping("/end")
    public void endVoting() {
        votingService.endVoting();
    }

    @GetMapping("/start")
    public void startVoting() {
        votingService.startVoting();
    }
}