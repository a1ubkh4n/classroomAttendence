package com.a1ubkh4n.app.classroom;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.a1ubkh4n.app.classroom.attendance.AttendancesFragment;
import com.a1ubkh4n.app.classroom.edit.EditClassroomFragment;
import com.a1ubkh4n.app.classroom.interfaces.PermissionGrantListener;
import com.a1ubkh4n.app.classroom.statistics.StatisticsFragment;
import com.a1ubkh4n.app.classroom.utility.ApplicationRating;
import com.a1ubkh4n.app.classroom.utility.PermissionProcessor;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by a1ubkh4n
 */
public class MainActivity extends AppCompatActivity {
    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private FirebaseAnalytics mFirebaseAnalytics;

    private TabLayout mSlidingTabLayout;

    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);



        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        int numberOfClassrooms = 0;
        Bundle args = getIntent().getExtras();
        if (args != null) {
            numberOfClassrooms = args.getInt("numberOfClassrooms");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //toolbar.setNavigationIcon(R.drawable.ic_nav_logo);
        toolbar.setLogo(R.drawable.ic_diit_logo_launcher);
        //toolbar.setTitle("Title");
        // toolbar.setSubtitle("Subtitle");


        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        mSlidingTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.white));
        mSlidingTabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.colourPrimaryLight),
                ContextCompat.getColor(this, R.color.white));
        mSlidingTabLayout.setupWithViewPager(viewPager);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        addOnPageChangeListener();
        //if there are already entered classrooms, just show take attendance page,
        //otherwise show edit classrooms page to add a new one.
        if (numberOfClassrooms > 0) {
            viewPager.setCurrentItem(1);
        } else {
            viewPager.setCurrentItem(0);
            //make floating button available to add classrooms
            setButtonAdd();
        }


        //rate the app
        ApplicationRating.ratingPopupManager(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Intent i = new Intent(this, about.class);
            startActivity(i);
            overridePendingTransition(R.anim.move_in_from_bottom, R.anim.stand_still);
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * View Pager, page change listener.
     */
    private void addOnPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: //editing
                        setButtonAdd();
                        break;
                    case 1: //attendance
                        setButtonHidden();
                        break;
                    case 2: //statistics
                        setButtonPublish();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * EditClassroom.<br />
     * Add a new classroom.
     */
    private void setButtonAdd() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                floatingActionButton.setImageResource(R.drawable.ic_action_add);
                floatingActionButton.show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditClassroomFragment fragment = (EditClassroomFragment) getSupportFragmentManager()
                        .findFragmentByTag("android:switcher:" + viewPager.getId() + ":"
                                + mAdapter.getItemId(0));
                fragment.addClassroom();
            }
        });
    }

    /**
     * Attendance.<br />
     * Just hide the button.
     */
    private void setButtonHidden() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                floatingActionButton.hide();
            }
        });
    }

    /**
     * Statistics.<br />
     * Convert attendances into an excel file and share it.
     */
    private void setButtonPublish() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                floatingActionButton.setImageResource(R.drawable.ic_action_document);
                floatingActionButton.show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionForExternal();
            }
        });
    }

    /**
     * Export attendances list as an excel file
     */
    private void exportToExcel() {
        StatisticsFragment fragment = (StatisticsFragment) getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + viewPager.getId() + ":"
                        + mAdapter.getItemId(2));
        fragment.getDataForExcel();
    }

    /**
     * Checks permission to access external storage.
     */
    public void checkPermissionForExternal() {
        PermissionProcessor permissionProcessor = new PermissionProcessor(this, viewPager);
        permissionProcessor.setPermissionGrantListener(new PermissionGrantListener() {
            @Override
            public void OnGranted() {
                exportToExcel();
            }
        });
        permissionProcessor.askForPermissionExternalStorage();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if (requestCode == PermissionProcessor.REQUEST_EXTERNAL_STORAGE) {
            //if request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                exportToExcel();
            }
        }
    }

    public class TabsPagerAdapter extends FragmentPagerAdapter {

        public TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return EditClassroomFragment.newInstance();
                case 1:
                    return AttendancesFragment.newInstance();
                case 2:
                    return StatisticsFragment.newInstance();
                default:
                    return AttendancesFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            // get item count - equal to number of tabs
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String[] titles = getResources().getStringArray(R.array.main_page);
            return titles[position];
        }
    }

}