package com.example.inventor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class All_Items extends AppCompatActivity {
    ItemModel[] items = MainActivity.items;
    String search = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_items);
        AddList(items);
    }

    //Вывод списка
    private void AddList(ItemModel[] arr){
        LinearLayout wrap_content =(LinearLayout) findViewById(R.id.wrap_content);
        float textSize = 20;
        for (ItemModel item : arr){
            if(search.equals("") && item.name.contains(search)) {
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
