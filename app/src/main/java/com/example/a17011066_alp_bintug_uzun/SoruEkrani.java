package com.example.a17011066_alp_bintug_uzun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SoruEkrani extends AppCompatActivity {

    Context context;
    LinearLayout layout;
    View viewToAdd;
    String kullaniciEposta;
    int[] rgb = {255,255,0};
    int alpha = 25;
    int lastTo255 = 0;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_soru_ekrani);
        layout = (LinearLayout)findViewById(R.id.soru_soruListesi);
        Intent intent = getIntent();
        context = getApplicationContext();
        db = new DBHelper(this);
        kullaniciEposta = intent.getStringExtra(db.KULLANICI_EPOSTA);
        ArrayList<Soru> sorular = db.sorulariGetir(kullaniciEposta);
        for (Soru soru: sorular ) {
            Log.d("herSeyiAyarladim","xd");

            viewToAdd = LayoutInflater.from(this).inflate(R.layout.sorular_liste_ogesi_metinli,null);

            int bgc = Color.argb(alpha,rgb[0],rgb[1],rgb[2]);
            if(rgb[(lastTo255+1)%3]!=255){
                rgb[(lastTo255+1)%3]+=15;
            }
            else if(rgb[lastTo255]>0){
                rgb[lastTo255]-=15;
            }
            else{
                lastTo255=(lastTo255+1)%3;
            }
            viewToAdd.findViewById(R.id.soru_cardView).setBackgroundColor(bgc);
            ((Button)viewToAdd.findViewById(R.id.button_soruEdit)).setText("Kaydet");

            ((TextView)viewToAdd.findViewById(R.id.txt_soruID)).setText(String.valueOf(soru.getSoruID()));
            ((EditText)viewToAdd.findViewById(R.id.soru_txtSoruMetni)).setText(soru.getSoruMetni());
            ((EditText)viewToAdd.findViewById(R.id.soru_txtSik0)).setText(soru.getSiklar()[0]);
            ((EditText)viewToAdd.findViewById(R.id.soru_txtSik1)).setText(soru.getSiklar()[1]);
            Log.d("herSeyiAyarladim","0");
            if(soru.getZorluk()>2)
            {
                ((EditText)viewToAdd.findViewById(R.id.soru_txtSik2)).setText(soru.getSiklar()[2]);
                if(soru.getZorluk()>3){
                    ((EditText)viewToAdd.findViewById(R.id.soru_txtSik3)).setText(soru.getSiklar()[3]);
                    if(soru.getZorluk()>4){
                        ((EditText)viewToAdd.findViewById(R.id.soru_txtSik4)).setText(soru.getSiklar()[4]);
                    }
                    else{
                        ((EditText)viewToAdd.findViewById(R.id.soru_txtSik4)).setHeight(0);
                        ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik4)).setHeight(0);
                    }
                }
                else{
                    ((EditText)viewToAdd.findViewById(R.id.soru_txtSik4)).setHeight(0);
                    ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik4)).setHeight(0);
                    ((EditText)viewToAdd.findViewById(R.id.soru_txtSik3)).setHeight(0);
                    ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik3)).setHeight(0);

                }
            }
            else{
                ((EditText)viewToAdd.findViewById(R.id.soru_txtSik4)).setHeight(0);
                ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik4)).setHeight(0);
                ((EditText)viewToAdd.findViewById(R.id.soru_txtSik3)).setHeight(0);
                ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik3)).setHeight(0);
                ((EditText)viewToAdd.findViewById(R.id.soru_txtSik2)).setHeight(0);
                ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik2)).setHeight(0);
            }

            layout.addView(viewToAdd);
        }

        int bgc = Color.argb(alpha,rgb[0],rgb[1],rgb[2]);
        if(rgb[(lastTo255+1)%3]!=255){
            rgb[(lastTo255+1)%3]+=15;
        }
        else if(rgb[lastTo255]>0){
            rgb[lastTo255]-=15;
        }
        else{
            lastTo255=(lastTo255+1)%3;
        }

        viewToAdd = LayoutInflater.from(this).inflate(R.layout.sorular_liste_ogesi_metinli,null);
        viewToAdd.findViewById(R.id.soru_cardView).setBackgroundColor(bgc);
        ((Button)viewToAdd.findViewById(R.id.button_soruEdit)).setText("Kaydet");
        layout.addView(viewToAdd);
    }

    public void buttonSoruDuzenle(View view){
        ViewGroup parentView = (ViewGroup)view.getParent();
        Log.d("xd","heheheh");
        Button duzenlemeButonu = (Button)parentView.findViewById(R.id.button_soruEdit);
        EditText soruMetni = (EditText)parentView.findViewById(R.id.soru_txtSoruMetni);
        EditText sik0 = (EditText)parentView.findViewById(R.id.soru_txtSik0);
        EditText sik1 = (EditText)parentView.findViewById(R.id.soru_txtSik1);
        EditText sik2 = (EditText)parentView.findViewById(R.id.soru_txtSik2);
        EditText sik3 = (EditText)parentView.findViewById(R.id.soru_txtSik3);
        EditText sik4 = (EditText)parentView.findViewById(R.id.soru_txtSik4);
        RadioGroup radioGroup_Siklar = (RadioGroup)parentView.findViewById(R.id.radbuttongr_siklar);
        TextView soruID = (TextView)parentView.findViewById(R.id.txt_soruID);

        Log.d(sik0.getText().toString(),"heheheh");
        ArrayList<String> siklar = new ArrayList<String>();
        if(sik0.getText().toString().length()!=0)
            siklar.add(sik0.getText().toString());
        if(sik1.getText().toString().length()!=0)
            siklar.add(sik1.getText().toString());
        if(sik2.getText().toString().length()!=0)
            siklar.add(sik2.getText().toString());
        if(sik3.getText().toString().length()!=0)
            siklar.add(sik3.getText().toString());
        if(sik4.getText().toString().length()!=0)
            siklar.add(sik4.getText().toString());
        Log.d("herSeyiAyarladim","heheheh");
        if(duzenlemeButonu.getText().toString().equals("Kaydet")){
            Log.d("ife girdim","heheheh");
            if(soruMetni.getText().length()==0 || siklar.size() <2){
                Toast.makeText(this,"Lütfen soru metnini giriniz ve en az iki adet şıkkı doldurunuz.",Toast.LENGTH_LONG).show();
                return;
            }
            Soru soru = new Soru(kullaniciEposta,soruMetni.getText().toString(),siklar.size(),siklar.toArray(new String[0]),Integer.valueOf(((RadioButton)parentView.findViewById(radioGroup_Siklar.getCheckedRadioButtonId())).getText().toString()));

            if(soruID.getText().toString().length()==0){
                db.soruEkle(soru);
            }
            else {
                soru.setSoruID(Integer.valueOf(soruID.getText().toString()));
                db.soruDuzenle(soru);
            }

            soruMetni.setEnabled(false);
            sik0.setEnabled(false);
            sik1.setEnabled(false);
            sik2.setEnabled(false);
            sik3.setEnabled(false);
            sik4.setEnabled(false);
            duzenlemeButonu.setText("Düzenle");
        }
        else{
            Log.d("else girdim","heheheh");
            soruMetni.setEnabled(true);

            Log.d("eneybılladım soru metnini","heheheh");
            sik0.setEnabled(true);
            Log.d("eneybılladım sik 0 metnini","heheheh");
            sik1.setEnabled(true);
            Log.d("eneybılladım sik 1 metnini","heheheh");
            sik2.setEnabled(true);
            Log.d("eneybılladım sik 2 metnini","heheheh");
            sik3.setEnabled(true);
            Log.d("eneybılladım sik 3 metnini","heheheh");
            sik4.setEnabled(true);
            Log.d("eneybılladım sik 4 metnini","heheheh");
            duzenlemeButonu.setText("Kaydet");
            Log.d("degistirdim buton metnini","heheheh");
        }
    }
}