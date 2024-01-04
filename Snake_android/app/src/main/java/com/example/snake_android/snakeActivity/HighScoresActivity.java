package com.example.snake_android.snakeActivity;

import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import com.example.snake_android.R;
import com.example.snake_android.snakeLogic.PointsComparator;
import com.example.snake_android.snakeLogic.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HighScoresActivity extends AppCompatActivity {

    Button menu;
    Button clearList;
    TextView highScoresList;
    private List<User> userScores;
    private GraphicsTools tools;
    private int linesColor = Color.WHITE;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SnakeFiles";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_high_scores);

        menu = (Button) findViewById(R.id.HighScoresMenuButton);
        clearList = (Button) findViewById(R.id.HighScoresClearListButton);
        highScoresList = (TextView) findViewById(R.id.HighScoresList);

        tools = new GraphicsTools();
        userScores = new ArrayList<User>();


        final File file = new File(path + "/HighScores.txt");

        //Log.v("czytaniezplikusnake", "uruchamiam metodę readFromFile");
        try {
            readFromFile(file);
        } catch (IOException ex) {
            System.out.println(ex + "Nieudana próba odczytu pliku.");
        }


        highScoresList.setMovementMethod(new ScrollingMovementMethod());


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        clearList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    clearList(file);
                } catch (IOException e) {
                    System.out.println(e + "Nieudana próba odczytu pliku.");
                }
                //Log.v("czytaniezplikusnake", "wyczyszczono zawartosc pliku");
                highScoresList.setText("");
            }
        });


    }

    public void clearList(File file) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, false);
            //Log.v("czytaniezplikusnake", "otworzylem plik");
            fos.write(("").getBytes());
            //Log.v("czytaniezplikusnake", "wpisalem pusty tekst");

        } finally {
            if (fos != null) {
                fos.close();
            }
        }

    }


    public void readFromFile(File file) throws IOException {
        int licznik = 1;

        String line;
        String name;
        int points;
        FileInputStream fis = null;
        Scanner scannerFile = null;


        try {
            //Log.v("czytaniezplikusnake", "tworze fileinputstream");
            fis = new FileInputStream(file);

            InputStreamReader isr = new InputStreamReader(fis);


            scannerFile = new Scanner(new BufferedReader(isr));

            while (scannerFile.hasNext()) {
                //Log.v("czytaniezplikusnake", "czytam");
                name = scannerFile.next();
                while (!scannerFile.hasNextInt()) {
                    name += (" " + scannerFile.next());
                }
                points = scannerFile.nextInt();
                userScores.add(new User(name, points));
                licznik++;
            }

            Collections.sort(userScores, new PointsComparator());
            //Log.v("czytaniezplikusnake", "posortowalem");

            for (int i = 0; i < licznik - 1; i++) {
                line = (i + 1) + ".  " + userScores.get(i).getUserName() + "     " + userScores.get(i).getPoints() + " PKT";
                highScoresList.append(line + "\n");
                //Log.v("czytaniezplikusnake", "dodaje do textview");

            }
        } finally {
            if (fis != null && scannerFile != null) {
                fis.close();
            }
        }
    }


}

