package com.example.tzadmin.nfc_reader_writer.network;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.callback.HttpConnectCallback;

import java.util.concurrent.Future;

/**
 * This class contains {@link AsyncHttpClient} for app and provide GET/POST functions
 */
public final class RequestManager {
    private static final AsyncHttpClient client = AsyncHttpClient.getDefaultInstance();

    private static final HttpConnectCallback EMPTY_CALLBACK = new HttpConnectCallback() {
        @Override
        public void onConnectCompleted(Exception ex, AsyncHttpResponse response) {

        }
    };

    private RequestManager() {
        throw new UnsupportedOperationException();
    }

    public static Future<AsyncHttpResponse> post(String uri) {
        AsyncHttpPost post = new AsyncHttpPost(uri);
        return client.execute(post, EMPTY_CALLBACK);
    }

    public static Future<AsyncHttpResponse> get(String uri, HttpConnectCallback callback) {
        AsyncHttpGet get = new AsyncHttpGet(uri);
        return client.execute(get, callback);
    }

    public static Future<String> get(String uri, AsyncHttpClient.StringCallback callback) {
        AsyncHttpGet get = new AsyncHttpGet(uri);
        return client.executeString(get, callback);
    }
}