package cs125.finalproject;

import java.util.ArrayList;

public class Song {
    private Note[] song;
    public static long startTime;
    private static ArrayList<Note> activeLeftNotes = new ArrayList<>();
    private static ArrayList<Note> activeRightNotes = new ArrayList<>();
    Song(Note[] song) {
        this.song = song;
    }

    public void play() {
        startTime = System.currentTimeMillis();
        int noteCount = 0;
        while (noteCount < song.length) {
            if (song[noteCount].getTimeDelay() + startTime <= System.currentTimeMillis()) {
                song[noteCount].start();
                noteCount++;
            }
        }
    }
    public static ArrayList<Note> getActiveLeftNotes() {
        return activeLeftNotes;
    }

    public static ArrayList<Note> getActiveRightNotes() {
        return activeRightNotes;
    }

    public static void addLeftNote(Note noteView) {
        activeLeftNotes.add(noteView);
    }

    public static void removeLeftNote(Note noteView) {
        activeLeftNotes.remove(noteView);
    }

    public static void addRightNote(Note noteView) {
        activeRightNotes.add(noteView);
    }

    public static void removeRightNote(Note noteView) {
        activeRightNotes.remove(noteView);
    }
}
