package cs125.finalproject;

import android.media.MediaPlayer;

import java.util.ArrayList;

public class SongManager {
    private ArrayList<Song> songList = new ArrayList<>();
    private MusicScreen screen;

    SongManager(MusicScreen screen) {
        this.screen = screen;
        initialize();
    }

    private void initialize() {
        Note[] notes0 = new Note[10];
        notes0[0] = new Note(true, 2000, screen);
        notes0[1] = new Note(false, 2800, screen);
        notes0[2] = new Note(false, 3500, screen);
        notes0[3] = new Note(true, 3900, screen);
        notes0[4] = new Note(false, 4200, screen);
        notes0[5] = new Note(false, 4400, screen);
        notes0[6] = new Note(true, 4400, screen);
        notes0[7] = new Note(false, 4700, screen);
        notes0[8] = new Note(true, 4700, screen);
        notes0[9] = new Note(true, 5300, screen);
        Song song0 = new Song(notes0);
        songList.add(song0);
        System.out.println("Song 0 Added");
    }

    public void playSong(int index) {
        MediaPlayer mp = MediaPlayer.create(screen, R.raw.give_me_candy);
        mp.start();
        songList.get(index).play();
    }
}
