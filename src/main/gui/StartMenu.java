package main.gui;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StartMenu extends Application {

    @Override
    public void start(Stage stage) {
        stage.setMaximized(true);
        show(stage);
    }

    public static void show(Stage stage) {
        Image bg = new Image(
                StartMenu.class.getResourceAsStream("/main/assets/img/bg/startmenubg.png"));
        ImageView bgView = new ImageView(bg);
        bgView.setPreserveRatio(false);

        Rectangle overlay = new Rectangle();
        overlay.setFill(Color.color(0, 0, 0, 0.50));

        Text title = new Text("THE LAND OF ICE AND FIRE");
        title.setFont(Fonts.got(58));
        title.setFill(Color.WHITE);
        title.setEffect(new DropShadow(28, Color.color(0.9, 0.22, 0.0, 0.9)));

        Text subtitle = new Text("«  Winter is Coming  »");
        subtitle.setFont(Font.font("Georgia", FontPosture.ITALIC, 24));
        subtitle.setFill(Color.color(0.72, 0.86, 1.0, 0.88));
        subtitle.setEffect(new DropShadow(12, Color.color(0.2, 0.45, 0.95, 0.65)));

        Region divider = new Region();
        divider.setPrefSize(360, 1);
        divider.setMaxSize(360, 1);
        divider.setMinSize(360, 1);
        divider.setStyle("-fx-background-color: #C8A84B;");
        divider.setEffect(new DropShadow(8, Color.web("#C8A84B")));

        Button playBtn = menuButton("PLAY",      "#6E0000", "#A93226");
        Button infoBtn = menuButton("GAME INFO", "#0D1B2A", "#1A5276");
        Button exitBtn = menuButton("EXIT",      "#1C1C1C", "#3D3D3D");

        playBtn.setOnAction(e -> new HouseSelection(stage));
        infoBtn.setOnAction(e -> GameInfoScreen.show(stage));
        exitBtn.setOnAction(e -> {
            javafx.application.Platform.exit();
            System.exit(0);
        });

        VBox titleArea = new VBox(10, title, subtitle);
        titleArea.setAlignment(Pos.CENTER);

        VBox buttonArea = new VBox(14, playBtn, infoBtn, exitBtn);
        buttonArea.setAlignment(Pos.CENTER);

        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(titleArea, spacer(24), divider, spacer(38), buttonArea);

        StackPane root = new StackPane(bgView, overlay, content);

        Scene scene = new Scene(root);
        bgView.fitWidthProperty().bind(scene.widthProperty());
        bgView.fitHeightProperty().bind(scene.heightProperty());
        overlay.widthProperty().bind(scene.widthProperty());
        overlay.heightProperty().bind(scene.heightProperty());

        content.setOpacity(0);
        stage.setScene(scene);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1400), content);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        if (!stage.isShowing()) stage.show();
    }

    private static Button menuButton(String label, String baseColor, String hoverColor) {
        Button btn = new Button(label);
        btn.setFont(Font.font("Georgia", FontWeight.BOLD, 21));
        btn.setPrefSize(300, 54);
        String base  = btnStyle(baseColor,  false);
        String hover = btnStyle(hoverColor, true);
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> { btn.setStyle(hover); scaleTo(btn, 1.06); });
        btn.setOnMouseExited(e  -> { btn.setStyle(base);  scaleTo(btn, 1.00); });
        btn.setOnMousePressed(e -> scaleTo(btn, 0.97));
        btn.setOnMouseReleased(e -> scaleTo(btn, 1.06));
        return btn;
    }

    private static String btnStyle(String bg, boolean glow) {
        return "-fx-background-color: " + bg + ";" +
               "-fx-text-fill: #E8DAAF;" +
               "-fx-border-color: rgba(200,168,75,0.5);" +
               "-fx-border-width: 1;" +
               "-fx-border-radius: 2;" +
               "-fx-background-radius: 2;" +
               "-fx-cursor: hand;" +
               (glow ? "-fx-effect: dropshadow(gaussian, #C8A84B, 18, 0.2, 0, 0);" : "");
    }

    private static void scaleTo(Button btn, double s) {
        ScaleTransition st = new ScaleTransition(Duration.millis(110), btn);
        st.setToX(s);
        st.setToY(s);
        st.play();
    }

    private static Region spacer(double height) {
        Region r = new Region();
        r.setPrefHeight(height);
        r.setMinHeight(height);
        return r;
    }
}
