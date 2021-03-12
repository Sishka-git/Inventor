package com.example.inventor;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

class ServerConnectionRequest {
    OkHttpClient client = new OkHttpClient();
    String ip = "192.168.0.8";//192.168.0.2
    String port = "52050";//55337
    public void login(Callback cl) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"username\":\"test@mail.ru\",\r\n    \"password\":\"test1\"\r\n}");
        Request request = new Request.Builder()
                .url("http://"+ip+":"+port+"/api/User/Login")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(cl);
    }

    public void getUser(Callback cl) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://"+ip+":"+port+"/api/User/GetUser")
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + MainActivity.token)
                .build();
        client.newCall(request).enqueue(cl);
    }

    public void InventoryList(Callback cl, int invId) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://"+ip+":"+port+"/api/Inventory/" + invId)
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + MainActivity.token)
                .build();
        client.newCall(request).enqueue(cl);
    }

    public void GetItems(Callback cl) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://"+ip+":"+port+"/api/Items/GetItems")
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + MainActivity.token)
                .build();
        client.newCall(request).enqueue(cl);
    }

    public void GetUserItems(Callback cl) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://"+ip+":"+port+"/api/Items/GetUserItems")
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + MainActivity.token)
                .build();
        client.newCall(request).enqueue(cl);
    }

    public void EditUser(Callback cl, String firstName, String secondName) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("firstName", firstName)
                .addFormDataPart("lastName", secondName)
                .build();
        Request request = new Request.Builder()
                .url("http://"+ip+":"+port+"/api/User/EditUser/1")
                .method("PUT", body)
                .addHeader("Authorization", "Bearer " + MainActivity.token)
                .build();
        client.newCall(request).enqueue(cl);
    }
    //1
    public void StartInventory(Callback cl) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://"+ip+":"+port+"/api/Inventory/StartInventory")
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + MainActivity.token)
                .build();
        client.newCall(request).enqueue(cl);
    }
    //2
    public void CheckItem(Callback cl, String invNum, int invId) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("inventoryNumber", invNum)
                .addFormDataPart("inventoryReportId", "" + invId)
                .build();
        Request request = new Request.Builder()
                .url("http://"+ip+":"+port+"/api/Inventory/CheckItem")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + MainActivity.token)
                .build();
        client.newCall(request).enqueue(cl);
    }
    //3
    public void EndInventory(Callback cl, int invId) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://"+ip+":"+port+"/api/Inventory/EndInventory/" + invId)
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + MainActivity.token)
                .build();
        client.newCall(request).enqueue(cl);
    }

}

