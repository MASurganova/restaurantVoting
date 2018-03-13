package ru.voting.web;

import org.slf4j.Logger;
import ru.voting.model.Restaurant;
import ru.voting.model.Role;
import ru.voting.model.User;
import ru.voting.repository.RestaurantRepository;
import ru.voting.repository.UserRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class RestaurantServlet extends HttpServlet {
    private static final Logger log = getLogger(RestaurantServlet.class);

    private RestaurantRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new RestaurantRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Restaurant restaurant = new Restaurant(id.isEmpty() ? null : Integer.valueOf(id),
                request.getParameter("name"));

        log.info(restaurant.isNew() ? "Create {}" : "Update {}", restaurant);
        repository.save(restaurant);
        response.sendRedirect("restaurants");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                repository.delete(id);
                response.sendRedirect("restaurants");
                break;
            case "create":
            case "update":
                final Restaurant restaurant = "create".equals(action) ?
                        new Restaurant(null, "") :
                        repository.get(getId(request));
                request.setAttribute("restaurant", restaurant);
                request.getRequestDispatcher("/jsp/user/restaurantForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("restaurants", repository.getAll());
                request.getRequestDispatcher("/jsp/restaurant/restaurants.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
