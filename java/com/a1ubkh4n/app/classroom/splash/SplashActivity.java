package com.a1ubkh4n.app.classroom.splash;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.a1ubkh4n.app.classroom.MainActivity;
import com.a1ubkh4n.app.classroom.R;
import com.a1ubkh4n.app.classroom.database.DatabaseManager;

/**
 * Created by a1ubkh4n on 5/7/2016.
 */
public class SplashActivity extends Activity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        context = this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startRevealAnimation();
            }
        }, 200);

        new GetNumberOfClassrooms().execute();
    }

    /**
     * Start splash animation with reveal effect
     */
    private void startRevealAnimation() {
        // previously invisible view
        View myView = findViewById(R.id.backgroundLayout);

        if (Build.VERSION.SDK_INT >= 21) {
            // get the center for the clipping circle
            int cx = (myView.getLeft() + myView.getRight()) / 2;
            int cy = (myView.getTop() + myView.getBottom()) / 2;

            // get the final radius for the clipping circle
            int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

            Animator anim = null;
            try {
                // create the animator for this view (the start radius is zero)
                anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
            } catch (IllegalStateException ignored) {}

            // make the view visible and start the animation
            myView.setVisibility(View.VISIBLE);

            if (anim != null) anim.start();
        } else {
            myView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Get number of classrooms from DB
     */
    private class GetNumberOfClassrooms extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            DatabaseManager databaseManager = new DatabaseManager(context);
            int numberOfClassrooms = databaseManager.countClassrooms();

            return numberOfClassrooms;
        }

        @Override
        protected void onPostExecute(Integer numberOfClassrooms) {
            startMainActivity(numberOfClassrooms);
        }
    }

    /**
     * finish splash screen, start main activity
     * @param numberOfClassrooms
     */
    private void startMainActivity(final int numberOfClassrooms) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("numberOfClassrooms", numberOfClassrooms);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }, 1000);
    }

}