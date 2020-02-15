package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;


@WebServlet("/app/recipe/edit")
public class AppEditRecipe extends HttpServlet {
    RecipeDao recipeDao = new RecipeDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String recipeName = request.getParameter("recipeName");
        String recipeDescription = request.getParameter("recipeDescription");
        int preparationTime = Integer.parseInt(request.getParameter("preparationTime"));
        String preparation = request.getParameter("preparation");
        String ingredients = request.getParameter("ingredients").trim();
        int id = Integer.parseInt(request.getParameter("id"));
        Date date = new Date(System.currentTimeMillis());


        Recipe recipe = new Recipe(id, recipeName, ingredients, recipeDescription, date, preparationTime, preparation);

        recipeDao.update(recipe);

        response.sendRedirect("/app/recipes");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Recipe recipe = recipeDao.read(id);
        String ingredients = recipe.getIngredients();
        String[] ingredientsArray = ingredients.split(", ");

        request.setAttribute("recipe", recipe);
        request.setAttribute("ingredientsArray", ingredientsArray);
        getServletContext().getRequestDispatcher("/app-edit-recipe.jsp").forward(request, response);
    }
}
