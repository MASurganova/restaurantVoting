package ru.voting.web;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import ru.voting.model.Restaurant;
import ru.voting.model.Dish;
import ru.voting.service.VotingService;
import ru.voting.util.ValidationUtil;

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

    private ConfigurableApplicationContext springContext;

    private VotingService service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        springContext = ValidationUtil.getSpringContext();
        service = springContext.getBean(VotingService.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Restaurant restaurant = service.getRestaurantById(Integer.valueOf(request.getParameter("restaurant")));

        Dish dish = new Dish(id.isEmpty() ? null : Integer.valueOf(id),
                request.getParameter("description"), Integer.parseInt(request.getParameter("price")));

        log.info(dish.isNew() ? "Create {}" : "Update {}", dish);
        service.addDishToLunch(restaurant, dish);
        request.setAttribute("restaurant", restaurant);
        request.getRequestDispatcher("/jsp/restaurant/restaurantForm.jsp").forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Restaurant restaurant = service.getRestaurantById(Integer.valueOf(request.getParameter("restaurant")));

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                service.removeDishFromLunch(restaurant, id);
                request.setAttribute("restaurant", restaurant);
                request.getRequestDispatcher("/jsp/restaurant/restaurantForm.jsp").forward(request, response);
                break;
            case "create":
            case "update":
                final Dish dish = "create".equals(action) ?
                        new Dish(null, "", 0,
                                service.getRestaurantById(Integer.valueOf(request.getParameter("restaurant")))) :
                        service.getDishById(getId(request));
                request.setAttribute("dish", dish);
                request.getRequestDispatcher("/jsp/restaurant/dishForm.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
