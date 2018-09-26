package ru.voting.web;

import org.slf4j.Logger;
import ru.voting.model.Role;
import ru.voting.model.User;
import ru.voting.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends AbstractServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = springContext.getBean(UserService.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        User user = new User(id.isEmpty() ? null : Integer.valueOf(id),
                request.getParameter("name"),
                request.getParameter("email"),
                request.getParameter("password"), request.getParameter("restaurantId").isEmpty() ? null :
                service.getRestaurantById(Integer.parseInt(request.getParameter("restaurantId"))));

        log.info(user.isNew() ? "Create {}" : "Update {}", user);
        if (user.isNew()) userService.create(user);
        else userService.update(user);
        response.sendRedirect("users");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                userService.delete(id);
                response.sendRedirect("users");
                break;
            case "create":
            case "update":
                final User user = "create".equals(action) ?
                        new User(null, "", "", "",  Role.ROLE_USER) :
                        userService.getWithChoice(getId(request));
                request.setAttribute("user", user);
                request.getRequestDispatcher("/jsp/user/userForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("users", userService.getAll());
                request.getRequestDispatcher("/jsp/user/users.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
