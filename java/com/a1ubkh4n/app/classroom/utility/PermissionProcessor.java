package com.a1ubkh4n.app.classroom.utility;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.a1ubkh4n.app.classroom.R;
import com.a1ubkh4n.app.classroom.interfaces.PermissionGrantListener;

/**
 * Created by a1ubkh4n on 3/22/2016.
 */
public class PermissionProcessor {
    private Activity activity;
    private View view;
    private PermissionGrantListener permissionGrantListener;
    public static final int REQUEST_EXTERNAL_STORAGE = 101;

    public PermissionProcessor(Activity activity, View view) {
        this.activity = activity;
        this.view = view;
    }

    public void setPermissionGrantListener(PermissionGrantListener permissionGrantListener) {
        this.permissionGrantListener = permissionGrantListener;
    }

    /**
     * Ask for read-write external storage permission
     */
    public void askForPermissionExternalStorage() {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) { //permission yet to be granted

            getPermissionExternalStorage();
        } else { //permission already granted
            if (permissionGrantListener != null) {
                permissionGrantListener.OnGranted();
            }
        }
    }

    /**
     * Request and get the permission for external storage
     */
    public void getPermissionExternalStorage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            Snackbar.make(view, R.string.grantPermission,
                    Snackbar.LENGTH_LONG)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(activity,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    REQUEST_EXTERNAL_STORAGE);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

}