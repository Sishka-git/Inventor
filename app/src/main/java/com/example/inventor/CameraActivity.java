package com.example.inventor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.Timer;

public class CameraActivity extends AppCompatActivity {

    SurfaceView cameraPreview;
    TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    AlertDialog.Builder ad;
    Boolean working = false;
    int target;
    Timer mTimer;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mTimer = new Timer();

        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
        txtResult = (TextView) findViewById(R.id.txtResult);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();
        //Add Event
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission
                    ActivityCompat.requestPermissions(CameraActivity.this,
                            new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if(qrcodes.size() != 0)
                {
                    txtResult.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            //Create vibrate
                            //Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            //vibrator.vibrate(1000);
///////////////////////////////  qrcodes.valueAt(0).displayValue
                            target = MainActivity.searhItemByInventorNum(qrcodes.valueAt(0).displayValue);

                                if (target != -1) {
                                    if (MainActivity.CameraMode) {
                                        if (MainActivity.items[target].status == 1) {
                                            txtResult.setText("Предмет проверен, инвентарный номер: " + qrcodes.valueAt(0).displayValue);
                                        } else {
                                            txtResult.setText("Предмет найден, инвентарный номер: " + qrcodes.valueAt(0).displayValue);
                                            MainActivity.items[target].status = 1;
                                        }
                                    } else {
                                        if(!working) {
                                            working = true;
                                            if (MainActivity.items[target].owner.equals("Артём")) {
                                                AlertSettingsMyItem();
                                                ad.show();
                                            } else {
                                                AlertSettingsItem();
                                                ad.show();
                                            }
                                        }
                                    }
                                } else {
                                    txtResult.setText("Предмет не найдет: " + qrcodes.valueAt(0).displayValue);
                                    //mTimer.schedule(new TimerTask() {
                                    //    @Override
                                    //   public void run() {
                                    //       txtResult.setText("Please focus camera to QR code");
                                    //    }
                                    //}, 5000);
                                }
                            }
////////////////////////////////////
                    });
                }
            }
        });
    }

    private  void AlertSettingsMyItem(){
        ad = new AlertDialog.Builder(CameraActivity.this);
        ad.setTitle("Объект: " + MainActivity.items[target].name);
        ad.setMessage("Что вы хотите сделать?");
        ad.setPositiveButton("Изменить Месторасположения", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Intent intent = new Intent(getApplicationContext(), ChangeLocationActivity.class);
                intent.putExtra("target", target);
                working = false;
                startActivity(intent);
            }
        });
        ad.setNegativeButton("Передать предмет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Intent intent = new Intent(getApplicationContext(), ChangeOwnerActivity.class);
                intent.putExtra("target", target);
                working = false;
                startActivity(intent);
            }
        });
        ad.setNeutralButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                working = false;
            }
        });
        ad.setCancelable(true);
    }

    private  void AlertSettingsItem(){
        ad = new AlertDialog.Builder(CameraActivity.this);
        ad.setTitle("Объект: " + MainActivity.items[target].name);
        ad.setMessage("Хотите забрать предмет в личное пользование?");
        ad.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                MainActivity.items[target].owner = "Артём";
                working = false;
            }
        });
        ad.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                working = false;
            }
        });
        ad.setCancelable(true);
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
