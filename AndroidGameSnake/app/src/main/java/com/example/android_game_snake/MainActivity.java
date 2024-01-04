package com.example.android_game_snake;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.*;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Burda AdView objesini oluşturuyoruz ve anasayfa.xml de oluşturduğumuz adView e bağlıyoruz


        sadsad();
        Button btn=(Button)findViewById(R.id.button);
        Button btn2=(Button)findViewById(R.id.button2);
        Button btn3=(Button)findViewById(R.id.button3);

btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(),Snake.class));

    }
});



        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://play.google.com/store/apps/dev?id=6929581014777072329";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });



        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Snake.class));
                String url = "http://www.ferhatozcelik.blogspot.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void sadsad() {

        ImageView image = (ImageView)findViewById(R.id.idImageView);
        Animation animation = AnimationUtils.loadAnimation(getApplication(), R.anim.fade_in);
        image.startAnimation(animation);


        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("A81CB541A31A00F6F8716568845CF269")
                .build();//Test i�lemlerinde bunu �zellikle eklememiz gerekiyor. Yoksa banlan�r�z.

        AdView bannerReklam = (AdView) this.findViewById(R.id.adView1);
        bannerReklam.loadAd(adRequest); //bannerReklam � y�kl�yoruz.





        AdRequest adRequestt = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("A81CB541A31A00F6F8716568845CF269")
                .build();//Test i�lemlerinde bunu �zellikle eklememiz gerekiyor. Yoksa banlan�r�z.

        AdView bannerReklamm = (AdView) this.findViewById(R.id.adView2);
        bannerReklamm.loadAd(adRequestt); //bannerReklam � y�kl�yoruz.






    }


    public void BaglantiHatasiVer() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);
        alertDialogBuilder.setTitle("Bağlantı Hatası");
        alertDialogBuilder.setMessage(
                "İnternet bağlantınızı kontrol edip tekrar deneyiniz.")
                .setCancelable(true);


        alertDialogBuilder.setNegativeButton("Tamam",
                new DialogInterface.OnClickListener() {

                    private static final int giriss = 0;

                    public void onClick(DialogInterface dialog, int which) {

                        System.exit(giriss);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public boolean InternetKontrol() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo() != null
                && manager.getActiveNetworkInfo().isAvailable()
                && manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }

    }
}
