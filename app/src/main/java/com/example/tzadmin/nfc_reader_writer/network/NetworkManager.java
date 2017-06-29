package com.example.tzadmin.nfc_reader_writer.network;

import android.util.Log;
import com.example.tzadmin.nfc_reader_writer.Models.Event;
import com.example.tzadmin.nfc_reader_writer.Models.Group;
import com.example.tzadmin.nfc_reader_writer.Models.GroupActivity;
import com.example.tzadmin.nfc_reader_writer.Models.MoneyLogs;
import com.example.tzadmin.nfc_reader_writer.Models.Morda;
import com.example.tzadmin.nfc_reader_writer.Models.Route;
import com.example.tzadmin.nfc_reader_writer.Models.Shop;
import com.example.tzadmin.nfc_reader_writer.Models.User;
import com.example.tzadmin.nfc_reader_writer.Models.UserMorda;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpResponse;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * This class send requests to server and return callbacks
 */
public final class NetworkManager {
    private static final CopyOnWriteArraySet<ErrorHandler> errorHandlers = new CopyOnWriteArraySet<>();

    private static final Type shopListType = new TypeToken<List<Shop>>(){}.getType();
    private static final Type eventListType = new TypeToken<List<Event>>(){}.getType();
    private static final Type mordaListType = new TypeToken<List<Morda>>(){}.getType();
    private static final Type groupListType = new TypeToken<List<Group>>(){}.getType();
    private static final Type routeListType = new TypeToken<List<Route>>(){}.getType();
    private static final Type userListType = new TypeToken<List<User>>(){}.getType();
    private static final Type moneyListType = new TypeToken<List<MoneyLogs>>(){}.getType();
    private static final Type priorityListType = new TypeToken<List<GroupActivity>>(){}.getType();
    private static final Type userMordaListType = new TypeToken<List<UserMorda>>(){}.getType();

    private static final String apiUrl = "http://194.67.194.82/sync/";

    private static final String shopURL = apiUrl + "get-shop";
    private static final String eventURL = apiUrl + "get-events";
    private static final String mordaURL = apiUrl + "get-morda";
    private static final String groupURL = apiUrl + "get-group";
    private static final String routeURL = apiUrl + "get-route";
    private static final String userURL = apiUrl + "get-user";
    private static final String moneyURL = apiUrl + "get-money";
    private static final String priorityURL = apiUrl + "get-priority";
    private static final String userMordaURL = apiUrl + "get-user-morda";

    private static final String addUserUrl = apiUrl + "add-user";
    private static final String addPriorityUrl = apiUrl + "add-priority";
    private static final String addMoneyUrl = apiUrl + "add-money";
    private static final String addUserMordaUrl = apiUrl + "add-user-morda";

    private static final String setUserUrl = apiUrl + "set-user";
    private static final String setPriorityUrl = apiUrl + "set-priority";
    private static final String setMoneyUrl = apiUrl + "set-money";
    private static final String setUserMordaUrl = apiUrl + "set-user-morda";

    private NetworkManager() {
        throw new UnsupportedOperationException();
    }

    public static void addErrorHandler(ErrorHandler handler) {
        errorHandlers.add(handler);
    }

    public static void removeErrorhandler(ErrorHandler handler) {
        errorHandlers.remove(handler);
    }

    public static void getShops(Callback<List<Shop>> callback) {
        RequestManager.get(shopURL, new JsonParseCallback<>(callback, shopListType));
    }

    public static void getEvents(Callback<List<Event>> callback) {
        RequestManager.get(eventURL, new JsonParseCallback<>(callback, eventListType));
    }

    public static void getMordas(Callback<List<Morda>> callback) {
        RequestManager.get(mordaURL, new JsonParseCallback<>(callback, mordaListType));
    }

    public static void getGroups(Callback<List<Group>> callback) {
        RequestManager.get(groupURL, new JsonParseCallback<>(callback, groupListType));
    }

    public static void getRoutes(Callback<List<Route>> callback) {
        RequestManager.get(routeURL, new JsonParseCallback<>(callback, routeListType));
    }

    public static void getUsers(Callback<List<User>> callback) {
        RequestManager.get(userURL, new JsonParseCallback<>(callback, userListType));
    }

    public static void getMoney(Callback<List<MoneyLogs>> callback) {
        RequestManager.get(moneyURL, new JsonParseCallback<>(callback, moneyListType));
    }

    public static void getPriority(Callback<List<GroupActivity>> callback) {
        RequestManager.get(priorityURL, new JsonParseCallback<>(callback, priorityListType));
    }

    public static void getUserMordas(Callback<List<UserMorda>> callback) {
        RequestManager.get(userMordaURL, new JsonParseCallback<>(callback, userMordaListType));
    }

    private static void reportError(int code, AsyncHttpResponse response) {
        for(ErrorHandler errorHandler : errorHandlers) {
            try {
                errorHandler.handleHttpError(code, response);
            } catch (Exception e) {
                Log.e("[NetworkManager]", "Detected exception in ErrorHandler!");
                e.printStackTrace();
            }
        }
    }

    private static final class JsonParseCallback<T> extends AsyncHttpClient.StringCallback {
        private final Callback<T> callback;
        private final Type cast;

        public JsonParseCallback(Callback<T> callback, Type cast) {
            this.callback = callback;
            this.cast = cast;
        }

        @Override
        public void onCompleted(Exception e, AsyncHttpResponse source, String result) {
            if(source.code() != 200) {
                NetworkManager.reportError(source.code(), source);
                return;
            }

            T res = JSON.fromString(result, cast);
            callback.receive(res);
        }
    }
}