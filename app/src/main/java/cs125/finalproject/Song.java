package cs125.finalproject;

import android.media.MediaPlayer;

import java.util.ArrayList;

public class Song {
    private ArrayList<Note> song;
    private MediaPlayer musicPlayer;
    public long startTime;
    private static ArrayList<Note> activeLeftNotes = new ArrayList<>();
    private static ArrayList<Note> activeRightNotes = new ArrayList<>();
    private long timeWhenPaused = 0;
    public boolean endSong;

    Song(ArrayList<Note> newSong, MediaPlayer newMusicPlayer) {
        activeLeftNotes = new ArrayList<>();
        activeRightNotes = new ArrayList<>();
        song = newSong;
        musicPlayer = newMusicPlayer;
        endSong = false;
    }

    public boolean play() {
        startTime = System.currentTimeMillis();
        int noteCount = 0;
        MusicScreen.resetScoreCounts();
        musicPlayer.start();
        while (noteCount < song.size() && !endSong) {
            if (song.get(noteCount).getTimeDelay() + startTime <= System.currentTimeMillis() && !MusicScreen.isPaused) {
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
        if (endSong) {
            song = null;
            endSong = false;
            return false;
        } else {
            return true;
        }
    }

    public void pause() {
        timeWhenPaused = System.currentTimeMillis();
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
        toBeYellow();
    }

    public static void removeLeftNote(Note note) {
        activeLeftNotes.remove(note);
        toBeYellow();
    }

    public static void addRightNote(Note note) {
        activeRightNotes.add(note);
        toBeYellow();
    }

    public static void removeRightNote(Note note) {
        activeRightNotes.remove(note);
        toBeYellow();
    }

    public static void toBeYellow() {
        if (activeLeftNotes.size() > 0) {
            if (activeRightNotes.size() > 0) {
                if (activeLeftNotes.get(0).getTimeDelay() == activeRightNotes.get(0).getTimeDelay()) {
                    activeLeftNotes.get(0).getView().setImageResource(R.drawable.next_note);
                    activeRightNotes.get(0).getView().setImageResource(R.drawable.next_note);
                } else if (activeLeftNotes.get(0).getTimeDelay() < activeRightNotes.get(0).getTimeDelay()) {
                    activeLeftNotes.get(0).getView().setImageResource(R.drawable.next_note);
                } else {
                    activeRightNotes.get(0).getView().setImageResource(R.drawable.next_note);
                }
            } else {
                activeLeftNotes.get(0).getView().setImageResource(R.drawable.next_note);
            }
        } else if (activeRightNotes.size() > 0){
            activeRightNotes.get(0).getView().setImageResource(R.drawable.next_note);
        }
    }
}
