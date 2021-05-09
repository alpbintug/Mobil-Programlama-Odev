package com.example.a17011066_alp_bintug_uzun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class MenuEkrani extends AppCompatActivity {

    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        db = new DBHelper(this);
        Kullanici kullanici = new Kullanici(
                intent.getStringExtra(db.KULLANICI_AD),
                intent.getStringExtra(db.KULLANICI_SOYAD),
                intent.getStringExtra(db.KULLANICI_TELEFON),
                intent.getStringExtra(db.KULLANICI_EPOSTA),
                intent.getStringExtra(db.KULLANICI_SIFRE),
                intent.getStringExtra(db.KULLANICI_AVATAR)
        );


        //ImageButton avatar = (ImageButton)findViewById(R.id.navHeaderAvatar);
        //Drawable drawableAvatar = getResources().getDrawable(getResources().getIdentifier(kullanici.getAvatar(),"drawable",getPackageName()));
        //avatar.setBackground(drawableAvatar);
        //TextView adSoyad = ((TextView)findViewById(R.id.navHeaderName));
    }
}