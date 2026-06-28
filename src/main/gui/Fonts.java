package main.gui;

import javafx.scene.text.Font;

public class Fonts {

    private static final String GOT_PATH = "/main/assets/fonts/Game of Thrones.ttf";

    /** Returns the Game of Thrones font at the given size, falling back to Georgia if unavailable. */
    public static Font got(double size) {
        Font f = Font.loadFont(Fonts.class.getResourceAsStream(GOT_PATH), size);
        return f != null ? f : Font.font("Georgia", size);
    }
}
