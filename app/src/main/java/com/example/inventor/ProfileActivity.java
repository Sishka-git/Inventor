package com.example.inventor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {
    TextView email;
    TextView name;
    TextView secondName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        MainActivity.req.getUser(GetUser);

    }
    private Callback GetUser = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("Test", "bad request GetUser: " + e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if(response.isSuccessful()) {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    Log.d("test", json.toString());
                    ProfileActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            email = (TextView) findViewById(R.id.email_value);
                            name = (TextView) findViewById(R.id.name_value);
                            secondName = (TextView) findViewById(R.id.second_name_value);

                            try {
                                email.setText(json.getString("username"));
                                name.setText(json.getString("firstName"));
                                secondName.setText(json.getString("lastName"));
                            } catch (JSONException e) {
                                Log.d("Test", "erToJson GetUser: " + e);
                                e.printStackTrace();
                            }

                        }
                    });
                } catch (JSONException e) {
                    Log.d("Test", "erToJson GetUser: " + e);
                    e.printStackTrace();
                }

            }else {Log.d("Test", "erRequest GetUser: " + response.toString());}
        }
    };


    public void GoToEdit(View v){
        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        startActivity(intent);
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

    public void GoToInventorization(View v){
        Intent intent = new Intent(getApplicationContext(), InventorizationActivity.class);
        startActivity(intent);
        finish();
    }
}
