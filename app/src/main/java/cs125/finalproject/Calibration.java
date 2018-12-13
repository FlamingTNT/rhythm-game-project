package cs125.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class Calibration extends Activity {
    private ConstraintLayout layout;
    private MediaPlayer musicPlayer;
    private int screenWidth;
    private int screenHeight;
    private long startTime;
    private boolean running = true;
    private boolean musicPlaying = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calibration);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ThreadHandler.getExecutor().purge();

        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        layout = findViewById(R.id.calibration);
        TextView delay = findViewById(R.id.delay_counter);
        delay.setText(((Integer) SongManager.delay).toString());

    }

    @Override
    public void onStart() {
        super.onStart();
        musicPlayer = MediaPlayer.create(this, R.raw.calibration_song);
        musicPlayer.setLooping(false);
        beginCalibration();
    }

    @Override
    public void onBackPressed() {
        running = false;
        musicPlayer.stop();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAfterTransition();
    }

    public void beginCalibration() {
        ThreadHandler.addRunnable(new Runnable() {
            @Override
            public void run() {
                startTime = System.currentTimeMillis();
                while (running) {
                    if (!musicPlayer.isPlaying() && musicPlaying) {
                        musicPlayer.start();
                    }
                    if (startTime + Note.DURATION + SongManager.delay <= System.currentTimeMillis()) {
                        createNote();
                        startTime = System.currentTimeMillis() - SongManager.delay;
                    }
                }
            }
        });
        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                musicPlayer.start();
                musicPlaying = true;
            }
        }, 850);
    }

    public void createNote() {
        final ImageView note = new ImageView(this);
        layout.post(new Runnable() {
            @Override
            public void run() {
                note.setImageResource(R.drawable.blue_note);
                note.setVisibility(View.VISIBLE);
                final Animation move = new PausableTranslateAnimation(
                        (screenWidth / 2) - (note.getDrawable().getIntrinsicWidth() / 2),
                        -50f,
                        (screenHeight / 2) - (note.getDrawable().getIntrinsicHeight() / 2),
                        (screenHeight / 2) - (note.getDrawable().getIntrinsicHeight() / 2));
                move.setDuration(Note.DURATION);
                move.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        layout.removeView(note);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT);
                note.setLayoutParams(lp);
                layout.addView(note);
                note.startAnimation(move);
            }
        });
    }

    public void increaseDelay(View view) {
        SongManager.delay += 10;
        TextView delay = findViewById(R.id.delay_counter);
        delay.setText(((Integer) SongManager.delay).toString());
    }

    public void decreaseDelay(View view) {
        SongManager.delay -= 10;
        TextView delay = findViewById(R.id.delay_counter);
        delay.setText(((Integer) SongManager.delay).toString());
    }
}
