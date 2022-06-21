package com.gahlot.project.gahlot.tic_tac_toi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gahlot.project.gahlot.tic_tac_toi.databinding.ActivityMainBinding;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    int xPlayerScore = 0;
    int oPlayerScore = 0;

    String xPlayerName;
    String oPlayerName;

    boolean gameActive = true;
    int activePlayer = 0;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winPosition = {{0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}};

    TextView xScore;
    TextView oScore;
    TextView xPlayerNametv;
    TextView oPlayerNametv;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.WHITE);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        xPlayerNametv = findViewById(R.id.tvxPlayer);
        oPlayerNametv = findViewById(R.id.tvoPlayer);

        xPlayerName = getIntent().getStringExtra("playerx");
        oPlayerName = getIntent().getStringExtra("playero");

        xPlayerNametv.setText(xPlayerName);
        oPlayerNametv.setText(oPlayerName);



        xScore = findViewById(R.id.xScore);
        oScore = findViewById(R.id.oScore);

    }



    public void playerClick(View view){
        boolean reset = true;
        ImageView img = (ImageView) view;
        int position = Integer.parseInt(img.getTag().toString());
        if(gameState[position] == 2 && gameActive){
            gameState[position] = activePlayer;
            img.setTranslationY(-1000f);
            if(activePlayer==0){
                img.setImageResource(R.drawable.ic_x);
                gameState[position] = 0;
                activePlayer = 1;
            }
            else{
                img.setImageResource(R.drawable.ic_o);
                gameState[position] = 1;
                activePlayer = 0;
            }

            img.animate().translationYBy(1000).setDuration(300);
        }

        for(int[] winState: winPosition){
            if(gameState[winState[0]]==gameState[winState[1]] && gameState[winState[1]]==gameState[winState[2]] && gameState[winState[0]]!=2){
                if(gameState[winState[0]] == 0) {
                    winStatus(0);
                    xPlayerScore++;
                    xScore.setText(String.valueOf(xPlayerScore));
                    gameActive = false;
                }else{
                    winStatus(1);
                    oPlayerScore++;
                    oScore.setText(String.valueOf(oPlayerScore));
                    gameActive = false;
                }

            }

        }

        for(int i = 0;i<gameState.length;i++){
            if(gameState[i]==2){
                reset = false;
            }
        }
        if(reset==true)
           reset();
    }

    private void winStatus(int i) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.retry_close_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView winnerName = dialog.findViewById(R.id.winner_Name);
        ImageView retry = dialog.findViewById(R.id.retry);
        ImageView close = dialog.findViewById(R.id.close);
        RelativeLayout container = dialog.findViewById(R.id.status);

        if(i==0){
            winnerName.setText(xPlayerName);
            container.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.blue));
        }else{
            winnerName.setText(oPlayerName);
            container.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.orange));
        }

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                reset();

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                winnerDialog();
            }
        });

        dialog.show();

    }

    private void reset() {
        ImageView imageView1 = findViewById(R.id.image1);
        ImageView imageView2 = findViewById(R.id.image2);
        ImageView imageView3 = findViewById(R.id.image3);
        ImageView imageView4 = findViewById(R.id.image4);
        ImageView imageView5 = findViewById(R.id.image5);
        ImageView imageView6 = findViewById(R.id.image6);
        ImageView imageView7 = findViewById(R.id.image7);
        ImageView imageView8 = findViewById(R.id.image8);
        ImageView imageView9 = findViewById(R.id.image9);

        imageView1.setImageResource(0);
        imageView2.setImageResource(0);
        imageView3.setImageResource(0);
        imageView4.setImageResource(0);
        imageView5.setImageResource(0);
        imageView6.setImageResource(0);
        imageView7.setImageResource(0);
        imageView8.setImageResource(0);
        imageView9.setImageResource(0);

        for(int i = 0;i<gameState.length;i++){
            gameState[i] = 2;
        }
        gameActive = true;

    }

    private void winnerDialog() {
        Dialog exitDialog = new Dialog(this);
        exitDialog.setContentView(R.layout.winner_dialog);
        exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView winnerStatus = exitDialog.findViewById(R.id.winnerStatus);
        Button exit = exitDialog.findViewById(R.id.exit);
        LinearLayout dialogcolor = exitDialog.findViewById(R.id.exitDialog);
        if(xPlayerScore>oPlayerScore){
            winnerStatus.setText("Winner of this Match "+xPlayerName);
            dialogcolor.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.blue));
        }else if(xPlayerScore<oPlayerScore){
            winnerStatus.setText("Winner of this Match "+oPlayerName);
            dialogcolor.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.orange));
        }else{
            winnerStatus.setText("Tie");
            dialogcolor.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.light_gray));
        }

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });

        exitDialog.show();
    }
}