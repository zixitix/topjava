package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to users");
        List<MealTo> mealTos =  MealsUtil.getWithExceeded(MealsUtil.getMeals(),2000);
        request.setAttribute("MealTo",mealTos);
        request.getRequestDispatcher("/meal.jsp").forward(request,response);

//        request.getRequestDispatcher("/users.jsp").forward(request, response);

        //response.sendRedirect("users.jsp");
    }
}
