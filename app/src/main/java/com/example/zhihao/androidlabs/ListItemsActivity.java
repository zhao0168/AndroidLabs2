package com.example.zhihao.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;


public class ListItemsActivity extends Activity {
    protected static final String ACTIVITY_NAME="ListItemsActivity";
    private ImageButton imagebutton;
    static final int  REQUEST_TMAGE_CAPTURE=1;
    private Switch aSwitch;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        imagebutton= findViewById(R.id.imageButton);
        checkBox=findViewById(R.id.checkBox2);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    Dialog();

                }
            }
        });
        aSwitch= findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    CharSequence text = "Switch is On";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(ListItemsActivity.this,text,duration);
                    toast.show();
                }else {
                    CharSequence text = "Switch is off";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(ListItemsActivity.this,text,duration);
                    toast.show();
                }

            }
        });
        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePictureIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(takePictureIntent,REQUEST_TMAGE_CAPTURE);
                }
            }
        });



    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==REQUEST_TMAGE_CAPTURE && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagebutton.setImageBitmap(imageBitmap);
        }
    }
    protected void Dialog(){
        AlertDialog.Builder b = new AlertDialog.Builder(ListItemsActivity.this);
        b.setMessage("Do you want to finish this ?");
        b.setTitle("**");
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Response","Here is my response");
                setResult(Activity.RESULT_OK,resultIntent);
                finish();
            }
        });
        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        b.show();
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