package cs125.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Process;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class MusicScreen extends Activity {
    private SongManager manager;
    private ConstraintLayout screen;
    private int score = 0;
    private TextView scoreView;
    private static int[] scoreCounts = new int[3];
    public static boolean isPaused;
    private boolean readyToStart = false;
    public String song;

    @Override
    public void onBackPressed() {
        if (isPaused && manager.isSongInProgress) {
            isPaused = false;
            manager.resumeSong();
            findViewById(R.id.pause_menu).setVisibility(View.INVISIBLE);
            System.out.println("UNPAUSED VIA BACK! isPaused = " + isPaused);
        } else if (!isPaused && readyToStart && manager.isSongInProgress){
            isPaused = true;
            manager.pauseSong();
            findViewById(R.id.pause_menu).setVisibility(View.VISIBLE);
            findViewById(R.id.pause_menu).bringToFront();
            System.out.println("PAUSED VIA BACK! isPaused = " + isPaused);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_screen);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        manager = new SongManager(this);
        screen = findViewById(R.id.music_screen);
        scoreView = findViewById(R.id.score);
        isPaused = false;
        System.out.println("Is paused reset to: " + isPaused);

        song = MainActivity.songTitle;
        TextView text = findViewById(R.id.song_title);
        text.setText(song);

        View bar = screen.findViewById(R.id.progress_bar);
        ViewGroup.LayoutParams lp = bar.getLayoutParams();
        lp.width = SongManager.screenWidth;
        bar.setLayoutParams(lp);

        //Animation bgFadeIn = new AlphaAnimation(0, 1);
        if (song.equals("Give Me Candy")) {
            screen.setBackground(getDrawable(R.drawable.candy));
        } else if (song.equals("Carol of the Bells")) {
            screen.setBackground(getDrawable(R.drawable.carol));
        } else if (song.equals("The Road")) {
            screen.setBackground(getDrawable(R.drawable.road));
        }
        /*bgFadeIn.setDuration(1500);
        screen.startAnimation(bgFadeIn);*/

        Animation titleLoad = new ScaleAnimation(0, 1, 0.1f, 0.1f);
        titleLoad.setDuration(700);
        titleLoad.setStartOffset(500);
        text.setVisibility(View.VISIBLE);

        Animation titleDrop = new ScaleAnimation(1, 1, 0.1f, 10);
        titleDrop.setDuration(1000);
        titleDrop.setStartOffset(1500);

        LinearLayout cores = findViewById(R.id.core_container);
        Animation coresAnimation = new TranslateAnimation(0, 0, cores.getY() + 1000f, 0);
        coresAnimation.setDuration(1000);
        coresAnimation.setStartOffset(3000);

        Animation scoreFadeIn = new AlphaAnimation(0, 1);
        scoreFadeIn.setDuration(1000);
        scoreFadeIn.setStartOffset(4500);

        ImageView pauseButton = findViewById(R.id.pause_button);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(titleLoad);
        set.addAnimation(titleDrop);
        text.startAnimation(set);
        cores.startAnimation(coresAnimation);
        scoreView.startAnimation(scoreFadeIn);
        pauseButton.startAnimation(scoreFadeIn);

        ThreadHandler.addRunnable(new Runnable() {
            @Override
            public void run() {
                android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                if (song.equals("Give Me Candy")) {
                    manager.loadSong("candy");
                } else if (song.equals("Carol of the Bells")) {
                    manager.loadSong("carol");
                } else if (song.equals("The Road")) {
                    manager.loadSong("the_road");
                }
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                readyToStart = true;
                manager.playSong();
            }
        });
    }

    /*@Override
    public void onPause() {
        isPaused = true;
        manager.pauseSong();
        super.onPause();
    }*/

    public ConstraintLayout getScreen() {
        return screen;
    }

    public void pauseAndResume(View view) {
        if (isPaused && manager.isSongInProgress) {
            isPaused = false;
            manager.resumeSong();
            findViewById(R.id.pause_menu).setVisibility(View.INVISIBLE);
            System.out.println("UNPAUSED VIA BUTTON! isPaused = " + isPaused);
        } else if (!isPaused && readyToStart && manager.isSongInProgress){
            isPaused = true;
            manager.pauseSong();
            findViewById(R.id.pause_menu).setVisibility(View.VISIBLE);
            findViewById(R.id.pause_menu).bringToFront();
            System.out.println("PAUSED VIA BUTTON! isPaused = " + isPaused);
        }
    }

    public void resumeSong(View view) {
        isPaused = false;
        manager.resumeSong();
        findViewById(R.id.pause_menu).setVisibility(View.INVISIBLE);
    }

    public void restartSong(View view) {
        manager.endSong();
        for (Note note : Song.getActiveLeftNotes()) {
            note.getView().animate().cancel();
            note.getView().getAnimation().cancel();
            screen.removeView(note.getView());
        }
        for (Note note : Song.getActiveRightNotes()) {
            note.getView().animate().cancel();
            note.getView().getAnimation().cancel();
            screen.removeView(note.getView());
        }
        recreate();
    }

    public void quitSong(View view) {
        manager.endSong();
        for (Note note : Song.getActiveLeftNotes()) {
            note.getView().animate().cancel();
            note.getView().getAnimation().cancel();
            screen.removeView(note.getView());
        }
        for (Note note : Song.getActiveRightNotes()) {
            note.getView().animate().cancel();
            note.getView().getAnimation().cancel();
            screen.removeView(note.getView());
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAfterTransition();
    }

    public void leftClicked(View view) {
        if (Song.getActiveLeftNotes().size() > 0 && !isPaused) {
            Note closestNote = Song.getActiveLeftNotes().get(0);
            double totalTimeTaken = System.currentTimeMillis() - (manager.getStartTime() + closestNote.getTimeDelay());
            double percentDistCovered = totalTimeTaken / (Note.DURATION);
            if (percentDistCovered >= 0.80 && closestNote.getView() != null) {
                float x = (SongManager.screenWidth / 2) - ((SongManager.screenWidth / 2) * (float)percentDistCovered);
                closestNote.noteClicked(x, true);
                score += 500;
                scoreCounts[0]++;
                displayHitScore(0, true);
                updateScore();
            } else if (percentDistCovered >= 0.60 && percentDistCovered < 0.80) {
                float x = (SongManager.screenWidth / 2) - ((SongManager.screenWidth / 2) * (float)percentDistCovered);
                closestNote.noteClicked(x, true);
                score += 250;
                scoreCounts[1]++;
                displayHitScore(1, true);
                updateScore();
            } else if (percentDistCovered >= 0.40 && percentDistCovered < 0.60){
                float x = (SongManager.screenWidth / 2) - ((SongManager.screenWidth / 2) * (float)percentDistCovered);
                closestNote.noteClicked(x, true);
                score += 0;
                scoreCounts[2]++;
                displayHitScore(2, true);
                updateScore();
            }
        }
    }

    public void rightClicked(View view) {
        if (Song.getActiveRightNotes().size() > 0 && !isPaused) {
            Note closestNote = Song.getActiveRightNotes().get(0);
            double totalTimeTaken = System.currentTimeMillis() - (manager.getStartTime() + closestNote.getTimeDelay());
            double percentDistCovered = totalTimeTaken / (Note.DURATION);
            if (percentDistCovered >= 0.80 && closestNote.getView() != null) {
                float x = (SongManager.screenWidth / 2) + ((SongManager.screenWidth / 2) * (float)percentDistCovered);
                closestNote.noteClicked(x, false);
                score += 500;
                scoreCounts[0]++;
                displayHitScore(0, false);
                updateScore();
            } else if (percentDistCovered >= 0.55 && percentDistCovered < 0.80) {
                float x = (SongManager.screenWidth / 2) + ((SongManager.screenWidth / 2) * (float)percentDistCovered);
                closestNote.noteClicked(x, false);
                score += 250;
                scoreCounts[1]++;
                displayHitScore(1, false);
                updateScore();
            } else if (percentDistCovered >= 0.40 && percentDistCovered < 0.55){
                float x = (SongManager.screenWidth / 2) + ((SongManager.screenWidth / 2) * (float)percentDistCovered);
                closestNote.noteClicked(x, false);
                score += 0;
                scoreCounts[2]++;
                displayHitScore(2, false);
                updateScore();
            }
        }
    }

    public void displayHitScore(int scoreIndex, boolean isLeft) {
        TextView text;
        if (isLeft) {
            text = findViewById(R.id.left_hit_text);
        } else {
            text = findViewById(R.id.right_hit_text);
        }
        final TextView textView = text;
        if (scoreIndex == 0) {
            textView.setText("PERFECT");
            textView.setTextColor(Color.rgb(255, 0, 255));
        } else if (scoreIndex == 1) {
            textView.setText("GOOD");
            textView.setTextColor(Color.rgb(0, 255, 255));
        } else {
            textView.setText("MISS");
            text.setTextColor(Color.rgb(255, 0, 0));
        }
        Animation fadeOut = new AlphaAnimation(1, 0);
        Animation.AnimationListener listener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                textView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        fadeOut.setAnimationListener(listener);
        fadeOut.setDuration(500);
        fadeOut.setStartOffset(500);
        textView.startAnimation(fadeOut);
    }

    public void updateScore() {
        //CHANGE THE '8' TO HOW MANY TOTAL # SHOULD BE DISPLAYED
        scoreView.setText(String.format(Locale.ENGLISH, "%08d", score));
    }

    public int[] getScoreCounts() {
        return scoreCounts;
    }

    public static void addMissToScoreCounts(){scoreCounts[2]++;}
    public static void resetScoreCounts() {
        scoreCounts[0] = 0;
        scoreCounts[1] = 0;
        scoreCounts[2] = 0;
    }

    public int getScore() {
        return score;
    }
}
