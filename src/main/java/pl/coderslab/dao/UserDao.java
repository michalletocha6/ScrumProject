package pl.coderslab.dao;

import pl.coderslab.model.User;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static final String CREATE_ADMIN_QUERY =
            "INSERT INTO admins ( first_name, last_name, email, password) VALUES(?,?,?,?);";
    private static final String GET_USER_BY_EMAIL_QUERY =
            "SELECT * FROM admins where email = ?;";
    private static final String UPDATE_ADMIN_QUERY =
            "UPDATE admins SET first_name = ?,last_name=?, email = ?, password = ?,superadmin=? where id = ?;";
    private static final String CHANGEPSW_ADMIN_QUERY =
            "UPDATE admins SET  password =? where id = ?;";
    private static final String DELETE_ADMIN_QUERY =
            "DELETE FROM admins WHERE id = ?;";
    private static final String FIND_ADMIN_QUERY =
            "SELECT * FROM admins WHERE id = ?;";
    private static final String FIND_ALL_ADMINS_QUERY =
            "SELECT * FROM admins;";
    private static final String UPDATE_ADMIN_STATUS =
            "UPDATE admins SET enable = ? where id = ?;";



    public User create(User user) {
        try (final Connection conn = DbUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(CREATE_ADMIN_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.executeUpdate();

            final ResultSet gk = ps.getGeneratedKeys();
            if (gk.next()) {
                final int adminId = gk.getInt(1);
                user.setId(adminId);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public User read(int userId) {
        try (final Connection conn = DbUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(FIND_ADMIN_QUERY);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setSuperadmin(rs.getInt("superadmin"));
                user.setEnable(rs.getInt("enable"));

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void update(User user) {
        try (final Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_ADMIN_QUERY);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setInt(5, user.getSuperadmin());

            statement.setInt(6, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changePassword(String password, int userId) {
        try (final Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CHANGEPSW_ADMIN_QUERY);
            statement.setInt(2, userId);
            statement.setString(1, password);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int user) {
        try (final Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_ADMIN_QUERY);
            statement.setInt(1, user);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User selectByEmail(String email) {
        try (final Connection conn = DbUtil.getConnection()) {
            final PreparedStatement ps = conn.prepareStatement(GET_USER_BY_EMAIL_QUERY);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setSuperadmin(rs.getInt("superadmin"));
                user.setEnable(rs.getInt("enable"));

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ADMINS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setSuperadmin(resultSet.getInt("superadmin"));
                user.setEnable(resultSet.getInt("enable"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;

    }

    public void userStatus(int action, int userId) {
        try (final Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_ADMIN_STATUS);
            statement.setInt(1, action);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}