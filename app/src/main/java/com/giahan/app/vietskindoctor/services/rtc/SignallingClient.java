package com.giahan.app.vietskindoctor.services.rtc;

import android.annotation.SuppressLint;
import android.util.Log;
import com.giahan.app.vietskindoctor.utils.Constant;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;

public class SignallingClient {
    private static SignallingClient instance;
    //    private String roomName = null;
    private Socket socket;
    //    boolean isChannelReady = false;
    public boolean isInitiator = false;
    public boolean isStarted = false;
    private SignalingInterface callback;

    //This piece of code should not go into production!!
    //This will help in cases where the node server is running in non-https server and you want to ignore the warnings
    @SuppressLint("TrustAllX509TrustManager")
    private final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }

        public void checkClientTrusted(X509Certificate[] chain,
                String authType) {
        }

        public void checkServerTrusted(X509Certificate[] chain,
                String authType) {
        }
    }};

    public static SignallingClient getInstance() {
        if (instance == null) {
            instance = new SignallingClient();
        }
//        if (instance.roomName == null) {
//            //set the room name here
//            instance.roomName = "some_room_name";
//        }
        return instance;
    }


    public void init(SignalingInterface signalingInterface, Socket socket) {
        this.callback = signalingInterface;
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, trustAllCerts, null);
//            IO.setDefaultHostnameVerifier((hostname, session) -> true);
//            IO.setDefaultSSLContext(sslcontext);
            //set the socket.io url here
//            socket = IO.socket("your_socket_io_instance_url_with_port");
//            socket.connect();
//            Log.e("SignallingClient", "init() called");
//            socket.on(Constant.TAG_RTC_OFFER_SOCKET, args -> {
//                JSONObject data = (JSONObject) args[0];
//                callback.onOfferReceived(data);
//            });
//
            socket.on(Constant.TAG_RTC_CANDIDATE_SOCKET, args -> {
                JSONObject data = (JSONObject) args[0];
                callback.onIceCandidateReceived(data);
            });
//            if (!roomName.isEmpty()) {
//                emitInitStatement(roomName);
//            }


//            //room created event.
//            socket.on("created", args -> {
//                Log.d("SignallingClient", "created call() called with: args = [" + Arrays.toString(args) + "]");
//                isInitiator = true;
//                callback.onCreatedRoom();
//            });
//
//            //room is full event
//            socket.on("full", args -> Log.d("SignallingClient", "full call() called with: args = [" + Arrays.toString(args) + "]"));
//
//            //peer joined event
//            socket.on("join", args -> {
//                Log.d("SignallingClient", "join call() called with: args = [" + Arrays.toString(args) + "]");
////                isChannelReady = true;
//                callback.onNewPeerJoined();
//            });
//
//            //when you joined a chat room successfully
//            socket.on("joined", args -> {
//                Log.d("SignallingClient", "joined call() called with: args = [" + Arrays.toString(args) + "]");
////                isChannelReady = true;
//                callback.onJoinedRoom();
//            });

            //log event
//            socket.on("LOG", args -> Log
//                    .e("SignallingClient", "init:  -----> SignallingClient  "+ " log call() called with: args = [" + Arrays
//                            .toString(args) + "]"));

            //bye event
//            socket.on("bye", args -> callback.onRemoteHangUp((String) args[0]));

            //messages - SDP and ICE candidates are transferred through this
//            socket.on(Constant.TAG_RTC_OFFER_SOCKET, args -> {
//                Log.e("----SignallingClient", "message call() called with: args = [" + Arrays.toString(args) + "]");
//                if (args[0] instanceof String) {
//                    Log.d("SignallingClient", "String received :: " + args[0]);
//                    String data = (String) args[0];
//                    callback.onTryToStart();
////                    if (data.equalsIgnoreCase("got user media")) {
////
////                    }
////                    if (data.equalsIgnoreCase("bye")) {
////                        callback.onRemoteHangUp(data);
////                    }
//                } else if (args[0] instanceof JSONObject) {
//                    try {
//
//                        JSONObject data = (JSONObject) args[0];
//                        JSONObject sdp = data.getJSONObject("sdp");
//                        Log.e("---SignallingClient", "Json Received :: " + data.toString());
//                        String type = sdp.getString("type");
//                        if (type.equalsIgnoreCase("offer")) {
//                            callback.onOfferReceived(sdp);
//                        } else if (type.equalsIgnoreCase("answer")) {
//                            callback.onAnswerReceived(sdp);
//                        } else if (type.equalsIgnoreCase("candidate")) {
//                            callback.onIceCandidateReceived(sdp);
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
        } catch ( NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private void emitInitStatement(String message) {
        Log.d("SignallingClient", "emitInitStatement() called with: event = [" + "create or join" + "], message = [" + message + "]");
        socket.emit("create or join", message);
    }

    public void emitMessage(String message) {
        Log.d("SignallingClient", "emitMessage() called with: message = [" + message + "]");
        socket.emit("message", message);
    }

    public void emitMessageOffer(SessionDescription message, Socket socket, int patientID) {
        try {
            Log.d("SignallingClient", "emitMessage() called with: message = [" + message + "]");
            JSONObject obj = new JSONObject();
            obj.put("type", message.type.canonicalForm());
            obj.put("sdp", message.description);
            Log.d("emitMessage", obj.toString());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sdp", obj);
            jsonObject.put("toUserId", patientID);
            socket.emit(Constant.TAG_RTC_OFFER_SOCKET, jsonObject);
            Log.e("SignallingClient", "emitMessage:  -----> vivek1794: "+jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void emitMessage(SessionDescription message, Socket socket, int doctorID) {
        try {
            Log.d("SignallingClient", "emitMessage() called with: message = [" + message + "]");
            JSONObject obj = new JSONObject();
            obj.put("type", message.type.canonicalForm());
            obj.put("sdp", message.description);
            Log.d("emitMessage", obj.toString());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sdp", obj);
            jsonObject.put("toUserId", doctorID);
            socket.emit(Constant.TAG_RTC_ANSWER_SOCKET, jsonObject);
            Log.e("SignallingClient", "emitMessage:  -----> vivek1794: "+jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void emitIceCandidate(IceCandidate iceCandidate, Socket socket, int doctorID) {
        try {
            JSONObject object = new JSONObject();
//            object.put("type", "candidate");
            object.put("sdpMLineIndex", iceCandidate.sdpMLineIndex);
            object.put("sdpMid", iceCandidate.sdpMid);
            object.put("candidate", iceCandidate.sdp);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("toUserId", doctorID);
            jsonObject.put("candidate", object);
            socket.emit(Constant.TAG_RTC_CANDIDATE_SOCKET, jsonObject);
            Log.e("SignallingClient", "emitIceCandidate:  -----> "+jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void close() {
//        socket.emit("bye", roomName);
        socket.disconnect();
        socket.close();
    }


    public interface SignalingInterface {
        void onRemoteHangUp(String msg);

        void onOfferReceived(JSONObject data);

        void onAnswerReceived(JSONObject data);

        void onIceCandidateReceived(JSONObject data);

        void onTryToStart();

        void onCreatedRoom();

        void onJoinedRoom();

        void onNewPeerJoined();
    }

}
