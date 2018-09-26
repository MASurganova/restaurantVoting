package ru.voting.web;

import org.slf4j.Logger;
import ru.voting.model.Restaurant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class RestaurantServlet extends AbstractServlet {
    private static final Logger log = getLogger(RestaurantServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Restaurant restaurant;
        if (id.isEmpty()) {
            restaurant = new Restaurant(null,
                    request.getParameter("name"));
        } else {
            restaurant = service.getRestaurantById(Integer.valueOf(id));
            restaurant.setName(request.getParameter("name"));
        }

        log.info(restaurant.isNew() ? "Create {}" : "Update {}", restaurant);
        if (restaurant.isNew()) service.createRestaurant(restaurant);
        else service.updateRestaurant(restaurant);
        request.setAttribute("restaurants", service.getAllRestaurants());
        request.getRequestDispatcher("/jsp/restaurant/restaurants.jsp").forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                service.deleteRestaurant(id);
                response.sendRedirect("restaurants");
                break;
            case "create":
            case "update":
                final Restaurant restaurant = "create".equals(action) ?
                        new Restaurant(null, "") :
                        service.getRestaurantByIdWithLunch(getId(request));
                request.setAttribute("restaurant", restaurant);
                request.getRequestDispatcher("/jsp/restaurant/restaurantForm.jsp").forward(request, response);
                break;
            case "enabled":
                service.addRestaurantToVote(getId(request));
                request.setAttribute("restaurants", service.getAllRestaurants());
                request.getRequestDispatcher("/jsp/restaurant/restaurants.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("restaurants", service.getAllRestaurants());
                request.getRequestDispatcher("/jsp/restaurant/restaurants.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
