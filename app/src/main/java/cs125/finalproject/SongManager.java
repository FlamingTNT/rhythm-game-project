package cs125.finalproject;

import android.content.Intent;
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
    private ArrayList<Note> noteList = new ArrayList<>();
    private boolean isSongInProgress = false;

    public static int delay = -687;
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
        String filename = "calibration_song";
        int songID = 0;
        if (name.equals("candy")) {
            filename = "candy.txt";
            songID = R.raw.give_me_candy;
        } else if (name.equals("carol")) {
            filename = "carol.txt";
            songID = R.raw.carol_of_the_bells;
        }
        try {
            //if (name.equals("candy")) {
                InputStream is = screen.getAssets().open(filename);
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
                musicPlayer = MediaPlayer.create(screen, songID);
           //}
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentSong = new Song(noteList, musicPlayer);
    }

    public void playSong() {
        screen.getScreen().post(new Runnable() {
            @Override
            public void run() {
                View bar = screen.findViewById(R.id.progress_bar);
                Animation progressBar = new PausableTranslateAnimation(-(bar.getWidth()), 0, 0, 0);
                progressBar.setDuration(musicPlayer.getDuration());
                bar.setVisibility(View.VISIBLE);
                bar.startAnimation(progressBar);
            }
        });

        isSongInProgress = true;
        if (currentSong.play()) {
            currentSong = null;
            noteList = null;
            screen.getScreen().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isSongInProgress = false;
                    musicPlayer.stop();
                    Intent intent = new Intent(screen, ResultsScreen.class);
                    intent.putExtra("scoreCounts", screen.getScoreCounts());
                    intent.putExtra("score", screen.getScore());
                    screen.startActivity(intent);
                }
            }, 3000);
        }
    }

    public void pauseSong() {
        if (isSongInProgress) {
            screen.getScreen().post(new Runnable() {
                @Override
                public void run() {
                    View bar = screen.findViewById(R.id.progress_bar);
                    PausableTranslateAnimation anim = (PausableTranslateAnimation) bar.getAnimation();
                    anim.pause();
                    currentSong.pause();
                }
            });
        }
    }

    public void resumeSong() {
        if (isSongInProgress) {
            screen.getScreen().post(new Runnable() {
                @Override
                public void run() {
                    View bar = screen.findViewById(R.id.progress_bar);
                    PausableTranslateAnimation anim = (PausableTranslateAnimation) bar.getAnimation();
                    anim.resume();
                    currentSong.resume();
                }
            });
        }
    }
}
