package cs125.finalproject;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ResultsScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_screen);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ConstraintLayout screen = findViewById(R.id.results_screen);
        ImageView bg = findViewById(R.id.results_bg);

        float height = (float)SongManager.screenWidth * ((float) bg.getDrawable().getIntrinsicHeight() / (float) bg.getDrawable().getIntrinsicWidth());
        ViewGroup.LayoutParams lp = bg.getLayoutParams();
        lp.height = (int)height;
        lp.width = SongManager.screenWidth;
        bg.setLayoutParams(lp);
        bg.requestLayout();
        bg.setY(screen.getBottom() - bg.getHeight());
    }
}
