package com.example.inventor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    public static String token = "";
    public static ClientModel Client;
    public static Boolean CameraMode = false;
    //public static String url = "https://jsonplaceholder.typicode.com/todos/1"; //https://192.168.0.2:55337";
    String search = "";
    public static ServerConnectionRequest req = new ServerConnectionRequest();
    public static ItemModel[] items;
    public static int searhItemByInventorNum (String str){
        int count = 0;
        for (ItemModel item : items){
            if(Integer.toString(item.inventNum).equals(str)){
                return count;
            }
            count++;
        }

        return -1;//Не найден
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Client = new ClientModel(1, "Артём", "Артёмов", "Test@mail.ru");
        req.login(login);
    }

    @SuppressLint("SetTextI18n")
    private void AddList(ItemModel[] arr){
        LinearLayout wrap_content =(LinearLayout) findViewById(R.id.wrap_content);
        float textSize = 20;
        for (ItemModel item : arr){
            LinearLayout wrap_item = new LinearLayout(getApplicationContext());
            wrap_item.setOrientation(LinearLayout.VERTICAL);
            TextView name = new TextView(getApplicationContext());
            TextView location = new TextView(getApplicationContext());
            TextView inventNum = new TextView(getApplicationContext());
            TextView description = new TextView(getApplicationContext());
            TextView line = new TextView(getApplicationContext());

            name.setTextSize(textSize);
            location.setTextSize(textSize);
            inventNum.setTextSize(textSize);
            description.setTextSize(textSize);

            name.setText("Name: " + item.name);
            location.setText("Location: " + item.location);
            inventNum.setText("Inventorization Number: " + item.inventNum);
            description.setText("Description: " + item.description);
            line.setBackgroundColor(Color.BLACK);
            line.setHeight(3);

            wrap_item.addView(name);
            wrap_item.addView(location);
            wrap_item.addView(inventNum);
            wrap_item.addView(description);
            wrap_item.addView(line);

            wrap_content.addView(wrap_item);
        }
    }

    private Callback login = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("Test", "bad request Login: " + e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if(response.isSuccessful()) {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    Log.d("Test", "Token is : " + json.getString("token"));
                    token = json.getString("token");
                    req.GetUserItems(ListMyItems);
                } catch (JSONException e) {
                    Log.d("Test", "erToJson Login: " + e);
                    e.printStackTrace();
                }

            }else {Log.d("Test", "erRequest Login: " + response.toString());}
        }
    };
    //////////////////////////////////////////////////////////////////////////////////////////////////тут заглушка
    private final Callback ListMyItems = new Callback() {
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
                    items = list;
                    Log.d("test", itemsJson.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("test", "hmm");


                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AddList(items);
                        Log.d("Test", "living? GetUser");
                    }
                });
            } else {
                Log.d("Test", "erRequest GetUser: " + response.toString());
            }
        }
    };

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

    public void GoToInventorization(View v){
        Intent intent = new Intent(getApplicationContext(), InventorizationActivity.class);
        startActivity(intent);
        finish();
    }

}