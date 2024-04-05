package com.example.financemanager.db;

import com.example.financemanager.model.Revenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RevenuDAO {

    private static final Logger log = LoggerFactory.getLogger(RevenuDAO.class);

    private static final String tableName = "revenu";
    private static final String periodColumn = "period";
    private static final String salaryColumn = "salary";
    private static final String helpColumn = "help";
    private static final String selfEmployedColumn = "selfEmployed";
    private static final String passifColumn = "passif";
    private static final String otherColumn = "other";

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final ObservableList<Revenu> Revenus;

    static {
        Revenus = FXCollections.observableArrayList();
        fetchAllDataFromDB();
    }

    public static ObservableList<Revenu> getRevenus() {
        return FXCollections.unmodifiableObservableList(Revenus.sorted(Revenu::compareTo));
    }

    private static void fetchAllDataFromDB() {

        String query = "SELECT * FROM " + tableName;

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            Revenus.clear();
            while (rs.next()) {
                Revenus.add(new Revenu(
                        LocalDate.parse(rs.getString(periodColumn), DATE_FORMAT),
                        rs.getFloat(salaryColumn),
                        rs.getFloat(helpColumn),
                        rs.getFloat(selfEmployedColumn),
                        rs.getFloat(passifColumn),
                        rs.getFloat(otherColumn)));
            }
        } catch (SQLException e) {
            log.error("Could not load Revenus from database", e);
            Revenus.clear();
        }
    }

    public static void insertRevenu(Revenu revenu) {
        //update database
        String query = "INSERT INTO Revenu(period, salary, help, selfEmployed, passif, other) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, revenu.getPeriod().format(DATE_FORMAT));
            statement.setFloat(2, revenu.getSalary());
            statement.setFloat(3, revenu.getHelp());
            statement.setFloat(4, revenu.getSelfEmployed());
            statement.setFloat(5, revenu.getPassif());
            statement.setFloat(6, revenu.getOther());

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Could not insert Revenus in database", e);
        }

        //update cache
        Revenus.add(revenu);
    }

    public static List<Revenu> findLastRevenusEndingAtCurrentMonth(int numberOfLine, LocalDate currentMonth) {
        String query = "SELECT * FROM " + tableName
                + " WHERE " + periodColumn + " <= '" + currentMonth.format(DATE_FORMAT)
                + "' ORDER BY " + periodColumn + " DESC LIMIT " + numberOfLine;

        List<Revenu> lastRevenus = new ArrayList<>();

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                lastRevenus.add(new Revenu(
                        LocalDate.parse(rs.getString(periodColumn), DATE_FORMAT),
                        rs.getFloat(salaryColumn),
                        rs.getFloat(helpColumn),
                        rs.getFloat(selfEmployedColumn),
                        rs.getFloat(passifColumn),
                        rs.getFloat(otherColumn)));
            }
        } catch (SQLException e) {
            log.error("Could not load Revenus from database", e);
        }
        return lastRevenus;

    }
}