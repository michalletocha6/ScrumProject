package pl.coderslab.dao;

import pl.coderslab.model.RecipePlan;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipePlanDao {
    private static final String ADD_RECIPE_TO_PLAN_QUERY = "INSERT INTO recipe_plan(recipe_id, meal_name, display_order," +
            " day_name_id, plan_id) VALUES (?, ?, ?, ?, ?);";
    private static final String READ_RECIPE_PLAN_QUERY = "SELECT recipe_plan.id, recipe_id, day_name.name as day_name, meal_name, recipe.name as recipe_name\n" +
            "FROM `recipe_plan`\n" +
            "JOIN day_name on day_name.id=day_name_id\n" +
            "JOIN recipe on recipe.id=recipe_id\n" +
            "WHERE plan_id = ?\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";
    private static final String DELETE_RECIPE_FROM_PLAN_QUERY = "DELETE FROM recipe_plan WHERE id = ?;";
    private static final String READ_LAST_RECIPE_FROM_USER = "SELECT day_name.name as day_name, meal_name, recipe.name as recipe_name, recipe.id AS recipe_id\n" +
            "FROM `recipe_plan`\n" +
            "         JOIN day_name on day_name.id=day_name_id\n" +
            "         JOIN recipe on recipe.id=recipe_id WHERE\n" +
            "        recipe_plan.plan_id = (SELECT MAX(id) from plan WHERE admin_id = ?)\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";

    public void addRecipeToPlan(int recipeId, String mealName, int displayOrder, int dayNameId, int planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(ADD_RECIPE_TO_PLAN_QUERY)) {
            insertStm.setInt(1, recipeId);
            insertStm.setString(2, mealName);
            insertStm.setInt(3, displayOrder);
            insertStm.setInt(4, dayNameId);
            insertStm.setInt(5, planId);
            insertStm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public List<RecipePlan> readAllRecipesInPlan(Integer planId) {
        List<RecipePlan> recipePlans = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_RECIPE_PLAN_QUERY)
        ) {
            statement.setInt(1, planId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    RecipePlan recipePlan = new RecipePlan();
                    recipePlan.setId(resultSet.getInt("id"));
                    recipePlan.setRecipeId(resultSet.getInt("recipe_id"));
                    recipePlan.setDayName(resultSet.getString("day_name"));
                    recipePlan.setMealName(resultSet.getString("meal_name"));
                    recipePlan.setRecipeName(resultSet.getString("recipe_name"));
                    recipePlans.add(recipePlan);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipePlans;

    }

    public List<RecipePlan> readLastRecipePlanFromUser(int userId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_LAST_RECIPE_FROM_USER)
        ) {
            List<RecipePlan> recipePlans = new ArrayList<>();
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                RecipePlan recipePlan = new RecipePlan();
                recipePlan.setDayName(resultSet.getString("day_name"));
                recipePlan.setMealName(resultSet.getString("meal_name"));
                recipePlan.setRecipeName(resultSet.getString("recipe_name"));
                recipePlan.setRecipeId(resultSet.getInt("recipe_id"));
                recipePlans.add(recipePlan);
            }
            return recipePlans;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int recipeId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_RECIPE_FROM_PLAN_QUERY)
        ) {
            statement.setInt(1, recipeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
