package cs125.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    public static final String SONG_TITLE = "song_title";
    //private int red, blue, green = 0;
    //private boolean stopColor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //colorSwirl();
    }

    /*public void colorSwirl() {
        ConstraintLayout bgElement = findViewById(R.id.title_background);
        while(!stopColor) {
            long millis = System.currentTimeMillis();
                bgElement.setBackgroundColor(Color.argb(255, red, green, blue));
                red += 5;
                green += 3;
                blue += 1;
                if (red > 255) {
                    red -= 255;
                }
                if (green > 255) {
                    green -= 255;
                }
                if (blue > 255) {
                    blue -= 255;
                }
            try {
                Thread.sleep(1000 - millis % 1000);
            } catch (InterruptedException e) {
                    break;
            }
        }
    }*/

    public void startCandy(View view) {
        Intent intent = new Intent(this, MusicScreen.class);
        intent.putExtra(SONG_TITLE, "Give Me Candy");
        //stopColor = true;
        startActivity(intent);
    }
}
