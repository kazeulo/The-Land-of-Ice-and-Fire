package main.gui;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        Image bg = new Image(
            GameInfoScreen.class.getResourceAsStream("/main/assets/img/bg/startmenubg.png"));
        ImageView bgView = new ImageView(bg);
        bgView.setPreserveRatio(false);

        Rectangle overlay = new Rectangle();
        overlay.setFill(Color.color(0, 0, 0, 0.62));

        // Header
        Button backBtn = backButton("< BACK");
        backBtn.setOnAction(e -> StartMenu.show(stage));

        Text headerTitle = new Text("GAME INFORMATION");
        headerTitle.setFont(Fonts.got(34));
        headerTitle.setFill(Color.WHITE);
        headerTitle.setEffect(new DropShadow(16, Color.color(0.9, 0.22, 0.0, 0.75)));

        Region leftPad  = new Region();
        Region rightPad = new Region();
        HBox.setHgrow(leftPad, Priority.ALWAYS);
        HBox.setHgrow(rightPad, Priority.ALWAYS);

        HBox header = new HBox(backBtn, leftPad, headerTitle, rightPad);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(22, 40, 16, 40));

        Region divider = new Region();
        divider.setPrefHeight(1);
        divider.setStyle("-fx-background-color: #C8A84B;");
        divider.setEffect(new DropShadow(6, Color.web("#C8A84B")));

        // Content panels───
        Node housesPanel  = buildHousesPanel();
        Node enemiesPanel = buildEnemiesPanel();
        Node lorePanel    = buildLorePanel();
        enemiesPanel.setVisible(false);
        lorePanel.setVisible(false);

        StackPane contentArea = new StackPane(housesPanel, enemiesPanel, lorePanel);
        contentArea.setAlignment(Pos.TOP_LEFT);
        VBox.setVgrow(contentArea, Priority.ALWAYS);
        HBox.setHgrow(contentArea, Priority.ALWAYS);

        // Sidebar navigation
        Button housesBtn  = sideBtn("NOBLE HOUSES");
        Button enemiesBtn = sideBtn("ENEMIES");
        Button loreBtn    = sideBtn("THE LORE");
        Button[] navBtns  = {housesBtn, enemiesBtn, loreBtn};
        Node[]   panels   = {housesPanel, enemiesPanel, lorePanel};

        activateNav(navBtns, panels, 0, contentArea);

        housesBtn.setOnAction(e  -> activateNav(navBtns, panels, 0, contentArea));
        enemiesBtn.setOnAction(e -> activateNav(navBtns, panels, 1, contentArea));
        loreBtn.setOnAction(e    -> activateNav(navBtns, panels, 2, contentArea));

        VBox sidebar = new VBox(12, housesBtn, enemiesBtn, loreBtn);
        sidebar.setPadding(new Insets(20, 16, 20, 40));
        sidebar.setPrefWidth(210);
        sidebar.setAlignment(Pos.TOP_LEFT);

        // Gold vertical rule between sidebar and content
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

    // Navigation helpers───

    private static void activateNav(Button[] btns, Node[] panels, int idx, StackPane area) {
        for (int i = 0; i < btns.length; i++) {
            boolean active = (i == idx);
            btns[i].setUserData(active ? "active" : "inactive");
            btns[i].setStyle(active ? activeSideStyle() : inactiveSideStyle());
            panels[i].setVisible(active);
            if (active) {
                FadeTransition ft = new FadeTransition(Duration.millis(300), panels[i]);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.play();
            }
        }
        // re-attach hover handlers for inactive buttons
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
        return "-fx-background-color: #6E0000;" +
               "-fx-text-fill: #E8DAAF;" +
               "-fx-border-color: #C8A84B;" +
               "-fx-border-width: 0 0 0 3;" +
               "-fx-background-radius: 2;" +
               "-fx-cursor: hand;";
    }

    private static String inactiveSideStyle() {
        return "-fx-background-color: rgba(20,20,20,0.0);" +
               "-fx-text-fill: #909090;" +
               "-fx-border-color: transparent;" +
               "-fx-background-radius: 2;" +
               "-fx-cursor: hand;";
    }

    private static String hoverSideStyle() {
        return "-fx-background-color: rgba(60,60,60,0.6);" +
               "-fx-text-fill: #E8DAAF;" +
               "-fx-border-color: transparent;" +
               "-fx-background-radius: 2;" +
               "-fx-cursor: hand;";
    }

    // Houses panel 

    private static Node buildHousesPanel() {
        VBox title = sectionTitle("NOBLE HOUSES OF WESTEROS");

        HBox cards = new HBox(24,
            houseCard("HOUSE TARGARYEN", "Fire and Blood",
                "Mastery of dragons, granting increased attack power.",
                "#5A0000CC", "#C0392B"),
            houseCard("HOUSE LANNISTER", "Hear Me Roar!",
                "Unyielding wealth and armor, providing additional defense.",
                "#5A4800CC", "#D4AC0D"),
            houseCard("HOUSE STARK", "Winter is Coming",
                "Resilience in the harshest winters, enhancing health and endurance.",
                "#1A2A3ACC", "#5D8AA8")
        );
        cards.setAlignment(Pos.CENTER);
        cards.setPadding(new Insets(10, 40, 30, 20));

        VBox wrapper = new VBox(title, cards);
        ScrollPane scroll = styledScroll(wrapper);
        return scroll;
    }

    private static VBox houseCard(String name, String motto, String ability,
                                   String bgColor, String accent) {
        Text nameText = new Text(name);
        nameText.setFont(Font.font("Georgia", FontWeight.BOLD, 19));
        nameText.setFill(Color.web(accent));
        nameText.setEffect(new DropShadow(10, Color.web(accent)));

        Text mottoText = new Text("\"" + motto + "\"");
        mottoText.setFont(Font.font("Georgia", FontPosture.ITALIC, 14));
        mottoText.setFill(Color.color(0.82, 0.82, 0.82, 0.9));

        Region sep = new Region();
        sep.setPrefSize(180, 1);
        sep.setMaxHeight(1);
        sep.setStyle("-fx-background-color: " + accent + ";");
        sep.setOpacity(0.4);

        Text abilityLabel = new Text("Special Ability");
        abilityLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 12));
        abilityLabel.setFill(Color.web("#C8A84B"));

        Text abilityText = new Text(ability);
        abilityText.setFont(Font.font("Georgia", 14));
        abilityText.setFill(Color.color(0.86, 0.86, 0.86, 1));
        abilityText.setWrappingWidth(190);

        VBox card = new VBox(12, nameText, mottoText, sep, abilityLabel, abilityText);
        card.setAlignment(Pos.TOP_LEFT);
        card.setPadding(new Insets(22, 20, 22, 20));
        card.setPrefSize(240, 240);
        card.setStyle(
            "-fx-background-color: " + bgColor + ";" +
            "-fx-border-color: " + accent + "80;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 4;" +
            "-fx-background-radius: 4;"
        );
        card.setEffect(new DropShadow(14, Color.web(accent + "55")));
        return card;
    }

    // Enemies panel 

    private static Node buildEnemiesPanel() {
        VBox title = sectionTitle("ENEMIES BEYOND THE WALL");

        String[][] data = {
            {"WILDLING",     "Haunted Forest",         "6 – 10",  "Savage fighters from beyond the Wall, skilled in ambush tactics."},
            {"GIANT",        "Fist of the First Men",  "7 – 13",  "Colossal beings with immense strength, feared even by seasoned warriors."},
            {"WHITE WALKER", "Frostfangs",              "9 – 16",  "Ancient undead creatures with terrifying frost magic."},
            {"NIGHT KING",   "Land of Always Winter",  "11 – 23", "The supreme leader of the White Walkers, a being of unparalleled power."}
        };
        String[] bgColors = {"#3B1A0ACC", "#0F2020CC", "#0A1A2ACC", "#1A0A2ECC"};
        String[] accents  = {"#CD853F",   "#5F9EA0",   "#6FA8DC",   "#9B59B6"};

        VBox list = new VBox(14);
        list.setPadding(new Insets(10, 40, 30, 20));
        for (int i = 0; i < data.length; i++) {
            list.getChildren().add(enemyCard(data[i], bgColors[i], accents[i]));
        }

        VBox wrapper = new VBox(title, list);
        return styledScroll(wrapper);
    }

    private static HBox enemyCard(String[] data, String bgColor, String accent) {
        Text name = new Text(data[0]);
        name.setFont(Font.font("Georgia", FontWeight.BOLD, 19));
        name.setFill(Color.web(accent));
        name.setEffect(new DropShadow(8, Color.web(accent)));

        Text location = new Text("Location: " + data[1]);
        location.setFont(Font.font("Georgia", FontPosture.ITALIC, 13));
        location.setFill(Color.color(0.72, 0.72, 0.72, 0.9));

        Text dmgLabel = new Text("Attack: ");
        dmgLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        dmgLabel.setFill(Color.web("#C8A84B"));
        Text dmgVal = new Text(data[2] + " damage");
        dmgVal.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        dmgVal.setFill(Color.web("#E74C3C"));
        TextFlow dmg = new TextFlow(dmgLabel, dmgVal);

        VBox left = new VBox(6, name, location, dmg);
        left.setPrefWidth(250);
        left.setAlignment(Pos.CENTER_LEFT);

        Text desc = new Text(data[3]);
        desc.setFont(Font.font("Georgia", 14));
        desc.setFill(Color.color(0.86, 0.86, 0.86, 1));
        desc.setWrappingWidth(520);
        VBox right = new VBox(desc);
        right.setAlignment(Pos.CENTER_LEFT);

        HBox card = new HBox(28, left, right);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(18, 22, 18, 22));
        card.setStyle(
            "-fx-background-color: " + bgColor + ";" +
            "-fx-border-color: " + accent + ";" +
            "-fx-border-width: 0 0 0 3;" +
            "-fx-background-radius: 3;"
        );
        card.setEffect(new DropShadow(10, Color.web(accent + "44")));
        return card;
    }

    // Lore panel─

    private static Node buildLorePanel() {
        VBox title = sectionTitle("THE LEGEND OF THE LONG NIGHT");

        String lore =
            "Long ago, during the Age of Heroes, a great darkness fell upon Westeros.\n\n" +
            "An endless winter, known as the Long Night, brought death and despair to all " +
            "the lands of men. The sun hid its face for a generation, and cold winds blew " +
            "from the north like the breath of death itself.\n\n" +
            "From the Land of Always Winter, the White Walkers emerged — ancient beings of " +
            "ice and shadow, bringing fear and ruin to the realm. The dead rose in their " +
            "wake, swelling their army with every soul that fell.\n\n" +
            "Only the unity of Westeros' greatest warriors could drive them back. " +
            "The last hero stood against the darkness, and with the blade Lightbringer, " +
            "the Long Night was ended.\n\n" +
            "Now the darkness returns. The Night King has risen once more, and the Wall " +
            "trembles before his power. The realm needs a champion — one who will choose " +
            "a house, take up arms, and march north into the abyss.\n\n" +
            "Will you lead Westeros to victory, or will winter consume all?\n\n" +
            "The choice is yours.";

        Text loreText = new Text(lore);
        loreText.setFont(Font.font("Georgia", 16));
        loreText.setFill(Color.color(0.88, 0.88, 0.88, 1.0));
        loreText.setLineSpacing(5);
        loreText.setWrappingWidth(680);

        VBox content = new VBox(20, title, loreText);
        content.setPadding(new Insets(0, 40, 40, 20));
        return styledScroll(content);
    }

    // Shared UI helpers──

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
        btn.setFont(Font.font("Georgia", FontWeight.BOLD, 15));
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
