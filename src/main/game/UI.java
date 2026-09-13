package main.game;

import java.util.Scanner;

public class UI {
    // ANSI Color Codes
    public static final String RESET  = "[0m";

    public static final String BLACK  = "[30m";
    public static final String RED    = "[31m";
    public static final String GREEN  = "[32m";
    public static final String YELLOW = "[33m";
    public static final String BLUE   = "[34m";
    public static final String PURPLE = "[35m";
    public static final String CYAN   = "[36m";
    public static final String WHITE  = "[37m";

    public static final String BOLD = "[1m";

    /**
     * Single Scanner shared by every console-mode class. Each class used to open
     * its own `new Scanner(System.in)`; multiple Scanners racing to buffer the same
     * underlying stream silently ate each other's input and could throw
     * NoSuchElementException mid-game. Everyone should read from this instance instead.
     */
    public static final Scanner IN = new Scanner(System.in);
}
