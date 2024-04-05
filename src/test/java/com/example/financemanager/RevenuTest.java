package com.example.financemanager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.MenuItemMatchers;
import org.testfx.robot.Motion;
import org.testfx.util.NodeQueryUtils;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class RevenuTest {

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(FinanceTrackerApplication.class.getResource("dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        scene.getRoot().applyCss();
        stage.show();
    }

    @BeforeEach
    public void setUp(FxRobot robot) throws TimeoutException {
        robot.clickOn("Navigation");

        WaitForAsyncUtils.waitFor(1, TimeUnit.SECONDS, () -> robot.lookup("Tableau de bord").match(NodeQueryUtils.isVisible()).tryQuery().isPresent());

        robot.clickOn("Tableau de bord", Motion.VERTICAL_FIRST, MouseButton.PRIMARY);
    }

    @Test
    public void shouldHaveMenuRevenus(FxRobot robot) {
        verifyThat(".menu-bar", isVisible());
        robot.lookup(".menu-bar").queryAs(MenuBar.class).getMenus().forEach(menu -> {
            verifyThat(menu, MenuItemMatchers.hasText("Navigation"));
            verifyThat(menu.getItems().get(2), MenuItemMatchers.hasText("Tableau des revenus"));
        });
    }

    @Test
    public void shouldChangeStageToRevenuWhenClickOn(FxRobot robot) throws TimeoutException {
        robot.clickOn("Navigation");

        WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, () -> robot.lookup("Tableau des revenus").match(NodeQueryUtils.isVisible()).tryQuery().isPresent());

        robot.clickOn("Tableau des revenus", Motion.DIRECT, MouseButton.PRIMARY);

        verifyThat(".title-text", hasText("Tableau récapitulatif des revenus"));
    }

    @Test
    public void shouldHaveAddButtonInRevenu(FxRobot robot) throws TimeoutException {
        robot.clickOn("Navigation");

        robot.clickOn("Tableau des revenus", Motion.DIRECT, MouseButton.PRIMARY);

        verifyThat("#addButton", hasText("Ajouter"));
    }

    @Test
    public void shouldOpenRevenuDialog(FxRobot robot) throws TimeoutException {
        robot.clickOn("Navigation");

        robot.clickOn("Tableau des revenus", Motion.DIRECT, MouseButton.PRIMARY);

        robot.clickOn("#addButton");

        verifyThat("Créer", isVisible());
    }


    @Test
    public void shouldHaveBarChart(FxRobot robot) {
        verifyThat("#barChart", isVisible());

        assertThat(robot.lookup("#barChart").queryAs(BarChart.class).getTitle(), equalTo("Dépenses VS Revenus"));
    }
}
