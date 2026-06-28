package main.gui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Manages background music across all screens.
 * Calling play() with the same track that is already playing is a no-op,
 * so navigating between screens of the same "zone" never restarts the song.
 */
public class AudioManager {

    public enum Track { MENU, BATTLE }

    private static final String MENU_PATH   = "/main/assets/music/gotsoundtrack.weba";
    private static final String BATTLE_PATH = "/main/assets/music/battle.m4a";

    private static MediaPlayer player;
    private static Track currentTrack;

    /** Starts the given track looping. No-op if that track is already playing. */
    public static void play(Track track) {
        if (track == currentTrack && player != null
                && player.getStatus() == MediaPlayer.Status.PLAYING) return;

        stop();
        currentTrack = track;

        String path = track == Track.BATTLE ? BATTLE_PATH : MENU_PATH;
        try {
            var url = AudioManager.class.getResource(path);
            if (url == null) return;
            player = new MediaPlayer(new Media(url.toExternalForm()));
            player.setCycleCount(MediaPlayer.INDEFINITE);
            player.setVolume(track == Track.BATTLE ? 0.7 : 0.5);
            player.play();
        } catch (Exception ignored) {
            // Unsupported format or missing codec — game continues silently
        }
    }

    /** Stops and disposes the current player. */
    public static void stop() {
        if (player != null) {
            player.stop();
            player.dispose();
            player = null;
        }
        currentTrack = null;
    }
}
