package com.example.viewmeme;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MysqlDb {

    private List<Uri> datalist = new ArrayList<>();

    public MysqlDb() {
    }

    public void getImageUris(Context context, ImageUrisCallback callback) throws IOException, KeyStoreException, KeyManagementException, JSONException, CertificateException, NoSuchAlgorithmException {

        KeyStore selfsignKeys = KeyStore.getInstance("BKS");
        selfsignKeys.load(context.getResources().openRawResource(R.raw.selfsignedcertsbks), "penguwu".toCharArray());
        TrustManagerFactory trustMgr = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustMgr.init(selfsignKeys);
        SSLContext selfsignSSLcontext = SSLContext.getInstance("TLS");
        selfsignSSLcontext.init(null, trustMgr.getTrustManagers(), new SecureRandom());
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

// Tạo OkHttpClient và thiết lập SSL Socket Factory
        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(selfsignSSLcontext.getSocketFactory(), (X509TrustManager) trustMgr.getTrustManagers()[0])
                .hostnameVerifier(hostnameVerifier)
                .build();

        Request request = new Request.Builder()
                .url("https://10.0.139.42/android/v1/GetImage.php")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String jsonData = response.body().string();
                        JSONArray imageUrls = new JSONArray(jsonData);
                        for (int i = 0; i < imageUrls.length(); i++) {
                            JSONObject imageObject = imageUrls.getJSONObject(i);
                            String imageUrl = imageObject.getString("image_url");
                            try {

                                imageUrl = imageUrl.replace("\\/", "/");
                                URI uri = new URI(imageUrl);
                                Uri imageUri = Uri.parse(uri.toString());
                                datalist.add(imageUri);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (!datalist.isEmpty()) {
                                callback.onSuccess(datalist);
                            } else {
                                callback.onFailure("Empty image list");
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (!datalist.isEmpty()) {
                            callback.onSuccess(datalist);
                        } else {
                            callback.onFailure("Empty image list");
                        }
                    }
                });
            }
        });

    }
}


