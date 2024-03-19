package org.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import java.util.Optional;
import org.slf4j.Logger;

import static org.example.demo.ExpenseDAO.fetchALlDataFromDB;

public class ExpenseController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ExpenseController.class);

    @FXML
    private TableView<Expense> expenseTable;

    private final ObservableList<Expense> items = FXCollections.observableArrayList();

    public void initialize() {
        fetchALlDataFromDB(items);
        expenseTable.setItems(items);
    }

    public void addExpense(ActionEvent event) {
        Dialog<Expense> addPersonDialog = new ExpenseDialog();
        Optional<Expense> result = addPersonDialog.showAndWait();
        result.ifPresent(items::add);

        log.debug(result.toString());
        event.consume();
    }

}
