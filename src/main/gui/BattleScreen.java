package main.gui;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.charactermanager.Enemy;
import main.charactermanager.EnemyFactory;
import main.charactermanager.House;
import main.game.BattleManager;

public class BattleScreen {

    private final Stage stage;
    private final House house;
    private final Enemy enemy;
    private final int level;
    private final int maxHouseHp;
    private final int maxArmor;
    private final BattleManager battleManager = new BattleManager();

    // Live UI refs
    private ProgressBar playerHpBar, playerArmorBar, enemyHpBar;
    private Text playerHpText, playerArmorText, enemyHpText;
    private VBox logBox;
    private ScrollPane logScroll;
    private Button attackBtn, runBtn;
    private ImageView playerSprite, enemySprite;

    private BattleScreen(Stage stage, House house, int level, int maxHouseHp, int maxArmor) {
        this.stage       = stage;
        this.house       = house;
        this.level       = level;
        this.maxHouseHp  = maxHouseHp;
        this.maxArmor    = maxArmor;
        this.enemy       = new EnemyFactory().createEnemy(level);
    }

    public static void show(Stage stage, House house, int level, int maxHouseHp, int maxArmor) {
        new BattleScreen(stage, house, level, maxHouseHp, maxArmor).build();
    }

    private void build() {
        // ── Background ────────────────────────────────────────────────────────
        Image bg = new Image(getClass().getResourceAsStream("/main/assets/img/bg/startmenubg.png"));
        ImageView bgView = new ImageView(bg);
        bgView.setPreserveRatio(false);

        Rectangle overlay = new Rectangle();
        overlay.setFill(Color.color(0, 0, 0, 0.72));

        // ── Top bar ───────────────────────────────────────────────────────────
        Text levelTxt = new Text("LEVEL " + level);
        levelTxt.setFont(Font.font("Georgia", FontWeight.BOLD, 15));
        levelTxt.setFill(Color.web("#C8A84B"));

        Text locationTxt = new Text(enemy.getLocation().toUpperCase());
        locationTxt.setFont(Font.font("Georgia", FontPosture.ITALIC, 14));
        locationTxt.setFill(Color.color(0.75, 0.75, 0.75, 0.9));

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        HBox topBar = new HBox(14, levelTxt, new Text("·"), locationTxt, topSpacer);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: rgba(0,0,0,0.45);");
        topBar.setPadding(new Insets(8, 20, 8, 20));
        ((Text) topBar.getChildren().get(1)).setFill(Color.web("#C8A84B"));
        ((Text) topBar.getChildren().get(1)).setFont(Font.font("Georgia", 14));

        // ── Sprites ───────────────────────────────────────────────────────────
        playerSprite = loadSprite(housePath(), 230);
        enemySprite  = loadSprite(enemyPath(),  230);

        // ── Player stat panel ─────────────────────────────────────────────────
        Text playerName = styledLabel("HOUSE " + house.getName().toUpperCase(), "#5D8AA8", 15);

        playerHpBar = bar(house.getHp() / (double) maxHouseHp, "#2ECC71");
        playerHpText = statText(house.getHp() + " / " + maxHouseHp + " HP");

        playerArmorBar = bar(house.getArmor() / (double) maxArmor, "#3498DB");
        playerArmorText = statText(house.getArmor() + " / " + maxArmor + " Armor");

        VBox playerStats = statPanel(
            playerName,
            label("HP",    "#2ECC71"), playerHpBar,    playerHpText,
            label("Armor", "#3498DB"), playerArmorBar, playerArmorText
        );

        VBox playerCol = new VBox(14, playerSprite, playerStats);
        playerCol.setAlignment(Pos.CENTER);
        playerCol.setPadding(new Insets(10, 10, 0, 20));
        HBox.setHgrow(playerCol, Priority.ALWAYS);

        // ── Enemy stat panel ──────────────────────────────────────────────────
        Text enemyName = styledLabel(enemy.getName().toUpperCase(), "#E74C3C", 15);

        enemyHpBar  = bar(1.0, "#E74C3C");
        enemyHpText = statText("100 / 100 HP");

        VBox enemyStats = enemyPanel(enemyName, enemyHpBar, enemyHpText);

        VBox enemyCol = new VBox(14, enemySprite, enemyStats);
        enemyCol.setAlignment(Pos.CENTER);
        enemyCol.setPadding(new Insets(10, 20, 0, 10));
        HBox.setHgrow(enemyCol, Priority.ALWAYS);

        // ── Battle field ──────────────────────────────────────────────────────
        HBox battlefield = new HBox(0, playerCol, enemyCol);
        battlefield.setAlignment(Pos.CENTER);
        VBox.setVgrow(battlefield, Priority.ALWAYS);

        // ── Combat log ────────────────────────────────────────────────────────
        logBox = new VBox(3);
        logBox.setPadding(new Insets(6, 12, 6, 12));

        logScroll = new ScrollPane(logBox);
        logScroll.setFitToWidth(true);
        logScroll.setPrefHeight(120);
        logScroll.setMaxHeight(120);
        logScroll.setStyle(
            "-fx-background-color: rgba(0,0,0,0.0);" +
            "-fx-border-color: #C8A84B40;" +
            "-fx-border-width: 1 0 0 0;"
        );
        logBox.setStyle("-fx-background-color: rgba(0,0,0,0.55);");

        // ── Action buttons ────────────────────────────────────────────────────
        attackBtn = actionBtn("ATTACK", "#6E0000", "#A93226");
        runBtn    = actionBtn("RUN",    "#1A1A1A", "#3D3D3D");

        attackBtn.setOnAction(e -> doAttack());
        runBtn.setOnAction(e    -> doRun());

        HBox actions = new HBox(28, attackBtn, runBtn);
        actions.setAlignment(Pos.CENTER);
        actions.setPadding(new Insets(10, 0, 14, 0));
        actions.setStyle("-fx-background-color: rgba(0,0,0,0.45);");

        // ── Root ──────────────────────────────────────────────────────────────
        VBox main = new VBox(topBar, battlefield, logScroll, actions);
        VBox.setVgrow(battlefield, Priority.ALWAYS);
        main.setFillWidth(true);

        StackPane root = new StackPane(bgView, overlay, main);

        Scene scene = new Scene(root, 1280, 720);
        bgView.fitWidthProperty().bind(scene.widthProperty());
        bgView.fitHeightProperty().bind(scene.heightProperty());
        overlay.widthProperty().bind(scene.widthProperty());
        overlay.heightProperty().bind(scene.heightProperty());

        // Bind log text wrapping
        logBox.widthProperty().addListener((obs, o, w) ->
            logBox.getChildren().stream()
                .filter(n -> n instanceof Text)
                .forEach(n -> ((Text) n).setWrappingWidth(w.doubleValue() - 28))
        );

        main.setOpacity(0);
        stage.setScene(scene);
        FadeTransition ft = new FadeTransition(Duration.millis(500), main);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setOnFinished(e -> addLog("You face the " + enemy.getName() + " in " + enemy.getLocation() + "!"));
        ft.play();
    }

