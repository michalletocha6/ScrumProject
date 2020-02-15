package pl.coderslab.dao;

import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class    RecipeDao {
    private static final String CREATE_RECIPE_QUERY = "INSERT INTO recipe(name, ingredients, description, created, " +
            "updated, preparation_time, preparation, admin_id) VALUES (?,?,?,?,?,?,?,?);";
    private static final String READ_RECIPE_QUERY = "SELECT id, name, ingredients, description, created, updated, " +
            "preparation_time, preparation, admin_id FROM recipe WHERE id = ?;";
    private static final String UPDATE_RECIPE_QUERY = "UPDATE recipe SET name = ? , ingredients = ?, description = ?, " +
            "updated = ?, preparation_time = ?, preparation = ? WHERE id = ?;";
    private static final String DELETE_RECIPE_QUERY = "DELETE FROM recipe WHERE id = ?;";
    private static final String FIND_ALL_RECIPES_QUERY = "SELECT id, name, ingredients, description, created, updated, " +
            "preparation_time, preparation, admin_id FROM recipe;";
    private static final String FIND_ALL_RECIPES_BY_CREATED_QUERY = "SELECT id, name, ingredients, description, created, updated, " +
            "preparation_time, preparation, admin_id FROM recipe ORDER BY created DESC;";
    private static final String READ_RECIPES_AMOUNT = "SELECT COUNT(*) AS number FROM recipe WHERE admin_id = ?;";
    private static final String READ_RECIPE_PLAN = "SELECT * FROM recipe JOIN recipe_plan ON recipe.id = recipe_plan.recipe_id WHERE recipe_plan.plan_id = ?;";

    public Recipe create(Recipe recipe) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(CREATE_RECIPE_QUERY, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, recipe.getName());
            statement.setString(2, recipe.getIngredients());
            statement.setString(3, recipe.getDescription());
            Date created = recipe.getCreated();
            statement.setTimestamp(4, new Timestamp(created.getTime()));
            Date updated = recipe.getUpdated();
            if (updated != null) {
                statement.setTimestamp(5, new Timestamp(updated.getTime()));
            } else {
                statement.setTimestamp(5, null);
            }
            statement.setInt(6, recipe.getPreparationTime());
            statement.setString(7, recipe.getPreparation());
            statement.setInt(8, recipe.getAdminId());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                recipe.setId(rs.getInt(1));
            }
            return recipe;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Recipe read(int recipeId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(READ_RECIPE_QUERY)
        ) {
            statement.setInt(1, recipeId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(rs.getInt("id"));
                recipe.setName(rs.getString("name"));
                recipe.setIngredients(rs.getString("ingredients"));
                recipe.setDescription(rs.getString("description"));
                recipe.setCreated(rs.getTimestamp("created"));
                recipe.setUpdated(rs.getTimestamp("updated"));
                recipe.setPreparationTime(rs.getInt("preparation_time"));
                recipe.setPreparation(rs.getString("preparation"));
                recipe.setAdminId(rs.getInt("admin_id"));

                return recipe;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int readRecipesAmount(int userId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(READ_RECIPES_AMOUNT)
        ) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int number = rs.getInt("number");
                return number;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void update(Recipe recipe) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_RECIPE_QUERY)
        ) {
            statement.setString(1, recipe.getName());
            statement.setString(2, recipe.getIngredients());
            statement.setString(3, recipe.getDescription());
            statement.setTimestamp(4, new Timestamp(recipe.getUpdated().getTime()));
            statement.setInt(5, recipe.getPreparationTime());
            statement.setString(6, recipe.getPreparation());
//            statement.setInt(7, recipe.getAdminId());
            statement.setInt(7, recipe.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int recipeId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_RECIPE_QUERY)
        ) {
            statement.setInt(1, recipeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Recipe> findAll() {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(FIND_ALL_RECIPES_QUERY)
        ) {
            List<Recipe> recipes = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(rs.getInt("id"));
                recipe.setName(rs.getString("name"));
                recipe.setIngredients(rs.getString("ingredients"));
                recipe.setDescription(rs.getString("description"));
                recipe.setCreated(rs.getTimestamp("created"));
                recipe.setUpdated(rs.getTimestamp("updated"));
                recipe.setPreparationTime(rs.getInt("preparation_time"));
                recipe.setPreparation(rs.getString("preparation"));
                recipe.setAdminId(rs.getInt("admin_id"));
                recipes.add(recipe);
            }
            return recipes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Recipe> findAllSorted() {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(FIND_ALL_RECIPES_BY_CREATED_QUERY)
        ) {
            List<Recipe> recipes = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(rs.getInt("id"));
                recipe.setName(rs.getString("name"));
                recipe.setIngredients(rs.getString("ingredients"));
                recipe.setDescription(rs.getString("description"));
                recipe.setCreated(rs.getTimestamp("created"));
                recipe.setUpdated(rs.getTimestamp("updated"));
                recipe.setPreparationTime(rs.getInt("preparation_time"));
                recipe.setPreparation(rs.getString("preparation"));
                recipe.setAdminId(rs.getInt("admin_id"));
                recipes.add(recipe);
            }
            return recipes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getRecipeIdByName(String recipeName) {
        List<Recipe> recipes = findAll();
        int recipeId = 0;
        for (Recipe recipe : recipes) {
            if (recipeName.equals(recipe.getName())) {
                recipeId = recipe.getId();
            }
        }
        return recipeId;
    }


    public Recipe readPlan(int planId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(READ_RECIPE_PLAN)
        ) {
            statement.setInt(1, planId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(rs.getInt("id"));
                recipe.setName(rs.getString("name"));
                recipe.setIngredients(rs.getString("ingredients"));
                recipe.setDescription(rs.getString("description"));
                recipe.setCreated(rs.getTimestamp("created"));
                recipe.setUpdated(rs.getTimestamp("updated"));
                recipe.setPreparationTime(rs.getInt("preparation_time"));
                recipe.setPreparation(rs.getString("preparation"));
                recipe.setAdminId(rs.getInt("admin_id"));

                return recipe;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
