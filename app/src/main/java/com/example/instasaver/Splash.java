package com.example.instasaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread td=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1500);
                }
                catch (Exception ex){
                    Toast.makeText(Splash.this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                finally {
                    Intent intent=new Intent(Splash.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };td.start();
    }
}