    // ── Combat logic ─────────────────────────────────────────────────────────

    private void doAttack() {
        setBtnsEnabled(false);
        addLog("You charge at the " + enemy.getName() + "!");

        pause(650, e1 -> {
            if (battleManager.coinToss()) {
                addLog("Your attack missed!");
            } else {
                int atk  = house.attack();
                String move = house.getLastMoveName();
                int dmg  = enemy.takenDamage(atk);
                shake(enemySprite);
                flash(enemySprite, Color.web("#E74C3C"));
                addLog("You used " + move + "!  Dealt " + dmg + " damage to " + enemy.getName() + ".");
                animateBar(enemyHpBar, Math.max(0, enemy.getHp()) / 100.0);
                enemyHpText.setText(Math.max(0, enemy.getHp()) + " / 100 HP");
                colorBar(enemyHpBar, enemy.getHp(), 100, "#E74C3C");
            }

            if (!enemy.isAlive()) {
                addLog(enemy.getName() + " has been slain!");
                pause(1100, e2 -> handleVictory());
                return;
            }

            // Enemy counter-attack
            pause(750, e2 -> {
                addLog(enemy.getName() + " retaliates!");
                pause(550, e3 -> {
                    if (battleManager.coinToss()) {
                        addLog("You dodged the attack!");
                    } else {
                        int armorBefore = house.getArmor();
                        int dmg = house.takenDamage(enemy.attack());
                        int newArmor = house.reduceArmor(house.getHp());
                        shake(playerSprite);
                        flash(playerSprite, Color.web("#C0392B"));
                        addLog(enemy.getName() + " dealt " + dmg + " damage to you.");
                        if (newArmor <= 0 && armorBefore > 0) addLog("Your armor is broken!");
                        refreshPlayerStats();
                    }

                    if (!house.isAlive()) {
                        addLog("You have fallen in battle...");
                        pause(1200, e4 -> EndScreen.show(stage, false, house));
                    } else {
                        setBtnsEnabled(true);
                    }
                });
            });
        });
    }

