package cs125.finalproject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONObject;

import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;



public class PlaylistScreen extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    final String candyID = "7zkppq9Gp70";
    final String carolID = "JZkvL0snS7w";
    final String roadID = "TsKWeCcjaBg";
    public static final String YOUTUBE_API_KEY = "AIzaSyAqyp431Piz_gqCtQ2ZEW5o3CKdP_aSSYg";
    private static final String TAG = "BeatLeBeat";
    private static RequestQueue requestQueue;
    private YouTubePlayer vidPlayer;
    private YouTubePlayerView youTubeView;
    private static final int RECOVERY_REQUEST = 1;
    private int selector = 0;
    void startAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    "https://www.youtube.com/watch?v=7zkppq9Gp70",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d(TAG, response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist_screen);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(YOUTUBE_API_KEY, this);
    }

    public void listenToCandy(View view) {
        playVideo(candyID);
    }
    public void listenToCarol(View view) {
        playVideo(carolID);
    }
    public void listenToRoad(View view) {
        playVideo(roadID);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {
        if(!b) {
            if (vidPlayer == null) {
                vidPlayer = player;
            }
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        }
    }

    private void playVideo(String videoId){
        if(vidPlayer != null){
            vidPlayer.loadVideo(videoId);
        }
    }






}
