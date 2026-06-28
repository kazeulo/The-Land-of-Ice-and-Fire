package main.gui;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class GameInfoScreen {

    public static void show(Stage stage) {
        AudioManager.play(AudioManager.Track.MENU);
        Image bg = new Image(
            GameInfoScreen.class.getResourceAsStream("/main/assets/img/bg/startmenubg.png"));
        ImageView bgView = new ImageView(bg);
        bgView.setPreserveRatio(false);

        Rectangle overlay = new Rectangle();
        overlay.setFill(Color.color(0, 0, 0, 0.65));

        // Header
        Button backBtn = backButton("< BACK");
        backBtn.setOnAction(e -> StartMenu.show(stage));

        Text headerTitle = new Text("GAME INFORMATION");
        headerTitle.setFont(Fonts.got(34));
        headerTitle.setFill(Color.WHITE);
        headerTitle.setEffect(new DropShadow(16, Color.color(0.9, 0.22, 0.0, 0.75)));

        Region leftPad  = new Region();
        Region rightPad = new Region();
        HBox.setHgrow(leftPad,  Priority.ALWAYS);
        HBox.setHgrow(rightPad, Priority.ALWAYS);

        HBox header = new HBox(backBtn, leftPad, headerTitle, rightPad);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(22, 40, 16, 40));

        Region divider = new Region();
        divider.setPrefHeight(1);
        divider.setStyle("-fx-background-color: #C8A84B;");
        divider.setEffect(new DropShadow(6, Color.web("#C8A84B")));

        // Four content panels
        Node housesPanel  = buildHousesPanel();
        Node enemiesPanel = buildEnemiesPanel();
        Node combatPanel  = buildCombatPanel();
        Node lorePanel    = buildLorePanel();
        enemiesPanel.setVisible(false);
        combatPanel.setVisible(false);
        lorePanel.setVisible(false);

        StackPane contentArea = new StackPane(housesPanel, enemiesPanel, combatPanel, lorePanel);
        contentArea.setAlignment(Pos.TOP_LEFT);
        VBox.setVgrow(contentArea, Priority.ALWAYS);
        HBox.setHgrow(contentArea, Priority.ALWAYS);

        // Sidebar — four nav buttons
        Button housesBtn  = sideBtn("NOBLE HOUSES");
        Button enemiesBtn = sideBtn("ENEMIES");
        Button combatBtn  = sideBtn("HOW TO PLAY");
        Button loreBtn    = sideBtn("THE LORE");
        Button[] navBtns = {housesBtn, enemiesBtn, combatBtn, loreBtn};
        Node[]   panels  = {housesPanel, enemiesPanel, combatPanel, lorePanel};

        activateNav(navBtns, panels, 0, contentArea);
        housesBtn.setOnAction(e  -> activateNav(navBtns, panels, 0, contentArea));
        enemiesBtn.setOnAction(e -> activateNav(navBtns, panels, 1, contentArea));
        combatBtn.setOnAction(e  -> activateNav(navBtns, panels, 2, contentArea));
        loreBtn.setOnAction(e    -> activateNav(navBtns, panels, 3, contentArea));

        VBox sidebar = new VBox(12, housesBtn, enemiesBtn, combatBtn, loreBtn);
        sidebar.setPadding(new Insets(20, 16, 20, 40));
        sidebar.setPrefWidth(210);
        sidebar.setAlignment(Pos.TOP_LEFT);

        Region vRule = new Region();
        vRule.setPrefWidth(1);
        vRule.setStyle("-fx-background-color: #C8A84B40;");

        HBox body = new HBox(sidebar, vRule, contentArea);
        VBox.setVgrow(body, Priority.ALWAYS);
        body.setPadding(new Insets(16, 0, 0, 0));

        VBox main = new VBox(header, divider, body);
        VBox.setVgrow(body, Priority.ALWAYS);

        StackPane root = new StackPane(bgView, overlay, main);
        Scene scene = new Scene(root);
        bgView.fitWidthProperty().bind(scene.widthProperty());
        bgView.fitHeightProperty().bind(scene.heightProperty());
        overlay.widthProperty().bind(scene.widthProperty());
        overlay.heightProperty().bind(scene.heightProperty());

        main.setOpacity(0);
        stage.setScene(scene);
        stage.setMaximized(true);
        FadeTransition ft = new FadeTransition(Duration.millis(500), main);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    // ── Navigation ──────────────────────────────────────────────────────────

    private static void activateNav(Button[] btns, Node[] panels, int idx, StackPane area) {
        for (int i = 0; i < btns.length; i++) {
            boolean active = (i == idx);
            btns[i].setUserData(active ? "active" : "inactive");
            btns[i].setStyle(active ? activeSideStyle() : inactiveSideStyle());
            panels[i].setVisible(active);
            if (active) {
                FadeTransition ft = new FadeTransition(Duration.millis(300), panels[i]);
                ft.setFromValue(0); ft.setToValue(1); ft.play();
            }
        }
        for (Button btn : btns) {
            if ("inactive".equals(btn.getUserData())) {
                btn.setOnMouseEntered(e -> { if (isInactive(btn)) btn.setStyle(hoverSideStyle()); });
                btn.setOnMouseExited(e  -> { if (isInactive(btn)) btn.setStyle(inactiveSideStyle()); });
            } else {
                btn.setOnMouseEntered(null);
                btn.setOnMouseExited(null);
            }
        }
    }

    private static boolean isInactive(Button b) { return "inactive".equals(b.getUserData()); }

    private static String activeSideStyle() {
        return "-fx-background-color: #6E0000;-fx-text-fill: #E8DAAF;" +
               "-fx-border-color: #C8A84B;-fx-border-width: 0 0 0 3;" +
               "-fx-background-radius: 2;-fx-cursor: hand;";
    }
    private static String inactiveSideStyle() {
        return "-fx-background-color: rgba(20,20,20,0.0);-fx-text-fill: #909090;" +
               "-fx-border-color: transparent;-fx-background-radius: 2;-fx-cursor: hand;";
    }
    private static String hoverSideStyle() {
        return "-fx-background-color: rgba(60,60,60,0.6);-fx-text-fill: #E8DAAF;" +
               "-fx-border-color: transparent;-fx-background-radius: 2;-fx-cursor: hand;";
    }

    // ── Houses Panel ─────────────────────────────────────────────────────────

    private static Node buildHousesPanel() {
        VBox title = sectionTitle("NOBLE HOUSES OF WESTEROS");

        // name, motto, hp, armor, atkRange, special name, special desc, bgColor, accent
        Object[][] houses = {
            {"HOUSE TARGARYEN", "Fire and Blood",
             100, 13, "18 – 25",
             "DRAGONFIRE",
             "Deals 2× attack damage in a single guaranteed hit. Never misses.\n3-turn cooldown.",
             "#5A0000CC", "#C0392B"},
            {"HOUSE LANNISTER", "Hear Me Roar!",
             100, 18, "15 – 21",
             "IRON BANK",
             "Attacks and steals 50% of damage dealt as HP. On miss: recovers 10 HP instead.\n3-turn cooldown.",
             "#5A4800CC", "#D4AC0D"},
            {"HOUSE STARK",     "Winter is Coming",
             125, 13, "16 – 22",
             "PACK HUNT",
             "Ghost joins the fight — two separate strikes, each with its own miss chance.\n3-turn cooldown.",
             "#1A2A3ACC", "#5D8AA8"},
        };

        HBox cards = new HBox(20);
        cards.setAlignment(Pos.CENTER);
        cards.setPadding(new Insets(10, 40, 30, 20));

        for (Object[] h : houses) {
            cards.getChildren().add(houseCard(h));
        }

        VBox wrapper = new VBox(title, cards);
        return styledScroll(wrapper);
    }

    private static VBox houseCard(Object[] h) {
        String name    = (String) h[0];
        String motto   = (String) h[1];
        int    hp      = (int)    h[2];
        int    armor   = (int)    h[3];
        String atk     = (String) h[4];
        String specNm  = (String) h[5];
        String specDsc = (String) h[6];
        String bgColor = (String) h[7];
        String accent  = (String) h[8];

        Text nameText = new Text(name);
        nameText.setFont(Fonts.got(16));
        nameText.setFill(Color.web(accent));
        nameText.setEffect(new DropShadow(10, Color.web(accent)));

        Text mottoText = new Text("\"" + motto + "\"");
        mottoText.setFont(Font.font("Georgia", FontPosture.ITALIC, 13));
        mottoText.setFill(Color.color(0.80, 0.80, 0.80, 0.9));

        Region sep1 = hRule(accent);

        // Stat block
        VBox stats = new VBox(4,
            statRow("HP",     String.valueOf(hp),    "#2ECC71"),
            statRow("ARMOR",  String.valueOf(armor),  "#3498DB"),
            statRow("ATTACK", atk + " dmg",           "#E67E22")
        );
        stats.setAlignment(Pos.CENTER_LEFT);

        Region sep2 = hRule(accent);

        // Special ability
        Text specLabel = new Text("SPECIAL  ·  " + specNm);
        specLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 12));
        specLabel.setFill(Color.web("#C8A84B"));

        Text specText = new Text(specDsc);
        specText.setFont(Font.font("Georgia", FontPosture.ITALIC, 12));
        specText.setFill(Color.color(0.80, 0.80, 0.80, 0.92));
        specText.setWrappingWidth(200);
        specText.setLineSpacing(3);

        VBox card = new VBox(10,
            nameText, mottoText, sep1,
            stats,    sep2,
            specLabel, specText);
        card.setAlignment(Pos.TOP_LEFT);
        card.setPadding(new Insets(20, 18, 20, 18));
        card.setPrefWidth(250);
        card.setStyle(
            "-fx-background-color: " + bgColor + ";" +
            "-fx-border-color: " + accent + "80;" +
            "-fx-border-width: 1;-fx-border-radius: 4;-fx-background-radius: 4;"
        );
        card.setEffect(new DropShadow(14, Color.web(accent + "55")));
        return card;
    }

    // ── Enemies Panel ────────────────────────────────────────────────────────

    private static Node buildEnemiesPanel() {
        VBox title = sectionTitle("ENEMIES BEYOND THE WALL");

        // level, name, location, hp, atkRange, description
        Object[][] enemies = {
            {1, "WILDLING",     "Haunted Forest",        80,  "6 – 10",
             "Savage fighters from beyond the Wall, skilled in ambush and guerrilla tactics. Do not underestimate their ferocity in numbers."},
            {2, "GIANT",        "Fist of the First Men", 130, "7 – 13",
             "Colossal beings with immense strength, feared even by seasoned warriors. Their blows shake the earth itself."},
            {3, "WHITE WALKER", "Frostfangs",            165, "9 – 16",
             "Ancient undead creatures born from the deepest winter. They do not bleed, they do not tire, and they remember every hero who failed before you."},
            {4, "NIGHT KING",   "Land of Always Winter", 220, "11 – 23",
             "The supreme commander of the dead. He has waited eight thousand years for this moment, and he is not afraid of you."},
        };
        String[] bgColors = {"#3B1A0ACC", "#0F2020CC", "#0A1A2ACC", "#1A0A2ECC"};
        String[] accents  = {"#CD853F",   "#5F9EA0",   "#6FA8DC",   "#9B59B6"  };

        VBox list = new VBox(14);
        list.setPadding(new Insets(10, 40, 30, 20));
        for (int i = 0; i < enemies.length; i++) {
            list.getChildren().add(enemyCard(enemies[i], bgColors[i], accents[i]));
        }

        VBox wrapper = new VBox(title, list);
        return styledScroll(wrapper);
    }

    private static HBox enemyCard(Object[] e, String bgColor, String accent) {
        int    level = (int)    e[0];
        String name  = (String) e[1];
        String loc   = (String) e[2];
        int    hp    = (int)    e[3];
        String atk   = (String) e[4];
        String desc  = (String) e[5];

        // Level badge
        Text lvlBadge = new Text("LVL " + level);
        lvlBadge.setFont(Font.font("Georgia", FontWeight.BOLD, 11));
        lvlBadge.setFill(Color.web(accent));
        StackPane badge = new StackPane(lvlBadge);
        badge.setPadding(new Insets(3, 7, 3, 7));
        badge.setStyle("-fx-background-color: " + accent + "30;-fx-border-color: " + accent +
                       ";-fx-border-width: 1;-fx-border-radius: 3;-fx-background-radius: 3;");

        Text nameText = new Text(name);
        nameText.setFont(Font.font("Georgia", FontWeight.BOLD, 19));
        nameText.setFill(Color.web(accent));
        nameText.setEffect(new DropShadow(8, Color.web(accent)));

        Text locText = new Text(loc);
        locText.setFont(Font.font("Georgia", FontPosture.ITALIC, 12));
        locText.setFill(Color.color(0.65, 0.65, 0.65, 0.9));

        // Stats row
        HBox statsRow = new HBox(18,
            inlineStatBox("HP",     String.valueOf(hp), "#2ECC71"),
            inlineStatBox("ATTACK", atk + " dmg",       "#E74C3C")
        );

        VBox left = new VBox(6, badge, nameText, locText, statsRow);
        left.setPrefWidth(240);
        left.setAlignment(Pos.TOP_LEFT);

        Text descText = new Text(desc);
        descText.setFont(Font.font("Georgia", 14));
        descText.setFill(Color.color(0.84, 0.84, 0.84, 1));
        descText.setWrappingWidth(480);
        descText.setLineSpacing(4);

        HBox card = new HBox(28, left, descText);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(18, 22, 18, 22));
        card.setStyle(
            "-fx-background-color: " + bgColor + ";" +
            "-fx-border-color: " + accent + ";-fx-border-width: 0 0 0 3;" +
            "-fx-background-radius: 3;"
        );
        card.setEffect(new DropShadow(10, Color.web(accent + "44")));
        return card;
    }

    // ── How to Play Panel ────────────────────────────────────────────────────

    private static Node buildCombatPanel() {
        VBox title = sectionTitle("HOW TO PLAY");

        VBox content = new VBox(28,
            combatSection("COMBAT ACTIONS", new String[][]{
                {"ATTACK",  "#C8A84B", "Deal damage to the enemy. Each side has a 12.5% miss chance per attack."},
                {"BLOCK",   "#3498DB", "Raise your shield to completely negate incoming damage. Costs 2 armor per use. Becomes unavailable when armor reaches 0."},
                {"SPECIAL", "#E67E22", "Use your house's unique ability. Deals bonus damage, heals, or strikes twice. Triggers a 3-turn cooldown."},
                {"RUN",     "#E74C3C", "Retreat to the previous level. You will face the same enemy again when you return."},
            }),
            combatSection("ARMOR", new String[][]{
                {"Damage Reduction", "#3498DB", "Armor reduces every incoming hit by 30% of its current value. Higher armor means less damage taken per hit."},
                {"Blocking",         "#3498DB", "Each successful block costs 2 armor. Once armor hits 0, the Block button is disabled for the rest of the fight."},
                {"Repair",           "#3498DB", "You can restore 7 armor at the Tavern before the final battle."},
            }),
            combatSection("COMBAT ODDS", new String[][]{
                {"Miss Chance",   "#F39C12", "Both you and the enemy have a 12.5% (1 in 8) chance to miss each attack."},
                {"Critical Hit",  "#E74C3C", "Regular attacks have a 15% (3 in 20) chance to deal 2× damage."},
                {"Special Moves", "#E67E22", "DRAGONFIRE and PACK HUNT always hit (no miss roll). IRON BANK checks miss; on miss it heals you instead."},
            }),
            combatSection("THE TAVERN", new String[][]{
                {"REST",             "#27AE60", "Recover 40 HP, capped at your starting maximum."},
                {"REPAIR ARMOR",     "#2980B9", "Restore 7 armor. Useful if you have been blocking frequently."},
                {"STRENGTH POTION",  "#E67E22", "Permanently add 5–10 damage to all your attacks for the rest of the run."},
            }),
            combatSection("SPECIAL ABILITIES", new String[][]{
                {"DRAGONFIRE (Targaryen)",   "#C0392B", "Deals 2× your normal attack damage. Guaranteed to connect — no miss roll. Best used against high-HP enemies."},
                {"IRON BANK (Lannister)",    "#D4AC0D", "On hit: deal damage and recover 50% of it as HP. On miss: recover 10 HP. Never wastes a turn."},
                {"PACK HUNT (Stark)",        "#5D8AA8", "Two independent strikes, each with its own 12.5% miss chance. Total expected output is roughly 1.75× a normal attack."},
            })
        );
        content.setPadding(new Insets(0, 40, 40, 20));

        VBox wrapper = new VBox(title, content);
        return styledScroll(wrapper);
    }

    private static VBox combatSection(String heading, String[][] rows) {
        Text headText = new Text(heading);
        headText.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        headText.setFill(Color.web("#C8A84B"));

        VBox rowsBox = new VBox(8);
        for (String[] row : rows) {
            HBox line = new HBox(14);
            line.setAlignment(Pos.TOP_LEFT);

            Label label = new Label(row[0]);
            label.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
            label.setTextFill(Color.web(row[1]));
            label.setMinWidth(190);

            Text desc = new Text(row[2]);
            desc.setFont(Font.font("Georgia", 13));
            desc.setFill(Color.color(0.82, 0.82, 0.82, 0.95));
            desc.setWrappingWidth(580);
            desc.setLineSpacing(3);

            line.getChildren().addAll(label, desc);
            rowsBox.getChildren().add(line);
        }

        VBox section = new VBox(10, headText, rowsBox);
        section.setPadding(new Insets(14, 16, 14, 16));
        section.setStyle(
            "-fx-background-color: rgba(0,0,0,0.30);" +
            "-fx-border-color: #C8A84B30;-fx-border-width: 1;" +
            "-fx-background-radius: 3;-fx-border-radius: 3;"
        );
        return section;
    }

    // ── Lore Panel ────────────────────────────────────────────────────────────

    private static Node buildLorePanel() {
        VBox title = sectionTitle("THE LEGEND OF THE LONG NIGHT");

        String lore =
            "Long ago, during the Age of Heroes, a great darkness fell upon Westeros.\n\n" +
            "An endless winter, known as the Long Night, brought death and despair to all the lands " +
            "of men. The sun hid its face for a generation, and cold winds blew from the north like " +
            "the breath of death itself.\n\n" +
            "From the Land of Always Winter, the White Walkers emerged — ancient beings of ice and " +
            "shadow, forged from the darkness before time. They do not hunger. They do not tire. " +
            "They do not die unless you know how to kill them.\n\n" +
            "The dead rose in their wake, swelling their army with every soul that fell. Villages " +
            "were emptied. Kingdoms fell silent. The realm's greatest heroes rode north and never " +
            "returned.\n\n" +
            "Only the unity of Westeros' greatest warriors could drive them back. The last hero " +
            "stood against the darkness, and with dragonglass and fire, the Long Night was ended. " +
            "The White Walkers retreated. The Wall was raised. The world forgot.\n\n" +
            "But the Night King cannot be killed by forgetting.\n\n" +
            "Now the darkness returns. The Wall has fallen. The Night King marches south with an " +
            "army of the dead, and the realm's only hope is a single warrior willing to go north " +
            "when everyone else is running south.\n\n" +
            "Choose your house. Take up arms. The Long Night will not end itself.";

        Text loreText = new Text(lore);
        loreText.setFont(Font.font("Georgia", 16));
        loreText.setFill(Color.color(0.88, 0.88, 0.88, 1.0));
        loreText.setLineSpacing(6);
        loreText.setWrappingWidth(680);

        VBox content = new VBox(20, title, loreText);
        content.setPadding(new Insets(0, 40, 40, 20));
        return styledScroll(content);
    }

    // ── Shared helpers ────────────────────────────────────────────────────────

    private static VBox sectionTitle(String text) {
        Text t = new Text(text);
        t.setFont(Fonts.got(22));
        t.setFill(Color.WHITE);
        t.setEffect(new DropShadow(12, Color.color(0.9, 0.22, 0.0, 0.6)));

        Region line = new Region();
        line.setPrefHeight(1);
        line.setPrefWidth(500);
        line.setStyle("-fx-background-color: #C8A84B60;");

        VBox box = new VBox(10, t, line);
        box.setPadding(new Insets(20, 40, 10, 20));
        return box;
    }

    private static HBox statRow(String label, String value, String color) {
        Label lbl = new Label(label + "  ");
        lbl.setFont(Font.font("Georgia", FontWeight.BOLD, 12));
        lbl.setTextFill(Color.color(0.65, 0.65, 0.65, 0.9));
        lbl.setMinWidth(60);

        Text val = new Text(value);
        val.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        val.setFill(Color.web(color));

        HBox row = new HBox(lbl, val);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private static VBox inlineStatBox(String label, String value, String color) {
        Text lbl = new Text(label);
        lbl.setFont(Font.font("Georgia", FontWeight.BOLD, 10));
        lbl.setFill(Color.color(0.60, 0.60, 0.60, 0.85));

        Text val = new Text(value);
        val.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        val.setFill(Color.web(color));

        VBox box = new VBox(1, lbl, val);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    private static Region hRule(String accent) {
        Region r = new Region();
        r.setPrefSize(200, 1);
        r.setMaxHeight(1);
        r.setStyle("-fx-background-color: " + accent + "50;");
        return r;
    }

    private static ScrollPane styledScroll(Node content) {
        ScrollPane sp = new ScrollPane(content);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setStyle(
            "-fx-background: transparent;" +
            "-fx-background-color: transparent;" +
            "-fx-border-color: transparent;"
        );
        return sp;
    }

    private static Button sideBtn(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        btn.setPrefSize(160, 44);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(0, 0, 0, 14));
        return btn;
    }

    private static Button backButton(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Georgia", FontWeight.BOLD, 15));
        btn.setPrefSize(120, 38);
        String base  = "-fx-background-color:#1C1C1C;-fx-text-fill:#E8DAAF;" +
                       "-fx-border-color:rgba(200,168,75,0.5);-fx-border-width:1;" +
                       "-fx-border-radius:2;-fx-background-radius:2;-fx-cursor:hand;";
        String hover = "-fx-background-color:#3D3D3D;-fx-text-fill:#E8DAAF;" +
                       "-fx-border-color:rgba(200,168,75,0.8);-fx-border-width:1;" +
                       "-fx-border-radius:2;-fx-background-radius:2;-fx-cursor:hand;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e  -> btn.setStyle(base));
        return btn;
    }
}
