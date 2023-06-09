package com.example.camera2;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView imageViewFoto;
    private Button btnGeo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGeo = (Button) findViewById(R.id.btn_gps);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);

        btnGeo.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                GPSTracker g = new GPSTracker(getApplication());
                Location l = g.getLocation();

                if (l != null){
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    Toast.makeText(getApplicationContext(), "LATUTUDE" + lat + "/nLONGITUDE" + lon, Toast.LENGTH_LONG).show();
                }

            }
        });

        //checar se a permissão para utilizar a camera foi concedida
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }
        //configura a exibição da foto
        imageViewFoto = (ImageView) findViewById(R.id.imageView2);
        findViewById(R.id.btn_pic).setOnClickListener(new View.OnClickListener(){

            //metodo que será executado ao clicar no botão de tirar foto
            @Override
            public void onClick(View view) {tirarFoto();}

        });
    }
     //metodo para tirar a foto
    private void tirarFoto() {
        Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
        startActivityForResult(intent, 1);

    }
     //depois que a foto for tirada, vai exibir o resultado
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imagem = (Bitmap) extras.get("data");
            imageViewFoto.setImageBitmap(imagem);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

