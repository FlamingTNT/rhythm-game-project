package cs125.finalproject;

import java.util.ArrayList;

public class Song {
    //private Note[] song;
    private static ArrayList<Note> song = new ArrayList<>();
    public static long startTime;
    private static ArrayList<Note> activeLeftNotes = new ArrayList<>();
    private static ArrayList<Note> activeRightNotes = new ArrayList<>();
    Song(ArrayList<Note> newSong) {
        song = newSong;
    }

    public void play() {
        startTime = System.currentTimeMillis();
        int noteCount = 0;
        while (noteCount < song.size()) {
            if (song.get(noteCount).getTimeDelay() + startTime <= System.currentTimeMillis()) {
                song.get(noteCount).start();
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

    public static void addLeftNote(Note note) {
        activeLeftNotes.add(note);
    }

    public static void removeLeftNote(Note note) {
        activeLeftNotes.remove(note);
    }

    public static void addRightNote(Note note) {
        activeRightNotes.add(note);
    }

    public static void removeRightNote(Note note) {
        activeRightNotes.remove(note);
    }
}
