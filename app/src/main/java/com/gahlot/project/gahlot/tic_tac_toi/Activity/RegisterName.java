package com.gahlot.project.gahlot.tic_tac_toi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.gahlot.project.gahlot.tic_tac_toi.MainActivity;
import com.gahlot.project.gahlot.tic_tac_toi.R;
import com.gahlot.project.gahlot.tic_tac_toi.databinding.ActivityRegisterNameBinding;

public class RegisterName extends AppCompatActivity {
    ActivityRegisterNameBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.WHITE);
        }

        binding.continueGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String playerx = binding.playerx.getText().toString();
                String playero = binding.playero.getText().toString();
                if(!TextUtils.isEmpty(playerx)&&!TextUtils.isEmpty(playero)){
                    Intent intent = new Intent(RegisterName.this, MainActivity.class);
                    intent.putExtra("playerx",playerx);
                    intent.putExtra("playero",playero);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Please Enter Players Name", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}