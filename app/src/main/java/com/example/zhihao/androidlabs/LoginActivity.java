package com.example.zhihao.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.widget.EditText;

public class LoginActivity extends Activity {
    protected static final String ACTIVITY_NAME="LoginActivity";
    private static final String MY_PREF="MY_PREF";
    private static final String DEFAULT_EMAIL_KEY = "DEFAULT_EMAIL_KEY";
    private Button loginButton;
    private SharedPreferences sharedPrfe;
    private EditText eamilEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.button_id);
        eamilEditText = findViewById(R.id.email_edit_text);
        sharedPrfe = getSharedPreferences(MY_PREF,Context.MODE_PRIVATE);
        String defaultEmail = sharedPrfe.getString("DefaultEmail","email@example.com");
        eamilEditText.setText(defaultEmail);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = eamilEditText.getText().toString();
                SharedPreferences.Editor edit=sharedPrfe.edit();
                edit.putString("DefaultEmail",input);
                edit.commit();

                Intent intent = new Intent(LoginActivity.this,StartActivity.class);
                startActivity(intent);

            }
        });

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
