package ru.voting.web;

import org.slf4j.Logger;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.repository.DishRepository;
import ru.voting.repository.RestaurantRepository;
import ru.voting.service.VotingService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class DishServlet extends HttpServlet {
    private static final Logger log = getLogger(DishServlet.class);

    private RestaurantRepository restaurants;
    private DishRepository dishes;
    private VotingService service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        restaurants = new RestaurantRepository();
        service = new VotingService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Restaurant restaurant = restaurants.get(Integer.parseInt("restaurant"));

        Dish dish = new Dish(id.isEmpty() ? null : Integer.valueOf(id),
                request.getParameter("name"), Integer.parseInt(request.getParameter("price")),
                restaurant);

        log.info(dish.isNew() ? "Create {}" : "Update {}", dish);
        service.addDishToLunch(restaurant, dish);
        request.setAttribute("restaurant", restaurant);
        request.getRequestDispatcher("/jsp/user/restaurantForm.jsp").forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Restaurant restaurant = restaurants.get(Integer.parseInt("restaurant"));

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                service.removeDishFromLunch(restaurant, id);
                request.setAttribute("restaurant", restaurant);
                request.getRequestDispatcher("/jsp/user/restaurantForm.jsp").forward(request, response);
                break;
            case "create":
            case "update":
                final Dish dish = "create".equals(action) ?
                        new Dish(null, "", 0, restaurant) :
                        dishes.get(getId(request));
                request.setAttribute("dish", dish);
                request.getRequestDispatcher("/jsp/user/dishForm.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
