package com.example.a17011066_alp_bintug_uzun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MenuEkrani extends AppCompatActivity {

    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
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
        findViewById(R.id.xd).setBackground(getResources().getDrawable(getResources().getIdentifier("avatar0","drawable",getPackageName())));

        ImageButton avatar = (ImageButton)findViewById(R.id.menu_ibuttonAvatar);
        Drawable drawableAvatar = getResources().getDrawable(getResources().getIdentifier(kullanici.getAvatar(),"drawable",getPackageName()));
        avatar.setBackground(drawableAvatar);
        TextView adSoyad = ((TextView)findViewById(R.id.menu_textWelcome));
        adSoyad.setText("Hoşgeldin, " + kullanici.getAd());
    }

    public void buttonSorular(View view){
        Intent menuAcma = new Intent(getApplicationContext(),SoruEkrani.class);
        startActivity(menuAcma);
    }
    public void buttonSinavlar(View view){
        Intent menuAcma = new Intent(getApplicationContext(),SinavEkrani.class);
        startActivity(menuAcma);

    }
    public void buttonCikisYap(View view){
        finish();

    }
}