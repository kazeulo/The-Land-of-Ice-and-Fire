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

public class CharacterIntroScreen {

    private record HouseIntro(String title, String body, String quote, String accent) {}

    private static HouseIntro getData(House house) {
        return switch (house.getName()) {
            case "Targaryen" -> new HouseIntro(
                "THE LAST DRAGON",
                "Fire and blood run in your veins. You are the last of your line — a Targaryen, " +
                "heir to a dynasty that once made kingdoms tremble. Your companion, the dragon " +
                "Drogon, circles overhead as you begin your march north.\n\n" +
                "Your power is fire. Your enemies are ice. The world will learn which is stronger.",
                "\"I am the blood of the dragon. The Night King will learn what that means.\"",
                "#C0392B"
            );
            case "Lannister" -> new HouseIntro(
                "THE GOLDEN SWORD",
                "A Lannister does not retreat. You march north not for glory or duty — but because " +
                "a Lannister always pays their debts, and the Night King owes the realm a reckoning.\n\n" +
                "Your armor is the finest gold can buy. Your blade has never tasted defeat. " +
                "But the dead do not care about gold.",
                "\"The lion does not concern himself with the opinions of the dead.\"",
                "#D4AC0D"
            );
            default -> new HouseIntro(
                "THE WOLF OF THE NORTH",
                "You were born for this. The Starks have stood watch against the darkness since the " +
                "first Long Night. Ghost, your direwolf, runs at your side as you head for the Wall.\n\n" +
                "Winter is not your enemy — it is your birthright. The Night King is the true threat, " +
                "and you are the last Warden of the North.",
                "\"The North remembers. And today, it fights back.\"",
                "#5D8AA8"
            );
        };
    }

    public static void show(Stage stage, House house, int maxHp, int maxArmor) {
        AudioManager.play(AudioManager.Track.MENU);
        HouseIntro d = getData(house);

        Image bg = new Image(
            CharacterIntroScreen.class.getResourceAsStream("/main/assets/img/bg/startmenubg.png"));
        ImageView bgView = new ImageView(bg);
        bgView.setPreserveRatio(false);

        Rectangle overlay = new Rectangle();
        overlay.setFill(Color.color(0, 0, 0, 0.80));

        Text houseSub = new Text("HOUSE  " + house.getName().toUpperCase());
        houseSub.setFont(Font.font("Georgia", FontPosture.ITALIC, 15));
        houseSub.setFill(Color.web(d.accent + "BB"));

        Text title = new Text(d.title);
        title.setFont(Fonts.got(50));
        title.setFill(Color.WHITE);
        title.setEffect(new DropShadow(26, Color.web(d.accent)));

        Region divider = new Region();
        divider.setPrefSize(460, 1);
        divider.setMaxSize(460, 1);
        divider.setStyle("-fx-background-color: " + d.accent + ";");
        divider.setEffect(new DropShadow(6, Color.web(d.accent)));

        Text body = new Text(d.body);
        body.setFont(Font.font("Georgia", 16));
        body.setFill(Color.color(0.82, 0.82, 0.82, 0.95));
        body.setTextAlignment(TextAlignment.CENTER);
        body.setLineSpacing(7);
        body.setWrappingWidth(620);

        Text quote = new Text(d.quote);
        quote.setFont(Font.font("Georgia", FontPosture.ITALIC, 15));
        quote.setFill(Color.web(d.accent));
        quote.setTextAlignment(TextAlignment.CENTER);
        quote.setWrappingWidth(560);

        Button beginBtn = new Button("BEGIN YOUR JOURNEY  ›");
        beginBtn.setFont(Font.font("Georgia", FontWeight.BOLD, 17));
        beginBtn.setPrefSize(290, 52);
        String base  = btnCss(d.accent, false);
        String hover = btnCss(d.accent, true);
        beginBtn.setStyle(base);
        beginBtn.setOnMouseEntered(e -> beginBtn.setStyle(hover));
        beginBtn.setOnMouseExited(e  -> beginBtn.setStyle(base));
        beginBtn.setOnAction(e -> LocationScreen.show(stage, house, 1, maxHp, maxArmor));

        VBox content = new VBox(14,
            houseSub, title, divider, spacer(8),
            body, spacer(6), quote, spacer(24), beginBtn);
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

        FadeTransition ft = new FadeTransition(Duration.millis(1100), content);
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
