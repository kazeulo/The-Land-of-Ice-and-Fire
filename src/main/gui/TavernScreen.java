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
import main.charactermanager.House;

public class TavernScreen {

    public static void show(Stage stage, House house, int maxHouseHp, int maxArmor) {
        Image bg = new Image(
            TavernScreen.class.getResourceAsStream("/main/assets/img/bg/startmenubg.png"));
        ImageView bgView = new ImageView(bg);
        bgView.setPreserveRatio(false);

        // Warm amber overlay — tavern feel
        Rectangle overlay = new Rectangle();
        overlay.setFill(Color.color(0.08, 0.04, 0.0, 0.74));

        // Header ─
        Text title = new Text("THE TAVERN");
        title.setFont(Fonts.got(46));
        title.setFill(Color.web("#C8A84B"));
        title.setEffect(new DropShadow(22, Color.color(0.8, 0.5, 0.0, 0.8)));

        Text subtitle = new Text("Prepare yourself before facing the Night King");
        subtitle.setFont(Font.font("Georgia", FontPosture.ITALIC, 18));
        subtitle.setFill(Color.color(0.80, 0.75, 0.65, 0.88));

        Region divider = new Region();
        divider.setPrefSize(400, 1);
        divider.setMaxSize(400, 1);
        divider.setStyle("-fx-background-color: #C8A84B;");
        divider.setEffect(new DropShadow(6, Color.web("#C8A84B")));

        // Current stats───
        Text statsTitle = new Text("Current Status  —  House " + house.getName());
        statsTitle.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        statsTitle.setFill(Color.color(0.75, 0.75, 0.75, 0.9));

        Text hpStat    = new Text("HP: " + house.getHp() + " / " + maxHouseHp);
        Text armorStat = new Text("Armor: " + house.getArmor() + " / " + maxArmor);
        for (Text t : new Text[]{hpStat, armorStat}) {
            t.setFont(Font.font("Georgia", 13));
            t.setFill(Color.color(0.72, 0.72, 0.72, 0.85));
        }

        HBox statsRow = new HBox(28, hpStat, armorStat);
        statsRow.setAlignment(Pos.CENTER);

        VBox header = new VBox(10, title, subtitle, divider, statsTitle, statsRow);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(28, 0, 20, 0));

        // Option cards
        VBox restCard    = optionCard("REST",
            "Recover 40 HP",
            "You settle by the fire and let your wounds close.",
            "#1A3A1A", "#27AE60");

        VBox repairCard  = optionCard("REPAIR ARMOR",
            "Restore 7 Armor",
            "A blacksmith hammers your dented plates back into shape.",
            "#1A2A3A", "#2980B9");

        VBox potionCard  = optionCard("STRENGTH POTION",
            "Gain +5 to +10 Attack",
            "A bubbling vial sends fire through your veins.",
            "#3A1A00", "#E67E22");

        // Result label (hidden until choice made)
        Text result = new Text();
        result.setFont(Font.font("Georgia", FontPosture.ITALIC, 16));
        result.setFill(Color.web("#C8A84B"));
        result.setVisible(false);

        Button continueBtn = continueButton();
        continueBtn.setVisible(false);
        continueBtn.setOnAction(e -> BattleScreen.show(stage, house, 4, maxHouseHp, maxArmor));

        // Wire up choices
        getChooseBtn(restCard).setOnAction(e -> {
            int newHp = house.rest();
            result.setText("You rested. HP is now " + newHp + ".");
            result.setVisible(true);
            continueBtn.setVisible(true);
            disableAll(restCard, repairCard, potionCard);
            highlightChosen(restCard, "#27AE60");
        });

        getChooseBtn(repairCard).setOnAction(e -> {
            int newArmor = house.repairArmor();
            result.setText("Armor repaired. Armor is now " + newArmor + ".");
            result.setVisible(true);
            continueBtn.setVisible(true);
            disableAll(restCard, repairCard, potionCard);
            highlightChosen(repairCard, "#2980B9");
        });

        getChooseBtn(potionCard).setOnAction(e -> {
            int bonus = house.drinkStrengthPotion();
            result.setText("You feel stronger! +" + bonus + " attack damage permanently.");
            result.setVisible(true);
            continueBtn.setVisible(true);
            disableAll(restCard, repairCard, potionCard);
            highlightChosen(potionCard, "#E67E22");
        });

        HBox cards = new HBox(24, restCard, repairCard, potionCard);
        cards.setAlignment(Pos.CENTER);

