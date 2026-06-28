package main.gui;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.charactermanager.House;

public class LocationScreen {

    // Per-level data: location name, enemy name, lore line, accent hex, overlay rgba, roman numeral
    private static final String[] LOCATION  = {"HAUNTED FOREST", "FIST OF THE FIRST MEN", "FROSTFANGS", "LAND OF ALWAYS WINTER"};
    private static final String[] ENEMY     = {"Wildling", "Giant", "White Walker", "Night King"};
    private static final String[] LORE      = {
        "The trees watch with a hundred savage eyes.\nNothing that enters the Haunted Forest returns unchanged.",
        "Ancient stones mark where giants once gathered.\nThe earth trembles beneath footsteps no man should hear.",
        "Wind cuts like a blade forged from frozen death.\nThe White Walkers rise from a cold older than memory.",
        "The sky is ash. The stars have gone out.\nThe Night King has been waiting since the first Long Night."
    };
    private static final String[] ACCENT    = {"#3A7A2E", "#B8860B", "#4A90D9", "#9B59B6"};
    private static final double[] OVERLAY_R = {0.01, 0.06, 0.01, 0.03};
    private static final double[] OVERLAY_G = {0.06, 0.04, 0.03, 0.01};
    private static final double[] OVERLAY_B = {0.01, 0.01, 0.10, 0.08};
    private static final String[] LEVEL_TAG = {"LEVEL  I", "LEVEL  II", "LEVEL  III", "LEVEL  IV"};

    public static void show(Stage stage, House house, int level, int maxHouseHp, int maxArmor) {
        int i = level - 1;

        // Background
        Image bg = new Image(
            LocationScreen.class.getResourceAsStream("/main/assets/img/bg/startmenubg.png"));
        ImageView bgView = new ImageView(bg);
        bgView.setPreserveRatio(false);
        bgView.setEffect(new GaussianBlur(4));

        // Tinted overlay — each location has its own colour
        Rectangle overlay = new Rectangle();
        overlay.setFill(Color.color(OVERLAY_R[i], OVERLAY_G[i], OVERLAY_B[i], 0.82));

        // Thin coloured top bar
        Rectangle topBar = new Rectangle();
        topBar.setHeight(3);
        topBar.setFill(Color.web(ACCENT[i]));
        topBar.setEffect(new DropShadow(10, Color.web(ACCENT[i])));

        // Level tag  e.g. "LEVEL  III"
        Text levelTag = new Text(LEVEL_TAG[i]);
        levelTag.setFont(Font.font("Georgia", FontWeight.BOLD, 15));
        levelTag.setFill(Color.web(ACCENT[i]));
        levelTag.setOpacity(0);

        // Location name
        Text locationText = new Text(LOCATION[i]);
        locationText.setFont(Fonts.got(64));
        locationText.setFill(Color.WHITE);
        locationText.setEffect(new DropShadow(32, Color.web(ACCENT[i])));
        locationText.setOpacity(0);

        // Thin gold rule
        Region rule = new Region();
        rule.setPrefSize(480, 1);
        rule.setMaxSize(480, 1);
        rule.setStyle("-fx-background-color: " + ACCENT[i] + ";");
        rule.setOpacity(0);

        // Enemy line
        Text enemyLine = new Text("Enemy  ·  " + ENEMY[i]);
        enemyLine.setFont(Font.font("Georgia", FontPosture.ITALIC, 22));
        enemyLine.setFill(Color.color(0.78, 0.78, 0.78, 0.9));
        enemyLine.setOpacity(0);

        // Lore text
        Text loreText = new Text(LORE[i]);
        loreText.setFont(Font.font("Georgia", FontPosture.ITALIC, 16));
        loreText.setFill(Color.color(0.60, 0.60, 0.60, 0.85));
        loreText.setTextAlignment(TextAlignment.CENTER);
        loreText.setLineSpacing(6);
        loreText.setOpacity(0);

        // Enter button
        Button enterBtn = new Button("ENTER  ›");
        enterBtn.setFont(Font.font("Georgia", FontWeight.BOLD, 18));
        enterBtn.setPrefSize(220, 52);
        String baseCss = btnCss(ACCENT[i], false);
        String hoverCss = btnCss(ACCENT[i], true);
        enterBtn.setStyle(baseCss);
        enterBtn.setOnMouseEntered(e -> enterBtn.setStyle(hoverCss));
        enterBtn.setOnMouseExited(e  -> enterBtn.setStyle(baseCss));
        enterBtn.setOpacity(0);
        enterBtn.setOnAction(e -> BattleScreen.show(stage, house, level, maxHouseHp, maxArmor));

        // Layout
        VBox center = new VBox(16, levelTag, locationText, rule, enemyLine, spacer(8), loreText, spacer(20), enterBtn);
        center.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(bgView, overlay, center);
        StackPane.setAlignment(topBar, Pos.TOP_CENTER);
        root.getChildren().add(topBar);

        Scene scene = new Scene(root);
        bgView.fitWidthProperty().bind(scene.widthProperty());
        bgView.fitHeightProperty().bind(scene.heightProperty());
        overlay.widthProperty().bind(scene.widthProperty());
        overlay.heightProperty().bind(scene.heightProperty());
        topBar.widthProperty().bind(scene.widthProperty());

        stage.setScene(scene);
        stage.setMaximized(true);

        // Staggered fade-in for each element
        fadeIn(levelTag,     200,   0);
        fadeIn(locationText, 700, 200);
        fadeIn(rule,         300, 750);
        fadeIn(enemyLine,    400, 950);
        fadeIn(loreText,     500, 1300);
        fadeIn(enterBtn,     500, 1900);
    }

    private static void fadeIn(javafx.scene.Node node, double durationMs, double delayMs) {
        PauseTransition delay = new PauseTransition(Duration.millis(delayMs));
        delay.setOnFinished(e -> {
            FadeTransition ft = new FadeTransition(Duration.millis(durationMs), node);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
        });
        delay.play();
    }

    private static Region spacer(double h) {
        Region r = new Region();
        r.setPrefHeight(h);
        r.setMinHeight(h);
        return r;
    }

    private static String btnCss(String accent, boolean glow) {
        return "-fx-background-color: transparent;" +
               "-fx-text-fill: " + accent + ";" +
               "-fx-border-color: " + accent + ";" +
               "-fx-border-width: 1;" +
               "-fx-border-radius: 2;" +
               "-fx-background-radius: 2;" +
               "-fx-cursor: hand;" +
               (glow ? "-fx-effect: dropshadow(gaussian, " + accent + ", 18, 0.25, 0, 0);" : "");
    }
}
