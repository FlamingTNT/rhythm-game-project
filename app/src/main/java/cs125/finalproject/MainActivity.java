package cs125.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    public static final String SONG_TITLE = "song_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void startCandy(View view) {
        Intent intent = new Intent(this, MusicScreen.class);
        intent.putExtra(SONG_TITLE, "Give Me Candy");
        //stopColor = true;
        startActivity(intent);
    }
    public void startCarol(View view) {
        Intent intent = new Intent(this, MusicScreen.class);
        intent.putExtra(SONG_TITLE, "Carol of the Bells");
        startActivity(intent);
    }
    public void playlists(View view) {
        Intent intent = new Intent(this, PlaylistScreen.class);
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, Calibration.class);
        startActivity(intent);
    }
}
