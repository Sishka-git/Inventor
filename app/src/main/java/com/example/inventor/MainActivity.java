package com.example.inventor;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    public static ClientModel Client = new ClientModel(1, "Артём", "Артёмов", "Test@mail.ru");
    public static Boolean CameraMode = false;
    public static ItemModel[] items = MockDataBase();
    String search = "";

    public static int searhItemByInventorNum (String str){
        int count = 0;
        for (ItemModel item : items){
            if(Integer.toString(item.inventNum).equals(str)){
                return count;
            }
            count++;
        }

        return -1;//Не найнед
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AddList(items);
    }

    public static ItemModel[] MockDataBase(){
        return  new ItemModel[]{
                new ItemModel("Стол", "Склад", 1, "Андрей", "размер 2/2/1"),
                new ItemModel("Стул", "Склад", 2, "Андрей", "Просто стул"),
                new ItemModel("Ноутбук", "Офис N322", 3, "Артём", "Дорогой и быстрый"),
                new ItemModel("Бинокль", "Офис N322", 4, "", "Кому он вообще понадобился?"),
                new ItemModel("Стул", "Офис N322", 5, "Артём", "размер 2/2/1"),
                new ItemModel("Стол", "Офис N322", 6, "Артём", "Просто стул"),
                new ItemModel("Стол", "Офис N322", 7, "Павел Николаевич", "Просто стул"),
        };
    }

    @SuppressLint("SetTextI18n")
    private void AddList(ItemModel[] arr){
        LinearLayout wrap_content =(LinearLayout) findViewById(R.id.wrap_content);
        float textSize = 20;
        for (ItemModel item : arr){
            if(item.owner.equals(Client.name) && search.equals("") && item.name.contains(search)) {
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
