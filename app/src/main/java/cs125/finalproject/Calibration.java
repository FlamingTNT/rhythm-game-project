package cs125.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Calibration extends Activity {
    private ArrayList<Note> noteList = new ArrayList<>();
    private Song currentSong;
    private MediaPlayer musicPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calibration);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        TextView delay = findViewById(R.id.delay_counter);
        delay.setText(((Integer) SongManager.delay).toString());
        /*noteList.add(new Note(true, 1000 + SongManager.delay, this));
        noteList.add(new Note(true, 2000 + SongManager.delay, this));
        noteList.add(new Note(true, 3000 + SongManager.delay, this));
        noteList.add(new Note(true, 4000 + SongManager.delay, this));
        noteList.add(new Note(true, 5000 + SongManager.delay, this));
        noteList.add(new Note(true, 6000 + SongManager.delay, this));
        noteList.add(new Note(true, 7000 + SongManager.delay, this));
        noteList.add(new Note(true, 8000 + SongManager.delay, this));
        currentSong = new Song(noteList);*/
        musicPlayer = MediaPlayer.create(this, R.raw.calibration_song);
        musicPlayer.setLooping(true);
        //playCalibrationNotes();
        /*new Timer().schedule(new TimerTask() {
            @Override
            public void run() {*/
                musicPlayer.start();
        //    }
        //}, 1000);
    }

    @Override
    public void onBackPressed() {
        musicPlayer.stop();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAfterTransition();
    }

    public void playCalibrationNotes() {
        currentSong.play();
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

    /*public void leftClicked(View view) {
        if (Song.getActiveLeftNotes().size() > 0) {
            Note closestNote = Song.getActiveLeftNotes().get(0);
            double totalTimeTaken = System.currentTimeMillis() - (Song.startTime + closestNote.getTimeDelay());
            double percentDistCovered = totalTimeTaken / (Note.DURATION);
            if (percentDistCovered >= 0.75 && closestNote.getView() != null) {
                float x = (SongManager.screenWidth / 2) - ((SongManager.screenWidth / 2) * (float)percentDistCovered);
                closestNote.noteClicked(x, true);
            }
        }
    }*/
}
