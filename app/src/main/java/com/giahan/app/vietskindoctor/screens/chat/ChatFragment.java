package com.giahan.app.vietskindoctor.screens.chat;

import static com.giahan.app.vietskindoctor.domains.Message.TYPE_FILE;
import static com.giahan.app.vietskindoctor.domains.Message.TYPE_PRESCRIPTION;
import static com.giahan.app.vietskindoctor.domains.Message.TYPE_TEST_SAMPLE;
import static com.giahan.app.vietskindoctor.utils.Constant.TAG_RECEIVE_MESSAGE;
import static com.giahan.app.vietskindoctor.utils.MediaHelper2.REQUEST_MEDIA_LIBRARY;
import static com.giahan.app.vietskindoctor.utils.MediaHelper2.REQUEST_PHOTO_CAPTURED;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.giahan.app.vietskindoctor.R;
import com.giahan.app.vietskindoctor.VietSkinDoctorApplication;
import com.giahan.app.vietskindoctor.activity.IntroImageActivity;
import com.giahan.app.vietskindoctor.activity.VideoCall2Activity;
import com.giahan.app.vietskindoctor.activity.VideoCallActivity;
import com.giahan.app.vietskindoctor.activity.WebviewActivity;
import com.giahan.app.vietskindoctor.base.BaseFragment;
import com.giahan.app.vietskindoctor.domains.Message;
import com.giahan.app.vietskindoctor.domains.MessageBody;
import com.giahan.app.vietskindoctor.domains.PatientResponse;
import com.giahan.app.vietskindoctor.domains.Photo;
import com.giahan.app.vietskindoctor.domains.SendMessageResult;
import com.giahan.app.vietskindoctor.domains.SessionResult;
import com.giahan.app.vietskindoctor.domains.UploadResult;
import com.giahan.app.vietskindoctor.model.UserInfoResponse;
import com.giahan.app.vietskindoctor.network.NoConnectivityException;
import com.giahan.app.vietskindoctor.screens.chat.MessageAdapter.OnClickImageListener;
import com.giahan.app.vietskindoctor.screens.chat.MessageAdapter.OnClickMtesListener;
import com.giahan.app.vietskindoctor.services.NetworkChanged;
import com.giahan.app.vietskindoctor.services.RequestHelper;
import com.giahan.app.vietskindoctor.utils.ActionsDialog;
import com.giahan.app.vietskindoctor.utils.Constant;
import com.giahan.app.vietskindoctor.utils.DateUtils;
import com.giahan.app.vietskindoctor.utils.DialogUtils;
import com.giahan.app.vietskindoctor.utils.GeneralUtil;
import com.giahan.app.vietskindoctor.utils.MediaHelper2;
import com.giahan.app.vietskindoctor.utils.Toolbox;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pham.duc.nam on 11/06/2018.
 */

