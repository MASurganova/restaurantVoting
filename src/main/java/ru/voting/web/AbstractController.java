package ru.voting.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import ru.voting.service.UserService;
import ru.voting.service.VotingService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.StringJoiner;

public abstract class AbstractController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected VotingService votingService;

    @Autowired
    protected UserService userService;

    protected String getErrorsMessage(BindingResult result) {
        StringJoiner joiner = new StringJoiner("<br>");
        result.getFieldErrors().forEach(
                fe -> {
                    String msg = fe.getDefaultMessage();
                    if (!msg.startsWith(fe.getField())) {
                        msg = fe.getField() + ' ' + msg;
                    }
                    joiner.add(msg);
                });
        return joiner.toString();
    }
}
