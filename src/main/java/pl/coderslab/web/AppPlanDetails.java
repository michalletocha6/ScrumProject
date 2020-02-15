package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.Plan;
import pl.coderslab.model.RecipePlan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/app/plan/details")
public class AppPlanDetails extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        PlanDao planDao = new PlanDao();
        Plan plan = planDao.read(id);
        RecipePlanDao recipePlanDao = new RecipePlanDao();
        List<RecipePlan> recipePlans = recipePlanDao.readAllRecipesInPlan(id);
        List<String> dayNames = new ArrayList<>();

        for (RecipePlan recipePlan : recipePlans) {
            if (!dayNames.contains(recipePlan.getDayName())) {
                dayNames.add(recipePlan.getDayName());
            }
        }


        request.setAttribute("plan", plan);
        request.setAttribute("dayNames", dayNames);
        request.setAttribute("recipePlans", recipePlans);
        getServletContext().getRequestDispatcher("/app-details-schedules.jsp").forward(request, response);
    }
}
