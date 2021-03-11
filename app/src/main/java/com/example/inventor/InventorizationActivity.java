package com.example.inventor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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
                    Intent intent = new Intent(getApplicationContext(), InventorizationActivityStart.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    Log.d("Test", "erToJson Start: " + e);
                    e.printStackTrace();
                }
            }else {Log.d("Test", "erRequest Start: " + response.toString());}
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
