package com.example.inventor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {
    EditText email;
    EditText name;
    EditText secondName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        email = (EditText) findViewById(R.id.email_value);
        name = (EditText) findViewById(R.id.name_value);
        secondName = (EditText) findViewById(R.id.second_name_value);

        email.setHint(MainActivity.Client.email);
        name.setHint(MainActivity.Client.name);
        secondName.setHint(MainActivity.Client.secondName);
    }

    public void SaveEdit(View v){
        if(!email.getText().toString().equals(""))
        MainActivity.Client.email = email.getText().toString();
        if(!name.getText().toString().equals(""))
        MainActivity.Client.name = name.getText().toString();
        if(!secondName.getText().toString().equals(""))
        MainActivity.Client.secondName = secondName.getText().toString();


        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
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
