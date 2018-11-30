package cs125.finalproject;

import android.media.MediaPlayer;

import java.util.ArrayList;

public class SongManager {
    private ArrayList<Song> songList = new ArrayList<>();
    private MusicScreen screen;
    public static final int delay = -687;

    SongManager(MusicScreen screen) {
        this.screen = screen;
        initialize();
    }

    private void initialize() {
        Note[] notes0 = new Note[41];
        notes0[0] = new Note(true, 1931  + delay, screen);
        notes0[1] = new Note(true, 2379  + delay, screen);
        notes0[2] = new Note(false, 2569  + delay, screen);
        notes0[3] = new Note(true, 2773  + delay, screen);
        notes0[4] = new Note(true, 3444  + delay, screen);
        notes0[5] = new Note(false, 3685  + delay, screen);
        notes0[6] = new Note(true, 3910  + delay, screen);
        notes0[7] = new Note(true, 4142  + delay, screen);
        notes0[8] = new Note(false, 4344  + delay, screen);
        notes0[9] = new Note(true, 4566  + delay, screen);
        notes0[10] = new Note(false, 4714  + delay, screen);
        notes0[11] = new Note(false,5027   + delay, screen);
        notes0[12] = new Note(true, 5233  + delay, screen);
        notes0[13] = new Note(true, 5463  + delay, screen);
        notes0[14] = new Note(true, 5911  + delay, screen);
        notes0[15] = new Note(false,6101   + delay, screen);
        notes0[16] = new Note(true, 6305  + delay, screen);
        notes0[17] = new Note(true,6976   + delay, screen);
        notes0[18] = new Note(false, 7217  + delay, screen);
        notes0[19] = new Note(true, 7442  + delay, screen);
        notes0[20] = new Note(true, 8569  + delay, screen);
        notes0[21] = new Note(false,8786    + delay, screen);
        notes0[22] = new Note(true, 9019   + delay, screen);
        notes0[23] = new Note(true, 9690 + delay, screen);
        notes0[24] = new Note(false, 9894   + delay, screen);
        notes0[25] = new Note(true, 10084   + delay, screen);
        notes0[26] = new Note(true, 10325   + delay, screen);
        notes0[27] = new Note(false,10540    + delay, screen);
        notes0[28] = new Note(true, 10782   + delay, screen);
        notes0[29] = new Note(true, 11182   + delay, screen);
        notes0[30] = new Note(false, 11404   + delay, screen);
        notes0[31] = new Note(true, 11660   + delay, screen);
        notes0[32] = new Note(false, 11870  + delay, screen);
        notes0[33] = new Note(false,12163    + delay, screen);
        notes0[34] = new Note(true, 12331  + delay, screen);
        notes0[35] = new Note(true, 12623   + delay, screen);
        notes0[36] = new Note(true, 12848   + delay, screen);
        notes0[37] = new Note(false, 13289   + delay, screen);
        notes0[38] = new Note(true, 13740   + delay, screen);
        notes0[39] = new Note(true, 13924  + delay, screen);
        notes0[40] = new Note(false,14084   + delay, screen);
        Song song0 = new Song(notes0);
        songList.add(song0);
    }

    public void playSong(int index) {
        MediaPlayer mp = MediaPlayer.create(screen, R.raw.give_me_candy);
        mp.start();
        songList.get(index).play();
    }
}
