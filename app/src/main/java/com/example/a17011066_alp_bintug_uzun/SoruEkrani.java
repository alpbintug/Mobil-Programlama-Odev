package com.example.a17011066_alp_bintug_uzun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class SoruEkrani extends AppCompatActivity {

    Context context;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru_ekrani);

        context = getApplicationContext();
        layout = (LinearLayout)findViewById(R.id.soru_soruListesi);
        int[] rgb = {255,255,0};
        int lastTo255 = 0;
        for (int i = 0;i<250;i++){
            View li = LayoutInflater.from(this).inflate(R.layout.sorular_liste_ogesi_metinli,null);
            //region SORU ARKA PLAN RENGİ
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

            li.findViewById(R.id.soru_cardView).setBackgroundColor(bgc);
            //endregion

            //DOĞRU ŞIK RENGİ
            li.findViewById(getResources().getIdentifier("txtSik"+String.valueOf(i%5), "id", context.getPackageName())).setBackgroundColor(Color.argb(200,0,200,200));
            layout.addView(li);
        }
    }
}