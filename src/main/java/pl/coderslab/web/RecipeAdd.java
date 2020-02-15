package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;
import pl.coderslab.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/app/recipe/add")
public class RecipeAdd extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Recipe recipe = new Recipe();
        RecipeDao recipeDao = new RecipeDao();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Date date = new Date(System.currentTimeMillis());
        String description = request.getParameter("description");
        String ingredients = request.getParameter("ingredients");
        String name = request.getParameter("name");
        String preparation = request.getParameter("preparation");
        String preparationTime = request.getParameter("preparationTime");
        if (description != "" && ingredients != "" && name != "" && preparation != "" && preparationTime != "") {
            recipe.setCreated(date);
            recipe.setUpdated(date);
            recipe.setDescription(description);
            recipe.setIngredients(ingredients);
            recipe.setName(name);
            recipe.setPreparation(preparation);
            recipe.setPreparationTime(Integer.parseInt(preparationTime));
            recipe.setAdminId(user.getId());
            recipeDao.create(recipe);
            response.sendRedirect(request.getContextPath() + "/app/recipes");
        } else {
            request.setAttribute("description", description);
            request.setAttribute("ingredients", ingredients);
            request.setAttribute("name", name);
            request.setAttribute("preparation", preparation);
            request.setAttribute("preparationTime", preparationTime);
            request.setAttribute("message", "Wypełnij wszystkie pola");
            getServletContext().getRequestDispatcher("/app-add-recipe.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/app-add-recipe.jsp").forward(request, response);
    }
}
