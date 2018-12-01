package cs125.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class MusicScreen extends Activity {
    private SongManager manager;
    private ConstraintLayout screen;
    private int score = 0;
    TextView scoreView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_screen);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        manager = new SongManager(this);
        screen = findViewById(R.id.music_screen);
        scoreView = findViewById(R.id.score);

        Intent intent = getIntent();
        String song = intent.getStringExtra(MainActivity.SONG_TITLE);
        TextView text = findViewById(R.id.song_title);
        text.setText(song);

        //Animation bgFadeIn = new AlphaAnimation(0, 1);
        if (song.equals("Give Me Candy")) {
            screen.setBackground(getDrawable(R.drawable.candy));
        }
        /*bgFadeIn.setDuration(1500);
        screen.startAnimation(bgFadeIn);*/

        Animation titleLoad = new ScaleAnimation(0, 1, 0.2f, 0.2f);
        titleLoad.setDuration(700);
        titleLoad.setStartOffset(500);
        text.setVisibility(View.VISIBLE);

        Animation titleDrop = new ScaleAnimation(1, 1, 0.2f, 5);
        titleDrop.setDuration(1000);
        titleDrop.setStartOffset(1500);

        LinearLayout cores = findViewById(R.id.core_container);
        Animation coresAnimation = new TranslateAnimation(0, 0, cores.getY() + 1000f, 0);
        coresAnimation.setDuration(1000);
        coresAnimation.setStartOffset(3000);

        Animation scoreFadeIn = new AlphaAnimation(0, 1);
        scoreFadeIn.setDuration(1000);
        scoreFadeIn.setStartOffset(4500);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(titleLoad);
        set.addAnimation(titleDrop);
        text.startAnimation(set);
        cores.startAnimation(coresAnimation);
        scoreView.startAnimation(scoreFadeIn);

        new Thread(new Runnable() {
            @Override
            public void run() {
                manager.loadSong("candy");
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                manager.playSong();
            }
        }).start();

    }

    public void leftClicked(View view) {
        if (Song.getActiveLeftNotes().size() > 0) {
            Note closestNote = Song.getActiveLeftNotes().get(0);
            double totalTimeTaken = System.currentTimeMillis() - (Song.startTime + closestNote.getTimeDelay());
            double percentDistCovered = totalTimeTaken / (Note.DURATION);
            //System.out.println("Total time taken: " + totalTimeTaken);
            //System.out.println("Note destroyed! % dist = " + percentDistCovered);
            if (percentDistCovered >= 0.75 && closestNote.getView() != null) {
                closestNote.getView().animate().cancel();
                closestNote.getView().clearAnimation();
                screen.removeView(closestNote.getView());
                score += 100;
                updateScore();
                //System.out.println("Your score: " + score);
            }
        }
    }

    public void rightClicked(View view) {
        if (Song.getActiveRightNotes().size() > 0) {
            Note closestNote = Song.getActiveRightNotes().get(0);
            double totalTimeTaken = System.currentTimeMillis() - (Song.startTime + closestNote.getTimeDelay());
            double percentDistCovered = totalTimeTaken / (Note.DURATION);
            //System.out.println("Total time taken: " + totalTimeTaken);
            //System.out.println("Note destroyed! % dist = " + percentDistCovered);
            if (percentDistCovered >= 0.75 && closestNote.getView() != null) {
                closestNote.getView().animate().cancel();
                closestNote.getView().clearAnimation();
                screen.removeView(closestNote.getView());
                score += 100;
                updateScore();
                //System.out.println("Your score: " + score);
            }
        }
    }

    public void updateScore() {
        //CHANGE THE '8' TO HOW MANY TOTAL # SHOULD BE DISPLAYED
        scoreView.setText(String.format(Locale.ENGLISH, "%08d", score));
    }
}
