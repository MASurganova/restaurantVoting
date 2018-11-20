package ru.voting.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.voting.AuthorizedUser;
import ru.voting.util.exception.TimeDelayException;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/voting")
public class VotingController extends AbstractController {

    @GetMapping("/choose")
    public String delete(HttpServletRequest request) throws TimeDelayException {
        votingService.addVoice(AuthorizedUser.id(), getId(request));
        return "redirect:/voting";
    }

    @GetMapping("/endVoting")
    public String endVoting(HttpServletRequest request) {
        votingService.endVoting();
        return "redirect:/voting";
    }

}
