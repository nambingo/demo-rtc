package com.giahan.app.vietskindoctor.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.giahan.app.vietskindoctor.R;

/**
 * Created by pham.duc.nam on 08/11/2018.
 */
public class PhotoGalleryUtil {

    public final static int REQUEST_GALLERY = 90;

    private static ImagePicker imagePicker;

    public static void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions,
            @NonNull final int[] grantResults, Activity activity, boolean isMultipleChoice) {
        Log.e("PhotoGalleryUtil", "onRequestPermissionsResult:  -----> "+requestCode);
        if (requestCode == REQUEST_GALLERY) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                implementData(activity, isMultipleChoice);
            }
        }
    }

    public static void openGalleryPhoto(Activity activity, boolean isMultipleChoice) {
        if (!isDeviceSupportCamera(activity)) {
            DialogUtils
                    .showDialogOneChoice(activity, true, false,
                            activity.getString(R.string.device_not_support_camera),
                            activity.getString(R.string.close), view -> DialogUtils.hideAlert());
        } else {
            checkPermission(activity, isMultipleChoice);
        }
    }

    private static void checkPermission(Activity activity, boolean isMultipleChoice) {
        if (checkStorePermission(activity, REQUEST_GALLERY)) {
            implementData(activity, isMultipleChoice);
        } else {
            showRequestPermission(activity, REQUEST_GALLERY);
        }
    }

    private static void implementData(Activity activity, boolean isMultipleChoice) {
        setUpImagePicker(activity, isMultipleChoice);
    }

    private static boolean isDeviceSupportCamera(Activity activity) {
        return activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private static void setUpImagePicker(Activity activity, boolean isMultipleChoice) {
        imagePicker = ImagePicker.create(activity)
                .theme(R.style.ImagePickerTheme)
                .returnMode(
                        ReturnMode.NONE) // set whether pick action or camera action should return immediate result or not. Only works in single mode for image picker
                .folderMode(false) // set folder mode (false by default)
                .includeVideo(false) // include video (false by default)
                .toolbarArrowColor(Color.RED) // set toolbar arrow up color
                .toolbarFolderTitle("Folder") // folder selection title
                .toolbarImageTitle("Chọn ảnh") // image selection title
                .toolbarDoneButtonText("Xong"); // done button text
        if (isMultipleChoice) {
            imagePicker.multi();
        } else {
            imagePicker.single();
        }
        imagePicker.limit(90) // max images can be selected (99 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera")   // captured image directory name ("Camera" folder by default)
                .imageFullDirectory(Environment.getExternalStorageDirectory().getPath()) // can be full path
                .start(); // start image picker activity with request code
    }

    private static boolean checkStorePermission(Activity activity, int permission) {
        if (permission == REQUEST_GALLERY) {
            String[] permissions = new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
            };
            return PermissionsUtil.checkPermissions(activity, permissions);
        } else {
            return true;
        }
    }

    private static void showRequestPermission(Activity activity, int requestCode) {
        String[] permissions;

        permissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };

        PermissionsUtil.requestPermissions(activity, requestCode, permissions);
    }
}
