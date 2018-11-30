package cs125.finalproject;

import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class Note {
    private boolean isLeft;
    private long timeDelay;
    private MusicScreen screen;
    private ConstraintLayout layout;
    private int screenWidth;
    private int screenHeight;
    Note(boolean isLeft, long timeDelay, MusicScreen screen) {
        this.isLeft = isLeft;
        this.timeDelay = timeDelay;
        this.screen = screen;
        layout = screen.findViewById(R.id.music_screen);

        WindowManager wm = screen.getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }

    public long getTimeDelay() {
        return timeDelay;
    }

    public void start() {
        System.out.println("START CALL RECEIVED BY NOTE");
        final ImageView note = new ImageView(screen);
        final ConstraintLayout cl = screen.findViewById(R.id.music_screen);
        if (isLeft) {
            cl.post(new Runnable() {
                @Override
                public void run() {
                    note.setImageResource(R.drawable.blue_note);
                    note.setVisibility(View.VISIBLE);
                    note.setX((screenWidth / 2) - (note.getDrawable().getIntrinsicWidth()));
                    note.setY((screenHeight / 2) - (note.getDrawable().getIntrinsicHeight() / 2));
                    final Animation move = new TranslateAnimation(0, -((screenWidth / 2) + 50f), 0, 0);
                    move.setDuration(1000);
                    move.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            System.out.println("Note destroyed");
                            layout.removeView(note);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    note.setLayoutParams(lp);
                    layout.addView(note);
                    System.out.println("Note should be visible");
                    note.startAnimation(move);
                }
            });
        } else {
            cl.post(new Runnable() {
                @Override
                public void run() {
                    note.setImageResource(R.drawable.red_note);
                    note.setVisibility(View.VISIBLE);
                    note.setX(screenWidth / 2);
                    note.setY((screenHeight / 2) - (note.getDrawable().getIntrinsicHeight() / 2));
                    final Animation move = new TranslateAnimation(0, (screenWidth / 2) + 50f, 0, 0);
                    move.setDuration(1000);
                    move.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            System.out.println("Note destroyed");
                            layout.removeView(note);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    note.setLayoutParams(lp);
                    layout.addView(note);
                    System.out.println("Note should be visible");
                    note.startAnimation(move);
                }
            });
        }
    }

}
