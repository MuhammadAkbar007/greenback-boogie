package com.example.gyrodraw.utils;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.gyrodraw.R;
import com.example.gyrodraw.auth.Account;
import com.example.gyrodraw.localDatabase.LocalDbForImages;
import com.example.gyrodraw.localDatabase.LocalDbHandlerForImages;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * This class is responsible for saving images to the device storage.
 */
public final class ImageStorageManager {

    private ImageStorageManager() {
        // Empty constructor
    }

    /**
     * Retrieves the latest image from the local database and saves it in local external storage.
     *
     * @param context activity context
     */
    public static void saveImageFromDb(Context context) {
        LocalDbForImages localDbHandler = new LocalDbHandlerForImages(context, null, 1);
        Account account = Account.getInstance(context);
        String imageName = "DRAWING_" + account.getTotalMatches()
                + "_" + account.getUsername();
        ImageStorageManager.writeImage(localDbHandler.getLatestBitmap(), imageName, context);
    }

    /**
     * Saves the given image in local external storage.
     *
     * @param context activity context
     */
    public static void saveImage(Context context, Bitmap bitmap) {
        Account account = Account.getInstance(context);
        String imageName = "DRAWING_" + account.getUsername() + "_"
                + Calendar.getInstance().getTime();
        ImageStorageManager.writeImage(bitmap, imageName, context);
    }

    /**
     * Saves an image to the device file system.
     *
     * @param image the image to save.
     * @param imageName the filename of the image.
     * @param context the activity.
     */
    private static void writeImage(Bitmap image, String imageName, final Context context) {
        File file = getFile(imageName);

        if (file.exists()) {
            file.delete();
        }

        writeFileToStorage(image, file);

        MediaScannerConnection.scanFile(context, new String[]{file.getPath()},
                new String[]{"image/png"}, null);

        if (context != null) {
            successfullyDownloadedImageToast(context);
        }
    }

    /**
     * Writes an image to the storage.
     *
     * @param image the image to store.
     * @param file the file path.
     */
    static void writeFileToStorage(Bitmap image, File file) {
        Log.d("ImageStorageManager", "Saving image: " + file.getPath());

        // Save image in file directory
        try (FileOutputStream out = new FileOutputStream(file)) {
            image.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a file object from a string describing the directory.
     *
     * @param imageName the name of the image.
     * @return a file object to the directory.
     */
    static File getFile(String imageName) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Gyrodraw");
        myDir.mkdirs();
        String fileName = "Image-" + imageName + ".png";
        return new File(myDir, fileName);
    }

    /**
     * Creates a toast to show that image was successfully downloaded.
     *
     * @param context to show the toast on
     */
    public static void successfullyDownloadedImageToast(final Context context) {
        Toast toast = Toast.makeText(context,
                context.getString(R.string.successfulImageDownload), Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Asks permissions for writing in external files.
     *
     * @param context context of the application
     */
    public static void askForStoragePermission(Context context) {
        requestPermissions((Activity) context, new String[]{
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

    }

    /**
     * Checks if storage permissions are granted. If permissions are revoked it requests
     * permission.
     *
     * @param context activity context
     * @return a boolean indicating if permissions are granted or not.
     */
    public static boolean hasExternalWritePermissions(Context context) {
        return checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }
}
