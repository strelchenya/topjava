package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MealServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<MealTo> meals = MealsUtil.filteredByStreams(MealsUtil.MEALS, MealsUtil.START_TIME,
                MealsUtil.END_TIME, MealsUtil.CALORIES_PER_DAY);

        request.setAttribute("meals",meals);

//        PrintWriter printWriter = response.getWriter();
//        printWriter.write("<html>");
//        printWriter.println("<h1>Tobi hona</h1>");
//        printWriter.write("</html>");

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/meals.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
