package cs125.finalproject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Song {
    private static ArrayList<Note> song = new ArrayList<>();
    public static long startTime;
    private static ArrayList<Note> activeLeftNotes = new ArrayList<>();
    private static ArrayList<Note> activeRightNotes = new ArrayList<>();

    Song(ArrayList<Note> newSong) {
        song = newSong;
    }

    public boolean play() {
        startTime = System.currentTimeMillis();
        int noteCount = 0;
        while (noteCount < song.size()) {
            if (song.get(noteCount).getTimeDelay() + startTime <= System.currentTimeMillis()) {
                if (noteCount < song.size() - 1 && song.get(noteCount).getTimeDelay() == song.get(noteCount + 1).getTimeDelay()) {
                    ThreadHandler.addRunnable(song.get(noteCount));
                    ThreadHandler.addRunnable(song.get(noteCount + 1));
                    noteCount += 2;
                    continue;
                }
                ThreadHandler.addRunnable(song.get(noteCount));
                noteCount++;
            }
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 1000);
        return true;
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
