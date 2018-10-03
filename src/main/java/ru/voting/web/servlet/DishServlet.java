package ru.voting.web.servlet;

import org.slf4j.Logger;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class DishServlet extends AbstractServlet {
    private static final Logger log = getLogger(DishServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Restaurant restaurant = service.getRestaurantByIdWithLunch(Integer.valueOf(request.getParameter("restaurantId")));

        Dish dish = new Dish(id.isEmpty() ? null : Integer.valueOf(id),
                request.getParameter("description"), Integer.parseInt(request.getParameter("price")));
        dish.setRestaurant(restaurant);
        log.info(dish.isNew() ? "Create {}" : "Update {}", dish);
        if (dish.isNew()) service.createDish(dish);
        else service.updateDish(dish);
//        request.setAttribute("restaurant", restaurant);
        response.sendRedirect("restaurants?action=update&id=" + restaurant.getId());
//        request.getRequestDispatcher("/jsp/restaurant/restaurantForm.jsp").forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Restaurant restaurant = service.getRestaurantByIdWithLunch(Integer.valueOf(request.getParameter("restaurantId")));

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                service.deleteDish(id);
                response.sendRedirect("restaurants?action=update&id=" + restaurant.getId());
                break;
            case "create":
            case "update":
                final Dish dish = "create".equals(action) ?
                        new Dish(null, "", 0,
                                service.getRestaurantById(Integer.valueOf(request.getParameter("restaurantId")))) :
                        service.getDishById(getId(request));
                request.setAttribute("dish", dish);
                request.getRequestDispatcher("/WEB-INF/jsp/dishForm.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
