package com.example.inventor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class InventorizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventorization);
        if (MainActivity.CameraMode){
            Intent intent = new Intent(getApplicationContext(), InventorizationActivityStart.class);
            startActivity(intent);
        }
    }

    private Callback Start = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("Test", "bad request Login: " + e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if(response.isSuccessful()) {

                try {
                    JSONObject json = new JSONObject(response.body().string());
                    Log.d("Test", "Id inventorization is : " + json.getString("id"));
                    MainActivity.idInventory = json.getInt("id");
                    MainActivity.req.InventoryList(InvList, MainActivity.idInventory);
                } catch (JSONException e) {
                    Log.d("Test", "erToJson Start: " + e);
                    e.printStackTrace();
                }
            }else {Log.d("Test", "erRequest Start: " + response.toString());}
        }
    };

    private final Callback InvList = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("Test", "bad request GetUser: " + e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                //достаём отправленные данные
                try {
                    JSONArray itemsJson = new JSONArray(response.body().string());
                    ItemModel[] list = new ItemModel[itemsJson.length()];

                    for (int i = 0; i < itemsJson.length(); i++) {
                        JSONObject item = itemsJson.getJSONObject(i);
                        //"roomId":1 "userId":1////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        list[i] = new ItemModel(item.getInt("id"),item.getString("name"), "Пока заглушка для location", item.getInt("inventoryNumber"),"Пока загушка для owner", item.getString("description"));
                    }
                    InventorizationActivityStart.items = list;
                    Log.d("test", itemsJson.toString());
                    Intent intent = new Intent(getApplicationContext(), InventorizationActivityStart.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Log.d("Test", "erRequest InvList: " + response.toString());
            }
        }
    };

    public void GoToStart(View v){
        MainActivity.CameraMode = true;
        MainActivity.req.StartInventory(Start);
    }

    public  void  GoToMyList(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void GoToAllList(View v){
        Intent intent = new Intent(getApplicationContext(), All_Items.class);
        startActivity(intent);
        finish();
    }

    public void GoToCamera(View v){
        Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
        startActivity(intent);
        finish();
    }

    public void GoToProfile(View v){
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
        finish();
    }

}
