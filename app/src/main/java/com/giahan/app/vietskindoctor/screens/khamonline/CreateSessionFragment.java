package com.giahan.app.vietskindoctor.screens.khamonline;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.domains.CreateSessionResult;
import com.giahan.app.vietskindoctor.domains.RequestDoctorBody;
import com.giahan.app.vietskindoctor.domains.RequestDoctorResult;
import com.giahan.app.vietskindoctor.domains.Session;
import com.giahan.app.vietskindoctor.domains.SessionBody;
import com.giahan.app.vietskindoctor.domains.UploadResult;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.screens.chat.ChatFragment;
import com.giahan.app.vietskindoctor.screens.danhsachbs.DanhSachBSFragment;
import com.giahan.app.vietskindoctor.screens.khamonline.PhotoAdapter.OnClickDeleteListener;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.DialogUtils;
import com.giahan.app.vietskindoctor.utils.GalleryUtil;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import com.giahan.app.vietskindoctor.utils.HandlerCallBackSimple;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pham.duc.nam on 04/06/2018.
 */

public class CreateSessionFragment extends BaseFragment implements OnClickDeleteListener{

    @BindView(R.id.tvSsCreate)
    TextView tvSsCreate;
    @BindView(R.id.edtWeight)
    EditText edtWeight;
    @BindView(R.id.edtDescription)
    EditText edtDescription;
    @BindView(R.id.imgAddPhoto)
    ImageView imgAddPhoto;
    @BindView(R.id.rvResultPhoto)
    RecyclerView rvResultPhoto;
    @BindView(R.id.lnBack)
    LinearLayout lnBack;
    private List<String> path = new ArrayList<>();
    private List<Integer> mListPicture = new ArrayList<>();
    private PhotoAdapter photoAdapter;
    private String mDsessionID;
    private String mDoctorID;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_session_v2;
    }

    @Override
    protected void createView(View view) {
        getDoctorData();
        initView();
    }

    @OnClick(R.id.imgAddPhoto)
    public void onAddPhoto() {
        showGallery();
    }

    private void initView() {
        initListPhoto();
        initGallery();
    }

    private void getDoctorData(){
        Bundle bundle = this.getArguments();
        if (bundle == null) return;
        mDoctorID = bundle.getString(Constant.TAG_DOCTOR_ID);
    }

    @OnClick(R.id.tvSsCreate)
    public void onCreate() {
        tvSsCreate.setEnabled(false);
        if (TextUtils.isEmpty(edtWeight.getText().toString())
                || Integer.parseInt(edtWeight.getText().toString()) > 200) {
            DialogUtils.showDialogOneChoice(mActivity, true, false, getString(R.string.validate_weight)
                    , getString(R.string.close), view -> {
                        DialogUtils.hideAlert();
                        tvSsCreate.setEnabled(true);
                    });
        } else if (TextUtils.isEmpty(edtDescription.getText().toString().trim())) {
            DialogUtils.showDialogOneChoice(mActivity, true, false, getString(R.string.vui_long_nhap_trieu_chung)
                    , getString(R.string.close), view -> {
                        DialogUtils.hideAlert();
                        tvSsCreate.setEnabled(true);
                    });
        } else {
            if (path.size() == 0) {
                mListPicture.clear();
                createSession();
            } else {
                uploadImage();
            }
        }
    }

    @OnClick(R.id.lnBack)
    public void onBack(){
        getMainActivity().popFragment();
    }

    private void initListPhoto() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvResultPhoto.setLayoutManager(linearLayoutManager);
        photoAdapter = new PhotoAdapter(mActivity, path);
        photoAdapter.setOnClickDeleteListener(this);
        rvResultPhoto.setAdapter(photoAdapter);
    }

    private void showGallery() {
        GalleryUtil.showGallery(mActivity);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        GalleryUtil.onRequestPermissionsResult(requestCode, permissions, grantResults, mActivity);
    }

    private void initGallery() {
        GalleryUtil.initGalleryMulti(new HandlerCallBackSimple() {
            @Override
            public void onSuccess(List<String> photoList) {
                path.clear();
                path.addAll(photoList);
                photoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError() {
                DialogUtils.showDialogError(mActivity,
                        getString(R.string.msg_alert_info));
            }
        }, path);
    }

    @Override
    public void onClickDelete(int position) {
        path.remove(position);
        GalleryUtil.getPathList().remove(position);
        photoAdapter.notifyDataSetChanged();
    }

    private void uploadImage(){
        if (path == null || path.size() == 0) return;
        List<Integer> listPhotoID = new ArrayList<>();
        for (int i=0;i<path.size();i++) {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            File file = new File(path.get(i));
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            builder.addFormDataPart("file", file.getName(), requestBody);
            Call<UploadResult> call = RequestHelper.getRequest(true, getActivity()).uploadImage(builder.build());
            call.enqueue(new retrofit2.Callback<UploadResult>() {
                @Override
                public void onResponse(retrofit2.Call<UploadResult> call, retrofit2.Response<UploadResult> response) {
                    if (response == null) return;
                    getMainActivity().checkCodeShowDialog(response.code());
                    if (response.body() == null) return;
                    listPhotoID.add(Integer.parseInt(response.body().getId()));
                    if (path.size() == listPhotoID.size()){
                       mListPicture.clear();
                       mListPicture = listPhotoID;
                       createSession();
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<UploadResult> call, Throwable t) {
                    if (t instanceof NoConnectivityException) {
                        // No internet connection
                        getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                    } else {
                        getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                    }
                }
            });
        }

    }

    private void createSession(){
        SessionBody sessionBody = new SessionBody();
        sessionBody.setDescription(edtDescription.getText().toString());
        sessionBody.setWeight(edtWeight.getText().toString());
        if (mListPicture.size() != 0) {
            sessionBody.setPhotoIds(mListPicture);
        }
        if (!TextUtils.isEmpty(mDoctorID)){
            sessionBody.setDoctor_id(mDoctorID);
            onCreateSession(sessionBody);
        }else {
            mPref.sessionBodyNotDoctor().put(GeneralUtil.toJSon(sessionBody));
            getMainActivity().pushFragment(new DanhSachBSFragment());
        }
        tvSsCreate.setEnabled(true);
    }

    private void onCreateSession(SessionBody sessionBody) {
        Call<CreateSessionResult> call = RequestHelper.getRequest(false, getActivity()).createSession(sessionBody);
        call.enqueue(new Callback<CreateSessionResult>() {
            @Override
            public void onResponse(Call<CreateSessionResult> call,
                    Response<CreateSessionResult> response) {
                if (response == null) return;
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() == null || TextUtils.isEmpty(response.body().getId())) return;
                mDsessionID = response.body().getId();
                DialogUtils.showDialogOneChoice(getActivity(), false, true,
                        getString(R.string.yeu_cau_thanh_cong), getString(R.string.close), view -> {
                    mPref.sessionBodyNotDoctor().put("");
                            DialogUtils.hideAlert();
                            getMainActivity().initialize();
                });
            }

            @Override
            public void onFailure(Call<CreateSessionResult> call, Throwable t) {
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
            }
        });
    }
}
