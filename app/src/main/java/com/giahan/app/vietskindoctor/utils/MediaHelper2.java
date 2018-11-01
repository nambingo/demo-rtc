package com.giahan.app.vietskindoctor.utils;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;
import com.giahan.app.vietskindoctor.R;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MediaHelper2 {

    public final static int REQUEST_PHOTO_CAPTURED = 90;

    public final static int REQUEST_VIDEO_CAPTURED = 91;

    public final static int REQUEST_MEDIA_LIBRARY = 92;

    public final static int STORAGE_PERMISSION_ID = 0;

    public final static int CAMERA_PERMISSION_ID = 1;

    private static String imageFilePath;

    private static final String STR_AUTHORITY = ".fileprovider";

    private static boolean isDeviceSupportCamera(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public static void takeMedia(Activity context, int type) {
        if (!isDeviceSupportCamera(context)) {
            Toast.makeText(context, context.getString(R.string.file_not_found),
                    Toast.LENGTH_SHORT).show();
        }
        if (checkStorePermission(context, CAMERA_PERMISSION_ID)) {
            if (type == REQUEST_PHOTO_CAPTURED) {
                takePhoto(context);
            } else {
                recordVideo(context);
            }
        } else {
            showRequestPermission(context, CAMERA_PERMISSION_ID);
        }
    }

    public static void openGallery(Context context) {
        if (checkStorePermission(context, STORAGE_PERMISSION_ID)) {
            Intent intent;
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
//            intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"*/*"});
            ((Activity) context).startActivityForResult(intent, REQUEST_MEDIA_LIBRARY);
        } else {
            showRequestPermission((Activity) context, STORAGE_PERMISSION_ID);
        }
    }

    public static String resultCapture(Context context, int requestCode, int resultCode,
            Intent data) {
        if (resultCode != RESULT_OK) {
            return null;
        }
        String mPath = null;
        if (requestCode == REQUEST_PHOTO_CAPTURED) {
            return imageFilePath;
        } else {
            if (data == null || data.getData() == null) {
                return null;
            }
            String path = ImageFilePath.getPath(context, data.getData());
            if (!TextUtils.isEmpty(path)) {
                mPath = path;
            }
        }
        return mPath;
    }

    public static String resultFilePathCaptured() {
        return imageFilePath;
    }

    private static void takePhoto(Context context) {
        File file = createImageFile();
        if (file == null) {
            return;
        }
        Uri photoURI;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            photoURI = FileProvider.getUriForFile(context, context.getPackageName() + STR_AUTHORITY, file);
        } else {
            photoURI = Uri.fromFile(file);
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) == null) {
            return;
        }
        ((Activity) context).startActivityForResult(takePictureIntent, REQUEST_PHOTO_CAPTURED);
    }

    private static void recordVideo(Context context) {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
        ((Activity) context).startActivityForResult(intent, REQUEST_VIDEO_CAPTURED);
    }

    private static File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + ".jpg";
        File imageFile = new File(Environment.getExternalStorageDirectory(), "VietSkin");
        if (!imageFile.exists()) {
            if (!imageFile.mkdir()) {
                return null;
            }
        }
        File image = new File(imageFile.getPath(), imageFileName);
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private static boolean checkStorePermission(Context context, int permission) {
        if (permission == STORAGE_PERMISSION_ID) {
            return PermissionsUtil.checkPermissions(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else if (permission == CAMERA_PERMISSION_ID) {
            String[] permissions = new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            return PermissionsUtil.checkPermissions(context, permissions);
        } else {
            return true;
        }
    }

    private static void showRequestPermission(Activity activity, int requestCode) {
        String[] permissions;
        if (requestCode == STORAGE_PERMISSION_ID) {
            permissions = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
        } else {
            permissions = new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
        }
        PermissionsUtil.requestPermissions(activity, requestCode, permissions);
    }
}