        VBox bottom = new VBox(14, result, continueBtn);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(16, 0, 0, 0));

        VBox content = new VBox(0, header, cards, bottom);
        content.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(bgView, overlay, content);

        Scene scene = new Scene(root);
        bgView.fitWidthProperty().bind(scene.widthProperty());
        bgView.fitHeightProperty().bind(scene.heightProperty());
        overlay.widthProperty().bind(scene.widthProperty());
        overlay.heightProperty().bind(scene.heightProperty());

        content.setOpacity(0);
        stage.setScene(scene);
        stage.setMaximized(true);
        FadeTransition ft = new FadeTransition(Duration.millis(600), content);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    // Helpers─

    private static VBox optionCard(String heading, String effect, String desc,
                                    String bgColor, String accent) {
        Text headTxt = new Text(heading);
        headTxt.setFont(Font.font("Georgia", FontWeight.BOLD, 17));
        headTxt.setFill(Color.web(accent));
        headTxt.setEffect(new DropShadow(8, Color.web(accent)));

        Text effectTxt = new Text(effect);
        effectTxt.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        effectTxt.setFill(Color.web("#C8A84B"));

        Region sep = new Region();
        sep.setPrefSize(170, 1);
        sep.setMaxHeight(1);
        sep.setStyle("-fx-background-color: " + accent + "60;");

        Text descTxt = new Text(desc);
        descTxt.setFont(Font.font("Georgia", FontPosture.ITALIC, 13));
        descTxt.setFill(Color.color(0.76, 0.76, 0.76, 0.9));
        descTxt.setWrappingWidth(190);

        Button choose = new Button("CHOOSE");
        choose.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        choose.setPrefSize(160, 40);
        String bs = choiceBtnStyle(accent, false), hs = choiceBtnStyle(accent, true);
        choose.setStyle(bs);
        choose.setOnMouseEntered(e -> { if (!choose.isDisabled()) choose.setStyle(hs); });
        choose.setOnMouseExited(e  -> { if (!choose.isDisabled()) choose.setStyle(bs); });
        choose.setOnMousePressed(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(80), choose);
            st.setToX(0.95); st.setToY(0.95); st.play();
        });
        choose.setOnMouseReleased(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(80), choose);
            st.setToX(1.0); st.setToY(1.0); st.play();
        });

        VBox card = new VBox(10, headTxt, effectTxt, sep, descTxt, choose);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(20, 18, 20, 18));
        card.setPrefSize(230, 230);
        card.setStyle(
            "-fx-background-color: " + bgColor + "CC;" +
            "-fx-border-color: " + accent + "70;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;"
        );
        card.setEffect(new DropShadow(10, Color.web(accent + "44")));
        return card;
    }

    private static Button getChooseBtn(VBox card) {
        return card.getChildren().stream()
            .filter(n -> n instanceof Button)
            .map(n -> (Button) n)
            .findFirst().orElseThrow();
    }

    private static void disableAll(VBox... cards) {
        for (VBox c : cards) getChooseBtn(c).setDisable(true);
    }

    private static void highlightChosen(VBox card, String accent) {
        card.setStyle(
            card.getStyle() +
            "-fx-border-width: 2;" +
            "-fx-border-color: " + accent + ";"
        );
        card.setEffect(new DropShadow(20, Color.web(accent)));
    }

    private static Button continueButton() {
        Button btn = new Button("FACE THE NIGHT KING  >");
        btn.setFont(Font.font("Georgia", FontWeight.BOLD, 17));
        btn.setPrefSize(280, 50);
        String bs = "-fx-background-color:#6E0000;-fx-text-fill:#E8DAAF;" +
                    "-fx-border-color:rgba(200,168,75,0.6);-fx-border-width:1;" +
                    "-fx-border-radius:2;-fx-background-radius:2;-fx-cursor:hand;";
        String hs = "-fx-background-color:#A93226;-fx-text-fill:#E8DAAF;" +
                    "-fx-border-color:#C8A84B;-fx-border-width:1;" +
                    "-fx-border-radius:2;-fx-background-radius:2;-fx-cursor:hand;" +
                    "-fx-effect:dropshadow(gaussian,#C8A84B,14,0.2,0,0);";
        btn.setStyle(bs);
        btn.setOnMouseEntered(e -> btn.setStyle(hs));
        btn.setOnMouseExited(e  -> btn.setStyle(bs));
        return btn;
    }

    private static String choiceBtnStyle(String accent, boolean glow) {
        return "-fx-background-color:" + accent + (glow ? "" : "99") + ";" +
               "-fx-text-fill:white;" +
               "-fx-border-color:" + accent + ";" +
               "-fx-border-width:1;-fx-border-radius:3;-fx-background-radius:3;" +
               "-fx-cursor:hand;" +
               (glow ? "-fx-effect:dropshadow(gaussian," + accent + ",12,0.3,0,0);" : "");
    }
}
