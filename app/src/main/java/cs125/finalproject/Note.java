package cs125.finalproject;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class Note implements Runnable{
    private boolean isLeft;
    private long timeDelay;
    private MusicScreen screen;
    private ConstraintLayout layout;
    public final static int DURATION = 1000;
    private boolean wasClicked = false;
    private ImageView noteView;
    private AnimationSet set;

    Note(boolean isLeft, long timeDelay, MusicScreen screen) {
        this.isLeft = isLeft;
        this.timeDelay = timeDelay;
        this.screen = screen;
        noteView = new ImageView(screen);
        layout = screen.findViewById(R.id.music_screen);
    }

    public long getTimeDelay() {
        return timeDelay;
    }

    public View getView() {
        return noteView;
    }
    public boolean isLeft() { return isLeft; }

    public void run() {
        final ImageView note = noteView;
        if (isLeft) {
            layout.post(new Runnable() {
                @Override
                public void run() {
                    note.setImageResource(R.drawable.blue_note);
                    note.setVisibility(View.VISIBLE);
                    /*note.setX((SongManager.screenWidth / 2) - (note.getDrawable().getIntrinsicWidth()));
                    note.setY((SongManager.screenHeight / 2) - (note.getDrawable().getIntrinsicHeight() / 2));
                    final Animation move = new TranslateAnimation(0, -SongManager.totalDistance, 0, 0);*/
                    final Animation move = new TranslateAnimation(
                            (SongManager.screenWidth / 2) - (note.getDrawable().getIntrinsicWidth() / 2),
                            -50f,
                            (SongManager.screenHeight / 2) - (note.getDrawable().getIntrinsicHeight() / 2),
                            (SongManager.screenHeight / 2) - (note.getDrawable().getIntrinsicHeight() / 2));
                    move.setDuration(DURATION);
                    move.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (!wasClicked) {
                                stop();
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    note.setLayoutParams(lp);
                    layout.addView(note);
                    addLeftNote();
                    note.startAnimation(move);
                }
            });
        } else {
            layout.post(new Runnable() {
                @Override
                public void run() {
                    note.setImageResource(R.drawable.red_note);
                    note.setVisibility(View.VISIBLE);
                    /*note.setX(SongManager.screenWidth / 2);
                    note.setY((SongManager.screenHeight / 2) - (note.getDrawable().getIntrinsicHeight() / 2));
                    final Animation move = new TranslateAnimation(0, SongManager.totalDistance, 0, 0);*/
                    final Animation move = new TranslateAnimation(
                            (SongManager.screenWidth / 2) - (note.getDrawable().getIntrinsicWidth() / 2),
                            SongManager.screenWidth + 50f,
                            (SongManager.screenHeight / 2) - (note.getDrawable().getIntrinsicHeight() / 2),
                            (SongManager.screenHeight / 2) - (note.getDrawable().getIntrinsicHeight() / 2));
                    move.setDuration(DURATION);
                    move.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (!wasClicked) {
                                stop();
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    note.setLayoutParams(lp);
                    layout.addView(note);
                    addRightNote();
                    note.startAnimation(move);
                }
            });
        }
    }

    public void noteClicked(final float x, final boolean isLeft) {
        wasClicked = true;
        noteView.setX(x);
        noteView.setY((SongManager.screenHeight / 2) - (noteView.getDrawable().getIntrinsicHeight() / 2));
        noteView.animate().cancel();
        noteView.getAnimation().cancel();

        Animation clicked;
        if (isLeft) {
            clicked = new ScaleAnimation(1, 1.5f, 1, 1.5f);
        }  else {
            clicked = new ScaleAnimation(1, 1.5f, 1, 1.5f, Animation.ABSOLUTE, noteView.getX()+ (noteView.getDrawable().getIntrinsicWidth() * 2), Animation.RELATIVE_TO_SELF, noteView.getTop());
        }
        clicked.setDuration(500);
        Animation fadeout = new AlphaAnimation(1, 0);
        fadeout.setDuration(500);
        set = new AnimationSet(false);
        set.addAnimation(clicked);
        set.addAnimation(fadeout);
        noteView.startAnimation(set);
        stop();
    }
    private void addLeftNote() {
        Song.addLeftNote(this);
    }

    private void addRightNote() {
        Song.addRightNote(this);
    }

    private void stop() {
        layout.removeView(noteView);
        if (isLeft) {
            Song.removeLeftNote(this);
        } else {
            Song.removeRightNote(this);
        }
    }
}
