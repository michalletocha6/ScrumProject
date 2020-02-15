package pl.coderslab.web;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.DayName;
import pl.coderslab.model.Plan;
import pl.coderslab.model.RecipePlan;
import pl.coderslab.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/app/dashboard")
public class AppDashboard extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        RecipeDao recipeDao = new RecipeDao();
        int recipesAmount = recipeDao.readRecipesAmount(user.getId());

        PlanDao planDao = new PlanDao();
        int plansAmount = planDao.countUserPlans(user.getId());
        Plan lastAdded = planDao.getUserLastPlan(user.getId());

        RecipePlanDao recipePlanDao = new RecipePlanDao();
        List<RecipePlan> recipePlans = recipePlanDao.readLastRecipePlanFromUser(user.getId());

        DayNameDao dayDao = new DayNameDao();
        List<DayName> dayNames = dayDao.findAll();

        request.setAttribute("user", user);
        request.setAttribute("recipesAmount", recipesAmount);
        request.setAttribute("plansAmount", plansAmount);
        request.setAttribute("lastAdded", lastAdded);
        request.setAttribute("recipePlans", recipePlans);
        request.setAttribute("dayNames", dayNames);

        getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}
