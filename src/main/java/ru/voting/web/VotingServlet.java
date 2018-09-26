package ru.voting.web;

import org.slf4j.Logger;
import ru.voting.AuthorizedUser;
import ru.voting.util.exception.TimeDelayException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class VotingServlet extends AbstractServlet {
    private static final Logger log = getLogger(VotingServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action == null ? "all" : action) {
                case "choose":
                    int id = getId(request);
                    log.info("Choose {}", id);
                    service.addVoice(AuthorizedUser.id(), id);
                    break;
                case "endVoting":
                    log.info("End voting by admin {}", AuthorizedUser.id());
                    service.endVoting();
                    break;
            }
            log.info("getAll");
            request.setAttribute("restaurants", service.getCurrentRestaurants());
            request.setAttribute("userId", AuthorizedUser.id());
            request.getRequestDispatcher("/jsp/voting.jsp").forward(request, response);
        } catch (TimeDelayException e) { request.getRequestDispatcher("/jsp/errors.jsp").forward(request, response); }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        request.setAttribute("userId", AuthorizedUser.id());
        request.getRequestDispatcher("/jsp/start.jsp").forward(request, response);
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
