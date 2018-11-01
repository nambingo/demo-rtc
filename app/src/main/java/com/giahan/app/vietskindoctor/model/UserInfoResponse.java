package com.giahan.app.vietskindoctor.model;

import com.giahan.app.vietskindoctor.utils.PrefHelper_;
import com.giahan.app.vietskindoctor.utils.Toolbox;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NamVT on 5/18/2018.
 */

public class UserInfoResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("birthdate")
    @Expose
    private String birthdate;
    @SerializedName("passwd")
    @Expose
    private String passwd;
    @SerializedName("facebook_id")
    @Expose
    private String facebookId;
    @SerializedName("google_id")
    @Expose
    private String googleId;
    @SerializedName("credits")
    @Expose
    private String credits;
    @SerializedName("credit_hold")
    @Expose
    private String creditHold;
    @SerializedName("verify_register")
    @Expose
    private String verifyRegister;
    @SerializedName("verify_register_hash")
    @Expose
    private String verifyRegisterHash;
    @SerializedName("activated")
    @Expose
    private String activated;
    @SerializedName("forgot_pw_hash")
    @Expose
    private String forgotPwHash;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("sex")
    @Expose
    private String gender;
    @SerializedName("avatarAcc")
    @Expose
    private String avatarAcc;
    @SerializedName("weight")
    @Expose
    private String weight;

    @SerializedName("avatar")
    private String mAvatarDoctor;

    @SerializedName("avatar_url")
    private String mAvatar;

    @SerializedName("workplace")
    private String mWorkplace;

    @SerializedName("degree")
    private String mDegree;

    public UserInfoResponse() {
    }

    public static UserInfoResponse getUser(PrefHelper_ pref) {
        String user = pref.user().get();
        if(!Toolbox.isEmpty(user)){
            return Toolbox.gson().fromJson(user, UserInfoResponse.class);
        }else {
            return null;
        }
    }

    public UserInfoResponse(String id, String email, String name, String accessToken, String type, String phone,
                            String birthdate, String passwd, String facebookId, String googleId, String credits, String creditHold,
                            String verifyRegister, String verifyRegisterHash, String activated, String forgotPwHash,
                            String address, String gender, String avatar, String weight) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.accessToken = accessToken;
        this.type = type;
        this.phone = phone;
        this.birthdate = birthdate;
        this.passwd = passwd;
        this.facebookId = facebookId;
        this.googleId = googleId;
        this.credits = credits;
        this.creditHold = creditHold;
        this.verifyRegister = verifyRegister;
        this.verifyRegisterHash = verifyRegisterHash;
        this.activated = activated;
        this.forgotPwHash = forgotPwHash;
        this.address = address;
        this.gender = gender;
        this.avatarAcc = avatar;
        this.weight = weight;
    }

    public void setAgainData(UserInfoResponse userInfoResponse){
        this.id = userInfoResponse.id;
        this.email = userInfoResponse.email;
        this.name = userInfoResponse.name;
        this.accessToken = userInfoResponse.accessToken;
        this.type = userInfoResponse.type;
        this.gender = userInfoResponse.gender;
        this.phone = userInfoResponse.phone;
        this.birthdate = userInfoResponse.birthdate;
        this.passwd = userInfoResponse.passwd;
        this.facebookId = userInfoResponse.facebookId;
        this.googleId = userInfoResponse.googleId;
        this.credits = userInfoResponse.credits;
        this.creditHold = userInfoResponse.creditHold;
        this.weight = userInfoResponse.weight;
        this.mAvatar = userInfoResponse.mAvatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getCreditHold() {
        return creditHold;
    }

    public void setCreditHold(String creditHold) {
        this.creditHold = creditHold;
    }

    public String getVerifyRegister() {
        return verifyRegister;
    }

    public void setVerifyRegister(String verifyRegister) {
        this.verifyRegister = verifyRegister;
    }

    public String getVerifyRegisterHash() {
        return verifyRegisterHash;
    }

    public void setVerifyRegisterHash(String verifyRegisterHash) {
        this.verifyRegisterHash = verifyRegisterHash;
    }

    public String getActivated() {
        return activated;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public String getForgotPwHash() {
        return forgotPwHash;
    }

    public void setForgotPwHash(String forgotPwHash) {
        this.forgotPwHash = forgotPwHash;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatarAcc() {
        return avatarAcc;
    }

    public void setAvatarAcc(String avatarAcc) {
        this.avatarAcc = avatarAcc;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAvatarDoctor() {
        return mAvatarDoctor;
    }

    public void setAvatarDoctor(final String avatarDoctor) {
        mAvatarDoctor = avatarDoctor;
    }

    public String getWorkplace() {
        return mWorkplace;
    }

    public void setWorkplace(final String workplace) {
        mWorkplace = workplace;
    }

    public String getDegree() {
        return mDegree;
    }

    public void setDegree(final String degree) {
        mDegree = degree;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(final String avatar) {
        mAvatar = avatar;
    }
}
