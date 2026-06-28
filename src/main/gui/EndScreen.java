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

public class EndScreen {

    public static void show(Stage stage, boolean won, House house) {
        Image bg = new Image(
            EndScreen.class.getResourceAsStream("/main/assets/img/bg/startmenubg.png"));
        ImageView bgView = new ImageView(bg);
        bgView.setPreserveRatio(false);

        // Victory: warm gold tint  |  Defeat: dark red tint
        Rectangle overlay = new Rectangle();
        overlay.setFill(won
            ? Color.color(0.05, 0.03, 0.0, 0.70)
            : Color.color(0.12, 0.0,  0.0, 0.78));

        // Outcome banner─
        Text outcome = new Text(won ? "VICTORY" : "DEFEAT");
        outcome.setFont(Fonts.got(72));
        outcome.setFill(won ? Color.web("#F5D060") : Color.web("#E74C3C"));
        DropShadow glow = new DropShadow(36,
            won ? Color.web("#C8A84B") : Color.web("#C0392B"));
        glow.setSpread(0.25);
        outcome.setEffect(glow);

        // Pulse the outcome text
        ScaleTransition pulse = new ScaleTransition(Duration.millis(1400), outcome);
        pulse.setFromX(0.85); pulse.setFromY(0.85);
        pulse.setToX(1.0);    pulse.setToY(1.0);
        pulse.setCycleCount(1);
        pulse.play();

        // Flavour text
        Text flavour = new Text(won
            ? "You have saved Westeros!"
            : "You have failed to save Westeros.");
        flavour.setFont(Font.font("Georgia", FontPosture.ITALIC, 26));
        flavour.setFill(Color.color(0.88, 0.88, 0.88, 0.92));

        Text sub = new Text(won
            ? "The Night King is defeated. Winter has ended."
            : "The darkness has consumed the realm. All is lost.");
        sub.setFont(Font.font("Georgia", 17));
        sub.setFill(Color.color(0.65, 0.65, 0.65, 0.85));

        Region divider = new Region();
        divider.setPrefSize(360, 1);
        divider.setMaxSize(360, 1);
        divider.setStyle("-fx-background-color: " + (won ? "#C8A84B" : "#C0392B") + ";");
        divider.setEffect(new DropShadow(6, Color.web(won ? "#C8A84B" : "#C0392B")));

        // Final stats
        Text statsLabel = new Text("— Final Stats —");
        statsLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        statsLabel.setFill(Color.color(0.65, 0.65, 0.65, 0.8));

        Text hpStat    = statLine("HP Remaining", house.getHp() + "", won ? "#2ECC71" : "#E74C3C");
        Text armorStat = statLine("Armor",        house.getArmor() + "", "#3498DB");
        Text houseStat = statLine("House",        house.getName(), "#C8A84B");

        VBox stats = new VBox(8, statsLabel, hpStat, armorStat, houseStat);
        stats.setAlignment(Pos.CENTER);
        stats.setPadding(new Insets(14, 28, 14, 28));
        stats.setStyle(
            "-fx-background-color: rgba(0,0,0,0.45);" +
            "-fx-border-color: rgba(200,168,75,0.25);" +
            "-fx-border-width: 1;" +
            "-fx-background-radius: 4;" +
            "-fx-border-radius: 4;"
        );
        stats.setMaxWidth(320);

        // Buttons 
        Button playAgainBtn = endBtn("PLAY AGAIN", "#6E0000", "#A93226");
        Button menuBtn      = endBtn("MAIN MENU",  "#0D1B2A", "#1A5276");

        playAgainBtn.setOnAction(e -> HouseSelectionScreen.show(stage));
        menuBtn.setOnAction(e      -> StartMenu.show(stage));

        HBox buttons = new HBox(24, playAgainBtn, menuBtn);
        buttons.setAlignment(Pos.CENTER);

        // Layout 
        VBox content = new VBox(18, outcome, flavour, sub, divider, stats, buttons);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40, 0, 40, 0));

        StackPane root = new StackPane(bgView, overlay, content);

        Scene scene = new Scene(root);
        bgView.fitWidthProperty().bind(scene.widthProperty());
        bgView.fitHeightProperty().bind(scene.heightProperty());
        overlay.widthProperty().bind(scene.widthProperty());
        overlay.heightProperty().bind(scene.heightProperty());

        content.setOpacity(0);
        stage.setScene(scene);
        stage.setMaximized(true);
        FadeTransition ft = new FadeTransition(Duration.millis(800), content);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private static Text statLine(String label, String value, String valueColor) {
        // Build "Label:  Value" as a single Text with inline colour via TextFlow trick
        // Easiest: just combine into one text, colour set via label
        Text t = new Text(label + ":  " + value);
        t.setFont(Font.font("Georgia", 14));
        t.setFill(Color.web(valueColor));
        return t;
    }

    private static Button endBtn(String label, String base, String hover) {
        Button btn = new Button(label);
        btn.setFont(Font.font("Georgia", FontWeight.BOLD, 18));
        btn.setPrefSize(200, 50);
        String bs = btnCss(base, false), hs = btnCss(hover, true);
        btn.setStyle(bs);
        btn.setOnMouseEntered(e -> { btn.setStyle(hs); scaleTo(btn, 1.05); });
        btn.setOnMouseExited(e  -> { btn.setStyle(bs); scaleTo(btn, 1.00); });
        return btn;
    }

    private static String btnCss(String bg, boolean glow) {
        return "-fx-background-color:" + bg + ";" +
               "-fx-text-fill:#E8DAAF;" +
               "-fx-border-color:rgba(200,168,75,0.5);" +
               "-fx-border-width:1;-fx-border-radius:2;-fx-background-radius:2;" +
               "-fx-cursor:hand;" +
               (glow ? "-fx-effect:dropshadow(gaussian,#C8A84B,16,0.2,0,0);" : "");
    }

    private static void scaleTo(Button btn, double s) {
        ScaleTransition st = new ScaleTransition(Duration.millis(110), btn);
        st.setToX(s); st.setToY(s); st.play();
    }
}
