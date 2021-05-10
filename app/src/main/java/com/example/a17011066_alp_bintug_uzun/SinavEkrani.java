package com.example.a17011066_alp_bintug_uzun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SinavEkrani extends AppCompatActivity {

    Context context;
    CardView cardview;
    RelativeLayout.LayoutParams layoutparams;
    TextView textview;
    Button button;
    View view;

    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sinav_ekrani);

        context = getApplicationContext();
        layout = (LinearLayout)findViewById(R.id.sinav_sinavListesi);
        int[] rgb = {255,255,0};
        int lastTo255 = 0;
        for (int i = 0;i<250;i++){
            View li = LayoutInflater.from(this).inflate(R.layout.sinavlar_liste_ogesi,null);

            if(rgb[(lastTo255+1)%3]!=255){
                rgb[(lastTo255+1)%3]+=15;
            }
            else if(rgb[lastTo255]>0){
                rgb[lastTo255]-=15;
            }
            else{
                lastTo255=(lastTo255+1)%3;
            }

            int bgc = Color.argb(25,rgb[0],rgb[1],rgb[2]);

            li.findViewById(R.id.sinav_cardView).setBackgroundColor(bgc);
            layout.addView(li);
        }
    }
}