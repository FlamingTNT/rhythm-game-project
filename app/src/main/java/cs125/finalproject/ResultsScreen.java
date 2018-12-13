package cs125.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class ResultsScreen extends Activity {
    private int[] scoreCounts;
    private int score;
    private String songName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_screen);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        overridePendingTransition(0, 0);
        ThreadHandler.getExecutor().purge();
    }

    @Override
    protected void onStart() {
        super.onStart();
        scoreCounts = getIntent().getIntArrayExtra("scoreCounts");
        score = getIntent().getIntExtra("score", 0);
        songName = getIntent().getStringExtra("songName");
        ConstraintLayout screen = findViewById(R.id.results_screen);
        ImageView bg = findViewById(R.id.results_bg);

        float height = (float)SongManager.screenWidth * ((float) bg.getDrawable().getIntrinsicHeight() / (float) bg.getDrawable().getIntrinsicWidth());

        final TextView numPerfects = findViewById(R.id.numPerfects);
        numPerfects.setText(((Integer) scoreCounts[0]).toString());
        final TextView numGoods = findViewById(R.id.numGoods);
        numGoods.setText(((Integer) scoreCounts[1]).toString());
        final TextView numMisses = findViewById(R.id.numMisses);
        numMisses.setText(((Integer) scoreCounts[2]).toString());

        ViewGroup.LayoutParams lp = bg.getLayoutParams();
        lp.height = (int)height;
        lp.width = SongManager.screenWidth;
        bg.setLayoutParams(lp);
        bg.requestLayout();
        bg.setY(screen.getBottom() - bg.getHeight());

        TextView scoreView = findViewById(R.id.final_score);
        scoreView.setText(String.format(Locale.ENGLISH, "%08d", score));

        startLoadingAnimations(numPerfects, 500);
        startLoadingAnimations(numGoods, 1500);
        startLoadingAnimations(numMisses, 2500);
    }

    public void startLoadingAnimations(final TextView scoreNum, final long startOffset) {
        ThreadHandler.addRunnable(new Runnable() {
            @Override
            public void run() {
                Animation changeLength = new ScaleAnimation(0.2f, 0.2f, 0f, 1f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                changeLength.setDuration(700);
                changeLength.setStartOffset(100);
                Animation changeWiden = new ScaleAnimation(0.2f, 5f, 1f, 1f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                changeWiden.setDuration(500);
                changeWiden.setStartOffset(1000);

                final AnimationSet loadChanges = new AnimationSet(true);
                loadChanges.addAnimation(changeLength);
                loadChanges.addAnimation(changeWiden);

                scoreNum.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scoreNum.setVisibility(View.VISIBLE);
                        scoreNum.startAnimation(loadChanges);
                    }
                }, startOffset);
            }
        });
    }

    public void finishSong(View view) {
        Intent intent = new Intent(this, HighScoreScreen.class);
        intent.putExtra("score", score);
        intent.putExtra("songName", songName);
        startActivity(intent);
        finishAfterTransition();
    }
}
