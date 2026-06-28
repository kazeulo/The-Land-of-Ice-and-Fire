package main.gui;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
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
import main.charactermanager.HouseFactory;

public class HouseSelectionScreen {

    private static final HouseFactory houseFactory = new HouseFactory();

    // house index: 1=Targaryen, 2=Lannister, 3=Stark
    private static final String[] NAMES    = {"TARGARYEN", "LANNISTER", "STARK"};
    private static final String[] MOTTOS   = {"Fire and Blood", "Hear Me Roar!", "Winter is Coming"};
    private static final String[] SPRITES  = {
        "/main/assets/img/houses/targaryen.png",
        "/main/assets/img/houses/lannister.png",
        "/main/assets/img/houses/snow.png"
    };
    private static final int[]    HP       = {100, 100, 125};
    private static final int[]    ARMOR    = {13, 18, 13};
    private static final String[] ATK_RANGE = {"18 – 25", "15 – 21", "16 – 22"};
    private static final String[] ACCENT   = {"#C0392B", "#D4AC0D", "#5D8AA8"};
    private static final String[] CARD_BG  = {"#5A0000CC", "#5A4800CC", "#1A2A3ACC"};

    public static void show(Stage stage) {
        Image bg = new Image(
            HouseSelectionScreen.class.getResourceAsStream("/main/assets/img/bg/startmenubg.png"));
        ImageView bgView = new ImageView(bg);
        bgView.setPreserveRatio(false);

        Rectangle overlay = new Rectangle();
        overlay.setFill(Color.color(0, 0, 0, 0.60));

        // Header ─
        Text title = new Text("CHOOSE YOUR HOUSE");
        title.setFont(Fonts.got(42));
        title.setFill(Color.WHITE);
        title.setEffect(new DropShadow(20, Color.color(0.9, 0.22, 0.0, 0.8)));

        Text subtitle = new Text("Your house determines your starting stats and abilities");
        subtitle.setFont(Font.font("Georgia", FontPosture.ITALIC, 18));
        subtitle.setFill(Color.color(0.75, 0.75, 0.75, 0.85));

        Region divider = new Region();
        divider.setPrefSize(420, 1);
        divider.setMaxSize(420, 1);
        divider.setStyle("-fx-background-color: #C8A84B;");
        divider.setEffect(new DropShadow(6, Color.web("#C8A84B")));

        VBox header = new VBox(10, title, subtitle, divider);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(30, 0, 24, 0));

        // Cards
        HBox cards = new HBox(28);
        cards.setAlignment(Pos.CENTER);

        for (int i = 0; i < 3; i++) {
            cards.getChildren().add(houseCard(stage, i));
        }

        // Layout ─
        VBox content = new VBox(0, header, cards);
        content.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(bgView, overlay, content);

        Scene scene = new Scene(root);
        bgView.fitWidthProperty().bind(scene.widthProperty());
        bgView.fitHeightProperty().bind(scene.heightProperty());
        overlay.widthProperty().bind(scene.widthProperty());
        overlay.heightProperty().bind(scene.heightProperty());

