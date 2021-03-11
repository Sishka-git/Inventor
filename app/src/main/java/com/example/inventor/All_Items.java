package com.example.inventor;

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

public class All_Items extends AppCompatActivity {
    ItemModel[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_items);
        MainActivity.req.GetItems(GetItems);
    }
    private final Callback GetItems = new Callback() {
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


                All_Items.this.runOnUiThread(new Runnable() {
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

    private void AddList(ItemModel[] arr){
        LinearLayout wrap_content =(LinearLayout) findViewById(R.id.wrap_content);
        float textSize = 20;
        for (ItemModel item : arr){
            LinearLayout wrap_item = new LinearLayout(getApplicationContext());
            wrap_item.setOrientation(LinearLayout.VERTICAL);
            TextView name = new TextView(getApplicationContext());
            TextView location = new TextView(getApplicationContext());
            TextView inventNum = new TextView(getApplicationContext());
            TextView owner = new TextView(getApplicationContext());
            TextView description = new TextView(getApplicationContext());
            TextView line = new TextView(getApplicationContext());

            name.setTextSize(textSize);
            location.setTextSize(textSize);
            inventNum.setTextSize(textSize);
            owner.setTextSize(textSize);
            description.setTextSize(textSize);

            name.setText("Name: " + item.name);
            location.setText("Location: " + item.location);
            inventNum.setText("Inventorization Number: " + item.inventNum);
            owner.setText("Owner: " + item.owner);
            description.setText("Description: " + item.description);
            line.setBackgroundColor(Color.BLACK);
            line.setHeight(3);

            wrap_item.addView(name);
            wrap_item.addView(location);
            wrap_item.addView(inventNum);
            wrap_item.addView(owner);
            wrap_item.addView(description);
            wrap_item.addView(line);

            wrap_content.addView(wrap_item);
        }
    }

    public  void  GoToMyList(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