public class ChatFragment extends BaseFragment implements OnClickImageListener,
        OnClickMtesListener, XetNghiemAdapter.OnClickMtesListener, PhotoAdapter.OnClickPhotoListener{
    @BindView(R.id.messages)
    RecyclerView mMessagesView;
    @BindView(R.id.message_input)
    EditText mInputMessageView;
    @BindView(R.id.imgCall)
    ImageView imgCall;
    @BindView(R.id.send_button)
    ImageView sendButton;
    @BindView(R.id.tvNameDoctor)
    TextView tvNameDoctor;
    @BindView(R.id.imgMenu)
    ImageView imgMenu;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @BindView(R.id.lnRight)
    LinearLayout lnRight;
    @BindView(R.id.lnBack)
    LinearLayout lnBack;
    @BindView(R.id.imgAttachPhoto)
    ImageView imgAttachPhoto;
    @BindView(R.id.rvPhoto)
    RecyclerView rvPhoto;
    @BindView(R.id.rlMauXN)
    RelativeLayout rlMauXN;
    @BindView(R.id.rlDonThuoc)
    RelativeLayout rlDonThuoc;
    @BindView(R.id.lvMauXN)
    RecyclerView lvMauXN;
    @BindView(R.id.lvDonThuoc)
    RecyclerView lvDonThuoc;
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    @BindView(R.id.lnCallVideo)
    LinearLayout lnCallVideo;
    @BindView(R.id.mNestedSV)
    NestedScrollView nestedScrollView;


    private List<Message> mMessages = new ArrayList<>();
    private List<Message> mPathURL = new ArrayList<>();
    private List<Message> mListMauXN = new ArrayList<>();
    private List<Message> mListDonThuoc = new ArrayList<>();

    private String mPatientID;
    private String mTimeRemain;
    private String mPhone;
    private String mAvatarPatient;

    private MessageAdapter mAdapter;
    private PhotoAdapter mPhotoAdapter;
    private XetNghiemAdapter mTestSampleAdapter;
    private XetNghiemAdapter mPresAdapter;
    private String mDsessionID;
    private String mPatientName;
    private String mUserID;
    private int firstVisibleItemIndex;
    private boolean loading = true;
    private boolean isOpen =false;
    private boolean isGallery = false;

    private Socket mSocket;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView mRecyclerView, int dx, int dy) {
            super.onScrolled(mRecyclerView, dx, dy);
            firstVisibleItemIndex =
                    ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            if (loading) return;
            if (firstVisibleItemIndex == 0) {
                loading = true;
                Message last = mAdapter.getListMessages().get(0);
                getData(mDsessionID, last.getId());
            }
        }
    };
    private Emitter.Listener OnNewMessage = args -> mActivity.runOnUiThread(() -> {
        JSONObject jsonObject = (JSONObject) args[0];
        Message message = new Message();
        try {
            message = GeneralUtil.fromJSon(Message.class, jsonObject.getString("newMessage"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (message.getUserId() == null) return;
        addMessageToBottom(message);
    });
    {
        try {
            mSocket = IO.socket(Constant.URL_SOCKET);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_2;
    }

    @Override
    protected void createView(View view) {
        getActivity().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        showLoading();
        getSessionData();
        getData(mDsessionID, null);
        getInfoPatient(mPatientID);
        setupView();
        setupSocket();
    }

    private void setupSocket() {
        mSocket = getBaseActivity().getSocket();
        if (mSocket == null) {
            JSONObject jsonObject = new JSONObject();
            try {
                mSocket = IO.socket(Constant.URL_SOCKET);
                jsonObject.put("access_token", mPref.token().get());
                mSocket.emit(Constant.TAG_LOGIN_SOCKET, jsonObject);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            } catch (JSONException ignored) {
                Toast.makeText(getActivity(), "Socket error!", Toast.LENGTH_SHORT).show();
            }
        }
        receiveMessage();
    }

    private void receiveMessage() {
        mSocket.on(TAG_RECEIVE_MESSAGE, OnNewMessage);
        mSocket.connect();
    }

    private void setupView() {
        setupMenuView();
        tvNameDoctor.setText(mPatientName);

        tvTime.setText(String.format("Còn %s ngày",
                TextUtils.isEmpty(mTimeRemain) ? "14" : DateUtils.getDateRemain(mTimeRemain)));

        mAdapter = new MessageAdapter(mActivity, mMessages, mUserID, mAvatarPatient);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mMessagesView.setLayoutManager(linearLayoutManager);
        mMessagesView.setAdapter(mAdapter);
        mMessagesView.addOnScrollListener(mOnScrollListener);
        mAdapter.setOnClickImageListener(this);
        mAdapter.setOnClickMtesListener(this);
        lnCallVideo.setOnClickListener(view -> onCallVideo());

    }

    private void setNestedScrollView() {
        nestedScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int scrollViewHeight = nestedScrollView.getHeight();
                if (scrollViewHeight > 0) {
                    nestedScrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    final View lastView = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
                    final int lastViewBottom = lastView.getBottom() + nestedScrollView.getPaddingBottom();
                    final int deltaScrollY = lastViewBottom - scrollViewHeight - nestedScrollView.getScrollY();
                    /* If you want to see the scroll animation, call this. */
                    nestedScrollView.smoothScrollBy(0, deltaScrollY);
                    /* If you don't want, call this. */
                    nestedScrollView.scrollBy(0, deltaScrollY);
                }
            }
        });
    }

    private void onCallVideo() {
        Intent intent = new Intent(getMainActivity(), VideoCall2Activity.class);
        intent.putExtra("PATIENT_ID", mPatientID);
        intent.putExtra("DOCTOR_ID", mUserID);
        startActivity(intent);
//        getMainActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

    private void setupMenuView(){
        drawer_layout.setClipToPadding(false);
        int width_layout = mActivity.getResources().getDisplayMetrics().widthPixels * 3/4;
        ScrollView.LayoutParams params = (ScrollView.LayoutParams)lnRight.getLayoutParams();
        params.width = width_layout;
        lnRight.setLayoutParams(params);
        rvPhoto.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mPhotoAdapter = new PhotoAdapter(getActivity(), mPathURL);
        mPhotoAdapter.setOnClickPhoto(this);
        rvPhoto.setAdapter(mPhotoAdapter);
        mTestSampleAdapter = new XetNghiemAdapter(mActivity, mListMauXN);
        setupList(lvMauXN, mTestSampleAdapter);
        mPresAdapter = new XetNghiemAdapter(mActivity, mListDonThuoc);
        setupList(lvDonThuoc, mPresAdapter);
        lvDonThuoc.setAdapter(mPresAdapter);
    }

    private void setupList(RecyclerView recyclerView, XetNghiemAdapter adapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        adapter.setOnClickImageListener(this);
    }

    @OnClick(R.id.send_button)
    public void onSend() {
        if (GeneralUtil.checkInternet(getActivity())){
            sendText();
        }else {
            getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
        }
    }

    @OnClick(R.id.llMenu)
    public void onMenu(){
        if (isOpen) closeMenu();
        else openMenu();
    }

    @OnClick(R.id.lnBack)
    public void onBack(){
        getMainActivity().popFragment();
    }

    @OnClick(R.id.imgAttachPhoto)
    public void onAttachPhoto(){
        showDialogMediaFile();
//        GalleryUtil.showGallery(mActivity);
//        DialogUtils.showDialogAttachFile(getActivity(), view -> takeNewPhoto(), view -> openGallery());
    }

    private void showDialogMediaFile() {
        hideKeyboard();
        ActionsDialog actionsDialog = new ActionsDialog();
        List<String> list = Arrays.asList(getString(R.string.new_photo), getString(R.string.select_photo));
        actionsDialog.getData(list);
        actionsDialog.show(getFragmentManager());
        actionsDialog.callback = index -> {
            switch (index) {
                case 0:
                    takeNewPhoto();
                    break;
                case 1:
                    openGallery();
                    break;
            }
        };
    }

    private void takeNewPhoto() {
        MediaHelper2.takeMedia(getActivity(), REQUEST_PHOTO_CAPTURED);
        DialogUtils.hideAlert();
    }

    private void openGallery(){
        isGallery = true;
        MediaHelper2.openGallery(getActivity());
        DialogUtils.hideAlert();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case MediaHelper2.STORAGE_PERMISSION_ID:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                }
                break;
            case MediaHelper2.CAMERA_PERMISSION_ID:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takeNewPhoto();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case REQUEST_MEDIA_LIBRARY:
                if (data == null) {
                    break;
                }
                showPreview(MediaHelper2.resultCapture(getActivity(), requestCode, resultCode, data));
                break;
            case REQUEST_PHOTO_CAPTURED:
                String mPath = MediaHelper2.resultFilePathCaptured();
                if (TextUtils.isEmpty(mPath)) {
                    break;
                }
                showPreview(mPath);
                break;
        }
    }

    private void showPreview(String pathPhoto) {
        DialogUtils.showDialogPreview(getActivity(), pathPhoto, view -> cancelUpload(), view -> upload(pathPhoto));
    }

    private void upload(String pathPhoto) {
        DialogUtils.hideAlert();
        if (!TextUtils.isEmpty(pathPhoto)) {
            uploadImage(pathPhoto);
        } else {
            Toast.makeText(getActivity(), getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelUpload(){
        DialogUtils.hideAlert();
//        if (isGallery) {
//            openGallery();
//        }
    }

    @OnClick(R.id.imgCall)
    public void actionCall() {
        if (!Toolbox.isEmpty(mPhone)) {
            DialogUtils.showDialogTwoChoice(getActivity(), false, false, true, null,
                    getString(R.string.ask_call), getString(R.string.cancel), getString(R.string.call), view -> {
                        DialogUtils.hideAlert();
                    }, view -> {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mPhone, null));
                        startActivity(intent);
                    });
        } else {
            DialogUtils.showDialogOneChoice(getActivity(), true, false,
                    getString(R.string.msg_no_phone, mPatientName),
                    getString(R.string.close), view -> {
                        DialogUtils.hideAlert();
                    });
        }
    }

    @OnClick(R.id.rlMauXN)
    public void clickMauXN() {
        lvMauXN.setVisibility(lvMauXN.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.rlDonThuoc)
    public void clickDonThuoc() {
        lvDonThuoc.setVisibility(
                lvDonThuoc.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//            @NonNull int[] grantResults) {
//        GalleryUtil.onRequestPermissionsResult(requestCode, permissions, grantResults, mActivity);
//    }

    private void openMenu() {
        isOpen = true;
        drawer_layout.openDrawer(Gravity.END);
    }

    private void closeMenu(){
        isOpen = false;
        drawer_layout.closeDrawer(Gravity.END);
    }

    private void sendText() {
        if (null == mUserID) return;
        if (!mSocket.connected()) return;

        String message = mInputMessageView.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            mInputMessageView.requestFocus();
            return;
        }

        mInputMessageView.setText("");
        Message item = new Message();
        item.setMessage(message);
        item.setType(Message.TYPE_TEXT);
        item.setUserId(mUserID);
        item.setCreatedAt(DateUtils.getCurrentDate());
        addMessageToBottom(item);
        sendMessage(mDsessionID, 1, message, null, null);
    }

    private void sendFile(String url, String id){
        if (null == mUserID) return;
        if (!mSocket.connected()) return;
        Message item = new Message();
        item.setObjUrl(url);
        item.setOnjId(id);
        item.setType(Message.TYPE_FILE);
        item.setUserId(mUserID);
        item.setCreatedAt(DateUtils.getCurrentDate());
        addMessageToBottom(item);
        sendMessage(mDsessionID, 2, null, id, url);
    }

    private void uploadImage(String path){
        if (TextUtils.isEmpty(path)) return;
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            File file = new File(path);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            builder.addFormDataPart("file", file.getName(), requestBody);
            Call<UploadResult> call = RequestHelper.getRequest(true, getActivity()).uploadImage(builder.build());
            call.enqueue(new retrofit2.Callback<UploadResult>() {
                @Override
                public void onResponse(retrofit2.Call<UploadResult> call, retrofit2.Response<UploadResult> response) {
                    if (response == null) return;
                    getMainActivity().checkCodeShowDialog(response.code());
                    if (response.body() == null) return;
                    //listPhotoID.add(Integer.parseInt(response.body().getId()));
                    sendFile(response.body().getUrl(), response.body().getId());
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

    private void sendMessage(String mDsessionID, int mType, String mMessage, String id, String
            url) {
        MessageBody messageBody = new MessageBody(mDsessionID, mType, mMessage, id, url);
        Call<SendMessageResult> call = RequestHelper.getRequest(false, getActivity()).sendMessage(messageBody);
        call.enqueue(new Callback<SendMessageResult>() {
            @Override
            public void onResponse(Call<SendMessageResult> call,
                    Response<SendMessageResult> response) {
                if (response == null) return;
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() == null) return;
            }

            @Override
            public void onFailure(Call<SendMessageResult> call, Throwable t) {
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
            }
        });
    }

    private void addMessageToBottom(Message message) {
        addDataToList(message, TYPE_FILE, mPathURL);
        addDataToList(message, TYPE_TEST_SAMPLE, mListMauXN);
        addDataToList(message, TYPE_PRESCRIPTION, mListDonThuoc);
        mPhotoAdapter.notifyDataSetChanged();
        mTestSampleAdapter.notifyDataSetChanged();
        mPresAdapter.notifyDataSetChanged();
        mAdapter.addMessage(message);
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }

    private void addDataToList(Message message, String type, List<Message> list){
        if (!TextUtils.isEmpty(message.getObjUrl()) && message.getType().equals(type)) {
            if (list.contains(message)) return;
            list.add(message);
        }
    }

    private void scrollToBottom() {
        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private void getSessionData() {
        Bundle bundle = this.getArguments();
        if (bundle == null) return;
        mDsessionID = bundle.getString(Constant.TAG_DSSESION_ID);
        mPatientName = bundle.getString(Constant.TAG_PATIENT_NAME);
        mPatientID = bundle.getString(Constant.TAG_PATIENT_ID);
        mTimeRemain = bundle.getString(Constant.TAG_REMAIN);
        mUserID = GeneralUtil.fromJSon(UserInfoResponse.class, mPref.user().get()).getId();
    }

    private void getData(String dssesion, @Nullable String lastid) {
        Call<SessionResult> call =
                RequestHelper.getRequest(false, getActivity()).getSessionMessage(dssesion, lastid);
        call.enqueue(new Callback<SessionResult>() {
            @Override
            public void onResponse(Call<SessionResult> call, Response<SessionResult> response) {
                hideLoading();
                if (response == null) return;
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() == null) return;
                List<Message> list = GeneralUtil.sortMessage(response.body().getMessageList());
                if (list.size() != 0) {
                    loading = false;
                    mMessages.addAll(0, list);
                    addData(TYPE_FILE, mPathURL);
                    addData(TYPE_TEST_SAMPLE, mListMauXN);
                    addData(TYPE_PRESCRIPTION, mListDonThuoc);
                    mTestSampleAdapter.notifyDataSetChanged();
                    mPresAdapter.notifyDataSetChanged();
                    mPhotoAdapter.notifyDataSetChanged();
                    mAdapter.notifyItemRangeInserted(0, list.size());
                    if (lastid == null) scrollToBottom();
                    setNestedScrollView();
                } else {
                    loading = true;
                }
            }

            @Override
            public void onFailure(Call<SessionResult> call, Throwable t) {
                hideLoading();
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
            }
        });
    }

    private void getInfoPatient(String patientID){
        if (TextUtils.isEmpty(patientID)) return;
        Call<PatientResponse> call = RequestHelper.getRequest(false, getActivity()).getPatientInfo(patientID);
        call.enqueue(new Callback<PatientResponse>() {
            @Override
            public void onResponse(final Call<PatientResponse> call, final Response<PatientResponse> response) {
                hideLoading();
                if (response == null) return;
                getMainActivity().checkCodeShowDialog(response.code());
                if (response.body() == null) return;
                mPhone = response.body().getPhone();
                mAvatarPatient = response.body().getAvatar();
            }

            @Override
            public void onFailure(final Call<PatientResponse> call, final Throwable t) {
                hideLoading();
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
                } else {
                    getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.msg_alert_info));
                }
            }
        });
    }

    private void addData(String type, List<Message> list){
        for (int i = 0; i < mMessages.size(); i++) {
            if (TextUtils.isEmpty(mMessages.get(i).getObjUrl())) continue;
            if (!mMessages.get(i).getType().equals(type)) continue;
            if (list.contains(mMessages.get(i))) continue;
            list.add(mMessages.get(i));
        }
    }

    @Override
    public void onClickImage(Message message) {
        showDetailImage(message);
    }

    @Override
    public void onClickMtes(Message message) {
        showDetailMtes(message);
    }

    private void showDetailImage(Message message){
        if(!VietSkinDoctorApplication.isIsOpenDetailScreen()) {
            VietSkinDoctorApplication.setIsOpenDetailScreen(true);
            ArrayList<Photo> photos = new ArrayList<>();
            photos.add(new Photo(0, message.getObjUrl()));
            Intent intent = new Intent(getMainActivity(), IntroImageActivity.class);
            intent.putExtra("data", Toolbox.gson().toJson(photos));
            startActivity(intent);
            getMainActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
        }
    }

    private void showDetailMtes(Message message){
        if(!VietSkinDoctorApplication.isIsOpenDetailScreen()) {
            VietSkinDoctorApplication.setIsOpenDetailScreen(true);
            Intent intent = new Intent(getMainActivity(), WebviewActivity.class);
            intent.putExtra("url", message.getObjUrl() + "?access_token=" + mPref.token().get());
            startActivity(intent);
            getMainActivity().overridePendingTransition(R.anim.enter_from_bottom, R.anim.exit_to_top);
        }
    }

    @Override
    public void OnClickPhoto(Message message) {
        showDetailImage(message);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NetworkChanged event) {
        showNetworkStateView();
    }

    private void showNetworkStateView() {
//        Crouton.cancelAllCroutons();
        boolean isConnected = GeneralUtil.checkInternet(VietSkinDoctorApplication.getInstance());
        if (!isConnected) {
            getMainActivity().showAlertDialog(getString(R.string.title_alert_info), getString(R.string.error_no_connection));
        }
    }
}
