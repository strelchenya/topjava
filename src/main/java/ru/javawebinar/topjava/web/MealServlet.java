package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.ListStorage;
import ru.javawebinar.topjava.storage.Storage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class MealServlet extends HttpServlet {

    private static final String ALL_MEALS_TO = "/meals.jsp";

    private static final String ADD_OR_EDIT = "/create.jsp";

    private final Storage storage;

    public MealServlet() {
        super();
        this.storage = new ListStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action == null) {
            forward = ALL_MEALS_TO;
            request.setAttribute("meals", storage.getAll());
            request.getRequestDispatcher(forward).forward(request, response);
            return;
        }

        if (action.equalsIgnoreCase("delete")) {

            String id = request.getParameter("id");

            storage.delete(id);

            request.setAttribute("meals", storage.getAll());
            response.sendRedirect("meals");
            return;
        } else if (action.equalsIgnoreCase("edit")) {
            forward = ADD_OR_EDIT;

            String id = request.getParameter("id");

            Meal mealEdit = storage.get(id);

            request.setAttribute("mealEdit", mealEdit);
        } /*else if (action.equalsIgnoreCase("return")) {
            forward = ALL_MEALS_TO;

            request.setAttribute("meals", storage.getAll());
        }*/ else {
            forward = ADD_OR_EDIT;
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("datetime"));

        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(dateTime, description, calories);

        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            storage.add(meal);
        } else {
            meal.setId(id);
            storage.update(meal);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ALL_MEALS_TO);
        request.setAttribute("meals", storage.getAll());
        requestDispatcher.forward(request, response);
    }
}
