package com.giahan.app.vietskindoctor.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.activity.MainActivity;
import com.giahan.app.vietskindoctor.domains.UploadResult;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by pham.duc.nam on 28/06/2018.
 */
public class GalleryUtil {
    private static final int PERMISSIONS_REQUEST_READ_STORAGE = 8;
    private static GalleryConfig galleryConfig;

    public static void initGalleryMulti(HandlerCallBackSimple iHandlerCallBack, List<String>
            path){
        galleryConfig = new GalleryConfig.Builder().imageLoader(new PicassoImageLoader())
                .iHandlerCallBack(iHandlerCallBack)
                .provider(Constant.TAG_FILE_PROVIDER)
                .pathList(path)
                .multiSelect(true)
                .multiSelect(true, 5)
                .maxSize(5)
                .crop(false)
                .crop(false, 1, 1, 500, 500)
                .isShowCamera(true)
                .filePath(Constant.TAG_FILE_PATH)
                .build();
    }

    public static void initGallerySingle(HandlerCallBackSimple iHandlerCallBack, String path){
        galleryConfig = new GalleryConfig.Builder().imageLoader(new PicassoImageLoader())
                .iHandlerCallBack(iHandlerCallBack)
                .provider(Constant.TAG_FILE_PROVIDER)
                .filePath(path)
                .multiSelect(false)
                .crop(false)
                .crop(false, 1, 1, 500, 500)
                .isShowCamera(true)
                .filePath(Constant.TAG_FILE_PATH)
                .build();
    }

    public static void showGallery(Context context){
        galleryConfig.getBuilder().build();
        galleryConfig.getBuilder().isOpenCamera(false).build();
        initPermissions(context);
    }

    public static ArrayList<String> getPathList(){
        return galleryConfig.getPathList();
    }

    private static void initPermissions(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(context, context.getString(R.string.permission_request), Toast
                        .LENGTH_SHORT)
                        .show();
            } else {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        PERMISSIONS_REQUEST_READ_STORAGE);
            }
        } else {
            GalleryPick.getInstance()
                    .setGalleryConfig(galleryConfig)
                    .open((Activity) context);
        }
    }

    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults, Context context){
        if (requestCode == PERMISSIONS_REQUEST_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                GalleryPick.getInstance()
                        .setGalleryConfig(galleryConfig)
                        .open((Activity) context);
            }
        }
    }
}