        content.setOpacity(0);
        stage.setScene(scene);
        FadeTransition ft = new FadeTransition(Duration.millis(600), content);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private static VBox houseCard(Stage stage, int idx) {
        // Sprite ─
        ImageView sprite = new ImageView();
        try {
            Image img = new Image(
                HouseSelectionScreen.class.getResourceAsStream(SPRITES[idx]));
            sprite.setImage(img);
            sprite.setFitHeight(190);
            sprite.setPreserveRatio(true);
        } catch (Exception ignored) {}

        StackPane spriteBox = new StackPane(sprite);
        spriteBox.setPrefHeight(200);

        // Text─
        Text nameText = new Text("HOUSE " + NAMES[idx]);
        nameText.setFont(Font.font("Georgia", FontWeight.BOLD, 18));
        nameText.setFill(Color.web(ACCENT[idx]));
        nameText.setEffect(new DropShadow(8, Color.web(ACCENT[idx])));

        Text mottoText = new Text("\"" + MOTTOS[idx] + "\"");
        mottoText.setFont(Font.font("Georgia", FontPosture.ITALIC, 13));
        mottoText.setFill(Color.color(0.80, 0.80, 0.80, 0.9));

        Region sep = new Region();
        sep.setPrefSize(180, 1);
        sep.setMaxHeight(1);
        sep.setStyle("-fx-background-color: " + ACCENT[idx] + "80;");

        // Stat rows
        VBox stats = new VBox(5,
            statRow("HP",     String.valueOf(HP[idx]),     "#2ECC71"),
            statRow("Armor",  String.valueOf(ARMOR[idx]),  "#3498DB"),
            statRow("Attack", ATK_RANGE[idx] + " dmg",    "#E67E22")
        );
        stats.setAlignment(Pos.CENTER_LEFT);

        // SELECT button
        Button selectBtn = new Button("SELECT");
        selectBtn.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        selectBtn.setPrefSize(200, 44);
        String baseStyle = cardBtnStyle(ACCENT[idx], false);
        String hoverStyle = cardBtnStyle(ACCENT[idx], true);
        selectBtn.setStyle(baseStyle);
        selectBtn.setOnMouseEntered(e -> { selectBtn.setStyle(hoverStyle); scaleTo(selectBtn, 1.05); });
        selectBtn.setOnMouseExited(e  -> { selectBtn.setStyle(baseStyle);  scaleTo(selectBtn, 1.00); });

        final int houseIdx = idx + 1;
        final int maxHp    = HP[idx];
        final int maxArmor = ARMOR[idx];

        selectBtn.setOnAction(e -> {
            var house = houseFactory.createHouse(houseIdx);
            LocationScreen.show(stage, house, 1, maxHp, maxArmor);
        });

        // Card container──
        VBox card = new VBox(12, spriteBox, nameText, mottoText, sep, stats, selectBtn);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(20, 20, 22, 20));
        card.setPrefSize(255, 430);
        card.setStyle(
            "-fx-background-color: " + CARD_BG[idx] + ";" +
            "-fx-border-color: " + ACCENT[idx] + "80;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;"
        );
        card.setEffect(new DropShadow(16, Color.web(ACCENT[idx] + "55")));

        // Hover lift effect on card
        card.setOnMouseEntered(e -> {
            card.setTranslateY(-6);
            card.setEffect(new DropShadow(24, Color.web(ACCENT[idx] + "AA")));
        });
        card.setOnMouseExited(e -> {
            card.setTranslateY(0);
            card.setEffect(new DropShadow(16, Color.web(ACCENT[idx] + "55")));
        });

        return card;
    }

    private static HBox statRow(String label, String value, String valueColor) {
        Text lbl = new Text(label + ": ");
        lbl.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        lbl.setFill(Color.color(0.75, 0.75, 0.75, 0.9));

        Text val = new Text(value);
        val.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        val.setFill(Color.web(valueColor));

        HBox row = new HBox(lbl, val);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private static String cardBtnStyle(String accent, boolean glow) {
        return "-fx-background-color: " + accent + (glow ? "" : "99") + ";" +
               "-fx-text-fill: white;" +
               "-fx-border-color: " + accent + ";" +
               "-fx-border-width: 1;" +
               "-fx-border-radius: 3;" +
               "-fx-background-radius: 3;" +
               "-fx-cursor: hand;" +
               (glow ? "-fx-effect: dropshadow(gaussian, " + accent + ", 14, 0.3, 0, 0);" : "");
    }

    private static void scaleTo(Button btn, double s) {
        ScaleTransition st = new ScaleTransition(Duration.millis(100), btn);
        st.setToX(s);
        st.setToY(s);
        st.play();
    }
}
