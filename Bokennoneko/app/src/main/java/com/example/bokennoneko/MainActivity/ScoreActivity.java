package com.example.bokennoneko.MainActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bokennoneko.MainActivity.Adapter.ScoresAdapter;
import com.example.bokennoneko.Model.Score;
import com.example.bokennoneko.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class ScoreActivity extends AppCompatActivity {

    RecyclerView recscore;
    ScoresAdapter scoresAdapter;
    List<Score> mScores;

    FirebaseUser fuser;
    DatabaseReference reference;

    GifImageView cat_angry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_score);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        cat_angry = findViewById(R.id.cat_angry);

        recscore  = findViewById(R.id.recscore);
        recscore.setHasFixedSize(true);
        recscore.setLayoutManager(linearLayoutManager);

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Scores");

        scoreList();
    }

    private void scoreList() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mScores = new ArrayList<>();
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {
                    Score score = postsnap.getValue(Score.class);
                    mScores.add(score);
                }

                Collections.sort(mScores, new Comparator<Score>() {
                    @Override
                    public int compare(Score obj1, Score obj2) {
                        if (obj1.getMaxscore() < obj2.getMaxscore()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });

                scoresAdapter = new ScoresAdapter(getApplicationContext(), mScores);
                recscore.setAdapter(scoresAdapter);

                cat_angry.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
