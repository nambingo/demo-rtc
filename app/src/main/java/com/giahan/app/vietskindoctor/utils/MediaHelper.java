package com.giahan.app.vietskindoctor.utils;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class MediaHelper {

    public final static int REQUEST_PHOTO_CAPTURED = 90;

    public final static int REQUEST_VIDEO_CAPTURED = 91;

    public final static int REQUEST_MEDIA_LIBRARY = 92;

    public final static int STORAGE_PERMISSION_ID = 0;

    public final static int CAMERA_PERMISSION_ID = 1;

    private static boolean isDeviceSupportCamera(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public static void takeMedia(Activity context, int type) {
        if (!isDeviceSupportCamera(context)) {
            Toast.makeText(context, "Thiết bị của bạn không hỗ trợ chức năng này!",
                    Toast.LENGTH_SHORT).show();
        }
        if (checkStorePermission(context, CAMERA_PERMISSION_ID)) {
            if (type == REQUEST_PHOTO_CAPTURED) {
                takePhoto(context);
            } else {
                recordVideo(context);
            }
        } else {
            showRequestPermission((Activity) context, CAMERA_PERMISSION_ID);
        }
    }

    public static void openGallery(Context context) {
        if (checkStorePermission(context, STORAGE_PERMISSION_ID)) {
            Intent intent;
            if (Build.VERSION.SDK_INT < 19) {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/* video/*");
            } else {
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"*/*"});
            }
            ((Activity) context).startActivityForResult(intent, REQUEST_MEDIA_LIBRARY);
        } else {
            showRequestPermission((Activity) context, STORAGE_PERMISSION_ID);
        }
    }

    public static void openGallery(Context context, int type) {
        if (checkStorePermission(context, STORAGE_PERMISSION_ID)) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"*/*"});
            ((Activity) context).startActivityForResult(intent, type);
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
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Uri tempUri = getImageUri(context, imageBitmap);
            File file = new File(getRealPathFromURI(context, tempUri));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    Uri outputUri = PhotoProvider.getPhotoUri(file);
                    mPath = getPathProvider(context, outputUri);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                mPath = getRealPathFromURI(context, tempUri);
            }
        } else {
            if (data.getData() == null) {
                return null;
            }
            String path = ImageFilePath.getPath(context, data.getData());
            if (!TextUtils.isEmpty(path)) {
                mPath = path;
            } else {//google drive
                try {
                    File file = saveFileIntoExternalStorageFromDrive(context, data.getData());
                    mPath = file.getAbsolutePath();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return mPath;
    }

//    public static String resultFilePathCaptured(Context context, Intent data) {
//        String mPath;
//        Bundle extras = data.getExtras();
//        Bitmap imageBitmap = (Bitmap) extras.get("data");
//        Uri tempUri = getImageUri(context, imageBitmap);
//        File file = new File(getRealPathFromURI(context, tempUri));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            try {
//                Uri outputUri = PhotoProvider.getPhotoUri(file);
//                mPath = FileUtil.getPathProvider(context, outputUri);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        } else {
//            mPath = getRealPathFromURI(context, tempUri);
//        }
//        return mPath;
//    }
//
//    public static Single<String> resultFilePathLibrary(Context context, Intent data) {
//        return Single.create(e -> {
//            String path = ImageFilePath.getPath(context, data.getData());
//            if (!TextUtils.isEmpty(path)) {
//                e.onSuccess(path);
//            } else {
//                try {
//                    File file = FileUtil.saveFileIntoExternalStorageFromDrive(context, data.getData());
//                    path = file.getAbsolutePath();
//                    e.onSuccess(path);
//                } catch (Exception error) {
//                    e.onError(error);
//                }
//            }
//        });
//    }

    private static void takePhoto(Context context) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) == null) {
            return;
        }
        ((Activity) context).startActivityForResult(takePictureIntent, REQUEST_PHOTO_CAPTURED);
    }

    private static void recordVideo(Context context) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        ((Activity) context).startActivityForResult(intent, REQUEST_VIDEO_CAPTURED);
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

    private static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage,
                "Title", null);
        return Uri.parse(path);
    }

    public static String getRealPathFromURI(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private static String getPathProvider(Context context, Uri uri) {
        String filePath = null;
        if (uri != null && "content".equals(uri.getScheme())) {
            Cursor cursor = context.getContentResolver()
                    .query(uri,
                            new String[]{android.provider.MediaStore.Images.ImageColumns.DATA},
                            null, null, null);
            cursor.moveToFirst();
            filePath = cursor.getString(0);
            cursor.close();
        } else {
            filePath = uri.getPath();
        }
        return filePath;
    }

    public static File saveFileIntoExternalStorageFromDrive(Context context, Uri uri) throws Exception {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        int originalSize = inputStream.available();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        String fileName = getFileName(context, uri);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                fileName);
        bis = new BufferedInputStream(inputStream);
        bos = new BufferedOutputStream(new FileOutputStream(
                file, false));
        byte[] buf = new byte[originalSize];
        bis.read(buf);
        do {
            bos.write(buf);
        } while (bis.read(buf) != -1);
        bos.flush();
        bos.close();
        bis.close();
        return file;

    }

    private static String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
