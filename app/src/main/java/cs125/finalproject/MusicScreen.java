package cs125.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
    private int[] scoreCounts = new int[4];
    private boolean isPaused = false;
    private boolean readyToStart = false;

    @Override
    public void onBackPressed() {
        if (isPaused) {
            isPaused = false;
            manager.resumeSong();
        } else if (readyToStart){
            isPaused = true;
            manager.pauseSong();
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

        Intent intent = getIntent();
        final String song = intent.getStringExtra(MainActivity.SONG_TITLE);
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
        if (isPaused) {
            isPaused = false;
            manager.resumeSong();
        } else if (readyToStart){
            isPaused = true;
            manager.pauseSong();
        }
    }

    public void leftClicked(View view) {
        if (Song.getActiveLeftNotes().size() > 0 && !isPaused) {
            Note closestNote = Song.getActiveLeftNotes().get(0);
            double totalTimeTaken = System.currentTimeMillis() - (Song.startTime + closestNote.getTimeDelay());
            double percentDistCovered = totalTimeTaken / (Note.DURATION);
            if (percentDistCovered >= 0.75 && closestNote.getView() != null) {
                float x = (SongManager.screenWidth / 2) - ((SongManager.screenWidth / 2) * (float)percentDistCovered);
                closestNote.noteClicked(x, true);
                score += 100;
                scoreCounts[0]++;
                updateScore();
            }
        }
    }

    public void rightClicked(View view) {
        if (Song.getActiveRightNotes().size() > 0 && !isPaused) {
            Note closestNote = Song.getActiveRightNotes().get(0);
            double totalTimeTaken = System.currentTimeMillis() - (Song.startTime + closestNote.getTimeDelay());
            double percentDistCovered = totalTimeTaken / (Note.DURATION);
            if (percentDistCovered >= 0.85 && closestNote.getView() != null) {
                float x = (SongManager.screenWidth / 2) + ((SongManager.screenWidth / 2) * (float)percentDistCovered);
                closestNote.noteClicked(x, false);
                score += 500;
                scoreCounts[0]++;
                updateScore();
            } else if (percentDistCovered >= 0.70 && percentDistCovered < 0.85) {
                float x = (SongManager.screenWidth / 2) + ((SongManager.screenWidth / 2) * (float)percentDistCovered);
                closestNote.noteClicked(x, false);
                score += 250;
                scoreCounts[1]++;
                updateScore();
            } else if (percentDistCovered >= 0.55 && percentDistCovered < 0.70) {
                float x = (SongManager.screenWidth / 2) + ((SongManager.screenWidth / 2) * (float)percentDistCovered);
                closestNote.noteClicked(x, false);
                score += 100;
                scoreCounts[2]++;
                updateScore();
            } else if (percentDistCovered >= 0.40 && percentDistCovered < 0.55){
                float x = (SongManager.screenWidth / 2) + ((SongManager.screenWidth / 2) * (float)percentDistCovered);
                closestNote.noteClicked(x, false);
                score += 0;
                scoreCounts[3]++;
                updateScore();
            }
        }
    }

    public void updateScore() {
        //CHANGE THE '8' TO HOW MANY TOTAL # SHOULD BE DISPLAYED
        scoreView.setText(String.format(Locale.ENGLISH, "%08d", score));
    }

    public int[] getScoreCounts() {
        return scoreCounts;
    }

    public int getScore() {
        return score;
    }
}
