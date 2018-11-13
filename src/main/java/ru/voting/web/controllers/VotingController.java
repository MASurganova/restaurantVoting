package ru.voting.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.voting.AuthorizedUser;
import ru.voting.service.VotingService;
import ru.voting.util.exception.TimeDelayException;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping(value = "/voting")
public class VotingController extends AbstractController {

    @Autowired
    VotingService service;

    @GetMapping("/choose")
    public String delete(HttpServletRequest request) throws TimeDelayException {
        service.addVoice(AuthorizedUser.id(), getId(request));
        return "redirect:/voting";
    }

    @GetMapping("/endVoting")
    public String endVoting(HttpServletRequest request) {
        service.endVoting();
        return "redirect:/voting";
    }

}
