package com.example.inventor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChangeLocationActivity extends AppCompatActivity {
    private String[] Rooms = MockDataBaseRooms();
    private int target;
    private Spinner spinner;

    private String[] MockDataBaseRooms(){
        // тут ещё убрать комнату в которой она сейчас
        return new String[]{"Офис N322", "Склад", "Офис N214"};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_location);

        spinner = findViewById(R.id.spinner);

        target = getIntent().getExtras().getInt("target");

        TextView location = (TextView) findViewById(R.id.current_location);
        TextView name = (TextView) findViewById(R.id.item_name);
        location.setText(MainActivity.items[target].location);
        name.setText(MainActivity.items[target].name);

        List<String> list = new ArrayList<>(Arrays.asList(Rooms));
        list.remove(MainActivity.items[target].location);
        Rooms = list.toArray(new String[list.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Rooms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }
    public void  Change(View v){
        MainActivity.items[target].location = spinner.getSelectedItem().toString();
        GoToCamera(v);
        finish();
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

    public void GoToAllList(View v){
        Intent intent = new Intent(getApplicationContext(), All_Items.class);
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
