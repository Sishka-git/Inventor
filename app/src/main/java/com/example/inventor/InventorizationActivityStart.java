package com.example.inventor;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class InventorizationActivityStart extends AppCompatActivity{
    static ItemModel[] items;
    AlertDialog.Builder ad;
    int target;
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
    private final Callback Stop = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("Test", "bad request Stop: " + e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                MainActivity.CameraMode = false;
                Intent intent = new Intent (getApplicationContext(), InventorizationActivity.class);
                startActivity(intent);
                finish();
            } else {
                Log.d("Test", "erRequest Stop: " + response.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventorization_start);
        AddList(items);
        AlertSettings();
    }

    private  void AlertSettings(){
        ad = new AlertDialog.Builder(InventorizationActivityStart.this);
        ad.setTitle("Проблема с нахождением");
        ad.setMessage("Добавить предмет в ненайденные?");
        ad.setPositiveButton("Да", new OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                items[target].status = 2;
                LinearLayout ln = (LinearLayout) findViewById(R.id.wrap_content);
                ln.removeAllViews();
                AddList(items);
            }
        });
        ad.setNegativeButton("Нет", new OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
            }
        });
        ad.setCancelable(true);
    }
    //Вывод списка
    private void AddList(ItemModel[] arr){
        LinearLayout wrap_content = (LinearLayout) findViewById(R.id.wrap_content);
        float textSize = 20;
        int count = 0;
        int id = 0;
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


            wrap_item.setId(id++);
            wrap_item.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    if (items[v.getId()].status != 1) {
                        target = v.getId();
                        ad.show();
                    }
                    return false;
                }
            });

            wrap_item.addView(name);
            wrap_item.addView(location);
            wrap_item.addView(inventNum);
            wrap_item.addView(owner);
            wrap_item.addView(description);
            wrap_item.addView(line);
            if(item.status != 0){
                int color = (item.status == 1) ? Color.GREEN : Color.RED;
                count++;
                wrap_item.setBackgroundColor(color);
            }
            wrap_content.addView(wrap_item);
        }
        Button button = new Button(getApplicationContext());
        button.setText("Остановить инвентаризацию");
        button.setOnClickListener(this::StopInventorization);
        button.setBackgroundColor(Color.BLUE);
        button.setTextColor(Color.WHITE);
        wrap_content.addView(button);
        if (count == arr.length){
            wrap_content.removeAllViews();
            button.setText("Инвентаризация окончена");

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button.getLayoutParams();
            params.topMargin = 250;
            wrap_content.addView(button);
        }
    }

    public  void StopInventorization(View v){
        MainActivity.req.EndInventory(Stop, MainActivity.idInventory);
    }

    public  void  GoToMyList(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void GoToCamera(View v){
        Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
        startActivity(intent);
    }

    public void GoToAllList(View v){
        Intent intent = new Intent(getApplicationContext(), All_Items.class);
        startActivity(intent);
    }

    public void GoToProfile(View v){
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }

    public void GoToInventorization(View v){
        Intent intent = new Intent(getApplicationContext(), InventorizationActivity.class);
        startActivity(intent);
        finish();

    }


}
