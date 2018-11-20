package ru.voting.web;

import org.springframework.beans.factory.annotation.Autowired;
import ru.voting.service.UserService;
import ru.voting.service.VotingService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public abstract class AbstractController {

    @Autowired
    protected UserService userService;

    @Autowired
    protected VotingService votingService;

    protected int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
