package com.example.financemanager.controller;

import com.example.financemanager.db.RevenuDAO;
import com.example.financemanager.model.Revenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import org.slf4j.Logger;

import java.util.Optional;

public class RevenuController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RevenuController.class);

    @FXML
    private TableView<Revenu> revenuTable;

    public void initialize() {
        revenuTable.setItems(RevenuDAO.getRevenus());
    }

    public void addRevenu(ActionEvent event) {
        Dialog<Revenu> addPersonDialog = new RevenuDialog();
        Optional<Revenu> result = addPersonDialog.showAndWait();
        result.ifPresent(RevenuDAO::insertRevenu);

        log.debug(result.toString());
        event.consume();
    }

}