package com.a1ubkh4n.app.classroom.utility;

import android.os.Environment;

import java.io.File;

/**
 * Created by a1ubkh4n on 10/12/2016.
 */
public class DirectoryUtility {
    //application's folder path
    private static final String PATH_FOLDER = Environment.getExternalStorageDirectory()
            + "/DIIT_Att3ndance_Diary/";

    /**
     * Checks if external storage is available for read and write
     * @return
     */
    public static boolean isExternalStorageMounted() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * Create directory for the application's use
     */
    public static void createDirectory() {
        // Output stream
        // create a File object for the parent directory
        File directory = new File(PATH_FOLDER);
        // have the object build the directory structure, if needed.
        directory.mkdirs();
    }

    public static String getPathFolder() {
        return PATH_FOLDER;
    }
}
