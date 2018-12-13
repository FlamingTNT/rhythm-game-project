package cs125.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class HighScoreScreen extends Activity {

    private static final String TAG_1 = "New High Score!";
    private static final String TAG_2 = "2nd Highest Score!";
    private static final String TAG_3 = "3rd Highest Score!";
    private static final String TAG_4 = "4th Highest Score!";
    private static final String TAG_5 = "5th Highest Score!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score_screen);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        int score = getIntent().getIntExtra("score", 0);
        SharedPreferences prefs = this.getSharedPreferences("scores", Context.MODE_PRIVATE);
        int first = prefs.getInt("first", 0);
        int second = prefs.getInt("second", 0);
        int third = prefs.getInt("third", 0);
        int fourth = prefs.getInt("fourth", 0);
        int fifth = prefs.getInt("fifth", 0);

        TextView highScoreTag = findViewById(R.id.high_score_tag);
        boolean updateScores = false;

        if (score > fifth) {
            updateScores = true;
            if (score > fourth) {
                if (score > third) {
                    if (score > second) {
                        if (score > first) {
                            fifth = fourth;
                            fourth = third;
                            third = second;
                            second = first;
                            first = score;
                            highScoreTag.setText(TAG_1);
                        } else {
                            fifth = fourth;
                            fourth = third;
                            third = second;
                            second = score;
                            highScoreTag.setText(TAG_2);
                        }
                    } else {
                        fifth = fourth;
                        fourth = third;
                        third = score;
                        highScoreTag.setText(TAG_3);
                    }
                } else {
                    fifth = fourth;
                    fourth = score;
                    highScoreTag.setText(TAG_4);
                }
            } else {
                fifth = score;
                highScoreTag.setText(TAG_5);
            }
            highScoreTag.setVisibility(View.VISIBLE);
        }
        TextView yourScore = findViewById(R.id.yourScore);
        TextView score1 = findViewById(R.id.score1);
        TextView score2 = findViewById(R.id.score2);
        TextView score3 = findViewById(R.id.score3);
        TextView score4 = findViewById(R.id.score4);
        TextView score5 = findViewById(R.id.score5);

        String string0 = String.format(Locale.ENGLISH, "%08d", score);
        String string1 = "1st - " + String.format(Locale.ENGLISH, "%08d", first);
        String string2 = "2nd - " + String.format(Locale.ENGLISH, "%08d", second);
        String string3 = "3rd - " + String.format(Locale.ENGLISH, "%08d", third);
        String string4 = "4th - " + String.format(Locale.ENGLISH, "%08d", fourth);
        String string5 = "5th - " + String.format(Locale.ENGLISH, "%08d", fifth);
        yourScore.setText(string0);
        score1.setText(string1);
        score2.setText(string2);
        score3.setText(string3);
        score4.setText(string4);
        score5.setText(string5);

        if (updateScores) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("first", first);
            editor.putInt("second", second);
            editor.putInt("third", third);
            editor.putInt("fourth", fourth);
            editor.putInt("fifth", fifth);
            editor.apply();
        }
    }

    public void finishSong(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAfterTransition();
    }

}
