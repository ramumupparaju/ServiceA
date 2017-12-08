package com.incon.service.api;

import com.google.gson.GsonBuilder;
import com.incon.service.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.incon.service.AppConstants.BUILD_FLAVOR;


public class ServiceGenerator {

    //Network constants
    private static final int TIMEOUT_CONNECT = 30;   //In seconds
    private static final int TIMEOUT_READ = 30;   //In seconds

    private static final String CONTENT_TYPE = "Content-ModelType";
    private static final String CONTENT_TYPE_VALUE = "application/json";
    private AppServiceObservable connectService;

    private OkHttpClient.Builder okHttpBuilder;
    private Retrofit retrofit;


    public ServiceGenerator(String url) {

        this.okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(headerInterceptor);
        okHttpBuilder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(TIMEOUT_READ, TimeUnit.SECONDS);

        if (BuildConfig.FLAVOR.equals(BUILD_FLAVOR)) {
            // used to print logs
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpBuilder.interceptors().add(logging);
        }

        connectService = createService(AppServiceObservable.class, url);
    }

    public AppServiceObservable getConnectService() {
        return connectService;
    }

    public <S> S createService(Class<S> serviceClass, String baseUrl) {

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl).client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        return retrofit.create(serviceClass);

    }


    private Interceptor headerInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request original = chain.request();

            Request request = original.newBuilder()
                    .header(CONTENT_TYPE, CONTENT_TYPE_VALUE)
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        }
    };

}
