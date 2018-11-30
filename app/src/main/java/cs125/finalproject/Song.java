package cs125.finalproject;

public class Song {
    private Note[] song;
    private long startTime;
    Song(Note[] song) {
        this.song = song;
    }

    public void play() {
        System.out.println("Song play() called");
        startTime = System.currentTimeMillis();
        int noteCount = 0;
        while (noteCount < song.length) {
            if (song[noteCount].getTimeDelay() + startTime <= System.currentTimeMillis()) {
                System.out.println("NOTE " + noteCount + " CALLED");
                song[noteCount].start();
                noteCount++;
            }
        }
        System.out.println("SONG HAS ENDED");
    }
}
