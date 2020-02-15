package pl.coderslab.web;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.DayName;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/app/recipe/plan/add")
public class AddRecipeToPlan extends HttpServlet {

    PlanDao planDao = new PlanDao();
    RecipeDao recipeDao = new RecipeDao();
    DayNameDao dayNameDao = new DayNameDao();
    RecipePlanDao recipePlanDao = new RecipePlanDao();
    List<Plan> plans = planDao.findAll();
    List<Recipe> recipes = recipeDao.findAll();
    List<DayName> daynames = dayNameDao.findAll();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String planName = request.getParameter("planName");
        String mealName = request.getParameter("mealName");
        int displayOrder = Integer.parseInt(request.getParameter("displayOrder"));
        String recipeName = request.getParameter("recipeName");
        String dayName = request.getParameter("dayName");
        int planId = planDao.getPlanIdByName(planName);
        int recipeId = recipeDao.getRecipeIdByName(recipeName);
        int dayId = dayNameDao.getDayIdByName(dayName);
        recipePlanDao.addRecipeToPlan(recipeId, mealName, displayOrder, dayId, planId);

        response.sendRedirect("/app/recipe/plan/add");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("plans", plans);
        request.setAttribute("recipes", recipes);
        request.setAttribute("daynames", daynames);

        getServletContext().getRequestDispatcher("/app-schedules-meal-recipe.jsp").forward(request, response);
    }
}
