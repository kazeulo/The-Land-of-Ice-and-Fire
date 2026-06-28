package main.gui;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.charactermanager.House;

/**
 * Dramatic pre-boss cutscene replacing the generic LocationScreen for the Night King.
 * The Night King speaks, then the player responds in their house's voice.
 */
public class NightKingCutscene {

    private static final String NIGHT_KING_SPEECH =
        "\"You have come far, little warrior.  Further than most.\n\n" +
        "I remember the first Long Night. I remember the heroes who thought they could stop me then.\n\n" +
        "I am still here.\n\n" +
        "They are not.\n\n" +
        "Come, then.  Add your name to the list of the forgotten.\"";

    private static String getResponse(House house) {
        return switch (house.getName()) {
            case "Targaryen" -> "\"Drogon and I have heard worse from better men.   Dracarys.\"";
            case "Lannister" -> "\"A Lannister never forgets either.  And I have come to collect.\"";
            default          -> "\"Then let's end this.  For the North.  For the living.\"";
        };
    }

    public static void show(Stage stage, House house, int maxHouseHp, int maxArmor) {
        AudioManager.play(AudioManager.Track.MENU);

        Image bg = new Image(
            NightKingCutscene.class.getResourceAsStream("/main/assets/img/bg/startmenubg.png"));
        ImageView bgView = new ImageView(bg);
        bgView.setPreserveRatio(false);

        Rectangle overlay = new Rectangle();
        overlay.setFill(Color.color(0.03, 0.0, 0.07, 0.90));

        // Night King label
        Text nkLabel = new Text("THE  NIGHT  KING  SPEAKS");
        nkLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 12));
        nkLabel.setFill(Color.web("#9B59B6BB"));
        nkLabel.setOpacity(0);

        // Night King speech
        Text speech = new Text(NIGHT_KING_SPEECH);
        speech.setFont(Font.font("Georgia", FontPosture.ITALIC, 17));
        speech.setFill(Color.color(0.70, 0.70, 0.82, 0.92));
        speech.setTextAlignment(TextAlignment.CENTER);
        speech.setLineSpacing(8);
        speech.setWrappingWidth(640);
        speech.setEffect(new DropShadow(14, Color.web("#9B59B6")));
        speech.setOpacity(0);

        // Divider
        Region divider = new Region();
        divider.setPrefSize(420, 1);
        divider.setMaxSize(420, 1);
        divider.setStyle("-fx-background-color: #6C3483;");
        divider.setOpacity(0);

        // Player response label
        Text playerLabel = new Text("YOUR  ANSWER");
        playerLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 12));
        playerLabel.setFill(Color.web("#C8A84BBB"));
        playerLabel.setOpacity(0);

        // Player response
        Text response = new Text(getResponse(house));
        response.setFont(Font.font("Georgia", FontPosture.ITALIC, 16));
        response.setFill(Color.web("#C8A84B"));
        response.setTextAlignment(TextAlignment.CENTER);
        response.setWrappingWidth(580);
        response.setOpacity(0);

        // Face Him button
        Button faceBtn = new Button("FACE  THE  NIGHT  KING");
        faceBtn.setFont(Fonts.got(18));
        faceBtn.setPrefSize(340, 58);
        String base  = "-fx-background-color:#3B0000;-fx-text-fill:#E8DAAF;" +
                       "-fx-border-color:#9B59B6;-fx-border-width:1;" +
                       "-fx-border-radius:2;-fx-background-radius:2;-fx-cursor:hand;";
        String hover = "-fx-background-color:#6E0000;-fx-text-fill:#F5D060;" +
                       "-fx-border-color:#C8A84B;-fx-border-width:1;" +
                       "-fx-border-radius:2;-fx-background-radius:2;-fx-cursor:hand;" +
                       "-fx-effect:dropshadow(gaussian,#C8A84B,22,0.3,0,0);";
        faceBtn.setStyle(base);
        faceBtn.setOnMouseEntered(e -> faceBtn.setStyle(hover));
        faceBtn.setOnMouseExited(e  -> faceBtn.setStyle(base));
        faceBtn.setOpacity(0);
        faceBtn.setOnAction(e -> {
            AudioManager.play(AudioManager.Track.BATTLE);
            BattleScreen.show(stage, house, 4, maxHouseHp, maxArmor);
        });

        VBox content = new VBox(12,
            nkLabel, spacer(4), speech,
            spacer(10), divider, spacer(10),
            playerLabel, response,
            spacer(28), faceBtn);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(50, 40, 50, 40));

        StackPane root = new StackPane(bgView, overlay, content);
        Scene scene = new Scene(root);
        bgView.fitWidthProperty().bind(scene.widthProperty());
        bgView.fitHeightProperty().bind(scene.heightProperty());
        overlay.widthProperty().bind(scene.widthProperty());
        overlay.heightProperty().bind(scene.heightProperty());

        stage.setScene(scene);

        // Staggered reveal — the Night King takes his time
        fadeIn(nkLabel,     300,    0);
        fadeIn(speech,     1400,  350);
        fadeIn(divider,     400, 2000);
        fadeIn(playerLabel, 300, 2500);
        fadeIn(response,    900, 2900);
        fadeIn(faceBtn,     700, 4000);
    }

    private static void fadeIn(javafx.scene.Node node, double ms, double delayMs) {
        PauseTransition delay = new PauseTransition(Duration.millis(delayMs));
        delay.setOnFinished(e -> {
            FadeTransition ft = new FadeTransition(Duration.millis(ms), node);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
        });
        delay.play();
    }

    private static Region spacer(double h) {
        Region r = new Region();
        r.setPrefHeight(h);
        return r;
    }
}
