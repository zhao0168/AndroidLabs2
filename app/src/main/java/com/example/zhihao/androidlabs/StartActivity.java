package com.example.zhihao.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME="StartActivity";
    private static final int REQUEST_CODE = 50;
    private Button buttonstart;
    private Button startchat;
    private Button weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        buttonstart=findViewById(R.id.button1);
        startchat=findViewById(R.id.startButton);
        weather=findViewById(R.id.weather);
        buttonstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME,"user click the start chat");
                Intent intent = new Intent(StartActivity.this,ListItemsActivity.class);
                startActivityForResult(intent,50);
            }
        });
        startchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,ChatWindow.class);
                startActivityForResult(intent,50);

            }

        });
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,WeatherForecast.class);
                startActivityForResult(intent,50);

            }

        });
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if (requestCode==50 ) {
            if (resultCode == RESULT_OK ) {
                Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                String messagePassed = data.getStringExtra("Response");
                if(messagePassed!=null){
                    Toast.makeText(getApplicationContext(),messagePassed,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(ACTIVITY_NAME,"In onStart()");
    }
    @Override
    public void onPause(){
        super.onPause();

        Log.i(ACTIVITY_NAME,"In onPause()");
    }
    @Override
    public void onStop(){
        super.onStop();

        Log.i(ACTIVITY_NAME,"In onStop()");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }
}