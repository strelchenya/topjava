package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MapMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class MealServlet extends HttpServlet {

    private static final String allMealsTo = "/meals.jsp";
    private static final String addOrEdit = "/create.jsp";

    private MealStorage mealStorage;

    @Override
    public void init() throws ServletException {
        super.init();
        this.mealStorage = new MapMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action == null) {
            forward = allMealsTo;
            request.setAttribute("meals",
                    MealsUtil.filteredByStreams(mealStorage.getAll(), MealsUtil.START_TIME,
                            MealsUtil.END_TIME, MealsUtil.CALORIES_PER_DAY));
            request.getRequestDispatcher(forward).forward(request, response);
            return;
        } else {
            action = action.toLowerCase();
        }

        switch (action) {
            case "delete":
                Integer id = Integer.parseInt(request.getParameter("id"));
                mealStorage.delete(id);
                response.sendRedirect("meals");
                return;
            case "edit":
                forward = addOrEdit;
                Integer idMeal = Integer.parseInt(request.getParameter("id"));
                Meal meal = mealStorage.get(idMeal);
                MealTo mealTo = new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(),
                        meal.getCalories(), false);
                request.setAttribute("mealEdit", mealTo);
                break;
            case "add":
                forward = addOrEdit;
                break;
            default:
                response.sendRedirect("meals");
                return;
        }

        /*if (action.equalsIgnoreCase("delete")) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            mealStorage.delete(id);
            response.sendRedirect("meals");
            return;
        } else if (action.equalsIgnoreCase("edit")) {
            forward = addOrEdit;
            Integer idMeal = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealStorage.get(idMeal);
            MealTo mealTo = new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(),
                    meal.getCalories(), false);
            request.setAttribute("mealEdit", mealTo);
        } else if (action.equalsIgnoreCase("add")) {
            forward = addOrEdit;
        } else {
            response.sendRedirect("meals");
            return;
        }*/

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("datetime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String id = request.getParameter("id");
        Meal meal = new Meal(dateTime, description, calories);

        if (id == null || id.isEmpty()) {
            mealStorage.add(meal);
        } else {
            Integer idMeal = Integer.parseInt(id);
            meal.setId(idMeal);
            mealStorage.update(meal);
        }

        response.sendRedirect("meals");
    }
}
