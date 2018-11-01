//package com.giahan.app.vietskindoctor.utils;
//
//import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.os.Handler;
//import android.os.Looper;
//import android.support.annotation.Nullable;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.Registry;
//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
//import com.bumptech.glide.load.engine.GlideException;
//import com.bumptech.glide.load.model.GlideUrl;
//import com.bumptech.glide.module.AppGlideModule;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.RequestOptions;
//import com.bumptech.glide.request.target.Target;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.Buffer;
//import java.util.HashMap;
//import java.util.Map;
//import javax.sql.DataSource;
//import okhttp3.HttpUrl;
//import okhttp3.Interceptor;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//import okio.BufferedSource;
//import okio.ForwardingSource;
//import okio.Okio;
//import okio.Source;
//
//public class GlideImageLoader {
//    private ImageView mImageView;
//    private ProgressBar mProgressBar;
//
//    public GlideImageLoader(ImageView imageView, ProgressBar progressBar) {
//        mImageView = imageView;
//        mProgressBar = progressBar;
//    }
//
//    public void load(final String url, RequestOptions options) {
//        if (url == null || options == null) return;
//
//        onConnecting();
//
//        //set Listener & start
//        ProgressAppGlideModule.expect(url, new ProgressAppGlideModule.UIonProgressListener() {
//            @Override
//            public void onProgress(long bytesRead, long expectedLength) {
//                if (mProgressBar != null) {
//                    mProgressBar.setProgress((int) (100 * bytesRead / expectedLength));
//                }
//            }
//
//            @Override
//            public float getGranualityPercentage() {
//                return 1.0f;
//            }
//        });
//        //Get Image
//        Glide.with(mImageView.getContext())
//                .load(url)
//                .transition(withCrossFade())
//                .apply(options.skipMemoryCache(true))
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        ProgressAppGlideModule.forget(url);
//                        onFinished();
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(final Drawable resource, final Object model,
//                            final Target<Drawable> target,
//                            final com.bumptech.glide.load.DataSource dataSource, final boolean isFirstResource) {
//                        ProgressAppGlideModule.forget(url);
//                        onFinished();
//                        return false;
//                    }
//                })
//                .into(mImageView);
//    }
//
//
//    private void onConnecting() {
//        if (mProgressBar != null) mProgressBar.setVisibility(View.VISIBLE);
//    }
//
//    private void onFinished() {
//        if (mProgressBar != null && mImageView != null) {
//            mProgressBar.setVisibility(View.GONE);
//            mImageView.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @GlideModule
//    public static class ProgressAppGlideModule extends AppGlideModule {
//
//        @Override
//        public void registerComponents(Context context, Glide glide, Registry registry) {
//            super.registerComponents(context, glide, registry);
//            OkHttpClient client = new OkHttpClient.Builder()
//                    .addNetworkInterceptor(new Interceptor() {
//                        @Override
//                        public Response intercept(Chain chain) throws IOException {
//                            Request request = chain.request();
//                            Response response = chain.proceed(request);
//                            ResponseProgressListener listener = new DispatchingProgressListener();
//                            return response.newBuilder()
//                                    .body(new OkHttpProgressResponseBody(request.url(), response.body(), listener))
//                                    .build();
//                        }
//                    })
//                    .build();
//            registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
//        }
//
//        public static void forget(String url) {
//            ProgressAppGlideModule.DispatchingProgressListener.forget(url);
//        }
//        public static void expect(String url, ProgressAppGlideModule.UIonProgressListener listener) {
//            ProgressAppGlideModule.DispatchingProgressListener.expect(url, listener);
//        }
//
//        private interface ResponseProgressListener {
//            void update(HttpUrl url, long bytesRead, long contentLength);
//        }
//
//        public interface UIonProgressListener {
//            void onProgress(long bytesRead, long expectedLength);
//            /**
//             * Control how often the listener needs an update. 0% and 100% will always be dispatched.
//             * @return in percentage (0.2 = call {@link #onProgress} around every 0.2 percent of progress)
//             */
//            float getGranualityPercentage();
//        }
//
//        private static class DispatchingProgressListener implements ProgressAppGlideModule.ResponseProgressListener {
//            private static final Map<String, UIonProgressListener> LISTENERS = new HashMap<>();
//            private static final Map<String, Long> PROGRESSES = new HashMap<>();
//
//            private final Handler handler;
//
//            DispatchingProgressListener() {
//                this.handler = new Handler(Looper.getMainLooper());
//            }
//
//            static void forget(String url) {
//                LISTENERS.remove(url);
//                PROGRESSES.remove(url);
//            }
//
//            static void expect(String url, UIonProgressListener listener) {
//                LISTENERS.put(url, listener);
//            }
//
//            @Override
//            public void update(HttpUrl url, final long bytesRead, final long contentLength) {
//                //System.out.printf("%s: %d/%d = %.2f%%%n", url, bytesRead, contentLength, (100f * bytesRead) / contentLength);
//                String key = url.toString();
//                final UIonProgressListener listener = LISTENERS.get(key);
//                if (listener == null) {
//                    return;
//                }
//                if (contentLength <= bytesRead) {
//                    forget(key);
//                }
//                if (needsDispatch(key, bytesRead, contentLength, listener.getGranualityPercentage())) {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            listener.onProgress(bytesRead, contentLength);
//                        }
//                    });
//                }
//            }
//
//            private boolean needsDispatch(String key, long current, long total, float granularity) {
//                if (granularity == 0 || current == 0 || total == current) {
//                    return true;
//                }
//                float percent = 100f * current / total;
//                long currentProgress = (long) (percent / granularity);
//                Long lastProgress = PROGRESSES.get(key);
//                if (lastProgress == null || currentProgress != lastProgress) {
//                    PROGRESSES.put(key, currentProgress);
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        }
//
//        private static class OkHttpProgressResponseBody extends ResponseBody {
//            private final HttpUrl url;
//            private final ResponseBody responseBody;
//            private final ResponseProgressListener progressListener;
//            private BufferedSource bufferedSource;
//
//            OkHttpProgressResponseBody(HttpUrl url, ResponseBody responseBody,
//                    ResponseProgressListener progressListener) {
//                this.url = url;
//                this.responseBody = responseBody;
//                this.progressListener = progressListener;
//            }
//
//            @Override
//            public MediaType contentType() {
//                return responseBody.contentType();
//            }
//
//            @Override
//            public long contentLength() {
//                return responseBody.contentLength();
//            }
//
//            @Override
//            public BufferedSource source() {
//                if (bufferedSource == null) {
//                    bufferedSource = Okio.buffer(source(responseBody.source()));
//                }
//                return bufferedSource;
//            }
//
//            private Source source(Source source) {
//                return new ForwardingSource(source) {
//                    long totalBytesRead = 0L;
//
//                    @Override
//                    public long read(Buffer sink, long byteCount) throws IOException {
//                        long bytesRead = super.read(sink, byteCount);
//                        long fullLength = responseBody.contentLength();
//                        if (bytesRead == -1) { // this source is exhausted
//                            totalBytesRead = fullLength;
//                        } else {
//                            totalBytesRead += bytesRead;
//                        }
//                        progressListener.update(url, totalBytesRead, fullLength);
//                        return bytesRead;
//                    }
//                };
//            }
//        }
//    }
//
//}
