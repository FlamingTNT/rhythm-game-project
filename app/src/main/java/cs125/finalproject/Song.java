package cs125.finalproject;

import android.media.MediaPlayer;

import java.util.ArrayList;

public class Song {
    private static ArrayList<Note> song = new ArrayList<>();
    private static MediaPlayer musicPlayer;
    public static long startTime;
    private static ArrayList<Note> activeLeftNotes = new ArrayList<>();
    private static ArrayList<Note> activeRightNotes = new ArrayList<>();
    private static long timeWhenPaused = 0;
    private static boolean isPaused = false;

    Song(ArrayList<Note> newSong, MediaPlayer newMusicPlayer) {
        song = newSong;
        musicPlayer = newMusicPlayer;
    }

    public boolean play() {
        startTime = System.currentTimeMillis();
        int noteCount = 0;
        musicPlayer.start();
        while (noteCount < song.size()) {
            if (song.get(noteCount).getTimeDelay() + startTime <= System.currentTimeMillis() && !isPaused) {
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
        while (musicPlayer.isPlaying()) {
            //:D
        }
        return true;
    }

    public void pause() {
        timeWhenPaused = System.currentTimeMillis();
        isPaused = true;
        ThreadHandler.addRunnable(new Runnable() {
            @Override
            public void run() {
                musicPlayer.pause();
            }
        });
        ThreadHandler.addRunnable(new Runnable() {
            @Override
            public void run() {
                for (Note note : activeLeftNotes) {
                    note.pause();
                }
                for (Note note : activeRightNotes) {
                    note.pause();
                }
            }
        });
    }

    public void resume() {
        startTime += System.currentTimeMillis() - timeWhenPaused;
        ThreadHandler.addRunnable(new Runnable() {
            @Override
            public void run() {
                isPaused = false;
                for (Note note : activeLeftNotes) {
                    note.resume();
                }
                for (Note note : activeRightNotes) {
                    note.resume();
                }
            }
        });
        ThreadHandler.addRunnable(new Runnable() {
            @Override
            public void run() {
                musicPlayer.start();
            }
        });
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
