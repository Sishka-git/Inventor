package com.example.inventor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EditProfileActivity extends AppCompatActivity {
    TextView email;
    EditText name;
    EditText secondName;
    String FirstName;
    String SecondName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        MainActivity.req.getUser(GetUser);

    }

    private final Callback GetUser = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("Test", "bad request GetUser: " + e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if(response.isSuccessful()) {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    EditProfileActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            email = (TextView) findViewById(R.id.email_value);
                            name = (EditText) findViewById(R.id.name_value);
                            secondName = (EditText) findViewById(R.id.second_name_value);

                            try {
                                FirstName = json.getString("firstName");
                                SecondName = json.getString("lastName");
                                email.setHint(json.getString("username"));
                                name.setHint(FirstName);
                                secondName.setHint(SecondName);
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

    private final Callback SaveEditCL = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("Test", "bad request SaveEditCL: " + e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if(response.isSuccessful()) {
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
            }else {Log.d("Test", "erRequest SaveEditCL: " + response.toString());}
        }
    };

    public void SaveEdit(View v){
        if(!name.getText().toString().equals(""))
            FirstName = name.getText().toString();
        if(!secondName.getText().toString().equals(""))
            SecondName = secondName.getText().toString();
        MainActivity.req.EditUser(SaveEditCL, FirstName, SecondName);
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

    public void GoToInventorization(View v){
        Intent intent = new Intent(getApplicationContext(), InventorizationActivity.class);
        startActivity(intent);
        finish();
    }
}
