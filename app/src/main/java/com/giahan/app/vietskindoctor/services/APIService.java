package com.giahan.app.vietskindoctor.services;

import com.giahan.app.vietskindoctor.domains.AcceptRequest;
import com.giahan.app.vietskindoctor.domains.AcceptRequestBody;
import com.giahan.app.vietskindoctor.domains.CancelBody;
import com.giahan.app.vietskindoctor.domains.CancelResult;
import com.giahan.app.vietskindoctor.domains.CreateSessionResult;
import com.giahan.app.vietskindoctor.domains.Doctor;
import com.giahan.app.vietskindoctor.domains.ListDoctorResult;
import com.giahan.app.vietskindoctor.domains.ListFilterResult;
import com.giahan.app.vietskindoctor.domains.ListRequestResult;
import com.giahan.app.vietskindoctor.domains.ListSessionResult;
import com.giahan.app.vietskindoctor.domains.MessageBody;
import com.giahan.app.vietskindoctor.domains.PatientResponse;
import com.giahan.app.vietskindoctor.domains.ReadMessageBody;
import com.giahan.app.vietskindoctor.domains.ReadMessageResult;
import com.giahan.app.vietskindoctor.domains.SendMessageResult;
import com.giahan.app.vietskindoctor.domains.Session;
import com.giahan.app.vietskindoctor.domains.MappingResult;
import com.giahan.app.vietskindoctor.domains.RequestDoctorBody;
import com.giahan.app.vietskindoctor.domains.RequestDoctorResult;
import com.giahan.app.vietskindoctor.domains.SessionBody;
import com.giahan.app.vietskindoctor.domains.SessionResult;
import com.giahan.app.vietskindoctor.domains.UploadResult;
import com.giahan.app.vietskindoctor.model.AutoDiagnoseBody;
import com.giahan.app.vietskindoctor.model.AutoDiagnoseResponse;
import com.giahan.app.vietskindoctor.model.BaseResponse;
import com.giahan.app.vietskindoctor.model.FbModelBody;
import com.giahan.app.vietskindoctor.model.GoogleModelBody;
import com.giahan.app.vietskindoctor.model.InfoUpdateBody;
import com.giahan.app.vietskindoctor.model.PayUrlBody;
import com.giahan.app.vietskindoctor.model.PayUrlResponse;
import com.giahan.app.vietskindoctor.model.UpdateInfoResponse;
import com.giahan.app.vietskindoctor.model.UserInfoResponse;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by pham.duc.nam
 */

public interface APIService {
    //Mapping-Quest
    @GET("mapping-quests")//1
    Call<MappingResult> getMappingResult();

    @POST("doctor/facebook-login")
    Call<UserInfoResponse> loginFb(@Body FbModelBody fbModelBody);

    @POST("doctor/google-login")
    Call<UserInfoResponse> loginGoogle(@Body GoogleModelBody googleModelBody);

    @GET("patient/get-available-doctors")
    Call<ListDoctorResult> getListDoctorResult(@Query("name") String name,
            @Query("degree") String degree, @Query("experience") String experience,
            @Query("sex") String sex, @Query("languages") String languages,
            @Query("majors") String majors, @Query("online") String online,
            @Query("city") String city);

    @POST("payment/get-pay-url")
    Call<PayUrlResponse> getPayUrl(@Body PayUrlBody payUrlBody);

    @GET("patient/get-doctor-filters")
    Call<ListFilterResult> getFilter();

    @GET("me/info")
    Call<UserInfoResponse> getUserInfo();

//    @POST("me")
//    Call<List<Integer>> updateInfo(@Body InfoUpdateBody infoUpdateBody);

    @POST("me")
    Call<UpdateInfoResponse> updateInfo(@Body InfoUpdateBody infoUpdateBody);

    @POST("upload")
    Call<UploadResult> uploadImage(@Body RequestBody file);

    @POST("patient/dsession")
    Call<CreateSessionResult> createSession(@Body SessionBody sessionBody);

    @GET("doctor/dsessions")
    Call<ListSessionResult> getListSession();

    @GET("doctor/dsessions")
    Call<ListSessionResult> getListSessionWait(@Query("filter") String filter);

    @POST("patient/dsession/request-doctor")
    Call<RequestDoctorResult> requestDoctor(@Body RequestDoctorBody requestDoctorBody);

    @GET("doctor/awaiting-drequests")
    Call<ListRequestResult> getListSessionRequest();
//    Call<ListSessionResult> getListSession(@Query("filter") String filter);

    @POST("patient/dsession/cancel")
    Call<CancelResult> cancelSession(@Body CancelBody cancelBody);

    @POST("patient/auto-diagnose")
    Call<AutoDiagnoseResponse> autoDiagnose(@Body AutoDiagnoseBody autoDiagnoseBody);

    @GET("dsession/chat")
    Call<SessionResult> getSessionMessage(@Query("dsession_id") String dsession_id, @Query
            ("last_id") String last_id);

    @POST("dsession/chat")
    Call<SendMessageResult> sendMessage(@Body MessageBody messageBody);

    @GET("patient/get-doctor-info/{id}")
    Call<Doctor> getInfoDoctor(@Path("id") String id);

    @POST("dsession/chat/last-read")
    Call<ReadMessageResult> sendLastRead(@Body ReadMessageBody readMessageBody);

    @POST("doctor/dsession/accept-request")
    Call<AcceptRequest> acceptRequest(@Body AcceptRequestBody acceptRequestBody);

    @GET("doctor/fetch-patient-info/{PATIENT_ID}")
    Call<PatientResponse> getPatientInfo(@Path("PATIENT_ID") String patientID);
}
