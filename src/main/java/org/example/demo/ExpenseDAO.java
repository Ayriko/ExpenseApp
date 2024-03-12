package org.example.demo;

import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExpenseDAO {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static boolean insertExpense(Expense newExpense) {
        String query = "INSERT INTO expense (date, housing, food, goingOut, transportation, travel, tax, other) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.connect()) {
            assert connection != null;
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, newExpense.getFormattedDate().format(DATE_FORMAT));
                statement.setFloat(2, newExpense.getHousingV());
                statement.setFloat(3, newExpense.getFoodV());
                statement.setFloat(4, newExpense.getGoingOutV());
                statement.setFloat(5, newExpense.getTransportationV());
                statement.setFloat(6, newExpense.getTravelV());
                statement.setFloat(7, newExpense.getTaxV());
                statement.setFloat(8, newExpense.getOtherV());

                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ObservableList<Expense> fetchALlDataFromDB(ObservableList<Expense> items) {

        String query = "SELECT * FROM expense";

        try (Connection connection = Database.connect()) {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                items.add(new Expense(
                        LocalDate.parse(rs.getString("date"), DATE_FORMAT),
                        rs.getFloat("housing"),
                        rs.getFloat("food"),
                        rs.getFloat("goingOut"),
                        rs.getFloat("transportation"),
                        rs.getFloat("travel"),
                        rs.getFloat("tax"),
                        rs.getFloat("other")));
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
