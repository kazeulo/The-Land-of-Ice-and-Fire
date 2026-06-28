package main.gui;

import javafx.animation.FadeTransition;
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
 * Shown after each battle victory (levels 1, 2, 3).
 * Bridges the narrative between fights and gives each house a unique voice.
 */
public class VictoryNarrativeScreen {

    private static final String[] TITLES = {
        "THE FOREST GROWS QUIET",
        "THE FIST STANDS SILENT",
        "ONE REMAINS"
    };

    private static final String[] BODIES = {
        "The last Wildling falls. The Haunted Forest, once alive with war cries and the clash " +
        "of bone, falls into an eerie silence. You stand among the fallen, breath misting in " +
        "the frozen air.\n\n" +
        "Beyond this forest, the world grows darker. The Fist of the First Men awaits — " +
        "where Giants once stood watch over the ancient world, and now serve something far worse.\n\n" +
        "You press north.",

        "The Giant crumbles to the earth, shaking the ground beneath your feet. The ancient " +
        "stones of the Fist of the First Men stand witness to another chapter of blood.\n\n" +
        "The Frostfangs rise ahead — jagged peaks piercing the grey sky. The White Walkers " +
        "dwell in that cold, born from an age before memory. They do not tire. They do not " +
        "bleed. They do not stop.\n\nBut neither do you.",

        "The White Walker dissolves into frost and shadow, leaving nothing behind but silence " +
        "and the memory of its eyes — cold, ancient, without mercy.\n\n" +
        "The Land of Always Winter lies ahead. The Night King is there. Waiting. He has waited " +
        "eight thousand years.\n\n" +
        "Before you face him, a forgotten tavern carved into the mountainside offers shelter. " +
        "A fire burns low. A moment of humanity before the end."
    };

    // [level index][house index: 0=Targaryen, 1=Lannister, 2=Stark]
    private static final String[][] HOUSE_LINES = {
        {
            "\"Fire cleanses,\" you whisper, and it has never felt more true.",
            "A Lannister pays every debt. You have paid the North one today.",
            "The forest knows you. These are your lands. This is your purpose."
        },
        {
            "You feel Drogon's fire in your veins. Even at the edge of the world, you do not fear.",
            "Gold cannot buy victory in a place like this. Only steel and will.",
            "Your ancestors fought here. You can almost hear them urging you forward."
        },
        {
            "Even dragons need rest before the final flight.",
            "Even the boldest gold needs tempering before the final strike.",
            "Winter came. You walked through it. Now you face what made winter."
        }
    };

    private static final String[] ACCENTS       = {"#3A7A2E", "#8B6914", "#4A90D9"};
    private static final String[] CONTINUE_LABELS = {"MARCH ON  ›", "PRESS DEEPER  ›", "ENTER THE TAVERN  ›"};

    public static void show(Stage stage, House house, int level, int maxHouseHp, int maxArmor) {
        AudioManager.play(AudioManager.Track.MENU);

        int i        = level - 1;
        int houseIdx = switch (house.getName()) {
            case "Targaryen" -> 0;
            case "Lannister" -> 1;
            default          -> 2;
        };
        String accent = ACCENTS[i];

        Image bg = new Image(
            VictoryNarrativeScreen.class.getResourceAsStream("/main/assets/img/bg/startmenubg.png"));
        ImageView bgView = new ImageView(bg);
        bgView.setPreserveRatio(false);

        Rectangle overlay = new Rectangle();
        overlay.setFill(Color.color(0.02, 0.02, 0.02, 0.83));

        Text title = new Text(TITLES[i]);
        title.setFont(Fonts.got(42));
        title.setFill(Color.WHITE);
        title.setEffect(new DropShadow(22, Color.web(accent)));

        Region divider = new Region();
        divider.setPrefSize(440, 1);
        divider.setMaxSize(440, 1);
        divider.setStyle("-fx-background-color: " + accent + ";");
        divider.setEffect(new DropShadow(5, Color.web(accent)));

        Text body = new Text(BODIES[i]);
        body.setFont(Font.font("Georgia", 16));
        body.setFill(Color.color(0.80, 0.80, 0.80, 0.95));
        body.setTextAlignment(TextAlignment.CENTER);
        body.setLineSpacing(7);
        body.setWrappingWidth(640);

        Text houseLine = new Text("— " + HOUSE_LINES[i][houseIdx]);
        houseLine.setFont(Font.font("Georgia", FontPosture.ITALIC, 15));
        houseLine.setFill(Color.web(accent));
        houseLine.setTextAlignment(TextAlignment.CENTER);
        houseLine.setWrappingWidth(560);

        Button continueBtn = new Button(CONTINUE_LABELS[i]);
        continueBtn.setFont(Font.font("Georgia", FontWeight.BOLD, 17));
        continueBtn.setPrefSize(270, 52);
        String base  = btnCss(accent, false);
        String hover = btnCss(accent, true);
        continueBtn.setStyle(base);
        continueBtn.setOnMouseEntered(e -> continueBtn.setStyle(hover));
        continueBtn.setOnMouseExited(e  -> continueBtn.setStyle(base));

        if (level == 3) {
            continueBtn.setOnAction(e -> TavernScreen.show(stage, house, maxHouseHp, maxArmor));
        } else {
            continueBtn.setOnAction(e -> LocationScreen.show(stage, house, level + 1, maxHouseHp, maxArmor));
        }

        VBox content = new VBox(16,
            title, divider, spacer(8),
            body, spacer(10), houseLine, spacer(26), continueBtn);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(60, 40, 60, 40));
        content.setOpacity(0);

        StackPane root = new StackPane(bgView, overlay, content);
        Scene scene = new Scene(root);
        bgView.fitWidthProperty().bind(scene.widthProperty());
        bgView.fitHeightProperty().bind(scene.heightProperty());
        overlay.widthProperty().bind(scene.widthProperty());
        overlay.heightProperty().bind(scene.heightProperty());

        stage.setScene(scene);

        FadeTransition ft = new FadeTransition(Duration.millis(1200), content);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private static Region spacer(double h) {
        Region r = new Region();
        r.setPrefHeight(h);
        return r;
    }

    private static String btnCss(String accent, boolean glow) {
        return "-fx-background-color: transparent;" +
               "-fx-text-fill: " + accent + ";" +
               "-fx-border-color: " + accent + ";" +
               "-fx-border-width: 1;-fx-border-radius: 2;-fx-background-radius: 2;" +
               "-fx-cursor: hand;" +
               (glow ? "-fx-effect: dropshadow(gaussian, " + accent + ", 18, 0.25, 0, 0);" : "");
    }
}
