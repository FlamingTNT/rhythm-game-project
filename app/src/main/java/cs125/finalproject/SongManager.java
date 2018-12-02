package cs125.finalproject;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SongManager {
    private Song currentSong;
    private MusicScreen screen;
    private final int delay = -687;

    public static MediaPlayer musicPlayer;
    public static int screenWidth;
    public static int screenHeight;
    public static float totalDistance;

    SongManager(MusicScreen screen) {
        this.screen = screen;
        WindowManager wm = screen.getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        totalDistance = ((screenWidth / 2) + 50f);
    }

    public void loadSong(String name) {
        ArrayList<Note> noteList = new ArrayList<>();

        try {
            if (name.equals("candy")) {
                InputStream is = screen.getAssets().open("candy.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String noteLine;
                while ((noteLine = reader.readLine()) != null) {
                    String[] split = noteLine.split(":");
                    if (split[0].equals("0")) { //IS A RIGHT-SIDE NOTE
                        noteList.add(new Note(false, Long.parseLong(split[1]) + delay, screen));
                    } else { //IS A LEFT-SIDE NOTE
                        noteList.add(new Note(true, Long.parseLong(split[1]) + delay, screen));
                    }
                }
                musicPlayer = MediaPlayer.create(screen, R.raw.give_me_candy);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentSong = new Song(noteList);
    }

    public void playSong() {
        screen.getScreen().post(new Runnable() {
            @Override
            public void run() {
                View bar = screen.findViewById(R.id.progress_bar);
                Animation progressBar = new TranslateAnimation(-(bar.getWidth()), 0, 0, 0);
                progressBar.setDuration(SongManager.musicPlayer.getDuration());
                bar.startAnimation(progressBar);
            }
        });
        musicPlayer.start();
        currentSong.play();
    }
}
