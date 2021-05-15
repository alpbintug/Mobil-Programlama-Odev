package com.example.a17011066_alp_bintug_uzun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SoruEkrani extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    Context context;
    LinearLayout layout;
    View viewToAdd;
    String kullaniciEposta;
    int[] rgb = {255,255,0};
    int alpha = 25;
    int lastTo255 = 0;
    int colorChange = 17;
    private DBHelper db;
    Uri imageURI;
    ImageView soruGorseli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_soru_ekrani);

        //region DEGISKENLERIN AYARLANMASI
        layout = (LinearLayout)findViewById(R.id.soru_soruListesi);
        Intent intent = getIntent();
        context = getApplicationContext();
        db = new DBHelper(this);
        kullaniciEposta = intent.getStringExtra(db.KULLANICI_EPOSTA);
        //endregion
        //region KULLANICI SORULARINI OLUSTURMA
        ArrayList<Soru> sorular = db.sorulariGetir(kullaniciEposta);
        sorulariYerlestir(sorular);
        //endregion
        //region BOS SORU PANOSU EKLEME
        int bgc = Color.argb(alpha,rgb[0],rgb[1],rgb[2]);
        if(rgb[(lastTo255+1)%3]!=255){
            rgb[(lastTo255+1)%3]+=colorChange;
        }
        else if(rgb[lastTo255]>0){
            rgb[lastTo255]-=colorChange;
        }
        else{
            lastTo255=(lastTo255+1)%3;
        }

        viewToAdd = LayoutInflater.from(this).inflate(R.layout.sorular_liste_ogesi_metinli,null);
        viewToAdd.findViewById(R.id.soru_cardView).setBackgroundColor(bgc);
        ((Button)viewToAdd.findViewById(R.id.button_soruEdit)).setText("Kaydet");
        layout.addView(viewToAdd);

        //endregion
    }

    public void sorulariYerlestir(ArrayList<Soru> sorular){
        for (Soru soru: sorular ) {

            viewToAdd = LayoutInflater.from(this).inflate(R.layout.sorular_liste_ogesi_metinli,null);

            //region ARKA PLAN RENKLERINI AYARLAMA
            int bgc = Color.argb(alpha,rgb[0],rgb[1],rgb[2]);
            if(rgb[(lastTo255+1)%3]!=255){
                rgb[(lastTo255+1)%3]+=colorChange;
            }
            else if(rgb[lastTo255]>0){
                rgb[lastTo255]-=colorChange;
            }
            else{
                lastTo255=(lastTo255+1)%3;
            }
            viewToAdd.findViewById(R.id.soru_cardView).setBackgroundColor(bgc);
            //endregion

            //region SORU PANOSUNU OLUSTURMA
            ((Button)viewToAdd.findViewById(R.id.button_soruEdit)).setText("Düzenle");
            ((TextView)viewToAdd.findViewById(R.id.txt_soruID)).setText(String.valueOf(soru.getSoruID()));
            ((EditText)viewToAdd.findViewById(R.id.soru_txtSoruMetni)).setText(soru.getSoruMetni());
            ((EditText)viewToAdd.findViewById(R.id.soru_txtSik0)).setText(soru.getSiklar()[0]);
            ((EditText)viewToAdd.findViewById(R.id.soru_txtSik1)).setText(soru.getSiklar()[1]);
            //endregion

            //region DOGRU SIKKI SECME
            if(soru.getDogruCevap()==0)
                ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik0)).setChecked(true);
            else if(soru.getDogruCevap()==1)
                ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik1)).setChecked(true);
            else if(soru.getDogruCevap()==2)
                ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik2)).setChecked(true);
            else if(soru.getDogruCevap()==3)
                ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik3)).setChecked(true);
            else
                ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik4)).setChecked(true);
            //endregion

            //region TERCIHEN EKLENEN SIKLARI OLUSTURMA
            if(soru.getZorluk()>2)
            {
                ((EditText)viewToAdd.findViewById(R.id.soru_txtSik2)).setText(soru.getSiklar()[2]);
                if(soru.getZorluk()>3){
                    ((EditText)viewToAdd.findViewById(R.id.soru_txtSik3)).setText(soru.getSiklar()[3]);
                    if(soru.getZorluk()>4){
                        ((EditText)viewToAdd.findViewById(R.id.soru_txtSik4)).setText(soru.getSiklar()[4]);
                    }
                    else{
                        ((EditText)viewToAdd.findViewById(R.id.soru_txtSik4)).setVisibility(View.INVISIBLE);
                        ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik4)).setVisibility(View.INVISIBLE);
                    }
                }
                else{
                    ((EditText)viewToAdd.findViewById(R.id.soru_txtSik4)).setVisibility(View.INVISIBLE);
                    ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik4)).setVisibility(View.INVISIBLE);
                    ((EditText)viewToAdd.findViewById(R.id.soru_txtSik3)).setVisibility(View.INVISIBLE);
                    ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik3)).setVisibility(View.INVISIBLE);

                }
            }
            else{
                ((EditText)viewToAdd.findViewById(R.id.soru_txtSik4)).setVisibility(View.INVISIBLE);
                ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik4)).setVisibility(View.INVISIBLE);
                ((EditText)viewToAdd.findViewById(R.id.soru_txtSik3)).setVisibility(View.INVISIBLE);
                ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik3)).setVisibility(View.INVISIBLE);
                ((EditText)viewToAdd.findViewById(R.id.soru_txtSik2)).setVisibility(View.INVISIBLE);
                ((RadioButton)viewToAdd.findViewById(R.id.radbutton_sik2)).setVisibility(View.INVISIBLE);
            }
            //endregion

            //region READONLY YAPMA
            viewToAdd.findViewById(R.id.soru_txtSoruMetni).setEnabled(false);
            viewToAdd.findViewById(R.id.soru_txtSik0).setEnabled(false);
            viewToAdd.findViewById(R.id.soru_txtSik1).setEnabled(false);
            viewToAdd.findViewById(R.id.soru_txtSik2).setEnabled(false);
            viewToAdd.findViewById(R.id.soru_txtSik3).setEnabled(false);
            viewToAdd.findViewById(R.id.soru_txtSik4).setEnabled(false);
            viewToAdd.findViewById(R.id.radbuttongr_siklar).setEnabled(false);
            //endregion

            //region GÖRSEL EKLEME
            soruGorseli = (ImageView) viewToAdd.findViewById(R.id.imageViewSoruResmi);
            if(soru.getMedyaYolu() != null && soru.getMedyaYolu().length() > 0) {
                Log.d("image ekleyecekmisim",soru.getMedyaYolu());
                try {
                    soruGorseli.setImageDrawable(Drawable.createFromPath(soru.getMedyaYolu()));
                    soruGorseli.setTag(soru.getMedyaYolu());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                soruGorseli.setImageBitmap(null);
            }
            soruGorseli.setEnabled(false);
            //endregion

            RadioGroup radioGroup_Siklar = (RadioGroup)viewToAdd.findViewById(R.id.radbuttongr_siklar);
            for(int i = 0;i<radioGroup_Siklar.getChildCount();i++){
                ((RadioButton)radioGroup_Siklar.getChildAt(i)).setEnabled(false);
            }

            layout.addView(viewToAdd);
        }
    }
    public void buttonSoruDuzenle(View view){

        //region DEGISKEN OLUSTURMA
        ViewGroup parentView = (ViewGroup)view.getParent();
        Button duzenlemeButonu = (Button)parentView.findViewById(R.id.button_soruEdit);
        EditText soruMetni = (EditText)parentView.findViewById(R.id.soru_txtSoruMetni);
        EditText sik0 = (EditText)parentView.findViewById(R.id.soru_txtSik0);
        EditText sik1 = (EditText)parentView.findViewById(R.id.soru_txtSik1);
        EditText sik2 = (EditText)parentView.findViewById(R.id.soru_txtSik2);
        EditText sik3 = (EditText)parentView.findViewById(R.id.soru_txtSik3);
        EditText sik4 = (EditText)parentView.findViewById(R.id.soru_txtSik4);
        ImageView soruGorseli = (ImageView)parentView.findViewById(R.id.imageViewSoruResmi);
        RadioGroup radioGroup_Siklar = (RadioGroup)parentView.findViewById(R.id.radbuttongr_siklar);
        TextView soruID = (TextView)parentView.findViewById(R.id.txt_soruID);
        //endregion
        //region BOS OLMAYAN SIKLARI OKUMA
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
        //endregion
        //region BUTON İŞLEVİNE KARAR VERİLMESİ
        if(duzenlemeButonu.getText().toString().equals("Kaydet")){

            //region GIRDI KONTROLU VE OKUNMASI
            if(soruMetni.getText().length()==0 || siklar.size() <2 || radioGroup_Siklar.getCheckedRadioButtonId()==-1){
                Toast.makeText(this,"Lütfen soru metnini giriniz, en az iki adet şıkkı doldurunuz ve doğru şıkkı seçiniz.",Toast.LENGTH_LONG).show();
                return;
            }
            Log.d("sikLen",String.valueOf(siklar.size()));
            Soru soru = new Soru(kullaniciEposta,soruMetni.getText().toString(),siklar.size(),siklar.toArray(new String[0]),Integer.valueOf(((RadioButton)parentView.findViewById(radioGroup_Siklar.getCheckedRadioButtonId())).getText().toString()));
            if(soruGorseli.getTag()!=null)
                soru.setMedyaYolu(soruGorseli.getTag().toString(),1);
            //endregion
            //region YENI SORU MU OLUSTURULACAK? YOKSA VAROLAN SORU MU DUZENLENECEK?
            if(soruID.getText().toString().length()==0){
                if(db.soruEkle(soru)){

                    int bgc = Color.argb(alpha,rgb[0],rgb[1],rgb[2]);
                    if(rgb[(lastTo255+1)%3]!=255){
                        rgb[(lastTo255+1)%3]+=colorChange;
                    }
                    else if(rgb[lastTo255]>0){
                        rgb[lastTo255]-=colorChange;
                    }
                    else{
                        lastTo255=(lastTo255+1)%3;
                    }
                    viewToAdd = LayoutInflater.from(this).inflate(R.layout.sorular_liste_ogesi_metinli,null);
                    viewToAdd.findViewById(R.id.soru_cardView).setBackgroundColor(bgc);
                    ((Button)viewToAdd.findViewById(R.id.button_soruEdit)).setText("Kaydet");
                    layout.addView(viewToAdd);
                }
            }
            else {
                Log.d("Soru duzenle oldugunu anladım","");
                soru.setSoruID(Integer.valueOf(soruID.getText().toString()));
                Log.d("Sorunun ID'si",soruID.getText().toString());
                db.soruDuzenle(soru);
            }
            //endregion
            //region READONLY YAPMA
            soruMetni.setEnabled(false);
            sik0.setEnabled(false);
            sik1.setEnabled(false);
            sik2.setEnabled(false);
            if(sik2.getText().toString().length()==0){
                parentView.findViewById(R.id.radbutton_sik2).setVisibility(View.INVISIBLE);
                sik2.setVisibility(View.INVISIBLE);
            }
            sik3.setEnabled(false);
            if(sik2.getText().toString().length()==0){
                parentView.findViewById(R.id.radbutton_sik3).setVisibility(View.INVISIBLE);
                sik2.setVisibility(View.INVISIBLE);
            }
            sik4.setEnabled(false);
            if(sik2.getText().toString().length()==0){
                parentView.findViewById(R.id.radbutton_sik4).setVisibility(View.INVISIBLE);
                sik2.setVisibility(View.INVISIBLE);
            }
            radioGroup_Siklar.setEnabled(false);
            for(int i = 0;i<radioGroup_Siklar.getChildCount();i++){
                ((RadioButton)radioGroup_Siklar.getChildAt(i)).setEnabled(false);
            }
            soruGorseli.setEnabled(false);
            if(soruGorseli.getTag()==null)
                soruGorseli.setImageResource(0);
            duzenlemeButonu.setText("Düzenle");
            //endregion
        }
        else{
            //region READONLY KALDIRMA
            soruMetni.setEnabled(true);
            sik0.setEnabled(true);
            sik1.setEnabled(true);
            sik2.setEnabled(true);
            sik3.setEnabled(true);
            sik4.setEnabled(true);
            radioGroup_Siklar.setEnabled(true);
            for(int i = 0;i<radioGroup_Siklar.getChildCount();i++){
                ((RadioButton)radioGroup_Siklar.getChildAt(i)).setEnabled(true);
            }
            soruGorseli.setEnabled(true);
            if(soruGorseli.getTag()==null){
                soruGorseli.setImageResource(R.drawable.pick_image);
            }
            sik2.setVisibility(View.VISIBLE);
            sik3.setVisibility(View.VISIBLE);
            sik4.setVisibility(View.VISIBLE);
            ((RadioButton)parentView.findViewById(R.id.radbutton_sik2)).setVisibility(View.VISIBLE);
            ((RadioButton)parentView.findViewById(R.id.radbutton_sik3)).setVisibility(View.VISIBLE);
            ((RadioButton)parentView.findViewById(R.id.radbutton_sik4)).setVisibility(View.VISIBLE);
            duzenlemeButonu.setText("Kaydet");
            //endregion
        }
        //endregion
    }

    public void resimSec(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra("IMAGE_VIEW_ID",view.getId());
        intent.setAction(Intent.ACTION_PICK);
        soruGorseli = (ImageView)view;
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    public void soruSil(View view){
        ViewGroup parentView = (ViewGroup)view.getParent();
        String soruID = ((TextView)parentView.findViewById(R.id.txt_soruID)).getText().toString();

        if(soruID!=null & soruID.length()>0){
            db.soruSil(Integer.valueOf(soruID));
            parentView.removeAllViews();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            try {
                imageURI = data.getData();

                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),imageURI);
                soruGorseli.setImageBitmap(bmp);
                //soruGorseli.setTag(imageURI.toString());

                String path = getPath(this,imageURI);
                String name = getName(imageURI);
                Log.d("name",name);
                Log.d("path",path);

                /*
                try {
                    saveToLocal(path,name);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }*/

                soruGorseli.setTag(path);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
/*
    private void saveToLocal(String path, String name) throws FileNotFoundException {
        FileOutputStream fos = openFileOutput(name,MODE_APPEND);
        File file = new File(path);
        byte[] bytes = new byte[(int)file.length()];

        try{
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes,0,bytes.length);
            buf.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    private String getName(Uri uri){
        String result = null;
        if(uri.getScheme().equals("content")){
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            try {
                if(cursor != null & cursor.moveToFirst()){
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }
        }
        if(result==null){
            result=uri.getPath();
            int cut = result.lastIndexOf('/');
            if(cut!=-1){
                result=result.substring(cut + 1);
            }
        }
        return result;
    }
    private String getPath(Activity context, Uri uri){
        String[] proj = {MediaStore.Files.FileColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri,proj,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
            int colindex = cursor.getColumnIndexOrThrow(proj[0]);
            String val = cursor.getString(colindex);
            return val;
        }
        else
            return uri.getPath();
    }

}