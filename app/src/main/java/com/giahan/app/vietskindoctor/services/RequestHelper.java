package com.giahan.app.vietskindoctor.services;

import android.content.Context;

import com.giahan.app.vietskindoctor.BuildConfig;
import com.giahan.app.vietskindoctor.VietSkinDoctorApplication;
import com.giahan.app.vietskindoctor.network.ConnectivityInterceptor;
import com.giahan.app.vietskindoctor.utils.PrefHelper_;
import com.giahan.app.vietskindoctor.utils.Toolbox;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by FRAMGIA\pham.duc.nam on 17/05/2018.
 */

public class RequestHelper {
    public static APIService getRequest(boolean isMedia, Context context) {
        return getRequest(true, isMedia, context);
    }

    public static APIService getRequest(boolean isUseAuthentication, boolean isMedia, Context context) {
        HttpLoggingInterceptor interceptor = getHttpLoggingInterceptor();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(interceptor);
        httpClient.addInterceptor(new ConnectivityInterceptor(context));
        if (isUseAuthentication) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .build();
                    PrefHelper_ pref = new PrefHelper_(VietSkinDoctorApplication.getInstance());
                    if (!Toolbox.isEmpty(pref.token().get())) {
                        Request requestPre = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .header("Access-Token", pref.token().get())
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(requestPre);
                    } else {
                        return chain.proceed(request);
                    }
                }
            });
        }
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Boolean.class, new BooleanTypeAdapter());
        Gson gson = builder.create();
        OkHttpClient client = httpClient.build();
        Retrofit retrofit =
                new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                        .baseUrl(Secure.self().getBaseUrlApi(isMedia))
                        .client(client)
                        .build();

        return retrofit.create(APIService.class);
    }

    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : Level.BODY);
        return interceptor;
    }

    static class BooleanTypeAdapter implements JsonDeserializer<Boolean> {
        public Boolean deserialize(JsonElement json, Type typeOfT,
                                   JsonDeserializationContext context) throws JsonParseException {
            int code = json.getAsInt();
            return code == 0 ? false : code == 1 ? true : null;
        }
    }
}


