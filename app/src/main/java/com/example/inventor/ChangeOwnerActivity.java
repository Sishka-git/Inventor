package com.example.inventor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChangeOwnerActivity extends AppCompatActivity {
    private String[] Clients = MockDataBaseClients();
    private int target;
    private Spinner spinner;

    private String[] MockDataBaseClients(){
        // тут ещё убрать комнату в которой она сейчас
        return new String[]{"Андрей", "Павел Николаевич", "Артём"};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_owner);

        spinner = findViewById(R.id.spinner);

        target = getIntent().getExtras().getInt("target");

        TextView location = (TextView) findViewById(R.id.current_owner);
        TextView name = (TextView) findViewById(R.id.item_name);
        location.setText(MainActivity.items[target].owner);
        name.setText(MainActivity.items[target].name);

        List<String> list = new ArrayList<>(Arrays.asList(Clients));
        list.remove(MainActivity.items[target].owner);
        Clients = list.toArray(new String[list.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Clients);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }
    public void  Change(View v){
        MainActivity.items[target].owner = spinner.getSelectedItem().toString();
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