    private void doRun() {
        setBtnsEnabled(false);
        addLog("You chose to flee... the realm is lost.");
        pause(1200, e -> EndScreen.show(stage, false, house));
    }

    private void handleVictory() {
        if (level == 3) {
            addLog("You prepare for the final battle...");
            pause(1000, e -> TavernScreen.show(stage, house, maxHouseHp, maxArmor));
        } else if (level == 4) {
            EndScreen.show(stage, true, house);
        } else {
            addLog("You advance to Level " + (level + 1) + "!");
            pause(900, e -> BattleScreen.show(stage, house, level + 1, maxHouseHp, maxArmor));
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void refreshPlayerStats() {
        int hp    = house.getHp();
        int armor = Math.max(0, house.getArmor());
        animateBar(playerHpBar,    Math.max(0, hp    / (double) maxHouseHp));
        animateBar(playerArmorBar, Math.max(0, armor / (double) maxArmor));
        playerHpText.setText(hp    + " / " + maxHouseHp + " HP");
        playerArmorText.setText(armor + " / " + maxArmor + " Armor");
        colorBar(playerHpBar, hp, maxHouseHp, "#2ECC71");
    }

    private void addLog(String msg) {
        Text t = new Text("> " + msg);
        t.setFont(Font.font("Georgia", 13));
        t.setFill(Color.web("#E8DAAF"));
        if (logBox.getWidth() > 0) t.setWrappingWidth(logBox.getWidth() - 28);
        logBox.getChildren().add(t);
        Platform.runLater(() -> logScroll.setVvalue(1.0));
    }

    private void shake(Node n) {
        TranslateTransition t = new TranslateTransition(Duration.millis(48), n);
        t.setByX(9); t.setCycleCount(6); t.setAutoReverse(true);
        t.play();
    }

    private void flash(Node n, Color c) {
        DropShadow glow = new DropShadow(30, c);
        n.setEffect(glow);
        PauseTransition p = new PauseTransition(Duration.millis(400));
        p.setOnFinished(e -> n.setEffect(null));
        p.play();
    }

    private void animateBar(ProgressBar pb, double target) {
        new Timeline(new KeyFrame(Duration.millis(400),
            new KeyValue(pb.progressProperty(), target))).play();
    }

    private void colorBar(ProgressBar pb, int hp, int max, String defaultColor) {
        double pct = hp / (double) max;
        if (pct < 0.25)      pb.setStyle("-fx-accent: #E74C3C;");
        else if (pct < 0.50) pb.setStyle("-fx-accent: #F39C12;");
        else                 pb.setStyle("-fx-accent: " + defaultColor + ";");
    }

    private void setBtnsEnabled(boolean on) {
        attackBtn.setDisable(!on);
        runBtn.setDisable(!on);
    }

    private void pause(double ms, javafx.event.EventHandler<ActionEvent> then) {
        PauseTransition p = new PauseTransition(Duration.millis(ms));
        p.setOnFinished(then);
        p.play();
    }

    // ── Builders ──────────────────────────────────────────────────────────────

    private ImageView loadSprite(String path, double height) {
        try {
            ImageView iv = new ImageView(new Image(getClass().getResourceAsStream(path)));
            iv.setFitHeight(height);
            iv.setPreserveRatio(true);
            return iv;
        } catch (Exception e) { return new ImageView(); }
    }

    private ProgressBar bar(double progress, String color) {
        ProgressBar pb = new ProgressBar(progress);
        pb.setPrefSize(240, 14);
        pb.setStyle("-fx-accent: " + color + ";");
        return pb;
    }

    private Text statText(String s) {
        Text t = new Text(s);
        t.setFont(Font.font("Georgia", 12));
        t.setFill(Color.color(0.78, 0.78, 0.78, 1));
        return t;
    }

    private Text styledLabel(String s, String color, double size) {
        Text t = new Text(s);
        t.setFont(Font.font("Georgia", FontWeight.BOLD, size));
        t.setFill(Color.web(color));
        return t;
    }

    private Text label(String s, String color) {
        Text t = new Text(s);
        t.setFont(Font.font("Georgia", FontWeight.BOLD, 11));
        t.setFill(Color.web(color));
        return t;
    }

    private VBox statPanel(Text name, Text hpLbl, ProgressBar hpBar, Text hpVal,
                           Text armLbl, ProgressBar armBar, Text armVal) {
        VBox v = new VBox(5, name,
            hpLbl, hpBar, hpVal,
            armLbl, armBar, armVal);
        v.setAlignment(Pos.CENTER_LEFT);
        v.setPadding(new Insets(12, 14, 12, 14));
        v.setMaxWidth(270);
        v.setStyle(
            "-fx-background-color: rgba(0,0,0,0.55);" +
            "-fx-border-color: #5D8AA855;" +
            "-fx-border-width: 1;" +
            "-fx-background-radius: 4;" +
            "-fx-border-radius: 4;"
        );
        return v;
    }

    private VBox enemyPanel(Text name, ProgressBar hpBar, Text hpVal) {
        VBox v = new VBox(5, name,
            label("HP", "#E74C3C"), hpBar, hpVal);
        v.setAlignment(Pos.CENTER_LEFT);
        v.setPadding(new Insets(12, 14, 12, 14));
        v.setMaxWidth(270);
        v.setStyle(
            "-fx-background-color: rgba(0,0,0,0.55);" +
            "-fx-border-color: #E74C3C55;" +
            "-fx-border-width: 1;" +
            "-fx-background-radius: 4;" +
            "-fx-border-radius: 4;"
        );
        return v;
    }

    private Button actionBtn(String label, String base, String hover) {
        Button btn = new Button(label);
        btn.setFont(Font.font("Georgia", FontWeight.BOLD, 18));
        btn.setPrefSize(200, 48);
        String bs = btnCss(base, false), hs = btnCss(hover, true);
        btn.setStyle(bs);
        btn.setOnMouseEntered(e -> { if (!btn.isDisabled()) btn.setStyle(hs); });
        btn.setOnMouseExited(e  -> { if (!btn.isDisabled()) btn.setStyle(bs); });
        return btn;
    }

    private String btnCss(String bg, boolean glow) {
        return "-fx-background-color:" + bg + ";" +
               "-fx-text-fill:#E8DAAF;" +
               "-fx-border-color:rgba(200,168,75,0.5);" +
               "-fx-border-width:1;" +
               "-fx-border-radius:2;" +
               "-fx-background-radius:2;" +
               "-fx-cursor:hand;" +
               (glow ? "-fx-effect:dropshadow(gaussian,#C8A84B,16,0.2,0,0);" : "");
    }

    private String housePath() {
        return switch (house.getName()) {
            case "Targaryen" -> "/main/assets/img/houses/targaryen.png";
            case "Lannister" -> "/main/assets/img/houses/lannister.png";
            default          -> "/main/assets/img/houses/snow.png";
        };
    }

    private String enemyPath() {
        return switch (enemy.getName()) {
            case "Giant"        -> "/main/assets/img/enemies/giant.png";
            case "White Walker" -> "/main/assets/img/enemies/whitewalker.png";
            case "Night King"   -> "/main/assets/img/enemies/nightking.png";
            default             -> "/main/assets/img/enemies/widling.png";
        };
    }
}
