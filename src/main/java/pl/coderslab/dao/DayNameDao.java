package pl.coderslab.dao;

import pl.coderslab.model.Book;
import pl.coderslab.model.DayName;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DayNameDao {
    private static final String FIND_ALL_DAYNAMES_QUERY = "SELECT * FROM day_name;";

    public List<DayName> findAll() {
        List<DayName> dayNameList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_DAYNAMES_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                DayName dayNameToAdd = new DayName();
                dayNameToAdd.setId(resultSet.getInt("id"));
                dayNameToAdd.setName(resultSet.getString("name"));
                dayNameToAdd.setDisplayOrder(resultSet.getInt("display_order"));
                dayNameList.add(dayNameToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dayNameList;

    }

    public int getDayIdByName(String dayName) {
        List<DayName> daynames = findAll();
        int dayId = 0;
        for (DayName day : daynames) {
            if (dayName.equals(day.getName())) {
                dayId = day.getId();
            }
        }

        return dayId;
    }


    public String getDayNameById(int dayId) {
        List<DayName> daynames = findAll();
        String dayName = "";
        for (DayName day : daynames) {
            if (dayId == day.getId()) {
                dayName = day.getName();
            }
        }
        return dayName;
    }


}
